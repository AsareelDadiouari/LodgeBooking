package Database;

import Client.entities.Client;
import Client.entities.ClientInfoGathering;
import Lodge.entities.*;
import Manager.entities.TravelAgency;
import Reservation.entities.BookingRecord;
import Reservation.entities.BookingState;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe de gestion de la base de données du système
 */
public class DatabaseManager implements IDatabaseManager {
    private final Connection connection;

    public DatabaseManager() {
        try {
            String dbURL = "jdbc:sqlite:" + System.getProperty("user.dir")
                    + "/src/main/java/Database/database.db";
            connection = DriverManager.getConnection(dbURL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> getClients() {
        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Client");
            while (resultSet.next()) {
                Client client = new Client();
                client.setAddress(resultSet.getString("adress"));
                client.setEmail(resultSet.getString("email"));
                client.setPhoneNumber(resultSet.getString("phoneNumber"));
                client.setFirstname(resultSet.getString("firstname"));
                client.setLastname(resultSet.getString("lastname"));

                clients.add(client);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }

    public List<LodgeAddress> getLodgeAddresses() {
        List<LodgeAddress> lodgeAddresses = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM LodgeAdress");
            while (resultSet.next()) {
                LodgeAddress lodgeAddress = new LodgeAddress();
                lodgeAddress.setFullAddress(resultSet.getString("province"));
                lodgeAddress.setCity(resultSet.getString("city"));
                lodgeAddress.setCountry(resultSet.getString("country"));
                lodgeAddress.setFullAddress(resultSet.getString("fullAdress"));
                lodgeAddress.setId(resultSet.getInt("id"));

                lodgeAddresses.add(lodgeAddress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lodgeAddresses;
    }

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Room");
            while (resultSet.next()) {
                Room room = new Room();
                room.setRoomType(RoomType.valueOf(resultSet.getString("roomType")));
                room.setPrice(resultSet.getDouble("price"));
                room.setCheckIn(LocalDate.parse(resultSet.getString("checkIn")));
                room.setCheckIn(LocalDate.parse(resultSet.getString("checkOut")));
                room.setId(resultSet.getInt("id"));

                rooms.add(room);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rooms;
    }

    @Override
    public List<BookingRecord> getBookings() {
        List<BookingRecord> bookings = new ArrayList<>();
        String sql = "SELECT id FROM BookingRecord";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next())
                bookings.add(getBookingRecordById(resultSet.getInt("id")));

        }catch (Exception e) {
            e.printStackTrace();
        }

        return bookings;
    }

    public List<ClientInfoGathering> getClientInfoGatheringList(){
        List<ClientInfoGathering> clientInfoGatherings = new ArrayList<>();
        String sql = "SELECT * FROM ClientInfoGathering";

        try{
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                ClientInfoGathering clientInfoGathering = getClientInfoGatheringById(resultSet.getInt("id"));
                clientInfoGatherings.add(clientInfoGathering);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return clientInfoGatherings;
    }

    public List<TravelAgency> getTravelAgencies() {
        List<TravelAgency> travelAgencies = new ArrayList<>();
        List<BookingRecord> bookingRecords = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            Statement statement2 = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM TravelAgency");
            ResultSet resultSet2 = statement2.executeQuery("SELECT BookingRecord.id FROM BookingRecord" +
                    " LEFT JOIN TravelAgency ON BookingRecord.travelAgencyId = TravelAgency.id = TravelAgency.id");

            while (resultSet.next()) {
                TravelAgency travelAgency = new TravelAgency();
                travelAgency.setManagerName(resultSet.getString("name"));
                travelAgency.setId(resultSet.getInt("id"));

                while (resultSet2.next())
                    bookingRecords.add(getBookingRecordById(resultSet2.getInt("id")));

                travelAgency.setBookingRecords(bookingRecords);
                travelAgencies.add(travelAgency);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return travelAgencies;
    }

    public Client getClientByEmail(String email) {
        Client client = new Client();
        String sql = "SELECT * FROM Client WHERE email = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                client.setAddress(resultSet.getString("adress"));
                client.setEmail(resultSet.getString("email"));
                client.setPhoneNumber(resultSet.getString("phoneNumber"));
                client.setFirstname(resultSet.getString("firstname"));
                client.setLastname(resultSet.getString("lastname"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }

    public Room getRoomById(int id) {
        Room room = new Room();
        String sql = "SELECT * FROM Room WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                room.setId(resultSet.getInt("id"));
                room.setAvailable(resultSet.getBoolean("available"));
                if (!resultSet.getString("checkOut").isEmpty())
                    room.setCheckOut(LocalDate.parse(resultSet.getString("checkOut")));

                if(!resultSet.getString("checkIn").isEmpty())
                    room.setCheckIn(LocalDate.parse(resultSet.getString("checkIn")));

                room.setPrice(resultSet.getDouble("price"));
                room.setRoomType(RoomType.valueOf(resultSet.getString("roomType")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return room;
    }

    public LodgeInfo getLodgeInfoById(int id) {
        LodgeInfo lodgeInfo = new LodgeInfo();
        List<Room> rooms = new ArrayList<>();

        String sql = "SELECT * FROM LodgeInfo WHERE id = ?";
        String sql2 = "SELECT Room.id FROM Room LEFT JOIN LodgeInfo ON Room.lodgeInfoId = ?";
        String sql3 = "SELECT LodgeAdress.id FROM LodgeAdress LEFT JOIN LodgeInfo ON LodgeAdress.lodgeInfoId = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            PreparedStatement statement3 = connection.prepareStatement(sql3);
            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement3.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSet2 = statement2.executeQuery();
            ResultSet resultSet3 = statement3.executeQuery();

            while (resultSet.next()) {
                lodgeInfo.setLodgeName(resultSet.getString("lodgeName"));
                lodgeInfo.setNumberOfRooms(resultSet.getInt("numberOfRooms"));

                while (resultSet2.next()) {
                    rooms.add(getRoomById(resultSet2.getInt("id")));
                }

                if (resultSet3.next())
                    lodgeInfo.setAddress(getLodgeAddressById(resultSet3.getInt("id")));

                lodgeInfo.setAvailableRooms(rooms);
                lodgeInfo.setId(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lodgeInfo;
    }

    public Reservation.entities.BookingRecord getBookingRecordById(int id) {
        BookingRecord bookingRecord = new BookingRecord();

        List<Client> clients = new ArrayList<>();
        List<LodgeInfo> lodgesInfo = new ArrayList<>();

        String sql = "SELECT * FROM BookingRecord WHERE id = ?";
        String sql2 = "SELECT ClientInfoGathering.roomType FROM ClientInfoGathering LEFT JOIN Room " +
                "ON ClientInfoGathering.clientId = ?";
        //String sql2 = "SELECT email FROM Client LEFT JOIN BookingRecord ON Client.email = BookingRecord.clientId";
        //String sql3 = "SELECT LodgeInfo.id FROM LodgeInfo LEFT JOIN BookingRecord ON LodgeInfo.id = BookingRecord.accomodationId";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()){
                clients.add(getClientByEmail(resultSet.getString("clientId")));
                lodgesInfo.add(getLodgeInfoById(resultSet.getInt("accomodationId")));
                bookingRecord.setBookingState(BookingState.valueOf(resultSet.getString("bookingState")));
            }

            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setString(1, clients.get(0).getEmail());

            ResultSet resultSet2 = statement2.executeQuery();

            if (resultSet2.next()){
                /*!!! Find a way to get room type and
                    also resolved the accommodation NullPointerException
                    !!!
                */
                bookingRecord.setTypeOfRoom(resultSet2.getString("roomType"));
            }

            bookingRecord.setClient(clients.get(0));
            bookingRecord.setAccommodations(lodgesInfo);
            bookingRecord.setId(id);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingRecord;
    }

    public LodgeAddress getLodgeAddressById(int id) {
        LodgeAddress lodgeAddress = new LodgeAddress();
        String sql = "SELECT * FROM LodgeAdress WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                lodgeAddress.setId(resultSet.getInt("id"));
                lodgeAddress.setProvince(resultSet.getString("province"));
                lodgeAddress.setFullAddress(resultSet.getString("fullAdress"));
                lodgeAddress.setCountry(resultSet.getString("country"));
                lodgeAddress.setCity(resultSet.getString("city"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lodgeAddress;
    }



    public ClientInfoGathering getClientInfoGatheringById(int id){
        ClientInfoGathering clientInfoGathering = new ClientInfoGathering();

        String sql = "SELECT * FROM ClientInfoGathering WHERE id = ?";
        //String sql2 = "SELECT email FROM Client LEFT JOIN ClientInfoGathering CIG ON Client.email = CIG.clientId AND CIG.id = ?";
        //String sql3 = "SELECT LodgeAdress.id FROM LodgeAdress LEFT JOIN ClientInfoGathering CIG ON LodgeAdress.id = CIG.lodgeAdressId AND CIG.id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                clientInfoGathering.setClient(getClientByEmail(resultSet.getString("clientId")));
                clientInfoGathering.setLodgeAddress(getLodgeAddressById(resultSet.getInt("lodgeAdressId")));
                clientInfoGathering.setId(resultSet.getInt("id"));
                clientInfoGathering.setTypeOfLodge(LodgeType.valueOf(resultSet.getString("typeOfLodge")));
                clientInfoGathering.setRoomType(RoomType.valueOf(resultSet.getString("roomType")));
                if (!resultSet.getString("checkIn").isEmpty())
                    clientInfoGathering.setCheckIn(LocalDate.parse(resultSet.getString("checkIn")));
                if (!resultSet.getString("checkOut").isEmpty())
                    clientInfoGathering.setCheckOut(LocalDate.parse(resultSet.getString("checkOut")));
                clientInfoGathering.setMaximumPriceToPay(resultSet.getDouble("maximumPrice"));
                clientInfoGathering.setParticularNeed(resultSet.getString("particularNeed"));
                clientInfoGathering.setFulfilled(resultSet.getBoolean("isFulfilled"));
                clientInfoGathering.setWantedServices(getListOfWantedServicesByClientId(resultSet.getString("clientId")));
            }

            /*PreparedStatement statement = connection.prepareStatement(sql);
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            PreparedStatement statement3 = connection.prepareStatement(sql3);

            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement3.setInt(1, id);


            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSet2 = statement.executeQuery();
            ResultSet resultSet3 = statement.executeQuery();*/


        }catch (Exception e){
            e.printStackTrace();
        }

        return clientInfoGathering;
    }

    public Set<String> getListOfWantedServicesByClientId(String clientEmail){
        Set<String> wantedServices = new HashSet<>();
        String sql = "SELECT service FROM WantedServices WHERE clientId = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clientEmail);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next())
                wantedServices.add(resultSet.getString("service"));

        }catch (Exception e){
            e.printStackTrace();
        }

        return wantedServices;
    }

    @Override
    public void setClientDemandState(boolean state, int clientInfo) {
        String sql = "UPDATE ClientInfoGathering " +
                "SET isFulfilled = ? WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, state);
            statement.setInt(2, clientInfo);

            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean setBookingState(int bookingId, BookingState bookingState){
        String sql = "UPDATE BookingRecord " +
                "SET bookingState = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, bookingState.name());
            statement.setInt(2, bookingId);

            statement.executeUpdate();
            System.out.println("Réservation confirmée");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public LodgeInfo findLodgeFromClientDemands(ClientInfoGathering clientInfoGathering) {
        String sql = "SELECT LodgeInfo.id FROM LodgeInfo LEFT JOIN LodgeAdress ON LodgeInfo.id = LodgeAdress.lodgeInfoId " +
                "LEFT JOIN ClientInfoGathering CIG on LodgeAdress.id = CIG.lodgeAdressId " +
                "LEFT JOIN Room ON LodgeInfo.id = Room.lodgeInfoId " +
                "WHERE LodgeAdress.city = ? AND LodgeAdress.country = ? AND Room.roomType = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clientInfoGathering.getLodgeAddress().getCity());
            statement.setString(2, clientInfoGathering.getLodgeAddress().getCountry());
            statement.setString(3, clientInfoGathering.getRoomType().name());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return getLodgeInfoById(resultSet.getInt("id"));

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int addBooking(ClientInfoGathering client, LodgeInfo lodgeInfo, int travelAgencyId) {
        String sql = "INSERT INTO BookingRecord(clientId, id, accomodationId, travelAgencyId, bookingState) VALUES (?,?,?,?,?)";
        int id = 0;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            id = new SecureRandom().nextInt();

            statement.setString(1, client.getClient().getEmail());
            statement.setInt(2, id);
            statement.setInt(3, lodgeInfo.getId());
            statement.setInt(4, travelAgencyId);
            statement.setString(5, "PENDING");

            statement.executeUpdate();
            System.out.println("Reservation ajouté");

        }catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void addClientInfoGathering(ClientInfoGathering clientInfoGathering){
        String sql = "INSERT INTO ClientInfoGathering(clientId, lodgeAdressId, id, typeOfLodge, roomType, checkIn, checkout, maximumPrice, particularNeed, isFulfilled) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clientInfoGathering.getClient().getEmail());
            statement.setInt(2, clientInfoGathering.getLodgeAddress().getId());
            statement.setInt(3, clientInfoGathering.getId());
            statement.setString(4, clientInfoGathering.getTypeOfLodge().toString());
            statement.setString(5, clientInfoGathering.getRoomType().toString());
            statement.setString(6, clientInfoGathering.getCheckIn() == null ? "" : clientInfoGathering.getCheckIn().toString());
            statement.setString(7, clientInfoGathering.getCheckOut() == null ? "" : clientInfoGathering.getCheckOut().toString());
            statement.setDouble(8, clientInfoGathering.getMaximumPriceToPay());
            statement.setString(9, clientInfoGathering.getParticularNeed());
            statement.setBoolean(10, clientInfoGathering.isFulfilled());

            addWantedServices(clientInfoGathering.getWantedServices(), clientInfoGathering.getClient().getEmail());

            statement.executeUpdate();
            System.out.println("Demande client ajouté");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addWantedServices(Set<String> wantedService, String clientEmail){
        String sql = "INSERT INTO WantedServices(id, clientId) VALUES (?,?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, new SecureRandom().nextInt());
            statement.setString(2, clientEmail);

            wantedService.forEach(service -> {
                addSingleWantedService(service, clientEmail);
            });

            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSingleWantedService(String service, String clientEmail){
        String sql = "INSERT INTO WantedServices(id, clientId, service) VALUES (?,?,?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, new SecureRandom().nextInt());
            statement.setString(2, clientEmail);
            statement.setString(3, service);

            statement.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addClient(Client client) {
        String sql = "INSERT INTO Client(firstname, lastname, adress, email, phoneNumber) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, client.getFirstname());
            statement.setString(2, client.getLastname());
            statement.setString(3, client.getAddress());
            statement.setString(4, client.getEmail());
            statement.setString(5, client.getPhoneNumber());

            statement.executeUpdate();
            System.out.println("Données sauvegardé");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRoom(Room room, int lodgeInfoId) {
        String sql = "INSERT INTO Room(roomType, price, checkIn, checkOut, id, available, lodgeInfoId) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, room.getRoomType().toString());
            statement.setDouble(2, room.getPrice());
            statement.setString(3, room.getCheckIn() == null ? "" : room.getCheckIn().toString());
            statement.setString(4, room.getCheckOut() == null ? "" : room.getCheckOut().toString());
            statement.setInt(5, new SecureRandom().nextInt(Integer.MAX_VALUE));
            statement.setBoolean(6, room.isAvailable());
            statement.setInt(7, lodgeInfoId);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLodgeAddress(LodgeAddress lodgeAddress, int lodgeInfoId) {
        String sql = "INSERT INTO LodgeAdress(id, province, city, fullAdress, country, lodgeInfoId) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lodgeAddress.getId());
            statement.setString(2, lodgeAddress.getProvince());
            statement.setString(3, lodgeAddress.getCity());
            statement.setString(4, lodgeAddress.getFullAddress());
            statement.setString(5, lodgeAddress.getCountry());
            statement.setInt(6, lodgeInfoId);

            statement.executeUpdate();
            System.out.println("Données sauvegardé");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addLodge(Lodge lodge){
        String sql = "INSERT INTO LodgeInfo(id, numberOfRooms, lodgeName) " +
                "VALUES (?, ?, ?)";

        lodge.getRooms().forEach(room -> {
            addRoom(room, lodge.getId());
        });
        addLodgeAddress(lodge.getAddress(), lodge.getId());

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, lodge.getId());
            statement.setInt(2, lodge.getRooms().size());
            statement.setString(3, lodge.getName());

            statement.executeUpdate();
            System.out.println("Données sauvegardé");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}