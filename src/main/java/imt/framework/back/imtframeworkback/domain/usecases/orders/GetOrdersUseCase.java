package imt.framework.back.imtframeworkback.domain.usecases.orders;

import imt.framework.back.imtframeworkback.core.utils.UseCase;
import imt.framework.back.imtframeworkback.data.services.OrderService;
import imt.framework.back.imtframeworkback.domain.results.OrderRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetOrdersUseCase implements UseCase<Integer, List<OrderRes>> {
    private final OrderService orderService;

    @Override
    public List<OrderRes> command(Integer request) {
        return orderService.findByUser(request).stream().map(OrderRes::fromDomain).toList();
    }
}
