package login;

import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;
import pub.md5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/4/10.
 */
public class getLoginInfo extends javax.servlet.http.HttpServlet {
    Logger logger = Logger.getLogger(getLoginInfo.class);
    Thread thread = Thread.currentThread();
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        try{
            //输出流
            PrintWriter out = response.getWriter();
            JSONObject data = new JSONObject();
            JSONArray list = new JSONArray();

            HttpSession session = request.getSession();
            String sessionId = session.getId();
            Cookie cookie = new Cookie("JSESSIONID", sessionId);
            cookie.setPath("/dxSystem");

            //数据库对象
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;

            md5 encrpty = new md5();

            logger.debug(thread.getId() + ":获取登录初始信息");

            String user = (String) session.getAttribute("userRem");

            if(user != null){
                try {
                    mysql.mysqlStart();
                    mysqlStr = "select pwd from user where userCode='"+user+"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum >0){
                        data.put("result","success");
                        data.put("cause","Rem");
                        data.put("user", user);
                        data.put("pwd",encrpty.convertMD5(result.get(0)+""));
                    }else{
                        data.put("result","faill");
                        data.put("cause","保存的用户已经不存在,请重新输入新用户");
                    }

                }catch (SQLException e){
                    e.printStackTrace();
                    logger.error(thread.getId() + ":" + e.toString());
                    data.put("result","faill");
                    data.put("cause","数据库错误");
                    data.put("debug",e.toString());
                }finally{
                    mysql.mysqlClose();
                }

            }else{
                data.put("result","success");
                data.put("cause","noRem");
            }
            out = response.getWriter();
            out.print(data);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
