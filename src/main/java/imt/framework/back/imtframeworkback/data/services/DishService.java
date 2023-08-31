package imt.framework.back.imtframeworkback.data.services;

import imt.framework.back.imtframeworkback.data.models.DishModel;
import imt.framework.back.imtframeworkback.domain.models.Dish;
import java.util.List;
import java.util.Optional;
import java.util.List;
import java.util.List;

public interface DishService {
    Dish save(Dish dish);

    List<Dish> findAll();

    Optional<Dish> findById(Integer id);
}
