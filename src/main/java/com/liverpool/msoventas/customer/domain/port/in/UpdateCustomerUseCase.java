package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.customer.domain.model.Customer;
import com.liverpool.msoventas.shared.domian.model.Result;

public interface UpdateCustomerUseCase {
	
	Result<Customer> update(String id, Customer customer);

}
