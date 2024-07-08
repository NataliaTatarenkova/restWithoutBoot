package com.tatarenkova.dao;

import java.util.List;

import com.tatarenkova.entity.User;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<User> getUsers() {
        return sessionFactory.getCurrentSession().createQuery("select u from User u", User.class).getResultList();
    }

    @Transactional
    public User getUserById(Long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Transactional
    public void save(User user) {
        sessionFactory.getCurrentSession().persist(user);
    }

    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }


    @Transactional
    public void delete(Long id) {
        sessionFactory.getCurrentSession().remove(User.builder().id(id).build());
    }
}
