package Reservation;

import Client.entities.ClientInfoGathering;
import Database.DatabaseManager;
import Database.IDatabaseManager;
import Lodge.entities.LodgeInfo;
import Reservation.entities.Booking;

import java.util.Objects;
import java.util.Scanner;

public class BookingMain {
    private String clientEmail;
    private final IDatabaseManager databaseManager;
    private BookingBuilder bookingBuilder;

    public BookingMain(DatabaseManager database) {
        databaseManager = database;
    }

    public void makeBookingByClient() {

    }

    public Booking getPendingBookings() {
        return null;
    }

    public void cancelBooking(String clientEmail) {

    }

    public Booking searchForBooking(ClientInfoGathering client) {
        LodgeInfo lodgeInfo = databaseManager.findLodgeFromClientDemands(client);

        System.out.println("Un logement à " + lodgeInfo.getAddress().getFullAddress() + " trouvé.");

        int bookingId = databaseManager.addBooking(client, lodgeInfo, 0);
        System.out.println("Booking ID : " + bookingId);

        bookingBuilder = new BookingBuilder(databaseManager.getBookingRecordById(bookingId));
        return bookingBuilder.buildBooking();
    }
}
