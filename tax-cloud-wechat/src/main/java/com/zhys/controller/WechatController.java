package com.zhys.controller;

import com.zhys.user.po.SysOrgs;
import com.zhys.user.po.SysUsers;
import com.zhys.util.MarkUtils;
import org.springframework.stereotype.*;
import com.zhys.fegin.*;
import com.zhys.redis.*;
import org.springframework.beans.factory.annotation.*;
import org.slf4j.*;

import javax.servlet.http.*;

import com.alibaba.fastjson.*;
import com.zhys.webservice.*;
import com.zhys.exception.*;
import com.zhys.result.*;
import com.zhys.utils.*;
import com.zhys.common.*;
import com.lycheeframework.core.cmp.kit.*;
import org.springframework.web.multipart.*;

import java.io.*;

import org.joda.time.*;
import com.zhys.hh.*;

import java.text.*;

import com.invoice.po.*;
import com.invoice.pojo.*;

import java.beans.*;
import java.lang.reflect.*;
import java.math.BigDecimal;

import com.zhys.rq.*;
import org.apache.commons.lang3.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping({"wechatController"})
@CrossOrigin
public class WechatController {
    private static Logger log;
    @Autowired
    private InvoiceHeadServiceFegin service;
    @Autowired
    private HeadServiceFegin headServiceFegin;
    @Autowired
    private SysUsersServiceFegin sysUsersServiceFegin;
    @Autowired
    private SysOrgsServiceFegin SysOrgsServiceFegin;
    @Autowired
    private TzsysEasInvoicereceiveServiceFegin easInvoicereceiveServiceFegin;
    @Autowired
    private RedisUtils redis;
    @Autowired
    private AsyncBean asyncBean;
    @Value("${appId}")
    private String appId;
    @Value("${secret}")
    private String secret;
    @Value("${pdfUrl}")
    private String pdfUrl;

    static {
        log = LoggerFactory.getLogger((Class) WechatController.class);
    }

    public String createDateDir(String basePath) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = sdf.format(new Date());
        String[] dayArr = dayStr.split("-");

        String year = dayArr[0];
        String month = dayArr[1];
        String day = dayArr[2];

        String yearDir = basePath + year;
        File yearFile = new File(yearDir);
        if (!yearFile.exists()) {
            yearFile.mkdirs();
        }

        String monthDir = yearDir + "/" + month;
        File monthFile = new File(monthDir);
        if (!monthFile.exists()) {
            monthFile.mkdirs();
        }

        String dayDir = monthDir + "/" + day;
        File dayFile = new File(dayDir);
        if (!dayFile.exists()) {
            dayFile.mkdirs();
        }
        System.out.println(dayDir);
        return dayDir;

    }

    @RequestMapping("/login")
    @ResponseBody
    public Map loginController(@RequestParam("account") String account, @RequestParam("password") String password) {
        Map<String, String> map = new HashMap<>();
        //根据账号密码查询用户表
        SysUsers user = new SysUsers();
        SysUsers loginUser = new SysUsers();
        loginUser.setNo(account);
        loginUser.setPassword(password);
        user.setNo(account);
        user.setPassword(password);
        SysUsers sysUsers = this.sysUsersServiceFegin.queryByNo(user);
        //如果数据库中有此用户，则返回ok，否则返回err
//        if (null != sysUsers) {
//            System.out.println(sysUsers.getState() + ":state");
//            if (sysUsers.getState().equals("0")) {
//                map.put("msg", "此账号已被禁用");
//                return map;
//            } else {
//                if (null != sysUsers.getPassword() && MD5Util.MD5(loginUser.getPassword()).equals(sysUsers.getPassword())) {
//                    map.put("message", "ok");
//                } else {
//                    map.put("message", "密码错误");
//                }
//            }
//        } else {
//            map.put("message", "未找到此用户");
//        }
//        return map;
        if (null == sysUsers) {
            map.put("message", "未找到此用户");
            return map;
        }
        if (("0").equals(sysUsers.getState())) {
            map.put("msg", "此账号已被禁用");
            return map;
        }
        if (null != sysUsers.getPassword() && MD5Util.MD5(loginUser.getPassword()).equals(sysUsers.getPassword())) {
            map.put("message", "ok");
        } else {
            map.put("message", "密码错误");
        }
        return map;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map registerController(@RequestParam("account") String account,
                                  @RequestParam("password") String password,
                                  @RequestParam("companyName") String companyName,
                                  @RequestParam("companyId") String companyId) {
        Map<String, String> map = new HashMap<>();
        //1.查验此账号是否重复
        SysUsers user = new SysUsers();
        user.setNo(account);
        SysUsers sysUsers = this.sysUsersServiceFegin.queryByNo(user);
        if (sysUsers != null) {
            map.put("msg", "此账号已被注册");
            return map;
        }
        //2.查验此公司名称和税号是否存在
        SysOrgs orgs = new SysOrgs();
        orgs.setOrgName(companyName);
        orgs.setOrgNo(companyId);
        List<SysOrgs> sysOrgs = this.SysOrgsServiceFegin.queryList(orgs);
        int k = 0;
        for (int i = 0; i < sysOrgs.size(); i++) {
            System.out.println(sysOrgs.get(i).getOrgName() + "123");
            System.out.println(sysOrgs.get(i).getOrgNo());
            if (sysOrgs.get(i).getOrgName().equals(companyName) && sysOrgs.get(i).getOrgNo().equalsIgnoreCase(companyId)) {
                //此公司名称和税号存在
                user.setOrgid(sysOrgs.get(i).getId().toString());
                k = k + 1;
            }
        }
        //如果新增成功，则返回ok
        if (k == 0) {
            map.put("msg", "此公司名称和税号不存在");
        } else {
            //3.将数据新增到数据库中(账号密码新增到用户表中)
            user.setPassword(password);
            this.sysUsersServiceFegin.save(user);
            map.put("msg", "ok");
        }
        return map;
    }

    @GetMapping({"/getConf/{type}"})
    private String getConf(@PathVariable("type") String type, HttpServletRequest req, HttpServletResponse response) {
        String timestamp = (String) this.redis.get("timestamp" + type);
        String nonceStr = (String) this.redis.get("nonceStr" + type);
        String signature = (String) this.redis.get("signature" + type);
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print("wx.config({debug:false,appId:'" + this.appId + "',timestamp:" + timestamp + ",nonceStr:'" + nonceStr + "',signature:'" + signature + "',jsApiList:['checkJsApi','chooseImage','previewImage','uploadImage','downloadImage','scanQRCode']});");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ResponseResult
    @PostMapping(value = {"/checkInvoice"}, produces = {"application/json"})
    @ResponseBody
    InvoiceMsg checkInvoice(@RequestParam(name = "openId", required = false) String openId, @RequestParam("invoiceCode") String invoiceCode, @RequestParam("invoiceNumber") String invoiceNumber, @RequestParam("billingDate") String billingDate, @RequestParam("totalAmount") String totalAmount, @RequestParam("checkCode") String checkCode, @RequestParam("fply") String fply, @RequestParam("pdfUrl") String pdfUrl) {
        InvoiceMsg msg = this.service.checkInvoice(openId, invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, fply, pdfUrl, "", "");
        List<InvoiceMsg> msgs = new ArrayList<InvoiceMsg>();
        msgs.add(msg);
        WechatController.log.info("同步到OA参数:" + JSONObject.toJSONString(msg));
        Long l21 = new Date().getTime();
        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l21);
//        this.asyncBean.asynToFin((List) msgs);
        Long l22 = new Date().getTime();
        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l22);
        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l22 - l21));
        return msg;
    }

    @GetMapping({"/setOpenid"})
    public String setOpenid(String code, HttpServletResponse response) {
        String openid = "";
        System.out.println("code:---" + code);
        WechatController.log.info("code:---" + code);
        String re = HttpGetUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + this.appId + "&secret=" + this.secret + "&code=" + code + "&grant_type=authorization_code");
        JSONObject js = JSONObject.parseObject(re);
        openid = js.getString("openid");
        CookieUtils.writeCookie(response, "openId", openid);
        return "redirect:http://www.ft-link.cn/views/myInvoices/myInvoices.html";
    }

    @GetMapping({"/setOpenidForBand"})
    public String setOpenidForBand(String code, HttpServletResponse response) {
        String openid = "";
        System.out.println("code:---" + code);
        String re = HttpGetUtil.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + this.appId + "&secret=" + this.secret + "&code=" + code + "&grant_type=authorization_code");
        JSONObject js = JSONObject.parseObject(re);
        openid = js.getString("openid");
        CookieUtils.writeCookie(response, "openId", openid);
        return "redirect:/views/myInvoices/myInvoices.html";
    }

    @ResponseResult
    @GetMapping(value = {"/pageForMobile/{openId}"}, produces = {"application/json"})
    @ResponseBody
    Pages<List<PersonalTicket>> pageForMobile(@PathVariable("openId") String openId, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("bxState") String bxState) {
        return (Pages<List<PersonalTicket>>) this.service.pageForMobile(openId, pageSize, pageNum, bxState);
    }


    @ResponseResult
    @GetMapping(value = {"/submitStatement/{openId}"}, produces = {"application/json"})
    @ResponseBody
    void submitStatement(@PathVariable("openId") String openId, @RequestParam("cli") String cli, @RequestParam("cs") String cs, @RequestParam("bxState") String bxState) {
        System.out.println(cli);
        System.out.println(cs);
    }

    @ResponseResult
    @GetMapping(value = {"/pageForMobileByStatement/{openId}"}, produces = {"application/json"})
    @ResponseBody
    Pages<List<HeadPojo>> pageForMobileByStatement(@PathVariable("openId") String openId, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum, @RequestParam("bxState") String bxState) {
        return (Pages<List<HeadPojo>>) this.headServiceFegin.pageForMobile(openId, pageSize, pageNum, bxState);
    }

    @ResponseResult
    @PostMapping(value = {"/checkInvoiceByPhoto1/{openId}/{type}"}, produces = {"application/json"})
    @ResponseBody
    InvoiceMsg checkInvoiceByPhoto1(@PathVariable("openId") String openId, @PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            String key = String.valueOf(new DateTime().getMillis()) + ".png";
            this.asyncBean.upload(baos.toByteArray(), key);
            if (type.equals("0")) {
                JSONObject jsonObject = OCRTest.fp(baos.toByteArray());
                String billingDate = jsonObject.getString("vat_invoice_issue_date").replace("年", "").replace("月", "").replace("日", "");
                String invoiceCode = jsonObject.getString("vat_invoice_daima");
                String invoiceNumber = jsonObject.getString("vat_invoice_haoma");
                String totalAmount = jsonObject.getString("vat_invoice_total").replace("¥", "");
                String checkCode = jsonObject.getString("vat_invoice_correct_code");
                if (checkCode != null && checkCode.length() > 6) {
                    checkCode = checkCode.substring(14);
                }
                WechatController.log.info(String.valueOf(openId) + "," + invoiceCode + "," + invoiceNumber + "," + billingDate + "," + totalAmount + "," + checkCode);
                return this.service.checkInvoice(openId, invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, "2", createDateDir(this.pdfUrl) + "/" + key, "", "");
            }
            if (type.equals("1")) {
                JSONObject jsonObject = OCRTest.pj(baos.toByteArray());
                InvoiceMsg msg = new InvoiceMsg();
                if (jsonObject != null && jsonObject.getString("code").equals("200")) {
                    JSONArray arr = jsonObject.getJSONArray("recognize_data");
                    if (arr != null && arr.size() > 0) {
                        for (int i = 0; i < arr.size(); ++i) {
                            if (arr.getJSONObject(i).getString("type").equals("出发地")) {
                                msg.setStartAddr(arr.getJSONObject(i).getString("data"));
                            } else if (arr.getJSONObject(i).getString("type").equals("目的地")) {
                                msg.setEndAddr(arr.getJSONObject(i).getString("data"));
                            } else if (arr.getJSONObject(i).getString("type").equals("乘车时间")) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                try {
                                    msg.setKprq(sdf.parse(arr.getJSONObject(i).getString("data").substring(0, 10).replace("年", "-").replace("月", "-")));
                                    msg.setRideTime(arr.getJSONObject(i).getString("data"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    new BusinessException("开票日期转换异常");
                                }
                            } else if (arr.getJSONObject(i).getString("type").equals("座位类别")) {
                                msg.setSeatCategory(arr.getJSONObject(i).getString("data"));
                            } else if (arr.getJSONObject(i).getString("type").equals("价格")) {
                                msg.setAmount(arr.getJSONObject(i).getString("data").replace("元", ""));
                                msg.setHjje(arr.getJSONObject(i).getString("data").replace("元", ""));
                            } else if (arr.getJSONObject(i).getString("type").equals("车次号")) {
                                msg.setTrainNum(arr.getJSONObject(i).getString("data"));
                            } else if (arr.getJSONObject(i).getString("type").equals("乘客名称")) {
                                msg.setPassenger(arr.getJSONObject(i).getString("data"));
                            } else if (arr.getJSONObject(i).getString("type").equals("火车票红色编码")) {
                                msg.setTicketId(arr.getJSONObject(i).getString("data"));
                            }
                        }
                    }
                    return msg;
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            new BusinessException("识别异常");
        }
        return null;
    }

    private InvoiceMsg changeToHead(InvoiceHead msg) {
        WechatController.log.info("head.id:" + msg.getId());
        Class clazz = InvoiceHead.class;
        InvoiceMsg head = new InvoiceMsg();
        Field[] fields = clazz.getDeclaredFields();
        Field[] array;
        for (int length = (array = fields).length, i = 0; i < length; ++i) {
            Field field = array[i];
            String key = field.getName();
            try {
                PropertyDescriptor descriptor = new PropertyDescriptor(key, InvoiceHeadPoJo.class);
                Method method = descriptor.getWriteMethod();
                field.setAccessible(true);
                method.invoke(head, field.get(msg));
            } catch (IntrospectionException ex) {
            } catch (IllegalAccessException ex2) {
            } catch (IllegalArgumentException ex3) {
            } catch (InvocationTargetException ex4) {
            }
        }
        return head;
    }

    @ResponseResult
    @PostMapping(value = {"/checkInvoiceByPhoto/{openId}/{type}"}, produces = {"application/json"})
    @ResponseBody
    InvoiceMsg checkInvoiceByPhoto(@PathVariable("openId") String openId, @PathVariable("type") String type, @RequestParam("file") MultipartFile file, InvoiceHead invoiceHead) {
        FileInputStream fis = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            String key = String.valueOf(new DateTime().getMillis()) + ".png";
            this.asyncBean.upload(baos.toByteArray(), key);
            String re = InvoiceRecognizeUtils.recognize(baos.toByteArray());
            System.out.println(re);
            JSONObject jsonre = JSONObject.parseObject(re);
            JSONObject jsonOA = new JSONObject();
            if ("1".equals(jsonre.getString("result"))) {
                JSONObject identify_results = jsonre.getJSONObject("response").getJSONObject("data").getJSONArray("identify_results").getJSONObject(0);
                JSONObject details = identify_results.getJSONObject("details");
                invoiceHead.setFplx(identify_results.getString("type"));
                String kind = details.getString("kind");
                if ("10100".equals(identify_results.getString("type")) || "10101".equals(identify_results.getString("type")) || "10102".equals(identify_results.getString("type")) || "10103".equals(identify_results.getString("type"))) {
                    String billingDate = details.getString("date").replace("年", "").replace("月", "").replace("日", "");
                    String invoiceCode = details.getString("code");
                    String invoiceNumber = details.getString("number");
                    String totalAmount = details.getString("pretax_amount");
                    String checkCode = details.getString("check_code");
                    if (checkCode != null && checkCode.length() > 6) {
                        checkCode = checkCode.substring(14);
                    }
                    String fplx = identify_results.getString("type");
                    WechatController.log.info(String.valueOf(openId) + "," + invoiceCode + "," + invoiceNumber + "," + billingDate + "," + totalAmount + "," + checkCode);
                    Long l5 = new Date().getTime();
                    WechatController.log.info("开始查验真伪>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l5);
                    InvoiceMsg msg = this.service.checkInvoice(openId, invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, "2", createDateDir(this.pdfUrl) + "/" + key, kind, fplx);
                    Long l6 = new Date().getTime();
                    WechatController.log.info("查验真伪完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l6);
                    WechatController.log.info("查验真伪耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l6 - l5));
                    if (msg != null && msg.getCode() != null && msg.getCode() == 1 && msg.getZfbz().equals("0")) {
                        jsonOA.put("invoceNum", (Object) msg.getFphm());
                        jsonOA.put("invoceCode", (Object) msg.getFpdm());
                        jsonOA.put("type", (Object) msg.getFplx());
                        jsonOA.put("salesParty", (Object) msg.getKpfmc());
                        jsonOA.put("buyer", (Object) msg.getSpfmc());
                        jsonOA.put("amountMoney", (Object) msg.getHjje());
                        jsonOA.put("taxRate", (Object) msg.getBodys().get(0).getSlv());
                        jsonOA.put("taxMoney", (Object) msg.getHjse());
                        jsonOA.put("totalWithTax", (Object) msg.getKpje());
                        jsonOA.put("makeDate", (Object) msg.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        jsonOA.put("typeInDate", (Object) sdf.format(now));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg.getId());
                        Long l7 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l7);
                        boolean oa_re = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l8 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l8);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l8 - l7));
                        if (!oa_re) {
                            new BusinessException("同步到OA异常");
                        }
                    }
                    return msg;
                }
                if ("10104".equals(identify_results.getString("type"))) {
                    String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                    String fpdm = details.getString("code");
                    String fphm = details.getString("number");
                    String kpje = details.getString("total");
                    String machineCode = details.getString("machine_code");
                    String machineNumber = details.getString("machine_number");
                    String hjje = details.getString("pretax_amount");
                    String kpfmc = details.getString("seller");
                    String kpfsbh = details.getString("seller_tax_id");
                    String spfmc = details.getString("buyer");
                    String spfsbh = details.getString("buyer_id");
                    String tax_authorities = details.getString("tax_authorities");
                    String tax_authorities_code = details.getString("tax_authorities_code");
                    String car_code = details.getString("car_code");
                    String car_engine_code = details.getString("car_engine_code");
                    String car_model = details.getString("car_model");
                    String certificate_number = details.getString("certificate_number");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    if (!"".equals(kprq)) {
                        invoiceHead.setKprq(sdf2.parse(kprq));
                    }
                    invoiceHead.setFpdm(fpdm);
                    invoiceHead.setFphm(fphm);
                    invoiceHead.setKpje(kpje);
                    invoiceHead.setMachineCode(machineCode);
                    invoiceHead.setMachineNumber(machineNumber);
                    invoiceHead.setHjje(hjje);
                    invoiceHead.setKpfmc(kpfmc);
                    invoiceHead.setKpfsbh(kpfsbh);
                    invoiceHead.setSpfmc(spfmc);
                    invoiceHead.setSpfsbh(spfsbh);
                    invoiceHead.setTaxAuthorities(tax_authorities);
                    invoiceHead.setTaxAuthoritiesCode(tax_authorities_code);
                    invoiceHead.setCarCode(car_code);
                    invoiceHead.setCarEngineCode(car_engine_code);
                    invoiceHead.setCarModel(car_model);
                    invoiceHead.setCertificateNumber(certificate_number);
                    Long l9 = new Date().getTime();
                    WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l9);
                    this.service.save(invoiceHead);
                    Long l10 = new Date().getTime();
                    WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l10);
                    WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l10 - l9));
                    InvoiceMsg msg2 = this.changeToHead(invoiceHead);
                    jsonOA.put("invoceNum", (Object) msg2.getFphm());
                    jsonOA.put("invoceCode", (Object) msg2.getFpdm());
                    jsonOA.put("type", (Object) msg2.getFplx());
                    jsonOA.put("salesParty", (Object) msg2.getKpfmc());
                    jsonOA.put("buyer", (Object) msg2.getSpfmc());
                    jsonOA.put("amountMoney", (Object) msg2.getHjje());
                    jsonOA.put("taxRate", (Object) 0);
                    jsonOA.put("taxMoney", (Object) msg2.getHjse());
                    jsonOA.put("totalWithTax", (Object) msg2.getKpje());
                    jsonOA.put("makeDate", (Object) msg2.getKprq());
                    jsonOA.put("makeUser", (Object) "1");
                    Date now2 = new Date();
                    jsonOA.put("typeInDate", (Object) sdf2.format(now2));
                    jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                    jsonOA.put("status", (Object) "0");
                    jsonOA.put("invoce_sys_id", (Object) msg2.getId());
                    Long l11 = new Date().getTime();
                    WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l11);
                    boolean oa_re2 = TestWebservice.synToOA(jsonOA.toJSONString());
                    Long l12 = new Date().getTime();
                    WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l12);
                    WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l12 - l11));
                    if (!oa_re2) {
                        new BusinessException("同步到OA异常");
                    }
                    return msg2;
                }
                if (!"10105".equals(identify_results.getString("type"))) {
                    if ("10200".equals(identify_results.getString("type"))) {
                        String fpdm2 = details.getString("code");
                        String fphm2 = details.getString("number");
                        String kpje2 = details.getString("total");
                        invoiceHead.setFpdm(fpdm2);
                        invoiceHead.setFphm(fphm2);
                        invoiceHead.setKpje(kpje2);
                        Long l13 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l13);
                        this.service.save(invoiceHead);
                        Long l14 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l14);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l14 - l13));
                        InvoiceMsg msg3 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg3.getFphm());
                        jsonOA.put("invoceCode", (Object) msg3.getFpdm());
                        jsonOA.put("type", (Object) msg3.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg3.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg3.getKpje());
                        jsonOA.put("makeDate", (Object) "");
                        jsonOA.put("makeUser", (Object) "1");
                        Date now3 = new Date();
                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                        jsonOA.put("typeInDate", (Object) sdf3.format(now3));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg3.getId());
                        Long l15 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l15);
                        boolean oa_re3 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l16 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l16);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l16 - l15));
                        if (!oa_re3) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg3;
                    }
                    if ("10400".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String fpdm = details.getString("code");
                        String fphm = details.getString("number");
                        String time = details.getString("time");
                        String jym = details.getString("check_code");
                        String category = details.getString("category");
                        String kpje3 = details.getString("total");
                        String kpfmc = details.getString("seller");
                        String kpfsbh = details.getString("seller_tax_id");
                        String spfmc = details.getString("buyer");
                        String spfsbh = details.getString("buyer_tax_id");
                        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf4.parse(kprq));
                        }
                        invoiceHead.setFpdm(fpdm);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setTime(time);
                        invoiceHead.setJym(jym);
                        invoiceHead.setCategory(category);
                        invoiceHead.setKpje(kpje3);
                        invoiceHead.setKpfmc(kpfmc);
                        invoiceHead.setKpfsbh(kpfsbh);
                        invoiceHead.setSpfmc(spfmc);
                        invoiceHead.setSpfsbh(spfsbh);
                        Long l17 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l17);
                        this.service.save(invoiceHead);
                        Long l18 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l18);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l18 - l17));
                        InvoiceMsg msg4 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg4.getFphm());
                        jsonOA.put("invoceCode", (Object) msg4.getFpdm());
                        jsonOA.put("type", (Object) msg4.getFplx());
                        jsonOA.put("salesParty", (Object) kpfmc);
                        jsonOA.put("buyer", (Object) spfmc);
                        jsonOA.put("amountMoney", (Object) msg4.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg4.getKpje());
                        jsonOA.put("makeDate", (Object) time);
                        jsonOA.put("makeUser", (Object) "1");
                        Date now4 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf4.format(now4));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg4.getId());
                        Long l19 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l19);
                        boolean oa_re4 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l20 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l20);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l20 - l19));
                        if (!oa_re4) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg4;
                    }
                    if ("10500".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String fpdm = details.getString("code");
                        String fphm = details.getString("number");
                        String time_geton = details.getString("time_geton");
                        String time_getoff = details.getString("time_getoff");
                        String mileage = details.getString("mileage");
                        String kpje3 = details.getString("total");
                        String place = details.getString("place");
                        SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf5.parse(kprq));
                        }
                        invoiceHead.setFpdm(fpdm);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setTimeGeton(time_geton);
                        invoiceHead.setTimeGetoff(time_getoff);
                        invoiceHead.setMileage(mileage);
                        invoiceHead.setKpje(kpje3);
                        invoiceHead.setPlace(place);
                        Long l21 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l21);
                        this.service.save(invoiceHead);
                        Long l22 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l22);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l22 - l21));
                        InvoiceMsg msg5 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg5.getFphm());
                        jsonOA.put("invoceCode", (Object) msg5.getFpdm());
                        jsonOA.put("type", (Object) msg5.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg5.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg5.getKpje());
                        jsonOA.put("makeDate", (Object) null);
                        jsonOA.put("makeUser", (Object) "1");
                        Date now5 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf5.format(now5));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg5.getId());
                        Long l23 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l23);
                        boolean oa_re5 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l24 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l24);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l24 - l23));
                        if (!oa_re5) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg5;
                    }
                    if ("10503".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String time2 = details.getString("time");
                        String fphm = details.getString("number");
                        String station_geton = details.getString("station_geton");
                        String station_getoff = details.getString("station_getoff");
                        String train_number = details.getString("train_number");
                        String kpje3 = details.getString("total");
                        String seat = details.getString("seat");
                        String name = details.getString("name");
                        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf6.parse(kprq));
                        }
                        invoiceHead.setTime(time2);
                        invoiceHead.setTimeGeton(time2);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setKpje(kpje3);
                        invoiceHead.setStationGeton(station_geton);
                        invoiceHead.setStationGetoff(station_getoff);
                        invoiceHead.setTrainNumber(train_number);
                        invoiceHead.setSeat(seat);
                        invoiceHead.setName(name);
                        Long l25 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l25);
                        this.service.save(invoiceHead);
                        Long l26 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l26);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l26 - l25));
                        InvoiceMsg msg6 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) (msg6.getFphm() == null));
                        jsonOA.put("invoceCode", (Object) (msg6.getFpdm() == null));
                        jsonOA.put("type", (Object) msg6.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg6.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg6.getKpje());
                        jsonOA.put("makeDate", (Object) msg6.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now6 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf6.format(now6));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg6.getId());
                        Long l27 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l27);
                        boolean oa_re6 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l28 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l28);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l28 - l27));
                        if (!oa_re6) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg6;
                    }
                    if ("10505".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String fpdm = details.getString("code");
                        String fphm = details.getString("number");
                        String station_geton = details.getString("station_geton");
                        String station_getoff = details.getString("station_getoff");
                        String kpje4 = details.getString("total");
                        String time3 = details.getString("time");
                        String name2 = details.getString("name");
                        SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf5.parse(kprq));
                        }
                        invoiceHead.setFpdm(fpdm);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setKpje(kpje4);
                        invoiceHead.setStationGeton(station_geton);
                        invoiceHead.setStationGetoff(station_getoff);
                        invoiceHead.setTime(time3);
                        invoiceHead.setName(name2);
                        Long l21 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l21);
                        this.service.save(invoiceHead);
                        Long l22 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l22);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l22 - l21));
                        InvoiceMsg msg5 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg5.getFphm());
                        jsonOA.put("invoceCode", (Object) msg5.getFpdm());
                        jsonOA.put("type", (Object) msg5.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg5.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg5.getKpje());
                        jsonOA.put("makeDate", (Object) msg5.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now5 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf5.format(now5));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg5.getId());
                        Long l23 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l23);
                        boolean oa_re5 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l24 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l24);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l24 - l23));
                        if (!oa_re5) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg5;
                    }
                    if ("10506".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String fphm2 = details.getString("number");
                        String kpje2 = details.getString("total");
                        String user_name = details.getString("user_name");
                        String user_id = details.getString("user_id");
                        String jym2 = details.getString("check_code");
                        String agentcode = details.getString("agentcode");
                        String issue_by = details.getString("issue_by");
                        String fare = details.getString("fare");
                        String tax = details.getString("tax");
                        String fuel_surcharge = details.getString("fuel_surcharge");
                        String caac_development_fund = details.getString("caac_development_fund");
                        String insurance = details.getString("insurance");
                        String flights = details.getJSONArray("flights").toJSONString();
                        JSONObject flight = details.getJSONArray("flights").getJSONObject(0);
                        String from = "";
                        String to = "";
                        String flight_number = "";
                        String time4 = "";
                        String seat2 = "";
                        if (flight != null) {
                            from = flight.getString("from");
                            to = flight.getString("to");
                            flight_number = flight.getString("flight_number");
                            time4 = flight.getString("time");
                            seat2 = flight.getString("seat");
                        }
                        SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf7.parse(kprq));
                        }
                        invoiceHead.setFphm(fphm2);
                        invoiceHead.setKpje(kpje2);
                        invoiceHead.setUserName(user_name);
                        invoiceHead.setUserId(user_id);
                        invoiceHead.setJym(jym2);
                        invoiceHead.setAgentcode(agentcode);
                        invoiceHead.setIssueBy(issue_by);
                        invoiceHead.setFare(fare);
                        invoiceHead.setTax(tax);
                        invoiceHead.setFuelSurcharge(fuel_surcharge);
                        invoiceHead.setCaacDevelopmentFund(caac_development_fund);
                        invoiceHead.setInsurance(insurance);
                        invoiceHead.setFlights(flights);
                        Long l29 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l29);
                        this.service.save(invoiceHead);
                        Long l30 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l30);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l30 - l29));
                        InvoiceMsg msg7 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg7.getFphm());
                        jsonOA.put("invoceCode", (Object) msg7.getFpdm());
                        jsonOA.put("type", (Object) msg7.getFplx());
                        jsonOA.put("salesParty", (Object) msg7.getIssueBy());
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg7.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg7.getKpje());
                        jsonOA.put("makeDate", (Object) msg7.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now7 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf7.format(now7));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg7.getId());
                        jsonOA.put("timeGeton", (Object) time4);
                        jsonOA.put("timeGetoff", (Object) "");
                        jsonOA.put("name", (Object) user_name);
                        jsonOA.put("userId", (Object) user_id);
                        jsonOA.put("stationGeton", (Object) from);
                        jsonOA.put("stationGetoff", (Object) to);
                        jsonOA.put("trainNumber", (Object) flight_number);
                        jsonOA.put("seat", (Object) seat2);
                        jsonOA.put("isChange", (Object) invoiceHead.getIsChange());
                        Long l31 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l31);
                        boolean oa_re7 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l32 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l32);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l32 - l31));
                        if (!oa_re7) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg7;
                    }
                    if ("10507".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String fpdm = details.getString("code");
                        String fphm = details.getString("number");
                        String entrance = details.getString("entrance");
                        String exit = details.getString("exit");
                        String kpje4 = details.getString("total");
                        String time3 = details.getString("time");
                        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf3.parse(kprq));
                        }
                        invoiceHead.setFpdm(fpdm);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setKpje(kpje4);
                        invoiceHead.setTime(time3);
                        invoiceHead.setEntrance(entrance);
                        invoiceHead.setExist(exit);
                        Long l33 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l33);
                        this.service.save(invoiceHead);
                        Long l34 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l34);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l34 - l33));
                        InvoiceMsg msg8 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg8.getFphm());
                        jsonOA.put("invoceCode", (Object) msg8.getFpdm());
                        jsonOA.put("type", (Object) msg8.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg8.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg8.getKpje());
                        jsonOA.put("makeDate", (Object) msg8.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now8 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf3.format(now8));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg8.getId());
                        Long l35 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l35);
                        boolean oa_re8 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l36 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l36);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l36 - l35));
                        if (!oa_re8) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg8;
                    }
                    if (!"10900".equals(identify_results.getString("type")) && !"00000".equals(identify_results.getString("type")) && "20100".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "-");
                        String store_name = details.getString("store_name");
                        String subtotal = details.getString("subtotal");
                        String tax2 = details.getString("tax");
                        String discount = details.getString("discount");
                        String tips = details.getString("tips");
                        String kpje3 = details.getString("total");
                        String time5 = details.getString("time");
                        String currency_code = details.getString("currency_code");
                        SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            invoiceHead.setKprq(sdf6.parse(kprq));
                        }
                        invoiceHead.setKpje(kpje3);
                        invoiceHead.setTime(time5);
                        invoiceHead.setStoreName(store_name);
                        invoiceHead.setSubtotal(subtotal);
                        invoiceHead.setTax(tax2);
                        invoiceHead.setDiscount(discount);
                        invoiceHead.setTips(tips);
                        invoiceHead.setCurrencyCode(currency_code);
                        Long l25 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l25);
                        this.service.save(invoiceHead);
                        Long l26 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l26);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l26 - l25));
                        InvoiceMsg msg6 = this.changeToHead(invoiceHead);
                        jsonOA.put("invoceNum", (Object) msg6.getFphm());
                        jsonOA.put("invoceCode", (Object) msg6.getFpdm());
                        jsonOA.put("type", (Object) msg6.getFplx());
                        jsonOA.put("salesParty", (Object) "");
                        jsonOA.put("buyer", (Object) "");
                        jsonOA.put("amountMoney", (Object) msg6.getKpje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) 0);
                        jsonOA.put("totalWithTax", (Object) msg6.getKpje());
                        jsonOA.put("makeDate", (Object) msg6.getKprq());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now6 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf6.format(now6));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg6.getId());
                        Long l27 = new Date().getTime();
                        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l27);
                        boolean oa_re6 = TestWebservice.synToOA(jsonOA.toJSONString());
                        Long l28 = new Date().getTime();
                        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l28);
                        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l28 - l27));
                        if (!oa_re6) {
                            new BusinessException("同步到OA异常");
                        }
                        return msg6;
                    }
                }
            } else {
                new BusinessException("识别异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            new BusinessException("识别异常");
        }
        return null;
    }

    @ResponseResult
    @PostMapping(value = {"/checkInvoiceByPhotos/{openId}/{type}"}, produces = {"application/json"})
    @ResponseBody
    List<InvoiceMsg> checkInvoiceByPhotos(@PathVariable(name = "openId", required = true) String openId, @PathVariable("type") String type, @RequestParam("file") MultipartFile file, InvoiceHead invoiceHead) {
        WechatController.log.info("{}正在上传发票>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + openId);
        invoiceHead.setOpenId(openId);
        Long l1 = new Date().getTime();
        WechatController.log.info("是否改变>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + invoiceHead.getIsChange());
        String isChange = "1";
        if (!StringUtils.isEmpty((CharSequence) invoiceHead.getIsChange())) {
            isChange = invoiceHead.getIsChange();
        }
        WechatController.log.info("识别发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l1);
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            Random rand = new Random();
            int sjs = rand.nextInt(10000); //生成0-100以内的随机数
            String key = String.valueOf(new DateTime().getMillis()) + "_" + sjs + ".png";
            Long l2 = new Date().getTime();
            WechatController.log.info("图片上传到服务器开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l2);
            this.asyncBean.upload(baos.toByteArray(), key);
            Long l3 = new Date().getTime();
            WechatController.log.info("图片上传到服务器结束，耗时：>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l3 - l2));
            WechatController.log.info("识别图片开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l3);
            String re = InvoiceRecognizeUtils.recognize(baos.toByteArray());
            Long l4 = new Date().getTime();
            WechatController.log.info("识别图片完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l4);
            WechatController.log.info("识别图片耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l4 - l3));
            WechatController.log.info("识别图片结果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + re);
            WechatController.log.info("识别结果：" + re);
            JSONObject jsonre = JSONObject.parseObject(re);
            JSONObject jsonOAData = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if ("1".equals(jsonre.getString("result"))) {
                List<InvoiceMsg> msgs = new ArrayList<InvoiceMsg>();
                JSONArray ja = jsonre.getJSONObject("response").getJSONObject("data").getJSONArray("identify_results");
                for (int i = 0; i < ja.size(); ++i) {
                    JSONObject jsonOA = new JSONObject();
                    invoiceHead = new InvoiceHead();
                    invoiceHead.setOpenId(openId);
                    WechatController.log.info("1111111");




                    //谭洋波,新增代码
                    log.info("tyb新增代码开始");
                    String okey = key;key=i+key;
                    log.info("执行圈定旋转方法");
                    MultipartFile rotateAndMark = MarkUtils.rotateAndMark(file.getInputStream(),  ja, i,ja.size()>1?true:false);
                    log.info("执行圈定旋转方法结束");
                    if(rotateAndMark != null)  {
                    	log.info("tyb上传开始");
                    	this.asyncBean.upload(rotateAndMark, key);
                    	 log.info("tyb上传结束");
                    } else { key=okey;}
                       
                    log.info("tyb新增代码结束");






                    invoiceHead.setPdfUrl(createDateDir(this.pdfUrl) + "/" + key);
                    JSONObject identify_results = ja.getJSONObject(i);
                    JSONObject details = identify_results.getJSONObject("details");
                    invoiceHead.setFplx(identify_results.getString("type"));
//			                    发票类型
//			                    名称	描述	名称	描述	名称	描述
//                    10100	增值税专用发票	10200	定额发票	10507	过路费发票
//                    10101	增值税普通发票	10400	机打发票	10900	可报销其他发票
//                    10102	增值税电子普通发票	10500	出租车发票
//                    10103	增值税普通发票(卷票)	10503	火车票	20100	国际小票
//                    10104	机动车销售统一发票	10505	客运汽车	20105	滴滴出行行程单
//                    10105	二手车销售统一发票	10506	航空运输电子客票行程单	10902	完税证明
//                    10505a	船票

                    String kind = details.getString("kind");
                    if ("10100".equals(identify_results.getString("type")) || "10101".equals(identify_results.getString("type")) || "10102".equals(identify_results.getString("type")) || "10103".equals(identify_results.getString("type")) || "10107".equals(identify_results.getString("type"))) {
                        String billingDate = details.getString("date").replace("年", "").replace("月", "").replace("日", "");
                        String invoiceCode = details.getString("code");
                        String invoiceNumber = details.getString("number");
                        String totalAmount = details.getString("pretax_amount");
                        String checkCode = details.getString("check_code");
                        if (checkCode != null && checkCode.length() > 6) {
                            checkCode = checkCode.substring(checkCode.length() - 6, checkCode.length());
                        }
                        String fplx = identify_results.getString("type");
                        WechatController.log.info(String.valueOf(openId) + "," + invoiceCode + "," + invoiceNumber + "," + billingDate + "," + totalAmount + "," + checkCode);
                        Long l5 = new Date().getTime();
                        WechatController.log.info("开始查验真伪>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l5);
                        InvoiceMsg msg = this.service.checkInvoice(openId, invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, "2", createDateDir(this.pdfUrl) + "/" + key, kind, fplx);
                        Long l6 = new Date().getTime();
                        WechatController.log.info("查验真伪完成>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l6);
                        WechatController.log.info("查验真伪耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l6 - l5));
                        if (msg != null && msg.getCode() != null && msg.getCode() == 1 && msg.getZfbz() != null && msg.getZfbz().equals("0")) {
                            WechatController.log.info("查验真伪结果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + JSONObject.toJSONString((Object) msg));
                            jsonOA.put("invoceNum", (Object) msg.getFphm());
                            jsonOA.put("invoceCode", (Object) msg.getFpdm());
                            jsonOA.put("type", (Object) msg.getFplx());
                            jsonOA.put("salesParty", (Object) msg.getKpfmc());
                            jsonOA.put("buyer", (Object) msg.getSpfmc());
                            jsonOA.put("amountMoney", (Object) msg.getHjje());
                            jsonOA.put("taxRate", (Object) msg.getBodys().get(0).getSlv());
                            jsonOA.put("taxMoney", (Object) msg.getHjse());
                            jsonOA.put("totalWithTax", (Object) msg.getKpje());
                            String rq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            jsonOA.put("makeDate", (Object) rq);
                            jsonOA.put("makeUser", (Object) "1");
                            Date now = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            jsonOA.put("typeInDate", (Object) sdf.format(now));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg.getId());
                            WechatController.log.info(">>>>>>>>>id" + msg.getId());
                            if (msg.getFphm() == null && msg.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                        } else {
                            msg.setPdfUrl(createDateDir(this.pdfUrl) + "/" + key);
                            msg.setSuccess("false");
                            log.error("未查验成功,参数：{}", JSONObject.toJSONString(msg));
                        }
                        msgs.add(msg);
                    } else if ("10104".equals(identify_results.getString("type"))) {
                        String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                        String fpdm = details.getString("code");
                        String fphm = details.getString("number");
                        String kpje = details.getString("total");
                        String machineCode = details.getString("machine_code");
                        String machineNumber = details.getString("machine_number");
                        String hjje = details.getString("pretax_amount");
                        String kpfmc = details.getString("seller");
                        String kpfsbh = details.getString("seller_tax_id");
                        String spfmc = details.getString("buyer");
                        String spfsbh = details.getString("buyer_id");
                        String tax_authorities = details.getString("tax_authorities");
                        String tax_authorities_code = details.getString("tax_authorities_code");
                        String car_code = details.getString("car_code");
                        String car_engine_code = details.getString("car_engine_code");
                        String car_model = details.getString("car_model");
                        String certificate_number = details.getString("certificate_number");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        if (!"".equals(kprq)) {
                            try {
                                invoiceHead.setKprq(sdf2.parse(kprq));
                                jsonOA.put("makeDate", (Object) kprq);
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                        invoiceHead.setFpdm(fpdm);
                        invoiceHead.setFphm(fphm);
                        invoiceHead.setKpje(kpje);
                        invoiceHead.setMachineCode(machineCode);
                        invoiceHead.setMachineNumber(machineNumber);
                        invoiceHead.setHjje(hjje);
                        invoiceHead.setKpfmc(kpfmc);
                        invoiceHead.setKpfsbh(kpfsbh);
                        invoiceHead.setSpfmc(spfmc);
                        invoiceHead.setSpfsbh(spfsbh);
                        invoiceHead.setTaxAuthorities(tax_authorities);
                        invoiceHead.setTaxAuthoritiesCode(tax_authorities_code);
                        invoiceHead.setCarCode(car_code);
                        invoiceHead.setCarEngineCode(car_engine_code);
                        invoiceHead.setCarModel(car_model);
                        invoiceHead.setCertificateNumber(certificate_number);
                        invoiceHead.setOpenId(openId);
                        Long l7 = new Date().getTime();
                        WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l7);
                        log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                        invoiceHead = this.service.save(invoiceHead);
                        Long l8 = new Date().getTime();
                        WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l8);
                        WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l8 - l7));
                        InvoiceMsg msg2 = this.changeToHead(invoiceHead);
                        msg2.setCode(0);
                        msg2.setSuccess("true");
                        jsonOA.put("invoceNum", (Object) msg2.getFphm());
                        jsonOA.put("invoceCode", (Object) msg2.getFpdm());
                        jsonOA.put("type", (Object) msg2.getFplx());
                        jsonOA.put("salesParty", (Object) msg2.getKpfmc());
                        jsonOA.put("buyer", (Object) msg2.getSpfmc());
                        jsonOA.put("amountMoney", (Object) msg2.getHjje());
                        jsonOA.put("taxRate", (Object) 0);
                        jsonOA.put("taxMoney", (Object) msg2.getHjse());
                        jsonOA.put("totalWithTax", (Object) msg2.getKpje());
                        jsonOA.put("makeUser", (Object) "1");
                        Date now2 = new Date();
                        jsonOA.put("typeInDate", (Object) sdf2.format(now2));
                        jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                        jsonOA.put("status", (Object) "0");
                        jsonOA.put("invoce_sys_id", (Object) msg2.getId());
                        if (msg2.getFphm() == null && msg2.getFpdm() == null) {
                            throw new BusinessException("发票代码、发票号码识别异常");
                        }
                        jsonArray.add((Object) jsonOA);
                        msgs.add(msg2);
                    } else if (!"10105".equals(identify_results.getString("type"))) {
                        if ("10200".equals(identify_results.getString("type"))) {
                            String fpdm2 = details.getString("code");
                            String fphm2 = details.getString("number");
                            String kpje2 = details.getString("total");
                            invoiceHead.setFpdm(fpdm2);
                            invoiceHead.setFphm(fphm2);
                            invoiceHead.setKpje(kpje2);
                            Long l9 = new Date().getTime();
                            WechatController.log.info("保存定额发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l9);
                            invoiceHead.setOpenId(openId);
                            invoiceHead.setKprq(new Date());
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l10 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l10);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l10 - l9));
                            InvoiceMsg msg3 = this.changeToHead(invoiceHead);
                            msg3.setCode(0);
                            msg3.setSuccess("true");
//                            jsonOA.put("invoceNum", (Object)msg3.getFphm());
//                            jsonOA.put("invoceCode", (Object)msg3.getFpdm());
//                            jsonOA.put("type", (Object)msg3.getFplx());
//                            jsonOA.put("salesParty", (Object)"");
//                            jsonOA.put("buyer", (Object)"");
//                            jsonOA.put("amountMoney", (Object)invoiceHead.getDkwsje());
//                            jsonOA.put("taxRate", (Object)invoiceHead.getTaxRate());
//                            jsonOA.put("taxMoney", (Object)invoiceHead.getDkje());
//                            jsonOA.put("totalWithTax", (Object)invoiceHead.getKpje());
//                            jsonOA.put("makeDate", (Object)"");
//                            jsonOA.put("makeUser", (Object)"1");
//                             Date now3 = new Date();
//                             SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
//                            jsonOA.put("typeInDate", (Object)sdf3.format(now3));
//                            jsonOA.put("imageUrl", (Object)(createDateDir(this.pdfUrl)+"/" + key));
//                            jsonOA.put("status", (Object)"0");
//                            jsonOA.put("invoce_sys_id", (Object)msg3.getId());
//                            if (msg3.getFphm() == null && msg3.getFpdm() == null) {
//                                throw new BusinessException("发票代码、发票号码识别异常");
//                            }
//                            jsonArray.add((Object)jsonOA);
                            msgs.add(msg3);
                        } else if ("10400".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String fpdm = details.getString("code");
                            String fphm = details.getString("number");
                            String time = details.getString("time");
                            String jym = details.getString("check_code");
                            String category = details.getString("category");
                            String kpje3 = details.getString("total");
                            String kpfmc = details.getString("seller");
                            String kpfsbh = details.getString("seller_tax_id");
                            String spfmc = details.getString("buyer");
                            String spfsbh = details.getString("buyer_tax_id");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            invoiceHead.setFpdm(fpdm);
                            invoiceHead.setFphm(fphm);
                            invoiceHead.setTime(time);
                            invoiceHead.setJym(jym);
                            invoiceHead.setCategory(category);
                            invoiceHead.setKpje(kpje3);
                            invoiceHead.setKpfmc(kpfmc);
                            invoiceHead.setKpfsbh(kpfsbh);
                            invoiceHead.setSpfmc(spfmc);
                            invoiceHead.setSpfsbh(spfsbh);
                            invoiceHead.setOpenId(openId);
                            Long l11 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l11);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l12 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l12);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l12 - l11));
                            InvoiceMsg msg4 = this.changeToHead(invoiceHead);
                            msg4.setCode(0);
                            msg4.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg4.getFphm());
                            jsonOA.put("invoceCode", (Object) msg4.getFpdm());
                            jsonOA.put("type", (Object) msg4.getFplx());
                            jsonOA.put("salesParty", (Object) kpfmc);
                            jsonOA.put("buyer", (Object) spfmc);
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now4 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf.format(now4));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg4.getId());
                            if (msg4.getFphm() == null && msg4.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg4);
                        } else if ("10500".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String fpdm = details.getString("code");
                            String fphm = details.getString("number");
                            String time_geton = details.getString("time_geton");
                            String time_getoff = details.getString("time_getoff");
                            String mileage = details.getString("mileage");
                            String kpje3 = details.getString("total");
                            String place = details.getString("place");
                            SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf4.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            invoiceHead.setFpdm(fpdm);
                            invoiceHead.setFphm(fphm);
                            invoiceHead.setTimeGeton(time_geton);
                            invoiceHead.setTimeGetoff(time_getoff);
                            invoiceHead.setMileage(mileage);
                            invoiceHead.setKpje(kpje3);
                            invoiceHead.setPlace(place);
                            invoiceHead.setStationGeton(place);
                            invoiceHead.setOpenId(openId);
                            Long l13 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l13);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l14 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l14);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l14 - l13));
                            InvoiceMsg msg5 = this.changeToHead(invoiceHead);
                            msg5.setCode(0);
                            msg5.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg5.getFphm());
                            jsonOA.put("invoceCode", (Object) msg5.getFpdm());
                            jsonOA.put("type", (Object) msg5.getFplx());
                            jsonOA.put("salesParty", (Object) "");
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now5 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf4.format(now5));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg5.getId());
                            jsonOA.put("timeGeton", (Object) time_geton);
                            jsonOA.put("timeGetoff", (Object) time_getoff);
                            jsonOA.put("name", (Object) "");
                            jsonOA.put("userId", (Object) "");
                            jsonOA.put("stationGeton", (Object) place);
                            jsonOA.put("stationGetoff", (Object) "");
                            jsonOA.put("trainNumber", (Object) "");
                            jsonOA.put("seat", (Object) "");
                            jsonOA.put("isChange", (Object) isChange);
                            if (msg5.getFphm() == null && msg5.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg5);
                        } else if ("10503".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String time2 = details.getString("time");
                            String fphm = details.getString("number");
                            String station_geton = details.getString("station_geton");
                            String station_getoff = details.getString("station_getoff");
                            String train_number = details.getString("train_number");
                            String kpje3 = details.getString("total");
                            String seat = details.getString("seat");
                            String name = details.getString("name");
                            SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf5.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            invoiceHead.setTime(time2);
                            invoiceHead.setTimeGeton(time2);
                            invoiceHead.setFphm(fphm);
                            invoiceHead.setKpje(kpje3);
                            invoiceHead.setStationGeton(station_geton);
                            invoiceHead.setStationGetoff(station_getoff);
                            invoiceHead.setTrainNumber(train_number);
                            invoiceHead.setSeat(seat);
                            invoiceHead.setName(name);
                            invoiceHead.setOpenId(openId);
                            Long l15 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l15);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l16 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l16);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l16 - l15));
                            InvoiceMsg msg6 = this.changeToHead(invoiceHead);
                            msg6.setCode(0);
                            msg6.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg6.getFphm());
                            jsonOA.put("invoceCode", (Object) msg6.getFpdm());
                            jsonOA.put("type", (Object) msg6.getFplx());
                            jsonOA.put("salesParty", (Object) "");
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now6 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf5.format(now6));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg6.getId());
                            jsonOA.put("timeGeton", (Object) time2);
                            jsonOA.put("timeGetoff", (Object) "");
                            jsonOA.put("name", (Object) name);
                            jsonOA.put("userId", (Object) "");
                            jsonOA.put("stationGeton", (Object) station_geton);
                            jsonOA.put("stationGetoff", (Object) station_getoff);
                            jsonOA.put("trainNumber", (Object) train_number);
                            jsonOA.put("seat", (Object) seat);
                            jsonOA.put("isChange", (Object) isChange);
                            if (msg6.getFphm() == null && msg6.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg6);
                        } else if ("10505".equals(identify_results.getString("type"))) {
                            String kprq = "";
                            try {
                                kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.error("此发票无日期=================");
                                log.info("此发票无日期==================");
                            }

                            String fpdm = details.getString("code");
                            String fphm = details.getString("number");
                            String station_geton = details.getString("station_geton");
                            String station_getoff = details.getString("station_getoff");
                            String kpje4 = details.getString("total");
                            String time3 = details.getString("time");
                            String name2 = details.getString("name");
                            SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf4.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            invoiceHead.setFpdm(fpdm);
                            invoiceHead.setFphm(fphm);
                            invoiceHead.setKpje(kpje4);
                            invoiceHead.setStationGeton(station_geton);
                            invoiceHead.setStationGetoff(station_getoff);
                            invoiceHead.setTime(time3);
                            invoiceHead.setTimeGeton(time3);
                            invoiceHead.setName(name2);
                            Long l13 = new Date().getTime();
                            invoiceHead.setOpenId(openId);
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l13);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l14 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l14);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l14 - l13));
                            InvoiceMsg msg5 = this.changeToHead(invoiceHead);
                            msg5.setCode(0);
                            msg5.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg5.getFphm());
                            jsonOA.put("invoceCode", (Object) msg5.getFpdm());
                            jsonOA.put("type", (Object) msg5.getFplx());
                            jsonOA.put("salesParty", (Object) "");
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now5 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf4.format(now5));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg5.getId());
                            jsonOA.put("timeGeton", (Object) time3);
                            jsonOA.put("timeGetoff", (Object) "");
                            jsonOA.put("name", (Object) name2);
                            jsonOA.put("userId", (Object) "");
                            jsonOA.put("stationGeton", (Object) station_geton);
                            jsonOA.put("stationGetoff", (Object) station_getoff);
                            jsonOA.put("trainNumber", (Object) "");
                            jsonOA.put("seat", (Object) "");
                            jsonOA.put("isChange", (Object) isChange);
                            if (msg5.getFphm() == null && msg5.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg5);
                        } else if ("10506".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String fphm2 = details.getString("number");
                            String kpje2 = details.getString("total");
                            String user_name = details.getString("user_name");
                            String user_id = details.getString("user_id");
                            String jym2 = details.getString("check_code");
                            String agentcode = details.getString("agentcode");
                            String issue_by = details.getString("issue_by");
                            String fare = details.getString("fare");
                            String tax = details.getString("tax");
                            String fuel_surcharge = details.getString("fuel_surcharge");
                            String caac_development_fund = details.getString("caac_development_fund");
                            String insurance = details.getString("insurance");
                            String flights = details.getJSONArray("flights").toJSONString();
                            SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf6.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e5) {
                                    e5.printStackTrace();
                                }
                            }
                            invoiceHead.setFphm(fphm2);
                            invoiceHead.setKpje(kpje2);
                            invoiceHead.setUserName(user_name);
                            invoiceHead.setUserId(user_id);
                            invoiceHead.setJym(jym2);
                            invoiceHead.setAgentcode(agentcode);
                            invoiceHead.setIssueBy(issue_by);
                            if ("0.00".equals(fare)) {
                                fare = new BigDecimal(kpje2).subtract(new BigDecimal(fuel_surcharge)).toString();
                            }
                            invoiceHead.setFare(fare);

//                            fare+tax+fuel+caac = toale
                            //如果fare识别出错，就以toale为中心计算
                            //2020-11-28  马文杰

                            BigDecimal kpje22;
                            BigDecimal faree;
                            BigDecimal taxx;
                            BigDecimal fuel_surchargee;
                            BigDecimal caac_development_fundd;

                            if (!"".equals(kpje2)) {
                                kpje22 = new BigDecimal(kpje2);
                            } else {
                                kpje22 = new BigDecimal(0);
                            }
                            if (!"".equals(fare)) {
                                faree = new BigDecimal(fare);
                            } else {
                                faree = new BigDecimal(0);
                            }
                            if (!"".equals(tax)) {
                                taxx = new BigDecimal(tax);
                            } else {
                                taxx = new BigDecimal(0);
                            }
                            if (!"".equals(fuel_surcharge)) {
                                fuel_surchargee = new BigDecimal(fuel_surcharge);
                            } else {
                                fuel_surchargee = new BigDecimal(0);
                            }
                            if (!"".equals(caac_development_fund)) {
                                caac_development_fundd = new BigDecimal(caac_development_fund);
                            } else {
                                caac_development_fundd = new BigDecimal(0);
                            }

                            invoiceHead.setKpje(kpje2);
                            if (kpje22.compareTo(faree.add(taxx).add(fuel_surchargee).add(caac_development_fundd)) == 0) {
                                //合计税额
                                invoiceHead.setHjse(faree.multiply(new BigDecimal("0.09")).divide(new BigDecimal("1.09"), 2, BigDecimal.ROUND_HALF_UP).toString());
                                //合计金额
                                invoiceHead.setHjje(
                                        (new BigDecimal(fare).subtract(new BigDecimal(invoiceHead.getHjse())).
                                                add(new BigDecimal(kpje2).subtract(new BigDecimal(fare)))).toString()
                                );
                            } else {
                                BigDecimal k = kpje22.subtract(taxx).subtract(fuel_surchargee).subtract(caac_development_fundd).setScale(2, BigDecimal.ROUND_HALF_UP);
                                //合计税额
                                invoiceHead.setHjse(k.multiply(new BigDecimal("0.09")).divide(new BigDecimal("1.09"), 2, BigDecimal.ROUND_HALF_UP).toString());
                                //合计金额
                                invoiceHead.setHjje(
                                        (k.subtract(new BigDecimal(invoiceHead.getHjse())).
                                                add(new BigDecimal(kpje2).subtract(k))).toString()
                                );
                            }

                            invoiceHead.setTax(tax);
                            invoiceHead.setFuelSurcharge(fuel_surcharge);
                            invoiceHead.setCaacDevelopmentFund(caac_development_fund);
                            invoiceHead.setInsurance(insurance);
                            invoiceHead.setFlights(flights);
                            invoiceHead.setOpenId(openId);
                            Long l17 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l17);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l18 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l18);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l18 - l17));
                            InvoiceMsg msg7 = this.changeToHead(invoiceHead);
                            msg7.setCode(0);
                            msg7.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg7.getFphm());
                            jsonOA.put("invoceCode", (Object) msg7.getFpdm());
                            jsonOA.put("type", (Object) msg7.getFplx());
                            jsonOA.put("salesParty", (Object) msg7.getIssueBy());
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now7 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf6.format(now7));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg7.getId());
                            if (msg7.getFphm() == null && msg7.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg7);
                        } else if ("10507".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String fpdm = details.getString("code");
                            String fphm = details.getString("number");
                            String entrance = details.getString("entrance");
                            String exit = details.getString("exit");
                            String kpje4 = details.getString("total");
                            String time3 = details.getString("time");
                            SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf3.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e6) {
                                    e6.printStackTrace();
                                }
                            }
                            invoiceHead.setFpdm(fpdm);
                            invoiceHead.setFphm(fphm);
                            invoiceHead.setKpje(kpje4);
                            invoiceHead.setTime(time3);
                            invoiceHead.setTimeGeton(time3);
                            invoiceHead.setEntrance(entrance);
                            invoiceHead.setExist(exit);
                            invoiceHead.setStationGeton(entrance);
                            invoiceHead.setStationGetoff(exit);
                            invoiceHead.setOpenId(openId);
                            Long l19 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l19);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l20 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l20);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l20 - l19));
                            InvoiceMsg msg8 = this.changeToHead(invoiceHead);
                            msg8.setCode(0);
                            msg8.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg8.getFphm());
                            jsonOA.put("invoceCode", (Object) msg8.getFpdm());
                            jsonOA.put("type", (Object) msg8.getFplx());
                            jsonOA.put("salesParty", (Object) "");
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now8 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf3.format(now8));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg8.getId());
                            jsonOA.put("timeGeton", (Object) time3);
                            jsonOA.put("timeGetoff", (Object) "");
                            jsonOA.put("name", (Object) "");
                            jsonOA.put("userId", (Object) "");
                            jsonOA.put("stationGeton", (Object) entrance);
                            jsonOA.put("stationGetoff", (Object) exit);
                            jsonOA.put("trainNumber", (Object) "");
                            jsonOA.put("seat", (Object) "");
                            jsonOA.put("isChange", (Object) isChange);
                            if (msg8.getFphm() == null && msg8.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg8);
                        } else if (!"10900".equals(identify_results.getString("type")) && !"00000".equals(identify_results.getString("type")) && "20100".equals(identify_results.getString("type"))) {
                            String kprq = details.getString("date").replace("年", "-").replace("月", "-").replace("日", "");
                            String store_name = details.getString("store_name");
                            String subtotal = details.getString("subtotal");
                            String tax2 = details.getString("tax");
                            String discount = details.getString("discount");
                            String tips = details.getString("tips");
                            String kpje3 = details.getString("total");
                            String time4 = details.getString("time");
                            String currency_code = details.getString("currency_code");
                            SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM-dd");
                            if (!"".equals(kprq)) {
                                try {
                                    invoiceHead.setKprq(sdf5.parse(kprq));
                                    jsonOA.put("makeDate", (Object) kprq);
                                } catch (ParseException e4) {
                                    e4.printStackTrace();
                                }
                            }
                            invoiceHead.setKpje(kpje3);
                            invoiceHead.setTime(time4);
                            invoiceHead.setTimeGeton(time4);
                            invoiceHead.setStoreName(store_name);
                            invoiceHead.setSubtotal(subtotal);
                            invoiceHead.setTax(tax2);
                            invoiceHead.setDiscount(discount);
                            invoiceHead.setTips(tips);
                            invoiceHead.setCurrencyCode(currency_code);
                            invoiceHead.setOpenId(openId);
                            Long l15 = new Date().getTime();
                            WechatController.log.info("保存发票开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l15);
                            log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
                            invoiceHead = this.service.save(invoiceHead);
                            Long l16 = new Date().getTime();
                            WechatController.log.info("保存发票结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l16);
                            WechatController.log.info("保存发票耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l16 - l15));
                            InvoiceMsg msg6 = this.changeToHead(invoiceHead);
                            msg6.setCode(0);
                            msg6.setSuccess("true");
                            jsonOA.put("invoceNum", (Object) msg6.getFphm());
                            jsonOA.put("invoceCode", (Object) msg6.getFpdm());
                            jsonOA.put("type", (Object) msg6.getFplx());
                            jsonOA.put("salesParty", (Object) "");
                            jsonOA.put("buyer", (Object) "");
                            jsonOA.put("amountMoney", (Object) invoiceHead.getDkwsje());
                            jsonOA.put("taxRate", (Object) invoiceHead.getTaxRate());
                            jsonOA.put("taxMoney", (Object) invoiceHead.getDkje());
                            jsonOA.put("totalWithTax", (Object) invoiceHead.getKpje());
                            jsonOA.put("makeUser", (Object) "1");
                            Date now6 = new Date();
                            jsonOA.put("typeInDate", (Object) sdf5.format(now6));
                            jsonOA.put("imageUrl", (Object) (createDateDir(this.pdfUrl) + "/" + key));
                            jsonOA.put("status", (Object) "0");
                            jsonOA.put("invoce_sys_id", (Object) msg6.getId());
                            if (msg6.getFphm() == null && msg6.getFpdm() == null) {
                                throw new BusinessException("发票代码、发票号码识别异常");
                            }
                            jsonArray.add((Object) jsonOA);
                            msgs.add(msg6);
                        }
                    }
                }
                jsonOAData.put("data", (Object) jsonArray);
//                WechatController.log.info("同步到OA参数:" + JSONObject.toJSONString(msgs));
//                Long l21 = new Date().getTime();
//                WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l21);
//                this.asyncBean.asynToFin((List) msgs);
//                Long l22 = new Date().getTime();
//                WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l22);
//                WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l22 - l21));
                return msgs;
            }
            new BusinessException("识别异常");
        } catch (Exception e7) {
            e7.printStackTrace();
            new BusinessException("识别异常");
            try {
                baos.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            return null;
        } finally {
            try {
                baos.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e8) {
                e8.printStackTrace();
            }
        }
        try {
            baos.close();
        } catch (IOException e8) {
            e8.printStackTrace();
        }
        try {
            fis.close();
        } catch (IOException e8) {
            e8.printStackTrace();
        }
        return null;
    }

    @ResponseResult
    @PostMapping({"save"})
    @ResponseBody
    public Map<String, String> save(@RequestBody InvoiceHead invoiceHead) {
        Map<String, String> map = new HashMap<String, String>();
        WechatController.log.info("发票修改保存》》》》》》》》");
        Long l1 = new Date().getTime();
        WechatController.log.info("是否改变>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：{}" + ((invoiceHead.getIsChange() == "0") ? "已修改" : "未修改"), (Object) invoiceHead.getIsChange());
        String isChange = "1";
        if (!StringUtils.isEmpty((CharSequence) invoiceHead.getIsChange())) {
            isChange = invoiceHead.getIsChange();
        }
        log.info("保存票据信息:{}", JSONObject.toJSON(invoiceHead));
        log.info("invoiceHead.getDkje():" + invoiceHead.getDkje());
        log.info("invoiceHead.getDkwsje():" + invoiceHead.getDkwsje());
        //火车
        if (invoiceHead.getFplx() != null && invoiceHead.getFplx().equals("10503")) {
            invoiceHead.setDkje(new BigDecimal(invoiceHead.getKpje()).divide(new BigDecimal(1.09), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(0.09)).setScale(2, BigDecimal.ROUND_HALF_UP));
            invoiceHead.setDkwsje(new BigDecimal(invoiceHead.getKpje()).subtract(invoiceHead.getDkje()));
        }
        //客运汽车
        if (invoiceHead.getFplx() != null && invoiceHead.getFplx().equals("10505")) {
            invoiceHead.setDkje(new BigDecimal(invoiceHead.getKpje()).divide(new BigDecimal(1.03), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(0.03)).setScale(2, BigDecimal.ROUND_HALF_UP));
            invoiceHead.setDkwsje(new BigDecimal(invoiceHead.getKpje()).subtract(invoiceHead.getDkje()));
        }
        log.info("计算之后invoiceHead.getDkje():" + invoiceHead.getDkje());
        log.info("计算之后invoiceHead.getDkwsje():" + invoiceHead.getDkwsje());
        log.info("save方法得到的fplx：" + invoiceHead.getFplx());
        log.info("save方法得到的SpecialType" + invoiceHead.getSpecialType());

        invoiceHead = this.service.save(invoiceHead);
        Long l2 = new Date().getTime();
        WechatController.log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l2);
        List<InvoiceMsg> hs = new ArrayList<InvoiceMsg>();
        InvoiceMsg msg = this.changeToHead(invoiceHead);
        hs.add(msg);
//        this.asyncBean.asynToFin((List) hs);
        Long l3 = new Date().getTime();
        WechatController.log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l3);
        WechatController.log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l3 - l2));
        map.put("msg", "保存成功");
        return map;
    }

    @ResponseResult
    @GetMapping({"/verification"})
    @ResponseBody
    public Map<String, Object> verification(@RequestParam(name = "state", required = true) String state, @RequestParam(name = "ids", required = true) String ids, @RequestParam(name = "reimburseNo", required = false) String reimburseNo, InvoiceHeadPoJo invoiceHead) {
        Map<String, Object> map = new HashMap<String, Object>();
        invoiceHead.setIds(ids);
        invoiceHead.setBxState(state);
        invoiceHead.setReimburseNo(reimburseNo);
        if (invoiceHead.getIds() != null && StringUtils.isNotEmpty((CharSequence) invoiceHead.getIds().toString()) && state != null && (state.equals("0") || state.equals("1"))) {
            Integer len = this.service.changeDelStateByIds(invoiceHead);
            String str = "";
            if (state.equals("0")) {
                str = "反核销";
            } else {
                str = "核销";
            }
            if (len != null && len > 0) {
                map.put("msg", String.valueOf(str) + "发票成功");
                map.put("success", true);
            } else {
                map.put("msg", String.valueOf(str) + "发票失败，未查询到相应发票");
                map.put("success", false);
            }
        } else {
            map.put("msg", "参数有误");
            map.put("success", false);
        }
        return map;
    }

    @ResponseResult
    @GetMapping({"/del"})
    @ResponseBody
    public Map<String, Object> verification(@RequestParam(name = "state", required = true) String state, @RequestParam(name = "ids", required = true) String ids, InvoiceHeadPoJo invoiceHead) {
        Map<String, Object> map = new HashMap<String, Object>();
        invoiceHead.setIds(ids);
        invoiceHead.setDelState(state);
        if (invoiceHead.getIds() != null && StringUtils.isNotEmpty((CharSequence) invoiceHead.getIds().toString()) && state != null && (state.equals("0") || state.equals("1"))) {
            Integer len = this.service.changeDelStateByIds(invoiceHead);
            String str = "";
            if (state.equals("0")) {
                str = "撤销删除";
            } else {
                str = "删除";
            }
            if (len != null && len > 0) {
                map.put("msg", String.valueOf(str) + "发票成功");
                map.put("success", true);
            } else {
                map.put("msg", String.valueOf(str) + "发票失败，未查询到相应发票");
                map.put("success", false);
            }
        } else {
            map.put("msg", "参数有误");
            map.put("success", false);
        }
        return map;
    }

    @ResponseResult
    @GetMapping({"/delInvoice"})
    @ResponseBody
    public Map<String, Object> verification(@RequestParam("openId") String openId, @RequestParam("invoiceId") String invoiceId) {
        Map map = new HashMap();
//        Integer len0 = this.easInvoicereceiveServiceFegin.delete(Long.parseLong(invoiceId));
//        if (len0 == 0) {
//            map.put("msg", "删除发票失败，发票已被锁定不可删除");
//            map.put("success", false);
//            return (Map<String, Object>) map;
//        }
        Integer len2 = this.service.delInvoice(openId, invoiceId);
        String str = "删除";
        if (len2 != null && len2 > 0) {
            map.put("msg", String.valueOf(str) + "发票成功");
            map.put("success", true);
        } else {
            map.put("msg", String.valueOf(str) + "发票失败，未查询到相应发票或发票状态不可删除");
            map.put("success", false);
        }
        return (Map<String, Object>) map;
    }


}
