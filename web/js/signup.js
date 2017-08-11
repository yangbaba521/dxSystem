/**
 * Created by sunyuyang on 17/3/24.
 */
$(document).ready(function () {
    $('#signup_info_text div input').on('click',ready_input);
    $('#signup_info_text_userCode').on('blur',{'reg':/^[a-zA-Z]\w{5,17}$/}, check_input);
    $('#signup_info_text_pwd').on('blur',{'reg':/^\w{6,12}$/},check_input).on('change',{'tage':$('#signup_info_text_repwd')},clean_input);
    $('#signup_info_text_repwd').on('blur', {'tage': $('#signup_info_text_pwd')},check_same)
    $('#signup_info_text_userName').on('blur',{'reg':/^[\u4e00-\u9fa5]{2,5}$/}, check_input);
    $('#signup_info_text_cpmpany').on('blur', check_select);
    $('#signup_info_text_partment').on('blur', check_select);
    $('#signup_info_text_question').on('blur',{'reg':/^[\u4E00-\u9FA5A-Za-z0-9_]{1,20}$/}, check_input);
    $('#signup_info_text_answer').on('blur',{'reg':/^[\u4E00-\u9FA5A-Za-z0-9_]{1,20}$/}, check_input);
    $('#signup_button_back').on('click',{'tage':'login.jsp'},jump);
    $('#signup_button_submit').on('click',submit_info);
    $('#signup_info_text_questionChoose').on('blur',select_question).on('blur',check_select);
})

function ready_input() {
    $(this).attr('class','click_border');
}

function select_question() {
   if($(this).val() == "自定义问题"){
        $('#signup_info_text_PersionQuestion').fadeIn();
   }else{
       $('#signup_info_text_PersionQuestion').hide();
       $('#signup_info_text_question').val($(this).val()).trigger('blur');
   }
}

function submit_info() {
    var tage = $('#signup_info_text div span');
    var user = $('#signup_info_text_userCode').val();
    var pwd = $('#signup_info_text_pwd').val();
    var userName = $('#signup_info_text_userName').val();
    var company = $('#signup_info_text_cpmpany').val();
    var partment = $('#signup_info_text_partment').val();
    var question = $('#signup_info_text_question').val();
    var answer = $('#signup_info_text_answer').val();
    var flag = true;
    tage.each(function () {
        if($(this).attr('class') != 'pass_span'){
            $('#Box p').text("仍然存在不合规的信息,请检查后再次提交");
            easyDialog.open({
                container: 'Box'
            });
            flag = false;
        }
    })
    if(flag) {
        $.ajax({
            type: 'POST',
            url: "signup/creatNewUser",
            data: {
                user: user,
                pwd: pwd,
                userName: userName,
                company: company,
                partment: partment,
                question: question,
                answer: answer
            },
            cache: false,
            async: false,
            dataType: 'json',
            success: function (data) {
                if (data.result == 'success') {
                    $('#sureBox_sure').val("去登录").on('click', {'tage': 'login.jsp'}, jump);
                    $('#sureBox p').text("用户建立成功");
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
    }
}

function jump(event) {
    var tage = event.data.tage;
    window.location.href=tage;
}

function check_input(event) {
    var reg = event.data.reg;
    var text = $(this).val();
    var tage = $(this).siblings('span');
    if(reg.test(text)){
        $(this).attr('class','normal_input');
        tage.attr('class','pass_span');
        return true;
    }else{
        $(this).attr('class','wrong_input');
        tage.attr('class','faill_span');
        return false;
    }

}

function check_same(event) {
    var tage = event.data.tage;
    var text = $(this).val();
    var text_span = $(this).siblings('span');
    if(text == tage.val()){
        $(this).attr('class','normal_input');
        text_span.attr('class','pass_span');
        return true;
    }else{
        $(this).attr('class','wrong_input');
        text_span.attr('class','faill_span');
        return false;
    }
}

function clean_input(event) {
    var tage = event.data.tage;
    var text = tage.siblings('span');
    tage.val('');
    text.attr('class','faill_span');
}

function check_select() {
    var text = $(this).val();
    var tage = $(this).siblings('span');
    if(text != '请选择'){
        $(this).attr('class','normal_input');
        tage.attr('class','pass_span');
        return true;
    }else{
        $(this).attr('class','wrong_input');
        tage.attr('class','faill_span');
        return false;
    }
}

window.onload = function () {
    set_input();
    set_select();
}

function set_input() {
    var tage = $('#signup_info_text div input');
    tage.each(function () {
        $(this).val('');
    })
    return true;
}

function set_select() {
    var tage = $('#signup_info_text div select');
    tage.each(function () {
        $(this).val("请选择");
    })
    return true;
}