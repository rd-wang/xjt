package com.catsic.biz.yh.bean;

import com.catsic.core.bean.Tfile;

import net.tsz.afinal.annotation.sqlite.Id;

import java.util.List;

public class YhXcjl {
	@Id
	private java.lang.String crowid;
	private java.lang.String xzqh;
	private java.lang.String lxbm;
	private java.lang.String lxmc;
	private java.lang.String xcsjStart;
	private java.lang.String xcsjEnd;
	private java.lang.String tbsj;
	private java.lang.String weather;
	private java.lang.String fzr;
	private java.lang.String jlr;
	private java.lang.String xcry;
	private java.lang.String fxwt;
	private java.lang.String clyj;
	private java.lang.String cljg;
	private java.lang.String dwdm;
	private java.lang.String dwmc;
	private java.lang.String shbz;
	private java.lang.String mkType;
	private List<Tfile> files ;

	public String getCrowid() {
		return crowid;
	}

	public void setCrowid(String crowid) {
		this.crowid = crowid;
	}

	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	public String getLxbm() {
		return lxbm;
	}

	public void setLxbm(String lxbm) {
		this.lxbm = lxbm;
	}

	public String getLxmc() {
		return lxmc;
	}

	public void setLxmc(String lxmc) {
		this.lxmc = lxmc;
	}

	public String getXcsjStart() {
		return xcsjStart;
	}

	public void setXcsjStart(String xcsjStart) {
		this.xcsjStart = xcsjStart;
	}

	public String getXcsjEnd() {
		return xcsjEnd;
	}

	public void setXcsjEnd(String xcsjEnd) {
		this.xcsjEnd = xcsjEnd;
	}

	public String getTbsj() {
		return tbsj;
	}

	public void setTbsj(String tbsj) {
		this.tbsj = tbsj;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getFzr() {
		return fzr;
	}

	public void setFzr(String fzr) {
		this.fzr = fzr;
	}

	public String getJlr() {
		return jlr;
	}

	public void setJlr(String jlr) {
		this.jlr = jlr;
	}

	public String getXcry() {
		return xcry;
	}

	public void setXcry(String xcry) {
		this.xcry = xcry;
	}

	public String getFxwt() {
		return fxwt;
	}

	public void setFxwt(String fxwt) {
		this.fxwt = fxwt;
	}

	public String getClyj() {
		return clyj;
	}

	public void setClyj(String clyj) {
		this.clyj = clyj;
	}

	public String getCljg() {
		return cljg;
	}

	public void setCljg(String cljg) {
		this.cljg = cljg;
	}

	public String getDwdm() {
		return dwdm;
	}

	public void setDwdm(String dwdm) {
		this.dwdm = dwdm;
	}

	public String getDwmc() {
		return dwmc;
	}

	public void setDwmc(String dwmc) {
		this.dwmc = dwmc;
	}

	public String getShbz() {
		return shbz;
	}

	public void setShbz(String shbz) {
		this.shbz = shbz;
	}

	public String getMkType() {
		return mkType;
	}

	public void setMkType(String mkType) {
		this.mkType = mkType;
	}

	public List<Tfile> getFiles() {
		return files;
	}

	public void setFiles(List<Tfile> files) {
		this.files = files;
	}
}

