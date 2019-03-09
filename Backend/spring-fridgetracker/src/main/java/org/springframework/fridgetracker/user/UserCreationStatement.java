package org.springframework.fridgetracker.user;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.fridgetracker.fridge.Fridge;

public class UserCreationStatement {
	private User user;
	private Fridge fridge;
	/*
	public UserCreationStatement(User user, Fridge fridge) {
		this.user = user;
		this.fridge = fridge;
	}
	public UserCreationStatement(User user) {
		this.user = user;
		this.fridge = new Fridge(user.getId());
	}*/
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
