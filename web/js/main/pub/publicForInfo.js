/**
 * Created by sunyuyang on 17/7/28.
 */
var fileSelect = {
    single : false,
    bath : false,
    all : false
};
var reg = {
    areaCode : /^[0-9]{12}$/,
    areaName :/^[\u4e00-\u9fa5]{2,15}$/,
    partCode : /^[0-9]{15}$/,
    partName :/^[\u4e00-\u9fa5]{2,15}$/,
    teamCode : /^[0-9]{18}$/,
    teamName :/^[\u4e00-\u9fa5]{2,15}$/,
    staffCode : /^[0-9]{9}$/,
    staffName :/^[\u4e00-\u9fa5]{2,5}$/,
    belongCode : /^[0-9]{12,18}$/,
    belongName :/^[\u4e00-\u9fa5]{2,15}$/,
    leaderCode : /^[0-9]{9}$/,
    leaderName : /^[\u4e00-\u9fa5]{2,5}$/,
    areaTarget : /^[0-9]{1,10}$/,
    partTarget : /^[0-9]{1,10}$/,
    teamTarget : /^[0-9]{1,10}$/,
    staffBirth:/^[0-9-]{8,10}$/,
    trainStart:/^[0-9-]{8,10}$/,
    trainEnd:/^[0-9-]{8,10}$/,
    codeIn:/^[0-9-]{8,10}$/,
    levelStartTime:/^[0-9-]{8,10}$/,
    staffNatureChange:/^[0-9-]{8,10}$/,
    quiteTime:/^[0-9-]{8,10}$/,
    staffID:/^[0-9x]{18,19}$/,
    staffPhone:/^[1][34578][0-9]{9}$/,
    agentCode: /^[0-9]{10,20}$/,
    businessCode:/^[0-9]{10,20}$/,
    bankCode:/^[0-9]{10,20}$/,
    Email:/^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
    min : /^[0-9]{1,10}$/,
    max : /^[0-9]{1,10}$/
}
var pointText = {
    areaCode : '只支持12位数字',
    areaName :'只支持2-15位中文',
    partCode : '只支持15位数字',
    partName :'只支持2-15位中文',
    teamCode : '只支持18位数字',
    teamName :'只支持2-15位中文',
    staffCode : '只支持9位数字',
    staffName :'只支持2-5位中文',
    leaderCode : '只支持9位数字',
    leaderName : '只支持2-5位中文',
    belongCode : '只支持12-18位数字',
    belongName : '只支持2-15位中文',
    areaTarget : '只支持1-10位数字',
    partTarget : '只支持1-10位数字',
    teamTarget : '只支持1-10位数字',
    staffBirth:'请输入正确日期',
    trainStart:'请输入正确日期',
    trainEnd:'请输入正确日期',
    codeIn:'请输入正确日期',
    staffNatureChange:'请输入正确日期',
    levelStartTime:'请输入正确日期',
    quiteTime:'请输入正确日期',
    staffID:'请输入正确身份证号',
    staffPhone:'请输入正确手机号码',
    agentCode:'请输入正确代理证号',
    businessCode:'请输入正确展业证号',
    bankCode:'请输入正确银行卡号',
    Email:'请输入正确邮箱',
    min : '只支持1-10位数字',
    max : '只支持1-10位数字'
}


var fileUpdateUrl = new Object();
fileUpdateUrl.single = '';
fileUpdateUrl.bath = 'infoAction/addBatch';
fileUpdateUrl.all = 'infoAction/reviseAll';

$(document).ready(function () {
    $('#home_body_action_reviseAll_button').on('click',selectActionFile);
    $('#home_body_action_batchAdd_button').on('click',selectActionFile);
    $('#home_body_action_file').on('change',comfirmUpdateFile);
    $('#home_body_action_download_button').on('click',exportFile);
});

function exportFile() {
    var wait = showWait();
    $.ajax({
        url: 'infoAction/exportFile',
        type: "POST",
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (data) {
            hide_wait(wait);
            if (data.result == 'success') {
                window.location.href= "fileInfo/"+data.fileName;
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            hide_wait(wait);
            $('#Box p').text('下载文件失败,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function updateFile() {
    var type = '';
    for(var target in fileSelect){
        if(fileSelect[target]){
            type = target;
            break;
        }
    }
    var wait = showWait();
    var formData = new FormData();
    formData.append("areaFile", document.getElementById('home_body_action_file').files[0]);
    $.ajax({
        url: fileUpdateUrl[type],
        type: "POST",
        data: formData,
        contentType: false,
        processData: false,
        dataType: 'json',
        success: function (data) {
            hide_wait(wait);
            if (data.result == 'success') {
                $('#Box p').text("上传完成");
                easyDialog.open({
                    container: 'Box'
                });
                showTable('none','none');
            } else {
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            hide_wait(wait);
            $('#Box p').text('上传文件失败,请重新尝试,或者联系管理员');
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

function comfirmUpdateFile() {
    if(!($('#home_body_action_file').val() == '')){
        $('#ComBox_sure').off('click').on('click', updateFile);
        $('#ComBox_nosure').off('click').on('click', easyDialog.close);
        $('#ComBox p').text('您确定要上传文件:' + $('#home_body_action_file').val() + " 吗?");
        easyDialog.open({
            container: 'ComBox'
        });
    }
}

function selectActionFile() {
    for(var type in fileSelect){
        fileSelect[type] = false;
    }
    switch ($(this).attr('name')){
        case 'reviseAll':{
            fileSelect.all = true;
            break;
        }
        case 'addBatch':{
            fileSelect.bath = true;
            break;
        }
        case 'addSign':{
            fileSelect.single = true;
            break;
        }
        default: break;
    }
    $('#home_body_action_file').trigger('click').val('');
    return true;
}


