package com.liverpool.msoventas.customer.domain.port.in;

import java.util.List;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domian.model.Result;

public interface GetCustomerUseCase {
	
	Result<Customer> findById(String id);
	
	Result<List<Customer>> findAll();

}
