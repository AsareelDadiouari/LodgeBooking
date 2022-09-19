package Reservation;

import Client.entities.Client;
import Lodge.entities.RoomType;
import Reservation.entities.Booking;
import Reservation.entities.BookingRecord;
import Reservation.entities.BookingState;

import java.time.LocalDate;

public class BookingBuilder {
    private Client client;
    private String lodgeName;
    private String lodgeAddress;
    private int numberOfRooms;
    private RoomType roomType;
    private LocalDate checkIn, checkOut;
    private BookingState state;

    private Booking result;
    private int id;

    public BookingBuilder(int id,
                          Client client,
                          String lodgeName,
                          String lodgeAddress,
                          int numberOfRooms,
                          RoomType roomType,
                          LocalDate checkIn,
                          LocalDate checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.lodgeName = lodgeName;
        this.client = client;
        this.lodgeAddress = lodgeAddress;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
        this.id = id;
    }

    public BookingBuilder(BookingRecord bookingRecord){
        if (bookingRecord.getDuration() != null){
            this.checkIn = LocalDate.parse(bookingRecord.getDuration().split(" ")[0]);
            this.checkOut = LocalDate.parse(bookingRecord.getDuration().split(" ")[1]);
        }

        this.lodgeName = bookingRecord.getLodgeName();
        this.client = bookingRecord.getClientInfo();
        this.lodgeAddress = bookingRecord.getAccomodations().get(0).getAddress().getFullAddress();
        this.roomType = RoomType.valueOf(bookingRecord.getTypeOfRoom());
        System.out.println("Booking record Id from the builder : " + bookingRecord.getId());
        this.id = bookingRecord.getId();
    }

    public BookingBuilder lodgeAddress(String lodgeAddress) {
        this.lodgeAddress = lodgeAddress;
        return this;
    }

    public BookingBuilder roomType(RoomType roomType) {
        this.roomType = roomType;
        return this;
    }

    public BookingBuilder lodgeName(String lodgeName) {
        this.lodgeName = lodgeName;
        return this;
    }

    public BookingBuilder numberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
        return this;
    }

    public BookingBuilder client(Client client) {
        this.client = client;
        return this;
    }

    public BookingBuilder checkIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public BookingBuilder checkOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public Booking buildBooking() {
        Booking booking = new Booking(id, client, lodgeName, lodgeAddress, numberOfRooms, roomType, checkIn, checkOut, state);
        //this.validateBooking(booking);
        this.result = booking;
        return booking;
    }

    private void validateBooking(Booking booking) {

    }

}
