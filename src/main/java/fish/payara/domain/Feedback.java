package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Feedback.findByFeedbackId", query = "SELECT e FROM Feedback e WHERE e.feedbackId = :feedbackId"),
    @NamedQuery(name = "Feedback.findByComments", query = "SELECT e FROM Feedback e WHERE e.comments = :comments"),
    @NamedQuery(name = "Feedback.findByRating", query = "SELECT e FROM Feedback e WHERE e.rating = :rating")
})
@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer feedbackId;

    private String comments;

    private Integer rating;

    @JsonbTransient
    @OneToMany(mappedBy = "feedback")
    private List<Interview> interviews;

    // Getters and setters

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

}
