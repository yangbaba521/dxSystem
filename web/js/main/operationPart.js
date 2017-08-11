/**
 * Created by sunyuyang on 17/4/13.
 */

$(document).ready(function () {
    $('#home_body_action_area_infoAction_function_singleAdd_button').on('click',showSingleAdd);
    $('#partInfo_info>div>input[type=text]').on('blur',checkPartInfoInput);
    $('#partInfo_button_cancel').on('click',closeSingleAdd);
    $('#partInfo_info_areaCode_input').on('blur',getAreaName);

    $('#home_body_action_area_select>div[name = type]>select').val('none').on('change',showSelectValue);
    $('#home_body_action_area_select>div[name=value]>div[name=partTarget]>select').val('equal').on('change',showAreTarget);
    $('#home_body_action_area_select>div[name=value]>div[name=partTarget]>input[name=max]').hide();
    $('#home_body_action_area_select_value input[type = text]').val('').attr('class','readyInput').on('blur',checkPartInfoInput)
    $('#home_body_action_area_select_action>input[name=submit]').on('click',searchPartList);
    showTable('none','none');
});

function getAreaName() {
    var info = new Array();
    if($(this).attr('class') == 'accessInput'){
        info[0] = $(this).val();
        $.ajax({
            url: 'partInfoAction/getAreaName',
            type: "POST",
            traditional:true,
            data:{info:info},
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#partInfo_info_areaName_input').val(data.info).attr('class','accessInput');
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                    $('#partInfo_info_areaName_input').val('').attr('class','readyInput');
                }
            },
            error: function () {
                $('#Box p').text('获取区域名称失败,请重新尝试,或者联系管理员');
                easyDialog.open({
                    container: 'Box'
                });
                $('#partInfo_info_areaName_input').val('').attr('class','readyInput');
            }
        });
    }
}

function searchPartList() {
    var type = $('#home_body_action_area_select>div[name = type]>select').val();
    var target = $('#home_body_action_area_select>div[name=value]>div[name=normal]>input');
    var max = $('#home_body_action_area_select_value>div[name = partTarget]>input[name=max]');
    var min = $('#home_body_action_area_select_value>div[name = partTarget]>input[name=min]');
    var partTargetType = $('#home_body_action_area_select>div[name = value]>div[name=partTarget]>select').val();
    if(type == 'partTarget') {
        if (partTargetType == 'custom') {
            min.trigger('blur');
            max.trigger('blur');
            if (min.attr('class') == "accessInput" && max.attr('class') == "accessInput") {
                showTable(type, partTargetType + "," + min.val() + "," + max.val());
            } else {
                min.trigger('blur');
                if (min.attr('class') == "accessInput") {
                    showTable(type, partTargetType + "," + min.val());
                }
            }
        }
    }else if (type == 'none') {
        showTable(type, target.val());
    } else {
        target.trigger('blur');
        if (target.attr('class') == "accessInput") {
            showTable(type, target.val())
        }
    }
    
}

function showAreTarget() {
    $('#home_body_action_area_select_value>div[name=partTarget]>input,#home_body_action_area_select_value>div[name=partTarget]>span').hide();
    if($(this).val() == 'custom'){
        $('#home_body_action_area_select_value>div[name=partTarget]>input,#home_body_action_area_select_value>div[name=partTarget]>span').fadeIn();
    }else{
        $('#home_body_action_area_select_value>div[name=partTarget]>input[name=min]').fadeIn();
    }
}

function showSelectValue() {
    $('#home_body_action_area_select_value>div').hide();
    $('#home_body_action_area_select_value').fadeIn();
    if($(this).val() == 'partTarget'){
        $('#home_body_action_area_select_value>div[name=partTarget]').fadeIn();
    }else if($(this).val() == 'none'){
        $('#home_body_action_area_select_value').hide();
    }else{
        $('#home_body_action_area_select_value>div[name=normal]').fadeIn();
        $('#home_body_action_area_select_value>div[name=normal]>input[type=text]').attr('name',$(this).val());
    }
}

function signAddPartInfo() {
    var target = Array.prototype.slice.call($('#partInfo_info>div>input[type=text]'));
    var wrong = target.filter(function (input) {
        if($(input).trigger('blur',checkPartInfoInput).attr('class') != 'accessInput'){
            return true;
        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var partInfo = new Array();
        target.forEach(function (input) {
            partInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'partInfoAction/singleAddPart',
            type: "POST",
            traditional:true,
            data:{partInfo:partInfo},
            dataType: 'json',
            success: function (data) {
                hide_wait(wait);
                if (data.result == 'success') {
                    closeSingleAdd();
                    $('#Box p').text('新建成功');
                    easyDialog.open({
                        container: 'Box'
                    });
                    $('#home_body_action_area_select_action>input[name=submit]').trigger('click');
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                }
            },
            error: function () {
                hide_wait(wait);
                $('#Box p').text('新建区部,请重新尝试,或者联系管理员');
                easyDialog.open({
                    container: 'Box'
                });
            }
        });
    }else{
        $('#Box p').text('输入的数据仍然有不合规的项或者未填写的项,请检查后');
        easyDialog.open({
            container: 'Box'
        });
        return false;
    }
}

function signUpdatePartInfo() {
    var target = Array.prototype.slice.call($('#partInfo_info>div>input[type=text]'));
    var wrong = target.filter(function (input) {
        if($(input).trigger('blur').attr('class') != 'accessInput'){
            return true;
        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var partInfo = new Array();
        target.forEach(function (input) {
            partInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'partInfoAction/updatePartInfo',
            type: "POST",
            traditional:true,
            data:{partInfo:partInfo},
            dataType: 'json',
            success: function (data) {
                hide_wait(wait);
                if (data.result == 'success') {
                    closeSingleAdd();
                    $('#Box p').text('修改成功');
                    easyDialog.open({
                        container: 'Box'
                    });
                     $('#home_body_action_area_select_action>input[name=submit]').trigger('click');
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                }
            },
            error: function () {
                hide_wait(wait);
                $('#Box p').text('修改区部失败,请重新尝试,或者联系管理员');
                easyDialog.open({
                    container: 'Box'
                });
            }
        });
    }else{
        $('#Box p').text('输入的数据仍然有不合规的项或者未填写的项,请检查后');
        easyDialog.open({
            container: 'Box'
        });
        return false;
    }
}

function checkPartInfoInput() {
    var rule = reg[$(this).attr('name')];
    if($(this).val() != '') {
        if (rule.test($(this).val())) {
            $(this).attr('class', 'accessInput').siblings('img').hide();
            return true;
        } else {
            $(this).attr('class', 'wrongInput').siblings('img').fadeIn();
            $('#Box p').text(pointText[$(this).attr('name')]);
            easyDialog.open({
                container: 'Box'
            });
        }
    }else{
        $(this).attr('class', 'readyInput').siblings('img').hide();
    }
    return false;
}

function showSingleAdd() {
    $('#partInfo_button_submit').off('click').on('click',signAddPartInfo);
    $('#partInfo_info>div>input').val('').attr('class','readyInput');
    $('#partInfo_info>div>img').hide();
    $('#back_board').fadeIn();
    $('#partInfo').fadeIn();
}

function showSingleUpdate(data) {
    var infoList = Array.prototype.slice.call($('#partInfo_info>div>input'));
    $('#partInfo_button_submit').off('click').on('click',signUpdatePartInfo);
    $('#partInfo_info>div>input[name = partCode]').attr('disabled','true');
    $('#partInfo_info>div>input').val('').attr('class','readyInput');
    $('#partInfo_info>div>img').hide();
    infoList.forEach(function (target) {
        $(target).val(data.shift());
    })
    $('#back_board').fadeIn();
    $('#partInfo').fadeIn();
}

function closeSingleAdd() {
    $('#partInfo_info>div>input[name = partCode]').removeAttr('disabled');
    $('#back_board').hide();
    $('#partInfo').hide();
}

function revisePart(target) {
    var code = $(target).text();
    var wait = showWait();
    $.ajax({
        url: 'partInfoAction/getPartInfo',
        type: "POST",
        traditional:true,
        data:{partCode:code},
        dataType: 'json',
        success: function (data) {
            hide_wait(wait);
            if (data.result == 'success') {
                showSingleUpdate(data.partInfo);
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            hide_wait(wait);
            $('#Box p').text('无法获取区部信息,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function deletePartComfirm(target) {
    $('#ComBox_sure').off('click').on('click',{partCode:$(target).attr('name')},deletePart);
    $('#ComBox_nosure').off('click').on('click',easyDialog.close);
    $('#ComBox p').text('您确定要删除'+$(target).attr('name')+'吗?');
    easyDialog.open({
        container: 'ComBox'
    });
}

function deletePart(event) {
    $.ajax({
        url: 'partInfoAction/deletePart',
        type: "POST",
        traditional:true,
        data:{partCode:event.data.partCode},
        dataType: 'json',
        success: function (data) {
            if (data.result == 'success') {
                $('#Box p').text("删除成功");
                easyDialog.open({
                    container: 'Box'
                });
                $('#home_body_action_area_select_action>input[name=submit]').trigger('click');
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            hide_wait(wait);
            $('#Box p').text('无法删除区部信息,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function showTable(type,val) {
    $('#partInfoList').DataTable({
        processing: true,
        bSort: true, //排序功能
        bAutoWidth:true,
        bLengthChange: false,
        serverSide: true,   //启用服务器端分页
        searching: false,    //禁用原生搜索
        destroy:true,
        ajax: {
            url: "partInfoAction/getPartList",
            type: 'POST',
            data:{"type":type,"val":val}
        },
        columnDefs : [
            {
                targets : 0,//操作按钮目标列
                render : function(data, type,row) {
                    var html = '<span onclick="javascript:revisePart(this);" class="codeLine" name="partCode">'+ data +'</span>';
                    return html;
                }
            } ,
            {
                "targets" : 7,//操作按钮目标列
                "render" : function(data, type,row) {
                    var html = "<input type='button' onclick='javascript:deletePartComfirm(this);' class='list_button' name = '"+data+"' value='删除' />"
                    return html;
                }
            } ],
        oLanguage: {
            sLengthMenu: "每页显示 _MENU_ 条记录",
            sZeroRecords: "抱歉， 没有找到",
            sInfo: "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            sInfoEmpty: "没有数据",
            sInfoFiltered: "(从 _MAX_ 条数据中检索)",
            oPaginate: {
                sFirst: "首页",
                sPrevious: "前一页",
                sNext: "后一页",
                sLast: "尾页"
            },
            sZeroRecords: "没有检索到数据",
            sSearch: "搜索",
        },
    });
}