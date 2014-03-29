$(document).ready(function(){

	$('#updateForm input').not('.hidden').each(function(index, element){
		if( !$(this).val() ){
			$('.help-inline').addClass('hidden');
			return;
		}
	});


	$('tr').click(function(event){
		$('#updateForm').parent().parent().removeClass('hidden');
		$('#updateForm .help-inline').addClass('hidden');
		var productValues = [];

		$('#updateForm input.hidden').val($(this).attr("objectId"));
		$(this).children("td").each(function(){
			productValues.push({
				name: $(this).attr("field"),
				value: $(this).text().replace(/^\s+|\s+$/g,'')
			});
		});

		for(var i = 0; i<productValues.length; i++){
			$('#updateForm [name="'+productValues[i].name+'"]').val(productValues[i].value);
		}
	});


	$('#saveChanges').click(function(){
		$('#updateForm').submit();
	});

	$('#deleteProduct').click(function(){
		deleteObject("product/INSERTID/delete");
	});

	$('#deletePerson').click(function(){
		deleteObject("person/INSERTID/delete");
	});
});


function deleteObject(url){
	var objectId = $('#updateForm input.hidden').val();
	url = url.replace("INSERTID",objectId);
	$.ajax({
		method: "POST",
		url: url,
		success: function(response) {
			$('tr[objectId="'+objectId+'"]').remove();
			$('#updateForm').trigger('reset');
		}
	});
}