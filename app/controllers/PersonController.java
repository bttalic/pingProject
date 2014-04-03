package controllers;

import play.mvc.*;
import play.data.*;
import models.*;



/**
 * The Class PersonController.
 */
public class PersonController extends Controller {

	
	/** The person form. */
	static Form<Person> personForm = Form.form(Person.class);
	
	/** The filled form. */
	static Form<Person> filledForm = personForm.bindFromRequest();
	
    /**
     * Index.
     *
     * @return the result
     */
    public static Result index() {
        return ok(
        	views.html.Person.personIndex.render(Person.all(),filledForm)
        	);
    }

    /**
     * Creates the.
     *
     * @return the result
     */
    public static Result create() {
    	return ok(
    			views.html.Person.personCreate.render(personForm)
    			);
    }
    
    /**
     * New person.
     *
     * @return the result
     */
    public static Result newPerson() {
        refreshFilledForm();
    	if(filledForm.hasErrors()) {
    		return badRequest( views.html.Person.personCreate.render(filledForm));
		  } else {
			Person.create(filledForm.get());
			cleanForms();
		    return redirect(routes.PersonController.index());
		  }
    }

    /**
     * Update person.
     *
     * @return the result
     */
    public static Result updatePerson() {
        refreshFilledForm();
        if(filledForm.hasErrors()) {
            return redirect(routes.PersonController.index());
          } else {
            Person.updatePerson(filledForm.get());
            cleanForms();
            return redirect(routes.PersonController.index());
          }
    }

    /**
     * Delete person.
     *
     * @param id the id
     * @return the result
     */
    public static Result deletePerson(Long id){
        Person.delete(id);
        cleanForms();
        return ok();
    }
    
    /**
     * Refresh filled form.
     */
    private static void refreshFilledForm(){
    	if( filledForm.equals(personForm.bindFromRequest()) == false 
    			&& personForm.bindFromRequest().data().isEmpty() == false ){
    		filledForm = personForm.bindFromRequest();
    		personForm.discardErrors();
    	}
    }
    
    /**
     * Clean forms.
     */
    private static void cleanForms(){
    	filledForm = Form.form(Person.class);
    	filledForm.discardErrors();
    	personForm = Form.form(Person.class);
    	personForm.discardErrors();
    }

}
