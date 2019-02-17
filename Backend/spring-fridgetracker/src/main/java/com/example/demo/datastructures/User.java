package com.example.demo.datastructures;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;
@Entity
@Table(name="Users")
public class User {
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;
	@Column(name="role")
    @NotFound(action = NotFoundAction.IGNORE)
	private String Role;
	@Column(name="fridgeId")
    @NotFound(action = NotFoundAction.IGNORE)
	private Integer FridgeID;
	
	public void setRole(String newRole) {
		this.Role=newRole;
	}
	public Integer getId() {
		return this.id;
	}
	public String getRole() {
		return this.Role;
	}
	public Integer getFridgeId() {
		return this.FridgeID;
	}
}
