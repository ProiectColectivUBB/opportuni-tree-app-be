package org.example.Model;

import org.example.Model.User;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Organisation")
public class Organisation extends User implements Serializable {

    @Column(name = "about", nullable = false, length = 255)
    private String about;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "name", nullable = false, length = 255)
    private String name;



    @Column(name = "creation_date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate creationDate;

    @Column(name = "website", length = 255)
    private String website;

    public Organisation() {
        super(null, null, null, null);
    }

    public Organisation(Long id, String role, String username, String password, String about, String phone, String name, LocalDate creationDate, String website) {
        super(id, role, username, password);
        this.about = about;
        this.phone = phone;
        this.name = name;
        this.creationDate = creationDate;
        this.website = website;
    }

    // Getters and Setters for other attributes...

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }




    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + getId() +
                ", role='" + getRole() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", about='" + about + '\'' +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", website='" + website + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return getUsername().equals(that.getUsername()) &&
                getPassword().equals(that.getPassword()) &&
                about.equals(that.about) &&
                phone.equals(that.phone) &&
                name.equals(that.name) &&
                creationDate.equals(that.creationDate) &&
                Objects.equals(website, that.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), about, phone, name, creationDate, website);
    }
}