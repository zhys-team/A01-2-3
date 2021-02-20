package com.invoice.controller;

import com.alibaba.fastjson.JSONObject;
import com.invoice.fegin.InvoiceHeadServiceFegin;
import com.invoice.fegin.TzsysEasInvoicereceiveServiceFegin;
import com.invoice.po.IndexEntity;
import com.invoice.po.InvoiceBody;
import com.invoice.po.InvoiceHead;
import com.invoice.po.JxtztjExcel;
import com.invoice.pojo.HeadList;
import com.invoice.pojo.IncomeCount;
import com.invoice.pojo.InvoiceBodyExcel;
import com.invoice.pojo.InvoiceHeadExcel;
import com.invoice.pojo.InvoiceHeadPoJo;
import com.invoice.pojo.InvoiceMsg;
import com.invoice.util.MultipartFileToFile;
import com.invoice.util.Pdf2picDemo;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.EntityValidate.EntityValidate;
import com.zhys.excel.ExcelCell;
import com.zhys.excel.ExportExcelUtil;
import com.zhys.excel.ImportExcelUtil;
import com.zhys.exception.BusinessException;
import com.zhys.po.DeductParamBody;
import com.zhys.po.DeductRecord;
import com.zhys.po.Dkhz;
import com.zhys.po.TzsysEasInvoicereceive;
import com.zhys.redis.RedisUtils;
import com.zhys.result.ResponseResult;
import com.zhys.result.ResultCode;
import com.zhys.user.po.SysUsersOrgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@ResponseResult
@RestController
@RequestMapping({"invoiceHeads"})
@Api(value = "发票接口", description = "发票接口")
public class InvoiceHeadController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceHeadController.class);

    @Autowired
    private InvoiceHeadServiceFegin service;

    @Autowired
    private TzsysEasInvoicereceiveServiceFegin easInvoicereceiveServiceFegin;

    @Autowired
    private RedisUtils redis;

    @Autowired
    private AsyncBean asyncBean;

    private static final String INDEX = "index";

    @Value("${pdfUrl}")
    private String pdfUrl;

    @ApiIgnore
    @GetMapping({"index"})
    public String index() {
        return "index";
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

    @ApiOperation(value = "发票分页列表", notes = "根据条件查询数据并分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true, paramType = "query", dataType = "Integer"), @ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType = "query", dataType = "Integer")})
    @PostMapping(value = {"page"}, produces = {"application/json"})
    public Pages<List<InvoiceHead>> pages(@RequestParam Integer pageSize, @RequestParam Integer pageNum, @ApiParam(name = "invoiceHead", value = "查询条件", required = true) @RequestBody(required = false) InvoiceHeadPoJo invoiceHead) {
        List<SysUsersOrgs> suos = null;
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser != null) {
            Session session = currentUser.getSession();
            suos = (List<SysUsersOrgs>) session.getAttribute("suos");
            if (suos != null && suos.size() > 0) {
                String ids = "";
                for (SysUsersOrgs uo : suos)
                    ids = String.valueOf(ids) + "'" + uo.getOrgId() + "',";
                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    invoiceHead.setOrgIds(ids);
                }
            } else {
                invoiceHead.setOrgIds("'1'");
            }
        }
        return this.service.pagesByPojo(invoiceHead, pageSize, pageNum);
    }

    @ApiOperation(value = "发票保存", notes = "发票保存")
    @PostMapping(value = {"/save"}, produces = {"application/json"})
    @CrossOrigin
    public Integer save(@RequestBody InvoiceHead invoiceHead) {
        this.service.save(invoiceHead);
        return Integer.valueOf(1);
    }

    @GetMapping({"{id}"})
    public InvoiceHead info(@PathVariable("id") Long id, InvoiceHead invoiceHead) {
        invoiceHead.setId(id);
        invoiceHead = this.service.queryByEntity(invoiceHead);
        return invoiceHead;
    }

    @PostMapping({"lists"})
    public List<InvoiceHead> queryList(@RequestBody InvoiceHead invoiceHead) {
        return this.service.queryList(invoiceHead);
    }

    @ApiOperation(value = "通过主键删除", notes = "根据主键删除数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "主键", required = true, paramType = "path", dataType = "Long")})
    @GetMapping({"/state/{id}"})
    public Object changeDelStateById(@PathVariable("id") Long id) {
        TzsysEasInvoicereceive easInvoicereceive = this.easInvoicereceiveServiceFegin.queryView(id);
        JSONObject json_re = new JSONObject();
        if (easInvoicereceive != null && easInvoicereceive.getInvoiceId() != null) {
            json_re.put("success", Boolean.valueOf(false));
            json_re.put("msg", "票据已被引用");
            return json_re;
        }
        InvoiceHead invoiceHead = new InvoiceHead();
        invoiceHead.setId(id);
        this.service.changeDelStateById(invoiceHead);
        this.easInvoicereceiveServiceFegin.delete(id);
        json_re.put("success", Boolean.valueOf(true));
        json_re.put("msg", "票据删除成功");
        return json_re;
    }

    @ApiOperation(value = "获取首页指数信息", notes = "获取首页指数信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "orgIds", value = "组织id", required = false, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "kprqStart", value = "起始开票日期", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "kprqEnd", value = "截至开票日期", required = true, paramType = "query", dataType = "String"), @ApiImplicitParam(name = "mx", value = "是否查询明细 0:否  1:是", required = true, paramType = "query", dataType = "String")})
    @GetMapping({"/indexMsg"})
    List<IndexEntity> indexMsg(@RequestParam(name = "orgIds", required = false) String orgId, @RequestParam("kprqStart") String kprqStart, @RequestParam("kprqEnd") String kprqEnd, @RequestParam("mx") String mx) {
        return this.service.indexMsg(orgId, kprqStart, kprqEnd, mx);
    }

    /**
     * 查验发票
     *
     * @param String InvoiceCode,String InvoiceNumber,String BillingDate,String TotalAmount,String CheckCode,String fply
     * @return
     */
    @ApiOperation(value = "查验发票", notes = "查验发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "员工号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "invoiceCode", value = "发票代码", required = false, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "invoiceNumber", value = "发票号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "billingDate", value = "开票日期", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "totalAmount", value = "不含税总金额", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "checkCode", value = "校验码后六位", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "fply", value = "发票来源  0 认证下载、1 pc扫码查验、2 、pc手动查验 3 app查验", required = true, paramType = "query", dataType = "String")
    })
    @GetMapping("/checkInvoice")
    InvoiceMsg checkInvoice(@RequestParam("invoiceCode") String invoiceCode, @RequestParam("invoiceNumber") String invoiceNumber, @RequestParam("billingDate") String billingDate, @RequestParam("totalAmount") String totalAmount, @RequestParam("checkCode") String checkCode, @RequestParam("fply") String fply) {
        return service.checkInvoice(invoiceCode, invoiceNumber, billingDate, totalAmount, checkCode, fply, "", "", "", "");
    }


    @PostMapping("/fileUpload")
    public Map fileUpload(@RequestParam("file") MultipartFile file) {
        Map<String, String> map = new HashMap<String, String>();
        Class clazz = InvoiceHeadExcel.class;
        Field[] fs = clazz.getDeclaredFields();
        if (fs != null && fs.length > 0) {
            for (Field f : fs) {
                ExcelCell ec = f.getAnnotation(ExcelCell.class);
                if (ec != null) {
                    map.put(ec.name(), f.getName());
                }

            }
        }

        Map<String, String> mapbody = new HashMap<String, String>();
        Class clazzbody = InvoiceBodyExcel.class;
        Field[] fsbody = clazzbody.getDeclaredFields();
        if (fsbody != null && fsbody.length > 0) {
            for (Field f : fsbody) {
                ExcelCell ec = f.getAnnotation(ExcelCell.class);
                if (ec != null) {
                    mapbody.put(ec.name(), f.getName());
                }

            }
        }

        FileInputStream fis = null;
        InputStream fis1 = null;
        InputStream fis2 = null;
        try {
            fis = (FileInputStream) file.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            fis1 = new ByteArrayInputStream(baos.toByteArray());
            fis2 = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        /**
         * 处理发票主信息
         */
        List<InvoiceHeadExcel> etps = ImportExcelUtil.readExcel(map, InvoiceHeadExcel.class, fis2, "test.xlsx", 0);
        List<InvoiceHeadPoJo> heads = new ArrayList<InvoiceHeadPoJo>();
        if (etps != null && etps.size() > 0) {
            for (InvoiceHeadExcel ihe : etps) {
                Field[] fs1 = clazz.getDeclaredFields();
                if (fs != null && fs.length > 0) {
                    for (Field f : fs1) {
                        EntityValidate ev = f.getAnnotation(EntityValidate.class);
                        if (ev != null) {
                            if (!ev.allowEmpty()) {
                                /**
                                 * 非空验证
                                 */
                                String key = f.getName();
                                PropertyDescriptor descriptor;
                                Object o = null;
                                try {
                                    descriptor = new PropertyDescriptor(key, InvoiceHeadExcel.class);
                                    Method method = descriptor.getReadMethod();
                                    f.setAccessible(true);
                                    o = method.invoke(ihe);
                                } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }

                                if (o == null) {
                                    ExcelCell ec = f.getAnnotation(ExcelCell.class);
                                    throw new BusinessException(ec.name() + "不能为空！");
                                }
                            }

                        }
                    }

                }

                //将表格里的值转成数据库里对应值
                if (ihe.getFpzl() != null && ihe.getFpzl().trim().equals("蓝票")) {
                    ihe.setFpzl("0");
                } else if (ihe.getFpzl() != null && ihe.getFpzl().trim().equals("红票")) {
                    ihe.setFpzl("1");
                }

                if (ihe.getFplx() != null && ihe.getFplx().trim().equals("增专")) {
                    ihe.setFplx("0");
                } else if (ihe.getFplx() != null && ihe.getFplx().trim().equals("增普")) {
                    ihe.setFplx("1");
                } else if (ihe.getFplx() != null && ihe.getFplx().trim().equals("电票")) {
                    ihe.setFplx("2");
                } else if (ihe.getFplx() != null && ihe.getFplx().trim().equals("外票")) {
                    ihe.setFplx("3");
                }

                if (ihe.getInOrOut() != null && ihe.getInOrOut().trim().equals("进")) {
                    ihe.setInOrOut("0");
                } else if (ihe.getInOrOut() != null && ihe.getInOrOut().trim().equals("销")) {
                    ihe.setInOrOut("1");
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    ihe.setKprq(sdf.parse(ihe.getKprq1()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    log.error("开票日期转换异常");
                }
                InvoiceHeadPoJo head = changeToHead(ihe);
                heads.add(head);
                //service.save(head);
            }
        }

        /**
         * 处理发票明细信息
         */
        List<InvoiceBodyExcel> ibes = ImportExcelUtil.readExcel(mapbody, InvoiceBodyExcel.class, fis1, "test.xlsx", 1);
        List<InvoiceBody> bodys = new ArrayList<InvoiceBody>();
        if (etps != null && etps.size() > 0) {
            for (InvoiceBodyExcel ihe : ibes) {
                Field[] fs1 = clazzbody.getDeclaredFields();
                if (fs != null && fs.length > 0) {
                    for (Field f : fs1) {
                        EntityValidate ev = f.getAnnotation(EntityValidate.class);
                        if (ev != null) {
                            if (!ev.allowEmpty()) {
                                /**
                                 * 非空验证
                                 */
                                String key = f.getName();
                                PropertyDescriptor descriptor;
                                Object o = null;
                                try {
                                    descriptor = new PropertyDescriptor(key, InvoiceBodyExcel.class);
                                    Method method = descriptor.getReadMethod();
                                    f.setAccessible(true);
                                    o = method.invoke(ihe);
                                } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }

                                if (o == null) {
                                    ExcelCell ec = f.getAnnotation(ExcelCell.class);
                                    throw new BusinessException(ec.name() + "不能为空！");
                                }
                            }

                        }

                    }

                }
                InvoiceBody body = changeToBody(ihe);
                bodys.add(body);
                //service.save(head);
            }
        }

        for (InvoiceHeadPoJo head : heads) {
            head.setBodys(bodys.stream()
                    .filter((InvoiceBody body) -> head.getId().equals(body.getHeadId()))
                    .collect(Collectors.toList()));
        }
        HeadList h = new HeadList();
        h.setHeads(heads);
        service.fileUpload(h);
        Map m = new HashMap<String, String>();
        m.put("success", "true");
        return m;
    }


    private InvoiceHeadPoJo changeToHead(InvoiceHeadExcel msg) {
        Class clazz = InvoiceHeadExcel.class;
        InvoiceHeadPoJo head = new InvoiceHeadPoJo();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(key, InvoiceHeadPoJo.class);
                Method method = descriptor.getWriteMethod();
                field.setAccessible(true);
                method.invoke(head, field.get(msg));
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                //e.printStackTrace();
            }

        }
        return head;
    }

    private InvoiceBody changeToBody(InvoiceBodyExcel msg) {
        Class clazz = InvoiceBodyExcel.class;
        InvoiceBody body = new InvoiceBody();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String key = field.getName();
            PropertyDescriptor descriptor;
            try {
                descriptor = new PropertyDescriptor(key, InvoiceBody.class);
                Method method = descriptor.getWriteMethod();
                field.setAccessible(true);
                method.invoke(body, field.get(msg));
            } catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return body;
    }


    @ApiOperation(value = "进项统计汇总", notes = "进项统计汇总")
    @PostMapping("/jxtj")
    public List<IncomeCount> jxtj(@RequestBody IncomeCount incomeCount) {
        return service.jxtj(incomeCount);
    }

    /**
     * 发票复查
     */
    @ApiOperation(value = "发票复查", notes = "发票复查")
    @GetMapping("/invoiceReview")
    public Object invoiceReview(@RequestParam("ids") String ids) {
        return service.invoiceReview(ids);
    }

    @ApiOperation(value = "发票复查日志", notes = "发票复查日志")
    @GetMapping(value = "/cy_log", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Object printInv() {
        JSONObject re = new JSONObject();
        re.put("success", true);
        re.put("data", redis.zGet("fclog", 0, 20));
        return re;
    }

    /**
     * 上传pdf
     *
     * @param file
     * @return
     */
    @ApiOperation(value = "上传pdf识别发票", notes = "上传pdf识别发票")
    @PostMapping("/upload_pdf")
    public Object upload_pdf(@RequestParam(name = "openId", required = true) String openId, @RequestParam("file") MultipartFile file) {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;

//		JSONObject ret = new JSONObject();

        try {
            //先判断文件类型
            //文件是pdf ，先转成图片，识别图片中二维码
            //文件是图片，直接识别图片中二维码
            //获取文件全名
            file.getOriginalFilename();
            File f;
            //首先判断是不是空的文件
            if (!file.isEmpty()) {
                //对文文件的全名进行截取然后在后缀名进行删选。
                int begin = file.getOriginalFilename().indexOf(".");
                int last = file.getOriginalFilename().length();
                //获得文件后缀名
                String a = file.getOriginalFilename().substring(begin, last);
                f = MultipartFileToFile.multipartFileToFile(file);
                //我这边需要的xlsx文件所以说我这边直接判断就是了
                String s = "";
                byte[] file_bytes = {};
                if (a.endsWith(".pdf")) {

                    s = new Pdf2picDemo().getMsg(f);
                    file_bytes = Pdf2picDemo.pdfbox(f);

                } else if (a.endsWith(".jpg") || a.endsWith(".png")) {
                    s = Pdf2picDemo.QRReader(f);
                    fis = (FileInputStream) file.getInputStream();
                    baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > -1) {
                        baos.write(buffer, 0, len);
                    }
                    file_bytes = baos.toByteArray();
                }

                if (s == null) {
                    log.info("图片/pdf精度不足，无法识别！");
                    log.error("图片/pdf精度不足，无法识别！");
                    JSONObject json = new JSONObject();
                    json.put("success", false);
                    json.put("msg", "图片/pdf精度不足，无法识别！");
                    json.put("code", "99");
                    throw new BusinessException(json.toJSONString());
                }

                String[] arr = s.split(",");
                String fpdm = arr[2];
                String fphm = arr[3];
                String je = arr[4];
                String rq = arr[5];
                String jym = arr[6];

//			ret.put("fpdm", arr[2]);
//			ret.put("fphm", arr[3]);
//			ret.put("je", arr[4]);
//			ret.put("rq", arr[5]);
//			ret.put("jym", arr[6]);


                if (jym != null && jym.length() > 6) {
                    jym = jym.substring(jym.length() - 6, jym.length());
                }
                //删除临时文件
                MultipartFileToFile.delteTempFile(f);
                log.info("电票校验码{}", jym);
                Random rand = new Random();
                int sjs = rand.nextInt(10000); //生成0-100以内的随机数


                String key = new DateTime().getMillis() + "_" + sjs + ".png";

                if (file_bytes != null) {

                    Long l2 = new Date().getTime();
                    log.info("图片上传到服务器开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l2);
                    asyncBean.upload(file_bytes, key);
                    Long l3 = new Date().getTime();
                    log.info("图片上传到服务器结束，耗时：>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l3 - l2));
                    log.info("s:" + s);
                } else {
                    log.error("图片转换出异常");

                }

                //查验发票
                InvoiceMsg im = service.checkInvoice(openId, fpdm, fphm, rq, je, jym, "1", createDateDir(this.pdfUrl) + "/" + key, "", "");
//                Long l7 = new Date().getTime();
//                log.info("同步到OA开始>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l7);
//                asyncBean.asynToFin(im);
//                Long l8 = new Date().getTime();
//                log.info("同步到OA结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + l8);
//                log.info("同步到OA耗时>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>：" + (l8 - l7));
                return im;

            } else {
                JSONObject data = new JSONObject();
                data.put("success", false);
                data.put("data", "文件为空");
                throw new BusinessException(ResultCode.DATA_IS_WRONG, data.toJSONString());
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            log.error("发票pdf识别异常，原因：{}", e.getMessage());
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public static void main(String[] args) {
        String s = "01,10,033001900311,17869524,117.92,20200410,03259727411935596106,9494";
        String[] arr = s.split(",");
        String fpdm = arr[2];
        String fphm = arr[3];
        String je = arr[4];
        String rq = arr[5];
        String jym = arr[6];
        if (jym != null && jym.length() > 6) {
            jym = jym.substring(jym.length() - 6, jym.length());
        }
        System.out.println(jym);
    }


    /**
     * 勾选
     *
     * @return
     */
    @PostMapping("/gx")
    public Object gx(@RequestBody DeductParamBody body) {
        return service.gx(body);
    }

    /**
     * 取消勾选
     *
     * @return
     */
    @ApiOperation(value = "取消勾选", notes = "取消勾选")
    @PostMapping("/cancel_gx")
    public Object cancel_gx(@RequestBody DeductParamBody body) {
        return service.cancel_gx(body);
    }


    @ApiOperation(value = "进项台账统计表导出", notes = "进项台账统计表导出")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody InvoiceHeadPoJo head) {
        ServletOutputStream out = null;
        HSSFWorkbook wb = null;
        try {
            List<InvoiceHead> list = service.queryListByPoJo(head);
            List<JxtztjExcel> list_excel = new ArrayList<JxtztjExcel>();
            Mapper mapper = new DozerBeanMapper();
            if (list != null && list.size() > 0) {
                for (InvoiceHead h : list) {

                    JxtztjExcel je = mapper.map(h, JxtztjExcel.class);
                    //1：初始状态 2：待认证  2.5 :已提交认证 3：已认证/已申报  4：已转出
                    if ("1".equals(je.getRzState())) {
                        je.setRzState("初始状态");
                    } else if ("2".equals(je.getRzState())) {
                        je.setRzState("待认证");
                    } else if ("2.5".equals(je.getRzState())) {
                        je.setRzState("已提交认证");
                    } else if ("3".equals(je.getRzState())) {
                        je.setRzState("已认证/已申报");
                    } else if ("4".equals(je.getRzState())) {
                        je.setRzState("已转出");
                    }
                    list_excel.add(je);
                }
            }
            log.info("进项台账统计表导出条数：{}", list_excel.size());
            wb = ExportExcelUtil.creatExcel("进项台账统计表", list_excel);

            //响应类型为application/octet- stream情况下使用了这个头信息的话，那就意味着不想直接显示内容
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);

            //attachment为以附件方式下载
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(
                    "进项台账统计表" + ".xls",
                    "utf-8"));

            response.setHeader("Cache-Control", "No-cache");
            response.flushBuffer();
            out = response.getOutputStream();
            wb.write(out);

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    @ApiOperation(value = "进项台账统计表导出数据", notes = "进项台账统计表导出数据")
    @PostMapping("/exportData")
    public List<JxtztjExcel> exportData(HttpServletResponse response, @RequestBody InvoiceHeadPoJo head) {

        List<InvoiceHead> list = service.queryListByPoJo(head);
        List<JxtztjExcel> list_excel = new ArrayList<JxtztjExcel>();
        Mapper mapper = new DozerBeanMapper();
        if (list != null && list.size() > 0) {
            for (InvoiceHead h : list) {

                JxtztjExcel je = mapper.map(h, JxtztjExcel.class);
                list_excel.add(je);
            }
        }
        return list_excel;

    }


    @ApiOperation(value = "客运类发票申报（批量）/发票转出", notes = "客运类发票申报（批量）/发票转出")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "ids", value = "id逗号分隔:'1','2'", required = true, paramType = "query", dataType = "String"),
                    @ApiImplicitParam(name = "rz_state", value = "认证状态 3:认证/申报 4：转出", required = true, paramType = "query", dataType = "String"),
                    @ApiImplicitParam(name = "kjdkq", value = "会计抵扣期", required = true, paramType = "query", dataType = "String")})
    @GetMapping({"/updateRzState"})
    public Object updateRzState(@RequestParam("ids") String ids, @RequestParam("rz_state") String rz_state, @RequestParam("kjdkq") String kjdkq) {
        InvoiceHeadPoJo ih = new InvoiceHeadPoJo();
        ih.setIds(ids);
        ih.setKjdkq(kjdkq);
        ih.setRzState(rz_state);
        service.updateRzState(ih);
        JSONObject data = new JSONObject();
        data.put("success", true);
        data.put("data", "更新成功");
        return data; 
    }


    @ApiOperation(value = "客运类发票申报（全部）", notes = "客运类发票申报（全部）")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "rzState", value = "认证状态 3:认证/申报 4：转出", required = true, paramType = "query", dataType = "String"),
                    @ApiImplicitParam(name = "kjdkq", value = "会计抵扣期", required = true, paramType = "query", dataType = "String")})
    @GetMapping({"/updateRzStateAll"})
    public Object updateRzStateAll(@RequestParam("rzState") String rzState, @RequestParam("kjdkq") String kjdkq) {
        InvoiceHeadPoJo ih = new InvoiceHeadPoJo();
        ih.setRzState(rzState);
        ih.setKjdkq(kjdkq);
        service.updateRzStateAll(ih);
        JSONObject data = new JSONObject();
        data.put("success", true);
        data.put("data", "更新成功");
        return data;
    }


    
    
    
    @ApiOperation(value = "抵扣汇总", notes = "抵扣汇总")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "nf", value = "年份 ：2020", required = true, paramType = "query", dataType = "String")})

    @GetMapping({"/getDkhz"})
    public List<Dkhz> getDkhz(@RequestParam("nf") String nf) {
        return service.getDkhz(nf);
    }


    @ApiOperation(value = "认证分页列表", notes = "根据条件查询数据并分页")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageSize", value = "每页多少条数据", required = true, paramType = "query", dataType = "Integer"), @ApiImplicitParam(name = "pageNum", value = "当前第几页", required = true, paramType = "query", dataType = "Integer")})
    @PostMapping(value = {"DeductPages"}, produces = {"application/json"})
    Pages<List<DeductRecord>> DeductPages(@RequestBody DeductRecord body, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum) {
        return service.DeductPages(body, pageSize, pageNum);
    }


}