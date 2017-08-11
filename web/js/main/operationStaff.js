/**
 * Created by sunyuyang on 17/4/13.
 */

$(document).ready(function () {
    getSelectTypeInfo();
    $('#home_body_action_area_infoAction_function_singleAdd_button').on('click',showSingleAdd);
    $('#staffInfo_info>div>input[type=text]').on('blur',checkStaffInfoInput);
    $('#staffInfo_button_cancel').on('click',closeSingleAdd);
    $('#staffInfo_info_belongCode_input').on('blur',getBelongName);

    $('#home_body_action_area_select>div[name = type]>select').val('none').on('change',showSelectValue);
    $('#home_body_action_area_select>div[name=value]>div[name=staffTarget]>select').val('equal').on('change',showAreTarget);
    $('#home_body_action_area_select>div[name=value]>div[name=staffTarget]>input[name=max]').hide();
    $('#home_body_action_area_select_value input[type = text]').val('').attr('class','readyInput').on('blur',checkStaffInfoInput)
    $('#home_body_action_area_select_action>input[name=submit]').on('click',searchStaffList);
    $('#staffInfo_info>div[name=staffBirth]>input,' +
        '#staffInfo_info>div[name=trainStart]>input,' +
        '#staffInfo_info>div[name=trainEnd]>input,' +
        '#staffInfo_info>div[name=codeIn]>input,' +
        '#staffInfo_info>div[name=levelStartTime]>input,' +
        '#staffInfo_info>div[name=quiteTime]>input,' +
        '#staffInfo_info>div[name=staffNatureChange]>input').fdatepicker({
        format: 'yyyy-mm-dd'
    });
    showTable('none','none');

});

function createSelectType(level, levelStart, state, nature, channel, edu) {
    var html1 = '';
    var html2 = '';
    for(var i =0; i<level.length/2; i ++){
        html1 += '<option value="'+ level[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+level[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=level]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=staffLevel]>select').append(html1);
    html1 = '';
    html2 = '';
    for(var i =0; i<levelStart.length/2; i ++){
        html1 += '<option value="'+ levelStart[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+levelStart[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=levelStart]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=levelStart]>select').append(html1);
    html1 = '';
    html2 = '';
    for(var i =0; i<state.length/2; i ++){
        html1 += '<option value="'+ state[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+state[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=staffState]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=staffState]>select').append(html1);
    html1 = '';
    html2 = '';
    for(var i =0; i<nature.length/2; i ++){
        html1 += '<option value="'+ nature[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+nature[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=staffNature]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=staffNature]>select').append(html1);
    html1 = '';
    html2 = '';
    for(var i =0; i<channel.length/2; i ++){
        html1 += '<option value="'+ channel[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+channel[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=staffChannel]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=staffChannel]>select').append(html1);
    html1 = '';
    html2 = '';
    for(var i =0; i<edu.length/2; i ++){
        html1 += '<option value="'+ edu[i*2+0] +'">'+level[i*2+1]+'</option>';
        html2 += '<option>'+edu[i*2+1]+'</option>';
    }
    $('#staffInfo_info>div[name=education]>select').append(html2);
    $('#home_body_action_area_select_value>div[name=staffEdu]>select').append(html1);
}

function getSelectTypeInfo() {
    $.ajax({
        url: 'staffInfoAction/getSelectTypeInfo',
        type: "POST",
        traditional:true,
        dataType: 'json',
        success: function (data) {
            if (data.result == 'success') {
                createSelectType(data.level,data.levelStart,data.state,data.nature,data.channel,data.edu);
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            $('#Box p').text('获取筛选信息失败,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function getBelongName() {
    var info = new Array();
    if($(this).attr('class') == 'accessInput'){
        info[0] = $(this).val();
        $.ajax({
            url: 'staffInfoAction/getBelongtName',
            type: "POST",
            traditional:true,
            data:{info:info},
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#staffInfo_info_belongName_input').val(data.belongName).attr('class','accessInput');
                    $('#staffInfo_info_leaderName_input').val(data.leaderName).attr('class','accessInput');
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                    $('#staffInfo_info_belongName_input').val('').attr('class','readyInput');
                    $('#staffInfo_info_leaderName_input').val('').attr('class','readyInput');
                }
            },
            error: function () {
                $('#Box p').text('获取所属名称失败,请重新尝试,或者联系管理员');
                easyDialog.open({
                    container: 'Box'
                });
                $('#staffInfo_info_belongName_input').val('').attr('class','readyInput');
                $('#staffInfo_info_leaderName_input').val('').attr('class','readyInput');
            }
        });
    }
}

function searchStaffList() {
    var type = $('#home_body_action_area_select>div[name = type]>select').val();
    var target = $('#home_body_action_area_select_value>div[name=normal]>input');
    var state = $('#home_body_action_area_select_value>div[name=staffState]>select');
    var level = $('#home_body_action_area_select_value>div[name=staffLevel]>select');
    var edu = $('#home_body_action_area_select_value>div[name=staffEdu]>select');
    var nature = $('#home_body_action_area_select_value>div[name=staffNature]>select');
    var channel = $('#home_body_action_area_select_value>div[name=staffChannel]>select');
    if(type == 'staffLevel'){
        showTable(type,level.val());
    }else if(type == 'staffState'){
        showTable(type,state.val());
    }else if(type == 'staffEdu'){
        showTable(type,edu.val());
    }else if(type == 'staffNature'){
        showTable(type,nature.val());
    }else if(type == 'staffChannel'){
        showTable(type,channel.val());
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
    $('#home_body_action_area_select_value>div[name=staffTarget]>input,#home_body_action_area_select_value>div[name=staffTarget]>span').hide();
    if($(this).val() == 'custom'){
        $('#home_body_action_area_select_value>div[name=staffTarget]>input,#home_body_action_area_select_value>div[name=staffTarget]>span').fadeIn();
    }else{
        $('#home_body_action_area_select_value>div[name=staffTarget]>input[name=min]').fadeIn();
    }
}

function showSelectValue() {
    $('#home_body_action_area_select_value>div').hide();
    $('#home_body_action_area_select_value').fadeIn();
    if($(this).val() == 'staffState'){
        $('#home_body_action_area_select_value>div[name=staffState]').fadeIn();
    }else if($(this).val() == 'none'){
        $('#home_body_action_area_select_value').hide();
    }else if($(this).val() == 'staffLevel'){
        $('#home_body_action_area_select_value>div[name=staffLevel]').fadeIn();
    }else if($(this).val() == 'staffNature'){
        $('#home_body_action_area_select_value>div[name=staffNature]').fadeIn();
    }else if($(this).val() == 'staffEdu'){
        $('#home_body_action_area_select_value>div[name=staffEdu]').fadeIn();
    }else if($(this).val() == 'staffChannel'){
        $('#home_body_action_area_select_value>div[name=staffChannel]').fadeIn();
    }else{
        $('#home_body_action_area_select_value>div[name=normal]').fadeIn();
        $('#home_body_action_area_select_value>div[name=normal]>input[type=text]').attr('name',$(this).val());
    }
}

function signAddStaffInfo() {
    var target = Array.prototype.slice.call($('#staffInfo_info>div>input[type=text]'));
    var accessNull = new Array('trainEnd','codeIn','quiteTime','staffPhone','agentCode','businessCode','bankCode','Emaill','staffNatureChange'); 
    var wrong = target.filter(function (input) {
        var checkClass = $(input).trigger('blur',checkStaffInfoInput).attr('class');
        if( checkClass != 'accessInput'){
            if(checkClass == 'readyInput'){
                if(accessNull.some(function (name) {
                        if($(input).attr('name') == name){
                            return true;
                        }else{
                            return false;
                        }
                    })){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }

        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var allTarget = Array.prototype.slice.call($('#staffInfo_info>div>input[type=text],#staffInfo_info>div>select'));
        var staffInfo = new Array();
        allTarget.forEach(function (input) {
            staffInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'staffInfoAction/singleAddStaff',
            type: "POST",
            traditional:true,
            data:{staffInfo:staffInfo},
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
                $('#Box p').text('新建人员失败,请重新尝试,或者联系管理员');
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

function signUpdateStaffInfo() {
    var target = Array.prototype.slice.call($('#staffInfo_info>div>input[type=text]'));
    var accessNull = new Array('trainEnd','codeIn','quiteTime','staffPhone','agentCode','businessCode','bankCode','Emaill','staffNatureChange');
    var wrong = target.filter(function (input) {
        var checkClass = $(input).trigger('blur',checkStaffInfoInput).attr('class');
        if( checkClass != 'accessInput'){
            if(checkClass == 'readyInput'){
                if(accessNull.some(function (name) {
                        if($(input).attr('name') == name){
                            return true;
                        }else{
                            return false;
                        }
                    })){
                    return false;
                }else{
                    return true;
                }
            }else{
                return true;
            }

        }else{
            return false;
        }
    });
    if(wrong.length == 0){
        var allTarget = Array.prototype.slice.call($('#staffInfo_info>div>input[type=text],#staffInfo_info>div>select'));
        var staffInfo = new Array();
        allTarget.forEach(function (input) {
            staffInfo.push($(input).val());
        })
        var wait = showWait();
        $.ajax({
            url: 'staffInfoAction/updateStaffInfo',
            type: "POST",
            traditional:true,
            data:{staffInfo:staffInfo},
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
                $('#Box p').text('修改组员失败,请重新尝试,或者联系管理员');
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

function checkStaffInfoInput() {
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
    $('#staffInfo_button_submit').off('click').on('click',signAddStaffInfo);
    $('#staffInfo_info>div>input').val('').attr('class','readyInput');
    $('#staffInfo_info>div>img').hide();
    $('#back_board').fadeIn();
    $('#staffInfo').fadeIn();
}

function showSingleUpdate(data) {
    var infoList = Array.prototype.slice.call($('#staffInfo_info>div>input,#staffInfo_info>div>select'));
    $('#staffInfo_button_submit').off('click').on('click',signUpdateStaffInfo);
    $('#staffInfo_info>div>input[name = staffCode]').attr('disabled','true');
    $('#staffInfo_info>div>input').val('').attr('class','readyInput');
    $('#staffInfo_info>div>img').hide();
    infoList.forEach(function (target) {
        $(target).val(data.shift());
    })
    $('#back_board').fadeIn();
    $('#staffInfo').fadeIn();
}

function closeSingleAdd() {
    $('#staffInfo_info>div>input[name = staffCode]').removeAttr('disabled');
    $('#back_board').hide();
    $('#staffInfo').hide();
}

function reviseStaff(target) {
    var code = $(target).text();
    var wait = showWait();
    $.ajax({
        url: 'staffInfoAction/getStaffInfo',
        type: "POST",
        traditional:true,
        data:{staffCode:code},
        dataType: 'json',
        success: function (data) {
            hide_wait(wait);
            if (data.result == 'success') {
                showSingleUpdate(data.staffInfo);
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            hide_wait(wait);
            $('#Box p').text('无法获取组员信息,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function deleteStaffComfirm(target) {
    $('#ComBox_sure').off('click').on('click',{staffCode:$(target).attr('name')},deleteStaff);
    $('#ComBox_nosure').off('click').on('click',easyDialog.close);
    $('#ComBox p').text('您确定要删除'+$(target).attr('name')+'吗?');
    easyDialog.open({
        container: 'ComBox'
    });
}

function deleteStaff(event) {
    $.ajax({
        url: 'staffInfoAction/deleteStaff',
        type: "POST",
        traditional:true,
        data:{staffCode:event.data.staffCode},
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
    $('#staffInfoList').DataTable({
        processing: true,
        bSort: true, //排序功能
        bAutoWidth:true,
        bLengthChange: false,
        serverSide: true,   //启用服务器端分页
        searching: false,    //禁用原生搜索
        destroy:true,
        ajax: {
            url: "staffInfoAction/getStaffList",
            type: 'POST',
            data:{"type":type,"val":val}
        },
        columnDefs : [
            {
                targets : 0,//操作按钮目标列
                render : function(data, type,row) {
                    var html = '<span onclick="javascript:reviseStaff(this);" class="codeLine" name="staffCode">'+ data +'</span>';
                    return html;
                }
            } ,
            {
                targets : 8,//操作按钮目标列
                render : function(data, type,row) {
                    var html = "<input type='button' onclick='javascript:deleteStaffComfirm(this);' class='list_button' name = '"+data+"' value='删除' />"
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