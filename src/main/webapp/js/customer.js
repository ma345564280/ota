$(document).ready(function () {
	querycustomerinfo();
	//querybusiness();
});


function querycustomerinfo() {
	 $('#customer_table').bootstrapTable({
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
	        url: 'customer-info',
	    });
}
function operationFun(value,row,index) {
	return ['<button type="button" class="btn btn-info btn-sm editCustomer" data-toggle="modal" data-target="editModal"><span class="glyphicon glyphicon glyphicon-edit"></span>Edit</button>',
	        '<button type="button" class="btn btn-danger btn-sm deleteCustomer"><span class="glyphicon glyphicon glyphicon-trash"></span>Delete</button>'].join('');	
}


window.operationEvents = {
        'click .deleteCustomer': function (e, value, row, index) {
        	var data = {
     				"id":row.id,
     			}
        	$.ajax({
        		type: "post",
        		url: "delete-customer",
        		contentType : 'application/json',
        		data: JSON.stringify(data),
        		success: function(result) {
        			result = $.parseJSON(result);
        			if (result.code == 1) {
        				layer.msg('Delete Successfully!');
        				$('#customer_table').bootstrapTable('refresh');
        			}
        		},
        		error: function(result, xhr) {
        			console.log(result);
        			console.log(xhr);
        		}
        	})
        },
        'click .editCustomer': function (e, value, row, index) {
        	$('#editModal').modal('show');
        	$("#editCustomer").val(row.name);
        	$("#editServerIP").val(row.serverip);
        	$("#customerid").val(row.id);
        }
}


//注册用户
$("#submit").click(function() {
	if ($("#addcustomer").val()) {
		var data = {
			"name": $("#addcustomer").val(),
			"serverip": $("#addserverip").val(),
			"nation": $("#addnation").val(),
		}
		//首先检查用户名是否重复
		$.ajax({
			type: "post",
			url: "check-newcustomer",
			contentType : 'application/json',
			dataType:"json",
			data: JSON.stringify(data),
			success: function(result) {
				if (result.code != 1) {
					alert("This customer is existed!");
					$("#addcustomer").focus();
				} else {
					$.ajax({
						type: "post",
						url: "add-customer",
						contentType : 'application/json',
						data: JSON.stringify(data),
						success: function(result) {
							$('#addModal').modal('hide');
							$('#customer_table').bootstrapTable('refresh');
						},
						error: function(result, xhr) {
							console.log(result);
							console.log(xhr);
							alert("ERROR");
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
			layer.alert("Please fill customer!",{icon:2})
	}
});
//当弹框隐藏后，清空input
$(".accountModal").on("hidden.bs.modal", function() {
	$(".accountModal input").val("");
});

//点击编辑提交按钮
$("#editSubmit").click(function() {
 	if ($("#editCustomer").val()) {
		var data = {
			"id": $("#customerid").val(),
			"name": $("#editCustomer").val(),
			"serverip": $("#editserverip").val(),
			"nation": $("#editnation").val(),
		}
		$.ajax({
			type: "post",
			url: "update-customer",
			contentType : 'application/json',
			data: JSON.stringify(data),
			success: function(result) {
				$('#editModal').modal('hide');
				$('#customer_table').bootstrapTable('refresh');
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
				alert("ERROR");
			}
		});
	} else {
			layer.msg("Please fill your customer!",{icon:2})
	}
});
