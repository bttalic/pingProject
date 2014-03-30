package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Inspectorate extends Model {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inspectorate_id_seq")
	@Id
	public Long id;

	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String name;

	public Inspectorate(){
		String placeHolder = "Nije dostupno";
		name = placeHolder;
	}

	public static Finder<Long,Inspectorate> find = new Finder(
		Long.class, Inspectorate.class
		);

	public static List<Inspectorate> all() {
		return find.all();
	}

	public static Inspectorate find(Long id){
		if( id == null )
			return new Inspectorate();

		Inspectorate thisInspectorate = find.byId(id);
		if( thisInspectorate == null )
			thisInspectorate = new Inspectorate();
		return thisInspectorate;
	}

	public static boolean exists(Long id){
		return find.byId(id) != null;
	}

	public static Map allAsMap() {
		List<Inspectorate> list = all();
		Map<String, String> hash = new HashMap();
		for(int i = 0; i<list.size(); i++){
			hash.put(String.valueOf(list.get(i).id), list.get(i).name);
		}
		return hash;
	}
}