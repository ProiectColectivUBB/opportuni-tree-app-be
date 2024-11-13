package org.example.Persistence;

import org.example.Model.Organisation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class OrganisationRepository {

    private final SessionFactory sessionFactory;

    public OrganisationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Organisation> findByUsernameAndPassword(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            Query<Organisation> query = session.createQuery(
                    "FROM Organisation WHERE username = :username AND password = :password", Organisation.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Organisation> findByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Organisation> query = session.createQuery(
                    "FROM Organisation WHERE username = :username", Organisation.class);
            query.setParameter("username", username);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Organisation> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Organisation> query = session.createQuery("FROM Organisation", Organisation.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    public Organisation save(Organisation entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Organisation delete(long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Organisation organisationToDelete = session.get(Organisation.class, id);
            if (organisationToDelete != null) {
                session.delete(organisationToDelete);
                session.getTransaction().commit();
                return organisationToDelete;
            }
            session.getTransaction().rollback();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Organisation update(Organisation entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Organisation> findOne(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Organisation entity = session.get(Organisation.class, id);
            return Optional.ofNullable(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
