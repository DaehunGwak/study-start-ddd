package io.ordi.daehunddd.domain;

public enum OrderState {
    PAYMENT_WAITING ,
    PREPARING,
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED,
    CANCELED;
}
