package io.ordi.daehunddd.domain;

import lombok.Builder;

public record Receiver(
        String name,
        String phone) {

    @Builder public Receiver {}
}
