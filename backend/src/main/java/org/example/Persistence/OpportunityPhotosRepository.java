package org.example.Persistence;
import org.example.Model.OpportunityPhoto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OpportunityPhotosRepository {
    private final SessionFactory sessionFactory;

    public OpportunityPhotosRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // Get photos for a specific opportunity
    public List<OpportunityPhoto> getOpportunityPhotos(Integer opportunityId) {
        try (Session session = sessionFactory.openSession()) {
            Query<OpportunityPhoto> query = session.createQuery("FROM OpportunityPhoto WHERE opportunityId = :id", OpportunityPhoto.class);
            query.setParameter("id", opportunityId);
            return query.list();
        } catch (Exception e) {
            //logger.error("Error retrieving entity: {}", e.getMessage());
            return List.of(); // Return an empty list in case of failure
        }
    }

    // Add a new photo for an opportunity
    public void addOpportunityPhotos(List<OpportunityPhoto> opportunityPhotos) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            opportunityPhotos.forEach(session::save);

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            //logger.error("Error saving opportunity photo: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}