package imt.framework.back.imtframeworkback.data.repositories.impl;

import imt.framework.back.imtframeworkback.data.models.DishModel;
import imt.framework.back.imtframeworkback.data.repositories.DishRepositoryExtension;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DishRepositoryExtensionImpl implements DishRepositoryExtension {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<DishModel> search(String terms, int limit, int offset) {
        return Search.
                session(entityManager).search(DishModel.class)
                .where(f -> f.match()
                        .fields("title", "description")
                        .matching(terms))
                .fetchHits(offset, limit);
    }
}
