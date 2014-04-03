package models;

import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;


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

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_service_id_seq")
	@Id
	private Long id;

	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	private String name;

	@Required
	private Long inspectorateId;

	@Required
	private Long jurisdictionId;

	@Required
	private Long personId;

	public Person contactPerson;
	public Jurisdiction jurisdiction;
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
	 * InspectionService klase
	 * 
	 * @param id
	 *            id inspekcijskog tijela
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the inspectorateId
	 */
	public Long getInspectorateId() {
		return inspectorateId;
	}

	/**
	 * @param inspectorateId
	 *            the inspectorateId to set
	 */
	public void setInspectorateId(Long inspectorateId) {
		this.inspectorateId = inspectorateId;
	}

	/**
	 * @return the jurisdictionId
	 */
	public Long getJurisdictionId() {
		return jurisdictionId;
	}

	/**
	 * @param jurisdictionId
	 *            the jurisdictionId to set
	 */
	public void setJurisdictionId(Long jurisdictionId) {
		this.jurisdictionId = jurisdictionId;
	}

	/**
	 * @return the personId
	 */
	public Long getPersonId() {
		return personId;
	}

	/**
	 * @param personId
	 *            the personId to set
	 */
	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	/**
	 * Varijabla sluzi kao konektor za bazu
	 */
	public static Finder<Long, InspectionService> find = new Finder<Long, InspectionService>(
			Long.class, InspectionService.class);

	/**
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
	 * metodi te instance kreiraju
	 * 
	 * @param current
	 *            instanca klase
	 */
	private static void loadAdditional(InspectionService current) {
		current.contactPerson = new Person(current.personId);
		current.jurisdiction = new Jurisdiction(current.jurisdictionId);
		current.inspectorate = new Inspectorate(current.inspectorateId);
	}

	/**
	 * @return Mapa svih inspekcijskih tijela u bazi, key je id proizvoda a
	 *         vrijednost
	 * 
	 * @return HashMap<String, String>
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
	 * razlikuje od allAsMap() mapa se koristi za izbornik pretrage
	 * 
	 * @see allAsMap()
	 * 
	 * @return HashMap key je id a vrijednost ime
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
	 * Spasava inspekcijsko tijelo u bazu
	 * 
	 * @param inspectionService
	 */
	public static void create(InspectionService inspectionService) {
		inspectionService.save();
	}

	/**
	 * Spasava promjene u bazu
	 * 
	 * @param newValues
	 *            Objekt tipa InspectionService u kojem su nove vrijednosti
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
	 * inspekcijsko tijelo
	 * 
	 * @param id
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
	 * 
	 * @param id
	 *            id koji treba provjeriti
	 * @return true ako inspekcijsko tijelo postoji u bazi, u suprotnom false
	 */
	public static boolean exists(Long id) {
		return find.byId(id) != null;
	}

	/**
	 * Kopira vrijednosti iz jednog u drugi objekt tipa InspectionService
	 * 
	 * @param newValues
	 *            objekt tipa InspectionService koji sadrzi nove vrijednosti
	 */
	private void copyValues(InspectionService newValues) {
		this.name = newValues.name;
		this.inspectorateId = newValues.inspectorateId;
		this.jurisdictionId = newValues.jurisdictionId;
		this.personId = newValues.personId;
	}

}