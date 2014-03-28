$(document).ready(function(){

	$('#updateForm input').not('.hidden1').each(function(index, element){
		if( !$(this).val() ){
			$('.help-inline').addClass('hidden');
			return;
		}
	});


	$('#productIndex tr').click(function(event){
		$('#updateForm .help-inline').addClass('hidden');
		var productValues = [];

		$('#updateForm input.hidden1').val($(this).attr("productId"));
		$(this).children("td").each(function(){
			productValues.push($(this).text().replace(/^\s+|\s+$/g,''));
		});
		var globalIndex = 0;
		$('#updateForm input').not('.hidden1').each(function(index, element){
			globalIndex = index;
			if( index < productValues.length )
			$(this).val(productValues[index]);
		});

		if( globalIndex < productValues.length )
			$('#updateForm textarea').val(productValues[globalIndex]);
	});


	$('#saveChanges').click(function(){
		$('#updateForm').submit();
	});

	/*$('#deleteProduct').click(function(){

		//$('#updateForm').submit();
		
		/*var url = $('#updateForm').attr("action");
		var data = $('#updateForm').serialize();
		$.ajax({
			method: "POST",
			url: url,
			data: data,

			success: function(response) {
				console.log(response);
			},
			error: function(response) {
				console.log(response);
			}
		});
	});*/

});