package models;

import java.util.Date;
/**
 * Search
 * 
 * klasa sluzi kao pomocna klasa za pretragu
 *  * 
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 01.04.2014
 */
public class Search {
	
	/** Izabrani pocetni datum. */
	public Date inspectionDateStart;
	
	/** Izabrani zavrsni datum. */
	public Date inspectionDateEnd;
	
	/** Izabrano Inspekcijsko tijelo. */
	public Long inspectionServiceId;
}