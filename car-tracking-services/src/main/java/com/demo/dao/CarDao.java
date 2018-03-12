package com.demo.dao;

import java.util.List;

import com.demo.domain.Car;
import com.demo.exception.VehicleNumberShouldBeUnique;

public interface CarDao {

	public void addCar(Car car) throws VehicleNumberShouldBeUnique;  
	 public Car getCarByVehicleNumber(String vehicleNumber) throws Exception;  
	 public List<Car> getCarList() throws Exception;  
	 public void deleteCar(String vehicleNumber) throws Exception; 
	 public void updateCar(String vehicleNumber,Car car) throws Exception;
	 public void addMultipleCars(List<Car> car) throws Exception;
}
