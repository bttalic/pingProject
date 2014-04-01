package controllers;

import java.util.Date;
import play.mvc.*;
import play.data.*;
import models.*;



public class InspectionController extends Controller {

	static Form<Inspection> inspectionForm = Form.form(Inspection.class);
	static Form<Inspection> filledForm = inspectionForm.bindFromRequest();
	static Form<Search> searchForm = Form.form(Search.class);
	
	public static Result index() {
		Form<Search> filledSearch = searchForm.bindFromRequest();
		if( filledSearch.data().isEmpty() == true && filledSearch.hasErrors() == false ) {
		return ok(
			views.html.Inspection.inspectionIndex.render(
				Inspection.all(), searchForm, Inspection.getInspectionDates(), InspectionService.getInspectionServices()
				)
			);
		} else {
			Date date = filledSearch.get().inspectionDate;
			Long serviceId = filledSearch.get().inspectionServiceId;
			
			return ok(
					views.html.Inspection.inspectionIndex.render(
						Inspection.filtered(date, serviceId),
						filledSearch, Inspection.getInspectionDates(), InspectionService.getInspectionServices()
						)
					);
		}
	}
	

	public static Result create() {
		return ok(
			views.html.Inspection.inspectionCreate.render(
				inspectionForm, InspectionService.allAsMap(), Product.allAsMap()
				)
			);
	}

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

	public static Result updateInspection() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return redirect(routes.InspectionController.update(inspectionForm.get().id));
		} else {
			Inspection.updateInspection(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionController.index());
		}
	}

	public static Result deleteInspection(Long id){
		Inspection.delete(id);
		cleanForms();
		return redirect(routes.InspectionController.index());
	}
	

	private static void refreshFilledForm(){
		if( filledForm.equals(inspectionForm.bindFromRequest()) == false 
			&& inspectionForm.bindFromRequest().data().isEmpty() == false ){
			filledForm = inspectionForm.bindFromRequest();
		inspectionForm.discardErrors();
	}
}

private static void cleanForms(){
	filledForm = Form.form(Inspection.class);
	filledForm.discardErrors();
	inspectionForm = Form.form(Inspection.class);
	inspectionForm.discardErrors();
}



}
