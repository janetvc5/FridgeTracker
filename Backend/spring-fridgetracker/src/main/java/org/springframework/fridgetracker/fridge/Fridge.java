package org.springframework.fridgetracker.fridge;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.fridgecontents.Fridgecontents;
import org.springframework.fridgetracker.grocerylist.Grocerylist;
import org.springframework.fridgetracker.user.User;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "fridge")
public class Fridge {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;
	
	@OneToMany(mappedBy="fridge", targetEntity=User.class)
	@Column(name="users")
	@JsonIgnoreProperties("fridge")
	private List users;
	
	@OneToMany(mappedBy="fridge", targetEntity=Fridgecontents.class)
	@Column(name="fridgecontents")
	@JsonIgnoreProperties("fridge")
	private List fridgecontents;
	
	@OneToMany(mappedBy="fridge", targetEntity=Fridgecontents.class)
	@Column(name="grocerylist")
	@JsonIgnoreProperties("fridge")
	private List grocerylist;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List getUsers() {
		return users;
	}

	public void setUsers(List users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}

	public void removeUser(User user) {
		this.users.remove(user);
	}

	public List getFridgecontents() {
		return fridgecontents;
	}

	public void setFridgecontents(List fridgecontents) {
		this.fridgecontents = fridgecontents;
	}

	public void addFridgecontents(Fridgecontents fridgecontents) {
		this.fridgecontents.add(fridgecontents);
	}

	public void removeFridgecontents(Fridgecontents fridgecontents) {
		this.fridgecontents.remove(fridgecontents);
	}
	

	public void addGrocerylist(Grocerylist grocerylist) {
		this.grocerylist.add(grocerylist);
		
	}
	
	public void removeGrocerylist(Grocerylist grocerylist) {
		this.grocerylist.remove(grocerylist);
	}
	
	public boolean isNew() {
		return this.id == null;
	}
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id",this.getId())
				.append("users",this.getUsers())
				.append("fridgecontents",this.getFridgecontents())
				.append("new",this.isNew()).toString();
	}

	
}
