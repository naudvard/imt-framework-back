package imt.framework.back.imtframeworkback.domain.usecases.dishes;

import imt.framework.back.imtframeworkback.core.utils.UseCase;
import imt.framework.back.imtframeworkback.data.services.DishService;
import imt.framework.back.imtframeworkback.domain.models.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchDishesUseCase implements UseCase<String, List<Dish>> {
    private final DishService dishService;
    @Override
    public List<Dish> command(String request) {
        return dishService.findInTitleOrDescription(request);
    }
}
