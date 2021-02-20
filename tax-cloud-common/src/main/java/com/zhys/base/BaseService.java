package com.zhys.base;

import com.lycheeframework.core.cmp.kit.Pages;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 
 * @author lihui
 *
 * @param <T>
 */
public interface BaseService <T,E> {

    /**
     * 新增
     * @param t
     * @return
     */
	@PostMapping("/save")
    Object save(@RequestBody T t);


    
    /**
     * 查询单个实体
     * @return
     */
	@PostMapping("/queryByEntity")
    T queryByEntity(@RequestBody T t);

    /**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pages")
    Pages<List<T>> pages(@RequestBody T t, Integer pageSize,Integer pageNum);
	
	/**
     * 查询带分页
     * @param t page
     * @return
     */
	@PostMapping("/pagesByPojo")
    Pages<List<T>> pagesByPojo(@RequestBody E e, Integer pageSize,Integer pageNum);
	
	
	/**
     * 查询
     * @param t
     * @return
     */
	@PostMapping("/queryList")
    List<T> queryList(@RequestBody T t);
	
	/**
     * 查询
     * @param e
     * @return
     */
	@PostMapping("/queryListByPoJo")
    List<T> queryListByPoJo(@RequestBody E e);
	
	/**
     * 通过主键更新状态
     * @param t
     * @return
     */
	@PostMapping("/changeDelStateById")
	Integer changeDelStateById(@RequestBody T t);
	
	/**
     * 通过ids批量修改状态
     * @param t
     * @return
     */
	@PostMapping("/changeDelStateByIds")
	Integer changeDelStateByIds(@RequestBody E t);




}
