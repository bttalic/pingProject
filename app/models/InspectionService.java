package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class InspectionService extends Model {

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

	public static InspectionService find(Long id){
		return find.byId(id);
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
	
	private  void copyValues(InspectionService newValues){
		this.name = newValues.name;
		this.inspectorateId = newValues.inspectorateId;
		this.jurisdictionId = newValues.jurisdictionId;
		this.personId = newValues.personId;
	}

}