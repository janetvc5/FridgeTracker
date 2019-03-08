package org.springframework.fridgetracker.fridge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "fridge")
public class Fridge {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;

	@Column(name = "userid")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer userid;
	
	@Column(name = "fridgeid")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer fridgeid;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFridgeid() {
		return fridgeid;
	}

	public void setFridgeid(Integer fridgeid) {
		this.fridgeid = fridgeid;
	}

	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("fridgeid", this.getFridgeid())
				.append("userid", this.getUserid())
				.append("id",this.getId()).toString();
	}
	
}
