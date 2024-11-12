package org.example.Model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class OpportunityReqDTO {
    private String name;
    private TYPE type;
    private String city;
    private String country;
    private String description;
    private Float price;
    private Integer noParticipants;
    private Integer organisationId;
    private List<MultipartFile> photos;

    public OpportunityReqDTO(String name, String type, String city, String country, String description, Float price, Integer noParticipants, Integer organisationId, List<MultipartFile> photos) {
        this.name = name;
        this.type = TYPE.valueOf(type);
        this.city = city;
        this.country = country;
        this.description = description;
        this.price = price;
        this.noParticipants = noParticipants;
        this.organisationId = organisationId;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(Integer noParticipants) {
        this.noParticipants = noParticipants;
    }

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public List<MultipartFile> getPhotos() {
        return photos;
    }

    public void setPhotos(List<MultipartFile> photos) {
        this.photos = photos;
    }
}
