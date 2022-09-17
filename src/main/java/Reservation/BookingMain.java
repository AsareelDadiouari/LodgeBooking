package Reservation;

import Database.IDatabaseManager;
import Reservation.entities.Booking;

public class BookingMain {
    private String clientEmail;
    private IDatabaseManager databaseManager;

    public BookingMain(IDatabaseManager database) {
        databaseManager = database;
    }

    public void makeBookingByClient() {

    }

    public Booking getPendingBookings() {
        return null;
    }

    public void cancelBooking(String clientEmail) {

    }
}
