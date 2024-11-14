package org.example.Persistence;

import org.example.Model.Participant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParticipantRepository {

    private static final Logger logger = Logger.getLogger(ParticipantRepository.class.getName());
    private final SessionFactory sessionFactory;

    public ParticipantRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Participant> findUser(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Participant> query = session.createQuery(
                    "FROM Participant WHERE username = :username AND password = :password", Participant.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding user", e);
            return Optional.empty();
        }
    }

    public Optional<Participant> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Participant> query = session.createQuery(
                    "FROM Participant WHERE username = :username", Participant.class);
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding by username", e);
            return Optional.empty();
        }
    }

    public List<Participant> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Participant> query = session.createQuery("FROM Participant", Participant.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding all participants", e);
            return List.of();
        }
    }

    public Participant save(Participant participant) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(participant);
            session.getTransaction().commit();
            return participant;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error saving participant", e);
            return null;
        }
    }

    public Participant delete(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Participant participant = session.get(Participant.class, id);
            if (participant != null) {
                session.delete(participant);
                session.getTransaction().commit();
                return participant;
            }
            session.getTransaction().rollback();
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting participant", e);
            return null;
        }
    }

    public Participant update(Participant participant) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(participant);
            session.getTransaction().commit();
            return participant;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating participant", e);
            return null;
        }
    }
}
