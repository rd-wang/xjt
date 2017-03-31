package com.catsic.core.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**  
  * @Description:  数据字典项 
  * @author wuxianling  
  * @date 2014年9月18日 下午2:18:09    
  */ 
@Table(name = "CATSIC_CODES")
public class CatsicCode {

	@Id
	private String crowid;
	private String cname;
	private String cnameChs;
	private Integer clen;
	private String xxdm;
	private String xxdmhy;
	
	public String getCrowid() {
		return crowid;
	}

	public void setCrowid(String crowid) {
		this.crowid = crowid;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCnameChs() {
		return cnameChs;
	}

	public void setCnameChs(String cnameChs) {
		this.cnameChs = cnameChs;
	}

	public Integer getClen() {
		return clen;
	}

	public void setClen(Integer clen) {
		this.clen = clen;
	}

	public String getXxdm() {
		return xxdm;
	}

	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}

	public String getXxdmhy() {
		return xxdmhy;
	}

	public void setXxdmhy(String xxdmhy) {
		this.xxdmhy = xxdmhy;
	}
	
	@Override
	public String toString() {
		if (xxdmhy!=null) {
			return xxdmhy;
		}
		return "";
	}

}
