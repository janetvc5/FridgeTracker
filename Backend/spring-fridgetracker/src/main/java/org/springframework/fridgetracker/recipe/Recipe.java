package org.springframework.fridgetracker.recipe;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.recipeitem.Recipeitem;

@Entity
@Table(name="recipe")
public class Recipe {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "directions")
	private String directions;
	@OneToMany(mappedBy="fridge", targetEntity=Recipeitem.class)
	@Column(name = "items")
	private List items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDirections() {
		return directions;
	}
	public void setDirections(String directions) {
		this.directions = directions;
	}
	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("name",this.getName())
				.append("items",this.getItems())
				.append("directions",this.getDirections()).toString();
	}
}
