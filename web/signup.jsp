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
    <link href="css/signup.css" rel="stylesheet" type="text/css"/>
    <link href="css/public.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="signup_bg">
    <div id="signup">
        <div id="signup_info_text" class="">
            <div class="">
                <label class="" for="signup_info_text_userCode">用户名:</label>
                <input type="text" id="signup_info_text_userCode" class="normal_input"/>
                <span class="">(支持数字,英文,下划线,字母开头长度6-18个字符)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_pwd">密码:</label>
                <input type="password" id="signup_info_text_pwd" class="normal_input"/>
                <span class="">(支持数字,英文,下划线,长度6-12个字符)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_repwd">确认密码:</label>
                <input type="password" id="signup_info_text_repwd" class="normal_input"/>
                <span class="">(与密码一致)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_userName">姓名:</label>
                <input type="text" id="signup_info_text_userName" class="normal_input"/>
                <span class="">(只支持中文,长度2-5个字符)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_cpmpany">机构:</label>
                <select id="signup_info_text_cpmpany" class="normal_input">
                    <option selected = "selected">请选择</option>
                    <option value="jsdy">江苏多元业务部</option>
                </select>
                <span class="">(必选)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_partment">部门:</label>
                <select id="signup_info_text_partment" class="normal_input">
                    <option selected = "selected" >请选择</option>
                    <option value="zjls">总经理室</option>
                    <option value="dynq">多元内勤</option>
                </select>
                <span class="">(必选)</span>
            </div>
            <div id="signup_info_text_ChooseQuestion" class="">
                <label class="" for="signup_info_text_questionChoose">安全问题:</label>
                <select id="signup_info_text_questionChoose" class="normal_input">
                    <option selected = "selected" >请选择</option>
                    <option >自定义问题</option>
                    <option >我爸爸的名字是</option>
                    <option >我妈妈的名字是</option>
                    <option >我爸爸的生日是</option>
                    <option >我妈妈的生日是</option>
                    <option >我妻子的名字是</option>
                    <option >我丈夫的名字是</option>
                    <option >我的出生地是</option>
                    <option >我的小学是</option>
                </select>
                <span class="">(必选)</span>
            </div>
            <div id="signup_info_text_PersionQuestion" class="hide">
                <label class="" for="signup_info_text_question">自定义:</label>
                <input type="text" id="signup_info_text_question" class="normal_input"/>
                <span class="">(支持数字,英文,中文下划线,长度1-20个字符)</span>
            </div>
            <div class="">
                <label class="" for="signup_info_text_answer">问题答案:</label>
                <input type="text" id="signup_info_text_answer" class="normal_input"/>
                <span class="">(支持数字,英文,中文下划线,长度1-20个字符)</span>
            </div>
        </div>
        <div id="signup_button" class="">
            <input type="button" id="signup_button_submit" value="提交"/>
            <input type="button" id="signup_button_back" value="返回"/>
        </div>
    </div>
</div>
<div class="hide_box" id="Box">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>提示</h4>
    <p></p>
</div>

<div class="hide_box" id="ComBox">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="ComBox_sure" value="确定"/>
    <input type="button" id="ComBox_nosure" value="不确定"/>
</div>

<div class="hide_box" id="sureBox">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="sureBox_sure" value=""/>
</div>

<script src="js/pub/jquery-1.10.2.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/signup.js"></script>

</body>
</html>
