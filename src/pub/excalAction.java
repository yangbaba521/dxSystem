package pub;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sunyuyang on 16/8/29.
 */
public class excalAction {
    public String[][] jgCode = new String[20][2];
    //private String excelPath = "/Users/sunyuyang/IdeaProjects/meetSignUpServer/model";
    //private String signUpExcelPath = "/Users/sunyuyang/IdeaProjects/meetSignUpServer/signup";
    //private String excelPath2 = "/Users/sunyuyang/IdeaProjects/meetSignUpServer/model";
    //private String signUpExcelPath2 = "/Users/sunyuyang/IdeaProjects/meetSignUpServer/signup";
    private String excelPath = "c:/signupInfo/model/";
    private String signUpExcelPath = "c:/signupInfo/signup/";
    private String hisExcelPath = "c:/signupInfo/his/";
    private String excelPath2 = "c:/tomcat/webapps/meetSignUpServer/signupInfo/model/";
    private String hisExcelPath2 = "c:/tomcat/webapps/meetSignUpServer/signupInfo/his/";
    private String signUpExcelPath2 = "c:/tomcat/webapps/meetSignUpServer/signupInfo/signup/";


    /**********************************************
     * @param file     目标文件地址 String
     * @param sheetNum 工作簿编号 int
     * @return int 错误异常代码
     * @function getMap
     **********************************************/
    public HSSFSheet getMap(String file, int sheetNum) {
        try
        {
            //设置要读取的文件路径
            FileInputStream fis = new FileInputStream(file);
            HSSFWorkbook wb = new HSSFWorkbook(fis);
            //获得sheet工作簿
            fis.close();
            return wb.getSheetAt(sheetNum);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**********************************************
     * @param colStr 列标 String
     * @param length 列标长度 int
     * @return int  转换后的列标值
     * @function excelColStrToNum
     **********************************************/
    public int excelColStrToNum(String colStr, int length)
    {
        int num = 0;
        int result = 0;
        if (colStr.equals("all"))
        {
            return -1;
        }
        for (int i = 0; i < length; i++)
        {
            char ch = colStr.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            result += num;
        }
        return result;
    }

    /**********************************************
     * @param columnIndex 列标值 int
     * @return String  转换后的列标值
     * @function excelColIndexToStr
     **********************************************/
    public String excelColIndexToStr(int columnIndex)
    {
        if (columnIndex <= 0)
        {
            return null;
        }
        String columnStr = "";
        columnIndex--;
        do
        {

            if (columnStr.length() > 0)
            {
                columnIndex--;
            }
            columnStr = ((char) (columnIndex % 26 + (int) 'A')) + columnStr;
            columnIndex = (int) ((columnIndex - columnIndex % 26) / 26);
        } while (columnIndex > 0);
        return columnStr;
    }


    /**********************************************
     * @param sheet     工作簿 XSSFSheet(被对比目标工作簿)
     * @return int 错误异常代码
     * @function getList
     **********************************************/
    public ArrayList<String> getLine(HSSFSheet sheet, HSSFRow row, int cellNum)
    {
        ArrayList<String> list = new ArrayList<String>();
        try
        {
            for(int i=0; i<cellNum; i++){
                row.getCell(i).setCellType(HSSFCell.CELL_TYPE_STRING);
                list.add(row.getCell(i)+"");
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getAll(HSSFSheet sheet, List<String> list)
    {
        try
        {
            int rowNum=sheet.getLastRowNum();//获得总行数

            for(int i =1; i <= rowNum; i++){
                for(int k =0; k < sheet.getRow(i).getPhysicalNumberOfCells(); k++){
                    sheet.getRow(i).getCell(k).setCellType(sheet.getRow(i).getCell(k).CELL_TYPE_STRING);
                    String info = sheet.getRow(i).getCell(k).getStringCellValue();
                    list.add(info);
                }
            }
            return 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**********************************************
     * @param sheet     工作簿 XSSFSheet(被对比目标工作簿)
     * @param startLine 对比初始行 int
     * @param endLine   对比结束行 int
     * @param cell      对比初始列 String
     * @param list      存储目标链表 ArrayList
     * @return int 错误异常代码
     * @function getList
     **********************************************/
    public int getList(HSSFSheet sheet, int startLine, int endLine, String cell, ArrayList list)
    {
        try
        {
            int i = startLine;
            int Cell = excelColStrToNum(cell, cell.length());

            if (endLine == -1)
            {
                while (!(sheet.getRow(i - 1).getCell(Cell - 1) + "").equals(""))
                {
                    sheet.getRow(i - 1).getCell(Cell - 1).setCellType(sheet.getRow(i - 1).getCell(Cell - 1).CELL_TYPE_STRING);
                    String temp = sheet.getRow(i - 1).getCell(Cell - 1).getStringCellValue();
                    list.add(temp);
                    i++;
                }
            }
            else
            {
                while (i < endLine)
                {
                    sheet.getRow(i - 1).getCell(Cell - 1).setCellType(sheet.getRow(i - 1).getCell(Cell - 1).CELL_TYPE_STRING);
                    String temp = sheet.getRow(i - 1).getCell(Cell - 1).getStringCellValue();
                    list.add(temp);
                    i++;
                }
            }
            return 1000;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean createModelExcelFile(String fileName, List<String> day, List<String> meet, List<String> eat, List<String> live)
    {
         boolean isCreateSuccess = false;
         Workbook workbook = null;

         try
         {
            // XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
            workbook = new HSSFWorkbook();//WorkbookFactory.create(inputStream);
         }catch(Exception e)
         {
             System.out.println("It cause Error on CREATING excel workbook: ");
             e.printStackTrace();
         }
         if(workbook != null)
         {
             Sheet sheet = workbook.createSheet(fileName);
             CellStyle style = getStyle(workbook);
             int cellNum = 0;

             Row row0 = sheet.createRow(0);
             Cell cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
             cell.setCellStyle(style);
             cell.setCellValue("姓名");
             sheet.autoSizeColumn(cellNum);
             cellNum++;

             cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
             cell.setCellStyle(style);
             cell.setCellValue("电话");
             sheet.autoSizeColumn(cellNum);
             cellNum++;

             cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
             cell.setCellStyle(style);
             cell.setCellValue("机构");
             sheet.autoSizeColumn(cellNum);
             cellNum++;

             cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
             cell.setCellStyle(style);
             cell.setCellValue("部门");
             sheet.autoSizeColumn(cellNum);
             cellNum++;

             cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
             cell.setCellStyle(style);
             cell.setCellValue("职级");
             sheet.autoSizeColumn(cellNum);
             cellNum++;

             for (int i = 1; i <= Integer.parseInt(day.get(0)); i++) {
                 for (int k = 0; k < Integer.parseInt(meet.get(0)); k++) {
                     cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
                     cell.setCellStyle(style);
                     cell.setCellValue(meet.get(1));
                     sheet.autoSizeColumn(cellNum);
                     meet.remove(1);
                     cellNum++;
                 }
                 meet.remove(0);
             }
             for (int i = 1; i <= Integer.parseInt(day.get(0)); i++) {
                 for (int k = 0; k < Integer.parseInt(eat.get(0)); k++) {
                     cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
                     cell.setCellStyle(style);
                     cell.setCellValue(eat.get(1));
                     sheet.autoSizeColumn(cellNum);
                     eat.remove(1);
                     cellNum++;
                 }
                 eat.remove(0);
             }
             for (int i = 1; i <= Integer.parseInt(day.get(0)); i++) {
                 for (int k = 0; k < Integer.parseInt(live.get(0)); k++) {
                     cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
                     cell.setCellStyle(style);
                     cell.setCellValue(live.get(1));
                     sheet.autoSizeColumn(cellNum);
                     live.remove(1);
                     cellNum++;
                 }
                 live.remove(0);
             }
             /*for (int rowNum = 1; rowNum < 200; rowNum++)
             {
                Row row = sheet.createRow(rowNum);
                for(int i = 0; i < 11; i++)
                {
                    Cell cell = row.createCell(i, Cell.CELL_TYPE_STRING);
                    cell.setCellValue("cell" + String.valueOf(rowNum+1) + String.valueOf(i+1));
                }
             }*/
            try {
                    FileOutputStream outputStream = new FileOutputStream(excelPath + fileName +".xls");
                   workbook.write(outputStream);
                FileOutputStream outputStream2 = new FileOutputStream(excelPath2 + fileName +".xls");
                workbook.write(outputStream2);
                    outputStream.flush();
                    outputStream.close();
                    isCreateSuccess = true;
                } catch (Exception e) {
                    System.out.println("It cause Error on WRITTING excel workbook: ");
                  e.printStackTrace();
                }
             }
        File sss = new File(excelPath);
        System.out.println(sss.getAbsolutePath());
        File sss2 = new File(excelPath2);
        System.out.println(sss.getAbsolutePath());
         return isCreateSuccess;
    }

    public boolean createInfoExcelFile(String fileName, List<String> info, int num)
    {
        boolean isCreateSuccess = false;
        Workbook workbook = null;

        try
        {
            // XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
            workbook = new HSSFWorkbook();//WorkbookFactory.create(inputStream);
        }catch(Exception e)
        {
            System.out.println("It cause Error on CREATING excel workbook: ");
            e.printStackTrace();
        }
        if(workbook != null)
        {
            Sheet sheet = workbook.createSheet(fileName);
            CellStyle style = getStyle(workbook);
            int cellNum = 0;

            Row row0 = sheet.createRow(0);
            Cell cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("姓名");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("电话");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("机构");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("部门");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("职级");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("签到");
            sheet.autoSizeColumn(cellNum);

            for(int i=1; i<=num; i++){
                Row row = sheet.createRow(i);
                for(int k=0; k<cellNum; k++){
                    cell = row.createCell(k, Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(style);
                    cell.setCellValue(info.get((i-1)*6+k));
                    sheet.autoSizeColumn(k);
                }
            }



            try {
                FileOutputStream outputStream = new FileOutputStream(signUpExcelPath + fileName +".xls");
                workbook.write(outputStream);
                FileOutputStream outputStream2 = new FileOutputStream(signUpExcelPath2 + fileName +".xls");
                workbook.write(outputStream2);
                outputStream.flush();
                outputStream.close();
                isCreateSuccess = true;
            } catch (Exception e) {
                System.out.println("It cause Error on WRITTING excel workbook: ");
                e.printStackTrace();
            }
        }
        File sss = new File(signUpExcelPath);
        System.out.println(sss.getAbsolutePath());
        File sss2 = new File(signUpExcelPath2);
        System.out.println(sss2.getAbsolutePath());
        return isCreateSuccess;
    }

    public boolean createhisExcelFile(List<String> info, int num)
    {
        boolean isCreateSuccess = false;
        Workbook workbook = null;

        try
        {
            // XSSFWork used for .xslx (>= 2007), HSSWorkbook for 03 .xsl
            workbook = new HSSFWorkbook();//WorkbookFactory.create(inputStream);
        }catch(Exception e)
        {
            System.out.println("It cause Error on CREATING excel workbook: ");
            e.printStackTrace();
        }
        if(workbook != null)
        {
            Sheet sheet = workbook.createSheet("hisList");
            CellStyle style = getStyle(workbook);
            int cellNum = 0;

            Row row0 = sheet.createRow(0);
            Cell cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("申请帐号");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("申请人");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("会务代码");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("会务名称");
            sheet.autoSizeColumn(cellNum);
            cellNum++;

            cell = row0.createCell(cellNum, Cell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue("申请时间");
            sheet.autoSizeColumn(cellNum);

            for(int i=1; i<=num; i++){
                Row row = sheet.createRow(i);
                for(int k=0; k<cellNum; k++){
                    cell = row.createCell(k, Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(style);
                    cell.setCellValue(info.get((i-1)*5+k));
                    sheet.autoSizeColumn(k);
                }
            }



            try {
                FileOutputStream outputStream = new FileOutputStream(hisExcelPath + "hisList.xls");
                workbook.write(outputStream);
                FileOutputStream outputStream2 = new FileOutputStream(hisExcelPath2 + "hisList.xls");
                workbook.write(outputStream2);
                outputStream.flush();
                outputStream.close();
                isCreateSuccess = true;
            } catch (Exception e) {
                System.out.println("It cause Error on WRITTING excel workbook: ");
                e.printStackTrace();
            }
        }
        File sss = new File(signUpExcelPath);
        System.out.println(sss.getAbsolutePath());
        File sss2 = new File(signUpExcelPath2);
        System.out.println(sss2.getAbsolutePath());
        return isCreateSuccess;
    }

    public String getExcalPath(){
        return excelPath;
    }

    private CellStyle getStyle(Workbook workbook){
        CellStyle style = workbook.createCellStyle();
     style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
         // 设置单元格字体
        Font headerFont = workbook.createFont(); // 字体
         headerFont.setFontHeightInPoints((short)14);
        headerFont.setColor(HSSFColor.RED.index);
         headerFont.setFontName("宋体");
        style.setFont(headerFont);
        style.setWrapText(true);

        // 设置单元格边框及颜色
         style.setBorderBottom((short)1);
         style.setBorderLeft((short)1);
         style.setBorderRight((short)1);
         style.setBorderTop((short)1);
        style.setWrapText(true);
        return style;
     }
    public String getExcelPath() {
         return this.excelPath;
     }

         public void setExcelPath(String excelPath) {
         this.excelPath = excelPath;
    }

}