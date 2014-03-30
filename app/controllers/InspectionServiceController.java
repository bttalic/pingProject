package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import models.*;
import views.html.*;



public class InspectionServiceController extends Controller {

	
	static Form<InspectionService> inspectionServiceForm = Form.form(InspectionService.class);
	static Form<InspectionService> filledForm = inspectionServiceForm.bindFromRequest();
	
	public static Result index() {
		return ok(
			views.html.InspectionService.inspectionServiceIndex.render(InspectionService.all())
			);
	}

	public static Result create() {
		return ok(
			views.html.InspectionService.inspectionServiceCreate.render(
				inspectionServiceForm, Jurisdiction.allAsMap(), Inspectorate.allAsMap(), Person.allAsMap()
				)
			);
	}

	public static Result update(Long id) {
		if( InspectionService.exists(id) == true ){
			InspectionService thisService = InspectionService.find(id);
			inspectionServiceForm = Form.form(InspectionService.class);
			inspectionServiceForm = inspectionServiceForm.fill(thisService);
			return ok (views.html.InspectionService.inspectionServiceUpdate.render(
				inspectionServiceForm, Jurisdiction.allAsMap(), Inspectorate.allAsMap(), Person.allAsMap(), thisService
				));
		} else {
			return redirect(routes.InspectionServiceController.index());
		}
	}
	
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

	public static Result updateInspectionService() {
		refreshFilledForm();
		if(filledForm.hasErrors()) {
			return redirect(routes.InspectionServiceController.update(inspectionServiceForm.get().id));
		} else {
			InspectionService.updateInspectionService(filledForm.get());
			cleanForms();
			return redirect(routes.InspectionServiceController.index());
		}
	}

	public static Result deleteInspectionService(Long id){
		InspectionService.delete(id);
		cleanForms();
		return redirect(routes.InspectionServiceController.index());
	}
	
	private static void refreshFilledForm(){
		if( filledForm.equals(inspectionServiceForm.bindFromRequest()) == false 
			&& inspectionServiceForm.bindFromRequest().data().isEmpty() == false ){
			filledForm = inspectionServiceForm.bindFromRequest();
		inspectionServiceForm.discardErrors();
	}
}

private static void cleanForms(){
	filledForm = Form.form(InspectionService.class);
	filledForm.discardErrors();
	inspectionServiceForm = Form.form(InspectionService.class);
	inspectionServiceForm.discardErrors();
}

}
