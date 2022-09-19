package Reservation.entities;

import Client.entities.Client;
import Lodge.entities.RoomType;

import java.time.LocalDate;


public class Booking {
    private Client client;
    private String lodgeName;
    private String lodgeAddress;
    private int numberOfRooms;
    private RoomType roomType;
    private LocalDate checkIn, checkOut;
    private int id;
    private BookingState state;

    public Booking(int id,
                   Client client,
                   String lodgeName,
                   String lodgeAddress,
                   int numberOfRooms,
                   RoomType roomType,
                   LocalDate checkIn,
                   LocalDate checkOut,
                   BookingState state) {

        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.lodgeName = lodgeName;
        this.client = client;
        this.lodgeAddress = lodgeAddress;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
        this.state = state;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getId() {
        return id;
    }

    public String getLodgeName() {
        return lodgeName;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public Client getClient() {
        return client;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public String getLodgeAddress() {
        return lodgeAddress;
    }

    public BookingState getState() {
        return state;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public void setLodgeName(String lodgeName) {
        this.lodgeName = lodgeName;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setLodgeAddress(String lodgeAddress) {
        this.lodgeAddress = lodgeAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(BookingState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        String result;

        result = "[Client : " + client.getFullName() + "]" +
                "[Lodge name : " + lodgeName + "]" +
                "[Lodge address : " + lodgeAddress + "]" +
                "[Room Type : " + roomType.toString() + "]" +
                "[Check In : " + (checkIn == null ? "" : checkIn.toString()) + "]" +
                "[Check Out : " + (checkOut == null ? "" : checkOut.toString()) + "]";

        return result;
    }
}
