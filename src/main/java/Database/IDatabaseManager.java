package Database;

import Client.entities.Client;
import Client.entities.ClientInfoGathering;
import Lodge.entities.Lodge;
import Lodge.entities.LodgeAddress;
import Lodge.entities.LodgeInfo;
import Lodge.entities.Room;
import Manager.entities.TravelAgency;
import Reservation.entities.BookingRecord;
import Reservation.entities.BookingState;

import java.util.List;
import java.util.Set;

/**
 * Représente l'interface des méthodes implémentés pour le système de base de données
 */
public interface IDatabaseManager {
    List<Client> getClients();

    List<LodgeAddress> getLodgeAddresses();

    List<Room> getRooms();

    List<TravelAgency> getTravelAgencies();

    Client getClientByEmail(String email);

    Room getRoomById(int id);

    LodgeInfo getLodgeInfoById(int id);

    BookingRecord getBookingRecordById(int id);

    LodgeAddress getLodgeAddressById(int id);

    void addClient(Client client);

    void addRoom(Room room, int lodgeInfoId);

    void addLodgeAddress(LodgeAddress lodgeAddress, int lodgeInfoId);

    void addLodge(Lodge lodge);

    void addClientInfoGathering(ClientInfoGathering clientInfoGathering);

    List<ClientInfoGathering> getClientInfoGatheringList();

    void addWantedServices(Set<String> wantedService, String clientEmail);

    Set<String> getListOfWantedServicesByClientId(String clientEmail);

    void setClientDemandState(boolean state, int clientInfo);

    LodgeInfo findLodgeFromClientDemands(ClientInfoGathering clientInfoGathering);

    int addBooking(ClientInfoGathering client, LodgeInfo lodgeInfo, int travelAgencyId);

    boolean setBookingState(int bookingId, BookingState bookingState);

    List<BookingRecord> getBookings();
    void addTravelAgency(TravelAgency travelAgency);
    TravelAgency getTravelAgencyByManagerName(String managerName);
    List<BookingRecord> getBookingRecordByTravelAgencyId(int travelAgencyId);
}
