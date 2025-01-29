package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "User.findByUserId", query = "SELECT e FROM User e WHERE e.userId = :userId"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT e FROM User e WHERE e.username = :username"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT e FROM User e WHERE e.email = :email")
})
@Table(name = "\"User\"")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    private String username;

    private String email;

    @JsonbTransient
    @OneToMany(mappedBy = "user")
    private List<JobApplication> jobApplications;

    // Getters and setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(List<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

}
