/*
 * Powered By [catsic]
 * Web Site: http://www.catsic.com
 */

package com.catsic.core.bean;

import android.os.Parcel;
import android.os.Parcelable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;


/**
 * @author wuxianling
 * @Description: File
 * @date 2014年8月25日 上午10:28:01
 */
@Table(name = "T_FILE")
public class Tfile implements Parcelable {


    //columns START
    @Id
    private java.lang.String fileId;
    private java.lang.String filePath;
    private java.lang.String fileSize;
    private java.lang.String fileType;
    private java.lang.String fileTime;
    private java.lang.String relationId;
    private java.lang.String groupId;
    private java.lang.String content;
    private java.lang.String fileName;
    //columns END

    public static final Parcelable.Creator<Tfile> CREATOR = new Parcelable.Creator<Tfile>() {

        @Override
        public Tfile createFromParcel(Parcel source) {
            return new Tfile(source);
        }

        @Override
        public Tfile[] newArray(int size) {
            return new Tfile[size];
        }
    };


    public Tfile() {
    }

    public Tfile(Parcel source) {
        this.fileId = source.readString();
        this.filePath = source.readString();
        this.fileSize = source.readString();
        this.fileType = source.readString();
        this.fileTime = source.readString();
        this.relationId = source.readString();
        this.groupId = source.readString();
        this.fileName = source.readString();
    }

    public Tfile(String fileId, String filePath, String fileSize,
                 String fileType, String fileTime, String relationId,
                 String groupId, String content, String fileName) {
        super();
        this.fileId = fileId;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileType = fileType;
        this.fileTime = fileTime;
        this.relationId = relationId;
        this.groupId = groupId;
        this.content = content;
        this.fileName = fileName;
    }

    public void setFileId(java.lang.String value) {
        this.fileId = value;
    }

    public java.lang.String getFilePath() {
        return filePath;
    }

    public void setFilePath(java.lang.String filePath) {
        this.filePath = filePath;
    }

    public java.lang.String getFileSize() {
        return fileSize;
    }

    public void setFileSize(java.lang.String fileSize) {
        this.fileSize = fileSize;
    }

    public java.lang.String getFileType() {
        return fileType;
    }

    public void setFileType(java.lang.String fileType) {
        this.fileType = fileType;
    }

    public java.lang.String getFileTime() {
        return fileTime;
    }

    public void setFileTime(java.lang.String fileTime) {
        this.fileTime = fileTime;
    }

    public java.lang.String getRelationId() {
        return relationId;
    }

    public void setRelationId(java.lang.String relationId) {
        this.relationId = relationId;
    }

    public java.lang.String getGroupId() {
        return groupId;
    }

    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }

    public java.lang.String getContent() {
        return content;
    }

    public void setContent(java.lang.String content) {
        this.content = content;
    }

    public java.lang.String getFileName() {
        return fileName;
    }

    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
    }

    public java.lang.String getFileId() {
        return fileId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

}

