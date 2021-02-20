package com.zhys.ys_webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"userNo", "md5Str", "dataName", "dataStr"})
@XmlRootElement(name = "SendDataToEOS")
public class SendDataToEOS {
  @XmlElementRef(name = "UserNo", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
  protected JAXBElement<String> userNo;
  
  @XmlElementRef(name = "Md5Str", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
  protected JAXBElement<String> md5Str;
  
  @XmlElementRef(name = "DataName", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
  protected JAXBElement<String> dataName;
  
  @XmlElementRef(name = "DataStr", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
  protected JAXBElement<String> dataStr;
  
  public JAXBElement<String> getUserNo() {
    return this.userNo;
  }
  
  public void setUserNo(JAXBElement<String> value) {
    this.userNo = value;
  }
  
  public JAXBElement<String> getMd5Str() {
    return this.md5Str;
  }
  
  public void setMd5Str(JAXBElement<String> value) {
    this.md5Str = value;
  }
  
  public JAXBElement<String> getDataName() {
    return this.dataName;
  }
  
  public void setDataName(JAXBElement<String> value) {
    this.dataName = value;
  }
  
  public JAXBElement<String> getDataStr() {
    return this.dataStr;
  }
  
  public void setDataStr(JAXBElement<String> value) {
    this.dataStr = value;
  }
}
