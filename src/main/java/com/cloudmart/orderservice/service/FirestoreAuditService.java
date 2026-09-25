package com.cloudmart.orderservice.service;

import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.cloudmart.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Writes a lightweight audit/event record to Firestore whenever an order is
 * placed. MongoDB remains the source of truth for order data; Firestore is
 * used here specifically to demonstrate the Firestore resource required by
 * the module's cloud infrastructure checklist.
 *
 * The client is created lazily and every call is wrapped in try/catch so a
 * missing GCP credential during local development never blocks placing an
 * order - it just skips the audit write and logs a warning.
 */
@Service
public class FirestoreAuditService {

    private volatile Firestore firestore;

    private Firestore getFirestore() {
        if (firestore == null) {
            synchronized (this) {
                if (firestore == null) {
                    firestore = FirestoreOptions.getDefaultInstance().getService();
                }
            }
        }
        return firestore;
    }

    public void logOrderEvent(Order order) {
        try {
            Map<String, Object> event = new HashMap<>();
            event.put("orderId", order.getId());
            event.put("productId", order.getProductId());
            event.put("productName", order.getProductName());
            event.put("customerName", order.getCustomerName());
            event.put("quantity", order.getQuantity());
            event.put("totalPrice", order.getTotalPrice() != null ? order.getTotalPrice().doubleValue() : null);
            event.put("eventType", "ORDER_CREATED");
            event.put("timestamp", System.currentTimeMillis());
            getFirestore().collection("order-events").document().set(event);
        } catch (Exception e) {
            System.err.println("Firestore audit log skipped (this is fine locally without GCP credentials): "
                    + e.getMessage());
        }
    }
}
