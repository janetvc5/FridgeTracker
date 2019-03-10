package org.springframework.fridgetracker.user;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.fridgetracker.fridge.Fridge;

/*
 * This object is used in the creation statement of a User.
 * 
 * If a user is not provided, an error will be thrown. A fridge can be provided,
 * but if one is not provided, one will be created for this user.
 */
public class UserCreationStatement {
	private User user;
	private Fridge fridge;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Fridge getFridge() {
		return fridge;
	}
	public void setFridge(Fridge fridge) {
		this.fridge = fridge;
	}
}
