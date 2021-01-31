package com.offcn.project.vo.resp;

import java.io.Serializable;

public class ProjectVo implements Serializable {

    private Integer mid;

    private Integer pid;

    private String projectName;

    private String headImg;

    private String remark;

    public ProjectVo(Integer mid, Integer pid, String projectName, String headImg, String remark) {
        this.mid = mid;
        this.pid = pid;
        this.projectName = projectName;
        this.headImg = headImg;
        this.remark = remark;
    }

    public ProjectVo() {
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
