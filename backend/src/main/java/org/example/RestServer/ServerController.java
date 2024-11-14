package org.example.RestServer;

import org.example.Model.OpportunityReqDTO;
import org.example.Model.OpportunityRespDTO;
import org.example.Service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/opptree")
public class ServerController
{
    @Autowired
    private Service service;

    @RequestMapping(method = RequestMethod.GET, value = "/login")
    public ResponseEntity<?> login(
            @RequestParam("username") String username,
            @RequestParam("password") String password)
    {
        System.out.println("login called");
        try {
            Optional<?> login_result = service.login(username, password);
            if (login_result.isPresent())
            {
                return new ResponseEntity<>(login_result, HttpStatus.OK);
            }
            else
            {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/register")
    public ResponseEntity<?> register()
    {
        System.out.println("register called");
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
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
