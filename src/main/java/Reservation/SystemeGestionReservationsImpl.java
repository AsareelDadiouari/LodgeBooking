package Reservation;

import Client.ClientMain;
import Client.entities.ClientInfoGathering;
import Database.DatabaseManager;
import Database.IDatabaseManager;
import Lodge.LodgeMain;
import Lodge.entities.Lodge;
import Lodge.entities.LodgeInfo;
import Manager.entities.TravelAgency;
import Reservation.entities.Booking;
import Reservation.entities.BookingRecord;
import Reservation.entities.BookingState;
import Reservation.interfaces.SystemeGestionReservations;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Système principale qui gère l'interface graphique et
 * les fonctionnalités principales
 */
public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private final IDatabaseManager database;
    private final ClientMain clientMain;
    private final LodgeMain lodgeMain;
    private final BookingMain bookingMain;
    private SystemeGestionReservations bookingBuilder;
    private TravelAgency agency;


    public SystemeGestionReservationsImpl() {
        database = new DatabaseManager();
        clientMain = new ClientMain();
        lodgeMain = new LodgeMain();
        bookingMain = new BookingMain((DatabaseManager) database);
    }

    private static void clearScreen() {
        /*try {
            if(System.getProperty("os.name").startsWith("Windows") )
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                new ProcessBuilder("/usr/bin/", "clear").inheritIO().start().waitFor();

        }catch (Exception e){
            e.printStackTrace();
        }*/
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
    public void agencyMenu() {
        System.out.println("--- Agence ---");
        System.out.println("1- Nom du manager d'agence");
        System.out.println("0- Quitter");
    }

    @Override
    public void reservationImpl() {
        clearScreen();
        List<ClientInfoGathering> clientRequests;
        Scanner sc = new Scanner(System.in);

        while (true) {
            clientRequests = database.getClientInfoGatheringList();

            if (clientRequests.isEmpty()) {
                System.out.println("Impossible de faire une reservation" +
                        ", aucune demande en attente");
                System.out.println("Appuyer pour revenir en arriere");
                sc.nextLine();
                break;
            }

            List<ClientInfoGathering> unfulfilledRequests = clientRequests.stream().filter(req -> !req.isFulfilled()).collect(Collectors.toList());
            if (!unfulfilledRequests.isEmpty()) {
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
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
                clearScreen();

                assert chosenClient != null;
                System.out.println("[" + chosenClient.getClient().getFullName() + "]");

                System.out.print("Chercher un hébergement pour ce client ? (oui/non) ");
                switch (sc.next()) {
                    case "oui":
                        Booking foundBooking = bookingMain.searchForBooking(chosenClient, agency.getId());

                        if (foundBooking != null) {
                            System.out.println("Voulez vous effectuer la reservation ? (oui/non) ");

                            if (Objects.equals(sc.next(), "oui")) {
                                System.out.println("ID : " + foundBooking.getId());
                                if (database.setBookingState(foundBooking.getId(), BookingState.CONFIRMED))
                                    database.setClientDemandState(true, chosenClient.getId());
                            }

                        } else
                            System.out.println("Reservation non confirmée");
                        break;
                    case "non":
                        database.setClientDemandState(false, chosenClient.getId());
                        break;
                    default:
                        System.out.println("Incorrect choice");
                        break;
                }
            } else {
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

        if (clientMain.saveClientRequest())
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

    @Override
    public void seeListOfBooking() {
        clearScreen();
        bookingMain.listOfBooking(agency.getId());
    }

    @Override
    public void cancelBooking() {
        clearScreen();
        bookingMain.cancelBooking();
    }

    public void Start() {
        Scanner sc = new Scanner(System.in);

        String agencyChoicePrompt;
        do{
            agencyMenu();
            System.out.print("Choix : ");
            agencyChoicePrompt = sc.next();

            sc.nextLine();

            if (agencyChoicePrompt.equals("0"))
                return;
            else if (agencyChoicePrompt.equals("1")){
                System.out.print("Nom de l'agent : ");
                String managerName = sc.nextLine();

                TravelAgency agentExist = database.getTravelAgencyByManagerName(managerName);

                if (agentExist == null){
                    TravelAgency travelAgency = new TravelAgency(managerName, Optional.of(new ArrayList<>()));
                    database.addTravelAgency(travelAgency);
                    this.agency = travelAgency;
                } else {
                    this.agency = agentExist;
                }
            }

        } while (!agencyChoicePrompt.equals("1"));


        try {
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
                        cancelBooking();
                        break;
                    case "5":
                        seeListOfBooking();
                        break;
                    case "6":
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
