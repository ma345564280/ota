$(document).ready(function () {
	queryroleinfo();
});

function queryroleinfo() {
	 $('#role_table').bootstrapTable({
	    	height:720,
	    	search:true,
//	    	showRefresh:true,
//	    	showToggle:true,
	    	showColumns:true,
	        pagination: true,
	        sidePagination: 'client',
	        pageNumber: 1,
	        pageSize: 20,
	        pageList: [10, 20, 30, 50],
//	        toolbar: $('#toolbar'),
	        uniqueId: 'id',
	        url: 'role-info',
	    });
}
function OperationFun(value,row,index) {
	return ['<button type="button" class="btn btn-info btn-sm editRole"><span class="glyphicon glyphicon glyphicon-edit"></span>Edit</button>',
	        '<button type="button" class="btn btn-danger btn-sm deleteRole"><span class="glyphicon glyphicon glyphicon-trash"></span>Delete</button>'].join('');	
}

function Reform(value,row,index) {
	return value;
}
window.actionEvents = {
        'click .deleteRole': function (e, value, row, index) {
        	var data = {
     				"role":row.role,
     			}
        	$.ajax({
        		type: "post",
        		url: "deleteRole",
        		contentType : 'application/json',
        		data: JSON.stringify(data),
        		success: function(result) {
        			result = $.parseJSON(result);
        			if (result.code == 1) {
        				layer.msg('Delete Successfully!');
        				$('#role_table').bootstrapTable('refresh');
        			} else if(result.code == 0){
        				layer.alert('Can not delete this role because this role is occupied by some user!',{icon:2});
        			}
        		},
        		error: function(result, xhr) {
        			console.log(result);
        			console.log(xhr);
        		}
        	})
        },
        'click .editRole': function (e, value, row, index) {
        	$('#editRoleModal').modal('show');
        	$('#editRole').val(row.role);
        	$('#editRole').attr("disabled",true);
        }
}

$("#submit").click(function() {
	
	var authority1 = $("input[name='authority1']:checked").val();
	var authority2 = $("input[name='authority2']:checked").val();
	var authority3 = $("input[name='authority3']:checked").val();
	
	if ($("#role").val()) {
		var data = {
			"role": $("#role").val(),
			"authority1": authority1,
			"authority2": authority2,
			"authority3": authority3,
		}
		//首先检查用户名是否重复
		$.ajax({
			type: "post",
			url: "check-role",
			contentType : 'application/json',
			dataType:"json",
			data: JSON.stringify(data),
			success: function(result) {
				if (result.code != 1) {
					layer.alert("This role is existed!");
					$("#role").focus();
				} else {
					$.ajax({
						type: "post",
						url: "add-role",
						contentType : 'application/json',
						data: JSON.stringify(data),
						success: function(result) {
							layer.alert("Success!");
							$('#addRoleModal').modal('hide');
							$('#role_table').bootstrapTable('refresh');
						},
						error: function(result, xhr) {
							console.log(result);
							console.log(xhr);
							layer.alert("ERROR");
						}
					});
				}
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
			}
		});
	} else {
		layer.alert("Please complete this form!",{icon:2})
	}
});

$("#editSubmit").click(function() {

	var authority1 = $("input[name='editauthority1']:checked").val();
	var authority2 = $("input[name='editauthority2']:checked").val();
	var authority3 = $("input[name='editauthority3']:checked").val();
	
	if ($("#editRole").val()) {
		var data = {
			"role": $("#editRole").val(),
			"authority1": authority1,
			"authority2": authority2,
			"authority3": authority3,
		}
		$.ajax({
			type: "post",
			url: "edit-role",
			contentType : 'application/json',
			data: JSON.stringify(data),
			success: function(result) {
				layer.alert("Success!");
				$('#editRoleModal').modal('hide');
				$('#role_table').bootstrapTable('refresh');
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
				layer.alert("ERROR");
			}
		});
	} else {
		layer.alert("Please complete this form!",{icon:2})
	}
});
