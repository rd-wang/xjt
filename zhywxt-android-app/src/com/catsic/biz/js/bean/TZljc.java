/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.js.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
  * @ClassName: TZljc
  * @Description: 自量检测
  * @author catsic-wuxianling
  * @date 2015年8月18日 下午5:02:01
  */
@Table(name = "T_JS_ZLJC")
public class TZljc implements Serializable {
	
	@Id
	private java.lang.String crowid;
	private java.lang.String xmid;
	private java.lang.String jcdw;
	private java.lang.String jcr;
	private java.lang.String jsr;
	private java.lang.String fhr;
	private java.lang.String syr;
	private java.util.Date jcsj;
	private java.lang.String jclb;
	private java.lang.String result;
	private java.lang.String tbdw;
	private java.lang.String tbr;
	private java.util.Date tbsj;
	private java.util.Date syrq;
	private java.lang.String shbz;//移动端上报
	
	private TZljcWccs tZljcWccs;
	private TZljcYsd tZljcYsd;

	public TZljc(){
	}

	public TZljc(java.lang.String crowid){
		this.crowid = crowid;
	}

	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public java.lang.String getXmid() {
		return this.xmid;
	}
	
	public void setXmid(java.lang.String value) {
		this.xmid = value;
	}
	
	public java.lang.String getJcdw() {
		return this.jcdw;
	}
	
	public void setJcdw(java.lang.String value) {
		this.jcdw = value;
	}
	
	public java.lang.String getJcr() {
		return this.jcr;
	}
	
	public void setJcr(java.lang.String value) {
		this.jcr = value;
	}
	
	public java.util.Date getJcsj() {
		return this.jcsj;
	}
	
	public void setJcsj(java.util.Date value) {
		this.jcsj = value;
	}
	
	public java.lang.String getJclb() {
		return this.jclb;
	}
	
	public void setJclb(java.lang.String value) {
		this.jclb = value;
	}
	
	public java.lang.String getResult() {
		return result;
	}
	
	public void setResult(java.lang.String result) {
		this.result = result;
	}
	
	public java.lang.String getTbdw() {
		return this.tbdw;
	}
	
	public void setTbdw(java.lang.String value) {
		this.tbdw = value;
	}
	
	public java.lang.String getTbr() {
		return this.tbr;
	}
	
	public void setTbr(java.lang.String value) {
		this.tbr = value;
	}
	
	public java.util.Date getTbsj() {
		return this.tbsj;
	}
	
	public void setTbsj(java.util.Date value) {
		this.tbsj = value;
	}
	
	public java.lang.String getJsr() {
		return jsr;
	}

	public void setJsr(java.lang.String jsr) {
		this.jsr = jsr;
	}

	public java.lang.String getFhr() {
		return fhr;
	}

	public void setFhr(java.lang.String fhr) {
		this.fhr = fhr;
	}
	
	public java.lang.String getSyr() {
		return syr;
	}

	public void setSyr(java.lang.String syr) {
		this.syr = syr;
	}

	public java.util.Date getSyrq() {
		return syrq;
	}

	public void setSyrq(java.util.Date syrq) {
		this.syrq = syrq;
	}
	
	public void setShbz(java.lang.String shbz) {
		this.shbz = shbz;
	}
	
	public java.lang.String getShbz() {
		return shbz;
	}
	
	public void setTZljcWccs(TZljcWccs tZljcWccs) {
		this.tZljcWccs = tZljcWccs;
	}
	
	public TZljcWccs getTZljcWccs() {
		return tZljcWccs;
	}
	
	public void setTZljcYsd(TZljcYsd tZljcYsd) {
		this.tZljcYsd = tZljcYsd;
	}
	
	public TZljcYsd getTZljcYsd() {
		return tZljcYsd;
	}
}




