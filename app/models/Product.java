package models;

import java.io.UnsupportedEncodingException;
import java.util.*;

import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

@Entity
public class Product extends Model {
	@Id
	public Long id;
	
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String name;
	
	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String manufacturer;

	@Required
	@MinLength(value = 3)
	@MaxLength(value = 50)
	public String countryOfOrigin;
	
	//Optional values
	@MinLength(value = 3)
	@MaxLength(value = 20)
	public String serialNumber;
	
	@MinLength(value = 3)
	@MaxLength(value = 255)
	public String description;
	
	public static Finder<Long,Product> find = new Finder(
		    Long.class, Product.class
		  );
	
	public static List<Product> all() {
		  return find.all();
		}

		public static void create(Product product) {
		  product.save();
		}

		public static Product find(Long id){
			return find.byId(id);
		}
		
		public static void updateProduct(Product newValues) {
			Product product = find.byId(newValues.id);
			if(product != null){
				product.copyValues(newValues);
				product.update();
			}
		}

		public static void delete(Long id) {
		  find.ref(id).delete();
		}
		
		private  void copyValues(Product newValues){
			this.name = newValues.name;
			this.manufacturer = newValues.manufacturer;
			this.countryOfOrigin = newValues.countryOfOrigin;
			this.serialNumber = newValues.serialNumber;
			this.description = newValues.description;
		}
		
}
