package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Candidate.findByCandidateId", query = "SELECT e FROM Candidate e WHERE e.candidateId = :candidateId"),
    @NamedQuery(name = "Candidate.findByName", query = "SELECT e FROM Candidate e WHERE e.name = :name"),
    @NamedQuery(name = "Candidate.findByEmail", query = "SELECT e FROM Candidate e WHERE e.email = :email"),
    @NamedQuery(name = "Candidate.findByPhone", query = "SELECT e FROM Candidate e WHERE e.phone = :phone")
})
@Entity
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer candidateId;

    private String name;

    private String email;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @JsonbTransient
    @OneToMany(mappedBy = "candidate")
    private List<JobApplication> jobApplications;
    @ManyToOne
    @JoinColumn(name = "interview_id")
    private Interview interview;

    // Getters and setters

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public Interview getInterview() {
        return interview;
    }

    public void setInterview(Interview interview) {
        this.interview = interview;
    }

}
