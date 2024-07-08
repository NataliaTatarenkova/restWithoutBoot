package com.tatarenkova.dao;

import com.tatarenkova.entity.Task;
import com.tatarenkova.service.TaskService;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TaskDAO {

    private final SessionFactory sessionFactory;

    public TaskDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void save(Task task){
        sessionFactory.getCurrentSession().save(task);
    }

    @Transactional
    public void deleteById(Long id) {
        sessionFactory.getCurrentSession().remove(Task.builder().id(id).build());
    }

    public Task findById(Long id) {
        return sessionFactory.getCurrentSession().get(Task.class, id);
    }
}
