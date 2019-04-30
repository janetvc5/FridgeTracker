package org.springframework.fridgetracker.recipeitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.fridge.Fridge;
import org.springframework.fridgetracker.recipe.Recipe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="recipeitem")
public class Recipeitem {
	@Id
	@GeneratedValue
	@Column(name="id")
	private Integer id;
	@Column(name="itemname")
	private String itemname;
	@Column(name="quantity")
	private String quantity;
	@Column(name="unit")
	private String unit;
	@ManyToOne(targetEntity = Recipe.class)
	@JsonIgnoreProperties({"items"})
	@JoinColumn
	private Recipe recipe;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("itemname",this.getItemname())
				.append("quantity",this.getQuantity())
				.append("unit",this.getUnit())
				.append("recipe",this.getRecipe()).toString();
	}
}
