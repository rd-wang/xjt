package com.catsic.biz.js.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * @ClassName: ZljcYsd
 * @Description: 质量检测-压实度
 * @author Comsys-wuxianling
 * @date 2015年5月19日 上午11:11:08
 */
@Table(name="T_JS_ZLJC_YSD")
public class TZljcYsd implements java.io.Serializable {

	@Id
	private java.lang.String crowid;
	private java.lang.String parentid;
	private java.lang.String jcld;
	private java.lang.String qywz;
	private Double sksd;
	private Double gstYylsz;
	private Double ztjccmhsz;
	private Double gstSylsz;
	private Double skhsz;
	private Double lsmd;
	private Double skrj;
	private Double ssyz;
	private Double sysmd;
	private java.lang.String hh;
	private Double hz;
	private Double hzSsyz;
	private Double hzGsyz;
	private Double sz;
	private Double gsyz;
	private Double syhsl;
	private Double pjhsl;
	private Double zjhsl;
	private Double sygmd;
	private Double zdgmd;
	private Double ysd;
	private Double ysdsjz;

	public java.lang.String getCrowid() {
		return crowid;
	}

	public void setCrowid(java.lang.String crowid) {
		this.crowid = crowid;
	}

	public void setParentid(java.lang.String parentid) {
		this.parentid = parentid;
	}
	
	public java.lang.String getParentid() {
		return parentid;
	}

	public java.lang.String getJcld() {
		return jcld;
	}

	public void setJcld(java.lang.String jcld) {
		this.jcld = jcld;
	}

	public java.lang.String getQywz() {
		return qywz;
	}

	public void setQywz(java.lang.String qywz) {
		this.qywz = qywz;
	}

	public Double getSksd() {
		return sksd;
	}

	public void setSksd(Double sksd) {
		this.sksd = sksd;
	}

	public Double getGstYylsz() {
		return gstYylsz;
	}

	public void setGstYylsz(Double gstYylsz) {
		this.gstYylsz = gstYylsz;
	}

	public Double getZtjccmhsz() {
		return ztjccmhsz;
	}

	public void setZtjccmhsz(Double ztjccmhsz) {
		this.ztjccmhsz = ztjccmhsz;
	}

	public Double getGstSylsz() {
		return gstSylsz;
	}

	public void setGstSylsz(Double gstSylsz) {
		this.gstSylsz = gstSylsz;
	}

	public Double getSkhsz() {
		return skhsz;
	}

	public void setSkhsz(Double skhsz) {
		this.skhsz = skhsz;
	}

	public Double getLsmd() {
		return lsmd;
	}

	public void setLsmd(Double lsmd) {
		this.lsmd = lsmd;
	}

	public Double getSkrj() {
		return skrj;
	}

	public void setSkrj(Double skrj) {
		this.skrj = skrj;
	}

	public Double getSsyz() {
		return ssyz;
	}

	public void setSsyz(Double ssyz) {
		this.ssyz = ssyz;
	}

	public Double getSysmd() {
		return sysmd;
	}

	public void setSysmd(Double sysmd) {
		this.sysmd = sysmd;
	}

	public java.lang.String getHh() {
		return hh;
	}

	public void setHh(java.lang.String hh) {
		this.hh = hh;
	}

	public Double getHz() {
		return hz;
	}

	public void setHz(Double hz) {
		this.hz = hz;
	}

	public Double getHzSsyz() {
		return hzSsyz;
	}

	public void setHzSsyz(Double hzSsyz) {
		this.hzSsyz = hzSsyz;
	}

	public Double getHzGsyz() {
		return hzGsyz;
	}

	public void setHzGsyz(Double hzGsyz) {
		this.hzGsyz = hzGsyz;
	}

	public Double getSz() {
		return sz;
	}

	public void setSz(Double sz) {
		this.sz = sz;
	}

	public Double getGsyz() {
		return gsyz;
	}

	public void setGsyz(Double gsyz) {
		this.gsyz = gsyz;
	}

	public Double getSyhsl() {
		return syhsl;
	}

	public void setSyhsl(Double syhsl) {
		this.syhsl = syhsl;
	}

	public Double getPjhsl() {
		return pjhsl;
	}

	public void setPjhsl(Double pjhsl) {
		this.pjhsl = pjhsl;
	}

	public Double getZjhsl() {
		return zjhsl;
	}

	public void setZjhsl(Double zjhsl) {
		this.zjhsl = zjhsl;
	}

	public Double getSygmd() {
		return sygmd;
	}

	public void setSygmd(Double sygmd) {
		this.sygmd = sygmd;
	}

	public Double getZdgmd() {
		return zdgmd;
	}

	public void setZdgmd(Double zdgmd) {
		this.zdgmd = zdgmd;
	}

	public Double getYsd() {
		return ysd;
	}

	public void setYsd(Double ysd) {
		this.ysd = ysd;
	}

	public void setYsdsjz(Double ysdsjz) {
		this.ysdsjz = ysdsjz;
	}
	
	public Double getYsdsjz() {
		return ysdsjz;
	}
}
