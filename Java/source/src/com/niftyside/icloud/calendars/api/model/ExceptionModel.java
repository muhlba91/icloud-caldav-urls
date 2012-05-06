package com.niftyside.icloud.calendars.api.model;

/**
 * Exception model.
 * 
 * @author Daniel Muehlbachler
 * @copyright 2012 Daniel Muehlbachler
 * 
 * @see http://icloud.niftyside.com
 * 
 * @version 1.1
 * 
 */
public interface ExceptionModel {
	/**
	 * Gets the originally thrown exception.
	 * 
	 * @return the originally thrown exception; null if no exception got thrown
	 *         before
	 * 
	 * @since 1.1
	 */
	Exception getOriginalException();
}
