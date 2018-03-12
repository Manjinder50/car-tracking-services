package com.demo.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.demo.domain.Car;
import com.demo.domain.ClientErrorInformation;
import com.demo.exception.VehicleNumberShouldBeUnique;
import com.demo.service.CarService;


@RestController
@EnableWebMvc
public class HomeController {

	@Autowired
	private CarService carService;
 
	//Post
	//Adding a new Car
	@RequestMapping(path="/addCar",headers = {"Accept=text/xml, application/json"},method=RequestMethod.POST,produces="applocation/json")
	public ResponseEntity<Car> createEmployee(@RequestBody Car car) throws VehicleNumberShouldBeUnique,Exception{
		HttpHeaders headers = new HttpHeaders();
		if(car==null){
			return new ResponseEntity<Car>(HttpStatus.BAD_REQUEST);
		}
		try {
			carService.addCar(car);
		}  catch (VehicleNumberShouldBeUnique exc) {
			System.out.println("Vehicle Number already exists from Controller");
			throw new VehicleNumberShouldBeUnique();
		}
		catch(Exception exc){
			exc.printStackTrace();
		}
		headers.add("Car Added", String.valueOf(car.getId()));
		return new ResponseEntity<Car>(car,headers,HttpStatus.CREATED);
	}

	//Get a list of Car present in the db
	@RequestMapping(value = "/carList",headers = {"Accept=text/xml, application/json"}, method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Car>> car() {
		HttpHeaders headers = new HttpHeaders();
		List<Car> cars = null;
		try {
			cars = carService.getCarList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (cars == null) {
			return new ResponseEntity<List<Car>>(HttpStatus.NOT_FOUND);
		}
		headers.add("Number Of Records Found", String.valueOf(cars.size()));
		return new ResponseEntity<List<Car>>(cars, headers, HttpStatus.OK);
	}

	//Update a particular Car
	@RequestMapping(value = "/car/{vehicleNumber}",headers = {"Accept=text/xml, application/json"}, method = RequestMethod.PUT)
	public ResponseEntity<Car> updateCar(@PathVariable("vehicleNumber") String vehicleNumber, @RequestBody Car car) {
		HttpHeaders headers = new HttpHeaders();
		Car isExist = null;
		try {
			isExist = carService.getCarByVehicleNumber(vehicleNumber);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (isExist == null) {   
			return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
		} else if (car == null) {
			return new ResponseEntity<Car>(HttpStatus.BAD_REQUEST);
		}
		try {
			carService.updateCar(vehicleNumber, car);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		headers.add("Car Updated  - ", String.valueOf(vehicleNumber));
		return new ResponseEntity<Car>(car, headers, HttpStatus.OK);
	}

	//Fetch a car based on its vehicleNumber
	@RequestMapping(value = "/car/{vehicleNumber}", method = RequestMethod.GET)
	public ResponseEntity<Car> getCarUsingId(@PathVariable("vehicleNumber") String vehicleNumber) {
		Car car = null;
		try {
			car = carService.getCarByVehicleNumber(vehicleNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (car == null) {
			return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Car>(car, HttpStatus.OK);
	}

	//Delete a car based on its Id
	@RequestMapping(value = "/car/delete/{vehicleNumber}", method = RequestMethod.DELETE)
	public ResponseEntity<Car> deleteCar(@PathVariable("vehicleNumber") String vehicleNumber) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		Car car = null;
		try {
			car = carService.getCarByVehicleNumber(vehicleNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (car == null) {   
			return new ResponseEntity<Car>(HttpStatus.NOT_FOUND);
		}
		carService.deleteCar(vehicleNumber);
		headers.add("Car Deleted - ", String.valueOf(vehicleNumber));
		return new ResponseEntity<Car>(car, headers, HttpStatus.NO_CONTENT);
	}
	//Adding multiple cars together
	@RequestMapping(value = "/cars",headers = {"Accept=text/xml, application/json"}, method = RequestMethod.POST)
	public ResponseEntity<List<Car>> updateWithMultipleCars(@RequestBody List<Car> cars) {
		System.out.println("Inserting data into database");
//		HttpHeaders headers = new HttpHeaders();
		if(cars==null){
			return new ResponseEntity<List<Car>>(HttpStatus.BAD_REQUEST);
		}
		try {
			carService.addMultipleCars(cars);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return new ResponseEntity<List<Car>>(cars, HttpStatus.OK);
	}
	
	@ExceptionHandler(VehicleNumberShouldBeUnique.class)
	public ResponseEntity<ClientErrorInformation> rulesForCustomerNotFound(HttpServletRequest req, Exception e) 
	{
	ClientErrorInformation error = new ClientErrorInformation(e.toString(), req.getRequestURI());
	return new ResponseEntity<ClientErrorInformation>(error, HttpStatus.CONFLICT);
	}

}
