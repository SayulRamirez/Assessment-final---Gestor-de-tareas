package com.metaphorce.assessment_final.repositories;

import com.metaphorce.assessment_final.dto.TaskStatusCount;
import com.metaphorce.assessment_final.entities.Task;
import com.metaphorce.assessment_final.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findAllByResponsibleId(Long id);

    @Query("select t.status as status, count(t.status) as count from Task t where t.responsible.id =:id group by t.status")
    List<TaskStatusCount> countTaskByStatus(Long id);

    @Query("select distinct t.responsible from Task t where t.project.id =:id")
    List<User> findResponsibleByProjectId(Long id);

    List<Task> findAllByResponsibleIdAndProjectId(Long idUser, Long idProject);
}
