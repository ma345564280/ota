$(document).ready(function () {
	//加载用户信息
	queryProductList();
});
window.actionEvents = {
        'click .deleteProduct': function (e, value, row, index) {
        	var data = {
     				"id":row.id,
     			}
        	$.ajax({
        		type: "post",
        		url: "delete-product",
        		contentType : 'application/json',
        		data: JSON.stringify(data),
        		success: function(result) {
        			result = $.parseJSON(result);
        			if (result.code == 1) {
        				layer.msg('Delete Successfully!');
        				$('#product_table').bootstrapTable('refresh');
        			}
        		},
        		error: function(result, xhr) {
        			console.log(result);
        			console.log(xhr);
        		}
        	})
        },
        'click .editProduct': function (e, value, row, index) {
        	$('#editProductModal').modal('show');
        	$('#editProduct_id').val(row.id);
        	$('#editProduct_model').val(row.type);
        	$('#editProduct_pid').val(row.pid);
        	$('#editProduct_description').val(row.description);
        }
}

function queryProductList() {
    $('#product_table').bootstrapTable({
    	height:720,
    	search:true,
//    	showRefresh:true,
//    	showToggle:true,
    	showColumns:true,
        pagination: true,
        sidePagination: 'client',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
//        toolbar: $('#toolbar'),
        uniqueId: 'id',
        url: 'product-info',
    });
}

function productStatusFun(value,row,index) {
	//open是1 close是0
	if(value=="0") {
		return '<font color="red">Close</font>';
	}
	if(value=="1") {
		return '<font color="green">Open</font>';
	}
	return '-';
}

function productOperationFun(value,row,index) {
	return ['<button type="button" class="btn btn-info btn-sm editProduct" data-toggle="modal" data-target="editProductModal"><span class="glyphicon glyphicon glyphicon-edit"></span>Edit</button>',
	        '<button type="button" class="btn btn-danger btn-sm deleteProduct"><span class="glyphicon glyphicon glyphicon-trash"></span>Delete</button>'].join('');	
}

	
function submit_addProductClick() {
	if ($("#addProduct_model").val()) {
		var data = {
			"type":$('#addProduct_model').val(),
			"pid": $("#addProduct_pid").val(),
			"description": $('#addProduct_description').val(),
			"status": $('#addProduct_status').val()
		}
		//首先检查用户名是否重复
		$.ajax({
			type: "post",
			url: "check-newproduct",
			contentType : 'application/json',
			dataType:"json",
			data: JSON.stringify(data),
			success: function(result) {
				if (result.code != 1) {
					layer.msg('This icon has been existed', {icon: 5});
					$("#addProduct_model").focus();
				} else {
					$.ajax({
						type: "post",
						url: "add-product",
						contentType : 'application/json',
						data: JSON.stringify(data),
						success: function(result) {
							$('#addProductModal').modal('hide');
							layer.msg('Success!');
							$('#product_table').bootstrapTable('refresh');
						},
						error: function(result, xhr) {
							console.log(result);
							console.log(xhr);
							layer.msg('Error');
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
		layer.msg('Please fill your model!', {icon: 5});
	}
}

//点击编辑提交按钮
$("#editProduct_Submit").click(function() {
 	if ($("#editProduct_model").val()) {
 		var data = {
 				"id":$('#editProduct_id').val(),
 				"type":$('#editProduct_model').val(),
 				"pid": $("#editProduct_pid").val(),
 				"description": $('#editProduct_description').val(),
 				"status": $('#editProduct_status').val()
 			}
		$.ajax({
			type: "post",
			url: "update-product",
			contentType : 'application/json',
			data: JSON.stringify(data),
			success: function(result) {
				$('#editProductModal').modal('hide');
				layer.msg('Success!');
				$('#product_table').bootstrapTable('refresh');
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
				layer.msg('Error',{icon:2});
			}
		});
	} else {
			layer.msg('Please fill your product name!',{icon:2});
	}
});
