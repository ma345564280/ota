$(document).ready(function () {
	//加载用户信息
	queryGroupList();
	queryCustomerList();
	$('#addGroup_selectCustomer').on('change', selectSortOnchange);
});

//$('#editGroupModal').on('shown.bs.modal', function () {
//	edit_getAvailableSortList();
//})


function selectSortOnchange(){
	getAvailableSortList();
}

function getAvailableSortList() {
	var data={
		"customer":$('#addGroup_selectCustomer').val()
	}
	$.ajax({
		type: "post",
		url: "query-sort",
		contentType : 'application/json',
		data: JSON.stringify(data),
		success: function(data) {
			var select = document.getElementById("addGroup_sort");
			select.options.length = 0;
            if(data.length==0 || data == null) {
            	select.options.length = 0;
            	$('#addGroup_sort').append("<option value=" + 1 + ">" + 1 + "</option>");
            	$('#addGroup_sort').selectpicker('refresh');
            } else {
            	var data = $.parseJSON(data);
            	for (var i = 0; i < data.length; i++) {
            		$('#addGroup_sort').append("<option value=" + data[i] + ">" + data[i] + "</option>");
            	}
            	$('#addGroup_sort').selectpicker('refresh');
            }
		},
		error: function(data, xhr) {
			console.log(data);
			console.log(xhr);
		}
	})
}

$('#addGroupModal').on('show.bs.modal', function () {
	getAvailableSortList();
})

window.operateEvents = {
        'click .deleteGroup': function (e, value, row, index) {
        	layer.msg('Sure to delete this record?', {
        		  time: 0 //不自动关闭
        		  ,btn: ['Confirm', 'Cancel']
        		  ,yes: function(index){
        		    layer.close(index);
        		    var data = {
             				"id":row.id,
             			}
                	$.ajax({
                		type: "post",
                		url: "delete-group",
                		contentType : 'application/json',
                		data: JSON.stringify(data),
                		success: function(result) {
                			result = $.parseJSON(result);
                			if (result.code == 1) {
                				layer.msg("Delete Successfully!");
                				$('#group_table').bootstrapTable('refresh');
                			}
                		},
                		error: function(result, xhr) {
                			console.log(result);
                			console.log(xhr);
                		}
                	})
        		  }
        	});
        },
        'click .editGroup': function (e, value, row, index) {
        	$('#editGroupModal').modal('show');
        	$('#editGroup_id').val(row.id);
        	$('#editGroup_customer').val(row.name);
        	$('#editGroup_groupname').val(row.groupname);
        	
        	var data={
        			"customer":row.name
        		}
        		$.ajax({
        			type: "post",
        			url: "query-sort",
        			contentType : 'application/json',
        			data: JSON.stringify(data),
        			success: function(data) {
        				var select = document.getElementById("editGroup_sort");
        				select.options.length = 0;
        				
        				$('#editGroup_sort').append("<option value=" + row.sort + ">" + row.sort + "</option>");
        	            if(data.length==0 || data == null) {
        	            	select.options.length = 0;
        	            	$('#editGroup_sort').append("<option value=" + 1 + ">" + 1 + "</option>");
        	            	$('#editGroup_sort').selectpicker('refresh');
        	            } else {
        	            	var data = $.parseJSON(data);
        	            	for (var i = 0; i < data.length; i++) {
        	            		$('#editGroup_sort').append("<option value=" + data[i] + ">" + data[i] + "</option>");
        	            	}
        	            	$('#editGroup_sort').selectpicker('refresh');
        	            }
        			},
        			error: function(data, xhr) {
        				console.log(data);
        				console.log(xhr);
        			}
        		})
        	
        	$('#editGroup_description').val("");
        	$('#editGroup_customer').attr('disabled',true);
        	//$('#editGroup_groupname').attr('disabled',true);
        }
}

function queryGroupList() {
    $('#group_table').bootstrapTable({
    	height:720,
    	search:true,
//    	showRefresh:true,
//    	showToggle:true,
    	showColumns:true,
    	events: operateEvents,
        pagination: true,
        sidePagination: 'client',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
        toolbar: $('#toolbar'),
        uniqueId: 'id',
        url: 'customer-info-indevicegroup',
    });
}

function groupOperationFun(value,row,index) {
	return ['<button type="button" class="btn btn-info btn-sm editGroup" data-toggle="modal" data-target="editGroupModal"><span class="glyphicon glyphicon glyphicon-edit"></span>Edit</button>',
	        '<button type="button" class="btn btn-danger btn-sm deleteGroup"><span class="glyphicon glyphicon glyphicon-trash"></span>Delete</button>'].join('');	
}

function queryCustomerList() {
    $.ajax({
        type: "get",
        url: "customer-info",
        data: {
            //
        },
        success: function (result) {
            var data = $.parseJSON(result);
            for (var i = 0; i < data.length; i++) {
                $('#addGroup_selectCustomer').append("<option value=" + data[i].name + ">" + data[i].name + "</option>");
            }
            $('#addGroup_selectCustomer').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
	
function submit_addGroupClick() {
	if ($("#addGroup_groupname").val()) {
		if(!isNaN($('#addGroup_sort').val())){
			var data = {
					"name":$('#addGroup_selectCustomer').val(),
					"groupname": $("#addGroup_groupname").val(),
					"sort": $('#addGroup_sort').val(),
					"description": $('#addGroup_description').val()
				}
				//首先检查用户名是否重复
				$.ajax({
					type: "post",
					url: "check-newgroup",
					contentType : 'application/json',
					dataType:"json",
					data: JSON.stringify(data),
					success: function(result) {
						if (result.code != 1) {
							layer.msg("The group has been existed!");
							$("#addGroup_groupname").focus();
						} else {
							$.ajax({
								type: "post",
								url: "add-group",
								contentType : 'application/json',
								data: JSON.stringify(data),
								success: function(result) {
									$('#addGroupModal').modal('hide');
									layer.msg("Success!");
									$('#group_table').bootstrapTable('refresh');
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
			}else{
			   layer.alert("Sort is illegl!",{icon:7});
			}
		
	} else {
			layer.alert("Please fill your group name!")
	}
}

//点击编辑提交按钮
$("#editGroup_Submit").click(function() {
 	if ($("#editGroup_groupname").val()) {
 		if(!isNaN($('#editGroup_sort').val())){
 			var data = {
 	 				"id":$('#editGroup_id').val(),
 	 				"customer":$('#editGroup_selectCustomer').val(),
 	 				"groupname": $("#editGroup_groupname").val(),
 	 				"sort": $('#editGroup_sort').val(),
 	 				"description": $('#editGroup_description').val()
 	 			}
 	 			//首先检查用户名是否重复
 	 			$.ajax({
 	 				type: "post",
 	 				url: "check-newgroup",
 	 				contentType : 'application/json',
 	 				dataType:"json",
 	 				data: JSON.stringify(data),
 	 				success: function(result) {
 	 					if (result.code != 1) {
 	 						layer.alert("The group has been existed!",{icon:7});
 	 						$("#addGroup_groupname").focus();
 	 					} else {
 	 						$.ajax({
 	 							type: "post",
 	 							url: "update-group",
 	 							contentType : 'application/json',
 	 							data: JSON.stringify(data),
 	 							success: function(result) {
 	 								$('#editGroupModal').modal('hide');
 	 								layer.msg("Success!");
 	 								$('#group_table').bootstrapTable('refresh');
 	 							},
 	 							error: function(result, xhr) {
 	 								console.log(result);
 	 								console.log(xhr);
 	 								layer.msg("ERROR");
 	 							}
 	 						});
 	 					}
 	 				},
 	 				error: function(result, xhr) {
 	 					console.log(result);
 	 					console.log(xhr);
 	 				}
 	 			});
 		}
 		else {
 			layer.alert("Sort is illegl!",{icon:7});
 		}
 		
	} else {
			layer.alert("Please fill your group name!",{icon:7})
	}
});
