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
@Table(name="Users")
public class Owner {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY) 
	    @Column(name = "id")
	    @NotFound(action = NotFoundAction.IGNORE)
	    private Integer id;
		
		@Column(name="role")
	    @NotFound(action = NotFoundAction.IGNORE)
		private String role;
		
		//@OneToMany(targetEntity = Fridge.class)
		@Column(name="fridgeId")
	    @NotFound(action = NotFoundAction.IGNORE)
		private Integer fridgeId;
		
		
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
		
		public Integer getFridgeId() {
			return this.fridgeId;
		}
		
		public void setFridgeId(Integer fridgeId ) {
			this.fridgeId = fridgeId;
		}
		
		public boolean isNew() {
			return this.id == null;
		}
		
		
		@Override
	    public String toString() {
	        return new ToStringCreator(this)
	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("fridgeId", this.getFridgeId())
	                .append("role", this.getRole()).toString();
	    }
}
