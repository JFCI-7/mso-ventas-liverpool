package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.shared.domain.model.Result;

public interface DeleteCustomerUseCase {
	
	Result<Void> deleteById(String id);
	

}
