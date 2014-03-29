$(document).ready(function(){
	$('.panel').click(function(){
		$(this).children('.panel-body').toggleClass('hidden');
	});
});