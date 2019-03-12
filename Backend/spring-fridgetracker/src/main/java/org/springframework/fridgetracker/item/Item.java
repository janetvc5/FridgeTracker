package org.springframework.fridgetracker.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.core.style.ToStringCreator;

@Entity
@Table(name = "item")
public class Item {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	private Integer id;
	
	@NotNull
	@Column(name = "itemname")
	@NotFound(action = NotFoundAction.IGNORE)
	private String itemname;
	
	@NotNull
	@Column(name = "tags")
	@NotFound(action = NotFoundAction.IGNORE)
	private String tags;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
	
	@Override
	public String toString() {
		return new ToStringCreator(this)
				.append("id", this.getId())
				.append("itemname", this.getItemname())
				.append("tags", this.getTags()).toString();

	}
}
