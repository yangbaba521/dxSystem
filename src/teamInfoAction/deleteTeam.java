package teamInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import partInfoAction.deletePart;
import pub.mysqlAction;

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
 * Created by sunyuyang on 17/8/4.
 */
@WebServlet(name = "deleteTeam")
public class deleteTeam extends HttpServlet {
    Logger logger = Logger.getLogger(deleteTeam.class);
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

            String teamCode = request.getParameter("teamCode");

            try{
                mysql.mysqlStart();
                mysql.connection.setAutoCommit(false);
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    mysqlStr="delete from team where teamCode='"+teamCode+"';";
                    if(mysql.doUpdateNoRes(mysqlStr)) {
                        mysqlStr="delete from staff where belongCode='"+teamCode+"';";
                        if(mysql.doUpdateNoRes(mysqlStr)) {
                            mysql.connection.commit();
                            data.put("result", "success");
                        }else{
                            data.put("result", "faill");
                            data.put("cause", "删除失败,请再次尝试,或者联系管理员");
                            mysql.connection.rollback();
                        }
                    } else {
                        data.put("result", "faill");
                        data.put("cause", "删除失败,请再次尝试,或者联系管理员");
                        mysql.connection.rollback();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                mysql.connection.rollback();
                data.put("result","faill");
                data.put("cause", "无法删除部组,请再次尝试,或者联系管理员");
                data.put("debug",e.toString());
            }finally{
                mysql.connection.setAutoCommit(true);
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

