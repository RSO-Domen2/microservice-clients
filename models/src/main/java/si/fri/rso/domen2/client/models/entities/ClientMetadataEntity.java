package si.fri.rso.domen2.client.models.entities;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "client_metadata")
@NamedQueries(value =
        {
                @NamedQuery(name = "ClientMetadataEntity.getAll",
                        query = "SELECT im FROM ClientMetadataEntity im")
        })
public class ClientMetadataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    @Column(name = "created")
    private Instant created;

    @Column(name = "street_number")
    private String street_number;

    @Column(name = "street_name")
    private String street_name;

    @Column(name = "postal_code")
    private Integer postal_code;

    @Column(name = "postal_name")
    private String postal_name;

    @Column(name = "country")
    private String country;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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




    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

}