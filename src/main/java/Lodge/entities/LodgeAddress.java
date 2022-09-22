package Lodge.entities;

import java.security.SecureRandom;

/**
 * Représente l'adresse de l'hébergement
 */
public class LodgeAddress {

    private int id;
    private String province, city, fullAddress, country;

    public LodgeAddress() {
        id = new SecureRandom().nextInt();
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

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress() {
        return fullAddress + "," + city + "," + province + "," + country;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getCityCountry() {
        return city + "," + country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "--- Address --- \nRue : [" + fullAddress + "]\n" +
                "City : [" + city + "]\n" +
                "Province : [" + province + "]\n" +
                "Country : [" + country + "]\n";
    }
}
