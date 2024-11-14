package org.example.Service;

import org.example.Model.Organisation;
import org.example.Model.Participant;
import org.example.Persistence.OrganisationRepository;
import org.example.Persistence.ParticipantRepository;
import org.example.Utils.PasswordHasher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Service {

    private final OrganisationRepository organisationRepo;
    private final ParticipantRepository participantRepo;

    public Service(OrganisationRepository organisationRepo, ParticipantRepository participantRepo) {
        this.organisationRepo = organisationRepo;
        this.participantRepo = participantRepo;
    }

    public List<Organisation> findAll() {
        return organisationRepo.findAll();
    }

    public Optional<Organisation> findOne(Long id) {
        return organisationRepo.findOne(id);
    }

    public Organisation save(Organisation organisation) {

        organisation.setPassword(PasswordHasher.hashPassword(organisation.getPassword()));
        return organisationRepo.save(organisation);
    }

    public Optional<?> login(String username, String password) {
        Optional<Organisation> orgOpt = organisationRepo.findByUsername(username);
        if (orgOpt.isPresent()) {
            if (PasswordHasher.checkPassword(password, orgOpt.get().getPassword())) {
                return orgOpt;
            }
        } else {
            Optional<Participant> partOpt = participantRepo.findByUsername(username);
            if (partOpt.isPresent() && PasswordHasher.checkPassword(password, partOpt.get().getPassword())) {
                return partOpt;
            }
        }
        return Optional.empty();
    }

    public Organisation update(Organisation organisation) {
        return organisationRepo.update(organisation);
    }

    public Organisation delete(Long id) {
        return organisationRepo.delete(id);
    }

    public Optional<Organisation> loginOrganisation(String username, String password) {
        Optional<Organisation> orgOpt = organisationRepo.findByUsername(username);
        return orgOpt.filter(organisation -> PasswordHasher.checkPassword(password, organisation.getPassword()));
    }

    public Optional<Organisation> registerOrganisation(String username, String password, String about, String phone,
                                                       String name, String creationDateStr, String website) {

        if (organisationRepo.findByUsername(username).isPresent()) {
            return Optional.empty();
        }

        LocalDate creationDate;
        try {
            creationDate = LocalDate.parse(creationDateStr);
        } catch (Exception e) {
            return Optional.empty();
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        String role = "Organisation";

        Organisation newOrganisation = new Organisation();
        newOrganisation.setUsername(username);
        newOrganisation.setPassword(hashedPassword);
        newOrganisation.setAbout(about);
        newOrganisation.setPhone(phone);
        newOrganisation.setName(name);
        newOrganisation.setCreationDate(creationDate);
        newOrganisation.setWebsite(website);
        newOrganisation.setRole(role);

        Organisation savedOrganisation = organisationRepo.save(newOrganisation);
        return Optional.ofNullable(savedOrganisation);
    }

    public Optional<Participant> registerParticipant(String username, String password, String about, String phone,
                                                     String first_name, String last_name, String birthDateStr) {

        if (participantRepo.findByUsername(username).isPresent()) {
            return Optional.empty();
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        String role = "Participant";

        Participant newParticipant = new Participant();
        newParticipant.setUsername(username);
        newParticipant.setPassword(hashedPassword);
        newParticipant.setAbout(about);
        newParticipant.setPhone(phone);
        newParticipant.setFirstName(first_name);
        newParticipant.setLastName(last_name);
        newParticipant.setBirthDate(LocalDate.parse(birthDateStr));
        newParticipant.setRole(role);

        Participant savedParticipant = participantRepo.save(newParticipant);
        return Optional.ofNullable(savedParticipant);
    }

    public Optional<Participant> loginParticipant(String username, String password) {
        Optional<Participant> participantOpt = participantRepo.findByUsername(username);
        return participantOpt.filter(participant -> PasswordHasher.checkPassword(password, participant.getPassword()));
    }

    public List<Participant> findAllParticipants() {
        return participantRepo.findAll();
    }

    public Optional<Participant> updateParticipant(Participant participant) {
        return Optional.ofNullable(participantRepo.update(participant));
    }

    public Optional<Participant> deleteParticipant(Long id) {
        return Optional.ofNullable(participantRepo.delete(id));
    }

}
