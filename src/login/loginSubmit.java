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
 * Created by sunyuyang on 17/4/11.
 */
@WebServlet(name = "loginSubmit")
public class loginSubmit extends HttpServlet {
    Logger logger = Logger.getLogger(loginSubmit.class);
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

            //数据库对象
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;

            //加密对象
            md5 encrypt = new md5();

            String user = request.getParameter("user");
            String pwd = request.getParameter("pwd");
            String type = request.getParameter("type");


            logger.debug(thread.getId() + ":用户登录");
            logger.info(thread.getId() + ":user=" + user + "pwd=****" + "type=" + type);
            if(user!=null && pwd!=null && type!=null) {
                try {
                    mysql.mysqlStart();
                    mysqlStr = "select pwd from user where userCode='"+ user +"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0){
                        if(pwd.equals(encrypt.convertMD5(result.get(0)+""))){
                            Cookie cookie = new Cookie("JSESSIONID", sessionId);
                            cookie.setPath("/dxSystem");
                            cookie.setMaxAge(60 * 60 * 3);
                            response.addCookie(cookie);
                            session.setAttribute("userOnline", user);
                            data.put("result","success");
                            if(type.equals("remember")){
                                cookie.setMaxAge(60 * 60 * 24 * 30);
                                response.addCookie(cookie);
                                session.setAttribute("userRem", user);
                                data.put("result","success");
                            }else if(type.equals("unremember")){
                                session.removeAttribute("userRem");
                                data.put("result","success");
                            }else{
                                data.put("result","faill");
                                data.put("cause","记住密码功能失败,请重新尝试");
                            }
                        }else{
                            data.put("result","faill");
                            data.put("cause","密码错误,请检查后再次提交");
                        }
                    }else{
                        data.put("result","faill");
                        data.put("cause","用户不存在,请检查后再次提交");
                    }
                }catch (SQLException e) {
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

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
