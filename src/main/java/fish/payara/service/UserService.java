package fish.payara.service;

import jakarta.enterprise.context.Dependent;

import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import fish.payara.domain.User;


@Dependent

public class UserService extends AbstractService<User, String> {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserService() {
        super(User.class);
    }
    
}
