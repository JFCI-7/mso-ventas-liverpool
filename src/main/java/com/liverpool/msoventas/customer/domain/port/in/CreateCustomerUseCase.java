package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domain.model.Result;

public interface CreateCustomerUseCase {
	
	Result<Customer> create(Customer customer);
}
