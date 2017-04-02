package ru.magora.test.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import ru.magora.test.model.Product;

@Repository
public class ProductService {
	
	@PersistenceContext
	private EntityManager em;
	

	public ProductService(){
		
	}
	
	public List<Product> getProducts(){

		List<Product> listProducts = em.createQuery("select p from Product as p").getResultList();
		return listProducts;
	}
	
	public  void saveProduct(Product p){

		em.persist(p);

	}
	
	public boolean updateProduct(Product p){

		Product temp = em.find(Product.class, p.getId());
		
		if(temp == null){
			return false;
		}
		em.merge(p);

		return true;
	}
	
	public void deleteProduct(Product p){

		em.remove(em.contains(p) ? p : em.merge(p));

	}
	
	public Product getProduct(int id){

		Product p = em.find(Product.class, id);
		return p;
	}
	

}
