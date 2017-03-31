package com.catsic.core.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**  
  * @Description: 行政区划 
  * @author wuxianling  
  * @date 2014年9月18日 下午3:32:53    
  */ 
@Table(name = "T_XZQH")
public class T_XZQH implements java.io.Serializable {

	// Fields

	@Id
	private String xzqhdm;
	private String xzqhmc;
	private String sjdm;

	// Constructors

	/** default constructor */
	public T_XZQH() {
	}

	/** minimal constructor */
	public T_XZQH(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}

	/** full constructor */
	public T_XZQH(String xzqhdm, String xzqhmc, String sjdm) {
		this.xzqhdm = xzqhdm;
		this.xzqhmc = xzqhmc;
		this.sjdm = sjdm;
	}

	// Property accessors

	public String getXzqhdm() {
		return this.xzqhdm;
	}

	public void setXzqhdm(String xzqhdm) {
		this.xzqhdm = xzqhdm;
	}

	public String getXzqhmc() {
		return this.xzqhmc;
	}

	public void setXzqhmc(String xzqhmc) {
		this.xzqhmc = xzqhmc;
	}

	public String getSjdm() {
		return this.sjdm;
	}

	public void setSjdm(String sjdm) {
		this.sjdm = sjdm;
	}
	
	@Override
	public String toString() {
		if (xzqhmc!=null) {
			return xzqhmc;
		}
		return "";
	}

}