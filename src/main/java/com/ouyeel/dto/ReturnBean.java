package com.ouyeel.dto;

import java.util.List;

public class ReturnBean {

    private String returnBeanName;

    private String returnBeanDes;

    private String returnBeanPkg;

    private List<Field> fields;

    public ReturnBean() {
    }

    public ReturnBean(String returnBeanName, String returnBeanDes) {
        this.returnBeanName = returnBeanName;
        this.returnBeanDes = returnBeanDes;
    }

    public String getReturnBeanName() {
        return returnBeanName;
    }

    public void setReturnBeanName(String returnBeanName) {
        this.returnBeanName = returnBeanName;
    }

    public String getReturnBeanDes() {
        return returnBeanDes;
    }

    public void setReturnBeanDes(String returnBeanDes) {
        this.returnBeanDes = returnBeanDes;
    }

    public String getReturnBeanPkg() {
        return returnBeanPkg;
    }

    public void setReturnBeanPkg(String returnBeanPkg) {
        this.returnBeanPkg = returnBeanPkg;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
