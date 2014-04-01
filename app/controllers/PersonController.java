package controllers;

import play.mvc.*;
import play.data.*;
import models.*;



public class PersonController extends Controller {

	
	static Form<Person> personForm = Form.form(Person.class);
	static Form<Person> filledForm = personForm.bindFromRequest();
	
    public static Result index() {
        return ok(
        	views.html.Person.personIndex.render(Person.all(),filledForm)
        	);
    }

    public static Result create() {
    	return ok(
    			views.html.Person.personCreate.render(personForm)
    			);
    }
    
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

    public static Result deletePerson(Long id){
        Person.delete(id);
        cleanForms();
        return ok();
    }
    
    private static void refreshFilledForm(){
    	if( filledForm.equals(personForm.bindFromRequest()) == false 
    			&& personForm.bindFromRequest().data().isEmpty() == false ){
    		filledForm = personForm.bindFromRequest();
    		personForm.discardErrors();
    	}
    }
    
    private static void cleanForms(){
    	filledForm = Form.form(Person.class);
    	filledForm.discardErrors();
    	personForm = Form.form(Person.class);
    	personForm.discardErrors();
    }

}
