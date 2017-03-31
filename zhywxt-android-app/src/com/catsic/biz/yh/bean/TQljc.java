/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.biz.yh.bean;


import com.catsic.core.bean.Tfile;

import net.tsz.afinal.annotation.sqlite.Table;

import java.util.List;
import java.util.Set;

/**
 * @author catsic
 * @version 1.0
 * @date 2015-09-22 14:25:50
 * @since 1.0
 */

@Table(name = "T_YH_QLGL_QLJC")
public class TQljc {

    private String crowid;
    private String parentCrowid;
    private String jlr;
    private String fzr;
    private String jcrq;
    private String tbdwdm;

    private List<Tfile> files;

    public String getTbdwdm() {
        return tbdwdm;
    }

    public void setTbdwdm(String tbdwdm) {
        this.tbdwdm = tbdwdm;
    }

    private Set qljcmxSet;

    public Set getQljcmxSet() {
        return qljcmxSet;
    }

    public void setQljcmxSet(Set qljcmxSet) {
        this.qljcmxSet = qljcmxSet;
    }

    public String getCrowid() {
        return crowid;
    }

    public void setCrowid(String crowid) {
        this.crowid = crowid;
    }

    public String getParentCrowid() {
        return parentCrowid;
    }

    public void setParentCrowid(String parentCrowid) {
        this.parentCrowid = parentCrowid;
    }

    public String getJlr() {
        return jlr;
    }

    public void setJlr(String jlr) {
        this.jlr = jlr;
    }

    public String getFzr() {
        return fzr;
    }

    public void setFzr(String fzr) {
        this.fzr = fzr;
    }

    public String getJcrq() {
        return jcrq;
    }

    public void setJcrq(String jcrq) {
        this.jcrq = jcrq;
    }

    public List<Tfile> getFiles() {
        return files;
    }

    public void setFiles(List<Tfile> files) {
        this.files = files;
    }
}

