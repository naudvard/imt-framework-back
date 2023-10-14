package imt.framework.back.imtframeworkback.data.repositories.impl;

import imt.framework.back.imtframeworkback.data.repositories.SearchRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;

public class SearchRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements SearchRepository<T, ID> {

    private final EntityManager entityManager;

    public SearchRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public SearchRepositoryImpl(Class<T> domainClass, EntityManager em, EntityManager entityManager) {
        super(domainClass, em);
        this.entityManager = entityManager;
    }
}
