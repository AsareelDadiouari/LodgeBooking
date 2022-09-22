package Client.entities;

import Lodge.entities.LodgeAddress;
import Lodge.entities.LodgeType;
import Lodge.entities.RoomType;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Représente les besoins du client
 */
public class ClientInfoGathering {
    private int id;
    private Client client;
    private LodgeType typeOfLodge;
    private RoomType roomType;
    private Set<String> wantedServices;
    private LocalDate checkIn, checkOut;
    private LodgeAddress lodgeAddress;
    private double maximumPriceToPay;
    private String particularNeed;
    private boolean fulfilled;

    public ClientInfoGathering() {
        client = new Client();
        lodgeAddress = new LodgeAddress();
        wantedServices = new HashSet<>();
        fulfilled = false;
        id = new SecureRandom().nextInt();
    }

    /**
     * @param client            Données du client
     * @param typeOfLodge       Type d'hébergements
     * @param roomType          Type de chambre
     * @param wantedServices    Services demandés par le client
     * @param checkIn           Date d'arrivée
     * @param checkOut          Date de départ
     * @param lodgeAddress      Adresse de l'hébergement
     * @param maximumPriceToPay Prix maximum quu le client paye
     * @param particularNeed    Besoins particuliers
     */
    public ClientInfoGathering(Client client, LodgeType typeOfLodge,
                               RoomType roomType, Set<String> wantedServices, LodgeAddress lodgeAddress,
                               double maximumPriceToPay, String particularNeed,
                               Optional<LocalDate> checkIn, Optional<LocalDate> checkOut) {
        checkIn.ifPresent(localDate -> this.checkIn = localDate);
        checkOut.ifPresent(localDate -> this.checkOut = localDate);
        this.wantedServices = wantedServices;
        this.roomType = roomType;
        this.client = client;
        this.typeOfLodge = typeOfLodge;
        this.maximumPriceToPay = maximumPriceToPay;
        this.lodgeAddress = lodgeAddress;
        this.particularNeed = particularNeed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LodgeAddress getLodgeAddress() {
        return lodgeAddress;
    }

    public void setLodgeAddress(LodgeAddress lodgeAddress) {
        this.lodgeAddress = lodgeAddress;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public double getMaximumPriceToPay() {
        return maximumPriceToPay;
    }

    public void setMaximumPriceToPay(double maximumPriceToPay) {
        this.maximumPriceToPay = maximumPriceToPay;
    }

    public LodgeType getTypeOfLodge() {
        return typeOfLodge;
    }

    public void setTypeOfLodge(LodgeType typeOfLodge) {
        this.typeOfLodge = typeOfLodge;
    }

    public String getParticularNeed() {
        return particularNeed;
    }

    public void setParticularNeed(String particularNeed) {
        this.particularNeed = particularNeed;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Set<String> getWantedServices() {
        return wantedServices;
    }

    public void setWantedServices(Set<String> wantedServices) {
        this.wantedServices = wantedServices;
    }

    @Override
    public String toString() {

        return "" + "[Nom Client : " + client.getFullName() + "]\n" +
                "[Type d'hébergement recherché : " + typeOfLodge.name() + "]\n" +
                "[Type de chambres recherché : " + roomType.name() + "]\n" +
                "[Prix Maximum de la chambre : " + maximumPriceToPay + "]\n" +
                "[Date d'entrée : " + ((checkIn == null) ? "" : checkIn.toString()) + "]\n" +
                "[Date de sortie : " + ((checkOut == null) ? "" : checkOut.toString()) + "]\n" +
                "[Address : " + lodgeAddress.getCityCountry() + "]\n";
    }
}