package com.zhys.serivce.impl;

import com.zhys.base.BaseRedisService;
import com.zhys.base.po.RateCode;
import com.zhys.bw.AllInvoiceInfo;
import com.zhys.bw.BaseItem;
import com.zhys.bw.Fpcy;
import com.zhys.excel.ExcelCell;
import com.zhys.excel.ImportExcelUtil;
import com.zhys.exception.BusinessException;
import com.zhys.po.DeductParamSub;
import com.zhys.po.Dkhz;
import com.zhys.po.InvoiceReview;
import com.zhys.po.ListTZInvoice;
import com.zhys.po.TzsysEasInvoicereceive;
import com.zhys.redis.RedisUtils;
import com.zhys.service.InvoiceHeadService;
import com.zhys.service.SQLManager;
import com.zhys.service.ValidateRecordService;
import com.zhys.utils.DateUtils;
import com.zhys.utils.HttpGetUtil;
import com.zhys.utils.POJOConvertUtil;

import io.swagger.annotations.ApiModelProperty;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.sound.midi.Soundbank;

import com.lycheeframework.core.cmp.kit.Pages;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fapiao.neon.client.in.BaseClient;
import com.fapiao.neon.client.in.CheckInvoiceClient;
import com.fapiao.neon.client.in.DeductClient;
import com.fapiao.neon.client.in.impl.CheckInvoiceClientImpl;
import com.fapiao.neon.enums.InspectReturnMappingEnum;
import com.fapiao.neon.model.CallResult;
import com.fapiao.neon.model.ExceptionResult;
import com.fapiao.neon.model.in.ApplyDeductResultInfo;
import com.fapiao.neon.model.in.inspect.BaseInvoice;
import com.fapiao.neon.model.in.inspect.ENormalInvoiceInfo;
import com.fapiao.neon.model.in.inspect.NormalInvoiceInfo;
import com.fapiao.neon.model.in.inspect.RollInvoiceInfo;
import com.fapiao.neon.model.in.inspect.SpecialInvoiceInfo;
import com.fapiao.neon.model.in.inspect.TollInvoiceInfo;
import com.fapiao.neon.model.in.inspect.TransportInvoiceInfo;
import com.fapiao.neon.model.in.inspect.UsedCarInvoiceInfo;
import com.fapiao.neon.model.in.inspect.VehicleInvoiceInfo;
import com.fapiao.neon.param.in.DeductInvoice;
import com.fapiao.neon.param.in.DeductParamBody;
import com.fapiao.neon.param.in.InvoiceInspectionParamBody;
import com.fapiao.neon.util.InvoiceTypeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.util.concurrent.Service;
import com.invoice.po.IndexEntity;
import com.invoice.po.IndexQueryEntity;
import com.invoice.po.InvoiceBody;
import com.invoice.po.InvoiceDetail;
import com.invoice.po.InvoiceDoubt;
import com.invoice.po.InvoiceHead;
import com.invoice.po.InvoiceHeadBW;
import com.invoice.po.RuleMatch;
import com.invoice.po.RuleMatchSub;
import com.invoice.po.ValidateRecord;
import com.invoice.pojo.HeadList;
import com.invoice.pojo.IncomeCount;
import com.invoice.pojo.InvoiceHeadExcel;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.invoice.pojo.PersonalTicket;
import com.invoice.pojo.RuleMatchPoJo;
import com.lycheeframework.core.cmp.kit.EasyPage;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.record.chart.BeginRecord;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
//import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

//import org.hamcrest.*;

@Slf4j
@RestController
public class InvoiceHeadServiceImpl implements InvoiceHeadService {

    @Autowired
    private SQLManager manager;
//	@Autowired
//	private BaseRedisService redis;


    @Autowired
    private RedisUtils redis;

//	@Autowired
//	private BaseClient baseClient;

    @Resource
    private CheckInvoiceClient checkInvoiceClient;

    @Autowired
    private ValidateRecordService vrs;

    @Resource
    private DeductClient deductClient;

    @Transactional
    @Override
    public InvoiceHead save(@RequestBody InvoiceHead invoiceHead) {
        if (invoiceHead.getId() != null) {//修改
            //invoiceHead = ruleMatch(invoiceHead);
            manager.update("invoice_head.update", invoiceHead);
            List<InvoiceBody> bodys = invoiceHead.getBodys();
            if (bodys != null && bodys.size() > 0) {
                manager.delete("invoice_body.delByParent", invoiceHead);
                for (InvoiceBody body : bodys) {
                    body.setHeadId(invoiceHead.getId());
                    manager.insert("invoice_body.create", body);
                }
            }
            return invoiceHead;
        } else {//插入
            InvoiceHeadPoJo head = new InvoiceHeadPoJo();
            head.setFpdm(invoiceHead.getFpdm());
            head.setFphm(invoiceHead.getFphm());
            if (StringUtils.isEmpty(invoiceHead.getFpdm()) && StringUtils.isEmpty(invoiceHead.getFphm())) {
                return invoiceHead;
            }
            List<InvoiceHead> heads = (List<InvoiceHead>) manager.list("invoice_head.queryList", head);
            if (heads != null && heads.size() > 0) {

                if ("1".equals(heads.get(0).getBxState())) {
                    log.info("已报销不能修改，直接返回数据======{}", JSONObject.toJSONString(heads.get(0)));
                    return heads.get(0);
                }
                //修改
                invoiceHead = ruleMatch(invoiceHead);
                invoiceHead.setId(heads.get(0).getId());
                manager.update("invoice_head.update", invoiceHead);
                List<InvoiceBody> bodys = invoiceHead.getBodys();
                if (bodys != null && bodys.size() > 0) {
                    manager.delete("invoice_body.delByParent", invoiceHead);
                    for (InvoiceBody body : bodys) {
                        body.setHeadId(invoiceHead.getId());
                        manager.insert("invoice_body.create", body);
                    }
                }
            } else {
//				invoiceHead.setSpecialType(getSpecialType(invoiceHead.getFplx()));
//				invoiceHead.setTaxAttr(getTaxAttr(invoiceHead.getFplx()));
                invoiceHead = ruleMatch(invoiceHead);
                invoiceHead.setCreateTime(new Date());
                manager.insert("invoice_head.create", invoiceHead);
                List<InvoiceBody> bodys = invoiceHead.getBodys();
                if (bodys != null && bodys.size() > 0) {
                    for (InvoiceBody body : bodys) {
                        body.setHeadId(invoiceHead.getId());
                        manager.insert("invoice_body.create", body);
                    }
                } else {
//没有发票明细的发票
                    String hwmc = null;
                    if ("10503".equals(invoiceHead.getFplx())) {
                        hwmc = "火车票";

                    } else if ("10505".equals(invoiceHead.getFplx())) {
                        hwmc = "汽车票";

                    } else if ("10506".equals(invoiceHead.getFplx())) {
                        hwmc = "飞机票";

                    } else if ("10507".equals(invoiceHead.getFplx())) {
                        hwmc = "过路过桥";

                    } else if ("10500".equals(invoiceHead.getFplx())) {
                        hwmc = "打车票";

                    } else if ("10200".equals(invoiceHead.getFplx())) {
                        hwmc = "定额发票";

                    }
                    // 10100 增值税专用发票 10200 定额发票 10507 过路费发票
                    // 10101 增值税普通发票 10400 机打发票 10900 可报销其他发票
                    // 10102 增值税电子普通发票 10500 出租车发票 00000 其他
                    // 10103 增值税普通发票(卷票) 10503 火车票 20100 国际小票
                    // 10104 机动车销售统一发票 10505 客运汽车
                    // 10105 二手车销售统一发票 10506 航空运输电子客票行程单

                    if (hwmc != null) {
                        log.info("货物名称：" + hwmc);
                        String bmn = ((RateCode) redis.hget("ratecode", hwmc)).getBusinessMatterName();
                        String dl = ((RateCode) redis.hget("ratecode", hwmc)).getDl();
                        String icon = ((RateCode) redis.hget("ratecode", hwmc)).getIcon();
                        if (StringUtils.isNotEmpty(bmn) && !"N/A".equals(bmn)) {
                            log.info("{}匹配出税码信息》》》》", hwmc);
                            invoiceHead.setYwsx(dl);
                            invoiceHead.setMxsx(bmn);
                            invoiceHead.setIcon(icon);

                        } else {
//						invoiceHead.setYwsx("其他");
//						invoiceHead.setMxsx("其他");
//						invoiceHead.setIcon("qita");
                            log.info("{}未匹配出税码信息》》》》", hwmc);
                        }
                        manager.update("invoice_head.update", invoiceHead);
                    }
                }
            }
            return invoiceHead;
        }
    }


    @Override
    public InvoiceHead queryByEntity(InvoiceHead invoiceBody) {
        return (InvoiceHead) manager.query("invoice_head.query", invoiceBody);
    }

    @Override
    public List<InvoiceHead> queryList(@RequestBody InvoiceHead invoiceBody) {
        return (List<InvoiceHead>) manager.list("invoice_head.queryList", invoiceBody);
    }

    @Override
    public Pages<List<InvoiceHead>> pages(@RequestBody InvoiceHead t, Integer pageSize, Integer pageNum) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        page.setOrderBy("id desc");
        Pages<List<InvoiceHead>> ps = (Pages<List<InvoiceHead>>) manager.pages("invoice_head.page", t, page);
        t.setDcState(1);
        Pages p = (Pages) manager.query("invoice_head.sumje", t);
        if (ps != null && p != null) {
            ps.setString1(p.getString1());
            ps.setString2(p.getString2());
            ps.setString3(p.getString3());
        }

        return ps;

    }

    @Override
    public Pages<List<InvoiceHead>> pagesByPojo(@RequestBody InvoiceHeadPoJo t, Integer pageSize, Integer pageNum) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        page.setOrderBy("id desc");
        Pages<List<InvoiceHead>> ps = (Pages<List<InvoiceHead>>) manager.pages("invoice_head.page", t, page);
        t.setDcState(1);
        Pages p = (Pages) manager.query("invoice_head.sumje", t);
        if (ps != null && p != null) {
            ps.setString1(p.getString1());
            ps.setString2(p.getString2());
            ps.setString3(p.getString3());
        }
        return ps;

    }

    @Transactional
    @Override
    public Integer changeDelStateById(@RequestBody InvoiceHead t) {
        return manager.delete("invoice_head.changeDelStateById", t);
    }


    @Override
    public Integer changeDelStateByIds(@RequestBody InvoiceHeadPoJo t) {
        return manager.delete("invoice_head.changeDelStateByIds", t);
    }


    @Override
    public List<InvoiceHead> queryListByPoJo(@RequestBody InvoiceHeadPoJo e) {
        log.info("queryListByPoJo参数{}", JSONObject.toJSONString(e));

        return (List<InvoiceHead>) manager.list("invoice_head.queryList", e);
    }


    @Override
    public List<IndexEntity> indexMsg(String orgId, String kprqStart, String kprqEnd, String mx) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", orgId);
        map.put("kprqStart", kprqStart);
        map.put("kprqEnd", kprqEnd);
        map.put("mx", mx);
        IndexQueryEntity iqe = new IndexQueryEntity();
        iqe.setOrgId(orgId);
        iqe.setKprqStart(kprqStart);
        iqe.setKprqEnd(kprqEnd);
        iqe.setMx(mx);
        //return manager.getSqlSessionTemplate().selectList("invoice_head.indexMsg", map);
        return (List<IndexEntity>) manager.list("invoice_head.indexMsg", iqe);
    }

    /**
     * 通过发票代码、发票号码查询是否已存在  若已存在则直接从数据库中取出
     *
     * @param invoiceCode
     * @param invoiceNumber
     * @return
     */
    private InvoiceMsg getInvoiceFromDB(String invoiceCode, String invoiceNumber) {
        InvoiceHeadPoJo h = new InvoiceHeadPoJo();
        h.setFpdm(invoiceCode);
        h.setFphm(invoiceNumber);
        List<InvoiceHead> hlist = (List<InvoiceHead>) manager.list("invoice_head.queryList", h);
        InvoiceMsg msg = null;
        if (hlist != null && hlist.size() == 1) {
            msg = changeToMsg(hlist.get(0));
            msg.setBodys(new ArrayList<InvoiceBody>());
            /**
             * 查询明细
             */
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("headId", hlist.get(0).getId());
            List<InvoiceBody> blist = (List<InvoiceBody>) manager.list("invoice_body.queryList", map);
            if (blist != null && blist.size() > 0) {
                int i = 0;
                for (InvoiceBody b : blist) {
                    if (i == 0) {
                        msg.setKpnr(b.getHwmc());
                    }
                    msg.getBodys().add(b);
                }

            }
            msg.setCode(5);
            String name = msg.getOpenId();
            try {
                String token = "" + redis.get("miniAccessToken");
                String re_json = HttpGetUtil.get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + token + "&userid=" + msg.getOpenId());
                if (StringUtils.isNotEmpty(re_json)) {
                    JSONObject jo = JSONObject.parseObject(re_json);
                    name = jo.getString("name");

                }
            } catch (Exception e) {
                e.printStackTrace();
                log.error("获取用户信息失败====================");
                log.info("获取用户信息失败====================");
            }

            msg.setMessage("发票已存在,已被" + name + "收录");
            return msg;
        }
        return null;
    }

    private InvoiceMsg changeToMsg(InvoiceHead head) {
        Class clazz = head.getClass();
        InvoiceMsg msg = new InvoiceMsg();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(key, InvoiceMsg.class);
                Method method = descriptor.getWriteMethod();
                field.setAccessible(true);
                method.invoke(msg, field.get(head));
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return msg;
    }

    private InvoiceHead changeToHead(InvoiceMsg msg) {
        Class clazz = InvoiceHead.class;
        InvoiceHead head = new InvoiceHead();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(key, InvoiceHead.class);
                Method method = descriptor.getWriteMethod();
                method.invoke(head, field.get(msg));
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return head;
    }

    private AllInvoiceInfo changeToALL(BaseInvoice head) {
        Class clazz = head.getClass();
        AllInvoiceInfo msg = new AllInvoiceInfo();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(key, AllInvoiceInfo.class);
                Method method = descriptor.getWriteMethod();
                field.setAccessible(true);
                method.invoke(msg, field.get(head));
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return msg;
    }

    @Override
    public AllInvoiceInfo fpcy(InvoiceInspectionParamBody pb) {
        CallResult<BaseInvoice> cr = checkInvoiceClient.check(pb);
        AllInvoiceInfo aii = null;
        if (cr.isSuccess()) {
            BaseInvoice bi = cr.getData();
            //InspectReturnMappingEnum.of(invoiceType);
//             return CallHelper.get(configuration, "api/check/invoice", paramBody, InspectReturnMappingEnum.of(invoiceType).getClazz());
            if (bi != null) {
                //   根据发票类型对应不同的实体返回结果进行赋值

//			SpecialInvoiceInfo 01 增值税专用发票
//			TransportInvoiceInfo 02 货运运输业增值税专用发票
//			VehicleInvoiceInfo 03 机动车销售统一发票
//			NormalInvoiceInfo 04 增值税普通发票
//			ENormalInvoiceInfo 10 增值税电子普通发票
//			RollInvoiceInfo 11 增值税普通发票（卷式）
//			TollInvoiceInfo 14 增值税电子普通发票（通行费）
//			UsedCarInvoiceInfo 15 二手车销售统一发票
                //AllInvoiceInfo aii = changeToALL(bi);
                aii = POJOConvertUtil.getConvertMapper().map(bi, AllInvoiceInfo.class);
                log.info("查询出：" + JSONObject.toJSONString(aii));
                //System.out.println("查询出："+JSONObject.toJSONString(aii));
//    		if(bi instanceof NormalInvoiceInfo ) {
//    			
//    			
//    		}else if(bi instanceof NormalInvoiceInfo ) {
//    			
//    		}else if(bi instanceof ENormalInvoiceInfo ) {
//    			
//    		}else if(bi instanceof RollInvoiceInfo ) {
//    			
//    		}else if(bi instanceof SpecialInvoiceInfo ) {
//    			
//    		}else if(bi instanceof TollInvoiceInfo ) {
//    			
//    		}else if(bi instanceof TransportInvoiceInfo ) {
//    			
//    		}else if(bi instanceof UsedCarInvoiceInfo ) {
//    			
//    		}else if(bi instanceof VehicleInvoiceInfo ) {
//    			
//    		}   
            }
        } else {
            System.out.println(cr.getExceptionResult().getMessage());
            aii = new AllInvoiceInfo();
            if (cr != null && cr.getExceptionResult() != null) {
                log.info(cr.getExceptionResult().getMessage());
                aii.setMessage(cr.getExceptionResult().getMessage());
                System.out.println(aii.getMessage());
            } else {
                aii.setMessage("查验异常，原因未知");
            }

//    		aii =  new AllInvoiceInfo();
//    		aii.setMessage("未查询到相关发票信息");
        }
        return aii;
    }


    @Transactional
    @Override
    public InvoiceMsg checkInvoice(String openId, String invoiceCode, String invoiceNumber, String billingDate,
                                   String totalAmount, String checkCode, String fply, String pdfUrl, String kind, String fplx) {

        if (billingDate.length() == 8) {
            billingDate = billingDate.substring(0, 4) + "-" + billingDate.substring(4, 6) + "-" + billingDate.substring(6, 8);
        }
        InvoiceMsg msg = getInvoiceFromDB(invoiceCode, invoiceNumber);
        if (msg != null) {
            return msg;
        }

        InvoiceInspectionParamBody pb = new InvoiceInspectionParamBody();
        pb.setInvoiceCode(invoiceCode);
        pb.setInvoiceNumber(invoiceNumber);
        pb.setBillingDate(billingDate);
        if (StringUtils.isNotEmpty(totalAmount)) {
            pb.setTotalAmount(totalAmount);
        }
        if (StringUtils.isNotEmpty(checkCode)) {
            pb.setCheckCode(checkCode);
        }
        log.info("查验发票请求参数：{}", JSONObject.toJSONString(pb));

        AllInvoiceInfo aii = fpcy(pb);
        log.info("查验发票返回结果：{}", JSONObject.toJSONString(aii));

        InvoiceMsg im = new InvoiceMsg();
        im.setPdfUrl(pdfUrl);
        im.setFplx(fplx);
        /**
         * 发票代码
         */
        im.setFpdm(invoiceCode);

        /**
         * 发票号码
         */
        im.setFphm(invoiceNumber);

        /**
         * 开票日期
         */
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            im.setKprq(sdf1.parse(billingDate));
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("开票日期转换异常");
        }
        if (aii != null && StringUtils.isNotEmpty(aii.getInvoiceCode())) {//查验成功
            log.info("发票查验成功,发票代码:{}", aii.getInvoiceCode());
            try {
                ValidateRecord vr = new ValidateRecord();
                vr.setCreateMan(openId);
                vr.setCreateTime(new Date());
                vr.setFpdm(invoiceCode);
                vr.setFphm(invoiceNumber);
                vr.setReason("查验成功");
                vr.setValidateResult(JSONObject.toJSONString(aii));
                vrs.save(vr);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("保存发票查验记录异常，原因：{}", e.getMessage());
            }

//				SpecialInvoiceInfo 01 增值税专用发票
//				TransportInvoiceInfo 02 货运运输业增值税专用发票
//				VehicleInvoiceInfo 03 机动车销售统一发票
//				NormalInvoiceInfo 04 增值税普通发票
//				ENormalInvoiceInfo 10 增值税电子普通发票
//				RollInvoiceInfo 11 增值税普通发票（卷式）
//				TollInvoiceInfo 14 增值税电子普通发票（通行费）
//				UsedCarInvoiceInfo 15 二手车销售统一发票

            // 10100 增值税专用发票 10200 定额发票 10507 过路费发票
            // 10101 增值税普通发票 10400 机打发票 10900 可报销其他发票
            // 10102 增值税电子普通发票 10500 出租车发票 00000 其他
            // 10103 增值税普通发票(卷票) 10503 火车票 20100 国际小票
            // 10104 机动车销售统一发票 10505 客运汽车
            // 10105 二手车销售统一发票 10506 航空运输电子客票行程单
            // 10106 货运运输业增值税专用发票
            //10107 增值税电子普通发票（通行费）
            String invoiceType = InvoiceTypeUtil.recoginizeFplx(invoiceCode);
            if ("01".equals(invoiceType)) {
                im.setFplx("10100");
            } else if ("02".equals(invoiceType)) {
                im.setFplx("10106");
            } else if ("03".equals(invoiceType)) {
                im.setFplx("10104");
            } else if ("04".equals(invoiceType)) {
                im.setFplx("10101");
            } else if ("10".equals(invoiceType)) {
                im.setFplx("10102");
            } else if ("11".equals(invoiceType)) {
                im.setFplx("10103");
            } else if ("14".equals(invoiceType)) {
                im.setFplx("10107");
            } else if ("15".equals(invoiceType)) {
                im.setFplx("10105");
            }


            //发票种类  0:蓝票  1：红票
            if (StringUtils.isNotEmpty(aii.getTotalTax()) && aii.getTotalTax().contains("-")) {
                im.setFpzl("1");
            } else {
                im.setFpzl("0");
            }

            /**
             * 发票代码
             */
            im.setFpdm(aii.getInvoiceCode());

            /**
             * 发票号码
             */
            im.setFphm(aii.getInvoiceNumber());

            /**
             * 开票日期
             */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                im.setKprq(sdf.parse(billingDate));
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("开票日期转换异常");
            }


            /**
             * 校验码
             */
            im.setJym(aii.getCheckCode());

            /**
             * 机器编号
             */
            im.setJqbh(aii.getMachineCode());

            /**
             * 受票方名称
             */
            im.setSpfmc(aii.getPurchaserName());
            if (aii.getPurchaserName() != null && !aii.getPurchaserName().equals("浙江省天正设计工程有限公司")
                    && !aii.getPurchaserName().equals("浙江天正设备成套工程有限公司")) {
                log.info("======不合法票据");
                im = new InvoiceMsg();
                im.setCode(3);
                im.setSuccess("false");
                im.setMessage("非本企业发票不能上传！");
                return im;
            }
            /**
             * 受票方识别号
             */
            im.setSpfsbh(aii.getPurchaserTaxNo());
            if (aii.getPurchaserTaxNo() != null && !aii.getPurchaserTaxNo().equals("913300007420055726")
                    && !aii.getPurchaserTaxNo().equals("9133000005011441X7")) {
                log.info("======不合法票据");
                im = new InvoiceMsg();
                im.setCode(3);
                im.setSuccess("false");
                im.setMessage("非本企业发票不能上传！");
                return im;
            }

            /**
             * 受票方地址及电话
             */
            im.setSpfdzdh(aii.getPurchaserAddressPhone());

            /**
             * 受票方银行及账号
             */
            im.setSpfyhzh(aii.getPurchaserBank());

            /**
             * 开票方名称
             */
            im.setKpfmc(aii.getSalesName());

            /**
             * 开票方识别号
             */
            im.setKpfsbh(aii.getSalesTaxNo());

            /**
             * 开票方地址及电话
             */
            im.setKpfdzdh(aii.getSalesAddressPhone());

            /**
             * 开票方银行及账号
             */
            im.setKpfyhzh(aii.getSalesBank());

            /**
             * 合计金额
             */
            im.setHjje(aii.getTotalAmount());

            /**
             * 合计税额
             */
            im.setHjse(aii.getTotalTax().toString());

            /**
             * 价税合计
             */
            im.setKpje(aii.getAmountTax().toString());

            /**
             * 备注
             */
            im.setMark(aii.getRemarks());

            /**
             * 密文
             */
            //private String secret;

            /**
             * 作废标志 0：正常 1：作废
             */
            if ("0".equals(aii.getState())) {
                im.setZfbz("0");
            } else if ("3".equals(aii.getState())) {
                im.setZfbz("3");
            } else if ("2".equals(aii.getState())) {
                im.setZfbz("1");
                //作废进疑票
                InvoiceDoubt invoiceDoubt = new InvoiceDoubt();
                invoiceDoubt.setCreateMan(openId);
                invoiceDoubt.setCreateTime(new Date());
                invoiceDoubt.setFpdm(invoiceCode);
                invoiceDoubt.setFphm(invoiceNumber);
                invoiceDoubt.setKpje(totalAmount);
                invoiceDoubt.setKprq(billingDate);
                invoiceDoubt.setJym(checkCode);
                invoiceDoubt.setReason("发票已作废");
                invoiceDoubt.setState("0");
                manager.insert("invoice_doubt.create", invoiceDoubt);
            }

            /**
             * 清单标志 0：不存在清单 1：存在清单
             */
            if (aii.getItems() != null && aii.getItems().size() > 8) {
                im.setQdbz("1");
            } else {
                im.setQdbz("0");
            }

            /**
             * 查验次数
             */
            //private Integer cycs;

            /**
             * 组织id
             */
            im.setOrgId("1");
            /**
             * 组织名称
             */
            //private String orgName;

            /**
             * 打印状态 默认为0:未打印 1：已打印
             */
            im.setDyState("0");

            /**
             * 导出状态 默认为0 :未导出  1：已导出
             */
            im.setOutState("0");
            /**
             * 采集时间
             */
            im.setCreateTime(new Date());

            /**
             * 复检结果 0:正常 1：异常
             */
            im.setFjState("0");

            /**
             * 进项类型
             */
            //private String jxType;

            /**
             * 认证日期
             */

            //private Date rzrq;

            /**
             * 认证状态 0：未认证 1：已认证
             */
            im.setRzState("1");

            /**
             * 抵扣日期
             */
            //private Date dcrq;

            /**
             * 抵扣状态 1：不抵扣2：已抵扣
             */
            //private Integer dcState;

            /**
             * 单据类型 1 ：正常2 ：红冲
             */
            //private String djlx;

            /**
             * 收票状态 10：未收票20：已收票
             * 若为pc查验 若为扫码查验则状态为已收票 若为手动开票则状态为10
             * 若为app查验 则状态为 已收票
             */
            if (fply.equals("0")) {
                im.setSpState("10");
            } else if (fply.equals("1")) {
                im.setSpState("20");
            } else if (fply.equals("2")) {
                im.setSpState("20");
            } else if (fply.equals("3")) {
                im.setSpState("20");
            } else {
                im.setSpState("10");
            }


            /**
             * 创建人
             */
            im.setCreateMan(openId);
            /**
             * 最后修改时间
             */
            im.setUpdateTime(new Date());

            /**
             * 最后修改人
             */
            im.setUpdateMan(openId);

            /**
             * 采购业务员
             */
            im.setProcMan(openId);

            /**
             * 凭证号
             */
            //private String voucherNo;

            /**
             * 发票来源  0 认证下载、1 pc扫码查验、2 、pc手动查验 3 app查验
             */
            if (fply.equals("1") || fply.equals("2")) {
                im.setFply("1");
            } else if (fply.equals("3")) {
                im.setFply("2");
            } else {
                im.setFply(fply);
            }
            /**
             *  删除标记 0：未删除  1：已删除
             */
            im.setDelState("0");

            /**
             * 0:进项  1：销项
             */
            im.setInOrOut("0");

            /**
             * 核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0
             */
            im.setBusinessType("0");
            /**
             * 保存发票主信息
             */
            im.setOpenId(openId);


            /**
             * 添加查验记录
             */
//			ValidateRecord validateRecord = new ValidateRecord();
//			validateRecord.setCreateMan("张三");
//			validateRecord.setCreateTime(new Date());
//			validateRecord.setFpdm(im.getFpdm());
//			validateRecord.setFphm(im.getFphm());
//			validateRecord.setFpzl(im.getFpzl());
//			if("0".equals(aii.getState())) {
//				 validateRecord.setValidateResult("发票正常");
//				 im.setFjContent("发票正常");
//			 }else {
//				 validateRecord.setValidateResult("发票已作废或红冲");
//				 im.setFjContent("发票已作废或红冲");
//				 }
//			manager.insert("validate_record.create", validateRecord);
            int i = 0;
            //发票明细转换
            List<InvoiceBody> bodies = new ArrayList<InvoiceBody>();
            if (aii != null && aii.getItems().size() > 0) {
                for (BaseItem detail : aii.getItems()) {
                    InvoiceBody body = new InvoiceBody();

                    /**
                     * 行号
                     */
                    body.setHh(Integer.parseInt(detail.getRowNo()));

                    /**
                     * 货物或应税劳务名称
                     */
                    String hwmc = detail.getCommodityName();
                    body.setHwmc(hwmc);


                    if (i == 0) {
//						im.setSpecialType(getSpecialType(im.getFplx()));
//						im.setTaxAttr(getTaxAttr(im.getFplx()));

                        if (hwmc != null) {
                            log.info("开票名称：" + hwmc);
                            try {
                                String bmn = ((RateCode) redis.hget("ratecode", hwmc.substring(1, hwmc.indexOf("*", 1)))).getBusinessMatterName();
                                String dl = ((RateCode) redis.hget("ratecode", hwmc.substring(1, hwmc.indexOf("*", 1)))).getDl();
                                String icon = ((RateCode) redis.hget("ratecode", hwmc.substring(1, hwmc.indexOf("*", 1)))).getIcon();
                                if (StringUtils.isNotEmpty(bmn) && !"N/A".equals(bmn)) {
                                    im.setYwsx(dl);
                                    im.setMxsx(bmn);
                                    im.setIcon(icon);
                                } else {
//								im.setYwsx("其他");
//								im.setMxsx("其他");
//								im.setIcon("qita");
                                    log.error("未匹配到业务事项：" + hwmc);
                                }
                            } catch (Exception e) {
                                log.error("未匹配到业务事项：" + hwmc);
                                e.printStackTrace();
//								im.setYwsx("其他");
//								im.setMxsx("其他");
//								im.setIcon("qita");
                            }
                            if (hwmc.contains("通行费")) {
                                im.setYwsx("差旅类");
                                im.setMxsx("过路过桥费");
                                im.setIcon("chalv");
                            }
                            if ("10507".equals(fplx)) {
                                im.setYwsx("差旅类");
                                im.setMxsx("过路过桥费");
                                im.setIcon("chalv");
                            }
                        }
                        manager.insert("invoice_head.create", im);
                        im.setKpnr(body.getHwmc());
                        i = 1;
                    }
                    /**
                     * 主表MID
                     */
                    body.setHeadId(im.getId());
                    /**
                     * 规格型号
                     */
                    body.setGgxh(detail.getSpecificationModel());

                    /**
                     * 单位
                     */
                    body.setDw(detail.getUnit());

                    /**
                     * 数量
                     */
                    body.setSl(detail.getQuantity());

                    /**
                     * 单价
                     */
                    if (detail.getUnitPrice() != null) {
                        body.setDj(detail.getUnitPrice().toString());
                    }


                    /**
                     * 金额
                     */
                    body.setJe(detail.getAmount());

                    /**
                     * 税率
                     */
                    body.setSlv(detail.getTaxRate());

                    /**
                     * 税额
                     */
                    body.setSe(detail.getTax());

                    /**
                     * 开票项目编码
                     */
                    //private String xmbm;

                    /**
                     * 开票项目名称
                     */
                    //private String xmmc;
                    bodies.add(body);
                    /**
                     * 保存发票明细
                     */
                    manager.insert("invoice_body.create", body);
                }
            } else {


            }
            im.setBodys(bodies);
            //匹配规则
            ruleMatch(im);
            manager.update("invoice_head.update", im);
            im.setCode(1);
            im.setMessage(aii.getMessage());
            im.setSuccess("true");
        } else {
            //进疑票管理
            InvoiceDoubt invoiceDoubt = new InvoiceDoubt();
            invoiceDoubt.setCreateMan(openId);
            invoiceDoubt.setCreateTime(new Date());
            invoiceDoubt.setFpdm(invoiceCode);
            invoiceDoubt.setFphm(invoiceNumber);
            invoiceDoubt.setKpje(totalAmount);
            invoiceDoubt.setKprq(billingDate);
            invoiceDoubt.setJym(checkCode);
            invoiceDoubt.setReason(aii.getMessage());
            invoiceDoubt.setState("0");
            manager.insert("invoice_doubt.create", invoiceDoubt);

            try {
                ValidateRecord vr = new ValidateRecord();
                vr.setCreateMan(openId);
                vr.setCreateTime(new Date());
                vr.setFpdm(invoiceCode);
                vr.setFphm(invoiceNumber);
//	    		vr.setReason("接口查询异常");
                vr.setReason(aii.getMessage());
//	    		vr.setValidateResult("接口查询异常");
                vr.setValidateResult(aii.getMessage());
                vrs.save(vr);
            } catch (Exception e) {
                e.printStackTrace();
            }
            im.setCode(2);
            log.info(aii.getMessage());
            im.setKpje(totalAmount);
            im.setJym(checkCode);
            im.setPdfUrl(pdfUrl);
            im.setMessage(JSONObject.toJSONString(invoiceDoubt));
            im.setSuccess("false");
//			JSONObject json = new JSONObject();
//        	json.put("success", false);
//        	json.put("msg", aii.getMessage());
//        	json.put("code", "99");
//        	json.put("data", JSONObject.toJSONString(invoiceDoubt));
//        	throw new BusinessException(json.toJSONString());
        }

        return im;
    }


    @Transactional
    //@Override
    public InvoiceMsg checkInvoice_old(String openId, String invoiceCode, String invoiceNumber, String billingDate,
                                       String totalAmount, String checkCode, String fply, String pdfUrl, String kind, String fplx) {

        if (billingDate.length() == 8) {
            billingDate = billingDate.substring(0, 4) + "-" + billingDate.substring(4, 6) + "-" + billingDate.substring(6, 8);
        }
        InvoiceMsg msg = getInvoiceFromDB(invoiceCode, invoiceNumber);
        if (msg != null) {
            return msg;
        }

        System.out.println("testredis>>>>>>>>>>>>>>>" + redis.get("accessToken"));
        ;

        String result = Fpcy.testFpcy(invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, redis.get("accessToken").toString(), redis.get("openId").toString());
        JSONObject re = JSONObject.parseObject(result);
        JSONObject bo = JSONObject.parseObject(re.get("BODY").toString());
        Integer code = bo.getInteger("Code");
        InvoiceHeadBW invoiceHeadBW = new InvoiceHeadBW();
        InvoiceMsg im = new InvoiceMsg();
        im.setPdfUrl(pdfUrl);
        im.setFplx(fplx);
        /**
         * 发票代码
         */
        im.setFpdm(invoiceCode);

        /**
         * 发票号码
         */
        im.setFphm(invoiceNumber);

        /**
         * 开票日期
         */
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            im.setKprq(sdf1.parse(billingDate));
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("开票日期转换异常");
        }
        if (code != null && code.intValue() == 0) {//查验成功
            invoiceHeadBW = JSONObject.parseObject(bo.get("Data").toString(), InvoiceHeadBW.class);
            //实体转换
            //发票类型  0：增值税专用发票1：增值税普通发票2：电票3：海关进口增值税专用缴款书4：农产品收购发票5：代扣代缴税收缴款凭证6：机动车销售统一发票7：货运运输业增值税专用发票8：其他
            if (StringUtils.isEmpty(fplx)) {
                if ("01".equals(bo.getString("InvoiceType"))) {
                    im.setFplx("10100");
                } else if ("02".equals(bo.getString("InvoiceType"))) {
                    im.setFplx("7");
                } else if ("03".equals(bo.getString("InvoiceType"))) {
                    im.setFplx("6");
                } else if ("04".equals(bo.getString("InvoiceType"))) {
                    im.setFplx("10101");
                } else if ("10".equals(bo.getString("InvoiceType"))) {
                    im.setFplx("10102");
                }
            }
            //发票种类  0:蓝票  1：红票
            if (invoiceHeadBW.getTotalTax() < 0) {
                im.setFpzl("1");
            } else {
                im.setFpzl("0");
            }

            /**
             * 发票代码
             */
            im.setFpdm(invoiceHeadBW.getInvoiceCode());

            /**
             * 发票号码
             */
            im.setFphm(invoiceHeadBW.getInvoiceNumber());

            /**
             * 开票日期
             */
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                im.setKprq(sdf.parse(billingDate));
            } catch (ParseException e) {
                e.printStackTrace();
                log.error("开票日期转换异常");
            }


            /**
             * 校验码
             */
            im.setJym(invoiceHeadBW.getCheckCode());

            /**
             * 机器编号
             */
            im.setJqbh(invoiceHeadBW.getMachineCode());

            /**
             * 受票方名称
             */
            im.setSpfmc(invoiceHeadBW.getPurchaserName());

            /**
             * 受票方识别号
             */
            im.setSpfsbh(invoiceHeadBW.getPurchaserTaxNo());

            /**
             * 受票方地址及电话
             */
            im.setSpfdzdh(invoiceHeadBW.getPurchaserAddressPhone());

            /**
             * 受票方银行及账号
             */
            im.setSpfyhzh(invoiceHeadBW.getPurchaserBank());

            /**
             * 开票方名称
             */
            im.setKpfmc(invoiceHeadBW.getSalesName());

            /**
             * 开票方识别号
             */
            im.setKpfsbh(invoiceHeadBW.getSalesTaxNo());

            /**
             * 开票方地址及电话
             */
            im.setKpfdzdh(invoiceHeadBW.getSalesAddressPhone());

            /**
             * 开票方银行及账号
             */
            im.setKpfyhzh(invoiceHeadBW.getSalesBank());

            /**
             * 合计金额
             */
            im.setHjje(invoiceHeadBW.getTotalAmount());

            /**
             * 合计税额
             */
            im.setHjse(invoiceHeadBW.getTotalTax().toString());

            /**
             * 价税合计
             */
            im.setKpje(invoiceHeadBW.getAmountTax().toString());

            /**
             * 备注
             */
            im.setMark(invoiceHeadBW.getRemarks());

            /**
             * 密文
             */
            //private String secret;

            /**
             * 作废标志 0：正常 1：作废
             */
            if ("1".equals(invoiceHeadBW.getState())) {
                im.setZfbz("0");
            } else {
                im.setZfbz("1");
                //作废进疑票
                InvoiceDoubt invoiceDoubt = new InvoiceDoubt();
                invoiceDoubt.setCreateMan(openId);
                invoiceDoubt.setCreateTime(new Date());
                invoiceDoubt.setFpdm(invoiceCode);
                invoiceDoubt.setFphm(invoiceNumber);
                invoiceDoubt.setKpje(totalAmount);
                invoiceDoubt.setKprq(billingDate);
                invoiceDoubt.setJym(checkCode);
                invoiceDoubt.setReason("发票已作废");
                invoiceDoubt.setState("0");
                manager.insert("invoice_doubt.create", invoiceDoubt);
            }

            /**
             * 清单标志 0：不存在清单 1：存在清单
             */
            if (invoiceHeadBW.getInvoiceList() != null && invoiceHeadBW.getInvoiceList().size() > 8) {
                im.setQdbz("1");
            } else {
                im.setQdbz("0");
            }

            /**
             * 查验次数
             */
            //private Integer cycs;

            /**
             * 组织id
             */
            im.setOrgId("1");
            /**
             * 组织名称
             */
            //private String orgName;

            /**
             * 打印状态 默认为0:未打印 1：已打印
             */
            im.setDyState("0");

            /**
             * 导出状态 默认为0 :未导出  1：已导出
             */
            im.setOutState("0");
            /**
             * 采集时间
             */
            im.setCreateTime(new Date());

            /**
             * 复检结果 0:正常 1：异常
             */
            im.setFjState("0");

            /**
             * 进项类型
             */
            //private String jxType;

            /**
             * 认证日期
             */

            //private Date rzrq;

            /**
             * 认证状态 0：未认证 1：已认证
             */
            im.setRzState("1");

            /**
             * 抵扣日期
             */
            //private Date dcrq;

            /**
             * 抵扣状态 1：不抵扣2：已抵扣
             */
            //private Integer dcState;

            /**
             * 单据类型 1 ：正常2 ：红冲
             */
            //private String djlx;

            /**
             * 收票状态 10：未收票20：已收票
             * 若为pc查验 若为扫码查验则状态为已收票 若为手动开票则状态为10
             * 若为app查验 则状态为 已收票
             */
            if (fply.equals("0")) {
                im.setSpState("10");
            } else if (fply.equals("1")) {
                im.setSpState("20");
            } else if (fply.equals("2")) {
                im.setSpState("20");
            } else if (fply.equals("3")) {
                im.setSpState("20");
            } else {
                im.setSpState("10");
            }


            /**
             * 创建人
             */
            im.setCreateMan(openId);
            /**
             * 最后修改时间
             */
            im.setUpdateTime(new Date());

            /**
             * 最后修改人
             */
            im.setUpdateMan(openId);

            /**
             * 采购业务员
             */
            im.setProcMan(openId);

            /**
             * 凭证号
             */
            //private String voucherNo;

            /**
             * 发票来源  0 认证下载、1 pc扫码查验、2 、pc手动查验 3 app查验
             */
            if (fply.equals("1") || fply.equals("2")) {
                im.setFply("1");
            } else if (fply.equals("3")) {
                im.setFply("2");
            } else {
                im.setFply(fply);
            }
            /**
             *  删除标记 0：未删除  1：已删除
             */
            im.setDelState("0");

            /**
             * 0:进项  1：销项
             */
            im.setInOrOut("0");

            /**
             * 核算要素 ，跟进销绑定 进（0：成本  1：费用） 销（0：主营收入  1：其他收入）  默认为0
             */
            im.setBusinessType("0");
            /**
             * 保存发票主信息
             */
            im.setOpenId(openId);


            /**
             * 添加查验记录
             */
            ValidateRecord validateRecord = new ValidateRecord();
            validateRecord.setCreateMan(openId);
            validateRecord.setCreateTime(new Date());
            validateRecord.setFpdm(im.getFpdm());
            validateRecord.setFphm(im.getFphm());
            validateRecord.setFpzl(im.getFpzl());
            if ("1".equals(invoiceHeadBW.getState())) {
                validateRecord.setValidateResult("发票正常");
                im.setFjContent("发票正常");
            } else {
                validateRecord.setValidateResult("发票已作废");
                im.setFjContent("发票已作废");
            }
            manager.insert("validate_record.create", validateRecord);
            int i = 0;
            //发票明细转换
            List<InvoiceBody> bodies = new ArrayList<InvoiceBody>();
            if (invoiceHeadBW.getInvoiceList() != null && invoiceHeadBW.getInvoiceList().size() > 0) {
                for (InvoiceDetail detail : invoiceHeadBW.getInvoiceList()) {
                    InvoiceBody body = new InvoiceBody();


                    /**
                     * 行号
                     */
                    body.setHh(Integer.parseInt(detail.getRowNo()));

                    /**
                     * 货物或应税劳务名称
                     */
                    String hwmc = detail.getCommodityName();
                    body.setHwmc(hwmc);

//					else {
//						im.setYwsx("其他");
//						im.setMxsx("其他");
//					}

                    //根据kind设置业务事项 及明细事项
//					if("餐饮".equals(kind)) {
//						im.setYwsx("差旅");
//						im.setMxsx("餐补");
//					}else if("交通".equals(kind)) {
//						im.setYwsx("差旅");
//						im.setMxsx("交通");
//					}else if("服务".equals(kind)) {
//						im.setYwsx("服务");
//						im.setMxsx("服务");
//					}else if("用车".equals(kind)) {
//						im.setYwsx("采购");
//						im.setMxsx("用车");
//					}else {
//						im.setYwsx("其他");
//						im.setMxsx("其他");
//					}
//					
//					
//					if(hwmc!=null&&hwmc.contains("餐饮服务")) {
//						im.setYwsx("差旅");
//						im.setMxsx("餐补");
//					}else if(hwmc!=null&&hwmc.contains("住宿服务")) {
//						im.setYwsx("差旅");
//						im.setMxsx("住宿");
//					}


                    if (i == 0) {
//						im.setSpecialType(getSpecialType(im.getFplx()));
//						im.setTaxAttr(getTaxAttr(im.getFplx()));

                        if (hwmc != null) {
                            String bmn = ((RateCode) redis.hget("ratecode", hwmc)).getBusinessMatterName();
                            String dl = ((RateCode) redis.hget("ratecode", hwmc)).getDl();
                            String icon = ((RateCode) redis.hget("ratecode", hwmc)).getIcon();
                            if (StringUtils.isNotEmpty(bmn) && !"N/A".equals(bmn)) {
                                im.setYwsx(dl);
                                im.setMxsx(bmn);
                                im.setIcon(icon);
                            } else {
                                log.info("{}未匹配出税码信息》》》》", hwmc);

//								im.setYwsx("其他");
//								im.setMxsx("其他");
//								im.setIcon("qita");
                            }


                        }
                        manager.insert("invoice_head.create", im);
                        im.setKpnr(body.getHwmc());
                        i = 1;
                    }
                    /**
                     * 主表MID
                     */
                    body.setHeadId(im.getId());
                    /**
                     * 规格型号
                     */
                    body.setGgxh(detail.getSpecificationModel());

                    /**
                     * 单位
                     */
                    body.setDw(detail.getUnit());

                    /**
                     * 数量
                     */
                    body.setSl(detail.getQuantity());

                    /**
                     * 单价
                     */
                    if (detail.getUnitPrice() != null) {
                        body.setDj(detail.getUnitPrice().toString());
                    }


                    /**
                     * 金额
                     */
                    body.setJe(detail.getAmount());

                    /**
                     * 税率
                     */
                    body.setSlv(detail.getTaxRate());

                    /**
                     * 税额
                     */
                    body.setSe(detail.getTax());

                    /**
                     * 开票项目编码
                     */
                    //private String xmbm;

                    /**
                     * 开票项目名称
                     */
                    //private String xmmc;
                    bodies.add(body);
                    /**
                     * 保存发票明细
                     */
                    manager.insert("invoice_body.create", body);
                }
            } else {


            }
            im.setBodys(bodies);
            //匹配规则
            ruleMatch(im);
            manager.update("invoice_head.update", im);
        } else {
            //进疑票管理
            InvoiceDoubt invoiceDoubt = new InvoiceDoubt();
            invoiceDoubt.setCreateMan(openId);
            invoiceDoubt.setCreateTime(new Date());
            invoiceDoubt.setFpdm(invoiceCode);
            invoiceDoubt.setFphm(invoiceNumber);
            invoiceDoubt.setKpje(totalAmount);
            invoiceDoubt.setKprq(billingDate);
            invoiceDoubt.setJym(checkCode);
            invoiceDoubt.setReason("接口查询异常");
            invoiceDoubt.setState("0");
            manager.insert("invoice_doubt.create", invoiceDoubt);
        }
        im.setCode(code);
        im.setMessage(bo.getString("Message"));


        return im;
    }


    @Override
    public Pages<List<PersonalTicket>> pageForMobile(String openId, Integer pageSize, Integer pageNum, String bxState) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        InvoiceHead ih = new InvoiceHead();
        ih.setOpenId(openId);
        ih.setBxState(bxState);
        Pages<List<PersonalTicket>> ps = (Pages<List<PersonalTicket>>) manager.pages("invoice_head.pageForMobile", ih, page);

        return ps;
    }


    @Override
    public String fileUpload(@RequestBody HeadList heads) {

        if (heads.getHeads() != null && heads.getHeads().size() > 0) {
            for (InvoiceHeadPoJo head : heads.getHeads()) {
                /*
                 * 先查验发票是否已存在  已存在则不导入根据三个条件  发票代码 发票号码  进销项标记
                 */
                List<InvoiceHeadPoJo> hs = (List<InvoiceHeadPoJo>) manager.list("invoice_head.queryList", head);
                if (hs != null && hs.size() > 0) {
                    //已存在
                } else {
                    //不存在
                    manager.insert("invoice_head.create", head);
                    //保存明细
                    if (head.getBodys() != null && head.getBodys().size() > 0) {
                        for (InvoiceBody body : head.getBodys()) {
                            body.setHeadId(head.getId());
                            manager.insert("invoice_body.create", body);
                        }
                    }
                }
            }
        }
        return "success";
    }


    @Override
    public Integer queryByTicketId(String ticketId) {
        InvoiceHead ih = new InvoiceHead();
        ih.setTicketId(ticketId);
        return (Integer) manager.query("invoice_head.queryByTicketId", ih);
    }


    private String getSpecialType(String invoiceType) {
        String specialType = "";
//		10100	增值税专用发票	10200	定额发票	10507	过路费发票
//		10101	增值税普通发票	10400	机打发票	10900	可报销其他发票
//		10102	增值税电子普通发票	10500	出租车发票	00000	其他
//		10103	增值税普通发票(卷票)	10503	火车票	20100	国际小票
//		10104	机动车销售统一发票	10505	客运汽车	 	 
//		10105	二手车销售统一发票	10506	航空运输电子客票行程单
        if ("10100".equals(invoiceType) || "10101".equals(invoiceType)
                || "10102".equals(invoiceType) || "10103".equals(invoiceType)
                || "10200".equals(invoiceType) || "10400".equals(invoiceType)
                || "10104".equals(invoiceType) || "10105".equals(invoiceType)
        ) {
            specialType = "1";
        } else if ("10503".equals(invoiceType) || "10505".equals(invoiceType)
                || "10507".equals(invoiceType) || "10500".equals(invoiceType)
        ) {
            specialType = "2";
        } else {
            specialType = "1";
        }
        return specialType;
    }

    private String getTaxAttr(String invoiceType) {
        String taxAttr = "";
//		10100	增值税专用发票	10200	定额发票	10507	过路费发票
//		10101	增值税普通发票	10400	机打发票	10900	可报销其他发票
//		10102	增值税电子普通发票	10500	出租车发票	00000	其他
//		10103	增值税普通发票(卷票)	10503	火车票	20100	国际小票
//		10104	机动车销售统一发票	10505	客运汽车	 	 
//		10105	二手车销售统一发票	10506	航空运输电子客票行程单
        if ("10100".equals(invoiceType)) {
            taxAttr = "1";
        } else if ("10503".equals(invoiceType) || "10506".equals(invoiceType)
                || "10505".equals(invoiceType) || "10507".equals(invoiceType)

        ) {
            taxAttr = "2";
        } else {
            taxAttr = "3";
        }
        return taxAttr;
    }


    /**
     * 处理规则匹配库
     */
    private InvoiceHead ruleMatch(InvoiceHead invoiceHead) {

        List<InvoiceBody> bodys = invoiceHead.getBodys();

        //规则类型：1：税务类型   2：发票大类  3：发票业务类型

        //税务类型  根据发票类型及规则类型=1 当前时间 （使用启用时间） 取出符合规则的匹配规则
        //根据该规则赋值规则名称  计算税额     是否可抵扣
        //

        RuleMatch ruleMatch = new RuleMatch();
        //ruleMatch.setRuleType("1");
        ruleMatch.setStartDate(new Date());
        //ruleMatch.setInvoiceType(invoiceHead.getFplx());

        List<RuleMatch> rms = (List<RuleMatch>) manager.list("rule_match.queryByCondition", ruleMatch);
        if (rms != null && rms.size() > 0) {
            Boolean fh = false;//是否符合该规则
            for (RuleMatch rm : rms) {
                List<RuleMatchSub> subs = rm.getSubs();
                if (subs != null && subs.size() > 0) {
                    for (RuleMatchSub sub : subs) {
                        //首先匹配发票类型
                        if (invoiceHead.getFplx().equals(sub.getInvoiceType().toString()) && "1".equals(sub.getIsEnable())) {
                            //是否需要通过明细字段过滤
                            if ("1".equals(sub.getIsDetailJudue())) {
                                //需要
                                //判断当前发票明细中是否包含该明细过滤关键字 并判断是否是选取、全选
                                if (bodys != null && bodys.size() > 0) {
                                    for (int i = 0; i < bodys.size(); i++) {
                                        if (bodys.get(i).getHwmc() != null && bodys.get(i).getHwmc().contains(sub.getDetailField())
                                                && !"1".equals(sub.getExclusionOrSelection())) {
                                            //说明该发票符合当前匹配规则
                                            //计算可抵扣金额  是否可抵扣
                                            cal(rm, sub, invoiceHead);
                                            break;
                                        } else if (bodys.get(i).getHwmc() != null && !bodys.get(i).getHwmc().contains(sub.getDetailField())
                                                && "1".equals(sub.getExclusionOrSelection()) && i == (bodys.size() - 1)) {
                                            //排除  如果是最后一行 并且是不满足的(不包含该明细字段)       则说明该发票符合当前匹配规则
                                            cal(rm, sub, invoiceHead);
                                            break;
                                        }
                                    }
                                }

                            } else {
                                //不需要通过明细字段匹配
                                //判断是否是选取、全选
                                if (!"1".equals(sub.getExclusionOrSelection())) {
                                    //是选取/全选
                                    //说明该发票符合当前匹配规则
                                    //计算可抵扣金额  是否可抵扣
                                    cal(rm, sub, invoiceHead);
                                    break;
                                }
                            }
                        }
                    }
                }


            }
        }

        return invoiceHead;
    }

    /**
     * 计算可抵扣金额  是否可抵扣
     *
     * @param sub
     * @param invoiceHead
     * @return
     */
    private InvoiceHead cal(RuleMatch rm, RuleMatchSub sub, InvoiceHead invoiceHead) {

        log.info("》》》》》》》》》》》》》匹配成功！名称：" + rm.getName() + ",RuleId:" + sub.getRuleId());


        if ("1".equals(rm.getRuleType())) {
            //税务类型
            log.info(">>>>>>>>>税务类型！");
            if ("1".equals(rm.getCanDeduct())) {
                //可抵扣
                //判断是取票面税额还是需通过维护的税率计算得出可抵扣金额
                log.info("》》》》》》》》》》》可抵扣！");
                if ("1".equals(sub.getTaxCalMethod())) {
                    //取票面税额
                    invoiceHead.setDkje(new BigDecimal(invoiceHead.getHjse()));
                    log.info(">>>>>>>>>>>>>取票面税额   抵扣金额 ：" + invoiceHead.getDkje());
                } else {
                    //通过税率计算得出
                    BigDecimal hjse = BigDecimal.ZERO;
                    hjse = (new BigDecimal(invoiceHead.getKpje())).multiply(new BigDecimal(sub.getTaxRate())).divide(BigDecimal.TEN.multiply(BigDecimal.TEN).add(new BigDecimal(sub.getTaxRate())), 2, BigDecimal.ROUND_HALF_UP);
                    invoiceHead.setDkje(hjse);
                    invoiceHead.setTaxRate(sub.getTaxRate().toString());
                    log.info(">>>>>>>>>>通过税率计算得出  抵扣金额 ：" + invoiceHead.getDkje());
                }
                invoiceHead.setDcState(1);
            } else {
                //不可抵扣
                invoiceHead.setDkje(BigDecimal.ZERO);
                invoiceHead.setDcState(2);
                log.info(">>>>>>>>>>不可抵扣");
            }
            //设置税务类型编码
            invoiceHead.setTaxAttr(rm.getCode());
            log.info(">>>>>>>>>税务类型编码" + invoiceHead.getTaxAttr());
            invoiceHead.setDkwsje((
                            new BigDecimal(invoiceHead.getKpje() == null ? "0" : invoiceHead.getKpje())
                    )
                            .subtract(invoiceHead.getDkje())
            );
        } else if ("2".equals(rm.getRuleType())) {
            //设置发票大类
            log.info(">>>>>>>>>发票大类：" + rm.getCode());
            invoiceHead.setSpecialType(rm.getCode());
        } else if ("3".equals(rm.getRuleType())) {
            //设置发票业务类型
            log.info(">>>>>>>>>发票业务类型：" + rm.getCode());
            invoiceHead.setServiceType(rm.getCode());
        }


        return invoiceHead;
    }


    @Override
    public List<IncomeCount> jxtj(@RequestBody IncomeCount incomeCount) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>fplx:" + incomeCount.getFplx());
        return (List<IncomeCount>) manager.list("invoice_head.jxtj", incomeCount);
    }


    @Override
    public Integer delInvoice(String openId, String invoiceId) {
        InvoiceHead head = new InvoiceHead();
        head.setId(Long.parseLong(invoiceId));

        return manager.delete("invoice_head.changeDelStateById", head);
    }

    @Transactional
    @Override
    public boolean synInvoiceMsg(@RequestBody ListTZInvoice invoice) {
        for (TzsysEasInvoicereceive invoicereceive : invoice.getInvoices()) {
            log.info("接收到同步信息{}", JSONObject.toJSONString(invoicereceive));
            InvoiceHead head = new InvoiceHead();
            head.setId(invoicereceive.getInvoiceId());
            if ("2".equals(invoicereceive.getBxState()) || "3".equals(invoicereceive.getBxState())) {
                head.setBxState("1");
            } else if ("4".equals(invoicereceive.getBxState())) {
                head.setZfState("1");
                head.setBxState("1");
            }
            if ("2".equals(invoicereceive.getLock())) {
                head.setBxState("1");
            } else if ("1".equals(invoicereceive.getLock())) {
                head.setBxState("0");
            }
            if ("2".equals(invoicereceive.getRzState())) {
                head.setJzState("1");
                InvoiceHead ih = (InvoiceHead) manager.query("invoice_head.query", head);
                if (ih != null && "1".equals(ih.getRzState())) {
                    head.setRzState("2");
                }
            }
            head.setReimburseNo(invoicereceive.getDocNo());
            log.info("财务认证日期：" + invoicereceive.getBxTime());
            //2020-05-18 13:20:04.207
            try {
                head.setRzrq(invoicereceive.getBxTime());
            } catch (Exception e) {
                log.error("财务认证日期：{}转换失败", invoicereceive.getBxTime());
            }


            manager.update("invoice_head.update", head);
        }
        return true;
    }


    @Override
    public Object invoiceReview(String ids) {
        /**
         * 查看日志是否存在 若存在 则提示：复查正在进行，请勿重复进行复查
         */
        JSONArray ja = redis.zGet("fclog", 0, 20);
        if (ja != null && ja.size() > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "复查正在进行，请勿重复进行复查");
            jsonObject.put("success", false);
            return jsonObject;
        }

        List<InvoiceReview> rs = (List<InvoiceReview>) manager.list("invoice_head.fpfc", ids);

        if (rs != null && rs.size() > 0) {
            rs.stream().filter(r -> StringUtils.isNotEmpty(r.getFpdm())).filter(r -> StringUtils.isNotEmpty(r.getFphm()))
                    .collect(Collectors.toList());
            InvoiceInspectionParamBody pb = null;
            AllInvoiceInfo aii = null;
            int total = rs.size();//总共需要查验数量
            int now = 0;//当前查验数量
            int error = 0;//异常数量
            String msg = null;
            for (InvoiceReview r : rs) {
                now++;
                redis.zSetAndTime("fclog", 60 * 10, new Date().getTime(), "正在查验发票代码:" + r.getFpdm() + ",发票号码:" + r.getFphm());
                try {
                    pb = new InvoiceInspectionParamBody();
                    pb.setInvoiceCode(r.getFpdm());
                    pb.setInvoiceNumber(r.getFphm());
                    pb.setBillingDate(r.getKprq());
                    if (StringUtils.isNotEmpty(r.getHjje())) {
                        pb.setTotalAmount(r.getHjje());
                    }
                    if (StringUtils.isNotEmpty(r.getJym())) {
                        pb.setCheckCode(r.getJym());
                    }
                    aii = fpcy(pb);
                    if (aii != null) {
                        try {
                            ValidateRecord vr = new ValidateRecord();
                            vr.setCreateMan(r.getCreateMan());
                            vr.setCreateTime(new Date());
                            vr.setFpdm(r.getFpdm());
                            vr.setFphm(r.getFphm());
                            vr.setReason("");
                            vr.setValidateResult(JSONObject.toJSONString(aii));
                            vrs.save(vr);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        msg = "发票代码:" + r.getFpdm() + ",发票号码:" + r.getFphm() + "查验成功，信息：" + JSONObject.toJSONString(aii);
                        redis.zSetAndTime("fclog", 60 * 60 * 24 * 7, new Date().getTime(), reMsg(total, now, error, msg));
                        if ("2".equals(aii.getState())) {
                            error++;
                            msg = "发票代码:" + r.getFpdm() + ",发票号码:" + r.getFphm() + "已作废，信息：" + JSONObject.toJSONString(aii);
                            redis.zSetAndTime("fclog", 60 * 10, new Date().getTime(), reMsg(total, now, error, msg));
                            //作废进疑票
                            InvoiceDoubt invoiceDoubt = new InvoiceDoubt();
                            invoiceDoubt.setCreateMan(r.getCreateMan());
                            invoiceDoubt.setCreateTime(new Date());
                            invoiceDoubt.setFpdm(r.getFpdm());
                            invoiceDoubt.setFphm(r.getFphm());
                            invoiceDoubt.setKpje(r.getKprq());
                            invoiceDoubt.setKprq(r.getHjje());
                            invoiceDoubt.setJym(r.getJym());
                            invoiceDoubt.setReason("发票已作废");
                            invoiceDoubt.setState("0");
                            manager.insert("invoice_doubt.create", invoiceDoubt);
                            /**
                             * 修改发票作废状态为已作废
                             */
                            InvoiceHead head = new InvoiceHead();
                            head.setId(r.getId());
                            head.setZfbz("1");
                            manager.update("invoice_head.update", head);
                        }
                    } else {
                        //未查询到发票信息
                        ValidateRecord vr = new ValidateRecord();
                        vr.setCreateMan(r.getCreateMan());
                        vr.setCreateTime(new Date());
                        vr.setFpdm(r.getFpdm());
                        vr.setFphm(r.getFphm());
                        vr.setReason("未查询到相关发票信息");
                        vr.setValidateResult("未查询到相关发票信息");

                        vrs.save(vr);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("发票查验异常，原因：{}", e.getMessage());
                    msg = "发票代码:" + r.getFpdm() + ",发票号码:" + r.getFphm() + "查验异常，原因：" + e.getMessage();
                    redis.zSetAndTime("fclog", 60 * 10, new Date().getTime(), reMsg(total, now, error, msg));
                }
            }
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "提交查验成功");
        jsonObject.put("success", true);
        return jsonObject;
    }

    private String reMsg(int total, int now, int error, String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", total);
        jsonObject.put("now", now);
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();

    }

    /**
     * 勾选
     */
    public JSONObject gx(@RequestBody com.zhys.po.DeductParamBody body) {
        //DeductParamBody body = new DeductParamBody();
        body.setDeductType("1");

        DeductParamBody dpb = new DeductParamBody();
        dpb.setDeductType("1");
        dpb.setPeriod(body.getPeriod());
        dpb.setTaxNo(body.getTaxNo());
        //dpb.setCustomId("");
        List<DeductInvoice> invoices = new ArrayList<DeductInvoice>();
        for (DeductParamSub sub : body.getInvoices()) {
            DeductInvoice inv = new DeductInvoice();
            inv.setInvoiceCode(sub.getInvoiceCode());
            inv.setInvoiceNumber(sub.getInvoiceNumber());
            inv.setValidTax(sub.getValidTax());
            invoices.add(inv);
            /**
             * 修改发票认证状态为认证中 2.5
             */
            log.info("修改认证状态为认证中============");
            InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
            pojo.setFpdm(sub.getInvoiceCode());
            pojo.setFphm(sub.getInvoiceNumber());
            pojo.setRzState("2.5");
            pojo.setKjdkq(body.getPeriod());
            manager.update("invoice_head.updateRzStateByNo", pojo);
            log.info("修改认证状态为认证中完成============");
        }
        dpb.setInvoices(invoices);
        log.info("=====================请求勾选参数{}", JSONObject.toJSON(dpb));
        CallResult<ApplyDeductResultInfo> cr = deductClient.deductInvoices(dpb);
        String errMsg = "";
        JSONObject jsonObject = new JSONObject();
        if (cr.isSuccess()) {
            ApplyDeductResultInfo ad = cr.getData();
            String requestId = ad.getRequestId();
            jsonObject.put("success", true);
            jsonObject.put("msg", "勾选成功，等待同步状态....");
            body.setRequestId(requestId);
            body.setState("1");
            log.info("=====================请求勾选返回成功结果{}", JSONObject.toJSON(cr.getData()));

        } else {
            ExceptionResult er = cr.getExceptionResult();
            errMsg = er.getMessage();
            String requestId = er.getRequestId();
            jsonObject.put("success", false);
            jsonObject.put("msg", errMsg);
            body.setRequestId(requestId);
            body.setState("3");
            log.info("=====================请求勾选返回失败结果{}", JSONObject.toJSON(er));
            log.error("=====================请求勾选参数{}", JSONObject.toJSON(dpb));
            log.error("=====================请求勾选返回失败结果{}", JSONObject.toJSON(er));


            for (DeductParamSub sub : body.getInvoices()) {

                /**
                 * 更新发票池发票状态
                 */
                log.info("修改认证状态为未认证============");
                InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
                pojo.setFpdm(sub.getInvoiceCode());
                pojo.setFphm(sub.getInvoiceNumber());
                pojo.setRzState("2");
                pojo.setKjdkq("");
                manager.update("invoice_head.updateRzStateByNo", pojo);
                log.info("修改认证状态为未认证完成============");
            }

            if (StringUtils.isEmpty(requestId)) {
                return jsonObject;
            }
        }

        manager.insert("deduct_param_body.create", body);
        if (body.getInvoices() != null && body.getInvoices().size() > 0) {
            List<DeductParamSub> dis = body.getInvoices();
            for (DeductParamSub ps : dis) {
                ps.setDeductId(body.getId());
                ps.setState("0");
                ps.setExportRejectNo(errMsg);
                manager.insert("deduct_param_sub.create", ps);
            }
        }
        return jsonObject;

    }


    /**
     * 取消勾选
     */
    public JSONObject cancel_gx(@RequestBody com.zhys.po.DeductParamBody body) {
        //DeductParamBody body = new DeductParamBody();
        body.setDeductType("6");

        DeductParamBody dpb = new DeductParamBody();
        dpb.setDeductType("6");
        dpb.setPeriod(body.getPeriod());
        dpb.setTaxNo(body.getTaxNo());
        //dpb.setCustomId("");
        List<DeductInvoice> invoices = new ArrayList<DeductInvoice>();
        for (DeductParamSub sub : body.getInvoices()) {
            DeductInvoice inv = new DeductInvoice();
            inv.setInvoiceCode(sub.getInvoiceCode());
            inv.setInvoiceNumber(sub.getInvoiceNumber());
            inv.setValidTax(sub.getValidTax());
            invoices.add(inv);
            /**
             * 修改发票认证状态为撤销认证中
             */
            log.info("修改发票认证状态为撤销认证中============");
            InvoiceHeadPoJo pojo = new InvoiceHeadPoJo();
            pojo.setFpdm(sub.getInvoiceCode());
            pojo.setFphm(sub.getInvoiceNumber());
            pojo.setRzState("3.5");
            pojo.setKjdkq(body.getPeriod());
            manager.update("invoice_head.updateRzStateByNo", pojo);
            log.info("修改认证状态为撤销认证中完成============");
        }
        dpb.setInvoices(invoices);
        log.info("=====================请求勾选参数{}", JSONObject.toJSON(dpb));
        CallResult<ApplyDeductResultInfo> cr = deductClient.deductInvoices(dpb);

        JSONObject jsonObject = new JSONObject();
        if (cr.isSuccess()) {
            ApplyDeductResultInfo ad = cr.getData();
            String requestId = ad.getRequestId();
            jsonObject.put("success", true);
            jsonObject.put("msg", "撤销勾选成功，等待同步状态....");
            body.setRequestId(requestId);
            body.setState("4");
            log.info("=====================请求撤销勾选返回成功结果{}", JSONObject.toJSON(cr.getData()));

        } else {
            ExceptionResult er = cr.getExceptionResult();
            String errMsg = er.getMessage();
            String requestId = er.getRequestId();
            jsonObject.put("success", false);
            jsonObject.put("msg", errMsg);
            body.setRequestId(requestId);
            body.setState("6");
            log.info("=====================请求撤销勾选返回失败结果{}", JSONObject.toJSON(er));
            log.error("=====================请求撤销勾选参数{}", JSONObject.toJSON(dpb));
            log.error("=====================请求撤销勾选返回失败结果{}", JSONObject.toJSON(er));
            if (StringUtils.isEmpty(requestId)) {
                return jsonObject;
            }
        }

        manager.insert("deduct_param_body.create", body);
        if (body.getInvoices() != null && body.getInvoices().size() > 0) {
            List<DeductParamSub> dis = body.getInvoices();
            for (DeductParamSub ps : dis) {
                ps.setDeductId(body.getId());
                ps.setState("2");
                manager.insert("deduct_param_sub.create", ps);
            }
        }
        return jsonObject;

    }


    @Override
    public Integer updateRzState(@RequestBody InvoiceHeadPoJo ih) {
        manager.update("invoice_head.updateRzState", ih);

        return 1;
    }


    @Override
    public Integer updateRzStateAll(@RequestBody InvoiceHeadPoJo ih) {
        manager.update("invoice_head.updateRzStateAll", ih);
        return 1;
    }


    @Override
    public List<Dkhz> getDkhz(@RequestParam("nf") String nf) {
        List<Dkhz> hkhzs = new ArrayList<Dkhz>();
        for (int i = 1; i <= 12; i++) {
            Dkhz dkhz = new Dkhz();
            if (i < 10) {
                dkhz.setYf(nf + "-0" + i);
            } else {
                dkhz.setYf(nf + "-" + i);
            }

            dkhz = (Dkhz) manager.query("invoice_head.dkhz", dkhz);
            dkhz.setDqdkse(dkhz.getRzse().add(dkhz.getSbse()).subtract(dkhz.getZcse()));
            hkhzs.add(dkhz);
        }

        return hkhzs;
    }


    @Override
    public Pages<List<com.zhys.po.DeductRecord>> DeductPages(@RequestBody com.zhys.po.DeductRecord body, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
        EasyPage page = new EasyPage();
        page.pageNum(pageNum);
        page.pageSize(pageSize);
        Pages<List<com.zhys.po.DeductRecord>> ps = (Pages<List<com.zhys.po.DeductRecord>>) manager.pages("deduct_param_sub.page", body, page);
        return ps;
    }

    @Override
    public void synRzrq(@RequestBody ListTZInvoice invoice) {
        for (TzsysEasInvoicereceive invoicereceive : invoice.getInvoices()) {
            manager.update("invoice_head.updateRzrq", invoicereceive);
        }
    }


}