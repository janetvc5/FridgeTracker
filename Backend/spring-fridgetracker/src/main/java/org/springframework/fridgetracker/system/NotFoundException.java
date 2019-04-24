package org.springframework.fridgetracker.system;

public class NotFoundException extends RuntimeException {
	/**
	 * Creates a RuntimeException with a string describing where the item was not found
	 * @param item - item not found
	 * @param table - table that item was not in
	 */
	public <T> NotFoundException(T item, String table) {
		super("could not find item '"+item.toString()+"' in table '"+table+"'");
	}
	/**
	 * Creates a RuntimeException with a string describing what item was not found
	 * @param item - Item that was not found
	 */
	public <T> NotFoundException(T item) {
		super("could not find "+item.toString());
	}
}
