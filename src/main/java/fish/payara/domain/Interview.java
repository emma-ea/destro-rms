package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import java.time.LocalDate;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Interview.findByInterviewId", query = "SELECT e FROM Interview e WHERE e.interviewId = :interviewId"),
    @NamedQuery(name = "Interview.findByDate", query = "SELECT e FROM Interview e WHERE e.date = :date"),
    @NamedQuery(name = "Interview.findByLocation", query = "SELECT e FROM Interview e WHERE e.location = :location")
})
@Entity
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interviewId;

    @Column(name = "\"date\"")
    private LocalDate date;

    private String location;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;
    @ManyToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;
    @JsonbTransient
    @OneToMany(mappedBy = "interview")
    private List<Candidate> candidates;

    // Getters and setters

    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

}
