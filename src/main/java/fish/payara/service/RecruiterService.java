package fish.payara.service;

import jakarta.enterprise.context.Dependent;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import fish.payara.domain.Recruiter;


@Dependent

public class RecruiterService extends AbstractService<Recruiter, Integer> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecruiterService() {
        super(Recruiter.class);
    }
    
}
