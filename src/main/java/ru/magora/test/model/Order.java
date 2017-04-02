package ru.magora.test.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name="tbl_order")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="order_id")
	private Integer idOrder;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Product> basket;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date dateTime;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private Client client;
	
	
	

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public Date getDate() {
		return dateTime;
	}
	public void setDate(Date dateTime) {
		this.dateTime = dateTime;
	}
	public Integer getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}
	public Set<Product> getBasket() {
		return basket;
	}
	public void setBasket(Set<Product> basket) {
		this.basket = basket;
	}
	
	
	

}
