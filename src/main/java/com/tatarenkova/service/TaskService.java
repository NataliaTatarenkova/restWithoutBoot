package com.tatarenkova.service;

import java.util.Optional;

import com.tatarenkova.dao.TaskDAO;
import com.tatarenkova.entity.Task;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskService {

    @Autowired
    private final TaskDAO taskDAO;

    public void save(Task task) {
        taskDAO.save(task);
    }

    public void deleteById(Long id) {
        taskDAO.deleteById(id);
    }

    public Object findById(Long id) {
        return taskDAO.findById(id);
    }
}
