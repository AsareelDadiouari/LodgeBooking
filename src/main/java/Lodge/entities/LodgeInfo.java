package Lodge.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représente le log d'un hébergement
 */
public class LodgeInfo {
    private int id;
    private int numberOfRooms;
    private String lodgeName;
    private LodgeType lodgeType;
    private LodgeAddress address;
    private List<Room> availableRooms;
    private Set<String> offeredServices;

    public LodgeInfo() {
        offeredServices = new HashSet<>();
        address = new LodgeAddress();
    }

    public LodgeInfo(int id, String lodgeName, int numberOfRooms,
                     LodgeType lodgeType, List<Room> availableRooms,
                     Set<String> services) {
        this.availableRooms = availableRooms;
        this.lodgeName = lodgeName;
        this.numberOfRooms = numberOfRooms;
        this.id = id;
        this.lodgeType = lodgeType;
        this.offeredServices = services;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<String> getOfferedServices() {
        return offeredServices;
    }

    public void setOfferedServices(Set<String> offeredServices) {
        this.offeredServices = offeredServices;
    }

    public LodgeAddress getAddress() {
        return address;
    }

    public void setAddress(LodgeAddress address) {
        this.address = address;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public List<Room> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<Room> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getLodgeName() {
        return lodgeName;
    }

    public void setLodgeName(String lodgeName) {
        this.lodgeName = lodgeName;
    }

    public LodgeType getLodgeType() {
        return lodgeType;
    }

    public void setLodgeType(LodgeType lodgeType) {
        this.lodgeType = lodgeType;
    }

    @Override
    public String toString() {
        String result;

        result = "Name : " + lodgeName
                + "\n|Type : " + lodgeType.name()
                + "\n\t" + address
                + "|Number of rooms : " + availableRooms.size() + "\n"
                + "\t|--- Services --- \n[" + String.join("->", offeredServices.toArray(new String[0])) + "]";

        return result;
    }
}
