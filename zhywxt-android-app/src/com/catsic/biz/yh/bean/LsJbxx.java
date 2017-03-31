package com.catsic.biz.yh.bean;

import java.util.List;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;
import net.tsz.afinal.annotation.sqlite.Transient;
import android.os.Parcel;
import android.os.Parcelable;

import com.catsic.core.bean.Tfile;
import com.google.gson.annotations.SerializedName;

/**  
  * @Description: 路损基本信息 
  * @author wuxianling  
  * @date 2014年7月31日 上午10:32:44    
  */ 
@Table(name="T_M_LS")
public class LsJbxx implements Parcelable{
	
	@Id
	private String crowid;
	private String szdm;
	private String lxbm;
	private String lxmc;
	private float qdzh;
	private float zdzh;
	@SerializedName(value="fxsjString")
	private String fxsj;
	private String xcqk;
	private String czcs;
	private String xzqh;
	private String tbdwdm;
	private String tbdwmc;
	private String userid;
	private String username;
	@SerializedName(value="tbsjString")
	private String tbsj;
	private String shbz;
	
	@Transient
	private List<Tfile> files ;
	
	public static final Parcelable.Creator<LsJbxx> CREATOR = new Parcelable.Creator<LsJbxx>() {

			@Override
			public LsJbxx createFromParcel(Parcel source) {
				return new LsJbxx(source);
			}

			@Override
			public LsJbxx[] newArray(int size) {
				return new LsJbxx[size];
			}
		  };
	
	public LsJbxx(){}
	
	public LsJbxx(Parcel source) {
		this.szdm = source.readString();
		this.lxbm = source.readString();
		this.lxmc = source.readString();
		this.qdzh = source.readFloat();
		this.zdzh = source.readFloat();
		this.fxsj = source.readString();
		this.xcqk = source.readString();
		this.czcs = source.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
	}
	
	public String getSzdm() {
		return szdm;
	}

	public void setSzdm(String szdm) {
		this.szdm = szdm;
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

	public float getQdzh() {
		return qdzh;
	}

	public void setQdzh(float qdzh) {
		this.qdzh = qdzh;
	}

	public float getZdzh() {
		return zdzh;
	}

	public void setZdzh(float zdzh) {
		this.zdzh = zdzh;
	}

	public String getFxsj() {
		return fxsj;
	}

	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}

	public String getXcqk() {
		return xcqk;
	}

	public void setXcqk(String xcqk) {
		this.xcqk = xcqk;
	}

	public String getCzcs() {
		return czcs;
	}

	public void setCzcs(String czcs) {
		this.czcs = czcs;
	}

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

	public String getTbdwdm() {
		return tbdwdm;
	}

	public void setTbdwdm(String tbdwdm) {
		this.tbdwdm = tbdwdm;
	}

	public String getTbdwmc() {
		return tbdwmc;
	}

	public void setTbdwmc(String tbdwmc) {
		this.tbdwmc = tbdwmc;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTbsj() {
		return tbsj;
	}

	public void setTbsj(String tbsj) {
		this.tbsj = tbsj;
	}

	public String getShbz() {
		return shbz;
	}

	public void setShbz(String shbz) {
		this.shbz = shbz;
	}

	public List<Tfile> getFiles() {
		return files;
	}

	public void setFiles(List<Tfile> files) {
		this.files = files;
	}

}
