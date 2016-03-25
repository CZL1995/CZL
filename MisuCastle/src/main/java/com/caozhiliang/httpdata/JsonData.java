package com.caozhiliang.httpdata;

/**
 * @author CZL
 * @time 2016-03-15 21:56
 */
public class JsonData {

    /**
     * versionName : 2.0
     * versionCode : 2
     * description : 新增NB功能,赶紧体验!!!
     * downloadUrl : http://192.168.43.87:8080/Safegarud-2.0.apk
     */

    private String versionName;
    private int versionCode;
    private String description;
    private String downloadUrl;

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getDescription() {
        return description;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
}
