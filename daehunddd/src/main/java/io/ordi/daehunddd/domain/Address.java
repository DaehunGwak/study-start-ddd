package io.ordi.daehunddd.domain;

import lombok.Builder;

public record Address(
        String zipCode,
        String address1,
        String address2) {

    @Builder public Address {}
}
