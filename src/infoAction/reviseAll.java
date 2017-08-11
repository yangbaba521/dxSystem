package infoAction;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.json.JSONObject;
import pub.mysqlAction;
import pub.path;
import pub.excalAction;
import pub.md5;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sunyuyang on 17/7/27.
 */
@WebServlet(name = "reviseAll")
public class reviseAll extends HttpServlet {
    Logger logger = Logger.getLogger(reviseAll.class);
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

            path path = new path();
            excalAction excel = new excalAction();
            md5 enc = new md5();

            List<String> areaInfo = new ArrayList();
            List<String> partInfo = new ArrayList();
            List<String> teamInfo = new ArrayList();
            List<String> staffInfo = new ArrayList();

            try{
                mysql.mysqlStart();
                mysql.connection.setAutoCommit(false);
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    try {
                        FileItemFactory factory = new DiskFileItemFactory();
                        ServletFileUpload upload = new ServletFileUpload(factory);

                        List<?> items = upload.parseRequest(request);
                        Iterator iter = items.iterator();
                        while(iter.hasNext()) {
                            FileItem item = (FileItem) iter.next();
                            if (!item.isFormField()) {
                                /*String fieldName = item.getFieldName();
                                String fileName = item.getName();
                                String contentType = item.getContentType();
                                boolean isInMemory = item.isInMemory();
                                long sizeInBytes = item.getSize();*/

                                File uploadedFile = new File(path.fileInfo +"HRFile.xls");
                                item.write(uploadedFile);
                            }
                        }
                    } catch (FileUploadException e) {
                        e.printStackTrace();
                        logger.error(thread.getId() + ":" + e.toString());
                        data.put("result","faill");
                        data.put("cause", "无法上传文件,请再次尝试,或者联系管理员");
                        data.put("debug",e.toString());
                    }

                    HSSFSheet areaSheet = excel.getMap(path.fileInfo +"HRFile.xls",0);
                    excel.getAll(areaSheet,areaInfo);
                    areaSheet = null;
                    HSSFSheet partSheet = excel.getMap(path.fileInfo +"HRFile.xls",1);
                    excel.getAll(partSheet,partInfo);
                    partSheet = null;
                    HSSFSheet teamSheet = excel.getMap(path.fileInfo +"HRFile.xls",2);
                    excel.getAll(teamSheet,teamInfo);
                    teamSheet = null;
                    HSSFSheet staffSheet = excel.getMap(path.fileInfo +"HRFile.xls",3);
                    excel.getAll(staffSheet,staffInfo);
                    staffSheet = null;

                    mysqlStr = "truncate table area;";
                    if(mysql.doUpdateNoRes(mysqlStr)) {
                        mysqlStr = "truncate table part;";
                        if (mysql.doUpdateNoRes(mysqlStr)) {
                            mysqlStr = "truncate table team;";
                            if (mysql.doUpdateNoRes(mysqlStr)) {
                                mysqlStr = "truncate table staff;";
                                if (mysql.doUpdateNoRes(mysqlStr)) {
                                    boolean flag = true;
                                    for (int i = 0; i < areaInfo.size() / 6; i++) {
                                        mysqlStr = "select areaCode from area where areaCode='" + areaInfo.get(i * 6 + 1) + "';";
                                        resNum = mysql.doQuery(mysqlStr, result);
                                        if (resNum == 0) {
                                            mysqlStr = "insert into area value(null,'" + areaInfo.get(i * 6 + 1) + "','" + areaInfo.get(i * 6 + 2) + "','" + areaInfo.get(i * 6 + 3) + "','" + areaInfo.get(i * 6 + 4) + "','" + areaInfo.get(i * 6 + 5) + "');";
                                            if (!mysql.doUpdate(mysqlStr)) {
                                                mysql.connection.rollback();
                                                flag = false;
                                                data.put("cause", "区域表" + areaInfo.get(i * 6) + "行,信息写入时出错");
                                                break;
                                            }
                                        } else {
                                            flag = false;
                                            mysql.connection.rollback();
                                            data.put("cause", "区域表" + areaInfo.get(i * 6) + "行,信息写入时出错,编号重复");
                                        }
                                    }
                                    areaInfo = null;
                                    for (int i = 0; i < partInfo.size() / 8; i++) {
                                        mysqlStr = "select partCode from part where partCode='" + partInfo.get(i * 8 + 1) + "';";
                                        resNum = mysql.doQuery(mysqlStr, result);
                                        if (resNum == 0) {
                                            mysqlStr = "select areaCode from area where areaCode='" + partInfo.get(i * 8 + 3) + "' and areaName='"+partInfo.get(i * 8 + 4)+"';";
                                            resNum = mysql.doQuery(mysqlStr, result);
                                            if (resNum > 0) {
                                                mysqlStr = "insert into part value(null,'" + partInfo.get(i * 8 + 1) + "','" + partInfo.get(i * 8 + 2) + "','" + partInfo.get(i * 8 + 3) + "','" + partInfo.get(i * 8 + 4) + "','" + partInfo.get(i * 8 + 5) + "','" + partInfo.get(i * 8 + 6) + "','" + partInfo.get(i * 8 + 7) + "');";
                                                if (!mysql.doUpdate(mysqlStr)) {
                                                    mysql.connection.rollback();
                                                    flag = false;
                                                    data.put("cause", "区部表" + partInfo.get(i * 8) + "行,信息写入时出错");
                                                    break;
                                                }
                                            } else {
                                                flag = false;
                                                mysql.connection.rollback();
                                                data.put("cause", "区部表" + partInfo.get(i * 8) + "行,信息写入时出错,所属不存在");
                                            }
                                        } else {
                                            flag = false;
                                            mysql.connection.rollback();
                                            data.put("cause", "区部表" + partInfo.get(i * 8) + "行,信息写入时出错,编号重复");
                                        }
                                    }
                                    partInfo = null;
                                    for (int i = 0; i < teamInfo.size() / 8; i++) {
                                        mysqlStr = "select teamCode from team where teamCode='" + teamInfo.get(i * 8 + 1) + "';";
                                        resNum = mysql.doQuery(mysqlStr, result);
                                        if (resNum == 0) {
                                            mysqlStr = "select partCode from part where partCode='" + teamInfo.get(i * 8 + 3) + "' and partName='"+teamInfo.get(i * 8 + 4)+"';";
                                            resNum = mysql.doQuery(mysqlStr, result);
                                            if (resNum > 0) {
                                                mysqlStr = "insert into team value(null,'" + teamInfo.get(i * 8 + 1) + "','" + teamInfo.get(i * 8 + 2) + "','" + teamInfo.get(i * 8 + 3) + "','" + teamInfo.get(i * 8 + 4) + "','" + teamInfo.get(i * 8 + 5) + "','" + teamInfo.get(i * 8 + 6) + "','" + teamInfo.get(i * 8 + 7) + "');";
                                                if (!mysql.doUpdate(mysqlStr)) {
                                                    mysql.connection.rollback();
                                                    flag = false;
                                                    data.put("cause", "部组表" + teamInfo.get(i * 8) + "行,信息写入时出错");
                                                    break;
                                                }
                                            } else {
                                                flag = false;
                                                mysql.connection.rollback();
                                                data.put("cause", "部组表" + teamInfo.get(i * 8) + "行,信息写入时出错,所属不存在");
                                            }
                                        } else {
                                            flag = false;
                                            mysql.connection.rollback();
                                            data.put("cause", "部组表" + teamInfo.get(i * 8) + "行,信息写入时出错,编号重复");
                                        }
                                    }
                                    teamInfo = null;

                                    for (int i = 0; i < staffInfo.size() / 25; i++) {
                                        mysqlStr = "select staffCode from staff where staffCode='" + staffInfo.get(i * 25 + 0) + "';";
                                        resNum = mysql.doQuery(mysqlStr, result);
                                        if (resNum == 0) {
                                            String type = "";
                                            int length = staffInfo.get(i*25+2).length();
                                            if(length == 12){
                                                type = "area";
                                            }else if(length == 15){
                                                type = "part";
                                            }else if(length == 18){
                                                type = "team";
                                            }
                                            mysqlStr = "select "+type+"Code from "+type+" where "+type+"Code='" + staffInfo.get(i * 25 + 2) + "' and "+type+"Name='"+staffInfo.get(i * 25 + 3)+"';";
                                            resNum = mysql.doQuery(mysqlStr, result);
                                            if (resNum > 0) {
                                                mysqlStr = "insert into staff value(null";
                                                for(int k =0; k<25; k++){
                                                    if(k == 7 || k == 17 || k == 20 ){
                                                        staffInfo.set(i*25+k,enc.convertMD5(staffInfo.get(i*25+k))+"");
                                                    }
                                                    if(staffInfo.get(i*25+k) == ""){
                                                        mysqlStr += ",null";
                                                    }else{
                                                        mysqlStr += ",'"+ staffInfo.get(i*25+k) +"'";
                                                    }
                                                }
                                                mysqlStr += ");";

                                                if (!mysql.doUpdate(mysqlStr)) {
                                                    mysql.connection.rollback();
                                                    flag = false;
                                                    data.put("cause", "组员表" + i + "行,信息写入时出错");
                                                    break;
                                                }
                                            }else{
                                                flag = false;
                                                mysql.connection.rollback();
                                                data.put("cause", "组员表" + i + "行,信息写入时出错,所属不存在");
                                            }
                                        }else{
                                            flag = false;
                                            mysql.connection.rollback();
                                            data.put("cause", "组员表" + i + "行,信息写入时出错,编号重复");
                                        }
                                    }
                                    staffInfo = null;
                                    if (flag) {
                                        data.put("result", "success");
                                        mysql.connection.commit();
                                    }
                                }else {
                                    data.put("result", "faill");
                                    data.put("cause", "组员表无法清空,请重新尝试");
                                }
                            }else {
                                data.put("result", "faill");
                                data.put("cause", "部组表无法清空,请重新尝试");
                            }
                        }else {
                            data.put("result", "faill");
                            data.put("cause", "区部表无法清空,请重新尝试");
                        }
                    }else {
                        data.put("result", "faill");
                        data.put("cause", "区域表无法清空,请重新尝试");
                    }
                }
            }catch(Exception e){
                mysql.connection.rollback();
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法写入文件,请再次尝试,或者联系管理员");
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






