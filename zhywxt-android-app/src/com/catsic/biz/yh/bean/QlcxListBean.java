package com.catsic.biz.yh.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.catsic.core.bean.Tfile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Litao-pc on 2016/4/27.
 * @param 桥梁检测表模型
 */
public class QlcxListBean implements Parcelable {


    public String getXmmsInfo() {
        return xmmsInfo;
    }

    public void setXmmsInfo(String xmmsInfo) {
        this.xmmsInfo = xmmsInfo;
    }

    /**
     * crowid : e65f1d94-5e51-49ae-b3b9-8d4476bd6f71
     * fzr : 大明
     * jcnx : 2015
     * jcrq : 2016-04-24
     * jlr : 小明
     * lxbm : C844410323
     * lxmc : 西河-石板洼
     * parentCrowid : d8fbe7f7-5c6a-4938-9566-9cf1ee694dfd
     * qlbm : qlbm001
     * qlcd : 200
     * qljcmxSet : [{"bycsyj":"桥面修复","crowid":"685f7bbb-10a7-46b0-ae6e-468e21f4b4b8","emptyOrNull":false,"jcbj":"1","qljcCrowid":"e65f1d94-5e51-49ae-b3b9-8d4476bd6f71","qsfw":"20米","qslx":"桥面受损"},{"bycsyj":"桥墩修复","crowid":"c73b0e10-7e95-40ba-bc07-fb56cd376dce","emptyOrNull":false,"jcbj":"2","qljcCrowid":"e65f1d94-5e51-49ae-b3b9-8d4476bd6f71","qsfw":"1个","qslx":"桥墩受损"}]
     * qlmc : 桥梁卡片测试1
     * qmqk : 8
     * sjhz : 2
     * tbdwdm : 411031000
     * txzz : 32
     * zxzh : 1.273
     */
    private String xmmsInfo;
    private String crowid;
    private String fzr;
    private String jcnx;
    private String jcrq;
    private String jlr;
    private String lxbm;
    private String lxmc;
    private String parentCrowid;
    private String qlbm;
    private String qlcd;
    private String qlmc;
    private int qmqk;
    private String sjhz;
    private String tbdwdm;
    private int txzz;
    private double zxzh;
    private List<Tfile> files;

    public List<Tfile> getFiles() {
        return files;
    }

    public void setFiles(List<Tfile> files) {
        this.files = files;
    }

    /**
     * bycsyj : 桥面修复
     * crowid : 685f7bbb-10a7-46b0-ae6e-468e21f4b4b8
     * emptyOrNull : false
     * jcbj : 1
     * qljcCrowid : e65f1d94-5e51-49ae-b3b9-8d4476bd6f71
     * qsfw : 20米
     * qslx : 桥面受损
     */

    private List<QljcmxSetBean> qljcmxSet;

    public String getCrowid() {
        return crowid;
    }

    public void setCrowid(String crowid) {
        this.crowid = crowid;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getJcnx() {
        return jcnx;
    }

    public void setJcnx(String jcnx) {
        this.jcnx = jcnx;
    }

    public String getJcrq() {
        return jcrq;
    }

    public void setJcrq(String jcrq) {
        this.jcrq = jcrq;
    }

    public String getJlr() {
        return jlr;
    }

    public void setJlr(String jlr) {
        this.jlr = jlr;
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

    public String getParentCrowid() {
        return parentCrowid;
    }

    public void setParentCrowid(String parentCrowid) {
        this.parentCrowid = parentCrowid;
    }

    public String getQlbm() {
        return qlbm;
    }

    public void setQlbm(String qlbm) {
        this.qlbm = qlbm;
    }

    public String getQlcd() {
        return qlcd;
    }

    public void setQlcd(String qlcd) {
        this.qlcd = qlcd;
    }

    public String getQlmc() {
        return qlmc;
    }

    public void setQlmc(String qlmc) {
        this.qlmc = qlmc;
    }

    public int getQmqk() {
        return qmqk;
    }

    public void setQmqk(int qmqk) {
        this.qmqk = qmqk;
    }

    public String getSjhz() {
        return sjhz;
    }

    public void setSjhz(String sjhz) {
        this.sjhz = sjhz;
    }

    public String getTbdwdm() {
        return tbdwdm;
    }

    public void setTbdwdm(String tbdwdm) {
        this.tbdwdm = tbdwdm;
    }

    public int getTxzz() {
        return txzz;
    }

    public void setTxzz(int txzz) {
        this.txzz = txzz;
    }

    public double getZxzh() {
        return zxzh;
    }

    public void setZxzh(double zxzh) {
        this.zxzh = zxzh;
    }

    public List<QljcmxSetBean> getQljcmxSet() {
        return qljcmxSet;
    }

    public void setQljcmxSet(List<QljcmxSetBean> qljcmxSet) {
        this.qljcmxSet = qljcmxSet;
    }

    public static class QljcmxSetBean implements Parcelable {
        private String bycsyj;
        private String crowid;
        private boolean emptyOrNull;
        private String jcbj;
        private String qljcCrowid;
        private String qsfw;
        private String qslx;

        public String getBycsyj() {
            return bycsyj;
        }

        public void setBycsyj(String bycsyj) {
            this.bycsyj = bycsyj;
        }

        public String getCrowid() {
            return crowid;
        }

        public void setCrowid(String crowid) {
            this.crowid = crowid;
        }

        public boolean isEmptyOrNull() {
            return emptyOrNull;
        }

        public void setEmptyOrNull(boolean emptyOrNull) {
            this.emptyOrNull = emptyOrNull;
        }

        public String getJcbj() {
            return jcbj;
        }

        public void setJcbj(String jcbj) {
            this.jcbj = jcbj;
        }

        public String getQljcCrowid() {
            return qljcCrowid;
        }

        public void setQljcCrowid(String qljcCrowid) {
            this.qljcCrowid = qljcCrowid;
        }

        public String getQsfw() {
            return qsfw;
        }

        public void setQsfw(String qsfw) {
            this.qsfw = qsfw;
        }

        public String getQslx() {
            return qslx;
        }

        public void setQslx(String qslx) {
            this.qslx = qslx;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.bycsyj);
            dest.writeString(this.crowid);
            dest.writeByte(emptyOrNull ? (byte) 1 : (byte) 0);
            dest.writeString(this.jcbj);
            dest.writeString(this.qljcCrowid);
            dest.writeString(this.qsfw);
            dest.writeString(this.qslx);
        }

        public QljcmxSetBean() {
        }

        protected QljcmxSetBean(Parcel in) {
            this.bycsyj = in.readString();
            this.crowid = in.readString();
            this.emptyOrNull = in.readByte() != 0;
            this.jcbj = in.readString();
            this.qljcCrowid = in.readString();
            this.qsfw = in.readString();
            this.qslx = in.readString();
        }

        public static final Creator<QljcmxSetBean> CREATOR = new Creator<QljcmxSetBean>() {
            @Override
            public QljcmxSetBean createFromParcel(Parcel source) {
                return new QljcmxSetBean(source);
            }

            @Override
            public QljcmxSetBean[] newArray(int size) {
                return new QljcmxSetBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.crowid);
        dest.writeString(this.fzr);
        dest.writeString(this.jcnx);
        dest.writeString(this.jcrq);
        dest.writeString(this.jlr);
        dest.writeString(this.lxbm);
        dest.writeString(this.lxmc);
        dest.writeString(this.parentCrowid);
        dest.writeString(this.qlbm);
        dest.writeString(this.qlcd);
        dest.writeString(this.qlmc);
        dest.writeInt(this.qmqk);
        dest.writeString(this.sjhz);
        dest.writeString(this.tbdwdm);
        dest.writeInt(this.txzz);
        dest.writeDouble(this.zxzh);
        dest.writeList(this.qljcmxSet);
    }

    public QlcxListBean() {
    }

    protected QlcxListBean(Parcel in) {
        this.crowid = in.readString();
        this.fzr = in.readString();
        this.jcnx = in.readString();
        this.jcrq = in.readString();
        this.jlr = in.readString();
        this.lxbm = in.readString();
        this.lxmc = in.readString();
        this.parentCrowid = in.readString();
        this.qlbm = in.readString();
        this.qlcd = in.readString();
        this.qlmc = in.readString();
        this.qmqk = in.readInt();
        this.sjhz = in.readString();
        this.tbdwdm = in.readString();
        this.txzz = in.readInt();
        this.zxzh = in.readDouble();
        this.qljcmxSet = new ArrayList<QljcmxSetBean>();
        in.readList(this.qljcmxSet, QljcmxSetBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<QlcxListBean> CREATOR = new Parcelable.Creator<QlcxListBean>() {
        @Override
        public QlcxListBean createFromParcel(Parcel source) {
            return new QlcxListBean(source);
        }

        @Override
        public QlcxListBean[] newArray(int size) {
            return new QlcxListBean[size];
        }
    };
}
