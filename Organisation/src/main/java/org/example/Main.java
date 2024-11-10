package org.example;

import org.example.Model.Organisation;
import org.example.Persistence.OrganisationRepository;
import org.example.Service.OrganisationService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Organisation.class)
                .buildSessionFactory();

        // Create a new OrganisationRepository instance
        OrganisationRepository organisationRepository = new OrganisationRepository(sessionFactory);

        OrganisationService organisationService = new OrganisationService(organisationRepository);

        try {

            Optional<Organisation> registeredOrganisation = organisationService.registerOrganisation("new_user", "password123",
                    "About us", "0123456789", "Organisation Name", "2021-01-01", "www.example.com");
            registeredOrganisation.ifPresent(org -> System.out.println("Registered Organisation: " + org));

            Optional<Organisation> registeredOrganisation2 = organisationService.registerOrganisation("new_user", "password123",
                    "About us", "0123456789", "Organisation Name", "2021-01-01", "www.example.com");
            registeredOrganisation2.ifPresent(org -> System.out.println("Registered Organisation: " + org));

            List<Organisation> allOrganisations = organisationRepository.findAll();
            System.out.println("All Organisations: ");
            allOrganisations.forEach(org -> System.out.println(org));


            Optional<Organisation> foundOrgByUsername = organisationService.loginOrganisation("new_user", "password123");
            foundOrgByUsername.ifPresent(org -> System.out.println("Found by username and passw: " + org));

//            if (foundOrgByUsername.isPresent()) {
//                Organisation orgToUpdate = foundOrgByUsername.get();
//                orgToUpdate.setPhone("0987654321");
//                organisationRepository.update(orgToUpdate);
//                System.out.println("Updated Organisation: " + orgToUpdate);
//            }

            if (!allOrganisations.isEmpty()) {
                Organisation deletedOrganisation = organisationRepository.delete(allOrganisations.get(0).getId());
                System.out.println("Deleted Organisation: " + deletedOrganisation);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
