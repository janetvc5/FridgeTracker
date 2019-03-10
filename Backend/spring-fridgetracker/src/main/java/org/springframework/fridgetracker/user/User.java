package org.springframework.fridgetracker.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
import org.springframework.fridgetracker.fridge.Fridge;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "username")
	@NotFound(action = NotFoundAction.IGNORE)
	private String username;

	@Column(name = "password")
	@NotFound(action = NotFoundAction.IGNORE)
	private String password;

	@Column(name = "role")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer role;

	//@ManyToOne(targetEntity = Fridge.class)
	//@JoinColumn
	@Column(name = "fridgeid")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer fridgeid;

	@Column(name = "firstname")
	@NotFound(action = NotFoundAction.IGNORE)
	private String firstname;

	@Column(name = "lastname")
	@NotFound(action = NotFoundAction.IGNORE)
	private String lastname;

	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getRole() {
		return this.role;
	}
	public void setRole(Integer newRole) {
		this.role = newRole;
	}

	public Integer getFridgeid() {
		return this.fridgeid;
	}
	public void setFridgeid(Integer fridgeId) {
		this.fridgeid = fridgeId;
	}

	public String getFirstname() {
		return this.firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isNew() {
		return this.username == null;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("username", this.getUsername())
				.append("password", this.getPassword())
				.append("role", this.getRole())
				.append("fridgeid", this.getFridgeid())
				.append("firstname", this.getFirstname())
				.append("lastname", this.getLastname())
				.append("id",this.getId())
				.append("new", this.isNew()).toString();

	}
}
