package com.zhys.ys_webservice;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {
  private static final QName _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
  
  private static final QName _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
  
  private static final QName _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
  
  private static final QName _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
  
  private static final QName _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
  
  private static final QName _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
  
  private static final QName _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
  
  private static final QName _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
  
  private static final QName _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
  
  private static final QName _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
  
  private static final QName _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
  
  private static final QName _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
  
  private static final QName _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
  
  private static final QName _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
  
  private static final QName _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
  
  private static final QName _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
  
  private static final QName _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
  
  private static final QName _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
  
  private static final QName _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
  
  private static final QName _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
  
  private static final QName _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
  
  private static final QName _GetDataFromEOSResponseGetDataFromEOSResult_QNAME = new QName("http://eossoft.com/WebServices/", "GetDataFromEOSResult");
  
  private static final QName _GetDataFromEOSWhereStr_QNAME = new QName("http://eossoft.com/WebServices/", "WhereStr");
  
  private static final QName _GetDataFromEOSDataName_QNAME = new QName("http://eossoft.com/WebServices/", "DataName");
  
  private static final QName _GetDataFromEOSUserNo_QNAME = new QName("http://eossoft.com/WebServices/", "UserNo");
  
  private static final QName _GetDataFromEOSMd5Str_QNAME = new QName("http://eossoft.com/WebServices/", "Md5Str");
  
  private static final QName _SendDataToEOSResponseSendDataToEOSResult_QNAME = new QName("http://eossoft.com/WebServices/", "SendDataToEOSResult");
  
  private static final QName _SendDataToEOSDataStr_QNAME = new QName("http://eossoft.com/WebServices/", "DataStr");
  
  public GetDataFromEOSResponse createGetDataFromEOSResponse() {
    return new GetDataFromEOSResponse();
  }
  
  public GetDataFromEOS createGetDataFromEOS() {
    return new GetDataFromEOS();
  }
  
  public SendDataToEOSResponse createSendDataToEOSResponse() {
    return new SendDataToEOSResponse();
  }
  
  public SendDataToEOS createSendDataToEOS() {
    return new SendDataToEOS();
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
  public JAXBElement<BigInteger> createUnsignedLong(BigInteger value) {
    return new JAXBElement<>(_UnsignedLong_QNAME, BigInteger.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
  public JAXBElement<Short> createUnsignedByte(Short value) {
    return new JAXBElement<>(_UnsignedByte_QNAME, Short.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
  public JAXBElement<Long> createUnsignedInt(Long value) {
    return new JAXBElement<>(_UnsignedInt_QNAME, Long.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
  public JAXBElement<Integer> createChar(Integer value) {
    return new JAXBElement<>(_Char_QNAME, Integer.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
  public JAXBElement<Short> createShort(Short value) {
    return new JAXBElement<>(_Short_QNAME, Short.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
  public JAXBElement<String> createGuid(String value) {
    return new JAXBElement<>(_Guid_QNAME, String.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
  public JAXBElement<Integer> createUnsignedShort(Integer value) {
    return new JAXBElement<>(_UnsignedShort_QNAME, Integer.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
  public JAXBElement<BigDecimal> createDecimal(BigDecimal value) {
    return new JAXBElement<>(_Decimal_QNAME, BigDecimal.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
  public JAXBElement<Boolean> createBoolean(Boolean value) {
    return new JAXBElement<>(_Boolean_QNAME, Boolean.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
  public JAXBElement<Duration> createDuration(Duration value) {
    return new JAXBElement<>(_Duration_QNAME, Duration.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
  public JAXBElement<byte[]> createBase64Binary(byte[] value) {
    return (JAXBElement)new JAXBElement<>(_Base64Binary_QNAME, (Class)byte[].class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
  public JAXBElement<Integer> createInt(Integer value) {
    return new JAXBElement<>(_Int_QNAME, Integer.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
  public JAXBElement<Long> createLong(Long value) {
    return new JAXBElement<>(_Long_QNAME, Long.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
  public JAXBElement<String> createAnyURI(String value) {
    return new JAXBElement<>(_AnyURI_QNAME, String.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
  public JAXBElement<Float> createFloat(Float value) {
    return new JAXBElement<>(_Float_QNAME, Float.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
  public JAXBElement<XMLGregorianCalendar> createDateTime(XMLGregorianCalendar value) {
    return new JAXBElement<>(_DateTime_QNAME, XMLGregorianCalendar.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
  public JAXBElement<Byte> createByte(Byte value) {
    return new JAXBElement<>(_Byte_QNAME, Byte.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
  public JAXBElement<Double> createDouble(Double value) {
    return new JAXBElement<>(_Double_QNAME, Double.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
  public JAXBElement<QName> createQName(QName value) {
    return new JAXBElement<>(_QName_QNAME, QName.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
  public JAXBElement<Object> createAnyType(Object value) {
    return new JAXBElement(_AnyType_QNAME, Object.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
  public JAXBElement<String> createString(String value) {
    return new JAXBElement<>(_String_QNAME, String.class, null, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "GetDataFromEOSResult", scope = GetDataFromEOSResponse.class)
  public JAXBElement<String> createGetDataFromEOSResponseGetDataFromEOSResult(String value) {
    return new JAXBElement<>(_GetDataFromEOSResponseGetDataFromEOSResult_QNAME, String.class, GetDataFromEOSResponse.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "WhereStr", scope = GetDataFromEOS.class)
  public JAXBElement<String> createGetDataFromEOSWhereStr(String value) {
    return new JAXBElement<>(_GetDataFromEOSWhereStr_QNAME, String.class, GetDataFromEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "DataName", scope = GetDataFromEOS.class)
  public JAXBElement<String> createGetDataFromEOSDataName(String value) {
    return new JAXBElement<>(_GetDataFromEOSDataName_QNAME, String.class, GetDataFromEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "UserNo", scope = GetDataFromEOS.class)
  public JAXBElement<String> createGetDataFromEOSUserNo(String value) {
    return new JAXBElement<>(_GetDataFromEOSUserNo_QNAME, String.class, GetDataFromEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "Md5Str", scope = GetDataFromEOS.class)
  public JAXBElement<String> createGetDataFromEOSMd5Str(String value) {
    return new JAXBElement<>(_GetDataFromEOSMd5Str_QNAME, String.class, GetDataFromEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "SendDataToEOSResult", scope = SendDataToEOSResponse.class)
  public JAXBElement<String> createSendDataToEOSResponseSendDataToEOSResult(String value) {
    return new JAXBElement<>(_SendDataToEOSResponseSendDataToEOSResult_QNAME, String.class, SendDataToEOSResponse.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "DataName", scope = SendDataToEOS.class)
  public JAXBElement<String> createSendDataToEOSDataName(String value) {
    return new JAXBElement<>(_GetDataFromEOSDataName_QNAME, String.class, SendDataToEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "UserNo", scope = SendDataToEOS.class)
  public JAXBElement<String> createSendDataToEOSUserNo(String value) {
    return new JAXBElement<>(_GetDataFromEOSUserNo_QNAME, String.class, SendDataToEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "Md5Str", scope = SendDataToEOS.class)
  public JAXBElement<String> createSendDataToEOSMd5Str(String value) {
    return new JAXBElement<>(_GetDataFromEOSMd5Str_QNAME, String.class, SendDataToEOS.class, value);
  }
  
  @XmlElementDecl(namespace = "http://eossoft.com/WebServices/", name = "DataStr", scope = SendDataToEOS.class)
  public JAXBElement<String> createSendDataToEOSDataStr(String value) {
    return new JAXBElement<>(_SendDataToEOSDataStr_QNAME, String.class, SendDataToEOS.class, value);
  }
}
