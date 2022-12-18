package io.ordi.daehunddd.domain;

import java.util.List;
import java.util.Objects;

public class Order {

    private String orderNumber;
    private OrderState state;
    private Orderer orderer;
    private List<OrderLine> orderLines;
    private ShippingInfo shippingInfo;
    private Money totalAmounts;

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        setOrderer(orderer);
        setOrderLines(orderLines);
        setShippingInfo(shippingInfo);
    }

    private void setOrderer(Orderer orderer) {
        if (Objects.isNull(orderer)) {
            throw new IllegalArgumentException("no orderer");
        }
        this.orderer = orderer;
    }

    private void setOrderLines(List<OrderLine> orderLines) {
        verifyAtLeastOneOrMoreOrderLines(orderLines);
        this.orderLines = orderLines;
        calculateTotalAmounts();
    }

    private void verifyAtLeastOneOrMoreOrderLines(List<OrderLine> orderLines) {
        if (Objects.isNull(orderLines) || orderLines.isEmpty()) {
            throw new IllegalArgumentException("no OrderLine");
        }
    }

    private void setShippingInfo(ShippingInfo shippingInfo) {
        if (Objects.isNull(shippingInfo)) {
            throw new IllegalArgumentException("no ShippingInfo");
        }
        this.shippingInfo = shippingInfo;
    }

    private void calculateTotalAmounts() {
        int sum = orderLines.stream()
                .map(OrderLine::getAmounts)
                .mapToInt(Money::value)
                .sum();
        this.totalAmounts = new Money(sum);
    }

    /**
     * 출고 상태로 변경하기
     */
    public void changeShipped() {
        if (this.state != OrderState.PREPARING) {
            throw new IllegalStateException("현재 주문 상태에서는 출고 상태로 변경할 수 없습니다. 현재 주문 상태:" + state);
        }
        this.state = OrderState.SHIPPED;
    }

    /**
     * 배송지 정보 변경하기
     * @param newShippingInfo
     */
    public void changeShippingInfo(ShippingInfo newShippingInfo) {
        verifyNotYestShipped();
        this.shippingInfo = newShippingInfo;
    }

    private boolean isShippingChangeable() {
        return state == OrderState.PAYMENT_WAITING ||
                state == OrderState.PREPARING;
    }

    /**
     * 주문 취소
     */
    public void cancel() {
        verifyNotYestShipped();
        this.state = OrderState.CANCELED;
    }

    private void verifyNotYestShipped() {
        if (state != OrderState.PAYMENT_WAITING && state != OrderState.PREPARING) {
            throw new IllegalStateException("already shipped");
        }
    }

    /**
     * 결제 완료하기
     */
    public void completePayment() {
        if (state != OrderState.PAYMENT_WAITING) {
            throw new IllegalStateException("결제 대기 중인 주문이 아닙니다. 현재 주문 상태:" + state);
        }
        this.state = OrderState.PREPARING;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        if (Objects.isNull(this.orderNumber) || Objects.isNull(order.orderNumber)) return false;
        return orderNumber.equals(order.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber);
    }
}
