package Reservation.interfaces;

import Reservation.entities.Booking;

/**
 * Interface implémentant les fonctionnalités principales
 * de l'application
 */
public interface SystemeGestionReservations {
    Booking buildBooking();

    void mainMenu();

    void agencyMenu();

    void reservationImpl();

    void addClient();

    void addLodgeInformation();

    void seeListOfBooking();

    void cancelBooking();
}
