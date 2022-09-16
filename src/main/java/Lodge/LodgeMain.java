package Lodge;

import Lodge.entities.*;

import java.util.*;

public class LodgeMain {
    private final LodgeInfo lodgeInfo;
    private Lodge lodge;

    public LodgeMain(){
        lodgeInfo = new LodgeInfo();
    }

    public LodgeInfo gatherInfos(){
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Renseignement Hebergement ---");

        System.out.println("Type de l'hébergement : \n"
                + "1- Hotel\n"
                + "2- Motel\n"
                + "3- Cafe\n"
                + "4- Couette");
        System.out.print("Choix : ");

        switch (sc.next()){
            case "1":
                lodgeInfo.setLodgeType(LodgeType.HOTEL);
                lodge = new Hotel();
                break;
            case "2":
                lodgeInfo.setLodgeType(LodgeType.MOTEL);
                lodge = new Motel();
                break;
            case "3":
                lodgeInfo.setLodgeType(LodgeType.CAFE);
                lodge = new Cafe();
                break;
            case "4":
                lodgeInfo.setLodgeType(LodgeType.BEDDING);
                lodge = new Bedding();
                break;
        }

        sc.nextLine();

        System.out.print("Nom : ");
        lodgeInfo.setLodgeName(sc.nextLine());

        System.out.println("--- Adresse ---");

        System.out.print("Pays : ");
        lodgeInfo.getAddress().setCountry(sc.nextLine());

        System.out.print("Province : ");
        lodgeInfo.getAddress().setProvince(sc.nextLine());

        System.out.print("Ville : ");
        lodgeInfo.getAddress().setCity(sc.nextLine());

        System.out.print("Rue : ");
        lodgeInfo.getAddress().setFullAddress(sc.nextLine());

        boolean continuer;
        List<Room> rooms = new ArrayList<>();
        do{
            System.out.println("Ajouter un type de chambre souhaité : " +
                    "\n1-Simple" +
                    "\n2-Double" +
                    "\n3-Suite");
            System.out.print("Choix : ");
            String roomChoice = sc.next();

            System.out.print("Entrez le prix de la chambre : ");
            double price = Double.parseDouble(sc.next());

            switch (roomChoice) {
                case "1":
                    rooms.add(new Room(UUID.randomUUID().hashCode() & Integer.MAX_VALUE,
                            RoomType.SINGLE, price, Optional.empty(), Optional.empty()));
                    break;
                case "2":
                    rooms.add(new Room(UUID.randomUUID().hashCode() & Integer.MAX_VALUE,
                            RoomType.DOUBLE, price, Optional.empty(), Optional.empty()));
                    break;
                case "3":
                    rooms.add(new Room(UUID.randomUUID().hashCode() & Integer.MAX_VALUE,
                            RoomType.SUITE, price, Optional.empty(), Optional.empty()));
                    break;
            }
            System.out.print("Ajouter une autre chambre ? [oui/non] : ");
            continuer = !Objects.equals(sc.next(), "non");

        }while (continuer);
        lodgeInfo.setAvailableRooms(rooms);

        do {
            System.out.println("Ajouter un service souhaité :");
            try {
                String[] offeredServices = (String[]) LodgeService.class.getDeclaredField("services").get(new LodgeService());
                for (int i = 0; i < offeredServices.length; i++) {
                    System.out.println(i + "- " + offeredServices[i]);
                }
                System.out.print("Choix : ");
                lodgeInfo.getOfferedServices().add(offeredServices[Integer.parseInt(sc.next())]);
                System.out.println("Service ajouté");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Ajouter un autre service ? [oui/non] : ");
            continuer = !Objects.equals(sc.next(), "non");

        } while (continuer);

        lodge.setInfos(lodgeInfo);
        return lodgeInfo;
    }

    public boolean saveInfos(){
        Scanner sc = new Scanner(System.in);

        System.out.print("Voulez vous sauvegarder les données ? [oui/non]");
        return Objects.equals("oui", sc.next());
    }

    public Lodge getLodge() {
        return lodge;
    }
}
