package com.zhys.ys_webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"getDataFromEOSResult"})
@XmlRootElement(name = "GetDataFromEOSResponse")
public class GetDataFromEOSResponse {
  @XmlElementRef(name = "GetDataFromEOSResult", namespace = "http://eossoft.com/WebServices/", type = JAXBElement.class, required = false)
  protected JAXBElement<String> getDataFromEOSResult;
  
  public JAXBElement<String> getGetDataFromEOSResult() {
    return this.getDataFromEOSResult;
  }
  
  public void setGetDataFromEOSResult(JAXBElement<String> value) {
    this.getDataFromEOSResult = value;
  }
}
