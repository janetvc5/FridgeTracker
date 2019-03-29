package org.springframework.fridgetracker.grocerylist;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "grocerylist")
public class Grocerylist {
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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Fridge getFridge() {
		return fridge;
	}

	public void setFridge(Fridge fridge) {
		this.fridge = fridge;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("foodname",this.getFoodname())
				.append("quantity", this.getQuantity())
				.append("fridge",this.getFridge()).toString();
	}
}
