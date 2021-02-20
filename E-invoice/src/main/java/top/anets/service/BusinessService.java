/**
 * 
 */
package top.anets.service;

import java.util.List;

import top.anets.entity.Path;
import top.anets.vo.BusinessVo;

/**
 * @author Administrator
 *
 */
public interface BusinessService {
    void saveOrUpdate(BusinessVo business);
    
    
    void delete(Integer id);
     
    
    List<BusinessVo> queryAll();


	/**
	* 
	*@param id
	*@return 
	*/
	List<BusinessVo> queryByUserId(Integer id);


	/**
	* 
	*@param orgId
	*@param orgMachine
	*@return 
	*/
	BusinessVo query(String orgId, String orgMachine);

 
}
