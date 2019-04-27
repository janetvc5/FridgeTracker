package org.springframework.fridgetracker.system;

public class NotFoundException extends RuntimeException {
	public <T> NotFoundException(T item, String table) {
		super("could not find item '"+item.toString()+"' in table '"+table+"'");
	}
	public <T> NotFoundException(T item) {
		super("could not find "+item.toString());
	}
}
