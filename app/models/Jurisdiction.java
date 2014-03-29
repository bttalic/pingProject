package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Jurisdiction extends Model {
	@Id
	public Long id;

	@Required
	@MinLength(value = 2)
	@MaxLength(value = 16)
	public String name;

	public static Finder<Long,Jurisdiction> find = new Finder(
		Long.class, Jurisdiction.class
		);

	public static List<Jurisdiction> all() {
		return find.all();
	}

	public static Jurisdiction find(Long id){
		return find.byId(id);
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