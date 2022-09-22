package Reservation.entities;

import Client.entities.Client;
import Lodge.entities.LodgeInfo;

import java.util.ArrayList;
import java.util.List;

public class BookingRecord {
    private int id;
    private Client clientInfo;
    private String lodgeName, typeOfRoom, duration;
    private BookingState bookingState;
    private List<LodgeInfo> accommodations;

    public BookingRecord(Client clientInfo, String lodgeName, String typeOfRoom, String duration, BookingState bookingState) {
        this.clientInfo = clientInfo;
        this.lodgeName = lodgeName;
        this.typeOfRoom = typeOfRoom;
        this.duration = duration;
        this.bookingState = bookingState;
        this.accommodations = new ArrayList<>();
    }

    public BookingRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LodgeInfo> getAccomodations() {
        return accommodations;
    }

    public Client getClientInfo() {
        return clientInfo;
    }

    public String getLodgeName() {
        return lodgeName;
    }

    public void setLodgeName(String lodgeName) {
        this.lodgeName = lodgeName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    public void setTypeOfRoom(String typeOfRoom) {
        this.typeOfRoom = typeOfRoom;
    }

    public BookingState getBookingState() {
        return bookingState;
    }

    public void setBookingState(BookingState bookingState) {
        this.bookingState = bookingState;
    }

    public void setAccommodations(List<LodgeInfo> accomodations) {
        this.accommodations = accomodations;
    }

    public void setClient(Client clientInfo) {
        this.clientInfo = clientInfo;
    }

    @Override
    public String toString() {
        return "Client : [" + clientInfo.getFullName() + "]\n" +
                "Lodge : [Type: " + typeOfRoom + "][Name: " + lodgeName + "][Duration: " + duration + "]\n" +
                "Etat : " + bookingState.name();
    }
}
