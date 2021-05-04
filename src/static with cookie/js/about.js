$(function() {

	// Handle the switch between the tabs:
	$("#cookie-button").click(function(){
		if (!($(this).hasClass("active"))){
			$(this).addClass("active")
			$("#privacy-button").removeClass("active")
			$("#privacy-policy").addClass("hidden")
			$("#cookie-policy").removeClass("hidden")
		}
	})

	$("#privacy-button").click(function(){
		if (!($(this).hasClass("active"))){
			$(this).addClass("active")
			$("#cookie-button").removeClass("active")
			$("#privacy-policy").removeClass("hidden")
			$("#cookie-policy").addClass("hidden")			
		}
	})

})