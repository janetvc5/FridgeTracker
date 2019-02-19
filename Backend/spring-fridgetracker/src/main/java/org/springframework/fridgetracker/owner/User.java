package org.springframework.fridgetracker.owner;

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
@Table(name="users")
public class User {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY) 
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;
		
		@Column(name="role")
	    @NotFound(action = NotFoundAction.IGNORE)
		private String role;
		
		//@OneToMany(targetEntity = Fridge.class)
		@Column(name="fridgeid")
	    @NotFound(action = NotFoundAction.IGNORE)
		private Integer fridgeid;
		
		
		public String getRole() {
			return this.role;
		}
		
		public void setRole(String newRole) {
			this.role=newRole;
		}
		
		public Integer getId() {
			return this.id;
		}
		
		public void setId(Integer id) {
			this.id = id;
		}
		
		public Integer getFridgeid() {
			return this.fridgeid;
		}
		
		public void setFridgeid(Integer fridgeId ) {
			this.fridgeid = fridgeId;
		}
		
		public boolean isNew() {
			return this.id == null;
		}
		
		
		@Override
	    public String toString() {
	        return new ToStringCreator(this)
	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("fridgeid", this.getFridgeid())
	                .append("role", this.getRole()).toString();
	    }
}
