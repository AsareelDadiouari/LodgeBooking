package Reservation.entities;

import Client.entities.Client;

public class BookingRecord {
    private int id;
    private final Client clientInfo;

    private final String lodgeName, typeOfRoom, duration;

    public BookingRecord(Client clientInfo, String lodgeName, String typeOfRoom, String duration) {
        this.clientInfo = clientInfo;
        this.lodgeName = lodgeName;
        this.typeOfRoom = typeOfRoom;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public Client getClientInfo() {
        return clientInfo;
    }

    public String getLodgeName() {
        return lodgeName;
    }

    public String getDuration() {
        return duration;
    }

    public String getTypeOfRoom() {
        return typeOfRoom;
    }

    @Override
    public String toString() {
        return "Client : [" + clientInfo.getFullName() + "]\n" +
                "Lodge : [Type: " + typeOfRoom + "][Name: " + lodgeName + "][Duration: " + duration + "]";
    }
}
