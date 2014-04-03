package controllers;


import play.mvc.*;
import play.data.*;
import models.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductController.
 */
public class ProductController extends Controller {
	
	/** The product form. */
	static Form<Product> productForm = Form.form(Product.class);
	
	/** The filled form. */
	static Form<Product> filledForm = productForm.bindFromRequest();
    
    /**
     * Index.
     *
     * @return the result
     */
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
    
    /**
     * Creates the.
     *
     * @return the result
     */
    public static Result create() {
    	return ok(
    			views.html.Product.productCreate.render(productForm)
    			);
    }
    
    /**
     * New product.
     *
     * @return the result
     */
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
    
    /**
     * Update product.
     *
     * @return the result
     */
    public static Result updateProduct() {
    	refreshFilledForm();
    	if(filledForm.hasErrors() ) {
    		return redirect(routes.ProductController.index());
		  } else {
			Product.updateProduct(filledForm.get());
			cleanForms();
			return redirect(routes.ProductController.index());
		  }
    }
    
    /**
     * Delete product.
     *
     * @param id the id
     * @return the result
     */
    public static Result deleteProduct(Long id){
    	Product.delete(id);
    	cleanForms();
    	return ok();
    }
    
    /**
     * Refresh filled form.
     */
    private static void refreshFilledForm(){
    	if( filledForm.equals(productForm.bindFromRequest()) == false 
    			&& productForm.bindFromRequest().data().isEmpty() == false ){
    		filledForm = productForm.bindFromRequest();
    		productForm.discardErrors();
    	}
    }
    
    /**
     * Clean forms.
     */
    private static void cleanForms(){
    	filledForm = Form.form(Product.class);
    	filledForm.discardErrors();
    	productForm = Form.form(Product.class);
    	productForm.discardErrors();
    }
    

}
