package Reservation.interfaces;

import Client.entities.ClientInfoGathering;
import Reservation.entities.Booking;

public interface SystemeGestionReservations {
    Booking buildBooking();
    void mainMenu();
    void reservationMenu();
    void reservationImpl();
    void addClient();
    void addLodgeInformations();
    boolean searchForBooking(ClientInfoGathering client);
}
