package com.zhys.excel;

import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.zhys.utils.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * excel导出工具类
 *
 * @author lihui
 * @date 2017-12-3
 */
public class ExportExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExportExcelUtil.class);

    public static HSSFWorkbook creatExcel(String name, List<?> list) {
        if (StringUtil.isEmpty(name) || list == null || list.size() < 1) {
            return null;
        }
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet(name);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中  
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        HSSFCell cell;

        Class<?> c = list.get(0).getClass();
        Field[] fields = c.getDeclaredFields();

        if (c.getSuperclass()!=null){
            fields = (Field[]) org.apache.commons.lang.ArrayUtils.addAll(fields, c.getSuperclass().getDeclaredFields());
        }


        List<CellInfo> list_cell = new ArrayList<CellInfo>();
        int count = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(ExcelCell.class)) {
                ExcelCell excelcell = (ExcelCell) field.getAnnotation(ExcelCell.class);
                cell = row.createCell(excelcell.index());
                cell.setCellValue(excelcell.name());
                cell.setCellStyle(style);
                CellInfo tmp = new CellInfo();
                tmp.setIndex(excelcell.index());
                String fieldname = field.getName();
                tmp.setMethod("get" + fieldname.substring(0, 1).toUpperCase() + fieldname.substring(1));
                tmp.setSdf(excelcell.dateFormat());
                list_cell.add(tmp);
                count++;
            }
        }
        if (count == 0)
            return null;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            for (CellInfo cellInfo : list_cell) {
                try {
                    Method method;
                    try{
                        method = c.getDeclaredMethod(cellInfo.getMethod());
                    }catch (Exception e){
                        method = c.getSuperclass().getDeclaredMethod(cellInfo.getMethod());
                    }
                    Object obj = method.invoke(list.get(i));
                    if (obj != null) {
                        if (obj instanceof Date) {
                            row.createCell(cellInfo.getIndex()).setCellValue(formatDateTime((Date) obj, cellInfo.getSdf()));
                        } else {
                            row.createCell(cellInfo.getIndex()).setCellValue(obj.toString());
                        }

                    } else {
                        row.createCell(cellInfo.getIndex()).setCellValue("");
                    }
                    log.debug(cellInfo.getMethod() + "方法执行后结果为：");
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return null;
                }

            }
        }
        return wb;
    }

    private static String formatDateTime(Date date, String format) {
        if (date == null) return null;

        DateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

//    public static void main(String[] args) {
//        List<ExcelTestPojo> list = new ArrayList<ExcelTestPojo>();
//        ExcelTestPojo pj = new ExcelTestPojo();
//        pj.setStr("基于注解的Excel导出方法");
//        list.add(pj);
//
//        try {
//            FileOutputStream fout = new FileOutputStream("d:/students.xls");
//            ExportExcelUtil.creatExcel("test", list).write(fout);
//            fout.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
