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
@Table(name="fridges")
public class Fridge {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer id;

    @Column(name = "fridge_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private String fridgeName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getFridgeName() {
        return fridgeName;
    }
    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("id", this.getId())
                .append("fridge_name",this.getFridgeName()).toString();
    }
}
