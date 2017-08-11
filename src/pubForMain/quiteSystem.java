package pubForMain;

import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.md5;
import pub.mysqlAction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/4/13.
 */
@WebServlet(name = "quiteSystem")
public class quiteSystem extends HttpServlet {
    Logger logger = Logger.getLogger(quiteSystem.class);
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

            logger.debug(thread.getId() + ":安全退出系统");

            session.removeAttribute("userOnline");
            data.put("result","success");
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

