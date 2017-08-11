/**
 * Created by sunyuyang on 17/3/27.
 */
$(document).ready(function () {
    $('#findPwd_user_info_input').on('blur',{'reg':/^\w{4,20}$/}, check_input);
    $('#findPwd_info_text_answer_input').on('blur',{'reg':/^[\u4E00-\u9FA5A-Za-z0-9_]{1,20}$/}, check_input);
    $('#findPwd_button_submitUser').on('click',submit_user);
    $('#findPwd_button_submitQ').on('click',submit_answer);
    $('#findPwd_button_back').on('click',{'tage':'login.jsp'},jump);
    $('#findPwd_user_info_input').on('click', ready_input);
    $('#findPwd_info_text_answer_input').on('click', ready_input);
})

function ready_input() {
    $(this).attr('class','click_border');
}

function jump(event) {
    var tage = event.data.tage;
    window.location.href=tage;
}

function submit_answer() {
    var tage = $('#findPwd_info_text_answer_input');
    if(tage.attr('class') == 'normal_input'){
        get_pwd();
        return true;
    }else{
        $('#Box p').text('答案存在不合规请检查后再次输入');
        easyDialog.open({
            container: 'Box'
        });
        return false;
    }
}

function submit_user(){
    var tage = $('#findPwd_user_info_input');
    if(tage.attr('class') == 'normal_input'){
        get_question();
        return true;
    }else{
        $('#Box p').text('用户名存在不合规请检查后再次输入');
        easyDialog.open({
            container: 'Box'
        });
        return false;
    }
}

function get_pwd() {
    var ans = $('#findPwd_info_text_answer_input');
    if(ans.val() != "" && ans.attr('class') != 'wrong_input') {
        $.ajax({
            type: 'POST',
            url: "findPwd/getPwdInfo",
            data: {
                user: $('#findPwd_user_info_input').val(),
                answer: $('#findPwd_info_text_answer_input').val()
            },
            cache: false,
            async: false,
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#sureBox_sure').val('去登录').on('click', {'tage': 'login.jsp'}, jump);
                    $('#sureBox p').text("您的密码是:" + data.pwd);
                    easyDialog.open({
                        container: 'sureBox'
                    });
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                }
            },
            error: function () {
                $('#Box p').text("数据库错误");
                easyDialog.open({
                    container: 'Box'
                });
            }
        });
    }else {
        $('#Box p').text("输入存在不合规,请修改后再次提交");
        easyDialog.open({
            container: 'Box'
        });
    }
}

function get_question() {
    var user = $('#findPwd_user_info_input');
    if(user.val() != "" && user.attr('class') != 'wrong_input') {
        $.ajax({
            type: 'POST',
            url: "findPwd/getQuestionInfo",
            data: {
                user: $('#findPwd_user_info_input').val()
            },
            cache: false,
            async: false,
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#findPwd_user').hide();
                    $('#findPwd_button_submitUser').hide();
                    $('#findPwd_info_text').fadeIn();
                    $('#findPwd_button_submitQ').fadeIn();
                    $('#findPwd_info_text_span').text("安全问题: " + data.question);
                } else {
                    $('#Box p').text(data.cause);
                    easyDialog.open({
                        container: 'Box'
                    });
                }
            },
            error: function () {
                $('#Box p').text("数据库错误");
                easyDialog.open({
                    container: 'Box'
                });
            }
        });
    }else {
        $('#Box p').text("输入存在不合规,请修改后再次提交");
        easyDialog.open({
            container: 'Box'
        });
    }
}

function check_input(event) {
    var reg = event.data.reg;
    var text = $(this).val();
    if(text != "") {
        if (reg.test(text)) {
            $(this).attr('class', 'normal_input');
            return true;
        } else {
            $(this).attr('class', 'wrong_input');
            $('#Box p').text('只支持数字,英文,下划线,长度4-20个字符');
            easyDialog.open({
                container: 'Box'
            });
            return false;
        }
    }else{
        $(this).attr('class', 'normal_input');
        return true;
    }
}

window.onload = function () {
    clean();
}


function clean() {
    $('#findPwd input[type=text]').each(function () {
        $(this).val('');
    })
}