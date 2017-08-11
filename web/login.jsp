<%--
  Created by IntelliJ IDEA.
  User: sunyuyang
  Date: 17/3/17
  Time: 上午11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>电销业务查询管理系统</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link href="css/login.css" rel="stylesheet" type="text/css"/>
  <link href="css/public.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="login_bg">
  <div id="login_info" class="">
    <div id="login_info_user" class="normal_border">
      <img src="img/login_user.png" class="input_logo"/>
      <input type="text" id="login_info_user_input" class="point_text" value="请输入用户名"/>
    </div>
    <div id="login_info_pwd" class="normal_border">
      <img src="img/login_pwd.png" class="input_logo"/>
      <input type="text" id="login_info_pwd_input" class="point_text" value="请输入密码"/>
      <img src="img/login_pwd_watch.png" class="watch_logo" id="pwd_selectWatch" name="Cipher"/>
    </div>
  </div>
  <div id="login_remember" class="">
    <img src="img/remember_off.png" id="login_remember_off" class="login_remember_button" name="remember_off"/>
    <span id="login_remember_span">记住密码</span>
    <input type="radio" id="login_remember_off_radio" value="remember" class="hide"/>
  </div>
  <div id="login_button" class="">
    <input type="button" id="login_button_submit" value="登录"/>
  </div>

  <div id="login_alink" class="">
    <a id="login_alink_signup" href="signup.jsp">现在注册!</a>
    <a id="login_alink_forget" href="findPwd.jsp">忘记密码?</a>
  </div>
</div>
<div class="hide_box" id="Box">
  <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>提示</h4>
  <p></p>
</div>

<script src="js/pub/jquery-1.10.2.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/login.js"></script>

</body>
</html>
