package top.anets.entity;

import top.anets.vo.User;

public class File {
    private Long fid;

    private Integer fidUid;

    private Integer fidCid;

    private String fname;

    private Long parentId;

    private Integer isdir;

    private String preview;

    private String address;

    private Long size;

    private String suffix;

    private String updatetime;

    private Integer ispublic;

    private Integer isshare;

    private String sharepassword;

    private String shareaddress;

    private String attr1;

    private String attr2;

    private String attr3;

    private String attr4;

    private String attr5;
    
    
  //多对一配置
    private User user;
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public File(){
    	
    }
    
    public File( Integer fidUid, Integer fidCid, String fname, Long parentId, Integer isdir, String address,
			Long size, String suffix, String updatetime, Integer ispublic, Integer isshare, String sharepassword,
			String shareaddress) {
		super();
		this.fidUid = fidUid;
		this.fidCid = fidCid;
		this.fname = fname;
		this.parentId = parentId;
		this.isdir = isdir;
		this.address = address;
		this.size = size;
		this.suffix = suffix;
		this.updatetime = updatetime;
		this.ispublic = ispublic;
		this.isshare = isshare;
		this.sharepassword = sharepassword;
		this.shareaddress = shareaddress;
	}


    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public Integer getFidUid() {
        return fidUid;
    }

    public void setFidUid(Integer fidUid) {
        this.fidUid = fidUid;
    }

    public Integer getFidCid() {
        return fidCid;
    }

    public void setFidCid(Integer fidCid) {
        this.fidCid = fidCid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname == null ? null : fname.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getIsdir() {
        return isdir;
    }

    public void setIsdir(Integer isdir) {
        this.isdir = isdir;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview == null ? null : preview.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix == null ? null : suffix.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIsshare() {
        return isshare;
    }

    public void setIsshare(Integer isshare) {
        this.isshare = isshare;
    }

    public String getSharepassword() {
        return sharepassword;
    }

    public void setSharepassword(String sharepassword) {
        this.sharepassword = sharepassword == null ? null : sharepassword.trim();
    }

    public String getShareaddress() {
        return shareaddress;
    }

    public void setShareaddress(String shareaddress) {
        this.shareaddress = shareaddress == null ? null : shareaddress.trim();
    }

    public String getAttr1() {
        return attr1;
    }

    public void setAttr1(String attr1) {
        this.attr1 = attr1 == null ? null : attr1.trim();
    }

    public String getAttr2() {
        return attr2;
    }

    public void setAttr2(String attr2) {
        this.attr2 = attr2 == null ? null : attr2.trim();
    }

    public String getAttr3() {
        return attr3;
    }

    public void setAttr3(String attr3) {
        this.attr3 = attr3 == null ? null : attr3.trim();
    }

    public String getAttr4() {
        return attr4;
    }

    public void setAttr4(String attr4) {
        this.attr4 = attr4 == null ? null : attr4.trim();
    }

    public String getAttr5() {
        return attr5;
    }

    public void setAttr5(String attr5) {
        this.attr5 = attr5 == null ? null : attr5.trim();
    }
}