package Manager.entities;

import Reservation.entities.BookingRecord;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * Représente une agence de voyage
 */
public class TravelAgency {
    private int id;
    /**
     * Nom du manager
     */
    private String managerName;

    /**
     * Liste des réservations effectuées avec
     * cette agence
     */
    private List<BookingRecord> bookingRecords;

    public TravelAgency() {

    }

    public TravelAgency(String managerName, Optional<List<BookingRecord>> bookingRecords) {
        this.id = new SecureRandom().nextInt();
        this.managerName = managerName;
        bookingRecords.ifPresent(records -> this.bookingRecords = records);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setBookingRecords(List<BookingRecord> bookingRecords) {
        this.bookingRecords = bookingRecords;
    }

    @Override
    public String toString() {
        StringBuilder result;

        result = new StringBuilder("[Manager Name : " + managerName + "]\n");

        for (BookingRecord book : bookingRecords) {
            result.append("[Booking : ").append(book).append("]");
        }

        return result.toString();
    }
}
