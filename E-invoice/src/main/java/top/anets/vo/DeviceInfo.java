/**
 * 
 */
package top.anets.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Administrator
 * 税控设备信息
 */
@Data
@Accessors(chain = true)
public class DeviceInfo {
	/*
	 * 税控设备编号
	 */
    private String sksbbh;
    
    /**
     * 纳税人识别号
     */
    private String nsrsbh;
    /**
     * 纳税人名称
     */
    private String nsrmc;
    /**
     * 税务机关代码
     */
    private String swjgdm;
    /**
     * 税务机关名称
     */
    private String  swjgmc;
    /**
     * 发票类型代码
     */
    private String  fplxdm;
    /**
     * 当前时钟
     */
    private String dqsz;
    /**
     * 启用时间
     */
    private String qysj;
    /**
     * 版本号
     */
    private String bbh;
    /**
     * 开票机号 ,0是主机 非0是分机
     */
    private String kpjh;
    /**
     * 企业类型，即特殊企业标识 "00"不是特殊企业  "01"是特殊企业
     */
    private String qylx;
    /**
     * 保留信息
     */
    private String blxx;
    /**
     * 其它扩展信息
     */
    private String qtkzxx;
    /**
     * 返回代码
     */
    private String returncode;
    /**
     * 返回信息
     */
    private String returnmsg;
    
    
}
