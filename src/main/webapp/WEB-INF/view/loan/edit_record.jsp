<!DOCTYPE html>
<%@ include file="../include/include.jsp" %>
<%@ page session="false" pageEncoding="UTF-8" %>
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
    <%--bootstrap-treeview.css
    <link href="${ctx}/resources/css/plugins/bootstrap-treeview/bootstrap-treeview.css" rel="stylesheet">
    <link href="${ctx}/resources/css/font-awesome.css" rel="stylesheet">--%>
    <%--sweetalert.css--%>
    <link href="${ctx}/resources/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="${ctx}/resources/css/attach.css" rel="stylesheet">
    <title>passport 平台</title>

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <form style="text-align: center;" action="${ctx}/loan/releaseLoan" id="planForm" name="planForm">
        <input type="hidden" value="${loanRecordAllInfos.pay_success_amt}" id="paiedSuccessAmt">
        <input type="hidden" value="${loanRecordAllInfos.pay_amt}" id="payAmt" id="payAmt">
        <input type="hidden" name="adjunctId" id="adjunctId">
        <div class="row">
            <div class="col-sm-12">
                <!--查询条件-->
                <div class="ibox float-e-margins">
                    <div class="ibox-title bt_min_h40">
                        <h5>条件查询</h5>
                    </div>
                    <div class="ibox-content clearfix text-center">
                        <div class="form-inline">
                            <!--活动名称和活动时间-->
                            <div class="form-group" style="width: 233px;">
                                <label class="font-noraml">请选择状态:</label>
                                <select name="payStatus" id="updateStatus" class="form-control" style="width: 150px;">
                                    <option>请选择状态</option>
                                    <option value="5">部分成功</option>
                                    <option value="3">放款成功</option>
                                    <option value="4" selected>放款失败</option>
                                </select>
                            </div>
                            <div class="form-group" style="width: 233px;">
                                <label class="font-noraml">实际放款日期:</label>
                                <input name="payDate" id="payDate" class="laydate-icon form-control layer-date clear">
                            </div>
                            <br>
                            <div class="form-group m-t" style="width: 233px;">
                                <label class="font-noraml">放款成功金额:</label>
                                <input id="paySuccessAmt" name="paySuccessAmt" class="form-control" type="text">
                            </div>
                            <div class="form-group m-t" style="width: 233px;">
                                <label class="font-noraml">备注:</label>
                                <input id="remark" name="remark" class="form-control" type="text">
                            </div>
                            <br>
                            <div class="form-group m-t" style="width: 100px;">
                                <h4 class="text-left m-t" style="height: 30px">
                                    <input type="file" id="_upload_box" name="file" class="hide">
                                    <label class="btn btn-info pull-right" for="_upload_box">上传附件</label></h4>
                            </div>
                            <ul class="file-list" id="file-list">

                            </ul>
                            <br>
                            <button type="button" class="btn btn-primary m-t" id="save_btn">提交</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row" id="planDiv" style="display:none;text-align: right;">
            <div class="ibox-title bt_min_h40">
                <h5>还款计划</h5>
            </div>
            <input type="hidden" name="id" value="${loanRecordId}"/>

            <div class="ibox-content clearfix text-center">
                <div class="form-inline">
                    <!--活动名称和活动时间-->
                    <div class="form-group">
                        <label class="font-noraml">还款日规则:</label>
                        <select name="repaymentDateMode" id="repaymentDateMode" class="form-control"
                                style="width: 150px;">
                            <option value="-1">请选择</option>
                            <option value="20"
                                    <c:if test='${loanRecordAllInfos.repayment_date_mode == 20}'>selected='selected'</c:if>>
                                20号
                            </option>
                            <option value="0"
                                    <c:if test='${loanRecordAllInfos.repayment_date_mode == 0}'>selected='selected'</c:if>>
                                任意
                            </option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="font-noraml">账单日:</label>
                        <input name="repayDay" class="form-control" id="repayDay"
                               value="${loanRecordAllInfos.repay_day}">
                    </div>
                </div>
            </div>

            <button type="button" class="btn btn-primary m-t" id="addBtn">新增</button>
            <button type="button" class="btn btn-primary m-t" id="delBtn" style=" margin-right: 168px">删减</button>
            <%--<button type="button" class="btn btn-primary m-t" id="saveBtn">提交</button>--%>
            <table style="width: 80%;height: 80%;margin:0 auto;" border="1" id="table-plan">
                <tr>
                    <th style="text-align: center">期数</th>
                    <th style="text-align: center">应还款日期</th>
                    <th style="text-align: center">应到本金</th>
                    <th style="text-align: center">应到利息</th>
                    <th style="text-align: center">应到服务费</th>
                </tr>
                <c:forEach items="${plans}" var="plan">
                    <tr>
                        <td><input class="form-control" name="periods" onblur='checkNum(this)' value="${plan.period}">
                        </td>
                        <td style="width: 213px;"><input class="laydate-icon form-control layer-date" name="repayDates"
                                                         value="${plan.repayDate}"></td>
                        <td><input onblur="checkNum(this)" class="form-control" name="capitals" value="${plan.capital}">
                        </td>
                        <td><input onblur="checkNum(this)" class="form-control" name="interests"
                                   value="${plan.interest}"></td>
                        <td><input onblur="checkNum(this)" class="form-control" name="serviceFees"
                                   value="${plan.serviceFee}"></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </form>
</div>
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
<%--上传
<script src="${ctx}/resources/js/borrow_edit.js?v=201702043"></script>
--%>
<script src="${ctx}/resources/js/plugins/layer/layer.min.js"></script>
<script src="${ctx}/resources/js/releaseLoan.js"></script>
<!-- Page-Level Scripts -->
<script>
    $(document).ready(function () {
        checkMessage();
        upload();

        // laydate
        var payDate = {
            elem: '#payDate',
            format: 'YYYY/MM/DD',
            min: '1970-01-01 00:00:00',
            max: '2099-06-16 23:59:59',
            istime: true,
            istoday: false
        };
        laydate(payDate);

        laydate({
            elem: '#table-plan .layer-date',
            format: 'YYYY/MM/DD', // 分隔符可以任意定义，该例子表示只显示年月
            min: '1970-01-01',
            max: '2099-06-16',
            istime: true,
            istoday: false,
            choose: function (datas) { //选择日期完毕的回调
                //alert('得到：'+datas);
            }
        });

    })

    function getPlants() {
        $.ajax({
            url: "${ctx}/loan/getInstallmentPlans",
            async: false,
            data: {"contractId": contractId},
            success: function (datas) {
                var trs;
                $.each(datas, function (i, e) {
                    trs += "<tr>" +
                            "<td><input class='form-control' name='periods' value='" + e.period + "' readonly></td>" +
                            "<td style='width: 213px;'><input class='laydate-icon form-control layer-date' name='repayDates' value='" + e.repayDate + "'></td>" +
                            "<td><input class='form-control' name='capitals' value='" + e.capital + "'></td>" +
                            "<td><input class='form-control' name='interests' value='" + e.interest + "'></td>" +
                            "<td><input class='form-control' name='serviceFees' value='" + e.serviceFee + "'></td>" +
                            "</tr>";
                });
                $("#table-plan").find("tr:gt(0)").remove();
                $("#table-plan").append(trs);
            },
            error: function () {
                swal("获取还款计划异常");
            }
        });
    }
    function checkNum(obj) {
        if ($(obj).attr("name") == "periods") {
            var RegExp = /^\d+$/;
            if (!RegExp.test($(obj).val())) {
                $(obj).val("");
            }
        } else {
            var RegExp = /^\d+(\.\d+)?$/;
            if (!RegExp.test($(obj).val())) {
                $(obj).val("");
            }
        }
    }

    function checkMessage() {
        if (!'${plans}') {
            $("#save_btn").attr("disabled", "disabled");
            $("label[for='_upload_box']").attr("disabled", "disabled");
            swal({
                title: "获取还款计划异常",
                text: "",
                type: "error"
            },function(){
                window.parent.closeCurrentPage()
            });
        }
        if('${loanRecordAllInfos}' == 'ERROR') {
            swal({
                title: "此笔已放款成功",
                text: "",
                type: "warning"
            },function(){
                window.parent.closeCurrentPage();
            });
        }
    }

    function upload() {

        var contractId = '${loanRecordAllInfos.contract_id}';

        $('#_upload_box').ajaxfileupload({
            action: '${ctx}/attach/upload',
            dataType: "json",
            validate_extensions: false,
            params: {contractId: contractId, flag: 3, appId: 0},//post contractId to server
            onStart: function () {
                loadingId = layer.load();
            },
            onComplete: function (result) {
                layer.close(loadingId);
                if (result.success) {
                    $("#adjunctId").val(result.data.id);
                    addAdjunctFile(result.data);
                } else {
                    swal("上传失败，" + result.message);
                }
            }
        });

        /**
         * 追加附件信息
         * @param file
         */
        function addAdjunctFile(file) {
            var tpl = "<li data-file='" + file["id"] + "' style='text-align: left;'><input type='hidden' name='adjunctFileId' value='" + file["id"]
                    + "'/><a target='_blank' href='" + file["url"]
                    + "'  class='i_" + file['suffix'] + "' title=''><span>" + file["name"] + "</span></a><a class='remove' data-file-id='" + file["id"] + "' title='删除' onclick='removeAdjunctFile(this)'><i class='glyphicon glyphicon-trash'></i></a></li>";
            //$(tpl).appendTo("#file-list");
            $("#file-list").empty().append(tpl);
        }

        //end upload
        $("#addBtn").click(function () {
            var trs = createElement();
            $("#table-plan tbody").append(trs);
        });
        function createElement(){
            var tr = document.createElement("tr");
            var td = document.createElement("td");
            var periods = document.createElement("input");
            periods.setAttribute("class","form-control");
            periods.setAttribute("name","periods");
            periods.setAttribute("onblur","checkNum(this)");
            td.appendChild(periods)

            var td1 = document.createElement("td");
            var repayDates = document.createElement("input");
            repayDates.setAttribute("class","laydate-icon form-control layer-date");
            repayDates.setAttribute("name","repayDates");
            repayDates.setAttribute("onclick","laydate()");
            td1.appendChild(repayDates);

            var td2 = document.createElement("td");
            var capitals = document.createElement("input");
            capitals.setAttribute("class","form-control");
            capitals.setAttribute("name","capitals");
            capitals.setAttribute("onblur","checkNum(this)");
            td2.appendChild(capitals);

            var td3 = document.createElement("td");
            var interests = document.createElement("input");
            interests.setAttribute("class","form-control");
            interests.setAttribute("name","interests");
            interests.setAttribute("onblur","checkNum(this)");
            td3.appendChild(interests);

            var td4 = document.createElement("td");
            var serviceFees = document.createElement("input");
            serviceFees.setAttribute("class","form-control");
            serviceFees.setAttribute("name","serviceFees");
            serviceFees.setAttribute("onblur","checkNum(this)");
            td4.appendChild(serviceFees);

            tr.appendChild(td);
            tr.appendChild(td1);
            tr.appendChild(td2);
            tr.appendChild(td3);
            tr.appendChild(td4);
            return tr;
        }

        $("#delBtn").click(function () {
            var index = $("table tbody tr:last").index();
            if (index > 0) {
                $("table tbody tr:eq(" + index + ")").remove();
            }
        });

    }
</script>
</body>
</html>