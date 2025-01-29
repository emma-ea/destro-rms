package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Recruiter.findByRecruiterId", query = "SELECT e FROM Recruiter e WHERE e.recruiterId = :recruiterId"),
    @NamedQuery(name = "Recruiter.findByName", query = "SELECT e FROM Recruiter e WHERE e.name = :name"),
    @NamedQuery(name = "Recruiter.findByDepartment", query = "SELECT e FROM Recruiter e WHERE e.department = :department")
})
@Entity
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer recruiterId;

    private String name;

    private String department;

    @JsonbTransient
    @OneToMany(mappedBy = "recruiter")
    private List<Interview> interviews;

    // Getters and setters

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Interview> getInterviews() {
        return interviews;
    }

    public void setInterviews(List<Interview> interviews) {
        this.interviews = interviews;
    }

}
