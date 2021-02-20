
package com.zhys.ys_webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UserNo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Md5Str" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WhereStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DataName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "userNo",
    "md5Str",
    "whereStr",
    "dataName"
})
@XmlRootElement(name = "GetDataFromEOS")
public class GetDataFromEOS {

    @XmlElementRef(name = "UserNo", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> userNo;
    @XmlElementRef(name = "Md5Str", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> md5Str;
    @XmlElementRef(name = "WhereStr", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> whereStr;
    @XmlElementRef(name = "DataName", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
    protected JAXBElement<String> dataName;

    /**
     * 获取userNo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getUserNo() {
        return userNo;
    }

    /**
     * 设置userNo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setUserNo(JAXBElement<String> value) {
        this.userNo = value;
    }

    /**
     * 获取md5Str属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getMd5Str() {
        return md5Str;
    }

    /**
     * 设置md5Str属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setMd5Str(JAXBElement<String> value) {
        this.md5Str = value;
    }

    /**
     * 获取whereStr属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getWhereStr() {
        return whereStr;
    }

    /**
     * 设置whereStr属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setWhereStr(JAXBElement<String> value) {
        this.whereStr = value;
    }

    /**
     * 获取dataName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDataName() {
        return dataName;
    }

    /**
     * 设置dataName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDataName(JAXBElement<String> value) {
        this.dataName = value;
    }

}
