package com.ouyeel.dto;

import java.util.List;

public class Command {

    private String commandName;

    private String commandDes;

    private String commandPkg;

    private List<Field> fields;

    public Command() {
    }

    public Command(String commandName, String commandDes) {
        this.commandName = commandName;
        this.commandDes = commandDes;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandDes() {
        return commandDes;
    }

    public void setCommandDes(String commandDes) {
        this.commandDes = commandDes;
    }

    public String getCommandPkg() {
        return commandPkg;
    }

    public void setCommandPkg(String commandPkg) {
        this.commandPkg = commandPkg;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }
}
