package com.zhys.timer;

import com.alibaba.fastjson.JSONObject;
import com.fapiao.neon.client.in.DeductClient;
import com.fapiao.neon.model.CallResult;
import com.fapiao.neon.model.ExceptionResult;
import com.fapiao.neon.model.in.DeductResultInfo;
import com.fapiao.neon.model.in.DeductResultListInfo;
import com.fapiao.neon.param.in.CommonParamBody;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.lycheeframework.core.model.IPO;
import com.zhys.po.DeductParamBody;
import com.zhys.po.DeductParamSub;
import com.zhys.redis.RedisUtils;
import com.zhys.service.SQLManager;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScheduledTasks {
  
  @Autowired
  private RedisUtils redis;
  
  @Autowired
  private SQLManager manager;
  
  @Autowired
  private DeductClient deductClient;
  
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  //同步待勾选的发票
  @Scheduled(fixedRate = 60000L)
  public void gxcx() {
    DeductParamBody body = new DeductParamBody();
    body.setState("1");
    List<DeductParamBody> bodys = (List<DeductParamBody>) this.manager.list("deduct_param_body.queryList", body);
    if (bodys != null && bodys.size() > 0)
      for (DeductParamBody b : bodys) {
        CommonParamBody cpb = new CommonParamBody();
        cpb.setRequestId(b.getRequestId());
        log.info("=====================勾选结果请求参数{}",JSONObject.toJSON(cpb));
        CallResult<DeductResultListInfo> dr = this.deductClient.deductResult(cpb);
        if (dr.isSuccess()) {
        	log.info("=====================勾选结果返回结果success=============");
          if (dr.getData() != null) {
        	log.info("=====================勾选结果返回结果{}",JSONObject.toJSON(dr.getData()));
            DeductResultListInfo drl = (DeductResultListInfo)dr.getData();
            if (drl != null && drl.getInvoices() != null && drl.getInvoices().size() > 0)
              for (DeductResultInfo dri : drl.getInvoices()) {
            	  log.info("=====================勾选结果返回结果{}",JSONObject.toJSON(dri));
                if (("1".equals(dri.getDeductibleResult()) || "4".equals(dri.getDeductibleResult())) &&
                  "1".equals(dri.getDeductibleStatus())) {
                	log.info("=====================勾选结果返回成功结果{}",JSONObject.toJSON(dri));
                  DeductParamSub po = new DeductParamSub();
                  po.setInvoiceCode(dri.getInvoiceCode());
                  po.setInvoiceNumber(dri.getInvoiceNumber());
                  po.setState(dri.getDeductibleStatus());
                  po.setValidTax(dri.getValidTax());
                  po.setExportRejectNo(dri.getDeductibleResultMsg());
                  manager.update("deduct_param_sub.updateMsg", (IPO)po);
                  
                  /**
                   * 更新发票池发票状态
                   */
                  InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
                  pojo.setFpdm(dri.getInvoiceCode());
                  pojo.setFphm(dri.getInvoiceNumber());
                  pojo.setRzState("3");
                  pojo.setRzrq(new Date());
                  pojo.setKjdkq(dri.getDeductiblePeriod());
                  manager.update("invoice_head.updateRzStateByNo", pojo);
                  
                } else {
                	log.info("=====================勾选结果返回失败结果{}",JSONObject.toJSON(dri));
                    DeductParamSub po = new DeductParamSub();
                    po.setInvoiceCode(dri.getInvoiceCode());
                    po.setInvoiceNumber(dri.getInvoiceNumber());
                    po.setExportRejectNo(dri.getDeductibleResultMsg());
                    po.setState("2");
                    manager.update("deduct_param_sub.updateMsg", (IPO)po);
                    
                  }
              }
            DeductParamBody bo = new DeductParamBody();
            bo.setRequestId(b.getRequestId());
            bo.setState("2");
            manager.update("deduct_param_body.update", bo);
          } 
          continue;
        }else {
        	ExceptionResult er = dr.getExceptionResult();
			String errMsg = er.getMessage();
			String requestId = er.getRequestId();
			log.info("=====================勾选结果请求返回失败结果id={},re={}",requestId,JSONObject.toJSON(er));
			log.error("=====================勾选结果请求参数{}",JSONObject.toJSON(cpb));
			log.error("=====================勾选结果请求返回失败结果id={},{}",requestId,JSONObject.toJSON(er));
        } 
        ExceptionResult er = dr.getExceptionResult();
        if (er != null)
          log.info("获取勾选结果信息返回：" + er.getMessage()); 
      }  
  }

  //同步被撤销勾选的发票
  //状态  1：状态同步中  2：勾选成功  3：勾选失败     4:撤销状态同步中 5：撤销勾选成功  6 ：撤销勾选失败
  @Scheduled(fixedRate = 60000L)
  public void cancel_gxcx() {
    DeductParamBody body = new DeductParamBody();
    body.setState("4");
    List<DeductParamBody> bodys = (List<DeductParamBody>) this.manager.list("deduct_param_body.queryList", body);
    if (bodys != null && bodys.size() > 0)
      for (DeductParamBody b : bodys) {
        CommonParamBody cpb = new CommonParamBody();
        cpb.setRequestId(b.getRequestId());
        log.info("=====================取消勾选结果请求参数{}",JSONObject.toJSON(cpb));
        CallResult<DeductResultListInfo> dr = this.deductClient.deductResult(cpb);
        if (dr.isSuccess()) {
        	log.info("=====================取消勾选结果返回结果success=============");
          if (dr.getData() != null) {
        	log.info("=====================取消勾选结果返回结果{}",JSONObject.toJSON(dr.getData()));
            DeductResultListInfo drl = (DeductResultListInfo)dr.getData();
            if (drl != null && drl.getInvoices() != null && drl.getInvoices().size() > 0)
              for (DeductResultInfo dri : drl.getInvoices()) {
            	  log.info("=====================取消勾选结果返回结果{}",JSONObject.toJSON(dri));
                if ("1".equals(dri.getDeductibleResult()) && 
                  "0".equals(dri.getDeductibleStatus())) {
                	log.info("=====================取消勾选结果返回成功结果{}",JSONObject.toJSON(dri));
                  DeductParamSub po = new DeductParamSub();
                  po.setInvoiceCode(dri.getInvoiceCode());
                  po.setInvoiceNumber(dri.getInvoiceNumber());
                  po.setState("4");
                  po.setValidTax(dri.getValidTax());
                  po.setExportRejectNo(dri.getDeductibleResultMsg());
                  manager.update("deduct_param_sub.updateMsg", (IPO)po);
                  
                  /**
                   * 更新发票池发票状态
                   */
                  InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
                  pojo.setFpdm(dri.getInvoiceCode());
                  pojo.setFphm(dri.getInvoiceNumber());
                  pojo.setRzState("2");
                  pojo.setRzrq(new Date());
                  pojo.setKjdkq(dri.getDeductiblePeriod());
                  manager.update("invoice_head.updateRzStateByNo", pojo);
                  
                } else {
                	log.info("=====================取消勾选结果返回失败结果{}",JSONObject.toJSON(dri));
                    DeductParamSub po = new DeductParamSub();
                    po.setInvoiceCode(dri.getInvoiceCode());
                    po.setInvoiceNumber(dri.getInvoiceNumber());
                    po.setExportRejectNo(dri.getDeductibleResultMsg());
                    po.setState("5");
                    manager.update("deduct_param_sub.updateMsg", (IPO)po);
                    
                    /**
                     * 更新发票池发票状态
                     */
                    InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
                    pojo.setFpdm(dri.getInvoiceCode());
                    pojo.setFphm(dri.getInvoiceNumber());
                    pojo.setRzState("3");
                    manager.update("invoice_head.updateRzStateByNo", pojo);
                    
                  }
              }
            DeductParamBody bo = new DeductParamBody();
            bo.setRequestId(b.getRequestId());
            bo.setState("5");
            manager.update("deduct_param_body.update", bo);
          } 
          continue;
        }else {
        	ExceptionResult er = dr.getExceptionResult();
			String errMsg = er.getMessage();
			String requestId = er.getRequestId();
			log.info("=====================取消勾选结果请求返回失败结果id={},re={}",requestId,JSONObject.toJSON(er));
			log.error("=====================取消勾选结果请求参数{}",JSONObject.toJSON(cpb));
			log.error("=====================取消勾选结果请求返回失败结果id={},{}",requestId,JSONObject.toJSON(er));
        } 
        ExceptionResult er = dr.getExceptionResult();
        if (er != null)
          log.info("获取取消勾选结果信息返回：" + er.getMessage()); 
      }  
  }
  
  
}
