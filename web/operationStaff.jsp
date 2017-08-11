<%--
  Created by IntelliJ IDEA.
  User: sunyuyang
  Date: 17/8/2
  Time: 上午9:34
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
    <link href="css/main/operationStaff.css" rel="stylesheet" type="text/css"/>
    <link href="css/public.css" rel="stylesheet" type="text/css"/>
    <link href="css/main/pub/publicForMain.css" rel="stylesheet" type="text/css"/>
    <link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
    <link href="css/foundation-datepicker.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="http://www.jq22.com/jquery/font-awesome.4.6.0.css">
</head>
<body>
<div id="home_title" class="">
    <img src="img/logo.png" id="home_title_logo">
    <span id="home_title_span">电销业务查询管理系统</span>
    <img src="img/quite.png" id="home_title_quite">
    <span id="home_title_userInfo"></span>
</div>
<div id="home_body" class="">
    <div id="home_body_guide" class="">
        <ul id="home_body_guide_area" class="">
            <li id="home_body_guide_area_report">
                <div name="close">
                    <img src="img/report.png" name="signLogo"/>
                    <span>查阅报表</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_report_pages" class="hide">
                    <li id="home_body_guide_area_report_pages_sum" class="guide_unselected">
                        <span>总结报表</span>
                    </li>
                </ul>
            </li>
            <li id="home_body_guide_area_rank">
                <div name="close">
                    <img src="img/rank.png" name="signLogo"/>
                    <span>排名结算</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_rank_pages" class="hide">
                    <li id="home_body_guide_area_rank_pages_persion" class="guide_unselected">
                        <span>个人排名</span>
                    </li>
                    <li id="home_body_guide_area_rank_pages_team" class="guide_unselected">
                        <span>小组排名</span>
                    </li>
                </ul>
            </li>
            <li id="home_body_guide_area_update">
                <div name="open">
                    <img src="img/update.png" name="signLogo"/>
                    <span>数据更新</span>
                    <img src="img/close.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_update_pages" class="">
                    <li id="home_body_guide_area_update_pages_area" class="guide_unselected">
                        <span>区域维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_part" class="guide_unselected">
                        <span>区部维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_team" class="guide_unselected">
                        <span>部组维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_staff" class="guide_selected">
                        <span>组员维护</span>
                    </li>
                </ul>

            </li>
            <li id="home_body_guide_area_set">
                <div name="close">
                    <img src="img/set.png" name="signLogo"/>
                    <span>用户设置</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_set_pages" class="hide">
                    <li id="home_body_guide_area_set_pages_baseInfo" class="guide_unselected">
                        <span>用户信息设置</span>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <div id="home_body_action" class="">
        <div id="home_body_action_area" class="">
            <div id="home_body_action_area_select" class="">
                <div id="home_body_action_area_select_type" name="type">
                    <span>筛选种类:</span>
                    <select>
                        <option value="none">不筛选</option>
                        <option value="staffCode">组员编号</option>
                        <option value="staffName">组员名称</option>
                        <option value="belongCode">所属编号</option>
                        <option value="belongName">所属名称</option>
                        <option value="leaderName">主管名称</option>
                        <option value="staffLevel">职级</option>
                        <option value="staffState">状态</option>
                        <option value="staffChannel">渠道</option>
                        <option value="staffEdu">学历</option>
                        <option value="staffNature">性质</option>
                    </select>
                </div>
                <div id="home_body_action_area_select_value" name="value">
                    <span>筛选值:</span>
                    <div name="normal">
                        <input type="text" class="readyInput"/>
                    </div>

                    <div name="staffLevel">
                        <select>
                        </select>
                    </div>

                    <div name="staffState">
                        <select>
                        </select>
                    </div>

                    <div name="staffChannel">
                        <select>
                        </select>
                    </div>

                    <div name="staffEdu">
                        <select>
                        </select>
                    </div>

                    <div name="staffNature">
                        <select>
                        </select>
                    </div>

                </div>
                <div id="home_body_action_area_select_action" name="button">
                    <input type="button" name="submit" class="list_button" value="查询"/>
                </div>
            </div>
            <table id="staffInfoList" class="">
                <thead>
                <tr>
                    <th>业务员代码</th>
                    <th>姓名</th>
                    <th>所属编号</th>
                    <th>所属名称</th>
                    <th>主管名称</th>
                    <th>性别</th>
                    <th>职级</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
            <div id="home_body_action_area_infoAction" class="">
                <div id="home_body_action_area_infoAction_download">
                    <input type="button" id="home_body_action_download_button" value="数据导出"/>
                </div>
                <div id="home_body_action_area_infoAction_function">
                    <input type="button" id="home_body_action_reviseAll_button" name="reviseAll" value="整体重载"/>
                    <input type="button" id="home_body_action_batchAdd_button" name="addBatch" value="批量新增"/>
                    <input type="button" id="home_body_action_area_infoAction_function_singleAdd_button" name="addSign" value="新增"/>
                    <input type="file" id="home_body_action_file" class="hide"/>
                </div>
            </div>
            <div id="home_body_action_area_infoAction_add_single">

            </div>
        </div>
    </div>
</div>
<div id="back_board" class="back">

</div>
<div id="home_buttom" class="">
    <span>copyright  &copy  2017  华夏保险江苏分公司  版权所有</span>
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
    <h4><a href="" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="sureBox_sure" value="确定"/>
</div>

<div id="wait">
    <img src="img/wait.png"/>
    <span>正在处理。。。。</span>
</div>

<div id="staffInfo">
    <div id="staffInfo_title" name="title">
        <span name="line">----------</span>
        <span name="title">组员信息</span>
        <span name="line">----------</span>
    </div>
    <div id="staffInfo_info" name="info">
        <div id="staffInfo_info_staffCode" name="staffCode">
            <label for="staffInfo_info_staffCode_input">业务员代码:</label>
            <input type="text" id="staffInfo_info_staffCode_input" name="staffCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="staffInfo_info_staffName" name="staffName">
            <label for="staffInfo_info_staffName_input">姓名:</label>
            <input type="text" id="staffInfo_info_staffName_input" name="staffName" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="staffInfo_info_belongCode" name="belongCode">
            <label for="staffInfo_info_belongCode_input">所属编号:</label>
            <input type="text" id="staffInfo_info_belongCode_input" name="belongCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="staffInfo_info_belongName" name="belongName">
            <label for="staffInfo_info_belongName_input">所属名称:</label>
            <input type="text" id="staffInfo_info_belongName_input" name="belongName" disabled="true" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="staffInfo_info_leaderName" name="leaderName">
            <label for="staffInfo_info_leaderName_input">负责人:</label>
            <input type="text" id="staffInfo_info_leaderName_input" name="leaderName" disabled="true" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="staffInfo_info_staffSex" name="staffSex">
            <label for="staffInfo_info_staffSex_input">性别:</label>
            <select  id="staffInfo_info_staffSex_input" name="staffSex">
                <option>男</option>
                <option>女</option>
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffBirth" name="staffBirth">
            <label for="staffInfo_info_staffBirth_input">出生日期:</label>
            <input type="text" id="staffInfo_info_staffBirth_input" name="staffBirth" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffID" name="staffID">
            <label for="staffInfo_info_staffID_input">身份证号:</label>
            <input type="text" id="staffInfo_info_staffID_input" name="staffID" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_trainStart" name="trainStart">
            <label for="staffInfo_info_trainStart_input">培训起始日:</label>
            <input type="text" id="staffInfo_info_trainStart_input" name="trainStart" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_trainEnd" name="trainEnd">
            <label for="staffInfo_info_trainEnd_input">报聘生效日:</label>
            <input type="text" id="staffInfo_info_trainEnd_input" name="trainEnd" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_codeIn" name="codeIn">
            <label for="staffInfo_info_codeIn_input">工号录入日:</label>
            <input type="text" id="staffInfo_info_codeIn_input" name="codeIn" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_level" name="level">
            <label for="staffInfo_info_level_input">职级:</label>
            <select id="staffInfo_info_level_input" name="level">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_levelStart" name="levelStart">
            <label for="staffInfo_info_levelStart_input">初始职级:</label>
            <select id="staffInfo_info_levelStart_input" name="levelStart">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_levelStartTime" name="levelStartTime">
            <label for="staffInfo_info_levelStartTime_input">职级起始日:</label>
            <input type="text" id="staffInfo_info_levelStartTime_input" name="levelStartTime" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_quiteTime" name="quiteTime">
            <label for="staffInfo_info_quiteTime_input">离职日期:</label>
            <input type="text" id="staffInfo_info_quiteTime_input" name="quiteTime" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffState" name="staffState">
            <label for="staffInfo_info_staffState_input">状态:</label>
            <select id="staffInfo_info_staffState_input" name="staffState">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_education" name="education">
            <label for="staffInfo_info_education_input">学历:</label>
            <select id="staffInfo_info_education_input" name="education">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffPhone" name="staffPhone">
            <label for="staffInfo_info_staffPhone_input">手机:</label>
            <input type="text" id="staffInfo_info_staffPhone_input" name="staffPhone" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_agentCode" name="agentCode">
            <label for="staffInfo_info_agentCode_input">代理证号:</label>
            <input type="text" id="staffInfo_info_agentCode_input" name="agentCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_businessCode" name="businessCode">
            <label for="staffInfo_info_businessCode_input">展业证号:</label>
            <input type="text" id="staffInfo_info_businessCode_input" name="businessCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_bankCode" name="bankCode">
            <label for="staffInfo_info_bankCode_input">银行帐号:</label>
            <input type="text" id="staffInfo_info_bankCode_input" name="bankCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_Email" name="Email">
            <label for="staffInfo_info_Email_input">E-mail:</label>
            <input type="text" id="staffInfo_info_Email_input" name="Emaill" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffNature" name="staffNature">
            <label for="staffInfo_info_staffNature_input">人员性质:</label>
            <select id="staffInfo_info_staffNature_input" name="staffNature">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffChannel" name="staffChannel">
            <label for="staffInfo_info_staffChannel_input">增员渠道:</label>
            <select id="staffInfo_info_staffChannel_input" name="staffChannel">
            </select>
            <img name="warning" src="img/warning.png"/>
        </div>

        <div id="staffInfo_info_staffNatureChange" name="staffNatureChange">
            <label for="staffInfo_info_staffNatureChange_input">性质变动日期:</label>
            <input type="text" id="staffInfo_info_staffNatureChange_input" name="staffNatureChange" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
    </div>
    <div id="staffInfo_button">
        <input type="button" id="staffInfo_button_cancel" name="cancel" value="取消"/>
        <input type="button" id="staffInfo_button_submit" name="submit" value="提交"/>
    </div>
</div>



<script src="js/pub/jquery-1.10.2.js"></script>

<script src="js/pub/foundation-datepicker.js"></script>
<script src="js/pub/locales/foundation-datepicker.zh-CN.js"></script>
<script src="js/pub/jquery.dataTables.js"></script>
<script src="js/pub/jquery.rotate.min.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/main/pub/publicForInfo.js"></script>
<script src="js/main/pub/publicForMain.js"></script>
<script src="js/main/operationStaff.js"></script>
</body>
</html>

