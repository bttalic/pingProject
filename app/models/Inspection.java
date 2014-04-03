package models;

import java.text.SimpleDateFormat;
import java.util.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;
import javax.persistence.*;

/**
 * Inspection
 * 
 * klasa sluzi kao model za entity inspekcijska kontrola
 * 
 * 
 * $LastChangedRevision: 01.04.2014 $LastChangedDate: 03.04.2014
 */

@SuppressWarnings("serial")
@Entity
public class Inspection extends Model {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_id_seq")
	private Long id;

	@Required
	private Date inspectionDate;

	@Required
	private Long inspectionServiceId;

	@Required
	private Long productId;

	@Required
	private String inspectionResult;

	@Required
	private boolean safe;

	public Product product;
	public InspectionService inspectionService;

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
	 * @return the inspectionDate
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}

	/**
	 * @param inspectionDate
	 *            the inspectionDate to set
	 */
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	/**
	 * @return the inspectionServiceId
	 */
	public Long getInspectionServiceId() {
		return inspectionServiceId;
	}

	/**
	 * @param inspectionServiceId
	 *            the inspectionServiceId to set
	 */
	public void setInspectionServiceId(Long inspectionServiceId) {
		this.inspectionServiceId = inspectionServiceId;
	}

	/**
	 * @return the productId
	 */
	public Long getProductId() {
		return productId;
	}

	/**
	 * @param productId
	 *            the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * @return the inspectionResult
	 */
	public String getInspectionResult() {
		return inspectionResult;
	}

	/**
	 * @param inspectionResult
	 *            the inspectionResult to set
	 */
	public void setInspectionResult(String inspectionResult) {
		this.inspectionResult = inspectionResult;
	}

	/**
	 * @return the find
	 */
	public static Finder<Long, Inspection> getFind() {
		return find;
	}

	/**
	 * @param safe
	 *            the safe to set
	 */
	public void setSafe(boolean safe) {
		this.safe = safe;
	}

	/**
	 * Varijabla sluzi kao konektor za bazu
	 */
	private static Finder<Long, Inspection> find = new Finder<Long, Inspection>(
			Long.class, Inspection.class);

	/**
	 * 
	 * @return Listu kontrola iz baze poredanu po datumu inspekcije manji ka
	 *         vecem
	 */
	public static List<Inspection> all() {
		List<Inspection> all = find.order("inspection_date asc").findList();
		for (int i = 0; i < all.size(); i++) {
			Inspection current = all.get(i);
			loadAdditional(current);
		}
		return all;
	}

	/**
	 * 
	 * @param id
	 *            id kontrole
	 * @return instancu objekta
	 */
	public static Inspection getInspection(Long id) {
		Inspection thisInspection = find.byId(id);
		loadAdditional(thisInspection);
		return thisInspection;
	}

	/**
	 * Da bi sprijecili probleme sa brisanjem podataka ova klasa ne drzi
	 * direktno instance druge klase nego id vrijednosti preko kojih se u ovoj
	 * metodi te instance kreiraju
	 * 
	 * @param current
	 *            instanca klase
	 */
	@SuppressWarnings("unused")
	private static void loadAdditional(Inspection current) {
		InspectionService thisSevice = new InspectionService(
				current.inspectionServiceId);
		Product thisProduct = new Product(current.productId);
		if (thisSevice == null) {
			thisSevice = new InspectionService();
		}
		current.inspectionService = thisSevice;
		current.product = thisProduct;
	}

	/**
	 * Spasava inspekcijsku kontorlu u bazu
	 * 
	 * @param inspectionService
	 */
	public static void create(Inspection inspection) {
		inspection.save();
	}

	/**
	 * Vraca datum inspekcijske kontrole formatiran u obliku 31.12.2014
	 * 
	 * @return string formatirani datum kontrole
	 */
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(this.inspectionDate);
	}

	/**
	 * 
	 * @return HashMap datuma svih inspekcijskih kontrola
	 */
	public static Map<String, String> getInspectionDates() {

		Map<String, String> dates = new HashMap<String, String>();
		List<Inspection> all = find.setDistinct(true).select("inspection_date")
				.findList();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dates.put("0001-01-01", "Svi");
		for (int i = 0; i < all.size(); i++) {
			dates.put(df.format(all.get(i).getInspectionDate()),
					sdf.format(all.get(i).getInspectionDate()));
		}
		return dates;
	}

	/**
	 * 
	 * @return Vraca vrijednost "Siguran" ako je proizvod siguran ili
	 *         "Nesiguran" u suprontom slucaju
	 */
	public String isSafe() {
		return this.safe == true ? "Siguran" : "Nesiguran";
	}

	/**
	 * Na osnovu toga da li je tijelo sigurno vraca se ime klase za div u kojem
	 * su podatci o kontroli
	 * 
	 * @return string koji predstavlja ime css klase
	 */
	public String getCSSClass() {
		return this.safe == true ? "primary" : "danger";
	}

	public static Inspection find(Long id) {
		return find.byId(id);
	}

	/**
	 * Spasava promjene u bazu
	 * 
	 * @param newValues
	 *            Objekt tipa Inspection u kojem su nove vrijednosti
	 */
	public static void updateInspection(Inspection newValues) {
		Inspection inspection = find.byId(newValues.id);
		if (inspection != null) {
			inspection.copyValues(newValues);
			inspection.update();
		}
	}

	/**
	 * Brise inspekcijsku kontrolu iz baze
	 * 
	 * @param id
	 *            id kontrole
	 */
	public static void delete(Long id) {
		find.ref(id).delete();
	}

	/**
	 * 
	 * @param id
	 *            id koji treba provjeriti
	 * @return true ako kontrola postoji u bazi, u suprotnom false
	 */
	public static boolean exists(Long id) {
		return find.byId(id) != null;
	}

	/**
	 * Vraca filtriranu listu objekata tipa Inspection za izabrano inspekcijsko
	 * za izabrani vremenski period sortiran po datumu kontrole
	 * 
	 * @param dateStart
	 *            datum kontrole pocetak intervala
	 * @param dateEnd
	 *            datum kontrole kraj intervala
	 * @param service_id
	 *            id inspekcijskog tijela
	 * @return Lista kontrola filtrirana po datumu
	 */
	public static List<Inspection> filtered(Date dateStart, Date dateEnd,
			Long service_id) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Inspection> filtered;
		List<Inspection> dates = find.order("inspection_date asc").findList();
		if (df.format(dateStart).equals("0001-01-01")) {
			dateStart = dates.get(0).inspectionDate;
		}
		if (df.format(dateEnd).equals("0001-01-01")) {
			dateEnd = dates.get(dates.size() - 1).inspectionDate;
		}

		if (service_id != -1) {
			filtered = find.where()
					.between("inspection_date", dateStart, dateEnd)
					.eq("inspectionServiceId", service_id)
					.orderBy("inspection_date asc").findList();
		} else {
			filtered = find.where()
					.between("inspection_date", dateStart, dateEnd)
					.orderBy("inspection_date asc").findList();
		}
		for (int i = 0; i < filtered.size(); i++) {
			Inspection current = filtered.get(i);
			loadAdditional(current);
		}
		return filtered;

	}

	/**
	 * Kopira vrijednosti iz jednog u drugi objekt tipa Inspection
	 * 
	 * @param newValues
	 *            objekt tipa Inspection koji sadrzi nove vrijednosti
	 */
	private void copyValues(Inspection newValues) {
		this.inspectionDate = newValues.inspectionDate;
		this.inspectionServiceId = newValues.inspectionServiceId;
		this.productId = newValues.productId;
		this.inspectionResult = newValues.inspectionResult;
		this.safe = newValues.safe;
	}

}
