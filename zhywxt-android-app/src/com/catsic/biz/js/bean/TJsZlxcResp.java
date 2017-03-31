/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.js.bean;

import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import com.catsic.core.bean.Tfile;
/**
  * @ClassName: TJsZlxcResp
  * @Description: 质量巡查反馈
  * @author catsic-wuxianling
  * @date 2015年9月24日 下午2:23:39
  */
@Table(name = "T_JS_ZLGL_ZLXC_RESPONSE")
public class TJsZlxcResp implements java.io.Serializable {
	
	//columns START
	@Id
	private java.lang.String crowid;
	private java.lang.String fkdw;
	private java.lang.String fkr;
	private java.lang.String fksj;
	private java.lang.String tbdw;
	private java.lang.String tbr;
	private java.util.Date tbsj;
	private java.lang.String fkqk;
	private java.lang.String zlxcid;
	//columns END
	
	private TJsZlxc jsZljc;
	
	private List<Tfile> files;


	public TJsZlxcResp(){
	}

	public TJsZlxcResp(java.lang.String crowid){
		this.crowid = crowid;
	}

	public void setCrowid(java.lang.String value) {
		this.crowid = value;
	}
	
	public java.lang.String getCrowid() {
		return this.crowid;
	}
	
	public java.lang.String getFkdw() {
		return this.fkdw;
	}
	
	public void setFkdw(java.lang.String value) {
		this.fkdw = value;
	}
	
	public java.lang.String getFkr() {
		return this.fkr;
	}
	
	public void setFkr(java.lang.String value) {
		this.fkr = value;
	}
	
	public java.lang.String getFksj() {
		return this.fksj;
	}
	
	public void setFksj(java.lang.String value) {
		this.fksj = value;
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
	
	public java.lang.String getFkqk() {
		return this.fkqk;
	}
	
	public void setFkqk(java.lang.String value) {
		this.fkqk = value;
	}
	
	public java.lang.String getZlxcid() {
		return this.zlxcid;
	}
	
	public void setZlxcid(java.lang.String value) {
		this.zlxcid = value;
	}
	
	public void setFiles(List<Tfile> files) {
		this.files = files;
	}
	public List<Tfile> getFiles() {
		return files;
	}
	
	public void setJsZljc(TJsZlxc jsZljc) {
		this.jsZljc = jsZljc;
	}
	
	public TJsZlxc getJsZljc() {
		return jsZljc;
	}

}

