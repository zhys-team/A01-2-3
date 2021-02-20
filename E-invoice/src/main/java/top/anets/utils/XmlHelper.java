package top.anets.utils;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlHelper {
    /**
     * Object转XML
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static String ObjectToXml(Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass());    // 获取上下文对象
        Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GB2312");  // 设置编码字符集
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(object, baos);
        String xmlObj = new String(baos.toByteArray());         // 生成XML字符串
        return xmlObj.trim();
    }

    /**
     * Object转XML
     *
     * @param object
     * @return
     * @throws Exception
     */
    public static String ObjectToXmlUtf8(Object object) throws Exception {
        JAXBContext context = JAXBContext.newInstance(object.getClass());    // 获取上下文对象
        Marshaller marshaller = context.createMarshaller(); // 根据上下文获取marshaller对象
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  // 设置编码字符集
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); // 格式化XML输出，有分行和缩进
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        marshaller.marshal(object, baos);
        String xmlObj = new String(baos.toByteArray());         // 生成XML字符串
        return xmlObj.trim();
    }

    /**
     * XML转Object
     *
     * @param xmlStr
     * @param classz
     * @return
     */
    public static Object XmlToObject(String xmlStr, Class classz) {
        try {
            JAXBContext context = JAXBContext.newInstance(classz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Object object = unmarshaller.unmarshal(new StringReader(xmlStr));
            return object;
        } catch (JAXBException ex) {
            ex.printStackTrace();
            return null;
        }
    }


    /**
     * xml格式字符串获取所有子节点存入list,每个List分两个部分：name和value，中间用“:”隔开
     *
     * @param srcXml
     * @return
     */
    public static List<String> getListXML(String srcXml) {
        //存储xml元素信息的容器
        List<Leaf> elemList = new ArrayList<Leaf>();
        List<String> list = new ArrayList<String>();
        Document srcdoc = null;
        try {
            srcdoc = DocumentHelper.parseText(srcXml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element elem = srcdoc.getRootElement();
        getElementList(elem, elemList);
        for (Iterator<Leaf> it = elemList.iterator(); it.hasNext(); ) {
            Leaf leaf = it.next();
            list.add(leaf.getName() + ":" + leaf.getValue());
        }
        return list;
    }

    /**
     * 递归遍历方法
     *
     * @param element
     */
    public static void getElementList(Element element, List<Leaf> elemList) {
        List elements = element.elements();
        if (elements.size() == 0) {
            //没有子元素
            //String xpath = element.getPath();
            String xpath = element.getName();
            String value = element.getTextTrim();
            Leaf leaf = new Leaf(xpath, value);
            elemList.add(leaf);
        } else {
            //有子元素
            for (Iterator it = elements.iterator(); it.hasNext(); ) {
                Element elem = (Element) it.next();
                //递归遍历
                getElementList(elem, elemList);
            }
        }
    }

    static class Leaf {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Leaf(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }
}