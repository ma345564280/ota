$(document).ready(function () {
    //第一次进入页面就自动进行一次在线统计
    // if (sessionStorage.getItem("statistic") == null) {
    //     getBox();
    // }
    queryCustomerList();
    queryModelList();
    queryBoxList();
    queryAddDeviceCustomerList();
    queryAddDeviceModelList();
    queryEditDeviceCustomerList();
    queryEditDeviceModelList();
    queryGroupforEditDevice();
    queryGroup();
    $('#deleteDevice').bind("click", deleteDeviceClick);
    $('#restartDevice').bind("click", restartDeviceClick);
    $('#factoryReset').bind("click", factoryResetDeviceClick);
    $('#confirm_selectDeviceWithFilter').bind("click", confirm_selectDeviceWithFilterClick);
    $('#submitEditDevice').bind("click", submitEditDeviceClick);
    $('#selectCustomer').on('change', selectCustomerOnchange);
});
/*
 *       加载Device页面时，加载筛选器内容的函数
 *       queryCustomerList():加载Customer筛选器的内容
 *       queryModelList():加载Model筛选器的内容
 *       queryGroup():加载Group筛选器的内容
 *       selectCustomerOnchang():当Customer筛选器变动时，Group筛选器随之变动
 *       confirm_selectDeviceWithFilterClick():点击GO时的查询操作
 * */
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
function selectCustomerOnchange() {
//获取被选中的option标签选项
//     alert($('#selectCustomer').val());
        queryGroup();
}
function queryGroup() {
    $.ajax({
        type: "get",
        url: "group-info",
        data: {
            "customer": $('#selectCustomer').val()
        },
        success: function (result) {
            var select = document.getElementById("selectGroup");
            select.options.length = 0;
            var data = $.parseJSON(result);
//            	$("#confirm_selectDeviceWithFilter").attr("disabled", false);
            $('#selectGroup').append("<option value=" + "All Group" + ">" + "All Group" + "</option>");
            for (var i = 0; i < data.length; i++) {
                $('#selectGroup').append("<option value=" + data[i] + ">" + data[i] + "</option>");
            }
            $('#selectGroup').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function confirm_selectDeviceWithFilterClick() {
    $('#table_boxinfo').bootstrapTable('refresh');
}

/*
 *       queryBoxList():加载页面时，加载Box信息的函数
 * */
function queryBoxList() {
    $('#table_boxinfo').bootstrapTable({
    	height:720,
        pagination: true,
        sidePagination: 'server',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
        toolbar: $('#toolbar'),

        uniqueId: 'mac',
        queryParams: function (params) {
            return {
                customer: $('#selectCustomer').val(),
                group: $('#selectGroup').val(),
                model: $('#selectModel').val(),
                status: $('#selectStatus').val(),
                pageSize: params.limit,
                offset: params.offset
            }
        },
        url: 'deviceinfo',
        onCheck: function () {
            buttonControl('#table_boxinfo', '#addDevice', '#editDevice', '#deleteDevice');
        },
        onCheckAll: function () {
            buttonControl('#table_boxinfo', '#addDevice', '#editDevice', '#deleteDevice');
        },
        onUncheckAll: function () {
            buttonControl('#table_boxinfo', '#addDevice', '#editDevice', '#deleteDevice');
        },
        onUncheck: function () {
            buttonControl('#table_boxinfo', '#addDevice', '#editDevice', '#deleteDevice');
        }
    });
}

function statusFun(value,row,index) {
	if(value=="Online") {
		return '<font color="green">'+'Online'+'</font>';
	} else if(value="Offline") {
		return '<font color="red">'+'Offline'+'</font>';
	}
	
}

/*
 *      $('#addDeviceModal').on('shown.bs.modal', function ():添加Device的模态框shown时的动作
 *      addDeviceselectCustomerList():添加Device的模态框中的Customer选择器变动时的操作
 *      queryAddDeviceCustomerList():添加Device的模态框中的CUstomer选择器
 *      queryAddDeviceModelList():添加Device的模态框中的Model选择器
 *      queryGroupforAddDevice():添加Device的模态框中的Group选择器
 *      $("#submitAddDevice").click(function ())：确认添加Deivce的操作
 * */
$('#addDeviceModal').on('shown.bs.modal', function () {
    // 执行一些动作...
    //alert("coming soon");
    queryGroupforAddDevice();
})
function addDeviceSelectCustomerOnchange() {
//获取被选中的option标签选项
//     alert($('#selectCustomer').val());
    queryGroupforAddDevice();
}
function queryAddDeviceCustomerList() {
    $.ajax({
        type: "get",
        url: "customer-info",
        data: {
            //
        },
        success: function (result) {
        	var data = $.parseJSON(result);
            for (var i = 0; i < data.length; i++) {
                $('#addDevice_selectCustomer').append("<option value=" + data[i].name + ">" + data[i].name + "</option>");
            }
            $('#addDevice_selectCustomer').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function queryAddDeviceModelList() {
    $.ajax({
        type: "get",
        url: "model-info",
        data: {
            //
        },
        success: function (result) {
            var select = document.getElementById("addDevice_selectModel");
            var data = $.parseJSON(result);
            select.options.length = 0;
            for (var i = 0; i < data.length; i++) {
                $('#addDevice_selectModel').append("<option value=" + data[i].type + ">" + data[i].type + "</option>");
            }
            $('#addDevice_selectModel').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function queryGroupforAddDevice() {
    $.ajax({
        type: "get",
        url: "group-info",
        data: {
            "customer": $('#addDevice_selectCustomer').val()
        },
        success: function (result) {
        	var data = $.parseJSON(result);
            var select = document.getElementById("addDevice_selectGroup");
            select.options.length = 0;
            var data = $.parseJSON(result);
            for (var i = 0; i < data.length; i++) {
                $('#addDevice_selectGroup').append("<option value=" + data[i] + ">" + data[i] + "</option>");
            }
            $('#addDevice_selectGroup').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function factoryResetDeviceClick() {
    var selected = JSON.stringify($('#table_boxinfo').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if(selected.length > 0) {
    	for (var i = 0; i < selected.length; i++) {
    		var mac = selected[i].mac;
    		sendReset(mac);
        }
    	
    } else {
    	layer.msg('Please choose a record!',{icon: 5});
    }
}
function restartDeviceClick() {
    var selected = JSON.stringify($('#table_boxinfo').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if(selected.length > 0) {
    	for (var i = 0; i < selected.length; i++) {
    		var mac = selected[i].mac;
    		sendReboot(mac);
        }
    	
    } else {
    	layer.msg('Please choose a record!',{icon: 5});
    } 
}
$("#submitAddDevice").click(function () {
    var MACBegin = $("#MACBegin").val();
    var MACEnd = $("#MACEnd").val();
    var UserIDBegin = $("#UserIDBegin").val();
    var UserIDEnd = $("#UserIDEnd").val();
    var Customer = $("#addDevice_selectCustomer").val();
    var Group = $("#addDevice_selectGroup").val();
    var Model = $("#addDevice_selectModel").val();
    if (MACBegin && MACEnd && UserIDBegin && UserIDEnd) {//先判断填写的表单是否为空
        if (checkMACAddress(MACBegin) && checkMACAddress(MACEnd)) {//判断MAC地址是否合法
            var Num0xMACBegin = parseInt(MACBegin.replace(/:/g, ""), 16);
            var Num0xMACEnd = parseInt(MACEnd.replace(/:/g, ""), 16);
            var diffMAC = Num0xMACEnd - Num0xMACBegin;
            var diffUserID = UserIDEnd - UserIDBegin;
            if (diffMAC != diffUserID) {//判断MAC地址和UserID的间隔个数是否相同
                console.log(Num0xMACEnd - Num0xMACBegin);
                console.log(UserIDEnd - UserIDBegin);
                layer.msg("The count between User ID and MAC address is not right!",{icon:2});
            } else if (diffMAC < 0 || diffUserID < 0) {
                layer.msg("MAC address OR UserID should begin with small and end with big",{icon:2});
            } else {
                var count = UserIDEnd - UserIDBegin;
                var data = {
                    count: count,
                    MACBegin: MACBegin,
                    MACEnd: MACEnd,
                    UserIDBegin: UserIDBegin,
                    UserIDEnd: UserIDEnd,
                    Customer: Customer,
                    Group: Group,
                    Model: Model,
                }
                $.ajax({
                    contentType: 'application/json',
                    type: "post",
                    url: "submitBox",
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function (result) {
                        layer.msg("Success, boxes have been added!");
                        $("#MACBegin").val("");
                        $("#MACEnd").val("");
                        $("#UserIDBegin").val("");
                        $("#UserIDEnd").val("");
                        $('#addDeviceModal').modal('hide');
                        $('#table_boxinfo').bootstrapTable('refresh');
                        console.log(result);
                    },
                    error: function (result) {
                        alert("Sorry, adding boxes failed at mac of " + result.erroInMac + "!");
                        console.log(result);
                    }
                });
            }
        } else {
            layer.msg("The MAC address is illegal！",{icon:2});
        }
    } else {//让空的表单获取焦点
        if (!MACBegin) {
            layer.msg("You don't write MAC Address",{icon:2});
            $("#MACBegin").focus();
        } else if (!MACEnd) {
            layer.msg("You don't write MAC Address",{icon:2});
            $("#MACEnd").focus();
        } else if (!UserIDBegin) {
            layer.msg("You don't write User ID",{icon:2});
            $("#UserIDBegin").focus();
        } else {
            layer.msg("You don't write User ID",{icon:2});
            $("#UserIDEnd").focus();
        }
    }
    return false;
})
function checkMACAddress(address) {
    if (address.length < 17) {
        return false;
    } else {
        var reg = /([a-fA-F0-9]{2}:){5}[a-fA-F0-9]{2}/;
        if (!address.match(reg)) {
            return false;
        }
    }
    return true;
}
/*
 *
 *       deleteDeviceClick():点击delete时，删除对应的记录
 *
 * */
function deleteDeviceClick() {
    var selected = JSON.stringify($('#table_boxinfo').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if(selected.length > 0) {
    	for (var i = 0; i < selected.length; i++) {
            $.ajax({
                type: "post",
                url: "delete-Box",
                contentType: 'application/json',
                data: JSON.stringify({mac: selected[i].mac}),
                success: function (result) {
                    result = $.parseJSON(result);
                    if (result.code == 1) {
                        layer.msg("Delete Successfully!");
                        $('#table_boxinfo').bootstrapTable('refresh');
                    }
                },
                error: function (result, xhr) {
                    console.log(result);
                    console.log(xhr);
                }
            })
        }
    } else {
    	layer.msg('Please choose a record!',{icon: 5});
    }
    

}
/*
 *      编辑Device时用到的模态框及相关函数
 *
 *
 * */
$('#editDeviceModal').on('show.bs.modal', function () {
    // 执行一些动作...
    //alert("coming soon");
    queryGroupforEditDevice();
    var selected = JSON.stringify($('#table_boxinfo').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if (selected.length == 1) {
        $('#editDevice_selectedMac').val(selected[0].mac);
        $("#editDevice_selectedMac").attr("readOnly", true);
        $('#editDevice_selectedUserID').val(selected[0].userid);
        $('#editDevice_selectedVserion').val(selected[0].romversion);
    } else {
        layer.msg("Please Choose A Record!",{icon:2});
        $('#editDeviceModal').modal('hidden');
    }
})
function editDeviceSelectCustomerOnchange() {
//获取被选中的option标签选项
//     alert($('#selectCustomer').val());
    queryGroupforEditDevice();
}
function queryEditDeviceCustomerList() {
    $.ajax({
        type: "get",
        url: "customer-info",
        data: {
            //
        },
        success: function (result) {
            var select = document.getElementById('editDevice_selectCustomer');
            var data = $.parseJSON(result);
            select.options.length = 0;

            for (var i = 0; i < data.length; i++) {
                $('#editDevice_selectCustomer').append("<option value=" + data[i].name + ">" + data[i].name + "</option>");
            }
            $('#editDevice_selectCustomer').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function queryEditDeviceModelList() {
    $.ajax({
        type: "get",
        url: "model-info",
        data: {
            //
        },
        success: function (result) {

            var select = document.getElementById("editDevice_selectModel");
            var data = $.parseJSON(result);
            select.options.length = 0;

            for (var i = 0; i < data.length; i++) {
                $('#editDevice_selectModel').append("<option value=" + data[i].type + ">" + data[i].type + "</option>");
            }
            $('#editDevice_selectModel').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}
function queryGroupforEditDevice() {
    $.ajax({
        type: "get",
        url: "group-info",
        data: {
            "customer": $('#editDevice_selectCustomer').val()
        },
        success: function (result) {
            var select = document.getElementById("editDevice_selectGroup");
            select.options.length = 0;
            var data = $.parseJSON(result);

            for (var i = 0; i < data.length; i++) {
                $('#editDevice_selectGroup').append("<option value=" + data[i] + ">" + data[i] + "</option>");
            }
            $('#editDevice_selectGroup').selectpicker('refresh');
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    });
}

function submitEditDeviceClick() {
    data = {
        mac: $('#editDevice_selectedMac').val(),
        userid: $('#editDevice_selectedUserID').val(),
        romversion: $('#editDevice_selectedVserion').val(),
        customer: $('#editDevice_selectCustomer').val(),
        group: $('#editDevice_selectGroup').val(),
        model: $('#editDevice_selectModel').val(),
    };
    $.ajax({
        type: "post",
        url: "edit-Box",
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            result = $.parseJSON(result);
            if (result.code == 1) {
            	$('#editDeviceModal').modal('hide');
            	layer.msg('Success!');
                $('#table_boxinfo').bootstrapTable('refresh');
            }
        },
        error: function (result, xhr) {
            console.log(result);
            console.log(xhr);
        }
    })
}


function clickEditAccount() {
    var mac = $(this).parent().parent().siblings(".mac").val();
    //alert(mac);
    sendReboot(mac);


};
function clickDeleteAccount() {
    var mac = $(this).parent().parent().siblings(".mac").val();
    sendReset(mac);
};

var websocket = null;
//判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
    var hostport = document.location.host;
    var uri = "ws://" + hostport + "/ota/websocket";
    //alert(uri);
    websocket = new WebSocket(uri);
} else {
    alert('Not support websocket')
}
//连接发生错误的回调方法
websocket.onerror = function () {
    //setMessageInnerHTML("error");
};
//连接成功建立的回调方法
websocket.onopen = function (event) {
    //setMessageInnerHTML("open");
}
//接收到消息的回调方法
websocket.onmessage = function (event) {
   /// setMessageInnerHTML(event.data);
}
//连接关闭的回调方法
websocket.onclose = function () {
    //setMessageInnerHTML("close");
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
window.onbeforeunload = function () {
    websocket.close();
}
//将消息显示在网页上
function setMessageInnerHTML(innerHTML) {
    //document.getElementById('message').innerHTML += innerHTML + '<br/>';
}
//关闭连接
function closeWebSocket() {
    websocket.close();
}
//发送消息
function sendReboot(msg) {
    var message = "reboot_" + msg;
    //alert(message);
    websocket.send(message);
}
//发送消息
function sendReset(msg) {
    var message = "reset_" + msg;
    //alert(message);
    websocket.send(message);
}

