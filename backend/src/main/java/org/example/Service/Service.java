package org.example.Service;

import org.example.Model.*;
import org.example.Persistence.OpportunityPhotosRepository;
import org.example.Persistence.OpportunityRepository;
import org.example.Persistence.OrganisationRepository;
import org.example.Persistence.ParticipantRepository;
import org.example.Utils.PasswordHasher;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@org.springframework.stereotype.Service
public class Service {

    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Opportunity.class)
            .addAnnotatedClass(OpportunityPhoto.class)
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(Participant.class)
            .addAnnotatedClass(Organisation.class)
            .buildSessionFactory();
    private final OrganisationRepository organisationRepo = new OrganisationRepository(sessionFactory);
    private final ParticipantRepository participantRepo = new ParticipantRepository(sessionFactory);

    private final OpportunityRepository opportunityRepository=new OpportunityRepository(sessionFactory);
    private final OpportunityPhotosRepository opportunityPhotosRepository=new OpportunityPhotosRepository(sessionFactory);

    public List<Organisation> findAllOrganisations() {
        return organisationRepo.findAll();
    }

    public Optional<Organisation> findOrganisation(Long id) {
        return organisationRepo.findOne(id);
    }

    public Organisation saveOrganisation(Organisation organisation) {

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

    public Organisation updateOrganisation(Organisation organisation) {
        return organisationRepo.update(organisation);
    }

    public Organisation deleteOrganisation(Long id) {
        return organisationRepo.delete(id);
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

    public List<Participant> findAllParticipants() {
        return participantRepo.findAll();
    }

    public Optional<Participant> updateParticipant(Participant participant) {
        return Optional.ofNullable(participantRepo.update(participant));
    }

    public Optional<Participant> deleteParticipant(Long id) {
        return Optional.ofNullable(participantRepo.delete(id));
    }

    public void addOpportunity(OpportunityReqDTO opportunityDTO){
        List<OpportunityPhoto> opportunityPhotos=new ArrayList<>();
        Opportunity opportunity = new Opportunity(opportunityDTO.getName(),opportunityDTO.getType(),opportunityDTO.getCity(),
                opportunityDTO.getCountry(),opportunityDTO.getDescription(),opportunityDTO.getPrice(),
                opportunityDTO.getNoParticipants(),opportunityDTO.getOrganisationId(),opportunityPhotos);
        Integer id = opportunityRepository.addOpportunity(opportunity);

        opportunityDTO.getPhotos().forEach(multipartFile -> {
            try {
                String photoURL=saveFile(multipartFile);
                OpportunityPhoto opportunityPhoto=new OpportunityPhoto(id,photoURL);
                opportunityPhotos.add(opportunityPhoto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        opportunityPhotosRepository.addOpportunityPhotos(opportunityPhotos);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        String folderPath = "../uploads";
        Path filePath = Paths.get(folderPath, fileName);
        Files.copy(file.getInputStream(), filePath);
        return filePath.toString();
    }

    public List<OpportunityRespDTO> getAllOpportunities() {
        List<OpportunityRespDTO> opportunityDTOS = new ArrayList<>();
        List<Opportunity> opportunities = opportunityRepository.getAllOpportunities();

        opportunities.forEach(opportunity -> {
            List<String> fileBase64s = new ArrayList<>();

            opportunity.getPhotos().forEach(opportunityPhoto -> {
                String photoBase64 = null;
                try {
                    // Convertește fișierul în Base64
                    photoBase64 = convertFileToBase64(opportunityPhoto.getPhotoUrl());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileBase64s.add(photoBase64);
            });

            OpportunityRespDTO opportunityDTO = new OpportunityRespDTO(
                    opportunity.getName(), opportunity.getType(), opportunity.getCity(),
                    opportunity.getCountry(), opportunity.getDescription(), opportunity.getPrice(),
                    opportunity.getNoParticipants(), opportunity.getOrganisationId(), fileBase64s
            );
            opportunityDTOS.add(opportunityDTO);
        });
        return opportunityDTOS;
    }

    private String convertFileToBase64(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        byte[] photoBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(photoBytes);
    }

}
