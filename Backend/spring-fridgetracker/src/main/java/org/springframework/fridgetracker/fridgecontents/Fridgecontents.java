package org.springframework.fridgetracker.fridgecontents;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.fridge.Fridge;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fridgecontents")
public class Fridgecontents {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@NotNull
	@Column(name="foodname")
	private String foodname;
	
	@NotNull
	@Column(name="quantity")
	private Integer quantity;
	
	@NotNull
	@Column(name="expirationdate")
	@JsonFormat(pattern="yyyy-MM-dd")
	private Date expirationdate;
	
	@ManyToOne(targetEntity = Fridge.class)
	@JsonIgnoreProperties({"users","fridgecontents"})
	@JoinColumn
	private Fridge fridge;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public Date getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(Date expirationdate) {
		this.expirationdate = expirationdate;
	}

	public Fridge getFridge() {
		return fridge;
	}

	public void setFridge(Fridge fridge) {
		this.fridge = fridge;
	}

	public boolean isNew() {
		return this.id == null;
	}
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id", this.getId())
				.append("foodname", this.getFoodname())
				.append("quantity",this.getQuantity())
				.append("expirationdate", this.getExpirationdate())
				.append("fridge", this.getFridge())
				.append("new",this.isNew()).toString();

	}
}
