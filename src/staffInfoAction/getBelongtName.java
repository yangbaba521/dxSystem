package staffInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;
import teamInfoAction.getPartName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/8/10.
 */
@WebServlet(name = "getBelongtName")
public class getBelongtName extends HttpServlet {
    Logger logger = Logger.getLogger(getBelongtName.class);
    Thread thread = Thread.currentThread();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject data = new JSONObject();
        try{
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;

            HttpSession session = request.getSession();
            String userCode = (String) session.getAttribute("userOnline");

            String[] info = request.getParameterValues("info");
            int belongType = info[0].length();
            String belongTypeStr = "";
            if(belongType == 12){
                belongTypeStr = "area";
            }else if(belongType == 15){
                belongTypeStr = "part";
            }else{
                belongTypeStr = "team";
            }

            try{
                mysql.mysqlStart();
                mysqlStr = "select "+belongTypeStr+"Name,leaderName from "+belongTypeStr+" where "+belongTypeStr+"Code='"+ info[0] +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("belongName",result.get(0));
                    data.put("leaderName",result.get(1));
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到该区部");
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法找到该所属,请再次尝试,或者联系管理员");
                data.put("debug",e.toString());
            }finally{
                mysql.mysqlClose();
            }
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
        }finally {
            out = response.getWriter();
            out.print(data);
            out.flush();
            if(out != null) {
                out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}




