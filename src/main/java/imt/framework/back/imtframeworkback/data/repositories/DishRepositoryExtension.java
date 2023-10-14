package imt.framework.back.imtframeworkback.data.repositories;

import imt.framework.back.imtframeworkback.data.models.DishModel;

import java.util.List;

public interface DishRepositoryExtension {
    List<DishModel> search(String terms, int limit, int offset);
}
