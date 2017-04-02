package ru.magora.test.controller;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ru.magora.test.model.Client;
import ru.magora.test.model.Order;
import ru.magora.test.model.Product;
import ru.magora.test.service.OrderService;
import ru.magora.test.service.ProductService;


@Transactional
@RestController
public class UserController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	
	/**
	 * Add new Product to the basket
	 * 
	 * @param p
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/api/version/basket/{id}", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> addProductBasket(@PathVariable("id") int id,
			HttpServletRequest req){
		
		Order order = (Order) req.getSession().getAttribute("current_order");
		
		if(req.getSession().getAttribute("current_order") == null){
			order = new Order();
			order.setBasket(new LinkedHashSet<Product>());
		}
		
		Product p = productService.getProduct(id);
		order.getBasket().add(p);
		req.getSession().setAttribute("current_order", order);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * Remove a Product to the basket
	 * 
	 * @param p
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/api/version/basket/{id}", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> removeProductBasket(@PathVariable("id") int id,
			HttpServletRequest req){
		
		Order order = (Order) req.getSession().getAttribute("current_order");
		
		Product p = new Product();
		p.setId(id);
		
		if(req.getSession().getAttribute("current_order") == null){
			order = new Order();
			order.setBasket(new LinkedHashSet<Product>());
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}else if(!order.getBasket().contains(p)){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		order.getBasket().remove(p);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	/**
	 * Get itens of the basket
	 * 
	 * @param p
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/api/version/basket", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<Product>> listBasket(HttpServletRequest req){
		
		Order order = (Order) req.getSession().getAttribute("current_order");
		
		if(order == null){
			return new ResponseEntity<Set<Product>>(new LinkedHashSet<Product>(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Set<Product>>(order.getBasket(),HttpStatus.OK);
	}
	
	/**
	 * Purchase, saves the order
	 * 
	 * @param p
	 * @param req
	 * @return
	 */
	@RequestMapping(value="/api/version/basket", method = RequestMethod.PUT,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> confirmOrder(HttpServletRequest req,@RequestBody Client c){
		
		Order order = (Order) req.getSession().getAttribute("current_order");
		
		if(order == null){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		order.setDate(new Date());
		order.setClient(c);
		
		orderService.saveClient(c);
		
		orderService.saveOrder(order);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
}
