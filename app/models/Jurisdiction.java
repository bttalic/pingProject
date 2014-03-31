package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Jurisdiction extends Model {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jurisdiction_id_seq")
	@Id
	public Long id;

	@Required
	@MinLength(value = 2)
	@MaxLength(value = 16)
	public String name;

	public Jurisdiction(){
		String placeHolder = "Nije dostupno";
		name = placeHolder;
	}

	public static Finder<Long,Jurisdiction> find = new Finder(
		Long.class, Jurisdiction.class
		);

	public static List<Jurisdiction> all() {
		return find.all();
	}

	public static Jurisdiction find(Long id){
		if( id == null )
			return new Jurisdiction();

		Jurisdiction thisJurisdiction;
		if( exists(id) == true )
			thisJurisdiction = find.byId(id);
		else
			thisJurisdiction = new Jurisdiction();
		return thisJurisdiction;
	}

	public static boolean exists(Long id){
		return find.byId(id) != null;
	}

	public static Map allAsMap() {
		List<Jurisdiction> list = all();
		Map<String, String> hash = new HashMap();
		for(int i = 0; i<list.size(); i++){
			hash.put(String.valueOf(list.get(i).id), list.get(i).name);
		}
		return hash;
	}
}