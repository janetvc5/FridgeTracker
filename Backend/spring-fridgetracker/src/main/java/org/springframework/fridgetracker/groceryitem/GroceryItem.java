package org.springframework.fridgetracker.groceryitem;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.fridge.Fridge;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class GroceryItem {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	
	@NotNull
	@ManyToOne(targetEntity = Fridge.class)
	@JsonIgnoreProperties({"users","fridgecontents","grocerylist"})
	@JoinColumn
	private Fridge fridge;
	
	@NotNull
	@Column(name="foodname")
	private String foodname;
	
	@NotNull
	@Column(name="type")
	private Integer type;

	@NotNull
	@Column(name="quantity")
	private Integer quantity;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fridge getFridge() {
		return fridge;
	}

	public void setFridge(Fridge fridge) {
		this.fridge = fridge;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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
				.append("type",this.getType())
				.append("fridge", this.getFridge()).toString();

	}
}
