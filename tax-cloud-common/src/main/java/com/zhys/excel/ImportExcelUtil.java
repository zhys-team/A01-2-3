package com.zhys.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.invoice.po.InvoiceHead;
import com.invoice.pojo.InvoiceHeadExcel;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ImportExcelUtil {

    /********************************************导入Excel**********************************************/

    /**
     * 读取Excel到List<object><>对象
     *
     * @param fieldMap 表示表格head与对象字段对应关系，key:tableHead,value:objProperty
     * @param clazz    对象Class
     * @return 数据List集合，可强转为实际对象的List集合
     * @throws Exception 文件未找到，文件类型不支持，set方法未找到
     */
    public static <T> List<T> readExcel(Map<String, String> fieldMap, Class clazz,InputStream inputStream ,String fileName,int sheetNum){
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
                .substring(fileName.lastIndexOf(".") + 1);
        if ("xls".equals(extension.toLowerCase())) {
            return read2003Excel(fieldMap, clazz, inputStream,sheetNum);
        } else if ("xlsx".equals(extension.toLowerCase())) {
            return read2007Excel(fieldMap, clazz, inputStream,sheetNum);
        } else {
            try {
                throw new IOException("不支持的文件类型");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * 读取2007版Excel（以xls结尾）到List<实际对象><>对象
     *
     * @param fieldMap 表示表格head与对象字段对应关系，key:tableHead,value:objProperty
     * @param clazz    对象Class
     * @return 数据List集合
     * @throws Exception 文件未找到，文件类型不支持，set方法未找到
     */
    public static <T> List<T> read2007Excel(Map<String, String> fieldMap, Class<T> clazz, InputStream inputStream,int sheetNum) {
        List<T> list = new ArrayList<>();
        try {
            // 构造 XSSFWorkbook 对象，strPath 传入文件路径
            XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
            // 读取第一章表格内容
            XSSFSheet sheet = xwb.getSheetAt(sheetNum);
            Object value = null;
            XSSFRow row = null;
            XSSFCell cell = null;
            int counter = 0;
            String[] setMethodNames = new String[fieldMap.entrySet().size()];
            List<Field> fields =new ArrayList<Field>();
            for (int i = sheet.getFirstRowNum(); counter < sheet
                    .getPhysicalNumberOfRows(); i++) {

                T t = clazz.newInstance();

                row = sheet.getRow(i);
                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<Object>();
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    // 格式化 number String
                    DecimalFormat df = new DecimalFormat("0");
                    // 字符
                    // 格式化日期字符串
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    // 格式化数字
                    DecimalFormat nf = new DecimalFormat("0.00");
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            System.out.println(i + "行" + j + " 列 is String type");
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            System.out.println(i + "行" + j
                                    + " 列 is Number type ; DateFormt:"
                                    + cell.getCellStyle().getDataFormatString());
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            System.out.println(i + "行" + j + " 列 is Boolean type");
                            value = cell.getBooleanCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            System.out.println(i + "行" + j + " 列 is Blank type");
                            value = "";
                            break;
                        default:
                            System.out.println(i + "行" + j + " 列 is default type");
                            value = cell.toString();
                    }
                    if (value == null || "".equals(value)) {
                        continue;
                    }

                    String setMethodName;
                    if (i == 0) {
                    	fields.add(clazz.getDeclaredField(fieldMap.get(value)));
                        String fieldName = clazz.getDeclaredField(fieldMap.get(value)).getName();
                        setMethodName = "set"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
                        setMethodNames[j] = setMethodName;
                    } else {
                        setMethodName = setMethodNames[j];
                        if(fields.get(j).getType().getName().equals("java.lang.Long")) {
                        	clazz.getDeclaredMethod(setMethodName, new Class[]{fields.get(j).getType()}).invoke(t, Long.parseLong(value.toString()));
                        }else if(fields.get(j).getType().getName().equals("java.lang.Integer")){
                        	clazz.getDeclaredMethod(setMethodName, new Class[]{fields.get(j).getType()}).invoke(t, Integer.parseInt(value.toString()));
                        }else {
                        clazz.getDeclaredMethod(setMethodName, new Class[]{fields.get(j).getType()}).invoke(t, value);
                        }
                    }

                }
                if (i != 0) {
                    list.add(t);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     * 读取2003版Excel（以xls结尾）到List<实际对象><>对象
     *
     * @param fieldMap 表示表格head与对象字段对应关系，key:tableHead,value:objProperty
     * @param clazz    对象Class
     * @return 数据List集合
     * @throws Exception 文件未找到，文件类型不支持，set方法未找到
     */
    public static <T> List<T> read2003Excel(Map<String, String> fieldMap, Class<T> clazz, InputStream inputStream,int sheetNum) {
        List<T> list = new ArrayList<>();
        try {
            HSSFWorkbook hwb = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = hwb.getSheetAt(sheetNum);
            Object value = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            int counter = 0;

            String[] setMethodNames = new String[fieldMap.entrySet().size()];
            List<Class> clzs = new ArrayList<>();

            Field[] fields = clazz.getDeclaredFields();

            if (clazz.getSuperclass()!=null){
                fields = (Field[]) org.apache.commons.lang.ArrayUtils.addAll(fields, clazz.getSuperclass().getDeclaredFields());
            }

            for (int i = sheet.getFirstRowNum(); counter < sheet
                    .getPhysicalNumberOfRows(); i++) {
                row = sheet.getRow(i);

                T t = clazz.newInstance();

                if (row == null) {
                    continue;
                } else {
                    counter++;
                }
                List<Object> linked = new LinkedList<Object>();
                for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                    cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    // 格式化 number String
                    DecimalFormat df = new DecimalFormat("0");
                    // 字符, 格式化日期字符串
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "yyyy-MM-dd HH:mm:ss");
                    // 格式化数字
                    DecimalFormat nf = new DecimalFormat("0.00");
                    switch (cell.getCellType()) {
                        case XSSFCell.CELL_TYPE_STRING:
                            System.out.println(i + "行" + j + " 列 is String type");
                            value = cell.getStringCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_NUMERIC:
                            System.out.println(i + "行" + j
                                    + " 列 is Number type ; DateFormt:"
                                    + cell.getCellStyle().getDataFormatString());
                            if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                                value = df.format(cell.getNumericCellValue());
                            } else if ("General".equals(cell.getCellStyle()
                                    .getDataFormatString())) {
                                value = nf.format(cell.getNumericCellValue());
                            } else {
                                value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                        .getNumericCellValue()));
                            }
                            break;
                        case XSSFCell.CELL_TYPE_BOOLEAN:
                            System.out.println(i + "行" + j + " 列 is Boolean type");
                            value = cell.getBooleanCellValue();
                            break;
                        case XSSFCell.CELL_TYPE_BLANK:
                            System.out.println(i + "行" + j + " 列 is Blank type");
                            value = "";
                            break;
                        default:
                            System.out.println(i + "行" + j + " 列 is default type");
                            value = cell.toString();
                    }
                    if (value == null || "".equals(value)) {
                        continue;
                    }

                    String setMethodName;
                    Class<?> type;
                    if (i == 0) {
                        String fieldName;
                        try {
                            fieldName = clazz.getDeclaredField(fieldMap.get(value)).getName();
                            type = clazz.getDeclaredField(fieldMap.get(value)).getType();
                            clzs.add(type);
                        }catch (Exception e){
                            fieldName = clazz.getSuperclass().getDeclaredField(fieldMap.get(value)).getName();
                            type = clazz.getSuperclass().getDeclaredField(fieldMap.get(value)).getType();
                            clzs.add(type);
                        }
                        setMethodName = "set"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
                        setMethodNames[j] = setMethodName;
                    } else {
                        setMethodName = setMethodNames[j];
                        type=clzs.get(j);
                        try {
                            clazz.getDeclaredMethod(setMethodName, new Class[]{type}).invoke(t, value);
                        } catch (Exception e) {
                            clazz.getSuperclass().getDeclaredMethod(setMethodName, new Class[]{type}).invoke(t, value);
                        }
                    }

                }
                if (i != 0) {
                    list.add(t);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public static void impInvoice1() {
    	Map<String,String> map = new HashMap<String,String>();
    	//map.put("字符串1", "str");
    	//map.put("字符串2", "str2");
    	Class clazz = InvoiceHeadExcel.class;
    	Field [] fs = clazz.getDeclaredFields();
    	if(fs != null&&fs.length>0) {
    		for(Field f:fs) {
    			ExcelCell ec = f.getAnnotation(ExcelCell.class);
    			if(ec!=null) {
    				map.put(ec.name(), f.getName());
    			}
    			
    		}
    	}
    	
    	File file = new File("C:\\Users\\11734\\Desktop\\test.xlsx");  
        if(!file.exists()){  
            throw new RuntimeException("要读取的文件不存在");  
        } 
    	try {
			List<InvoiceHeadExcel> etps = ImportExcelUtil.readExcel(map,InvoiceHeadExcel.class,new FileInputStream(file),"test.xlsx",0);
			etps.forEach(e -> System.out.println(e.getKprq()));
		System.out.println(etps.size());
    	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public static void impInvoice2(FileInputStream fis) {
    	Map<String,String> map = new HashMap<String,String>();
    	//map.put("字符串1", "str");
    	//map.put("字符串2", "str2");
    	Class clazz = InvoiceHeadExcel.class;
    	Field [] fs = clazz.getDeclaredFields();
    	if(fs != null&&fs.length>0) {
    		for(Field f:fs) {
    			ExcelCell ec = f.getAnnotation(ExcelCell.class);
    			if(ec!=null) {
    				map.put(ec.name(), f.getName());
    			}
    			
    		}
    	}
    	
			List<InvoiceHeadExcel> etps = ImportExcelUtil.readExcel(map,InvoiceHeadExcel.class,fis,"test.xlsx",0);
			etps.forEach(e -> System.out.println(e.getKprq()));
		System.out.println(etps.size());
	}

}
