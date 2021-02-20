package top.anets.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.anets.mapper.FileMapper;
import top.anets.service.FileService;
import top.anets.entity.File;
import top.anets.entity.FileExample;
import top.anets.entity.FileExample.Criteria;

@Service
public class FileServiceImpl implements FileService{
    @Autowired
    private FileMapper fileMapper;
	@Override
	public Boolean upLoadFile(String path, String name, String extName, Long size, long parentId, Integer userId) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//to judge the file type
		System.out.println("suffix"+extName);
		Integer fidCid;
		if("gif,jpg,bmp,png,psd,ico".indexOf(extName)!=-1){
			fidCid=3;
		}else if("rar,7z,zip".indexOf(extName)!=-1){
			fidCid=7;
		}else if("exe,apk".indexOf(extName)!=-1){
			fidCid=6;
		}else if("avi,rmvb,3gp,flv,mp4".indexOf(extName)!=-1){
			fidCid=1;
		}else if("mp3,wav,krc,lrc".indexOf(extName)!=-1){
			fidCid=2;
		}else if("doc,excel,ppt,pptx,pdf,chm,txt,md,docx,xls,xlsx,html,css,js,java".indexOf(extName)!=-1){
			fidCid=5;
		}else if("torrent".indexOf(extName)!=-1){
			fidCid=4;
		}else{
			fidCid=8;
		}
		
		File file =new File(userId, fidCid, name, parentId, 0,path, size, extName, simpleDateFormat.format(new Date()), 1, 0, null, null);
		fileMapper.insert(file);
		return true;
	}
	@Override
	public List<File> getUserFiles(Integer uid, long parentId) {
		FileExample example = new FileExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		createCriteria.andFidUidEqualTo(uid);
		List<File> list = fileMapper.selectByExample(example);
		return list;
	}
	@Override
	public List<File> getRepeatFileByFname(long parentId, Integer userId, String fname) {
		FileExample example = new FileExample();
		Criteria criteria = example.createCriteria();
		criteria.andFidUidEqualTo(userId);
		criteria.andParentIdEqualTo(parentId);
		criteria.andFnameEqualTo(fname);
		criteria.andIsdirEqualTo(0);
		List<File> list = fileMapper.selectByExample(example);
		return list;
	}
	@Override
	public void deleteFile(long fid) {
		fileMapper.deleteByPrimaryKey(fid);
		
	}
	@Override
	public long saveDir(long parentId, Integer userId, String fname) {
		File file = new File();
		file.setParentId(parentId);
		file.setFidUid(userId);
		file.setFname(fname);
		file.setFidCid(9);
		file.setIsdir(1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = format.format(new Date());
		file.setUpdatetime(time);
		fileMapper.insert(file);
		System.out.println("==================返回的主键:"+file.getFid());
		return file.getFid();
	}
	@Override
	public List<File> getRepeatDirFname(long parentId, Integer userId, String fname) {
		FileExample example = new FileExample();
		Criteria criteria = example.createCriteria();
		criteria.andFidUidEqualTo(userId);
		criteria.andParentIdEqualTo(parentId);
		criteria.andFnameEqualTo(fname);
		criteria.andIsdirEqualTo(1);
		List<File> list = fileMapper.selectByExample(example);
		return list;
		
	}
	@Override
	public File getFileByFid(long fid) {
		
//		File file = fileMapper.getFileAndUserInfoByFid(fid);
		File file = fileMapper.selectByPrimaryKey(fid);
		return file;
	}
	@Override
	public List<File> getChildren(long parentId, Integer userId) {
		FileExample example = new FileExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		createCriteria.andFidUidEqualTo(userId);
		List<File> list = fileMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public List<File> getClassifyedFiles(Integer userId, Integer fidCid) {
		FileExample example = new FileExample();
		Criteria criteria = example.createCriteria();
		criteria.andFidUidEqualTo(userId);
		criteria.andFidCidEqualTo(fidCid);
		return fileMapper.selectByExample(example);
	}
	
	@Override
	public List<File> getFilesByKey(Integer userId, String key) {
		FileExample example = new FileExample();
		Criteria criteria = example.createCriteria();
		criteria.andFidUidEqualTo(userId);
		criteria.andFnameLike("%"+key+"%");
		return fileMapper.selectByExample(example);
	}
	
	@Override
	public void updateShare(Long fid, String address, String password) {
		File file = fileMapper.selectByPrimaryKey(fid);
		file.setIspublic(1);
		file.setIsshare(1);
		file.setShareaddress(address);
		file.setSharepassword(password);
		fileMapper.updateByPrimaryKey(file);
	}
	@Override
	public File getFileByAddress(String address) {
FileExample example = new FileExample();
example.createCriteria().andShareaddressEqualTo(address);
		//		File file = fileMapper.getFileAndUserInfo(address);
		List<File> list = fileMapper.selectByExample(example );
		
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else {
			return null;
		}
		
	}
	@Override
	public Long upLoadFileAndReturnFid(String path, String name, String extName, Long size, long parentId,
			Integer userId) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//to judge the file type
		System.out.println("suffix"+extName);
		Integer fidCid;
		if("gif,jpg,bmp,png,psd,ico".indexOf(extName)!=-1){
			fidCid=3;
		}else if("rar,7z,zip".indexOf(extName)!=-1){
			fidCid=7;
		}else if("exe,apk".indexOf(extName)!=-1){
			fidCid=6;
		}else if("avi,rmvb,3gp,flv,mp4".indexOf(extName)!=-1){
			fidCid=1;
		}else if("mp3,wav,krc,lrc".indexOf(extName)!=-1){
			fidCid=2;
		}else if("doc,excel,ppt,pptx,pdf,chm,txt,md,docx,xls,xlsx,html,css,js,java".indexOf(extName)!=-1){
			fidCid=5;
		}else if("torrent".indexOf(extName)!=-1){
			fidCid=4;
		}else{
			fidCid=8;
		}
		
		File file =new File(userId, fidCid, name, parentId, 0,path, size, extName, simpleDateFormat.format(new Date()), 1, 0, null, null);
		fileMapper.insert(file);
		return file.getFid();
	}
	
	@Override
	public long mkdirs(String dir ) {
		return this.mkdirs(dir, 0,null);
	}
	@Override
	public long mkdirs(String dir, long parentId,Integer userId) {  
		
		System.out.println("当前路径:"+dir);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updatetime = format.format(new Date());
//		File file =new File(userId, null, name, parentId, 0,null, null,null, simpleDateFormat.format(new Date()), 1, 0, null, null);
		
		String[] split = dir.split("(\\\\+|/+|,+|\\.+)");
		ArrayList<String> list = new ArrayList<>();
		for (String string : split) {
			
			if(StringUtils.isNoneBlank(string)) {
				System.out.println("拆分:"+string);
				 list.add(string);
			}
		}
		String current = null;
		String next ="";
		for (int i = 0; i < list.size(); i++) {
			  current = list.get(0);
			  if(i!=0) {
				  next+=list.get(i)+',';
			  }
		}
		System.out.println("当前:"+current);
		System.out.println("next:"+next);
		 
        FileExample example = new FileExample();
        example.createCriteria().andParentIdEqualTo(parentId).andFnameEqualTo(current);
		List<File> byExample = fileMapper.selectByExample(example );
		if(byExample!=null&&byExample.size()>0&&byExample.get(0).getIsdir()==1) {
			if(StringUtils.isNotBlank(next)) {
				return this.mkdirs(next, byExample.get(0).getFid(),userId);
			}else {
				return byExample.get(0).getFid();
			}
		}else {
            File record = new File(userId, null, current, parentId, 1, null, null, null, updatetime, null, null, null,null);
			//如果没有找到，就需要创建目录 
			fileMapper.insertSelective(record); 
			if(StringUtils.isNotBlank(next)) {
				return this.mkdirs(next, record.getFid(),userId);
			}else {
				return record.getFid();
			}
			
		} 
	}
	
	public static void main(String[] args) {
		
		String  dir = "3344\\55553\\\\我们";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String updatetime = format.format(new Date());
//		File file =new File(userId, null, name, parentId, 0,null, null,null, simpleDateFormat.format(new Date()), 1, 0, null, null);
		
		String[] split = dir.split("(\\\\+|/+|,+|\\.+)");
		ArrayList<String> list = new ArrayList<>();
		for (String string : split) {
			System.out.println(string);
		}
	}
	@Override
	public void upLoadFile(String path, String name, String extName, Long size, long parentId, Integer userId,
			String preview) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//to judge the file type
		System.out.println("suffix"+extName);
		Integer fidCid;
		if("gif,jpg,bmp,png,psd,ico".indexOf(extName)!=-1){
			fidCid=3;
		}else if("rar,7z,zip".indexOf(extName)!=-1){
			fidCid=7;
		}else if("exe,apk".indexOf(extName)!=-1){
			fidCid=6;
		}else if("avi,rmvb,3gp,flv,mp4".indexOf(extName)!=-1){
			fidCid=1;
		}else if("mp3,wav,krc,lrc".indexOf(extName)!=-1){
			fidCid=2;
		}else if("doc,excel,ppt,pptx,pdf,chm,txt,md,docx,xls,xlsx,html,css,js,java".indexOf(extName)!=-1){
			fidCid=5;
		}else if("torrent".indexOf(extName)!=-1){
			fidCid=4;
		}else{
			fidCid=8;
		}
		
		File file =new File(userId, fidCid, name, parentId, 0,path, size, extName, simpleDateFormat.format(new Date()), 1, 0, null, null);
		file.setPreview(preview);
		fileMapper.insert(file);
	}
	 

}
