package io.ordi.daehunddd.domain;

import lombok.Getter;

public class OrderLine {

    private Product product;
    private Money price;
    private int quantity;
    @Getter private Money amounts;

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }
}
