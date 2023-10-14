package imt.framework.back.imtframeworkback.data.services.impl;

import imt.framework.back.imtframeworkback.data.models.OrderModel;
import imt.framework.back.imtframeworkback.data.repositories.OrderRepository;
import imt.framework.back.imtframeworkback.data.services.OrderService;
import imt.framework.back.imtframeworkback.domain.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return Order.fromData(orderRepository.save(OrderModel.fromDomain(order)));
    }

    @Override
    public List<Order> findByUser(Integer userId) {
        return orderRepository.findAllByUser_UserId(userId).stream().map(Order::fromData).toList();
    }
}
