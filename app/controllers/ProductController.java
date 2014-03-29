package controllers;


import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;

public class ProductController extends Controller {
	
	static Form<Product> productForm = Form.form(Product.class);
	static Form<Product> filledForm = productForm.bindFromRequest();
    
    public static Result index() {
    	refreshFilledForm();
    	if(filledForm != null)
    	return	badRequest(
    	        	views.html.Product.productIndex.render(Product.all(),filledForm)
    	        	);
    	else {
    		cleanForms();
        return ok(
        	views.html.Product.productIndex.render(Product.all(),productForm)
        	);
    	}
    }
    
    public static Result create() {
    	return ok(
    			views.html.Product.productCreate.render(productForm)
    			);
    }
    
    public static Result newProduct() {
    	refreshFilledForm();
    	if(filledForm.hasErrors()) {
    		return badRequest( views.html.Product.productCreate.render(filledForm));
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
			Product.updateProduct(filledForm.get());
			cleanForms();
			return redirect(routes.ProductController.index());
		  }
    }
    
    public static Result deleteProduct(Long id){
    	Product.delete(id);
    	cleanForms();
    	return ok();
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
