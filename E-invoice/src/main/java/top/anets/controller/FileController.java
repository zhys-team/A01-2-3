package top.anets.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.RecursiveAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
//import org.apache.zookeeper.Op.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import top.anets.entity.File;
import  top.anets.utils.Result;
import top.anets.service.FileService;
//import top.anets.utils.FastDFSClient;
import top.anets.utils.ShortUrlUtils;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
//	@Value("${IMAGE_SERVER_URL}")
//	private String IMAGE_SERVER_URL;
//	@RequestMapping("/file/upload")
//	@ResponseBody
//    public Result fileUpload(MultipartFile file,HttpServletRequest request){
//		String path=null;
//		FastDFSClient client=null;
//		try {
//			 client = new FastDFSClient("classpath:conf/client.conf");
//			String  originalFilename= file.getOriginalFilename();
//			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
//			
//			long parentId = Long.parseLong(request.getParameter("parentId"));
//			int userId = Integer.parseInt(request.getParameter("userId"));
//			//check if it is repeated
//			List<File> list = fileService.getRepeatFileByFname(parentId, userId, originalFilename);
//			
//			if(list!=null&&list.size()>0){
//				for (File filer : list) {
//					//delete repeat files
//					System.out.println("repeated files,deleting...:"+filer.getFid());
//					Boolean deleteFile = client.deleteFile(filer.getAddress());
//					//delete data in database
//					fileService.deleteFile(filer.getFid());
//					if(!deleteFile){
//						return new Result(false,"500","替换时发生错误", null);
//					}
//				}
//				//replace
//				path = client.uploadFile(file.getBytes(), extName);
//				if(StringUtils.isNotBlank(path)){
//					fileService.upLoadFile(path, originalFilename, extName, file.getSize(),parentId,userId);
//				}
//			}else {
//				path = client.uploadFile(file.getBytes(), extName);
//				if(StringUtils.isNoneBlank(path)){
//					fileService.upLoadFile(path, originalFilename, extName, file.getSize(),parentId,userId);
//				}
//			}
//			
//		} catch (Exception e) {
//			try {
//				if(path!=null&&client!=null){
//					Boolean deleteFile = client.deleteFile(path);
//					System.out.println("出错！已经删除源文件");
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//			return new Result(false,"500", e.getMessage(), null);
//		}
//    	return Result.success();
//    }
//	
	
	
//	@RequestMapping("/file/uploadDir")
//	@ResponseBody
//    public Result fileUploadDir(MultipartFile file,HttpServletRequest request){
//		FastDFSClient client =null;
//		String  filepath=null;
//		try {
//			client = new FastDFSClient("classpath:conf/client.conf");
//			String  filename= file.getOriginalFilename();
//			String extName = filename.substring(filename.lastIndexOf(".")+1);
//			
//			long parentId = Long.parseLong(request.getParameter("parentId"));
//			int userId = Integer.parseInt(request.getParameter("userId"));
//			String path = request.getParameter("webkitRelativePath");
//			
//			System.out.println("filename:"+filename);
//			System.out.println("path:"+path);
//			
//			String nextpath=path;
//		    //解析路径
//		    while(nextpath.indexOf("/")!=-1){
//		    	String dir =nextpath.substring(0, nextpath.indexOf("/"));
////		    	Check if  it is repeated
//		    	List<File> repeatedDirs = fileService.getRepeatDirFname(parentId, userId,dir);
//		    	if(repeatedDirs!=null&&repeatedDirs.size()>0){
//		    		parentId=repeatedDirs.get(0).getFid();
//		    	}else{
// //		    	create directory
//			    	parentId = fileService.saveDir(parentId, userId, dir);
//		    	}
//		    	nextpath= nextpath.substring(nextpath.indexOf("/")+1);
//		    }
//		    
//		    //文件上传
//		  //check if it is repeated
//			List<File> list = fileService.getRepeatFileByFname(parentId, userId, filename);
//			
//			if(list!=null&&list.size()>0){
//				for (File filer : list) {
//					//delete repeat files
//					System.out.println("repeated files,deleting...:"+filer.getFid());
//					Boolean deleteFile = client.deleteFile(filer.getAddress());
//					//delete data in database
//					fileService.deleteFile(filer.getFid());
//					if(!deleteFile){
//						return new Result(false,"500","替换时发生错误", null);
//					}
//				}
//				//replace
//				filepath = client.uploadFile(file.getBytes(), extName);
//				if(StringUtils.isNoneBlank(filepath)){
//					fileService.upLoadFile(filepath, filename, extName, file.getSize(),parentId,userId);
//				}
//				
//			}else {
//				filepath = client.uploadFile(file.getBytes(), extName);
//				if(StringUtils.isNoneBlank(filepath)){
//				    fileService.upLoadFile(filepath, filename, extName, file.getSize(),parentId,userId);
//				}
//			}
//		    
//			
//		} catch (Exception e) {
//			try {
//				if(filepath!=null&&client!=null){
//					Boolean deleteFile = client.deleteFile(filepath);
//					System.out.println("出错！已经删除源文件");
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			e.printStackTrace();
//			return new Result(false,"500", e.getMessage(), null);
//		}
//    	return Result.success();
//    }
	

	
	@RequestMapping("/file/list")
	@ResponseBody
	public Result getUserFiles(Integer uid,long parentId){
		List<File> list =null;
		try {
			list = fileService.getUserFiles(uid, parentId);
		} catch (Exception e) {
			return new Result(false, "500",e.getMessage(),null);
		}
		return new Result(true, "200", "", list);
	}
	
//	@RequestMapping("/file/delete")
//	@ResponseBody
//	public Result deleteFile(long fid){
//		File file= fileService.getFileByFid(fid);
//		FastDFSClient client;
//		try {
//			client = new FastDFSClient("classpath:conf/client.conf");
//			if(file!=null){
//				if(!StringUtils.isNoneBlank(file.getAddress())){
//					fileService.deleteFile(fid);
//					return new Result(true, "200", "", null);
//				}
//				Boolean deleteFile = client.deleteFile(file.getAddress());
//				if(deleteFile){
//					fileService.deleteFile(fid);
//					return new Result(true, "200", "", null);
//				}else{
//					return new Result(false, "500", "删除错误", null);
//				}
//				
//			}else{
//				return new Result(false, "500", "已经删除", null);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return new Result(false, "500",e.getMessage(), null);
//		}
//		
//		
//	}
//	@RequestMapping("/dir/delete")
//	@ResponseBody
//	public Result deleteDir(long fid,Integer userId){
//		//get in floder
//		try {
//			List<File> list = fileService.getChildren(fid,userId);
//			FastDFSClient client = new FastDFSClient("classpath:conf/client.conf");
//			//递归删除文件夹里面的东西
//			this.Recursive(list, userId, client);
//			//删除完后，就可以删除本身了
//			fileService.deleteFile(fid);
//			return Result.success();
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			return new Result(false,"500",e.getMessage(), null);
//		}
//		
//	}
	
//	//递归删除文件夹
//    private void Recursive(List<File> list,Integer userId,FastDFSClient client) throws Exception{
//    	for (File file : list) {
//    		//对于每次循环 ,比如荡当前有   dir  rys.txt  
//    		if(file.getIsdir()==0){
//    			//是文件，删除文件
//    			Boolean deleteFile = client.deleteFile(file.getAddress());
//    			if(deleteFile){
//    				fileService.deleteFile(file.getFid());
//    			}
//    		}else{
//    			list = fileService.getChildren(file.getFid(),userId);
//    			//进行递归
//    			this.Recursive(list, userId,client);
//    			//文件夹里面的递归完了，就删除文件夹
//    			fileService.deleteFile(file.getFid());
//    		}
//		}
//	}
    
    
    @RequestMapping(value="/file/query")
    @ResponseBody
    public Result queryFiles(String key,Integer userId,Integer fidCid){
    	if(userId==null){
    		return new Result(false,"500", "没有用户id",null);
    	}
    	if(StringUtils.isNoneBlank(key)){
    		try {
				key=new String(key.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		List<File> list = fileService.getFilesByKey(userId, key);
    		return new Result(true,"200", "",list);
    	}
    	if(fidCid!=null){
    		List<File> list = fileService.getClassifyedFiles(userId, fidCid);
    		return new Result(true,"200", "",list);
    	}
    	List<File> files = fileService.getUserFiles(userId, 0);
    	return new Result(true,"200", "",files);
    	
    }
    
    //生成分享地址密码
    @RequestMapping("/file/share")
    @ResponseBody
    public Result share(Long fid,Integer uid){
    	String address=fid+""+uid;
    	String[] urls = ShortUrlUtils.toShortUrl(address);
    	Random random = new Random();
    	int index = random.nextInt(4);
    	address=urls[index];
    	if(index==0){
    	   index=random.nextInt(3)+1;
    	}else if(index==3){
    		index=random.nextInt(3);
    	}else{
    		index=index+1;
    	}
    	String password=urls[index].substring(0, 4);
    	
    	
    	HashMap<String, String> map = new HashMap<>();
    	
    	try {
    		fileService.updateShare(fid,address,password);
		} catch (Exception e) {
			return new Result(false,"500",e.getMessage(),null);
		}
    	
    	map.put("address",address);
    	map.put("password", password);
    	
    	return new Result(true,"200",null,map);
    }
    
  //根据分享查询文件
    @RequestMapping("/file/getShareFile")
    @ResponseBody
    public File getShareFile(HttpServletRequest request,String address){
    	File file=fileService.getFileByAddress(address);
    	if(file!=null&&request.getSession().getAttribute("fileAccess")!=null&&request.getSession().getAttribute("fileAccess").equals(file.getFid()+"")){
    		//说明已经访问过
    		File detailFile = fileService.getFileByFid(file.getFid());
    		return detailFile;
    	}else{
    		return file;
    	}
    	
    }
    
  //根据分享密码查询文件详情
    @RequestMapping("/file/getShareDetailFile")
    @ResponseBody
    public File getDetailFile(HttpServletRequest request,Integer fid,String password){
    	File file=fileService.getFileByFid(fid);
    	if(file!=null&&file.getSharepassword().equals(password)){
    		HttpSession session = request.getSession();
    		session.setAttribute("fileAccess",fid+"");
    		return file;
    	}else{
    		return null;
    	}
    	
    }
    
    @RequestMapping("/file/space")
    @ResponseBody
    public Result space() {
    	java.io.File[] roots =java.io.File.listRoots();
		double constm = 1024 * 1024 * 1024 ;
		double total = 0d;
		double free = 0d;
		for (java.io.File file : roots) {
		System.out.println(file.getPath());
		System.out.println("剩余空间 = " + doubleFormat(file.getFreeSpace()/constm)+" G");
		System.out.println("已使用空间 = " + doubleFormat(file.getUsableSpace()/constm)+" G");
		System.out.println(file.getPath()+"盘总大小 = " + doubleFormat(file.getTotalSpace()/constm)+" G");
		System.out.println();
		total += file.getTotalSpace();
		free+=file.getFreeSpace();
		}
		System.out.println("你的硬盘总大小 = "+doubleFormat(total/constm));
		System.out.println("剩余空间 = "+doubleFormat(free/constm));
		
		
		HashMap<String, String> map = new HashMap<>();
		map.put("total", doubleFormat(total/constm));
		map.put("free", doubleFormat(free/constm));
		return new Result(true,"200", "",map);
	}
	
	
		private static String doubleFormat(double d){
		DecimalFormat df = new DecimalFormat("0.##");
		return df.format(d);
		}
    
	
	
}