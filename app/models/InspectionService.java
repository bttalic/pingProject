package models;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

import com.avaje.ebean.Query;

@Entity
public class InspectionService extends Model {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspection_service_id_seq")
	@Id
	public Long id;

	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String name;

	@Required
	public Long inspectorateId;

	@Required
	public Long jurisdictionId;

	@Required
	public Long personId;

	public Person contactPerson;
	public Jurisdiction jurisdiction;
	public Inspectorate inspectorate;

	public InspectionService(){
		String placeHolder = "Nije dostupno";
		name = placeHolder;
	}

	public static Finder<Long,InspectionService> find = new Finder(
		Long.class, InspectionService.class
		);

	public static List<InspectionService> all() {
		List<InspectionService> all = find.all();
		for(int i = 0; i<all.size(); i++){
			InspectionService current = all.get(i);
			Person thisPerson = Person.find(current.personId);
			if( thisPerson == null) {
				thisPerson = new Person();
			}
			current.contactPerson = thisPerson;
			current.jurisdiction = Jurisdiction.find(current.jurisdictionId);
			current.inspectorate = Inspectorate.find(current.inspectorateId);
		}
		return all;
	}

	public static Map allAsMap() {
		List<InspectionService> list = all();
		Map<String, String> hash = new HashMap();
		for(int i = 0; i<list.size(); i++){
			InspectionService current = list.get(i);
			hash.put(String.valueOf(current.id), current.name);
		}
		return hash;
	}

	public static InspectionService find(Long id){
		InspectionService thisService;
		if( id == null)
			thisService = new InspectionService();
		else
			thisService = find.byId(id);
		if( thisService == null)
			thisService = new InspectionService();
		
		loadAdditional(thisService);
		return thisService;
	}

	private static void loadAdditional(InspectionService current){
		current.contactPerson = Person.find(current.personId);
		current.jurisdiction = Jurisdiction.find(current.id);
		current.inspectorate = Inspectorate.find(current.id);
	}

	public static void create(InspectionService inspectionService) {
		inspectionService.save();
	}

	public static void updateInspectionService(InspectionService newValues) {
		InspectionService inspectionService = find.byId(newValues.id);
		if(inspectionService != null){
			inspectionService.copyValues(newValues);
			inspectionService.update();
		}
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static Map getInspectionServices(){
		Map<String, String> services = new HashMap();
		List<InspectionService> all = find.setDistinct(true).select("name, id").findList();
		services.put("-1", "Svi");
		for(int i=0; i<all.size(); i++){
			services.put(all.get(i).id.toString(), all.get(i).name);
		}
		return services;
	}

	public static boolean exists(Long id){
		return find.byId(id) != null;
	}
	
	private  void copyValues(InspectionService newValues){
		this.name = newValues.name;
		this.inspectorateId = newValues.inspectorateId;
		this.jurisdictionId = newValues.jurisdictionId;
		this.personId = newValues.personId;
	}

}