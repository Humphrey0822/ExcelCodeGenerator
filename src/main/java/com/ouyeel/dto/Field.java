package com.ouyeel.dto;

import java.util.List;

public class Field {

    private String fieldCName; // 字段中文名

    private String fieldEName; // 字段英文名

    private String fieldType; // 字段类型

    private String isHas; // 是否必须

    private String mark; //备注(字段上注释)

    private List<Field> fields; // 当该字段为对象时

    public Field(String fieldCName, String fieldEName, String fieldType, String isHas, String mark) {
        this.fieldCName = fieldCName;
        this.fieldEName = fieldEName;
        this.fieldType = fieldType;
        this.isHas = isHas;
        this.mark = mark;
    }

    public Field(String fieldCName, String fieldEName, String fieldType, String mark) {
        this.fieldCName = fieldCName;
        this.fieldEName = fieldEName;
        this.fieldType = fieldType;
        this.mark = mark;
    }

    public String getFieldCName() {
        return fieldCName;
    }

    public void setFieldCName(String fieldCName) {
        this.fieldCName = fieldCName;
    }

    public String getFieldEName() {
        return fieldEName;
    }

    public void setFieldEName(String fieldEName) {
        this.fieldEName = fieldEName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getIsHas() {
        return isHas;
    }

    public void setIsHas(String isHas) {
        this.isHas = isHas;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
