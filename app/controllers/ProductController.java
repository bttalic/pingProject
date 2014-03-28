package controllers;


import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;
import play.mvc.*;

import views.html.*;

public class ProductController extends Controller {
	
	static Form<Product> productForm = Form.form(Product.class);
	static Form<Product> filledForm = productForm.bindFromRequest();
    public static Result index() {
    	refreshFilledForm();
    	if(filledForm != null)
    	return	badRequest(
    	        	views.html.Product.index.render(Product.all(),filledForm)
    	        	);
    	else {
    		cleanForms();
        return ok(
        	views.html.Product.index.render(Product.all(),productForm)
        	);
    	}
    }
    
    public static Result create() {
    	return ok(
    			views.html.Product.create.render(productForm)
    			);
    }
    
    public static Result newProduct() {
    	refreshFilledForm();
    	if(filledForm.hasErrors()) {
    		return badRequest( views.html.Product.create.render(filledForm));
		  } else {
			Product.create(filledForm.get());
			cleanForms();
		    return redirect(routes.ProductController.index());
		  }
    }
    
    public static Result updateProduct() {
    	refreshFilledForm();
    	if(filledForm.hasErrors()) {
    		return redirect(routes.ProductController.index());
		  } else {
			Product.update(filledForm.get());
			cleanForms();
			return redirect(routes.ProductController.index());
		  }
    }
    
    private static void refreshFilledForm(){
    	if( filledForm.equals(productForm.bindFromRequest()) == false 
    			&& productForm.bindFromRequest().data().isEmpty() == false ){
    		filledForm = productForm.bindFromRequest();
    		productForm.discardErrors();
    	}
    }
    
    private static void cleanForms(){
    	filledForm = Form.form(Product.class);
    	filledForm.discardErrors();
    	productForm = Form.form(Product.class);
    	productForm.discardErrors();
    }
    

}
