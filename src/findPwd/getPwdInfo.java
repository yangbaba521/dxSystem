package findPwd;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;
import pub.md5;

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
@WebServlet(name = "getPwdInfo")
public class getPwdInfo extends HttpServlet {
    Logger logger = Logger.getLogger(getPwdInfo.class);
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

            String user = request.getParameter("user");
            String answer = request.getParameter("answer");


            logger.debug(thread.getId() + ":密码找回,获取密码");
            logger.info(thread.getId() + ":user=" + user +",answer="+answer);
            if(user!=null && answer!=null) {
                try{
                    mysql.mysqlStart();
                    mysqlStr = "select pwd,answer from user where userCode='"+user+"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0) {
                        if(answer.equals(encrypt.convertMD5(result.get(1)+""))) {
                            data.put("result", "success");
                            data.put("pwd", encrypt.convertMD5(result.get(0) + ""));
                        }else{
                            data.put("result", "faill");
                            data.put("cause", "答案不正确请重新填写后再提交");
                        }
                    }else{
                        data.put("result", "faill");
                        data.put("cause", "该用户不存在,请刷新页面后检查输入的用户名是否正确");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                    logger.error(thread.getId() + ":" + e.toString());
                    data.put("result","faill");
                    data.put("cause","数据库错误");
                    data.put("debug",e.toString());
                }finally {
                    mysql.mysqlClose();
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



