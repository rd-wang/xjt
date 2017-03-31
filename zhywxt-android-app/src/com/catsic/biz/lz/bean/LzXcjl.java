package com.catsic.biz.lz.bean;

import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;
import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.catsic.core.bean.Tfile;
import com.google.gson.annotations.SerializedName;

@Table(name="T_LZ_XCJL")
public class LzXcjl implements Parcelable{
	@Id
	private java.lang.String crowid;
	private java.lang.String xzqh;
	private java.lang.String lxbm;
	private java.lang.String lxmc;
	@SerializedName(value="xcsjStart")
	private java.lang.String xcsjStart;
	@SerializedName(value="xcsjEnd")
	private java.lang.String xcsjEnd;
	@SerializedName(value="tbsj")
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
	@Transient
	private List<Tfile> files ;

	public java.lang.String getCrowid() {
		return crowid;
	}

	public void setCrowid(java.lang.String crowid) {
		this.crowid = crowid;
	}

	public java.lang.String getXzqh() {
		return xzqh;
	}

	public void setXzqh(java.lang.String xzqh) {
		this.xzqh = xzqh;
	}

	public java.lang.String getLxbm() {
		return lxbm;
	}

	public void setLxbm(java.lang.String lxbm) {
		this.lxbm = lxbm;
	}

	public java.lang.String getLxmc() {
		return lxmc;
	}

	public void setLxmc(java.lang.String lxmc) {
		this.lxmc = lxmc;
	}



	public java.lang.String getXcsjStart() {
		return xcsjStart;
	}

	public void setXcsjStart(java.lang.String xcsjStart) {
		this.xcsjStart = xcsjStart;
	}

	public java.lang.String getXcsjEnd() {
		return xcsjEnd;
	}

	public void setXcsjEnd(java.lang.String xcsjEnd) {
		this.xcsjEnd = xcsjEnd;
	}

	public java.lang.String getWeather() {
		return weather;
	}

	public void setWeather(java.lang.String weather) {
		this.weather = weather;
	}

	public java.lang.String getFzr() {
		return fzr;
	}

	public void setFzr(java.lang.String fzr) {
		this.fzr = fzr;
	}

	public java.lang.String getJlr() {
		return jlr;
	}

	public void setJlr(java.lang.String jlr) {
		this.jlr = jlr;
	}

	public java.lang.String getXcry() {
		return xcry;
	}

	public void setXcry(java.lang.String xcry) {
		this.xcry = xcry;
	}

	public java.lang.String getFxwt() {
		return fxwt;
	}

	public void setFxwt(java.lang.String fxwt) {
		this.fxwt = fxwt;
	}

	public java.lang.String getClyj() {
		return clyj;
	}

	public void setClyj(java.lang.String clyj) {
		this.clyj = clyj;
	}

	public java.lang.String getCljg() {
		return cljg;
	}

	public void setCljg(java.lang.String cljg) {
		this.cljg = cljg;
	}

	public java.lang.String getDwdm() {
		return dwdm;
	}

	public void setDwdm(java.lang.String dwdm) {
		this.dwdm = dwdm;
	}

	public java.lang.String getDwmc() {
		return dwmc;
	}

	public void setDwmc(java.lang.String dwmc) {
		this.dwmc = dwmc;
	}


	

	public java.lang.String getTbsj() {
		return tbsj;
	}

	public void setTbsj(java.lang.String tbsj) {
		this.tbsj = tbsj;
	}

	public java.lang.String getShbz() {
		return shbz;
	}

	public void setShbz(java.lang.String shbz) {
		this.shbz = shbz;
	}

	
	
	public List<Tfile> getFiles() {
		return files;
	}

	public void setFiles(List<Tfile> files) {
		this.files = files;
	}
	
	

	public java.lang.String getMkType() {
		return mkType;
	}

	public void setMkType(java.lang.String mkType) {
		this.mkType = mkType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}

	
}
