package Lodge.entities;

import java.util.*;

/**
 * Représente les services offerts par un
 * hébergement
 */
public class LodgeService {
    /**
     * Liste de services totale
     */
    public static final String[] services = {"piscine intérieure",
            "cuisinette",
            "salle de conditionnement physique",
            "stationnement",
            "accès handicapé",
            "dépanneur",
            "restaurant"};

    /**
     * Liste de services disponibles pour un
     * hébergement
     */
    private String[] availableServices;

    public LodgeService(){
        availableServices = new String[]{""};
    }

    public LodgeService(Set<String> offeredServices){
        this.availableServices = offeredServices.toArray(String[]::new);
    }

    /**
     * Initialise la liste des services disponibles
     * en fonction d'un nombre défini
     *
     * @param number nombre de service disponible
     */
    public void setServicesByNumber(int number) {
        List<String> serviceList = Arrays.asList(services);
        Collections.shuffle(serviceList);

        this.availableServices = Arrays.copyOf(serviceList.toArray(services), number);
    }

    /**
     * Retourne une liste de services aléatoires
     *
     * @return une liste de services aléatoires
     */
    public String[] getAvailableServicesRandom() {
        int randomNum = 1 + new Random().nextInt(services.length);
        return Arrays.copyOf(services, randomNum);
    }

    public String[] getAvailableServices() {
        return availableServices;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[Services : ");

        for (String service : availableServices) {
            result.append("|").append(service);
        }

        return result + "]";
    }
}
