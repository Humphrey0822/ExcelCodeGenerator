package com.ouyeel.dto;

public class InterfaceMethod {

    private String methodName;

    private String methodDes;

    private Command command;

    private ReturnBean returnBean;

    public InterfaceMethod() {
    }

    public InterfaceMethod(String methodName, String methodDes) {
        this.methodName = methodName;
        this.methodDes = methodDes;
    }

    public InterfaceMethod(Command command, ReturnBean returnBean) {
        this.command = command;
        this.returnBean = returnBean;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodDes() {
        return methodDes;
    }

    public void setMethodDes(String methodDes) {
        this.methodDes = methodDes;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ReturnBean getReturnBean() {
        return returnBean;
    }

    public void setReturnBean(ReturnBean returnBean) {
        this.returnBean = returnBean;
    }
}
