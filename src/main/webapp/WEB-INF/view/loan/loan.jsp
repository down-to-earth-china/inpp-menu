<!DOCTYPE html>
<%@ include file="../include/include.jsp" %>
<%@ page session="false" pageEncoding="UTF-8" %>
<html lang="zh">

<head>
    <meta http-equiv="content-type" content="text/html, charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/resources/bootstraptable/bootstrap-table.css" rel="stylesheet">
    <link href="${ctx}/resources/img/favicon.ico" rel="icon">
</head>

<body>
<div class="container">

    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">passport 平台</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="${ctx}/">Home</a></li>
                    <li><a href="${ctx}/usermgmt">用户管理</a></li>
                    <li><a href="${ctx}/orgmgmt">组织机构管理</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                           aria-expanded="false">Dropdown <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">Action</a></li>
                            <li><a href="#">Another action</a></li>
                            <li><a href="#">Something else here</a></li>
                            <li role="separator" class="divider"></li>
                            <li class="dropdown-header">Nav header</li>
                            <li><a href="#">Separated link</a></li>
                            <li><a href="#">One more separated link</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="${ctx}/error">error</a></li>
                    <li><a href="${ctx}/logout">退出</a></li>
                </ul>
            </div><!--/.nav-collapse -->
        </div><!--/.container-fluid -->
    </nav>

    <!-- Main component for a primary marketing message or call to action -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#">组织机构列表</a></li>
    </ul>

    <div class="panel-body" style="padding-bottom:0px;">
        <div class="panel panel-default">
            <div class="panel-heading">查询条件</div>
            <div class="panel-body">
                <form id="formSearch" class="form-horizontal">
                    <div class="form-group" style="margin-top:15px">
                        <label class="control-label col-sm-1" for="txt_search_username">用户名称</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_search_username">
                        </div>
                        <label class="control-label col-sm-1" for="txt_search_status">状态</label>
                        <div class="col-sm-2">
                            <input type="text" class="form-control" id="txt_search_status">
                        </div>
                        <div class="col-sm-2" style="text-align:left;">
                            <button type="button" style="margin-left:50px" id="btn_query" class="btn btn-primary">查询
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="toolbar" class="btn-group">
            <button id="btn_add" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>新增
            </button>
            <button id="btn_edit" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>修改
            </button>
            <button id="btn_delete" type="button" class="btn btn-default">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>禁用
            </button>
        </div>
        <table id="tb_users"></table>
    </div>

    <footer class="footer">
        <p>&copy; 2016 Company, Inc.</p>
    </footer>
</div>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="${ctx}/resources/jquery/1.12.4/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="${ctx}/resources/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/bootstraptable/bootstrap-table.js"></script>
<script src="${ctx}/resources/bootstraptable/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript">
    $(function () {

        //1.初始化Table
        var oTable = new TableInit();
        oTable.Init();

        //2.初始化Button的点击事件
        var oButtonInit = new ButtonInit();
        oButtonInit.Init();

    });


    var TableInit = function () {
        var oTableInit = new Object();
        //初始化Table
        oTableInit.Init = function () {
            $('#tb_users').bootstrapTable({
                url: '${ctx}/orgmgmt/getOrgList',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: oTableInit.queryParams,//传递参数（*）
                sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                strictSearch: true,
                showColumns: true,                  //是否显示所有的列
                showRefresh: true,                  //是否显示刷新按钮
                minimumCountColumns: 2,             //最少允许的列数
                clickToSelect: true,                //是否启用点击选中行
                height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
                showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [{
                    checkbox: true
                }, {
                    field: 'userName',
                    title: '账号',
                    align: 'center'
                }, {
                    field: 'email',
                    title: '邮箱'
                }, {
                    field: 'status',
                    title: '是否可用'
                }, {
                    field: 'createTime',
                    title: '注册时间'
                },]
            });
        };

        //得到查询的参数
        oTableInit.queryParams = function (params) {
            var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit,   //页面大小
                offset: params.offset,  //页码
                username: $("#txt_search_username").val(),
                status: $("#txt_search_status").val()
            };
            return temp;
        };
        return oTableInit;
    };


    var ButtonInit = function () {
        var oInit = new Object();
        var postdata = {};

        oInit.Init = function () {
            //初始化页面上面的按钮事件
        };

        return oInit;
    };

    //加载tb_user出错，提示错误信息
    $('#tb_users').on('load-error.bs.table', function (event, status, res) {
        console.log("status = " + status + ", res = " + res)

    });
</script>
</body>
</html>