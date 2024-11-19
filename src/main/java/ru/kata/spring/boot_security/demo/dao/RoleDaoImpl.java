package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public RoleDaoImpl() {

    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r", Role.class)
                .getResultList());
    }

    @Override
    public Role findRoleByName(String roleName) {
        String query = "SELECT r FROM Role r WHERE r.authority = :roleName";
        return entityManager.createQuery(query, Role.class)
                .setParameter("roleName", roleName)
                .getSingleResult();
    }
}
