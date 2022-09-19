package Reservation.interfaces;

import Reservation.entities.Booking;

public interface SystemeGestionReservations {
    Booking buildBooking();
    void mainMenu();
    void reservationMenu();
    void reservationImpl();
    void addClient();
    void addLodgeInformation();
    void seeListOfBooking();
    void cancelBooking();
}
