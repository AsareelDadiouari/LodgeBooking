package Client;

import Client.entities.ClientInfoGathering;
import Lodge.entities.LodgeService;
import Lodge.entities.LodgeType;
import Lodge.entities.RoomType;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

public class ClientMain {
    ClientInfoGathering clientInfo;

    public ClientMain() {
        clientInfo = new ClientInfoGathering();
    }

    /**
     * Méthode client qui recupère les besoins du client
     *
     * @return Informations sur la demande du client
     */
    public ClientInfoGathering gatherInfo() {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Renseignement Client ---");

        System.out.print("Nom de famille du client : ");
        clientInfo.getClient().setLastname(sc.nextLine());

        System.out.print("Prénom du client : ");
        clientInfo.getClient().setFirstname(sc.nextLine());

        System.out.print("Adresse du client : ");
        clientInfo.getClient().setAddress(sc.nextLine());

        System.out.print("Courriel du client : ");
        clientInfo.getClient().setEmail(sc.nextLine());

        System.out.print("Téléphone du client : ");
        clientInfo.getClient().setPhoneNumber(sc.nextLine());

        System.out.print("Type d'hébergement souhaité " +
                "\n1-Hotel " +
                "\n2-Motel" +
                "\n3-Café" +
                "\n4-Couette\n");
        System.out.println("Choix : ");
        switch (sc.next()) {
            case "1":
                clientInfo.setTypeOfLodge(LodgeType.HOTEL);
                break;
            case "2":
                clientInfo.setTypeOfLodge(LodgeType.MOTEL);
                break;
            case "3":
                clientInfo.setTypeOfLodge(LodgeType.CAFE);
                break;
            case "4":
                clientInfo.setTypeOfLodge(LodgeType.BEDDING);
                break;
        }

        System.out.println("Type de chambre souhaité " +
                "\n1-Simple" +
                "\n2-Double" +
                "\n3-Suite");
        System.out.println("Choix : ");
        switch (sc.next()) {
            case "1":
                clientInfo.setRoomType(RoomType.SINGLE);
                break;
            case "2":
                clientInfo.setRoomType(RoomType.DOUBLE);
                break;
            case "3":
                clientInfo.setRoomType(RoomType.SUITE);
                break;
        }

        sc.nextLine();

        System.out.print("Pays : ");
        clientInfo.getLodgeAddress().setCountry(sc.nextLine());

        System.out.print("Province : ");
        clientInfo.getLodgeAddress().setProvince(sc.nextLine());

        System.out.print("Ville : ");
        clientInfo.getLodgeAddress().setCity(sc.nextLine());

        System.out.print("Rue : ");
        clientInfo.getLodgeAddress().setFullAddress(sc.nextLine());

        boolean continuer;
        do {
            System.out.println("Ajouter un service souhaité :");
            try {
                String[] offeredServices = (String[]) LodgeService.class.getDeclaredField("services").get(new LodgeService());
                for (int i = 0; i < offeredServices.length; i++) {
                    System.out.println(i + "- " + offeredServices[i]);
                }
                System.out.println("Choix : ");
                clientInfo.getWantedServices().add(offeredServices[Integer.parseInt(sc.next())]);
                System.out.println("Service ajouté");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Ajouter un autre service ? [oui/non] : ");
            continuer = !Objects.equals(sc.next(), "non");

        } while (continuer);

        sc.nextLine();

        System.out.println("Prix maximum souhaité : ");
        String price = sc.next();
        clientInfo.setMaximumPriceToPay(Double.parseDouble(price));

        sc.nextLine();

        System.out.println("Date d'entrée souhaité : (AAAA-MM-JJ) ");
        clientInfo.setCheckIn(LocalDate.parse(sc.next()));

        System.out.println("Date de sortie souhaité : (AAAA-MM-JJ)");
        clientInfo.setCheckOut(LocalDate.parse(sc.next()));

        sc.nextLine();

        System.out.println("Besoin particuliers : ");
        clientInfo.setParticularNeed(sc.nextLine());

        return clientInfo;
    }

    public boolean saveClientInfoQuestion() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Sauvegarder vos données ? (oui/non) : ");
        return Objects.equals(sc.next(), "oui");
    }

    public boolean saveClientRequest() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Voulez vous envoyer votre demande ? (oui/non) : ");
        return Objects.equals(sc.next(), "oui");
    }
}
