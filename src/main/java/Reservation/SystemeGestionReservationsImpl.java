package Reservation;

import Client.ClientMain;
import Client.entities.ClientInfoGathering;
import Database.DatabaseManager;
import Database.IDatabaseManager;
import Lodge.LodgeMain;
import Lodge.entities.Lodge;
import Lodge.entities.LodgeInfo;
import Reservation.entities.Booking;
import Reservation.interfaces.SystemeGestionReservations;

import java.util.List;
import java.util.Scanner;

public class SystemeGestionReservationsImpl implements SystemeGestionReservations {
    private SystemeGestionReservations bookingBuilder;
    private final IDatabaseManager database;
    private final ClientMain clientMain;
    private final LodgeMain lodgeMain;

    public SystemeGestionReservationsImpl() {
        database = new DatabaseManager();
        clientMain = new ClientMain();
        lodgeMain = new LodgeMain();
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
        List<ClientInfoGathering> clientRequests = database.getClientInfoGatheringList();
        while (true){
            if(clientRequests.isEmpty()){
                System.out.println("Impossible de faire une reservation" +
                        ", aucune demande en attente");
                break;
            }
            clientRequests.forEach(req -> {
                System.out.println("fefe");
            });
        }
    }

    @Override
    public void addClient() {
        clearScreen();
        ClientInfoGathering clientInfos = clientMain.gatherInfo();

        if (clientMain.saveClientInfoQuestion())
            database.addClient(clientInfos.getClient());
    }

    @Override
    public void addLodgeInformations() {
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
                        addLodgeInformations();
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
                        clearScreen();
                        break;
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
