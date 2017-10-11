package com.ouyeel.dto;

import java.util.List;

public class Interface {

    private String interfaceName; // 接口名称

    private String interfaceDes; // 接口描述

    private String interfacePkg; // 接口包名

    private List<InterfaceMethod> interfaceMethods;

    public Interface() {
    }
    public Interface(String interfaceName, String interfaceDes, String interfacePkg) {
        this.interfaceName = interfaceName;
        this.interfaceDes = interfaceDes;
        this.interfacePkg = interfacePkg;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceDes() {
        return interfaceDes;
    }

    public void setInterfaceDes(String interfaceDes) {
        this.interfaceDes = interfaceDes;
    }

    public String getInterfacePkg() {
        return interfacePkg;
    }

    public void setInterfacePkg(String interfacePkg) {
        this.interfacePkg = interfacePkg;
    }

    public List<InterfaceMethod> getInterfaceMethods() {
        return interfaceMethods;
    }

    public void setInterfaceMethods(List<InterfaceMethod> interfaceMethods) {
        this.interfaceMethods = interfaceMethods;
    }
}
