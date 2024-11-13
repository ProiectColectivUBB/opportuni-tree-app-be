package org.example.Service;

import org.example.Model.Organisation;
import org.example.Persistence.OrganisationRepository;
import org.example.Utils.PasswordHasher;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class OrganisationService {

    private final OrganisationRepository repository;

    public OrganisationService(OrganisationRepository repository) {
        this.repository = repository;
    }

    public List<Organisation> findAll() {
        return repository.findAll();
    }

    public Optional<Organisation> findOne(Long id) {
        return repository.findOne(id);
    }

    public Organisation save(Organisation organisation) {

        organisation.setPassword(PasswordHasher.hashPassword(organisation.getPassword()));
        return repository.save(organisation);
    }

    public Organisation update(Organisation organisation) {
        return repository.update(organisation);
    }

    public Organisation delete(Long id) {
        return repository.delete(id);
    }

    public Optional<Organisation> loginOrganisation(String username, String password) {
        Optional<Organisation> orgOpt = repository.findByUsername(username);
        return orgOpt.filter(organisation -> PasswordHasher.checkPassword(password, organisation.getPassword()));
    }

    public Optional<Organisation> registerOrganisation(String username, String password, String about, String phone,
                                                       String name, String creationDateStr, String website) {

        if (repository.findByUsername(username).isPresent()) {
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

        Organisation savedOrganisation = repository.save(newOrganisation);
        return Optional.ofNullable(savedOrganisation);
    }

}
