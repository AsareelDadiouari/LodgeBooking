package Database;

import Client.entities.Client;
import Client.entities.ClientInfoGathering;
import Lodge.entities.Lodge;
import Lodge.entities.LodgeAddress;
import Lodge.entities.LodgeInfo;
import Lodge.entities.Room;
import Manager.entities.BookingRecord;
import Manager.entities.TravelAgency;

import java.util.List;
import java.util.Set;

public interface IDatabaseManager {
    public List<Client> getClients();

    public List<LodgeAddress> getLodgeAddresses();

    public List<Room> getRooms();

    public List<TravelAgency> getTravelAgencies();

    public Client getClientByEmail(String email);

    public Room getRoomById(int id);

    public LodgeInfo getLodgeInfoById(int id);

    public BookingRecord getBookingRecordById(int id);

    public LodgeAddress getLodgeAddressById(int id);

    public void addClient(Client client);

    public void addRoom(Room room, int lodgeInfoId);

    public void addLodgeAddress(LodgeAddress lodgeAddress, int lodgeInfoId);

    public void addLodge(Lodge lodge);

    public void addClientInfoGathering(ClientInfoGathering clientInfoGathering);

    List<ClientInfoGathering> getClientInfoGatheringList();
    public void addWantedServices(Set<String> wantedService, String clientEmail);
    public Set<String> getListOfWantedServicesByClientId(String clientEmail);

    public void setClientDemandState(boolean state,  int clientInfo);
}
