package com.demo.daoImpl;

import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.dao.CarDao;
import com.demo.domain.Car;
import com.demo.exception.VehicleNumberShouldBeUnique;

@Repository("carDao")
public class CarDaoImpl implements CarDao{

	@Autowired  
	SessionFactory sessionFactory;  

	/*Session session = null;  
	Transaction tx = null;  */
	@Override
	public void addCar(Car car) throws VehicleNumberShouldBeUnique{
		sessionFactory.openSession().saveOrUpdate(car);
	}
	@Override
	public Car getCarByVehicleNumber(String vehicleNumber) throws Exception {
		//return (Car) sessionFactory.openSession().get(Car.class, vehicleNumber);
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Car.class);
		criteria.add(Restrictions.eq("vehicleNumber", vehicleNumber));
		Car car = (Car) criteria.uniqueResult();        
		if (car!=null) {

			System.out.println("Car found:");

			System.out.println(car.getVehicleNumber() + " - " + car.getName());
		}

		return car;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Car> getCarList() throws Exception {

		return sessionFactory.openSession().createCriteria(Car.class).list();
	}

	@Override
	public void deleteCar(String vehicleNumber) throws Exception {
		Session session = sessionFactory.openSession();
		 Query query = session.createQuery("delete from Car where vehicleNumber = :vehicleNumber");
		 query.setParameter("vehicleNumber", vehicleNumber);
		 int result = query.executeUpdate();
		 System.out.println("Deleted rows "+result);
	
	}

	@Override
	public void updateCar(String vehicleNumber,Car car) throws Exception {
		Session session = sessionFactory.openSession();
		//Car car2 = (Car) session.byId(Car.class).load(vehicleNumber);
		/*Query qry = session.createQuery("update Car p set p.name=?,p.vehicleNumber=?,p.phoneNumber=? where p.vehicleNumber="+vehicleNumber);
		System.out.println("name of the car "+car.getName());
		qry.setParameter(0,car.getName());
		qry.setParameter(1,car.getVehicleNumber());
		qry.setParameter(2,car.getPhoneNumber());
		int res = qry.executeUpdate();
		System.out.println("No. of rows updated "+res);*/

		Query query = session.createQuery("update Car set name = :name,phoneNumber = :phoneNumber" +
				" where vehicleNumber = :vehicleNumber");
		query.setParameter("name", car.getName());
		query.setParameter("phoneNumber", car.getPhoneNumber());
		query.setParameter("vehicleNumber", vehicleNumber);
		int result = query.executeUpdate();
		System.out.println("Updated rows "+result);
		session.flush();
	}

	@Override
	public void addMultipleCars(List<Car> car) throws Exception {
		Session session = sessionFactory.openSession();
		for(Car cars : car){
			session.saveOrUpdate(cars);
		}
	}
}
