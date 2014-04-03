package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

/**
 * Jurisdiction
 * 
 * Pomocni model za cuvanje podataka o nadleznosti u @see InspectionService
 * modelu (Inspekcijsko Tijelo) Klasa nema settere jer nije predvideno
 * ubacivanje podataka preko klase @see specifikacije zadatka
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 01.04.2014
 */

@SuppressWarnings("serial")
@Entity
public class Jurisdiction extends Model {

	/** The id. */
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jurisdiction_id_seq")
	@Id
	private Long id;

	/** The name. */
	@Required
	@MinLength(value = 2)
	@MaxLength(value = 16)
	private String name;

	/**
	 * Konstruktor za slucaj greske pri spremanju/ucitavanju podataka.
	 */
	public Jurisdiction() {
		String placeHolder = "NA";
		name = placeHolder;
	}

	/**
	 * Sluzi da bi se izbjegao scenarij rusenja aplikacije u slucaju pretrage za
	 * ne postojecom id vrijednosti ili null vrijednosti U slucaju da je
	 * proslijedena nedozvoljena vrijednost za id kreira default instancu
	 * Jurisdiction klase.
	 *
	 * @param id            id proizvoda
	 */
	public Jurisdiction(Long id) {
		if (id == null)
			new Jurisdiction();

		if (exists(id) == true)
			copyValues(find.byId(id));
		else
			new Jurisdiction();
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** Varijabla sluzi kao konektor za bazu. */
	public static Finder<Long, Jurisdiction> find = new Finder<Long, Jurisdiction>(
			Long.class, Jurisdiction.class);

	/**
	 * All.
	 *
	 * @return Listu nadleznosti iz baze poredanu po nazivu A-Z
	 */
	public static List<Jurisdiction> all() {
		return find.order("name asc").findList();
	}

	/**
	 * Exists.
	 *
	 * @param id            id koji treba provjeriti
	 * @return true ako nadleznost postoji u bazi, u suprotnom false
	 */
	public static boolean exists(Long id) {
		return find.byId(id) != null;
	}

	/**
	 * All as map.
	 *
	 * @return Mapa svih osoba u bazi, key je id nadleznosti a vrijednost ime
	 * HashMap<String, String>
	 */
	public static Map<String, String> allAsMap() {
		List<Jurisdiction> list = all();
		Map<String, String> hash = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			hash.put(String.valueOf(list.get(i).id), list.get(i).name);
		}
		return hash;
	}

	/**
	 * Kopira vrijednosti iz jednog u drugi objekt tipa Jurisdiction.
	 *
	 * @param old            objekt tipa Jurisdiction koji sadrzi nove vrijednosti
	 */
	private void copyValues(Jurisdiction old) {
		this.name = old.name;
		this.id = old.id;
	}
}