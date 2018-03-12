package com.demo.serviceImpl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.demo.dao.CarDao;
import com.demo.domain.Car;
import com.demo.exception.VehicleNumberShouldBeUnique;
import com.demo.service.CarService;

@Service("carService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CarServiceImpl implements CarService {

	@Autowired
	private CarDao carDao;
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addCar(Car car) throws VehicleNumberShouldBeUnique{
		try {
			carDao.addCar(car);
		} catch (VehicleNumberShouldBeUnique exc) {
			
		System.out.println("Vehicle Number already exists");
		throw new VehicleNumberShouldBeUnique();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
	}

	@Override
	public List<Car> getCarList() throws Exception {
		return carDao.getCarList();
	}

	@Override
	public void updateCar(String vehicleNumber, Car car) throws Exception {
		carDao.updateCar(vehicleNumber, car);
		
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void deleteCar(String vehicleNumber) throws Exception {
		carDao.deleteCar(vehicleNumber);
	}


	@Override
	public void addMultipleCars(List<Car> car) throws Exception{
		carDao.addMultipleCars(car);
		
	}


	@Override
	public Car getCarByVehicleNumber(String vehicleNumber) throws Exception {
		return carDao.getCarByVehicleNumber(vehicleNumber);
	}

}
