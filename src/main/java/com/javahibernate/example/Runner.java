package com.javahibernate.example;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

public class Runner {

    @Test
    public void crud() {
        SessionFactory sessionFactory = getSessionFactory();
        Session session = sessionFactory.openSession();

        read(session);
        Car car = create(session);

        read(session);

        update(session, car);
        read(session);

        delete(session, car);
        read(session);

        session.close();
    }

    public SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder registryBuilder=
        new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(registryBuilder.build());
    }

    private Car create(Session session) {
        System.out.println("Creating car record...");
        Car car = new Car();
        car.setPrice("£47.000,01 " + UUID.randomUUID());

        session.beginTransaction();
        session.save(car);
        session.getTransaction().commit();

        read(session);
        return car;
    }

    private void read(Session session) {
        Query q = session.createQuery("select _car from Car _car");

        List<Car> cars = q.list();

        System.out.println("Reading car records...");
        System.out.printf("%-30.30s  %-30.30s%n", "Id", "Price");
        for (Car c : cars) {
            System.out.printf("%-30.30s  %-30.30s%n", c.getId(), c.getPrice());
        }
    }

    private void update(Session session, Car car) {
        System.out.println("Updating car price...");
        car.setPrice("£35,250.00"+ UUID.randomUUID());

        session.beginTransaction();
        session.saveOrUpdate(car);
        session.getTransaction().commit();
    }

    private void delete(Session session, Car car) {
        System.out.println("Deleting car record...");
        session.beginTransaction();
        session.delete(car);
        session.getTransaction().commit();
    }
}
