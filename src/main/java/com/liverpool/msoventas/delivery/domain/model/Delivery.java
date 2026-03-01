package com.liverpool.msoventas.delivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
	
	private String id;
    private String customerId;
    private String alias;
    private String street;
    private String colony;
    private String city;
    private String state;
    private String zipCode;
    private String country;

}
