/**
 * Created by sunyuyang on 17/4/13.
 */

$(document).ready(function () {
    $('#home_body_action_area_infoAction_function_singleAdd_button').on('click',showSingleAdd);
    $('#teamInfo_info>div>input[type=text]').on('blur',checkTeamInfoInput);
    $('#teamInfo_button_cancel').on('click',closeSingleAdd);
    $('#teamInfo_info_partCode_input').on('blur',getPartName);

    $('#home_body_action_area_select>div[name = type]>select').val('none').on('change',showSelectValue);
    $('#home_body_action_area_select>div[name=value]>div[name=teamTarget]>select').val('equal').on('change',showAreTarget);
    $('#home_body_action_area_select>div[name=value]>div[name=teamTarget]>input[name=max]').hide();
    $('#home_body_action_area_select_value input[type = text]').val('').attr('class','readyInput').on('blur',checkTeamInfoInput)
    $('#home_body_action_area_select_action>input[name=submit]').on('click',searchTeamList);
    showTable('none','none');
});

function getPartName() {
    var info = new Array();
    if($(this).attr('class') == 'accessInput'){
        info[0] = $(this).val();
        $.ajax({
            url: 'teamInfoAction/getPartName',
            type: "POST",
            traditional:true,
            data:{info:info},
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#teamInfo_info_partName_input').val(data.info).attr('class','accessInput');
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                    $('#teamInfo_info_partName_input').val('').attr('class','readyInput');
                }
            },
            error: function () {
                $('#Box p').text('获取区部名称失败,请重新尝试,或者联系管理员');
                easyDialog.open({
                    container: 'Box'
                });
                $('#teamInfo_info_partName_input').val('').attr('class','readyInput');
            }
        });
    }
}

function searchTeamList() {
    var type = $('#home_body_action_area_select>div[name = type]>select').val();
    var target = $('#home_body_action_area_select>div[name=value]>div[name=normal]>input');
    var max = $('#home_body_action_area_select_value>div[name = teamTarget]>input[name=max]');
    var min = $('#home_body_action_area_select_value>div[name = teamTarget]>input[name=min]');
    var teamTargetType = $('#home_body_action_area_select>div[name = value]>div[name=teamTarget]>select').val();
    if(type == 'teamTarget'){
        if(teamTargetType == 'custom'){
            min.trigger('blur');
            max.trigger('blur');
            if(min.attr('class') == "accessInput" && max.attr('class') == "accessInput"){
                showTable(type,teamTargetType+","+min.val()+","+max.val());
            }
        }else{
            min.trigger('blur');
            if(min.attr('class') == "accessInput"){
                showTable(type,teamTargetType+","+min.val());
            }
        }
    }else if(type == 'none'){
        showTable(type,target.val());
    }else{
        target.trigger('blur');
        if(target.attr('class') == "accessInput"){
            showTable(type,target.val())
        }
    }
}

function showAreTarget() {
    $('#home_body_action_area_select_value>div[name=teamTarget]>input,#home_body_action_area_select_value>div[name=teamTarget]>span').hide();
    if($(this).val() == 'custom'){
        $('#home_body_action_area_select_value>div[name=teamTarget]>input,#home_body_action_area_select_value>div[name=teamTarget]>span').fadeIn();
    }else{
        $('#home_body_action_area_select_value>div[name=teamTarget]>input[name=min]').fadeIn();
    }
}

function showSelectValue() {
    $('#home_body_action_area_select_value>div').hide();
    $('#home_body_action_area_select_value').fadeIn();
    if($(this).val() == 'teamTarget'){
        $('#home_body_action_area_select_value>div[name=teamTarget]').fadeIn();
    }else if($(this).val() == 'none'){
        $('#home_body_action_area_select_value').hide();
    }else{
        $('#home_body_action_area_select_value>div[name=normal]').fadeIn();
        $('#home_body_action_area_select_value>div[name=normal]>input[type=text]').attr('name',$(this).val());
    }
}

function signAddTeamInfo() {
    var target = Array.prototype.slice.call($('#teamInfo_info>div>input[type=text]'));
    var wrong = target.filter(function (input) {
        if($(input).trigger('blur',checkTeamInfoInput).attr('class') != 'accessInput'){
            return true;
        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var teamInfo = new Array();
        target.forEach(function (input) {
            teamInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'teamInfoAction/singleAddTeam',
            type: "POST",
            traditional:true,
            data:{teamInfo:teamInfo},
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

function signUpdateTeamInfo() {
    var target = Array.prototype.slice.call($('#teamInfo_info>div>input[type=text]'));
    var wrong = target.filter(function (input) {
        if($(input).trigger('blur').attr('class') != 'accessInput'){
            return true;
        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var teamInfo = new Array();
        target.forEach(function (input) {
            teamInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'teamInfoAction/updateTeamInfo',
            type: "POST",
            traditional:true,
            data:{teamInfo:teamInfo},
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

function checkTeamInfoInput() {
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
    $('#teamInfo_button_submit').off('click').on('click',signAddTeamInfo);
    $('#teamInfo_info>div>input').val('').attr('class','readyInput');
    $('#teamInfo_info>div>img').hide();
    $('#back_board').fadeIn();
    $('#teamInfo').fadeIn();
}

function showSingleUpdate(data) {
    var infoList = Array.prototype.slice.call($('#teamInfo_info>div>input'));
    $('#teamInfo_button_submit').off('click').on('click',signUpdateTeamInfo);
    $('#teamInfo_info>div>input[name = teamCode]').attr('disabled','true');
    $('#teamInfo_info>div>input').val('').attr('class','readyInput');
    $('#teamInfo_info>div>img').hide();
    infoList.forEach(function (target) {
        $(target).val(data.shift());
    })
    $('#back_board').fadeIn();
    $('#teamInfo').fadeIn();
}

function closeSingleAdd() {
    $('#teamInfo_info>div>input[name = teamCode]').removeAttr('disabled');
    $('#back_board').hide();
    $('#teamInfo').hide();
}

function reviseTeam(target) {
    var code = $(target).text();
    var wait = showWait();
    $.ajax({
        url: 'teamInfoAction/getTeamInfo',
        type: "POST",
        traditional:true,
        data:{teamCode:code},
        dataType: 'json',
        success: function (data) {
            hide_wait(wait);
            if (data.result == 'success') {
                showSingleUpdate(data.teamInfo);
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

function deleteTeamComfirm(target) {
    $('#ComBox_sure').off('click').on('click',{teamCode:$(target).attr('name')},deleteTeam);
    $('#ComBox_nosure').off('click').on('click',easyDialog.close);
    $('#ComBox p').text('您确定要删除'+$(target).attr('name')+'吗?');
    easyDialog.open({
        container: 'ComBox'
    });
}

function deleteTeam(event) {
    $.ajax({
        url: 'teamInfoAction/deleteTeam',
        type: "POST",
        traditional:true,
        data:{teamCode:event.data.teamCode},
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
    $('#teamInfoList').DataTable({
        processing: true,
        bSort: true, //排序功能
        bAutoWidth:true,
        bLengthChange: false,
        serverSide: true,   //启用服务器端分页
        searching: false,    //禁用原生搜索
        destroy:true,
        ajax: {
            url: "teamInfoAction/getTeamList",
            type: 'POST',
            data:{"type":type,"val":val}
        },
        columnDefs : [
            {
                targets : 0,//操作按钮目标列
                render : function(data, type,row) {
                    var html = '<span onclick="javascript:reviseTeam(this);" class="codeLine" name="teamCode">'+ data +'</span>';
                    return html;
                }
            } ,
            {
                "targets" : 7,//操作按钮目标列
                "render" : function(data, type,row) {
                    var html = "<input type='button' onclick='javascript:deleteTeamComfirm(this);' class='list_button' name = '"+data+"' value='删除' />"
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