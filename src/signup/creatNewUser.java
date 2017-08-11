package signup;

import login.loginSubmit;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.md5;
import pub.mysqlAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/4/11.
 */
@WebServlet(name = "creatNewUser")
public class creatNewUser extends HttpServlet {
    Logger logger = Logger.getLogger(creatNewUser.class);
    Thread thread = Thread.currentThread();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //初始化操作
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            //输出流
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();

            //数据库对象
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;

            //加密对象
            md5 encrypt = new md5();

            //用户对象
            userInfo userInfo = new userInfo();

            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");
            String userName = request.getParameter("userName");
            String company = request.getParameter("company");
            String partment = request.getParameter("partment");
            String question = request.getParameter("question");
            String answer = request.getParameter("answer");

            logger.debug(thread.getId() + ":用户注册");
            logger.info(thread.getId() + ":user=" + user + ":pwd=******"+ "userName="+userName + ":company=" + company+ "partment="+partment + ":question=" + question+ "answer=******");
            if(user!=null && pwd!=null && userName!=null && company!=null && partment!=null && question!=null && answer!=null) {
                company = userInfo.getCompany(company);
                partment = userInfo.getPartment(partment);
                if (!company.equals("null") && !partment.equals("null")) {
                    try {
                        mysql.mysqlStart();
                        mysqlStr = "select userCode from user where userCode='" + user + "';";
                        resNum = mysql.doQuery(mysqlStr, result);
                        if (resNum == 0) {
                            pwd = encrypt.convertMD5(pwd);
                            answer = encrypt.convertMD5(answer);
                            mysqlStr = "insert into user value(null,'" + user + "','" + pwd + "','" + userName + "','" + company + "','" + partment + "','" + question + "','" + answer + "','guest');";
                            if (mysql.doUpdate(mysqlStr)) {
                                data.put("result", "success");
                            } else {
                                data.put("result", "faill");
                                data.put("cause", "无法插入数据库");
                            }
                        } else {
                            data.put("result", "faill");
                            data.put("cause", "该用户已经存在,请用其他的用户名,建议使用姓名的全拼");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        logger.error(thread.getId() + ":" + e.toString());
                        data.put("result", "faill");
                        data.put("cause", "数据库错误");
                        data.put("debug", e.toString());
                    } finally {
                        mysql.mysqlClose();
                    }
                }else{
                    data.put("result", "faill");
                    data.put("cause", "机构或者部门不存在,请确认后再次尝试");
                }
            }else{
                data.put("result", "faill");
                data.put("cause", "传输数据有误,请再次尝试");
            }

            out = response.getWriter();
            out.print(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

}


