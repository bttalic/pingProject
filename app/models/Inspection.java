package models;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;


@Entity
public class Inspection extends Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_id_seq")
	public Long id;

	@Required
	public Date inspectionDate;

	@Required
	public Long inspectionServiceId;

	@Required
	public Long productId;

	@Required
	public String inspectionResult;

	@Required
	public boolean safe;

	public Product product;
	public InspectionService inspectionService;
	
	public static Finder<Long,Inspection> find = new Finder(
		Long.class, Inspection.class
		);
	
	public static List<Inspection> all() {
		List<Inspection> all = find.all();
		for(int i = 0; i<all.size(); i++){
			Inspection current = all.get(i);
			loadAdditional(current);
		}
		return all;
	}

	public static Inspection getInspection(Long id){
		Inspection thisInspection = find.byId(id);
		loadAdditional(thisInspection);
		return thisInspection;
	}

	private static void loadAdditional(Inspection current){
		InspectionService thisSevice = InspectionService.find(current.inspectionServiceId);
			Product thisProduct = Product.find(current.productId);
			if( thisSevice == null) {
				thisSevice = new InspectionService();
			}
			if( thisProduct == null) {
				thisProduct = new Product();
			}
			current.inspectionService = thisSevice;
			current.product = thisProduct;
	}

	public static void create(Inspection inspection) {
		inspection.save();
	}
	
	public String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		return sdf.format(this.inspectionDate);
	}

	public String getDayDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(new Date());
	}
	
	public static Map getInspectionDates() {
		
		Map<String, String> dates = new HashMap();
		List<Inspection> all = find.setDistinct(true).select("inspection_date").findList();
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		dates.put("0001-01-01", "Svi");
		for(int i=0; i<all.size(); i++){
			dates.put(df.format(all.get(i).inspectionDate), sdf.format(all.get(i).inspectionDate));
		}
		return dates;
	}
	
	public String isSafe(){
		return this.safe == true ? "Siguran" : "Nesiguran";
	}
	
	public String getCSSClass() {
		return this.safe == true ? "primary" : "danger";
	}

	public static Inspection find(Long id){
		return find.byId(id);
	}

	public static void updateInspection(Inspection newValues) {
		Inspection inspection = find.byId(newValues.id);
		if(inspection != null){
			inspection.copyValues(newValues);
			inspection.update();
		}
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static boolean exists(Long id){
		return find.byId(id) != null;
	}
	
	public static List<Inspection> filtered(Date date, Long service_id ){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		List<Inspection> filtered;
		if ( df.format(date).equals("0001-01-01") == false && service_id != -1 ){
		filtered = find.where()
				.eq("inspectionDate", date)
				.eq("inspectionServiceId", service_id)
				.orderBy("inspection_date asc")
				.findList();
		} else if ( df.format(date).equals("0001-01-01") && service_id != -1 ) {
			filtered = find.where()
					.eq("inspectionServiceId", service_id)
					.orderBy("inspection_date asc")
					.findList();
		} else if ( df.format(date).equals("0001-01-01") && service_id == -1 ){
			filtered = find.where()
					.orderBy("inspection_date asc")
					.findList();
		} else if ( df.format(date).equals("0001-01-01") == false && service_id == -1 ) {
			filtered = find.where()
					.eq("inspectionDate", date)
					.orderBy("inspection_date asc")
					.findList();
		} else {
			filtered = find.where().orderBy("inspection_date asc").findList();
		}
		for(int i = 0; i<filtered.size(); i++){
			Inspection current = filtered.get(i);
			loadAdditional(current);
		}
		return filtered;
		
	}

	private  void copyValues(Inspection newValues){
		this.inspectionDate = newValues.inspectionDate;
		this.inspectionServiceId = newValues.inspectionServiceId;
		this.productId = newValues.productId;
		this.inspectionResult = newValues.inspectionResult;
		this.safe = newValues.safe;
	}

}
