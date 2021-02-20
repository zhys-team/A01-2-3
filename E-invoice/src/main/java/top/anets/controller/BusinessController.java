/**
 * 
 */
package top.anets.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.anets.entity.Path;
import top.anets.entity.SysUsers;
import top.anets.service.BusinessService;
import top.anets.utils.Result;
import top.anets.vo.BusinessVo;

/**
 * @author Administrator
 *
 */
@RestController
@RequestMapping("business")
public class BusinessController {
	@Autowired
	private BusinessService businessService;
	@RequestMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody BusinessVo businessVo) { 
		List<Path> paths = businessVo.getPaths();
		if(paths==null||businessVo==null) {
			return Result.Error("参数有误");
		}
		try {
			businessService.saveOrUpdate(businessVo);
		} catch (Exception e) {
			return Result.Success("操作失败！"+e.getMessage(),e.getCause());
		}
		return Result.Success("操作成功！");
	}
	
	@RequestMapping("/delete")
    public Result delete(Integer id) {
		try {
			businessService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return Result.Error("删除失败！"+e.getMessage(),e.getCause());
		}
		return Result.Success("删除成功！");
	}

	
	@RequestMapping("/queryAll")
    public Result queryAll() {
		  List<BusinessVo> all = businessService.queryAll();
		return Result.Success("查询成功!",all);
	}
	
	
	@RequestMapping("/queryByUserId")
    public Result queryAll(Integer id) {
		List<BusinessVo> all = businessService.queryByUserId(id);
		HashMap<String, BusinessVo> map = new HashMap<>();
//		for (BusinessVo businessVo : all) {
//			map.put(businessVo.getOrgId(), businessVo);
//		}		
//		Set<Entry<String, BusinessVo>> set = map.entrySet();
//		List<BusinessVo> list = new ArrayList<>();
//		for (Entry<String, BusinessVo> entry : set) {
//			list.add(entry.getValue());
//		}
		return Result.Success("查询成功!",all);
	}
}
