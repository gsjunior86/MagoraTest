package ru.magora.test.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import ru.magora.test.model.Order;
import ru.magora.test.model.Product;
import ru.magora.test.service.OrderService;
import ru.magora.test.service.ProductService;
import ru.magora.test.util.DateQuery;

@Transactional
@RestController
public class AdminController {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * Retrieve All Products
	 * @return
	 */
	
	
	@RequestMapping(value="/api/version/token", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getToken(HttpServletRequest request){
		CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		
//		Cookie cookie = WebUtils.getCookie(request, "X-CSRF-TOKEN");

        String token = csrf.getToken();
		
		return new ResponseEntity<String>(token,HttpStatus.OK);
	}
	

	@RequestMapping(value="/api/version/products", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Product>> listAllProducts(){
		List<Product> listProducts = productService.getProducts();
		
		return new ResponseEntity<List<Product>>(listProducts,HttpStatus.OK);
	}
	
	/**
	 * Retrieve a single Product
	 * @return
	 */
	
	@RequestMapping(value="/api/version/products/{id}", method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> getProduct(@PathVariable("id") int id){
		
		Product prod = productService.getProduct(id);
		if(prod == null){
			 return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Product>(prod,HttpStatus.OK);
	} 
	
	
	/**
	 * Delete a single Product
	 * @return
	 */
	
	@RequestMapping(value="/api/version/products/{id}", method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id){
		
		Product p = productService.getProduct(id);
		if(p == null){
			 return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
		}
		
		productService.deleteProduct(p);
		
		return new ResponseEntity<Product>(HttpStatus.OK);
	} 
	
	
	/**
	 * Insert a new Product
	 * @return
	 */
	
	@RequestMapping(value="/api/version/products", method = RequestMethod.POST)
	public ResponseEntity<Void> createProduct(@RequestBody Product p,  UriComponentsBuilder ucBuilder){
		
		productService.saveProduct(p);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/version/products/{id}").buildAndExpand(p.getId()).toUri());
        return new ResponseEntity<Void>(headers,HttpStatus.CREATED);
	}
	
	/**
	 * Update an existing product
	 * @return
	 */
	@RequestMapping(value="/api/version/products", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateProduct(@RequestBody Product p){
		if(!productService.updateProduct(p)){
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<Void>(HttpStatus.FOUND);
	}

	@RequestMapping(value="/api/version/orders", method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Order>> getOrders(@RequestBody DateQuery dq){
		List<Order> listOrders = new ArrayList<Order>();
		try {
			listOrders = orderService.getOrders(dq);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<List<Order>>(listOrders,HttpStatus.OK);
	}
	
}
