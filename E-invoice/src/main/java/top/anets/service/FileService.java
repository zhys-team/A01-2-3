package top.anets.service;

import java.util.List;

import top.anets.entity.File;

public interface FileService {
	
    public Boolean upLoadFile(String  path ,String name,String extName,Long size,long parentId,Integer userId);
   
    public Long upLoadFileAndReturnFid(String  path ,String name,String extName,Long size,long parentId,Integer userId);
   
    
    public List<File> getUserFiles(Integer uid , long parentId);
    
    
    public File getFileByFid(long fid);
    
    
    public List<File> getRepeatFileByFname(long parentId,Integer userId,String fname);
    
    
    public void deleteFile(long fid);
    
    
    public long saveDir(long parentId,Integer userId,String fname);
    
    
    public List<File> getRepeatDirFname(long parentId,Integer userId,String fname);
    
    
    public List<File> getChildren(long parentId,Integer userId);
    
    
    
    public List<File> getClassifyedFiles(Integer userId,Integer fidCid);
    
    
    public List<File> getFilesByKey(Integer userId,String key);

	public void updateShare(Long fid, String address, String password);

	public File getFileByAddress(String address);

	 

	/**
	* 
	*@param dir
	*@return 
	*/
	long mkdirs(String dir);

	/**
	* 
	*@param string
	*@param fname
	*@param suffix
	*@param size
	*@param pid
	*@param i
	*@param preview 
	*/
	public void upLoadFile(String  path ,String name,String extName,Long size,long parentId,Integer userId, String preview);

	/**
	* 
	*@param dir
	*@param parentId
	*@param userId
	*@return 
	*/
	long mkdirs(String dir, long parentId, Integer userId);
}
