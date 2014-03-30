package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Person extends Model {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_id_seq")
	@Id
	public Long id;

	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String name;

	@Required
	@Pattern(value = "\\+387[1-9]{8}$")
	public String phoneNumber;

	@Required
	@Email
	@MaxLength(value = 50)
	public String email;

	@Pattern(value = "\\+387[1-9]{8}$")
	public String fax;

	public Person(){
		String placeHolder = "Nije dostupno";
		name = placeHolder;
		phoneNumber = placeHolder;
		email = placeHolder;
	}

	public static Finder<Long,Person> find = new Finder(
		Long.class, Person.class
		);

	public static List<Person> all() {
		return find.all();
	}

	public static Person find(Long id){
		if( id == null )
			return new Person();

		Person thisPerson = find.byId(id);
		if( thisPerson == null )
			thisPerson = new Person();
		return thisPerson;
	}

	public static Map allAsMap() {
		List<Person> list = all();
		Map<String, String> hash = new HashMap();
		for(int i = 0; i<list.size(); i++){
			Person current = list.get(i);
			hash.put(String.valueOf(current.id), current.name+" "+current.phoneNumber);
		}
		return hash;
	}

	public static void create(Person person) {
		person.save();
	}

	public static void updatePerson(Person newValues) {
		Person person = find.byId(newValues.id);
		if(person != null){
			person.copyValues(newValues);
			person.update();
		}
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static boolean exists(Long id){
		return find.byId(id) != null;
	}
	
	private  void copyValues(Person newValues){
		this.name = newValues.name;
		this.phoneNumber = newValues.phoneNumber;
		this.email = newValues.email;
		this.fax = newValues.fax;
	}

	

}