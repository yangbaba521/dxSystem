package infoAction;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import pub.mysqlAction;
import pub.path;
import pub.excal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/7/28.
 */
@WebServlet(name = "exportFile")
public class exportFile extends HttpServlet {
    Logger logger = Logger.getLogger(exportFile.class);
    Thread thread = Thread.currentThread();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //初始化操作
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject data = new JSONObject();
        //初始化对象
        try{

            mysqlAction mysql = new mysqlAction();
            String mysqlStr = null;
            List<String> result = new ArrayList();
            int resNum =0;

            path path = new path();
            excal excel = new excal();

            List<String> area = new ArrayList();
            List<String> part = new ArrayList();
            List<String> team = new ArrayList();
            List<String> staff = new ArrayList();

            HttpSession session = request.getSession();
            String sessionId = session.getId();

            String userCode = (String) session.getAttribute("userOnline");

            logger.debug(thread.getId() + ":下载人力");
            try{
                mysql.mysqlStart();
                if(userCode != null && userCode != ""){
                    mysqlStr = "select level from user where userCode='"+ userCode +"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0){
                        mysqlStr = "select * from area;";
                        resNum = mysql.doQuery(mysqlStr,area);
                        mysqlStr = "select * from part;";
                        resNum = mysql.doQuery(mysqlStr,part);
                        mysqlStr = "select * from team;";
                        resNum = mysql.doQuery(mysqlStr,team);
                        mysqlStr = "select * from staff;";
                        resNum = mysql.doQuery(mysqlStr,staff);
                        HSSFWorkbook wb = new HSSFWorkbook();
                        if(excel.creatArea(area, wb, result.get(0)) && excel.createPart(part, wb, result.get(0)) && excel.createTeam(team, wb, result.get(0)) && excel.createStaff(staff, wb, result.get(0))) {
                            //制作完毕,提供下载
                           if(excel.getDownload(wb, "HRList.xls", path.fileInfo)){
                                data.put("result", "success");
                                data.put("fileName","HRList.xls");
                            }else{
                                data.put("result", "faill");
                                data.put("cause", "无法导出表格");
                            }
                        } else {
                            data.put("result", "faill");
                            data.put("cause", "无法生成表格");
                        }
                    }else{
                        data.put("result", "faill");
                        data.put("cause", "您的帐号不存在,请重新登录");
                    }
                }else{
                    data.put("result", "faill");
                    data.put("cause", "您没有被授权,请重新登录");
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result", "faill");
                data.put("cause", e.toString());
            }finally {
                mysql.mysqlClose();
            }

        }catch(Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
        }finally{
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


