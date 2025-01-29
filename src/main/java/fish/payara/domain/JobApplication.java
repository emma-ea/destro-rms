package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "JobApplication.findByApplicationId", query = "SELECT e FROM JobApplication e WHERE e.applicationId = :applicationId"),
    @NamedQuery(name = "JobApplication.findByStatus", query = "SELECT e FROM JobApplication e WHERE e.status = :status"),
    @NamedQuery(name = "JobApplication.findByResume", query = "SELECT e FROM JobApplication e WHERE e.resume = :resume")
})
@Entity
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer applicationId;

    private String status;

    private String resume;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @JsonbTransient
    @OneToMany(mappedBy = "application")
    private List<Job> jobs;
    @ManyToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    // Getters and setters

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

}
