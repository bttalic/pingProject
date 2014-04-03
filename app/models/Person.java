package models;

import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * Person
 * 
 * Pomocni model za cuvanje podataka o kontak osobama u @see InspectionService
 * modelu (Inspekcijsko Tijelo)
 * 
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 01.04.2014
 */

@SuppressWarnings("serial")
@Entity
public class Person extends Model {

	/** The id. */
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
	@Id
	private Long id;

	/** The name. */
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String name;

	/** The phone number. */
	@Required
	@Pattern(value = "\\+387[1-9]{8}$")
	private String phoneNumber;

	/** The email. */
	@Required
	@Email
	@MaxLength(value = 50)
	private String email;

	/** The fax. */
	@Pattern(value = "\\+387[1-9]{8}$")
	private String fax;

	/**
	 * Kreira novu instancu klase Person, ovaj konstruktor sluzi za slucajeve
	 * gdje je proizvod izbrisan iz baze, ali zelimo referentna inspekcijska
	 * tijela. Sve varijable ce imati vrijednost "Nije dostupno"
	 * */
	public Person() {
		String placeHolder = "NA";
		name = placeHolder;
		phoneNumber = placeHolder;
		email = placeHolder;
	}

	/**
	 * Instantiates a new person.
	 *
	 * @param id            id u bazi
	 * @param name            ime osobe
	 * @param phoneNumber            broj telefona
	 * @param email            email
	 * @param fax            fax (opcionalno)
	 */
	public Person(Long id, String name, String phoneNumber, String email,
			String fax) {

	}

	/**
	 * Instantiates a new person.
	 *
	 * @param id            id u bazi
	 * @param name            ime osobe
	 * @param phoneNumber            broj telefona
	 * @param email            email
	 */
	public Person(Long id, String name, String phoneNumber, String email) {
		this.id=id;
		this.name=name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	/**
	 * Sluzi da bi se izbjegao scenarij rusenja aplikacije u slucaju pretrage za
	 * ne postojecom id vrijednosti ili null vrijednosti U slucaju da je
	 * proslijedena nedozvoljena vrijednost za id kreira default instancu Person
	 * klase.
	 *
	 * @param id            id proizvoda
	 */
	public Person(Long id) {
		if (id == null) {
			new Person();
			return;
		}

		if (exists(id)) {
			this.copyValues(find.byId(id));
			return;
		}
		new Person();
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
	 * Sets the id.
	 *
	 * @param id            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name            the name to set
	 */
	public void setName(String name) {
		if (name.length() > 3 && name.length() < 50)
			this.name = name;
	}

	/**
	 * Gets the phone number.
	 *
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Sets the phone number.
	 *
	 * @param phoneNumber            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() == 12 && phoneNumber.indexOf("+387") == 0)
			this.phoneNumber = phoneNumber;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the fax.
	 *
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * Sets the fax.
	 *
	 * @param fax            the fax to set
	 */
	public void setFax(String fax) {
		if (fax.length() == 12 && fax.indexOf("+387") == 0)
			this.fax = fax;
	}

	/** Varijabla sluzi kao konektor za bazu. */
	private static Finder<Long, Person> find = new Finder<Long, Person>(
			Long.class, Person.class);

	/**
	 * All.
	 *
	 * @return Listu osoba iz baze poredanu po imenu osobe A-Z
	 */
	public static List<Person> all() {
		return find.order("name asc").findList();
	}

	/**
	 * All as map.
	 *
	 * @return Mapa svih osoba u bazi, key je id osobe a vrijednost ime + broj
	 *         telefona
	 * HashMap<String, String>
	 * @see toString()
	 */
	public static Map<String, String> allAsMap() {
		List<Person> list = all();
		Map<String, String> hash = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Person current = list.get(i);
			hash.put(String.valueOf(current.id), current.toString());
		}
		return hash;
	}

	/**
	 * Creates the.
	 *
	 * @param person the person
	 */
	public static void create(Person person) {
		person.save();
	}

	/**
	 * Spasava promjene u bazu.
	 *
	 * @param newValues            Objekt tipa Person u kojem su nove vrijednosti
	 */
	public static void updatePerson(Person newValues) {
		Person person = find.byId(newValues.id);
		if (person != null) {
			person.copyValues(newValues);
			person.update();
		}
	}

	/**
	 * Brise osobu iz baze, izbjegnuto je brisanje inspekcijskih tijela
	 * povezanih s tim osobama da bi se omogucilo dodavanje druge osobe
	 * umjesto gubljenja inspkecijskih tijela i povezanih kontrola.
	 *
	 * @param id the id
	 */
	public static void delete(Long id) {
		find.ref(id).delete();
	}

	/**
	 * Exists.
	 *
	 * @param id            id koji treba provjeriti
	 * @return true ako proizvod postoji u bazi, u suprotnom false
	 */
	public static boolean exists(Long id) {
		return find.byId(id) != null;
	}

	/**
	 * Kopira vrijednosti iz jednog u drugi objekt tipa Person.
	 *
	 * @param newValues            objekt tipa Person koji sadrzi nove vrijednosti
	 */
	private void copyValues(Person newValues) {
		this.name = newValues.name;
		this.phoneNumber = newValues.phoneNumber;
		this.email = newValues.email;
		this.fax = newValues.fax;
	}

	/**
	 * Vraca string sa vrijednoscu ime + broj telefona osobe.
	 * 
	 * @return me + broj telefona osobe.
	 */
	public String toString() {
		return this.name + " " + this.phoneNumber;
	}

}