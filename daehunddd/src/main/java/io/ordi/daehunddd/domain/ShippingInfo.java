package io.ordi.daehunddd.domain;

import lombok.Builder;

public record ShippingInfo (
        Address address,
        Receiver receiver,
        String message) {

    @Builder public ShippingInfo {}
}
