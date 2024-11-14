package org.example.RestServer;

import org.example.Model.OpportunityReqDTO;
import org.example.Model.OpportunityRespDTO;
import org.example.Model.Organisation;
import org.example.Model.Participant;
import org.example.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/opptree")
public class ServerController
{
    @Autowired
    private Service service;

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        System.out.println("login called");
        try {
            String username = loginData.get("username");
            String password = loginData.get("password");

            Optional<?> login_result = service.login(username, password);
            if (login_result.isPresent()) {
                Object result = login_result.get();

                if (result instanceof Participant) {
                    Participant participant = (Participant) result;
                    return new ResponseEntity<>(participant, HttpStatus.OK);
                } else if (result instanceof Organisation) {
                    Organisation organisation = (Organisation) result;
                    return new ResponseEntity<>(organisation, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/register/organisation")
    public ResponseEntity<?> register_organisation(@RequestBody Map<String, String> organisationData) {
        System.out.println("register organisation called");
        try {
            String username = organisationData.get("username");
            String password = organisationData.get("password");
            String about = organisationData.get("about");
            String phone = organisationData.get("phone");
            String name = organisationData.get("name");
            String creationDateStr = organisationData.get("creationDateStr");
            String website = organisationData.get("website");

            Optional<Organisation> saved_org = service.registerOrganisation(username, password, about, phone, name, creationDateStr, website);
            if (saved_org.isPresent()) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/participant")
    public ResponseEntity<?> register_participant(@RequestBody Map<String, String> participantData) {
        System.out.println("register participant called");
        try {
            String username = participantData.get("username");
            String password = participantData.get("password");
            String about = participantData.get("about");
            String phone = participantData.get("phone");
            String first_name = participantData.get("first_name");
            String last_name = participantData.get("last_name");
            String birthDateStr = participantData.get("birthDateStr");

            Optional<Participant> saved_part = service.registerParticipant(username, password, about, phone, first_name, last_name, birthDateStr);
            if (saved_part.isPresent()) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/opportunities")
    public ResponseEntity<List<OpportunityRespDTO>> getAllOpportunities()
    {
        List<OpportunityRespDTO> opportunities = service.getAllOpportunities();
        return ResponseEntity.ok(opportunities);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/opportunities")
    public ResponseEntity<String> addOpportunity(
            @RequestParam("name") String name,
            @RequestParam("type") String type,
            @RequestParam("city") String city,
            @RequestParam("country") String country,
            @RequestParam("description") String description,
            @RequestParam("price") Float price,
            @RequestParam("noParticipants") Integer noParticipants,
            @RequestParam("organisationId") Integer organisationId,
            @RequestParam("photos") List<MultipartFile> photos)
    {
        OpportunityReqDTO opportunityDTO = new OpportunityReqDTO(name, type, city, country, description, price, noParticipants, organisationId, photos);
        service.addOpportunity(opportunityDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
