package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;


// TODO: Auto-generated Javadoc
/**
 * InspectionService
 * 
 * klasa sluzi kao model za entity inspekcijsko tijelo
 * 
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 01.04.2014
 */

@SuppressWarnings("serial")
@Entity
public class InspectionService extends Model {

	/** The id. */
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_service_id_seq")
	@Id
	private Long id;

	/** The name. */
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String name;

	/** The inspectorate id. */
	@Required
	private Long inspectorateId;

	/** The jurisdiction id. */
	@Required
	private Long jurisdictionId;

	/** The person id. */
	@Required
	private Long personId;

	/** The contact person. */
	public Person contactPerson;
	
	/** The jurisdiction. */
	public Jurisdiction jurisdiction;
	
	/** The inspectorate. */
	public Inspectorate inspectorate;

	/**
	 * Kreira novu instancu klase Product, ovaj konstruktor sluzi za slucajeve
	 * gdje je proizvod izbrisan iz baze, ali zelimo zadrzati izvjestaj. Sve
	 * varijable ce imati vrijednost "Nije dostupno"
	 * */
	public InspectionService() {
		String placeHolder = "NA";
		name = placeHolder;
		inspectorateId = null;
		jurisdictionId = null;
		personId = null;
	}

	/**
	 * Sluzi da bi se izbjegao scenarij rusenja aplikacije u slucaju pretrage za
	 * ne postojecom id vrijednosti ili null vrijednosti U slucaju da je
	 * proslijedena nedozvoljena vrijednost za id kreira default instancu
	 * InspectionService klase.
	 *
	 * @param id            id inspekcijskog tijela
	 */
	public InspectionService(Long id) {
		if (id == null)
			new InspectionService();

		if (exists(id)) {
			this.id = id;
			copyValues(find.byId(id));
			loadAdditional(this);
		}
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
		this.name = name;
	}

	/**
	 * Gets the inspectorate id.
	 *
	 * @return the inspectorateId
	 */
	public Long getInspectorateId() {
		return inspectorateId;
	}

	/**
	 * Sets the inspectorate id.
	 *
	 * @param inspectorateId            the inspectorateId to set
	 */
	public void setInspectorateId(Long inspectorateId) {
		this.inspectorateId = inspectorateId;
	}

	/**
	 * Gets the jurisdiction id.
	 *
	 * @return the jurisdictionId
	 */
	public Long getJurisdictionId() {
		return jurisdictionId;
	}

	/**
	 * Sets the jurisdiction id.
	 *
	 * @param jurisdictionId            the jurisdictionId to set
	 */
	public void setJurisdictionId(Long jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}

	/**
	 * Gets the person id.
	 *
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * Sets the person id.
	 *
	 * @param personId            the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/** Varijabla sluzi kao konektor za bazu. */
	public static Finder<Long, InspectionService> find = new Finder<Long, InspectionService>(
			Long.class, InspectionService.class);

	/**
	 * All.
	 *
	 * @return Listu nadleznih tijela iz baze poredanu po imenu proizvoda A-Z
	 */
	public static List<InspectionService> all() {
		List<InspectionService> all = find.order("name asc").findList();
		for (int i = 0; i < all.size(); i++) {
			InspectionService current = all.get(i);
			loadAdditional(current);
		}
		return all;
	}

	/**
	 * Da bi sprijecili probleme sa brisanjem podataka ova klasa ne drzi
	 * direktno instance druge klase nego id vrijednosti preko kojih se u ovoj
	 * metodi te instance kreiraju.
	 *
	 * @param current            instanca klase
	 */
	private static void loadAdditional(InspectionService current) {
		current.contactPerson = new Person(current.personId);
		current.jurisdiction = new Jurisdiction(current.jurisdictionId);
		current.inspectorate = new Inspectorate(current.inspectorateId);
	}

	/**
	 * All as map.
	 *
	 * @return Mapa svih inspekcijskih tijela u bazi, key je id proizvoda a
	 *         vrijednost
	 * HashMap<String, String>
	 */
	public static Map<String, String> allAsMap() {
		List<InspectionService> list = all();
		Map<String, String> hash = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			InspectionService current = list.get(i);
			hash.put(String.valueOf(current.id), current.name);
		}
		return hash;
	}

	/**
	 * Mapa inspekcijskih tijela sa dodanom vrijednoscu <-1, Svi> u tome se
	 * razlikuje od allAsMap() mapa se koristi za izbornik pretrage.
	 *
	 * @return HashMap key je id a vrijednost ime
	 * @see allAsMap()
	 */
	public static Map<String, String> getInspectionServices() {
		Map<String, String> services = new HashMap<String, String>();
		List<InspectionService> all = find.setDistinct(true).select("name, id")
				.findList();
		services.put("-1", "Svi");
		for (int i = 0; i < all.size(); i++) {
			services.put(all.get(i).id.toString(), all.get(i).name);
		}
		return services;
	}

	/**
	 * Spasava inspekcijsko tijelo u bazu.
	 *
	 * @param inspectionService the inspection service
	 */
	public static void create(InspectionService inspectionService) {
		inspectionService.save();
	}

	/**
	 * Spasava promjene u bazu.
	 *
	 * @param newValues            Objekt tipa InspectionService u kojem su nove vrijednosti
	 */
	public static void updateInspectionService(InspectionService newValues) {
		InspectionService inspectionService = find.byId(newValues.id);
		if (inspectionService != null) {
			inspectionService.copyValues(newValues);
			inspectionService.update();
		}
	}

	/**
	 * Brise inspekcijsko tijelo iz baze i skriva brise izvjestaje vezane za to
	 * inspekcijsko tijelo.
	 *
	 * @param id the id
	 */
	public static void delete(Long id) {
		List<Inspection> connected = Inspection.getFind().where()
				.eq("inspection_service_id", id)
				.findList();
		for(int i = 0; i<connected.size(); i++)
			connected.get(i).delete();
		find.ref(id).delete();
	}

	/**
	 * Exists.
	 *
	 * @param id            id koji treba provjeriti
	 * @return true ako inspekcijsko tijelo postoji u bazi, u suprotnom false
	 */
	public static boolean exists(Long id) {
		return find.byId(id) != null;
	}

	/**
	 * Kopira vrijednosti iz jednog u drugi objekt tipa InspectionService.
	 *
	 * @param newValues            objekt tipa InspectionService koji sadrzi nove vrijednosti
	 */
	private void copyValues(InspectionService newValues) {
		this.name = newValues.name;
		this.inspectorateId = newValues.inspectorateId;
		this.jurisdictionId = newValues.jurisdictionId;
		this.personId = newValues.personId;
	}

}