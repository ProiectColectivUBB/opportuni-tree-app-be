package org.example.Model;

import com.sun.istack.NotNull;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="OpportunityPhotos")
public class OpportunityPhoto implements Serializable {
    @Id
    @GeneratedValue(strategy=IDENTITY)
    private Integer id;
    @NotNull
    @Column(name = "opportunity_id", nullable = false)
    private Integer opportunityId;
    @NotNull
    @Column(name = "photo_url", nullable = false)
    private String photoUrl;

    public OpportunityPhoto(){}

    public OpportunityPhoto(Integer opportunityId, String photoUrl) {
        this.opportunityId = opportunityId;
        this.photoUrl = photoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpportunityId() {
        return opportunityId;
    }

    public void setOpportunityId(Integer opportunityId) {
        this.opportunityId = opportunityId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "OpportunityPhoto{" +
                "id=" + id +
                ", opportunityId=" + opportunityId +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
