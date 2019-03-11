package org.springframwork.fridgetracker.fridgecontents;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "fridge_contents")
public class Content {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idfood")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer idfood;
	
	@Column(name = "foodname")
	@NotFound(action = NotFoundAction.IGNORE)
	private String foodname;
	
	@Column(name = "quantity")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer quantity;
	
	@Column(name = "fridgeid")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer fridgeid;
	
	@Column(name = "expirationdate")
	@NotFound(action = NotFoundAction.IGNORE)
	@JsonFormat(pattern="yyyy-MM-dd")
    private Date expirationdate;

	public Integer getIdfood() {
		return idfood;
	}

	public void setIdfood(Integer idfood) {
		this.idfood = idfood;
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

	public Integer getFridgeid() {
		return fridgeid;
	}

	public void setFridgeid(Integer fridgeid) {
		this.fridgeid = fridgeid;
	}

	public Date getExpirationdate() {
		return expirationdate;
	}

	public void setExpirationdate(Date expirationdate) {
		this.expirationdate = expirationdate;
	}  
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("idfood",this.getIdfood())
				.append("foodname",this.getFoodname())
				.append("fridgeid",this.getFridgeid())
				.append("quantity",this.getQuantity())
				.append("experationdate",this.getExpirationdate()).toString();
	}
}
