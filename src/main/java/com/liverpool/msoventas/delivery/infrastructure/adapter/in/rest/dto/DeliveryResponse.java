package com.liverpool.msoventas.delivery.infrastructure.adapter.in.rest.dto;

import com.liverpool.msoventas.delivery.domain.model.Delivery;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeliveryResponse {

    private String id;
    private String customerId;
    private String alias;
    private String street;
    private String colony;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public static DeliveryResponse fromDomain(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .customerId(delivery.getCustomerId())
                .alias(delivery.getAlias())
                .street(delivery.getStreet())
                .colony(delivery.getColony())
                .city(delivery.getCity())
                .state(delivery.getState())
                .zipCode(delivery.getZipCode())
                .country(delivery.getCountry())
                .build();
    }
}
