package za.co.project.calculator.ejb;


import za.co.project.calculator.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.SecurityContext;

@Stateless
public class UserEJB {

    @PersistenceContext(unitName = "default")
    private EntityManager entityManager;

    @Resource
    private SecurityContext securityContext;

    public User authenticateUser(String username, String password) {
        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public boolean isUserInRole(String role) {
        User user = (User) securityContext.getCallerPrincipal();
        return securityContext.isCallerInRole(role);
    }
}
