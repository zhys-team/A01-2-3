package top.anets.entity;

import java.util.List;

public class SysUsers {
    private Integer id;

    private String name;

    private String password;

    private String no;

    private String openid;

    private String state;

    private String orgid;

    private String otherid;
    
    
    private List<String> businesses;
    private List<Business>  orgs ;
    
    

    public List<Business> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Business> orgs) {
		this.orgs = orgs;
	}

	@Override
	public String toString() {
		return "SysUsers [id=" + id + ", name=" + name + ", password=" + password + ", no=" + no + ", openid=" + openid
				+ ", state=" + state + ", orgid=" + orgid + ", otherid=" + otherid + ", businesses=" + businesses + "]";
	}

	public List<String> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(List<String> businesses) {
		this.businesses = businesses;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no == null ? null : no.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

    public String getOtherid() {
        return otherid;
    }

    public void setOtherid(String otherid) {
        this.otherid = otherid == null ? null : otherid.trim();
    }
}