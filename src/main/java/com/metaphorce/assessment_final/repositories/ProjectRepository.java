package com.metaphorce.assessment_final.repositories;

import com.metaphorce.assessment_final.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByLeaderId(Long id);
}
