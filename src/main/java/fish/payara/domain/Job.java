package fish.payara.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@NamedQueries({
    @NamedQuery(name = "Job.findByJobId", query = "SELECT e FROM Job e WHERE e.jobId = :jobId"),
    @NamedQuery(name = "Job.findByTitle", query = "SELECT e FROM Job e WHERE e.title = :title"),
    @NamedQuery(name = "Job.findByDescription", query = "SELECT e FROM Job e WHERE e.description = :description"),
    @NamedQuery(name = "Job.findByStartDate", query = "SELECT e FROM Job e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "Job.findByEndDate", query = "SELECT e FROM Job e WHERE e.endDate = :endDate")
})
@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer jobId;

    private String title;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "application_id")
    private JobApplication application;

    // Getters and setters

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public JobApplication getApplication() {
        return application;
    }

    public void setApplication(JobApplication application) {
        this.application = application;
    }

}
