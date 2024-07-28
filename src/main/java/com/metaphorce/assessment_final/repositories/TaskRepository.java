package com.metaphorce.assessment_final.repositories;

import com.metaphorce.assessment_final.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByResponsibleId(Long id);
}
