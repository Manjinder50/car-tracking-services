package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT,reason="This Vehicle is already registered")
public class VehicleNumberShouldBeUnique extends Exception {
 
    private static final long serialVersionUID = -2859292084648724403L;
    private String vehicleNumber;
	
    public VehicleNumberShouldBeUnique() {
    super();
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}
    
    
}
