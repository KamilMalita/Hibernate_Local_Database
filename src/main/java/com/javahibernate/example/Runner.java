package com.javahibernate.example;

import java.util.List;
import java.util.UUID;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.junit.Test;

public class Runner {

    @Test
    public void crud() {
        SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();

        create(session);
        read(session);

        update(session);
        read(session);

//        delete(session);
//        read(session);

        session.close();
    }

    private void delete(Session session) {
        System.out.println("Deleting mondeo record...");
        Car mondeo = (Car) session.get(Car.class, 22L);

        session.beginTransaction();
        session.delete(mondeo);
        session.getTransaction().commit();
    }

    private void update(Session session) {
        System.out.println("Updating mustang price...");
        Car mustang = (Car) session.get(Car.class, 23L);
        mustang.setPrice("£35,250.00"+ UUID.randomUUID());

        session.beginTransaction();
        session.saveOrUpdate(mustang);
        session.getTransaction().commit();
    }

    private void create(Session session) {
        System.out.println("Creating car records...");
        Car mustang = new Car();
        mustang.setPrice("£20,000.00 " + UUID.randomUUID());

        Car mondeo = new Car();
        mondeo.setPrice("£26,000.00 " + UUID.randomUUID());

        session.beginTransaction();
        session.save(mustang);
        session.save(mondeo);
        session.getTransaction().commit();
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
}
