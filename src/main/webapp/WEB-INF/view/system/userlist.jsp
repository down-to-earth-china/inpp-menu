<!DOCTYPE html>
<%@ include file="../include/include.jsp" %>
<%@ page session="false" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<html lang="zh">

<head>
    <meta http-equiv="content-type" content="text/html, charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="renderer" content="webkit">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/css/font-awesome.min.css?v=4.3.0" rel="stylesheet">
    <link href="${ctx}/resources/css/plugins/jqgrid/ui.jqgrid.css?0820" rel="stylesheet">
    <link href="${ctx}/resources/css/animate.css" rel="stylesheet">
    <link href="${ctx}/resources/css/style.css?v=3.2.0" rel="stylesheet">
    <link href="${ctx}/resources/img/favicon.ico" rel="icon">
    <link href="${ctx}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">

    <div class="tabs-container">

        <div class="panel-body">

            <!--查询条件begin-->
            <div class="ibox float-e-margins">
                <div class="ibox-content clearfix">
                    <form role="form" class="form-inline" action="${ctx}/user/export" id="queryForm">
                        <shiro:hasRole name="001">
                            <button type="button" class="btn btn-primary" id="expor-tbutton">导出文件</button>
                        </shiro:hasRole>
                        <shiro:hasPermission name="/user/upload">
                            <div class="form-group">
                                <label class="btn btn-info pull-right" for="_upload_box">上传附件</label>
                                <input type="file" id="_upload_box" name="file" class="hide">
                            </div>
                        </shiro:hasPermission>
                        <shiro:hasRole name="001">
                            <div class="form-group">
                                <button type="button" class="btn btn-primary" id="add-btn">新增用户</button>
                            </div>
                        </shiro:hasRole>
                    </form>
                </div>
            </div>
            <!--查询条件end-->

            <div class="row">
                <div class="col-sm-12 tabs">

                    <div class="ibox-content clearPadding">
                        <!--活动数据表格-->
                        <div class="jqGrid_wrapper">
                            <table id="loanRecords_table_1"></table>
                            <div id="pager_list_1"></div>
                        </div>
                    </div>

                    <%--<div class="ibox-content">
                        <label class="font-noraml" style="width: 15%;">合计</label>
                        笔数: <label class="font-noraml count-label" style="width: 15%;"> </label>
                        放款金额:<label class="amt-label" style="width: 15%;"></label>
                    </div>--%>

                </div>
            </div>

        </div>
    </div>
</div>
</div>

</div>

<%--弹窗--%>
<div id="menu-modal-form" class="modal fade" aria-hidden="true">
    <div class="modal-dialog" style="width: 600px;">
        <%--<div class="modal-body">--%>
        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>添加用户</h5>
                </div>
                <div class="ibox-content">
                    <form class="form-horizontal m-t" id="userForm" action="${ctx}/user/addUser" accept-charset="UTF-8">
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">姓名:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="请输入名字">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="age" class="col-sm-2 control-label">年龄:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="age" name="age"
                                       placeholder="请输入年龄">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="address" class="col-sm-2 control-label">地址:</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="address" name="address"
                                       placeholder="请输入地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="status" class="col-sm-2 control-label">状态:</label>
                            <div class="col-sm-10">
                                <select class="form-control" id="status" name="status"
                                        placeholder="请输入名字">
                                    <option>禁用</option>
                                    <option>启用</option>
                                </select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default"
                                    data-dismiss="modal">关闭
                            </button>
                            <button type="button" id="add-user-btn" class="btn btn-primary">
                                提交
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%--end弹窗--%>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${ctx}/resources/js/jquery-2.1.1.min.js"></script>
<script src="${ctx}/resources/js/plugins/form/jquery.form.js"></script>
<script src="${ctx}/resources/js/plugins/ajaxfileupload/jquery.ajaxfileupload.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${ctx}/resources/js/bootstrap.min.js?v=3.4.0"></script>
<script src="${ctx}/resources/js/plugins/layer/laydate/laydate.js"></script>
<!-- jqGrid -->
<script src="${ctx}/resources/js/plugins/jqgrid/i18n/grid.locale-cn.js?0820"></script>
<script src="${ctx}/resources/js/plugins/jqgrid/jquery.jqGrid.min.js?0820"></script>
<%--jquery.validate--%>
<script src="${ctx}/resources/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctx}/resources/js/plugins/validate/additional-methods.min.js"></script>
<script src="${ctx}/resources/js/plugins/validate/messages_zh.min.js"></script>
<!-- 自定义js -->
<script src="${ctx}/resources/js/contabs.js"></script>
<%--bootstrap-treeview.js
<script src="${ctx}/resources/js/plugins/bootstrap-treeview/bootstrap-treeview.js"></script>--%>
<%--sweetalert.min.js--%>
<script src="${ctx}/resources/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${ctx}/resources/js/plugins/layer/layer.min.js"></script>
<!-- Page-Level Scripts -->
<script>
    upload();
    //修改
    function operatorLoanRecord(obj) {
        if ($(obj).hasClass("updateLink")) {
            //记录id
            $("#id").val($(obj).attr("recordId"));
            $("#payamtsuccess").val($(obj).attr("payamtsuccess"));
            $("#payamt").val($(obj).attr("payamt"));
            $("#conId").val($(obj).attr("contractid"));
            $("#paySuccessAmt,#remark").val("");
            $(".error").empty().removeClass("error");
        } else if ($(obj).hasClass("logLink")) {
            getLogs($(obj).attr("recordId"));
        }
        //$("#payDate").val(new Date().toLocaleDateString());
    }

    $(document).ready(function () {

        $.jgrid.defaults.styleUI = 'Bootstrap';

        // Configuration for jqGrid
        $("#loanRecords_table_1").jqGrid({

            beforeRequest: function () {
                jQuery("#loanRecords_table_1").jqGrid("clearGridData");
            },	//请求前的函数
            url: '${ctx}/user/getLoanRecords',
            postData: {
                "flag": 0
            },
            datatype: 'json',
            height: 'auto',
            width: 'auto',
            shrinkToFit: false,//设置了此配置，将会参考每列配置的列宽，对列宽进行重新计算
            mtype: 'post',
            colNames: ['id', '姓名', '年龄', '地址'],
            colModel: [
                {name: 'id', index: 'id', align: 'center', width: '200'},
                {name: 'name', index: 'name', width: '200', align: 'center'},
                {name: 'age', index: 'age', width: '200', align: 'center'},
                {name: 'address', index: 'address', width: '200', align: 'center'}
            ],
            jsonReader: {
                root: "viewJsonData", //json中的viewJsonData，对应Page中的 viewJsonData。
                page: "curPage", //json中curPage，当前页号,对应Page中的curPage。
                total: "totalPages",//总的页数，对应Page中的pageSizes
                records: "totalRecords",//总记录数，对应Page中的totalRecords
                repeatitems: false,
                id: "0"
            },
            reloadOnExpand: false,//设置为false，展开时仅加载一次数据，随后的点击图标点击操作只是显示或者隐藏子表格，而不会再发送ajax重新获取数据
            pager: '#pager_list_1',
            rowNum: 10,
            rowList: [10, 20, 30],
            // sortname: 'id',
            // sortorder: 'desc',
            viewrecords: false,
            rownumbers: true,
            onSelectRow: function (rowid) {
                var rowData = $("#loanRecords_table_1").jqGrid("getRowData",rowid);
                openUserMenu(rowData.id);
            },
            loadComplete: function (data) {

            },
            gridview: true,
        }).closest(".ui-jqgrid-bdiv").css({"overflow-x": "scroll"});
        jQuery("#loanRecords_table_1").jqGrid('setFrozenColumns');
        // Add selection
    });
    $("#expor-tbutton").click(function () {
        $("#queryForm").submit();
    });
    function upload() {
        $('#_upload_box').ajaxfileupload({
            action: '${ctx}/user/upload',
            dataType: "json",
            validate_extensions: false,
            //params: {contractId: contractId, flag: 3, appId: 0},//post contractId to server
            onStart: function () {
                 loadingId = layer.load();
            },
            onComplete: function (result) {
                 layer.close(loadingId);
                if (result=="success") {
                    swal({
                        title:"上传成功",
                        text:"",
                        type:"success"
                    });
                } else if(result=="typeerror"){
                    swal({
                        title:"上传文件类型错误",
                        text:"",
                        type:"error"
                    });
                }else {
                    swal({
                        title:"上传文件异常",
                        text:"",
                        type:"error"
                    });
                }
            }
        });
    }

    function openUserMenu(id){
        window.parent.ParentMenuItem({
            attr: function () {
                return "${ctx}/user/userMenu?userId="+id+"&date="+new Date();
            },
            text: function () {
                return "用户菜单";
            },
            data: function () {
                return new Date().getTime();
            }
        });
        return false;
    }
    //新增用户
    $("#add-btn").click(function(){
        //parentMenu();
        $("#menu-modal-form").modal();
    });
    $("#add-user-btn").click(function(){
        $("#userForm").ajaxSubmit({
            type:"post",
            url:"${ctx}/user/addUser",
            data:$(this).serialize(),
            success:function(){
                swal({
                    title:"添加成功",
                    text:"",
                    type:"success"
                },function(){
                    $(this).resetForm(); // 提交后重置表单
                    $('#menu-modal-form').modal('hide')
                });
                reload();
            }
        });
    });
    function reload(){
        $("#loanRecords_table_1").trigger("reloadGrid");
    }

</script>

</body>
</html>