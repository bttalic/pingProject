package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

/**
 * Product
 * 
 * klasa sluzi kao model za entity proizvod
 * 
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 01.04.2014
 */

@SuppressWarnings("serial")
@Entity
public class Product extends Model {

	/** The id. */
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_id_seq")
	@Id
	private Long id;

	/** The name. */
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String name;

	/** The manufacturer. */
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String manufacturer;

	/** The country of origin. */
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String countryOfOrigin;

	// Optional values
	/** The serial number. */
	@MinLength(value = 3)
	@MaxLength(value = 20)
	private String serialNumber;

	/** The description. */
	@MinLength(value = 3)
	@MaxLength(value = 255)
	private String description;

	/**
	 * Kreira novu instancu klase Product, ovaj konstruktor sluzi za slucajeve
	 * gdje je proizvod izbrisan iz baze, ali zelimo zadrzati izvjestaj. Sve
	 * varijable ce imati vrijednost "Nije dostupno"
	 * */
	public Product() {
		String placeHolder = "NA";
		id = null;
		name = placeHolder;
		manufacturer = placeHolder;
		countryOfOrigin = placeHolder;
		serialNumber = placeHolder;
		description = placeHolder;
	}

	/**
	 * Instantiates a new product.
	 *
	 * @param id            id proizvoda u bazi
	 * @param name            ime proizvoda
	 * @param manufacturer            proizvodac
	 * @param countryOfOrigin            zemlja porijekla
	 * @param serialNumber            serijski broj proizvoda
	 * @param description            opis proizvoda
	 */
	public Product(Long id, String name, String manufacturer,
			String countryOfOrigin, String serialNumber, String description) {
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.countryOfOrigin = countryOfOrigin;
		this.serialNumber = serialNumber;
		this.description = description;
	}

	/**
	 * Kreira Product (Proizvod) bez postavljenog id-a, id ce dobiti vrijednost
	 * slijedeceg id-a iz baze.
	 *
	 * @param name            ime proizvoda
	 * @param manufacturer            proizvodac
	 * @param countryOfOrigin            zemlja porijekla
	 * @param serialNumber            serijski broj proizvoda
	 * @param description            opis proizvoda
	 */
	public Product(String name, String manufacturer, String countryOfOrigin,
			String serialNumber, String description) {
		this.id = find.nextId();
		this.name = name;
		this.manufacturer = manufacturer;
		this.countryOfOrigin = countryOfOrigin;
		this.serialNumber = serialNumber;
		this.description = description;
	}

	/**
	 * Sluzi da bi se izbjegao scenarij rusenja aplikacije u slucaju pretrage za
	 * ne postojecom id vrijednosti ili null vrijednosti U slucaju da je
	 * proslijedena nedozvoljena vrijednost za id kreira default instancu
	 * Product klase.
	 *
	 * @param id            id proizvoda
	 */
	public Product(Long id) {
		if (id == null) {
			new Product();
			return;
		}
		if (exists(id) == false) {
			new Product();
			return;
		}
		this.copyValues(find.byId(id));
	}

	/* Geteri i Seteri */
	/**
	 * Geter funkcija za id.
	 *
	 * @return vraca id proizvoda
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
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
	 * Seter za name (ime) proizvoda Vrijednost mora imati duzinu izmedu 3 i 50
	 * znakova.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		if (name.length() > 3 && name.length() < 50)
			this.name = name;
	}

	/**
	 * Geter za varijablu manufacturer (proizvodac).
	 *
	 * @return proizvodaca
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * Seter za varijablu manufacturer (proizvodac) Vrijednost mora imati duzinu
	 * izmedu 3 i 50 znakova.
	 *
	 * @param manufacturer the new manufacturer
	 */
	public void setManufacturer(String manufacturer) {
		if (manufacturer.length() > 3 && manufacturer.length() < 50)
			this.manufacturer = manufacturer;
	}

	/**
	 * Geter za zemlju porijekla Vrijednost mora imati duzinu izmedu 3 i 50
	 * znakova.
	 *
	 * @return zemlju porijekla
	 */
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	/**
	 * Seter za zemlju porijekla Vrijednost mora imati duzinu izmedu 3 i 50
	 * znakova.
	 *
	 * @param countryOfOrigin the new country of origin
	 */
	public void setCountryOfOrigin(String countryOfOrigin) {
		if (countryOfOrigin.length() > 3 && countryOfOrigin.length() < 50)
			this.countryOfOrigin = countryOfOrigin;
	}

	/**
	 * Geter za serijski broj proizvoda Vrijednost mora imati duzinu izmedu 3 i
	 * 50 znakova.
	 *
	 * @return serialNumber
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Seter za serijski broj proizvoda Vrijednost mora imati duzinu izmedu 3 i
	 * 50 znakova.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(String serialNumber) {
		if (serialNumber.length() >= 3 && serialNumber.length() <= 50)
			this.serialNumber = serialNumber;
	}

	/**
	 * Gets the description.
	 *
	 * @return opis proizvoda
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Vrijednost mora imati duzinu izmedu 3 i 255 znakova.
	 *
	 * @param description the new description
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	/** Varijabla sluzi kao konektor za bazu. */
	private static Finder<Long, Product> find = new Finder<Long, Product>(
			Long.class, Product.class);

	/**
	 * All.
	 *
	 * @return Listu proizvoda iz baze poredanu po imenu proizvoda A-Z
	 */
	public static List<Product> all() {
		return find.order("name asc").findList();
	}

	/**
	 * All as map.
	 *
	 * @return Mapa svih proizvoda u bazi, key je id proizvoda a vrijednost ime
	 *         + proizvodac proizvoda
	 * HashMap<String, String>
	 * @see toString()
	 */
	public static Map<String, String> allAsMap() {
		List<Product> list = all();
		Map<String, String> hash = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Product current = list.get(i);
			hash.put(String.valueOf(current.id), current.toString());
		}
		return hash;
	}

	/**
	 * Spasava proiyvod u bazu.
	 *
	 * @param product the product
	 */
	public static void create(Product product) {
		product.save();
	}

	/**
	 * Spasava promjene u bazu.
	 *
	 * @param newValues            Objekt tipa Product u kojem su nove vrijednosti
	 */
	public static void updateProduct(Product newValues) {
		Product product = find.byId(newValues.id);
		if (product != null) {
			product.copyValues(newValues);
			product.update();
		}
	}

	/**
	 * Brise proizvod iz baze i skriva sve izvjestaje vezane za taj proizvod.
	 *
	 * @param id the id
	 */
	public static void delete(Long id) {
		List<Inspection> inspections = Inspection.getFind()
				.where()
				.eq("product_id", id)
				.findList();
		for(int i = 0; i<inspections.size(); i++)
			inspections.get(i).delete();
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
	 * Kopira vrijednosti iz jednog u drugi objekt tipa Product.
	 *
	 * @param newValues            objekt tipa Product koji sadrzi nove vrijednosti
	 */
	private void copyValues(Product newValues) {
		this.name = newValues.name;
		this.manufacturer = newValues.manufacturer;
		this.countryOfOrigin = newValues.countryOfOrigin;
		this.serialNumber = newValues.serialNumber;
		this.description = newValues.description;
	}

	/**
	 * Vraca string sa vrijednoscu ime + proizvodac proizvoda.
	 * 
	 * @return ime + proizvodac proizvoda.
	 */
	public String toString() {
		return this.name + " - " + this.manufacturer;
	}

}
