package com.cloudmart.orderservice.service;

import com.cloudmart.orderservice.model.Order;
import com.cloudmart.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final FirestoreAuditService firestoreAuditService;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found with id: " + id));
    }

    public Order createOrder(Order order) {
        order.setStatus("PLACED");
        order.setCreatedAt(LocalDateTime.now());
        Order saved = orderRepository.save(order);
        firestoreAuditService.logOrderEvent(saved);
        return saved;
    }

    public Order updateStatus(String id, String status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}
