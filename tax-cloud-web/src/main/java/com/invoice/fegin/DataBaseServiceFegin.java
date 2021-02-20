package com.invoice.fegin;

import java.util.List;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.lycheeframework.core.cmp.kit.Pages;
import com.zhys.base.po.DataBase;

@Component
@FeignClient(value="user",path="/dataBase")
public interface DataBaseServiceFegin  {	

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Integer save(@RequestBody DataBase t);

	/**
     * 查询单个实体
     * @return
     */
	@GetMapping("/queryByEntity")
    DataBase queryByEntity(DataBase t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<DataBase>> pages(@RequestBody DataBase t,@RequestParam("pageSize") Integer pageSize,@RequestParam("pageNum") Integer pageNum);


	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody DataBase dataBase);


	@PostMapping("/changeDelStateById")
	Integer changeDelStateById( @RequestBody DataBase dataBase);
	
	@PostMapping("/queryList")
    List<DataBase> queryList(@RequestBody DataBase t);
	
	

}
