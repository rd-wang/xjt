/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.js.bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import com.catsic.core.bean.Tfile;
/**
  * @ClassName: TJsZlxc
  * @Description: 质量巡查
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午2:23:28
  */
@Table(name = "T_JS_ZLGL_ZLXC")
public class TJsZlxc implements java.io.Serializable {
	
	//columns START
	@Id
	private java.lang.String crowid;
	private java.lang.String xcdw;
	private java.lang.String xcr;
	private java.util.Date xcsj;
	private java.lang.String xcjg;
	private java.lang.String zgyj;
	private java.lang.String tbdw;
	private java.lang.String tbr;
	private java.util.Date tbsj;
	private java.lang.String xmid;
	//columns END
	
	private TJdXmjbxx  xmjbxx;
	
	private List<Tfile> files;
	
	private Set zlxcRespSet = new HashSet(0);


	public TJsZlxc(){
	}

	public TJsZlxc(java.lang.String crowid){
		this.crowid = crowid;
	}

	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public java.lang.String getXcdw() {
		return this.xcdw;
	}
	
	public void setXcdw(java.lang.String value) {
		this.xcdw = value;
	}
	
	public java.lang.String getXcr() {
		return this.xcr;
	}
	
	public void setXcr(java.lang.String value) {
		this.xcr = value;
	}
	
	public java.util.Date getXcsj() {
		return this.xcsj;
	}
	
	public void setXcsj(java.util.Date value) {
		this.xcsj = value;
	}
	
	public java.lang.String getXcjg() {
		return this.xcjg;
	}
	
	public void setXcjg(java.lang.String value) {
		this.xcjg = value;
	}
	
	public java.lang.String getZgyj() {
		return this.zgyj;
	}
	
	public void setZgyj(java.lang.String value) {
		this.zgyj = value;
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
	
	public java.lang.String getXmid() {
		return this.xmid;
	}
	
	public void setXmid(java.lang.String value) {
		this.xmid = value;
	}
	
	public void setXmjbxx(TJdXmjbxx xmjbxx) {
		this.xmjbxx = xmjbxx;
	}
	
	public TJdXmjbxx getXmjbxx() {
		return xmjbxx;
	}
	
	public void setFiles(List<Tfile> files) {
		this.files = files;
	}
	
	public List<Tfile> getFiles() {
		return files;
	}
	
	public void setZlxcRespSet(Set zlxcRespSet) {
		this.zlxcRespSet = zlxcRespSet;
	}
	
	public Set getZlxcRespSet() {
		return zlxcRespSet;
	}

}

