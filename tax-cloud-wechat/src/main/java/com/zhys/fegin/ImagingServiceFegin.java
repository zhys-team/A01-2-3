package com.zhys.fegin;

import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import com.lycheeframework.core.cmp.kit.EasyPage;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.ResponseBase;
import com.zhys.ea.po.EaHead;

@Component

@FeignClient(value="imaging",path="/upload")
public interface ImagingServiceFegin  {

    /**
     * 上传附件
     * @param t
     * @return
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") byte[] file,@RequestParam("fileName") String fileName);


    @PostMapping(value = "/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void upload(@RequestPart(value = "file") MultipartFile file,@RequestParam("fileName") String fileName);




}
