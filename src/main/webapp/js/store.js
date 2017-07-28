$(document).ready(function () {
	//截取URL判断是哪个页面
	var beginIndex = location.pathname.indexOf("/", 1);
	var endIndex = location.pathname.indexOf(".", 0);
	var pageName = location.pathname.substring(beginIndex + 1, endIndex);
	switch (pageName) {
		case "store-dis":
			storePageLoad();
			break;
		case "store-detail":
			storeDetailPageLoad();
			break;
		case "store-add":
		break;
		case "store-addsuccess":
		break;
	}
});
function confirm_selectWithFilterClick() {
    $('#store_table').bootstrapTable('refresh');
}
function detailStoreClick() {
	var selected = JSON.stringify($('#store_table').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if (selected.length == 1) {
    	window.location.href="store-detail.html?id=" + selected[0].id;
    } else {
        layer.msg("Please Choose A Record!",{icon:2});
    }
	
}
function statusFormatter(value,row,index) {
	if(value=="Open") {
		return '<font color="green">'+'Open'+'</font>';
	} else if(value="Close") {
		return '<font color="red">'+'Close'+'</font>';
	}
	
}
function timeFormatter(value,row,index) {
	var date = new Date(value);
	var updateTime = date.getFullYear() +"-"+ (parseInt(date.getMonth()) + 1) +"-"+ date.getDate()+" " +date.getHours() + ":" +date.getMinutes()+":"+date.getSeconds();
	return updateTime;
}
function OperationFun(value,row,index) {
	return ['<button type="button" class="btn btn-warning btn-sm closeUpdateFile"><span class="glyphicon glyphicon glyphicon-off"></span>Cloes</button>',
	        '<button type="button" class="btn btn-warning btn-sm unForceUpdateFile"><span class="glyphicon glyphicon glyphicon-hand-down"></span>Unforce</button>'].join('');	
}
window.operateEvents = {
        'click .closeUpdateFile': function (e, value, row, index) {
        	layer.alert('This Function is undefined',{icon:4});
        },
        'click .unForceUpdateFile': function (e, value, row, index) {
        	layer.alert('This Function is undefined',{icon:4});
        }
}
function enforceFormatter(value,row,index) {
	if(value=="Yes") {
		return '<font color="green">'+'Yes'+'</font>';
	} else if(value="No") {
		return '<font color="red">'+'No'+'</font>';
	}
	
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
function apkdownloadFormatter(value,row,index) {
	return '<a href='+ value+'>'+value+'</a>';
}
function queryStoreInfo() {
    $('#store_table').bootstrapTable({
    	height:720,
    	search:true,
//    	showRefresh:true,
//    	showToggle:true,
    	showColumns:true,
    	events: operateEvents,
        pagination: true,
        queryParams: function (params) {
            return {
                customer: $('#selectCustomer').val(),
                model: $('#selectModel').val(),
                pageSize: params.limit,
                offset: params.offset
            }
        },
        sidePagination: 'client',
        pageNumber: 1,
        pageSize: 20,
        pageList: [10, 20, 30, 50],
        toolbar: $('#toolbar'),
       // uniqueId: 'id',
        url: 'store-info',
        onCheck: function () {
            buttonControl('#store_table', '#editStore', '#deleteStore', '#detailStore');
        },
        onCheckAll: function () {
            buttonControl('#store_table', '#editStore', '#deleteStore', '#detailStore');
        },
        onUncheckAll: function () {
            buttonControl('#store_table', '#editStore', '#deleteStore', '#detailStore');
        },
        onUncheck: function () {
            buttonControl('#store_table', '#editStore', '#deleteStore', '#detailStore');
        }
    });
}
function clickDeleteStore() {
	var selected = JSON.stringify($('#store_table').bootstrapTable('getSelections'));
    selected = $.parseJSON(selected);
    if(selected.length > 0) {
    	for (var i = 0; i < selected.length; i++) {
            $.ajax({
                type: "post",
                url: "deleteAppFile",
                contentType: 'application/json',
                data: JSON.stringify({id: selected[i].id}),
                success: function (result) {
                    result = $.parseJSON(result);
                    if (result.code == 1) {
                        layer.msg("Delete Successfully!");
                        $('#store_table').bootstrapTable('refresh');
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
function selectCustomerOnchange() {
	//获取被选中的option标签选项
	if($('#selectCustomer').val() == "Please Select!") {
		var select = document.getElementById("selectGroup");
        select.options.length = 0;
		$("#selectGroup").attr("disabled", true);
	} else {
		$("#selectGroup").attr("disabled", false);
		query_patchadd_Group();
	}
//	     alert($('#selectCustomer').val());
	}
//store.html的数据加载
function storePageLoad() {
	queryModelList();
	queryCustomerList();
	queryStoreInfo();
	
	$('#confirm_selectWithFilter').bind("click", confirm_selectWithFilterClick);
	$('#detailStore').bind("click", detailStoreClick);
	$('#deleteStore').bind("click", clickDeleteStore);
}
//store-detail.html的数据加载
function storeDetailPageLoad() {
	queryModelList();
	var data = {
		"storeId": location.search.substring(location.search.indexOf("=") + 1)
	};
	$.ajax({
		type: "post",
		url: "store-detail",
		contentType : 'application/json',
		data: JSON.stringify(data),
		success: function(result) {console.log($.parseJSON(result));
			var data = $.parseJSON(result).appstore;
			var filter = $.parseJSON(result).appstoremapping;
			var content = "";
			var date = new Date(data.updateTime);
			var updateTime = date.getFullYear() +"-"+ (parseInt(date.getMonth()) + 1) +"-"+ date.getDate();
			//加载文件信息
			content +=
				'<tr>' +
					'<td><a href="'+data.apkdownload+'">'+data.apkname+'</a></td>' +
				  '<td id="apkversion">'+data.apkversion+'</td>' +
				  '<td>'+data.appstoreversion+'</td>' +
				  '<td>'+updateTime+'</td>' +
				  '<input type="hidden" id="apkid" value="'+data.id+'">' +
				  '<input type="hidden" id="businessId" value="'+data.businessid+'">' +
				'</tr>';
			$("#infoTable").append(content);
			content = "";
			//加载策略信息
			for (var i = 0; i < filter.length; i++) {
				if (filter[i] != null) {
					content +=
						'<tr>' +
						  '<td>'+(i+1)+'</td>' +
						  '<td id="type'+filter[i].id+'">'+filter[i].type+'</td>' +
						  '<td id="romVersion'+filter[i].id+'">'+filter[i].romversion+'</td>' +
						  '<td id="hardwareVersion'+filter[i].id+'">'+filter[i].hardwareversion+'</td>' +
						  '<td id="manufacturer'+filter[i].id+'">'+filter[i].manufacturer+'</td>' +
						  '<td id="appVersion'+filter[i].id+'">'+filter[i].appversion+'</td>' +
						  '<td id="homeUI'+filter[i].id+'">'+filter[i].homeui+'</td>';
						  if (typeof filter[i].dvbsupport != "undefined") {
						  	content += '<td id="DVBSupport'+filter[i].id+'">'+filter[i].dvbsupport+'</td>';
						  } else {
						  	content += '<td id="DVBSupport'+filter[i].id+'"></td>';
						  }
						  content +=
						  '<td id="userID'+filter[i].id+'">'+filter[i].userid+'</td>';
						  if (typeof filter[i].ipbegin != "undefined" && filter[i].ipbegin != '') {
						  	content += '<td id="IPAddress'+filter[i].id+'">'+filter[i].ipbegin +"-<br>"+ filter[i].ipend+'</td>';
						  } else {
						  	content += '<td id="IPAddress'+filter[i].id+'"></td>';
						  }
						  content +=
						  '<td><div class="btn-group" role="group" aria-label="operation">  <button type="button" class="btn btn-default btn-sm filterEditBtn" value="'+filter[i].id+'">Edit</button>  <button type="button" class="btn btn-default btn-sm filterDeleteBtn"value="'+filter[i].id+'">Delete</button></div></td>' +
						'</tr>';
				}
			}
			$("#filterTable").append(content);
			$(".filterEditBtn").bind("click",clickFilterEditBtn);
			$(".filterDeleteBtn").bind("click",clickFilterDeleteBtn);
		},
		error: function(result, xhr) {
			console.log(result);
			console.log(xhr);
		}
	});
}
//点击添加apk文件按钮
$("#addApk").click(function(){
	location.href = "store-add.html";
});
//多选apk按钮
$("#selectBtn").click(function(){
	$("input.selectFilter[type='checkbox']").removeClass("displayNone");
	$("#bulkAddFilterBtn").parents("tr").removeClass("displayNone");
});
//点击取消多选按钮
function clickCanceltBtn(){
	$("input.selectFilter[type='checkbox']").addClass("displayNone");
	$("#bulkAddFilterBtn").parents("tr").addClass("displayNone");
	$("input.selectFilter[type='checkbox']").prop("checked", false);
}
//点击添加策略按钮
function clickBulkAddFilterBtn(){
	var checkedBox = $("input.selectFilter:checked");
	if (checkedBox.length > 0) {
		var checkedValue = '';
		for (var i = 0; i < checkedBox.length - 1; i++) {
			checkedValue += checkedBox[i].value + ',';
		}
		checkedValue += checkedBox[i].value;
		$("#apkid").val(checkedValue);
		
		$("#addFilterModal").modal("show");
	} else {
		alert("You don't select any file!");
	}
}
//点击删除文件按钮
function clickDeleteFile() {
	var id = $(this).val();
	$.ajax({
		type: "post",
		url: "deleteAppFile",
		contentType : 'application/json',
		data: JSON.stringify({id: $(this).val()}),
		success: function(result) {
			result = $.parseJSON(result);
			if (result.code == 1) {
				alert("Delete Successfully!");
				$('button[value="'+id+'"]').parents("tr").remove();
			}
		},
		error: function(result, xhr) {
			console.log(result);
			console.log(xhr);
		}
	});
}
$("#back").click(function(){
	window.history.back();
});

//文件上传
$("#start-upload").click(uploadFile);
function uploadFile(){
	var fileObj = document.getElementById("updateFile").files[0]; // 获取文件对象
    if(fileObj){
    	console.log(fileObj);
      // FormData 对象
      var form = new FormData();
      form.append("file", fileObj);// 文件对象
      // XMLHttpRequest 对象
      console.log(form);
      var xhr = new XMLHttpRequest();

      // 接收上传文件的后台地址
      xhr.open("post", "uploadappstorefile", true);
      xhr.onload = function () {
        xhr.responseText;
      };
      // 每当readyState发生改变的时候，onreadystatechange函数就会被执行
      xhr.onreadystatechange = function() {
      	if (xhr.readyState == 4) {
      		alert("File update successfully!");
      		console.log($("#start-upload").val());
      		$("#start-upload").val(1);
      		console.log($("#start-upload").val());
      	}
      }
      xhr.send(form);
    }else{
      alert("Please choose a file!");
    }
  return false;
}
//图标上传
$("#start-upload2").click(uploadIcon);
function uploadIcon(){
	var fileObj = document.getElementById("updateIcon").files[0]; // 获取文件对象
    if(fileObj){
    	console.log(fileObj);
      // FormData 对象
      var form = new FormData();
      form.append("file", fileObj);// 文件对象
      // XMLHttpRequest 对象
      console.log(form);
      var xhr = new XMLHttpRequest();

      // 接收上传文件的后台地址
      xhr.open("post", "uploadIconfile", true);
      xhr.onload = function () {
        xhr.responseText;
      };
      // 每当readyState发生改变的时候，onreadystatechange函数就会被执行
      xhr.onreadystatechange = function() {
      	if (xhr.readyState == 4) {
      		alert("File update successfully!");
      		console.log($("#start-upload2").val());
      		$("#start-upload2").val(1);
      		console.log($("#start-upload2").val());
      	}
      }
      xhr.send(form);
    }else{
      alert("Please choose a file!");
    }
  return false;
}
//store-add.html数据提交按钮
$("#submit").click(function(){
	if ($("#appStoreVersion").val() && $("#apkName").val() && $("#apkVersion").val()) {
		if($("#alreadyUploadFile").val()=="1" && $("#alreadyUploadIcon").val()=="1") {
			var data = {
					"apkname": $("#apkName").val(),
					"appStoreVersion": $("#appStoreVersion").val(),
					"apkVersion": $("#apkVersion").val(),
					"apkinfo": $("#description").val(),
				}
				$.ajax({
					type: "post",
					url: "addapkupdateFile",
					contentType : 'application/json',
					data: JSON.stringify(data),
					success: function(result){
						if ($.parseJSON(result).code == "1") {
								layer.alert("Submit File Success!");
								location.href = "store-dis.html";
							}
					},
					error: function(result, xhr) {
						console.log(result);
						console.log(xhr);
					}
				});
		} else {
			layer.alert("You didn't click updatefile",{icon:2});
		}
		
	} else {
		console.log($("#appStoreVersion").val());
		console.log($("#apkVersion").val());
		layer.alert("You should fill the blank with red star!",{icon:2});
	}
	return false;
});
//点击提交策略按钮
$("#addFilterBtn").click(function(){
	if ($("#romVersion").val() && $("#selectModel").val()) {
		var data = {
			"id": $("#apkid").val(),//对应的文件的id
			"mappingid": $("#mappingid").val(),//如果是更新策略就有mappingid，新增策略则为空
			"Romversion": $("#romVersion").val(),
			"Type": $("#selectModel").val(),
			"AppVersion": $("#appVersion").val(),
			"hardwareversion": $("#hardwareVersion").val(),
			"manufacturer": $("#manufacturer").val(),
			"DVBSupport": $("input[name='DVBSupport']:checked").val(),
			"HomeUI": $("#homeUI").val(),
			"UserID": $("#userID").val(),
			"customName": $("#customName").val(),
			"ipbegin": $("#IPAddressBegin").val(),
			"ipend": $("#IPAddressEnd").val()
		}
		var url = "storeAddFilter";
		//如果mappingid不为空，则是修改策略
		if ($("#mappingid").val() != "") {
			url = "storeUpdateFilter";
		}
		$.ajax({
			type: "post",
			url: url,
			data: JSON.stringify(data),
			contentType : 'application/json',
			success: function(result){
				if ($.parseJSON(result).code == "1") {
					//如果mappingid不为空，则是修改策略
					if($("#mappingid").val() != "") {
						alert("Change Filter Success!");
					} else {
						alert("Submit File Success!");
					}
					$('#addFilterModal').modal('hide');
					location.href = "store-detail.html?id=" + $("#apkid").val();
				}
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
			}
		});
	} else {
		console.log("input.val(romVersion, type)");
		console.log($("#romVersion").val());
		console.log($("#type").val());
		alert("You should fill the blank with red star!");
	}
	return false;
});
//点击批量广告提交策略按钮
$("#bulk-addFilterBtn").click(function(){
	if ($("#romVersion").val() && $("#type").val()) {
		var data = {
			"ids": $("#apkid").val(),//对应的文件的id
			"Romversion": $("#romVersion").val(),
			"Type": $("#type").val(),
			"AppVersion": $("#appVersion").val(),
			"hardwareversion": $("#hardwareVersion").val(),
			"manufacturer": $("#manufacturer").val(),
			"DVBSupport": $("input[name='DVBSupport']:checked").val(),
			"HomeUI": $("#homeUI").val(),
			"UserID": $("#userID").val(),
			"customName": $("#customName").val(),
			"ipbegin": $("#IPAddressBegin").val(),
			"ipend": $("#IPAddressEnd").val()
		}
		$.ajax({
			type: "post",
			url: "apkstoreAddMultFilter",
			data: JSON.stringify(data),
			contentType : 'application/json',
			success: function(result){
				if ($.parseJSON(result).code == "1") {
					//如果mappingid不为空，则是修改策略
					alert("Submit File Success!");
					$('#addFilterModal').modal('hide');
				}
			},
			error: function(result, xhr) {
				console.log(result);
				console.log(xhr);
			}
		});
	} else {
		console.log("input.val(romVersion, type)");
		console.log($("#romVersion").val());
		console.log($("#type").val());
		alert("You should fill the blank with red star!");
	}
	return false;
});
//对话框在隐藏之后
$("#addFilterModal").on("hidden.bs.modal", function() {
	if (typeof $("#addFilterBtn")[0] != "undefined") {
		$("#addFilterBtn")[0].innerHTML = "Add";
		$("#addFilterTitle")[0].innerHTML = "Add filter";
	}
	clearCheckForm();
});
//点击编辑策略按钮
function clickFilterEditBtn() {
	$("#addFilterBtn")[0].innerHTML = "Update";
	$("#addFilterTitle")[0].innerHTML = "Edit filter";
	$('#addFilterModal').modal('show');
	var num = $(this).val();
	$("#id").val($("#id").val());
	$("#mappingid").val(num);
	//填充input元素
	fillInputElement("romVersion", $("#romVersion"+num)[0].innerHTML);
	fillInputElement("type", $("#type"+num)[0].innerHTML);
	fillInputElement("appVersion", $("#appVersion"+num)[0].innerHTML);
	fillInputElement("hardwareVersion", $("#hardwareVersion"+num)[0].innerHTML);
	fillInputElement("manufacturer", $("#manufacturer"+num)[0].innerHTML);
	fillInputElement("DVBSupport", $("#DVBSupport"+num)[0].innerHTML);
	fillInputElement("homeUI", $("#homeUI"+num)[0].innerHTML);
	fillInputElement("userID", $("#userID"+num)[0].innerHTML);
	fillInputElement("IPAddress", $("#IPAddress"+num)[0].innerHTML);
	if ($("#DVBSupport"+num)[0].innerHTML == "true") {
		$("#yesSupport").prop("checked", true);
		$("#noSupport").prop("checked", false);
	} else {
		if ($("#DVBSupport"+num)[0].innerHTML == "false") {
			$("#yesSupport").prop("checked", false);
			$("#noSupport").prop("checked", true);
		} else {
			$("#yesSupport").prop("checked", false);
			$("#noSupport").prop("checked", false);
		}
	}
	//IP地址的分割
	var ipaddress = $("#IPAddress"+num);
	var index = ipaddress[0].innerHTML.indexOf("-");
	var ipbegin = ipaddress[0].innerHTML.substring(0, index);
	var ipend = ipaddress[0].innerHTML.substring( index + 5);
	$("#IPAddressBegin").val(ipbegin);
	$("#IPAddressEnd").val(ipend);
}
//点击删除策略按钮
function clickFilterDeleteBtn() {
	var id = $(this).val();
	$.ajax({
		type: "post",
		url: "deleteAppFilter",
		contentType : 'application/json',
		data: JSON.stringify(
		{
			id: $(this).val(),
			apkid:$(apkid).val()
		}),
		success: function(result) {
			result = $.parseJSON(result);
			if (result.code == 1) {
				alert("Delete Successfully!");
				$('button[value="'+id+'"]').parents("tr").remove();
			}
		},
		error: function(result, xhr) {
			console.log(result);
			console.log(xhr);
		}
	});
}
//填充对应的input元素
function fillInputElement(name, value) {
	if (value != "") {
		if ($("#"+name+"Check").length > 0) {
			$("#"+name+"Check")[0].checked = true;
		}
		$("#"+name+"Div").removeClass("displayNone");
		$("#"+name).val(value);
	} else {
		//
	}
}
//清理勾选表单，隐藏input
function clearCheckForm() {
	var checkboxArray = $('.filterForm input[type="checkbox"]');
	$('.filterForm input[type!="checkbox"][type!="radio"]').val("");
	for (var i = 0; i < checkboxArray.length; i++) {
		checkboxArray[i].checked = false;
		$("#" + checkboxArray[i].value).addClass("displayNone");
	}
}



//////////////////////////////////////////////////////////////
//文件修改时
$("#uploadFile").change(function() {
    $("#btnUploadFile").val("UploadFile");
    $("#progressBarFile").width("0%");
    var file = $(this).prop('files');
    if (file.length != 0) {
        $("#batchUploadFileBtn").attr('disabled', false);
    }

});


// 上传按钮点击事件
$("#btnUploadFile").click(function() {
    // 进度条归零
    $("#progressBarFile").width("0%");
    // 进度条显示
    $("#progressBarFile").parent().show();
    $("#progressBarFile").parent().addClass("active");
	
    //上传按钮修改为可用
    $(this).attr("disabled", true);
    uploadFunction();
})

// 弹出上传Model
$("#activeUploadFile").click(function(){
	 $("#uploadFile").val("");
		$("#progressBarFile").width("0%");
		 $("#progressBarFile").parent().hide();
    $("#uploadFileModal").modal("show");
})


// 文件修改时
$("#uploadFile").change(function() {
    $("#btnUploadFile").val("UploadFile");
    var file = $(this).prop("files");
    if (file.length != 0) {
        $("#btnUploadFile").attr("disabled", false);
    }
});

//文件上传
function uploadFunction() {
    var uploadFile = $("#uploadFile").get(0).files[0]; //获取文件对象

    // FormData 对象
    var form = new FormData();
    form.append("file", uploadFile); // 文件对象
    var uploadUrl = "store_uploadfile";//异步上传地址
    $.ajax({
        cache: false,
        type: "POST",
        url: uploadUrl,
        contentType: false, 
        processData: false, 
        data: form,
        xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
            myXhr = $.ajaxSettings.xhr();
            if(progressFunctionFile && myXhr.upload) { //检查进度函数和upload属性是否存在
                //绑定progress事件的回调函数
                myXhr.upload.addEventListener("progress",progressFunctionFile, false);
            }
            return myXhr; //xhr对象返回给jQuery使用
        },
        error: function(request) {
            alert("Connection error");
            $("#alreadyUploadFile").val("0");
        },
        success: function(data) {
        	layer.alert("Upload Success!");
//            $("input[name=companyLicenseImg]").val(data);
//            $("#licenseImg").attr("src","<%=path%>"+data); //将后台返回图片路径设置给IMG，显示图片
//            $("#licenseImg").attr("width","100"); 
//            $("#activeUpload").val("重新上传");
        	$("#alreadyUploadFile").val("1");
            $("#btnUploadFile").attr("disabled", false);
            $("#btnUploadFile").val("Upload");
            $("#uploadFileModal").modal("hide");
        }
    });
}

//进度条控制
function progressFunctionFile(evt) {
	var progressBar = $("#progressBarFile");
    if (evt.lengthComputable) {
        var completePercent = Math.round(evt.loaded / evt.total * 100)+ "%";
        $("#btnUploadFile").val("Uploading：" + completePercent);
        progressBar.width(completePercent);
    }
}


//////////////////////////////////////////////////////////////
//文件修改时
$("#uploadIcon").change(function() {
  $("#btnUploadIcon").val("UploadIcon");
  $("#progressBarIcon").width("0%");
  var file = $(this).prop('files');
  if (file.length != 0) {
      $("#batchUploadIconBtn").attr('disabled', false);
  }

});


//上传按钮点击事件
$("#btnUploadIcon").click(function() {
  // 进度条归零
  $("#progressBarIcon").width("0%");
  // 进度条显示
  $("#progressBarIcon").parent().show();
  $("#progressBarIcon").parent().addClass("active");
	
  //上传按钮修改为可用
  $(this).attr("disabled", true);
  uploadIconFunction();
})

//弹出上传Model
$("#activeUploadIcon").click(function(){
	 $("#uploadIcon").val("");
		$("#progressBarIcon").width("0%");
		 $("#progressBarIcon").parent().hide();
  $("#uploadModalIcon").modal("show");
})


//文件修改时
$("#uploadIcon").change(function() {
  $("#btnUploadIcon").val("UploadIcon");
  var file = $(this).prop("files");
  if (file.length != 0) {
      $("#btnUploadIcon").attr("disabled", false);
  }
});

//文件上传
function uploadIconFunction() {
  var uploadFile = $("#uploadIcon").get(0).files[0]; //获取文件对象

  // FormData 对象
  var form = new FormData();
  form.append("file", uploadFile); // 文件对象
  var uploadUrl = "store_uploadicon";//异步上传地址
  $.ajax({
      cache: false,
      type: "POST",
      url: uploadUrl,
      contentType: false, 
      processData: false, 
      data: form,
      xhr: function(){ //获取ajaxSettings中的xhr对象，为它的upload属性绑定progress事件的处理函数
          myXhr = $.ajaxSettings.xhr();
          if(progressFunctionIcon && myXhr.upload) { //检查进度函数和upload属性是否存在
              //绑定progress事件的回调函数
              myXhr.upload.addEventListener("progress",progressFunctionIcon, false);
          }
          return myXhr; //xhr对象返回给jQuery使用
      },
      error: function(request) {
          alert("Connection error");
          $("#alreadyUploadIcon").val("0");
      },
      success: function(data) {
      	layer.alert("Upload Success!");
//          $("input[name=companyLicenseImg]").val(data);
//          $("#licenseImg").attr("src","<%=path%>"+data); //将后台返回图片路径设置给IMG，显示图片
//          $("#licenseImg").attr("width","100"); 
//          $("#activeUpload").val("重新上传");
      	$("#alreadyUploadIcon").val("1");
          $("#btnUploadIcon").attr("disabled", false);
          $("#btnUploadIcon").val("Upload");
          $("#uploadModalIcon").modal("hide");
      }
  });
}

//进度条控制
function progressFunctionIcon(evt) {
	var progressBar = $("#progressBarIcon");
  if (evt.lengthComputable) {
      var completePercent = Math.round(evt.loaded / evt.total * 100)+ "%";
      $("#btnUploadIcon").val("Uploading：" + completePercent);
      progressBar.width(completePercent);
  }
}




