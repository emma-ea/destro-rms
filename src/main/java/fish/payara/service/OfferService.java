package fish.payara.service;

import jakarta.enterprise.context.Dependent;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import fish.payara.domain.Offer;


@Dependent

public class OfferService extends AbstractService<Offer, Integer> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OfferService() {
        super(Offer.class);
    }
    
}
