$("#login").click(function(){
	data = {
			"username" : $.trim($("#account").val()),
			"password" : $.trim($("#password").val())
	}
	$.ajax({
		type: "post",
		url: "checkin",
		contentType : "application/json",
		data:JSON.stringify(data),
		dataType: "json",
		success: function(data) {
			if (data.msgcode != 1) {
				alert("The account or password is wrong!");
			} else {
				sessionStorage.setItem("role", data.role);
				window.location.href = data.href;
			}
		},
		error: function(result, xhr) {
			console.log(result);
			console.log(xhr);
		}
	});
	return false;
});
