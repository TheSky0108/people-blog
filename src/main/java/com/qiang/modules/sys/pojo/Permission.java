package com.qiang.modules.sys.pojo;

/**
 * @Author: qiang
 * @ProjectName: adminsystem
 * @Package: com.qiang.modules.sys.pojo
 * @Description: 权限表
 * @Date: 2019/7/11 0011 20:16
 **/
public class Permission {
    private Integer pid;
    private String pname;
    private String url;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
