package controllers;

import java.util.Date;
import play.mvc.*;
import play.data.*;
import models.*;



/**
 * The Class InspectionController.
 */
public class InspectionController extends Controller {

	/** The inspection form. */
	static Form<Inspection> inspectionForm = Form.form(Inspection.class);
	
	/** The filled form. */
	static Form<Inspection> filledForm = inspectionForm.bindFromRequest();
	
	/** The search form. */
	static Form<Search> searchForm = Form.form(Search.class);
	
	/**
	 * Index.
	 *
	 * @return the result
	 */
	public static Result index() {
		Form<Search> filledSearch = searchForm.bindFromRequest();
		if( filledSearch.data().isEmpty() == true && filledSearch.hasErrors() == false ) {
		return ok(
			views.html.Inspection.inspectionIndex.render(
				Inspection.all(), searchForm, Inspection.getInspectionDates(), InspectionService.getInspectionServices()
				)
			);
		} else {
			Date dateStart = filledSearch.get().inspectionDateStart;
			Date dateEnd = filledSearch.get().inspectionDateEnd;
			Long serviceId = filledSearch.get().inspectionServiceId;
			
			return ok(
					views.html.Inspection.inspectionIndex.render(
						Inspection.filtered(dateStart, dateEnd, serviceId),
						filledSearch, Inspection.getInspectionDates(), InspectionService.getInspectionServices()
						)
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
			views.html.Inspection.inspectionCreate.render(
				inspectionForm, InspectionService.allAsMap(), Product.allAsMap()
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
		if( Inspection.exists(id) == true ){
			Inspection thisInspection = Inspection.find(id);
			inspectionForm = Form.form(Inspection.class);
			inspectionForm = inspectionForm.fill(thisInspection);
			return ok (views.html.Inspection.inspectionUpdate.render(
				inspectionForm, InspectionService.allAsMap(), Product.allAsMap(), thisInspection
				));
		} else {
			return redirect(routes.InspectionController.index());
		}
	}
	
	/**
	 * New inspection.
	 *
	 * @return the result
	 */
	public static Result newInspection() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return badRequest( views.html.Inspection.inspectionCreate.render(
				filledForm, InspectionService.allAsMap(), Product.allAsMap()
				));
		} else {
			Inspection.create(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionController.index());
		}
	}

	/**
	 * Update inspection.
	 *
	 * @return the result
	 */
	public static Result updateInspection() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return redirect(routes.InspectionController.update(inspectionForm.get().getId()));
		} else {
			Inspection.updateInspection(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionController.index());
		}
	}

	/**
	 * Delete inspection.
	 *
	 * @param id the id
	 * @return the result
	 */
	public static Result deleteInspection(Long id){
		Inspection.delete(id);
		cleanForms();
		return redirect(routes.InspectionController.index());
	}
	

	/**
	 * Refresh filled form.
	 */
	private static void refreshFilledForm(){
		if( filledForm.equals(inspectionForm.bindFromRequest()) == false 
			&& inspectionForm.bindFromRequest().data().isEmpty() == false ){
			filledForm = inspectionForm.bindFromRequest();
		inspectionForm.discardErrors();
	}
}

/**
 * Clean forms.
 */
private static void cleanForms(){
	filledForm = Form.form(Inspection.class);
	filledForm.discardErrors();
	inspectionForm = Form.form(Inspection.class);
	inspectionForm.discardErrors();
}



}
