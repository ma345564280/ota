$(document).ready(function () {
	//加载otalog
	queryCustomerList();
	queryModelList();
	queryotalog();
	$('#confirm_selectOtaLogWithFilter').bind("click", confirm_selectOtaLogWithFilterClick);
});
function actionReFormFun(value,row,index) {
	if(value=="Box Start") {
		return '<font color="green">Box Start</font>';
	} else {
		return '<font color="red">Box Stop</font>';
	}
}

function confirm_selectOtaLogWithFilterClick() {
    $('#otalog_table').bootstrapTable('refresh');
}
function queryotalog() {
	$('#otalog_table').bootstrapTable ({
    	height:720,
    	search:true,
//    	showRefresh:true,
//    	showToggle:true,
    	showColumns:true,
        pagination: true,
        showExport:true,
        exportDataType:'all',
        sidePagination: 'server',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
//        toolbar: $('#toolbar'),
        uniqueId: 'id',
        queryParams: function (params) {
            return {
                customer: $('#selectCustomer').val(),
                model: $('#selectModel').val(),
                action: $('#selectAction').val(),
                pageSize: params.limit,
                offset: params.offset
            }
        },
        url: 'otalog-info',
    });
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
                $('#selectCustomer').append("<option value=" + data[i].name + ">" + data[i].name + "</option>");
            }
            $('#selectCustomer').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}

function queryModelList() {
    $.ajax({
        type: "get",
        url: "model-info",
        data: {
            //
        },
        success: function (result) {
            var data = $.parseJSON(result);
            for (var i = 0; i < data.length; i++) {
                $('#selectModel').append("<option value=" + data[i].type + ">" + data[i].type + "</option>");
            }
            $('#selectModel').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
