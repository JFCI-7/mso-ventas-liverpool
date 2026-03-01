package com.liverpool.msoventas.customer.domain.port.in;

import com.liverpool.msoventas.shared.domian.model.Result;

public interface DeleteCustomerUseCase {
	
	Result<Void> deleteById(String id);
	

}
