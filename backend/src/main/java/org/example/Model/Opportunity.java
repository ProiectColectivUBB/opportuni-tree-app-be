package org.example.Model;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="Opportunity")
public class Opportunity implements Serializable {
    private Integer id;
    private String name;
    private TYPE type;
    private String city;
    private String country;
    private String description;
    private Float price;
    private Integer noParticipants;
    private Integer organisationId;
    private List<OpportunityPhoto> photos;

    public Opportunity(){}

    public Opportunity(String name, TYPE type, String city, String country, String description, Float price, Integer noParticipants, Integer organisationId, List<OpportunityPhoto> photos) {
        this.name = name;
        this.type = type;
        this.city = city;
        this.country = country;
        this.description = description;
        this.price = price;
        this.noParticipants = noParticipants;
        this.organisationId = organisationId;
        this.photos = photos;
    }

    @Id
    @GeneratedValue(strategy=IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @NotNull
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="type")
    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }
    @NotNull
    @Column(name="city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @NotNull
    @Column(name="country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    @NotNull
    @Column(name="description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column(name="price")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
    @Column(name="no_participants")
    public Integer getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(Integer noParticipants) {
        this.noParticipants = noParticipants;
    }
    @NotNull
    @Column(name="organisation_id")
    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "opportunity_id", referencedColumnName = "id")
    public List<OpportunityPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(List<OpportunityPhoto> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "Opportunity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", noParticipants=" + noParticipants +
                ", organisationId=" + organisationId +
                '}';
    }
}

