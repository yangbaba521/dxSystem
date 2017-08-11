/**
 * Created by sunyuyang on 17/4/12.
 */
$(document).ready(function () {
    $('#home_title_quite').on('click',quiteSystem);
    $('#home_body_guide_area li div').on('click',oAc);
    $('#home_body_guide_area_report_pages_sum').on('click',{tage:'reportSum.jsp'},jump);
    $('#home_body_guide_area_rank_pages_persion').on('click',{tage:'rankPersion.jsp'},jump);
    $('#home_body_guide_area_rank_pages_team').on('click',{tage:'rankTeam.jsp'},jump);
    $('#home_body_guide_area_update_pages_daily').on('click',{tage:'updateDaily.jsp'},jump);
    $('#home_body_guide_area_update_pages_area').on('click',{tage:'operationArea.jsp'},jump);
    $('#home_body_guide_area_update_pages_part').on('click',{tage:'operationPart.jsp'},jump);
    $('#home_body_guide_area_update_pages_team').on('click',{tage:'operationTeam.jsp'},jump);
    $('#home_body_guide_area_update_pages_staff').on('click',{tage:'operationStaff.jsp'},jump);
    $('#home_body_guide_area_set_pages_baseInfo').on('click',{tage:'setBaseInfo.jsp'},jump);
});

function oAc() {
    var type = $(this).attr('name');
    if(type == 'close'){
        $(this).attr('name','open').siblings('ul').fadeIn();
        $(this).children('img[name=oAc_button]').attr('src','img/close.png');
        return true;
    }else if(type == 'open'){
        $(this).attr('name','close').siblings('ul').hide();
        $(this).children('img[name=oAc_button]').attr('src','img/open.png');
        return true;
    }
    return false;
}

function quiteSystem() {
    $('#ComBox_sure').off('click').on('click',cleanCookie);
    $('#ComBox_nosure').off('click').on('click',easyDialog.close);
    $('#ComBox p').text('您确定要退出系统吗?');
    easyDialog.open({
        container: 'ComBox'
    });
}


function cleanCookie() {
    $.ajax({
        type: 'POST',
        url: "pubForMain/quiteSystem",
        data: {},
        cache: false,
        async: false,
        dataType: 'json',
        success: function (data) {
            if(data.result == "success"){
                window.location.href="login.jsp";
            }else{
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            $('#Box p').text(data.cause);
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

window.onload = function () {
    getBaseInfo();
}

function getBaseInfo() {
    $.ajax({
        type: 'POST',
        url: "pubForMain/getBaseInfo",
        data: {},
        cache: false,
        async: false,
        dataType: 'json',
        success: function (data) {
            if(data.result == "success"){
                $('#home_title_userInfo').text('您好,'+ data.partment + ',' + data.userName);
                //setLevel(data.level);
            }else{
                $('#sureBox_sure').off('click').on('click',{tage:'login.jsp'},jump);
                $('#sureBox p').text(data.cause);
                easyDialog.open({
                    container: 'sureBox'
                });
            }
        },
        error: function () {
            $('#sureBox_sure').off('click').on('click',{tage:'login.jsp'},jump);
            $('#sureBox p').text("登录信息异常,请重新登录");
            easyDialog.open({
                container: 'sureBox'
            });
        }
    });
}

function setLevel(level) {
    var admin_funciton = new Array('#home_body_guide_area_update');
    if(level == 'guest'){
        for (var func in admin_funciton)
        {
            $(admin_funciton[func]).hide();
        }
    }
    return true;
}

function jump(event) {
    var tage = event.data.tage;
    window.location.href=tage;
}

function showWait() {
    $('#back_board').fadeIn();
    $('#wait').fadeIn();
    return rotation();
}

function rotation(){
    var angle = 0;
    return setInterval(function(){
        angle +=1;
        $('#wait>img').rotate(angle);
    }, 5);
}

function hide_wait(id) {
    $('#back_board').hide();
    $('#wait').hide();
    clearInterval(id);
}

