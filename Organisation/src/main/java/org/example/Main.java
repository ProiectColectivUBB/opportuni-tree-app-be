package org.example;

import org.example.Model.Organisation;
import org.example.Model.Participant;
import org.example.Persistence.OrganisationRepository;
import org.example.Persistence.ParticipantRepository;
import org.example.Service.Service;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Organisation.class)
                .buildSessionFactory();

        // Create a new OrganisationRepository instance
        OrganisationRepository organisationRepository = new OrganisationRepository(sessionFactory);
        ParticipantRepository participantRepository = new ParticipantRepository(sessionFactory);

        Service service = new Service(organisationRepository, participantRepository);

        try {

//            Optional<Organisation> registeredOrganisation = service.registerOrganisation("new_user", "password123",
//                    "About us", "0123456789", "Organisation Name", "2021-01-01", "www.example.com");
//            registeredOrganisation.ifPresent(org -> System.out.println("Registered Organisation: " + org));
//
//            Optional<Organisation> registeredOrganisation2 = service.registerOrganisation("new_user", "password123",
//                    "About us", "0123456789", "Organisation Name", "2021-01-01", "www.example.com");
//            registeredOrganisation2.ifPresent(org -> System.out.println("Registered Organisation: " + org));
//
//            List<Organisation> allOrganisations = organisationRepository.findAll();
//            System.out.println("All Organisations: ");
//            allOrganisations.forEach(org -> System.out.println(org));
//
//
//            Optional<Organisation> foundOrgByUsername = service.loginOrganisation("new_user", "password123");
//            foundOrgByUsername.ifPresent(org -> System.out.println("Found by username and passw: " + org));
//
////            if (foundOrgByUsername.isPresent()) {
////                Organisation orgToUpdate = foundOrgByUsername.get();
////                orgToUpdate.setPhone("0987654321");
////                organisationRepository.update(orgToUpdate);
////                System.out.println("Updated Organisation: " + orgToUpdate);
////            }
//
//            if (!allOrganisations.isEmpty()) {
//                Organisation deletedOrganisation = organisationRepository.delete(allOrganisations.get(0).getId());
//                System.out.println("Deleted Organisation: " + deletedOrganisation);
//            }

            Optional<Participant> participant = service.registerParticipant("user@on.ro", "password123",
                    "About us", "0123456789", "Participant", "Name", "2021-01-01");

            participant.ifPresent(p -> System.out.println("Registered Participant: " + p));

            Optional<Participant> participant2 = service.registerParticipant("user1@on.ro", "password123",
                    "About us", "0123456789", "Participant", "Name", "2021-01-01");

            participant2.ifPresent(p -> System.out.println("Registered Participant: " + p));

            List<Participant> allParticipants = participantRepository.findAll();
            System.out.println("All Participants: ");
            allParticipants.forEach(System.out::println);

            Optional<Participant> foundParticipantByUsername = service.loginParticipant("user@on.ro", "password123");
            foundParticipantByUsername.ifPresent(p -> System.out.println("Found by username and passw: " + p));

            if (foundParticipantByUsername.isPresent()) {
                Participant pToUpdate = foundParticipantByUsername.get();
                pToUpdate.setPhone("0987654321");
                participantRepository.update(pToUpdate);
                System.out.println("Updated Participant: " + pToUpdate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFactory.close();
        }
    }
}
