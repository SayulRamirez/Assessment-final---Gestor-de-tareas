package com.metaphorce.assessment_final.entities;

import com.metaphorce.assessment_final.enums.Priority;
import com.metaphorce.assessment_final.enums.Status;
import com.metaphorce.assessment_final.utils.Builder;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_id")
    private User responsible;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "estimated_delivery", nullable = false)
    private LocalDate estimatedDelivery;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Priority priority;

    @Column(name = "create_date")
    private LocalDate createDate;

    private int runtime;

    public Task(){}

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

    public User getResponsible() {
        return responsible;
    }

    public void setResponsible(User responsible) {
        this.responsible = responsible;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public LocalDate getEstimatedDelivery() {
        return estimatedDelivery;
    }

    public void setEstimatedDelivery(LocalDate estimatedDelivery) {
        this.estimatedDelivery = estimatedDelivery;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public static TaskBuilder builder() {
        return new TaskBuilder();
    }

    private Task(Long id, String title, String description, User responsible, Status status, Project project, LocalDate estimatedDelivery, Priority priority, LocalDate createDate, int runtime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.responsible = responsible;
        this.status = status;
        this.project = project;
        this.estimatedDelivery = estimatedDelivery;
        this.priority = priority;
        this.createDate = createDate;
        this.runtime = runtime;
    }

    public static class TaskBuilder implements Builder<Task> {
        private Long id;
        private String title;
        private String description;
        private User responsible;
        private Status status;
        private Project project;
        private LocalDate estimatedDelivery;
        private Priority priority;
        private LocalDate createDate;
        private int runtime;

        public TaskBuilder() {}

        public TaskBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public TaskBuilder title(String title) {
            this.title = title;
            return this;
        }

        public TaskBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TaskBuilder responsible(User responsible) {
            this.responsible = responsible;
            return this;
        }

        public TaskBuilder status(Status status) {
            this.status = status;
            return this;
        }

        public TaskBuilder project(Project project) {
            this.project = project;
            return this;
        }

        public TaskBuilder estimatedDelivery(LocalDate estimatedDelivery) {
            this.estimatedDelivery = estimatedDelivery;
            return this;
        }

        public TaskBuilder priority(Priority priority) {
            this.priority = priority;
            return this;
        }

        public TaskBuilder createDate(LocalDate createDate) {
            this.createDate = createDate;
            return this;
        }

        public TaskBuilder runtime(int runtime) {
            this.runtime = runtime;
            return this;
        }

        @Override
        public Task build() {
            return new Task(id,title, description, responsible, status, project, estimatedDelivery, priority, createDate, runtime);
        }
    }
}
