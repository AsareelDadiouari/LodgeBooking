package Lodge.entities;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Représente l'entité chambre
 */
public class Room {

    protected int id;
    protected RoomType roomType;
    protected double price;
    protected LocalDate checkIn, checkOut;

    protected boolean isAvailable;

    public Room() {
    }

    public Room(int id, RoomType roomType, double price,
                Optional<LocalDate> checkIn, Optional<LocalDate> checkOut) {
        this.id = id;
        checkIn.ifPresent(localDate -> this.checkIn = localDate);
        checkOut.ifPresent(localDate -> this.checkOut = localDate);
        this.roomType = roomType;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

}
