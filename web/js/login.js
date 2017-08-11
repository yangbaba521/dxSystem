/**
 * Created by sunyuyang on 17/4/7.
 */
$(document).ready(function () {
    $('#login_info_user_input').on('click',ready_inputUser).on('blur',{'reg':/^[a-zA-Z]\w{5,17}$/},check_userInput);
    $('#login_info_pwd_input').on('click',ready_inputPwd).on('blur',{'reg':/^\w{6,12}$/},check_pwdInput);
    $('#pwd_selectWatch').on('click', select_pwdType);
    $('#login_remember_off').on('click', rememberPwd);
    $('#login_button_submit').on('click', login_submit);
})

var point_user = "请输入用户名";
var point_pwd = "请输入密码";

function ready_inputUser() {
    var text = $(this).val();
    if(text == point_user){
        $(this).val("").attr('class',"input_text");
    }
    $(this).parent('div').attr('class','click_border');
}

function check_userInput(event) {
    var text = $(this).val();
    var reg = event.data.reg;
    if(text == ""){
        $(this).val(point_user).attr('class','point_text');
        $(this).parent('div').attr('class','normal_border');
    }else{
        if(reg.test(text)){
            $(this).parent('div').attr('class','normal_border');
        }else{
            $(this).parent('div').attr('class','wrong_border');
        }
    }
}

function ready_inputPwd() {
    var text = $(this).val();
    if(text == point_pwd){
        $(this).val("").attr('class',"input_text").attr('type','password');
    }
    $(this).parent('div').attr('class','click_border');
}

function check_pwdInput(event) {
    var text = $(this).val();
    var reg = event.data.reg;
    if(text == ""){
        $(this).val(point_pwd).attr('class','point_text').attr('type','text');
        $(this).parent('div').attr('class','normal_border');
    }else{
        if(reg.test(text)){
            $(this).parent('div').attr('class','normal_border');
        }else{
            $(this).parent('div').attr('class','wrong_border');
        }
    }
}

function select_pwdType() {
    var model = $(this).attr('name');
    if($(this).siblings('input').val() == point_pwd){
        $('#Box p').text("您尚未输入密码");
        easyDialog.open({
            container: 'Box'
        });
    }else {
        if (model == 'Cipher') {
            $(this).attr('src', 'img/login_pwd_unwatch.png');
            $(this).attr('name', 'Plain');
            $(this).siblings('input').attr('type', 'text');
        } else if (model == 'Plain') {
            $(this).attr('src', 'img/login_pwd_watch.png');
            $(this).attr('name', 'Cipher');
            $(this).siblings('input').attr('type', 'password');
        } else {
            $('#Box p').text("密码显示模式错误,默认设置成密文模式");
            easyDialog.open({
                container: 'Box'
            });
            $(this).attr('src', 'img/login_pwd_watch.png').attr('name', 'Cipher').siblings('input').attr('type', 'password');
        }
    }
}

function rememberPwd() {
    var model = $(this).attr('name');
    if(model == 'remember_off'){
        $('#login_remember_off_radio').trigger('click');
        $(this).attr('src','img/remember_on.png').attr('name','remember_on');
    }else if(model == 'remember_on'){
        $('#login_remember_off_radio').attr('checked',false);
        $(this).attr('src','img/remember_off.png').attr('name','remember_off');
    }else{
        $('#Box p').text("记住密码模式错误,默认设置成不记录模式");
        easyDialog.open({
            container: 'Box'
        });
        $('#login_rember_off_radio').attr('checked','false');
        $(this).attr('src','img/remember_off.png').attr('name','remember_off');
    }
}

function login_submit() {
    var user = $('#login_info_user_input').val();
    var pwd = $('#login_info_pwd_input').val();
    var remember_type = $('#login_remember_off_radio:checked').val();
    if(remember_type){
        remember_type = "remember";
    }else {
        remember_type = "unremember";
    }
    $.ajax({
        type: 'POST',
        url: "login/loginSubmit",
        data: {user: user, pwd: pwd, type: remember_type},
        cache: false,
        async: false,
        dataType: 'json',
        success: function (data) {
            if(data.result == "success"){
                window.location.href = "home.jsp"
            }else{
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            $('#Box p').text("无法登录,请联系管理员");
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}

window.onload=function () {
    getUserInfoRem();
}

function getUserInfoRem() {
    $.ajax({
        type: 'POST',
        url: "login/getLoginInfo",
        data: {},
        cache: false,
        async: false,
        dataType: 'json',
        success: function (data) {
            if(data.result == "success"){
                if(data.cause == "Rem" ) {
                    $('#login_info_pwd_input').val(data.pwd).attr('class','input_text').attr('type','password').trigger('blur');
                    $('#login_info_user_input').val(data.user).attr('class','input_text').trigger('blur');
                    $('#login_remember_off').trigger('click');
                }else{
                    $('#login_info_pwd_input').val(point_pwd);
                    $('#login_info_user_input').val(point_user);
                }
            }else{
                $('#Box p').text(data.cause);
                easyDialog.open({
                    container: 'Box'
                });
            }
        },
        error: function () {
            $('#Box p').text("无法登录,请联系管理员");
            easyDialog.open({
                container: 'Box'
            });
        }
    });
}