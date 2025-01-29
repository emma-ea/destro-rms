package fish.payara.service;

import jakarta.enterprise.context.Dependent;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import fish.payara.domain.JobApplication;


@Dependent

public class JobApplicationService extends AbstractService<JobApplication, Integer> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JobApplicationService() {
        super(JobApplication.class);
    }
    
}
