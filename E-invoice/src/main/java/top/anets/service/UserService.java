/**
 * 
 */
package top.anets.service;

import java.util.List;

import top.anets.entity.SysUsers;

/**
 * @author Administrator
 *
 */
public interface UserService {
	List<SysUsers> queryAll();
	
	void saveOrUpdate(SysUsers users);
	
	
	void delete(Integer id);

	/**
	* 
	*@param sysUsers
	*@return 
	*/
	SysUsers getUser(String no ,String password);
	
	
}
