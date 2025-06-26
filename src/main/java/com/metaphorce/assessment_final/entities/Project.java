package com.metaphorce.assessment_final.entities;

import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.utils.Builder;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    private User leader;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(name = "estimated_completion", nullable = false)
    private LocalDate estimatedCompletion;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "project", orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Project(){}

    public static ProjectBuilder builder() {
        return new ProjectBuilder();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getEstimatedCompletion() {
        return estimatedCompletion;
    }

    public void setEstimatedCompletion(LocalDate estimatedCompletion) {
        this.estimatedCompletion = estimatedCompletion;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    private Project(Long id, String title, String description, User leader, Status status, LocalDate estimatedCompletion, List<Task> tasks) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.leader = leader;
        this.status = status;
        this.estimatedCompletion = estimatedCompletion;
        this.tasks = tasks;
    }

    public static class ProjectBuilder implements Builder<Project> {
        private Long id;
        private String title;
        private String description;
        private User leader;
        private Status status;
        private LocalDate estimatedCompletion;
        private List<Task> tasks = new ArrayList<>();

        public ProjectBuilder() {}

        public ProjectBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ProjectBuilder title(String title) {
            this.title = title;
            return this;
        }

        public ProjectBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProjectBuilder leader(User leader) {
            this.leader = leader;
            return this;
        }

        public ProjectBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public ProjectBuilder estimatedCompletion(LocalDate estimatedCompletion) {
            this.estimatedCompletion = estimatedCompletion;
            return this;
        }

        public ProjectBuilder tasks(List<Task> tasks) {
            this.tasks = tasks;
            return this;
        }

        @Override
        public Project build() {
            return new Project(id, title, description, leader, status, estimatedCompletion, tasks);
        }
    }
}
