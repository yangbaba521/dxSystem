package findPwd;

import org.apache.log4j.Logger;
import org.json.JSONObject;
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
@WebServlet(name = "getQuestionInfo")
public class getQuestionInfo extends HttpServlet {
    Logger logger = Logger.getLogger(getQuestionInfo.class);
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

            String user = request.getParameter("user");

            logger.debug(thread.getId() + ":获取密码找回问题");
            logger.info(thread.getId() + ":user=" + user );
            if(user!=null) {
                try{
                    mysql.mysqlStart();
                    mysqlStr = "select question,level from user where userCode='"+user+"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0) {
                        if("guest".equals(result.get(1))) {
                            data.put("result", "success");
                            data.put("question", result.get(0) + "");
                        }else{
                            data.put("result", "faill");
                            data.put("cause", "管理员帐号无法找回密码");
                        }
                    }else{
                        data.put("result", "faill");
                        data.put("cause", "该用户不存在,请检查输入的用户名是否正确");
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


