package fish.payara.service;

import jakarta.enterprise.context.Dependent;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import fish.payara.domain.Feedback;


@Dependent

public class FeedbackService extends AbstractService<Feedback, Integer> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackService() {
        super(Feedback.class);
    }
    
}
