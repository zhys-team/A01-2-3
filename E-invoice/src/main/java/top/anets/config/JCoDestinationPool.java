package top.anets.config;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment; 
import lombok.extern.java.Log;
import top.anets.utils.MyDestinationDataProvider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Component
@Log
public class JCoDestinationPool {
    @Value("${httpclient.dev_or_prod}")
    private String dev_or_prod;

    
    MyDestinationDataProvider myProvider = MyDestinationDataProvider.getInstance();
    JCoDestination destination  ;
    Properties connectProperties2 = new Properties();
    String destinationName2 = "ABAP_AS2";
    @PostConstruct
    public void init(){
        Environment.registerDestinationDataProvider(myProvider);
        // Register the MyDestinationDataProvider 环境注册
        System.out.println("Test destination - " + destinationName2);
//      正式生产环境
        if(StringUtils.isNotEmpty(dev_or_prod)&&dev_or_prod.equals("prod")){
            log.info("SAP正式环境》》》》》》》》》》》》》");


            connectProperties2.setProperty(DestinationDataProvider.JCO_MSHOST, "172.30.96.132");//服务器  
connectProperties2.setProperty(DestinationDataProvider.JCO_MSSERV, "3601");//服务端口  
            connectProperties2.setProperty(DestinationDataProvider.JCO_GROUP, "Logon_Group");//组名称  
 connectProperties2.setProperty(DestinationDataProvider.JCO_CLIENT, "800"); //SAP集团客户端号  
connectProperties2.setProperty(DestinationDataProvider.JCO_USER,"RFCUSER"); //SAP用户名  
connectProperties2.setProperty(DestinationDataProvider.JCO_PASSWD, "Welcome11");  //密码  
connectProperties2.setProperty(DestinationDataProvider.JCO_LANG, "zh"); //登录语言  
connectProperties2.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "20"); //最大连接数    
connectProperties2.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "40"); //最大连接线程  
 connectProperties2.setProperty(DestinationDataProvider.JCO_MAX_GET_TIME, "240"); //最大连接时间
        }else{
            log.info("SAP测试环境》》》》》》》》》》》》》");
            connectProperties2.setProperty(DestinationDataProvider.JCO_ASHOST,

                    "192.168.200.61");

            connectProperties2.setProperty(DestinationDataProvider.JCO_SYSNR, "00");

            connectProperties2

                    .setProperty(DestinationDataProvider.JCO_CLIENT, "400");

            connectProperties2.setProperty(DestinationDataProvider.JCO_USER,

                    "JS01");

            connectProperties2.setProperty(DestinationDataProvider.JCO_PASSWD,

                    "Aa123456");
            System.out.println("测试环境配置输入成功");
            log.info("==========================================");
            
        }




        connectProperties2.setProperty(DestinationDataProvider.JCO_LANG, "ZH");

        connectProperties2.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,

                "40");

        connectProperties2.setProperty(

                DestinationDataProvider.JCO_POOL_CAPACITY, "20");



        // Add a destination

        myProvider.addDestination(destinationName2, connectProperties2);

        getJCoDestination();
    }

    {

    }

public JCoDestination  getJCoDestination(){

    try {
        JCoDestination DES_ABAP_AS2 = JCoDestinationManager

                .getDestination(destinationName2);
        DES_ABAP_AS2.ping();

        System.out.println("Destination - " + destinationName2 + " is ok");
        log.info("Destination - " + destinationName2 + " is ok");
        destination  =  DES_ABAP_AS2;
        return destination;
    } catch (Exception ex) {

//        ex.printStackTrace();
 
        log.info("Destination - " + destinationName2 + " is invalid"+ex.getMessage());
    }

    return  null;
}







}
