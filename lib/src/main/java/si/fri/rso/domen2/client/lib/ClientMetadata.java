package si.fri.rso.domen2.client.lib;

import java.time.Instant;

public class ClientMetadata {

    private Integer id;
    private String name;
    private String surname;
    private Double lat;
    private Double lng;
    private Instant created;
    private String street_number;
    private String street_name;
    private Integer postal_code;
    private String postal_name;
    private String country;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getStreetNumber() { return street_number; }
    public void setStreetNumber(String street_number) {this.street_number = street_number;}

    public String getStreetName() { return street_name; }
    public void setStreetName(String street_name) {this.street_name = street_name;}

    public Integer getPostalCode() { return postal_code; }
    public void setPostalCode(Integer postal_code) {this.postal_code = postal_code;}

    public String getPostalName() { return postal_name; }
    public void setPostalName(String postal_name) {this.postal_name = postal_name;}

    public String getCountry() {return country;}
    public void setCountry(String country) {this.country = country;}

    public Instant getCreated() { return created; }
    public void setCreated(Instant created) { this.created = created; }

    public boolean isValid() {
        return (this.name != null && this.surname != null && this.lat != null & this.lng != null);
    }



}
