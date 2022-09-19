package Reservation;

import Client.ClientMain;
import Client.entities.ClientInfoGathering;
import Database.DatabaseManager;
import Database.IDatabaseManager;
import Lodge.LodgeMain;
import Lodge.entities.Lodge;
import Lodge.entities.LodgeInfo;
import Reservation.entities.Booking;
import Reservation.entities.BookingState;
import Reservation.interfaces.SystemeGestionReservations;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private SystemeGestionReservations bookingBuilder;
    private final IDatabaseManager database;
    private final ClientMain clientMain;
    private final LodgeMain lodgeMain;
    private final BookingMain bookingMain;
    private String managerName;

    public SystemeGestionReservationsImpl() {
        database = new DatabaseManager();
        clientMain = new ClientMain();
        lodgeMain = new LodgeMain();
        bookingMain = new BookingMain((DatabaseManager) database);
    }

    @Override
    public Booking buildBooking() {
        return bookingBuilder.buildBooking();
    }

    @Override
    public void mainMenu() {
        System.out.println("--- LOGICIEL HEBERGEMENT ---");
        System.out.println("1- Renseignement Client");
        System.out.println("2- Ajouter un hébergement");
        System.out.println("3- Effectuer une reservation");
        System.out.println("4- Annuler une reservation");
        System.out.println("5- Voir les reservations en cours");
        System.out.println("6- Quitter");
    }

    @Override
    public void reservationMenu() {
        System.out.println("--- RESERVATION ---");
    }

    @Override
    public void reservationImpl() {
        clearScreen();
        List<ClientInfoGathering> clientRequests = null;
        Scanner sc = new Scanner(System.in);

        while (true){
            clientRequests = database.getClientInfoGatheringList();

            if(clientRequests.isEmpty()){
                System.out.println("Impossible de faire une reservation" +
                        ", aucune demande en attente");
                System.out.println("Appuyer pour revenir en arriere");
                sc.nextLine();
                break;
            }

            List<ClientInfoGathering> unfulfilledRequests = clientRequests.stream().filter(req -> !req.isFulfilled()).collect(Collectors.toList());
            if (!unfulfilledRequests.isEmpty())
            {
                System.out.println("\t--- DEMANDES EN ATTENTES ---");
                AtomicInteger clientIndex = new AtomicInteger();
                unfulfilledRequests.forEach(req -> {
                    System.out.println("\t\t--- [" + clientIndex.getAndIncrement() + "] ---");
                    System.out.println(req);
                });

                System.out.print("Choix client : ");
                int clientChoiceNumber = Integer.parseInt(sc.next());
                ClientInfoGathering chosenClient;
                try {
                    chosenClient = unfulfilledRequests.get(clientChoiceNumber);
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
                clearScreen();

                assert chosenClient != null;
                System.out.println("[" + chosenClient.getClient().getFullName() + "]");

                System.out.print("Chercher un hébergement pour ce client ? (oui/non) ");
                switch (sc.next()){
                    case "oui" :
                        Booking foundBooking = bookingMain.searchForBooking(chosenClient);

                        if (foundBooking != null){
                            System.out.println("Voulez vous effectuer la reservation ? (oui/non) ");

                            if (Objects.equals(sc.next(), "oui")){
                                System.out.println("ID : " + foundBooking.getId());
                                if (database.setBookingState(foundBooking.getId(), BookingState.CONFIRMED))
                                    database.setClientDemandState(true, chosenClient.getId());
                            }

                        } else
                            System.out.println("Reservation non confirmée");
                        break;
                    case "non" :
                        database.setClientDemandState(false, chosenClient.getId());
                        break;
                    default:
                        System.out.println("Incorrect choice");
                        break;
                }
            }else {
                System.out.println("Aucune demande de reservations en attente," +
                        " appuyer pour quittter");
                sc.nextLine();
                break;
            }
        }
    }

    @Override
    public void addClient() {
        clearScreen();
        ClientInfoGathering clientInfos = clientMain.gatherInfo();
        database.addLodgeAddress(clientInfos.getLodgeAddress(), 0);

        if (clientMain.saveClientInfoQuestion())
            database.addClient(clientInfos.getClient());

        if(clientMain.saveClientRequest())
            database.addClientInfoGathering(clientInfos);
    }

    @Override
    public void addLodgeInformation() {
        clearScreen();
        LodgeInfo lodgeInfo = lodgeMain.gatherInfos();
        Lodge lodge = lodgeMain.getLodge();

        if (lodgeMain.saveInfos())
            database.addLodge(lodge);
    }

    public void Start() {
        Scanner sc = new Scanner(System.in);

        try{
            while (true) {
                mainMenu();
                System.out.print("Choix : ");
                switch (sc.next()) {
                    case "1":
                        addClient();
                        break;
                    case "2":
                        addLodgeInformation();
                        break;
                    case "3":
                        reservationImpl();
                        break;
                    case "4":
                        clearScreen();
                        break;
                    case "5":
                        clearScreen();
                        break;
                    case "6":
                        return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void clearScreen() {
        /*try {
            if(System.getProperty("os.name").startsWith("Windows") )
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("/usr/bin/", "clear").inheritIO().start().waitFor();

        }catch (Exception e){
            e.printStackTrace();
        }*/
    }
}
