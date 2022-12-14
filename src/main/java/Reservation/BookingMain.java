package Reservation;

import Client.entities.ClientInfoGathering;
import Database.DatabaseManager;
import Database.IDatabaseManager;
import Lodge.entities.LodgeInfo;
import Reservation.entities.Booking;
import Reservation.entities.BookingRecord;
import Reservation.entities.BookingState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BookingMain {
    private final IDatabaseManager databaseManager;
    private String clientEmail;
    private BookingBuilder bookingBuilder;

    public BookingMain(DatabaseManager database) {
        databaseManager = database;
    }

    public void makeBookingByClient() {

    }

    public List<Booking> getPendingBookings() {
        List<BookingRecord> records = databaseManager.getBookings().stream().filter(bookingRecord -> bookingRecord.getBookingState() == BookingState.PENDING).collect(Collectors.toList());
        List<Booking> bookings = new ArrayList<>();

        records.forEach(record -> {
            bookingBuilder = new BookingBuilder(record);
            bookings.add(bookingBuilder.buildBooking());
        });

        return bookings;
    }

    public void cancelBooking() {
        System.out.println("--- Reservations ---");
        List<BookingRecord> bookingRecords = databaseManager.getBookings();
        Scanner sc = new Scanner(System.in);
        AtomicInteger clientIndex = new AtomicInteger();

        if (bookingRecords.size() > 0) {
            bookingRecords.forEach(booking -> {
                System.out.println("\t\t--- [" + clientIndex.getAndIncrement() + "] ---");
                System.out.println(booking);
            });

            System.out.print("Choix : ");
            BookingRecord chosenBooking;

            try {
                chosenBooking = bookingRecords.get(Integer.parseInt(sc.next()));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Mauvais choix !");
                return;
            }

            databaseManager.setBookingState(chosenBooking.getId(), BookingState.CANCELLED);

            if (databaseManager.getBookingRecordById(chosenBooking.getId()).getBookingState() == BookingState.CANCELLED)
                System.out.println("R??servation annul??e avec succ??s !");
            else
                System.out.println("Une erreur s'est produite lors de l'annulation");
        } else
            System.out.println("Aucune reservation en cours ou d??ja effectu??e");
    }

    public Booking searchForBooking(ClientInfoGathering client, int travelAgencyId) {
        LodgeInfo lodgeInfo = databaseManager.findLodgeFromClientDemands(client);

        System.out.println("Un logement ?? " + lodgeInfo.getAddress().getFullAddress() + " trouv??.");

        int bookingId = databaseManager.addBooking(client, lodgeInfo, travelAgencyId);
        System.out.println("Booking ID : " + bookingId);

        bookingBuilder = new BookingBuilder(databaseManager.getBookingRecordById(bookingId));
        return bookingBuilder.buildBooking();
    }

    public void listOfBooking(int travelAgencyId) {
        System.out.println("1-Toutes Reservations en attentes");
        System.out.println("2-Toutes Reservations complet??es");
        System.out.println("3-Reservations effectu??s par l'agence");

        Scanner sc = new Scanner(System.in);

        List<BookingRecord> bookingRecords = databaseManager.getBookings();

        System.out.print("Choix : ");

        switch (sc.next()) {
            case "1":
                List<BookingRecord> pendingBookings = bookingRecords.stream().filter(booking -> booking.getBookingState() == BookingState.PENDING).collect(Collectors.toList());

                if (pendingBookings.isEmpty())
                    System.out.println("Aucune reservation trouv??e");
                else
                    pendingBookings.forEach(System.out::println);
                break;
            case "2":
                List<BookingRecord> confirmedBookings = bookingRecords.stream().filter(booking -> booking.getBookingState() == BookingState.CONFIRMED).collect(Collectors.toList());

                if (confirmedBookings.isEmpty())
                    System.out.println("Aucune reservation trouv??e");
                else
                    confirmedBookings.forEach(System.out::println);
                break;
            case "3":
                List<BookingRecord> bookings = databaseManager.getBookingRecordByTravelAgencyId(travelAgencyId);

                if (bookings.isEmpty())
                    System.out.println("Aucune reservation trouv??e");
                else
                    bookings.forEach(System.out::println);
                break;
            default:
                System.out.println("Commande non reconnue, retour...");
                break;
        }
    }
}
