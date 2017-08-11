package pub;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;
import pub.md5;

/**
 * Created by sunyuyang on 17/1/12.
 */
public class excal {
    public String url = null;
    md5 enc = new md5();
    public boolean creatArea(List list, HSSFWorkbook wb, String level) {
        Logger logger = Logger.getLogger(excal.class);
        Thread thread = Thread.currentThread();
        try {
            HSSFSheet sheet = wb.createSheet("区域");
            // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
            HSSFRow row = sheet.createRow((int) 0);
            // 4.创建单元格，设置值表头，设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 设置表头
            String[] title= {"编号","区域编号","区域名称","负责人编号","负责人名称","目标"};
            for(int i=0; i<title.length;i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size()/title.length; i++) {
                row = sheet.createRow(i + 1);
                for(int k=0; k<title.length; k++){
                    row.createCell(k).setCellValue((String)list.get(i*title.length+k));
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
    }

    public boolean createPart(List list, HSSFWorkbook wb, String level) {
        Logger logger = Logger.getLogger(excal.class);
        Thread thread = Thread.currentThread();
        try {
            HSSFSheet sheet = wb.createSheet("区部");
            // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
            HSSFRow row = sheet.createRow((int) 0);
            // 4.创建单元格，设置值表头，设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 设置表头
            String[] title= {"编号","区部编号","区部名称","所属编号","所属名称","负责人编号","负责人名称","目标"};
            for(int i=0; i<title.length;i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size()/title.length; i++) {
                row = sheet.createRow(i + 1);
                for(int k=0; k<title.length; k++){
                    row.createCell(k).setCellValue((String)list.get(i*title.length+k));
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
    }

    public boolean createTeam(List list, HSSFWorkbook wb, String level) {
        Logger logger = Logger.getLogger(excal.class);
        Thread thread = Thread.currentThread();
        try {
            HSSFSheet sheet = wb.createSheet("部组");
            // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
            HSSFRow row = sheet.createRow((int) 0);
            // 4.创建单元格，设置值表头，设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 设置表头
            String[] title= {"编号","部组编号","部组名称","所属编号","所属名称","负责人编号","负责人名称","目标"};
            for(int i=0; i<title.length;i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size()/title.length; i++) {
                row = sheet.createRow(i + 1);
                for(int k=0; k<title.length; k++){
                    row.createCell(k).setCellValue((String)list.get(i*title.length+k));
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
    }

    public boolean createStaff(List list, HSSFWorkbook wb, String level){
        Logger logger = Logger.getLogger(excal.class);
        Thread thread = Thread.currentThread();
        try {
            HSSFSheet sheet = wb.createSheet("组员");
            // 3.在sheet中添加表头第0行，老版本poi对excel行数列数有限制short
            HSSFRow row = sheet.createRow((int) 0);
            // 4.创建单元格，设置值表头，设置表头居中
            HSSFCellStyle style = wb.createCellStyle();
            // 居中格式
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            // 设置表头
            String[] title= {"业务员代码","姓名","销售机构代码","销售机构名称","组长","性别","出生日期","证件号码","培训起始日","报聘生效日","工号录入日","职级","初始职级	","职级起始日","离职日期","状态","学历","手机","代理证号","展业证号","银行帐号","E_mail	","人员性质","增员渠道	","人员性质的变动日期"};
            for(int i=0; i<title.length;i++){
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(title[i]);
                cell.setCellStyle(style);
            }
            for (int i = 0; i < list.size()/(title.length+1); i++) {
                row = sheet.createRow(i + 1);
                for(int k=0; k<title.length; k++){
                    if(k == 7 || k == 17 || k ==20){
                        row.createCell(k).setCellValue(enc.convertMD5((String)list.get(i*(title.length+1)+k+1)));
                    }else{
                        row.createCell(k).setCellValue((String)list.get(i*(title.length+1)+k+1));
                    }
                }
            }
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
    }

    public boolean getDownload(HSSFWorkbook wb, String fileName, String path){
        Logger logger = Logger.getLogger(excal.class);
        Thread thread = Thread.currentThread();
        logger.debug(thread.getId() + ":getDownload");
        String url="";
        try{
            url = path + fileName;
            FileOutputStream out =new FileOutputStream(url);
            wb.write(out);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
    }
}
