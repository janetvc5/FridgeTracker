package com.example.demo.fridge;
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
@Table(name="foods")
public class Food { 
    @Column(name = "food_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String foodName;
    @Column(name = "amount")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer amount;
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;
    
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer id) {
        this.amount = id;
    }

    public String getFoodName() {
        return foodName;
    }
    public Integer getId() {
        return id;
    }
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("food_name",this.getFoodName())
                .append("amount", this.getAmount()).toString();
    }
}
