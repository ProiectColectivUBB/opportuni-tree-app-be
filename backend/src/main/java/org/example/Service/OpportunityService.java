package org.example.Service;
import org.example.Model.Opportunity;
import org.example.Model.OpportunityPhoto;
import org.example.Model.OpportunityReqDTO;
import org.example.Model.OpportunityRespDTO;
import org.example.Persistence.OpportunityPhotosRepository;
import org.example.Persistence.OpportunityRepository;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class OpportunityService {
    private SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Opportunity.class).addAnnotatedClass(OpportunityPhoto.class)
            .buildSessionFactory();
    private final OpportunityRepository opportunityRepository=new OpportunityRepository(sessionFactory);
    private final OpportunityPhotosRepository opportunityPhotosRepository=new OpportunityPhotosRepository(sessionFactory);
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

    public String saveFile(MultipartFile file) throws IOException {
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
