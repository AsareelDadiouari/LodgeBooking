package Lodge.entities;

import java.util.List;
import java.util.UUID;

/**
 * Représente la classe abstraite Hébergement qui sera
 * héritée dans ses sous classes
 */
public abstract class Lodge {

    protected int id = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
    /**
     * La liste des chambres disponibles de l'hébergement
     */
    protected List<Room> rooms;
    /**
     * Nom du logement
     */
    protected String name;
    /**
     * Classe qui correspond aux services disponibles
     * dans l'hébergement
     */
    protected LodgeService service;
    /**
     * Adresse du logement
     */
    protected LodgeAddress address;
    /**
     * Informations du logement
     */
    protected LodgeInfo infos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfLodge() {
        return this.getClass().getSimpleName();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LodgeService getService() {
        return service;
    }

    public void setService(LodgeService service) {
        this.service = service;
    }

    public LodgeAddress getAddress() {
        return address;
    }

    public void setAddress(LodgeAddress address) {
        this.address = address;
    }

    public LodgeInfo getInfos() {
        return infos;
    }

    public void setInfos(LodgeInfo infos) {
        this.infos = infos;
        this.rooms = this.infos.getAvailableRooms();
        this.service = new LodgeService();
        this.name = this.infos.getLodgeName();
        this.address = this.infos.getAddress();
    }

    @Override
    public String toString() {
        String result = "";

        result = "[Name = " + name + "]\n"
                + address + "\n"
                + service
                + "[Rooms : " + rooms.size() + "]"
                + "[Available : " + (int) rooms.stream().filter(x -> x.isAvailable).count() + "]"
                + "[Type : " + this.getClass().getSimpleName() + "]";

        return result;
    }
}
