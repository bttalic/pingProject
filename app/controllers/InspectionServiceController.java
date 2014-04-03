package controllers;

import play.mvc.*;
import play.data.*;
import models.*;



/**
 * The Class InspectionServiceController.
 */
public class InspectionServiceController extends Controller {

	
	/** The inspection service form. */
	static Form<InspectionService> inspectionServiceForm = Form.form(InspectionService.class);
	
	/** The filled form. */
	static Form<InspectionService> filledForm = inspectionServiceForm.bindFromRequest();
	
	/**
	 * Index.
	 *
	 * @return the result
	 */
	public static Result index() {
		return ok(
			views.html.InspectionService.inspectionServiceIndex.render(InspectionService.all())
			);
	}

	/**
	 * Creates the.
	 *
	 * @return the result
	 */
	public static Result create() {
		return ok(
			views.html.InspectionService.inspectionServiceCreate.render(
				inspectionServiceForm, Jurisdiction.allAsMap(), Inspectorate.allAsMap(), Person.allAsMap()
				)
			);
	}

	/**
	 * Update.
	 *
	 * @param id the id
	 * @return the result
	 */
	public static Result update(Long id) {
		if( InspectionService.exists(id) == true ){
			InspectionService thisService = new InspectionService(id);
			inspectionServiceForm = Form.form(InspectionService.class);
			inspectionServiceForm = inspectionServiceForm.fill(thisService);
			return ok (views.html.InspectionService.inspectionServiceUpdate.render(
				inspectionServiceForm, Jurisdiction.allAsMap(), Inspectorate.allAsMap(), Person.allAsMap(), thisService
				));
		} else {
			return redirect(routes.InspectionServiceController.index());
		}
	}
	
	/**
	 * New inspection service.
	 *
	 * @return the result
	 */
	public static Result newInspectionService() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return badRequest( views.html.InspectionService.inspectionServiceCreate.render(
				filledForm, Jurisdiction.allAsMap(), Inspectorate.allAsMap(), Person.allAsMap()
				));
		} else {
			InspectionService.create(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionServiceController.index());
		}
	}

	/**
	 * Update inspection service.
	 *
	 * @return the result
	 */
	public static Result updateInspectionService() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return redirect(routes.InspectionServiceController.update(inspectionServiceForm.get().getId()));
		} else {
			InspectionService.updateInspectionService(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionServiceController.index());
		}
	}

	/**
	 * Delete inspection service.
	 *
	 * @param id the id
	 * @return the result
	 */
	public static Result deleteInspectionService(Long id){
		InspectionService.delete(id);
		cleanForms();
		return redirect(routes.InspectionServiceController.index());
	}
	
	/**
	 * Refresh filled form.
	 */
	private static void refreshFilledForm(){
		if( filledForm.equals(inspectionServiceForm.bindFromRequest()) == false 
			&& inspectionServiceForm.bindFromRequest().data().isEmpty() == false ){
			filledForm = inspectionServiceForm.bindFromRequest();
		inspectionServiceForm.discardErrors();
	}
}

/**
 * Clean forms.
 */
private static void cleanForms(){
	filledForm = Form.form(InspectionService.class);
	filledForm.discardErrors();
	inspectionServiceForm = Form.form(InspectionService.class);
	inspectionServiceForm.discardErrors();
}

}
