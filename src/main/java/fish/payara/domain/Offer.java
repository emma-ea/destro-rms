package fish.payara.domain;

import jakarta.persistence.*;
import java.util.List;
import jakarta.json.bind.annotation.JsonbTransient;

@NamedQueries({
    @NamedQuery(name = "Offer.findByOfferId", query = "SELECT e FROM Offer e WHERE e.offerId = :offerId"),
    @NamedQuery(name = "Offer.findByStatus", query = "SELECT e FROM Offer e WHERE e.status = :status"),
    @NamedQuery(name = "Offer.findBySalary", query = "SELECT e FROM Offer e WHERE e.salary = :salary")
})
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer offerId;

    private String status;

    private Float salary;

    @JsonbTransient
    @OneToMany(mappedBy = "offer")
    private List<Candidate> candidates;

    // Getters and setters

    public Integer getOfferId() {
        return offerId;
    }

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

}
