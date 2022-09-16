package Manager.entities;

import Client.entities.Client;
import Lodge.entities.LodgeInfo;
import Reservation.entities.BookingState;

import java.util.List;

/**
 * Représente les informations d'une reservation
 */
public class BookingRecord {
    /**
     * Identifant de la réservation
     */
    private int id;
    /**
     * Informations du client
     */
    private Client client;
    /**
     * Liste de logements réservés par le client
     */
    private List<LodgeInfo> accommodations;
    public BookingState bookingState;


    public BookingState getBookingState() {
        return bookingState;
    }
    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public List<LodgeInfo> getAccommodations() {
        return accommodations;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAccommodations(List<LodgeInfo> accommodations) {
        this.accommodations = accommodations;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }

    @Override
    public String toString() {
        StringBuilder result;

        result = new StringBuilder("[Client : " + client + "]\n");

        for (LodgeInfo acc : accommodations) {
            result.append("[Lodge : ").append(acc).append("]");
        }

        return result.toString();
    }
}
