package org.example.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Participant")
public class Participant extends User implements Serializable {

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Column(name = "birthdate")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate birthDate;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "about", length = 255)
    private String about;

    public Participant() {
        super(null, null, null, null);
    }

    public Participant(Long id, String username, String pass, String firstName, String lastName, LocalDate birthDate, String phone, String about) {
        super(id, "participant", username, pass);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.about = about;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", phone='" + phone + '\'' +
                ", about='" + about + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                birthDate.equals(that.birthDate) &&
                phone.equals(that.phone) &&
                Objects.equals(about, that.about);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, phone, about);
    }

}
