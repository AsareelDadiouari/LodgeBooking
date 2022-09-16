package Lodge.entities;

/**
 * Représente l'adresse de l'hébergement
 */
public class LodgeAddress {

    private int id;
    private String province, city, fullAddress, country;

    public LodgeAddress() {

    }

    public LodgeAddress(int id, String province, String city, String fullAddress, String country) {
        this.id = id;
        this.province = province;
        this.city = city;
        this.fullAddress = fullAddress;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "--- Address --- \nRue : [" + fullAddress + "]\n" +
                "City : [" + city + "]\n" +
                "Province : [" + province + "]\n" +
                "Country : [" + country + "]\n";
    }
}
