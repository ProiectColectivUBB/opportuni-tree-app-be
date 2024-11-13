package org.example.Persistence;
import org.example.Model.Opportunity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OpportunityRepository {
    private final SessionFactory sessionFactory;

    public OpportunityRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Get opportunity by ID
    public Optional<Opportunity> getOpportunityById(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Opportunity opportunity = session.get(Opportunity.class, id);
            return Optional.ofNullable(opportunity); // Return the opportunity if found, otherwise empty
        } catch (Exception e) {
            //logger.error("Error retrieving opportunity by ID: {}", e.getMessage());
            return Optional.empty(); // Return empty in case of failure
        }
    }

    // Add a new opportunity
    public Integer addOpportunity(Opportunity opportunity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Salvează obiectul Opportunity și obține ID-ul generat
            Integer opportunityId = (Integer) session.save(opportunity);

            // Comite tranzacția
            session.getTransaction().commit();

            // Returnează ID-ul generat de baza de date
            return opportunityId;
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Returnează null în caz de eroare
        }
    }

    // Get all opportunities (optional method for fetching multiple opportunities)
    public List<Opportunity> getAllOpportunities() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Opportunity> query = session.createQuery("FROM Opportunity", Opportunity.class);
            List<Opportunity> opportunities = query.list();
            session.getTransaction().commit();
            return opportunities;
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}
