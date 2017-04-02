package ru.magora.test.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ru.magora.test.model.Client;
import ru.magora.test.model.Order;
import ru.magora.test.util.DateQuery;
import ru.magora.test.util.Utils;

@Repository
public class OrderService {
	
	@PersistenceContext
	private EntityManager em;
	
	
	public OrderService(){
		
	}
	
	public List<Order> getOrders(DateQuery dateQuery) throws ParseException{
		
		Date begin = Utils.sdf.parse(dateQuery.getFrom());
		Date end = Utils.sdf.parse(dateQuery.getTo());

		List<Order> listOrder = em.createQuery("select o from Order as o WHERE o.dateTime BETWEEN :beginDate AND :endDate")
				.setParameter("beginDate", begin)
				.setParameter("endDate", end)
				.getResultList();
	
		return listOrder;
	}
	
	public void saveClient(Client c){
		em.persist(c);
	}
	
	public void saveOrder(Order o){
		em.merge(o);

	}

}
