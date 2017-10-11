package com.ouyeel.utils;

import com.ouyeel.dto.Command;
import com.ouyeel.dto.Interface;
import com.ouyeel.dto.InterfaceMethod;
import com.ouyeel.dto.ReturnBean;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class XCode {

    private static final Logger logger = LoggerFactory.getLogger(XCode.class);

    public static void run(String outputPath, List<Interface> interfaces) throws IOException {
//        String path = XCode.class.getClassLoader().getResource("generator").getFile() + "/";
//        logger.info("Read Configuration from: " + path);
//
//        Properties p = new Properties();
//        p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
//        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
//        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
//        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
//        p.setProperty(Velocity.RESOURCE_LOADER, "file");
//        p.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");

        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(p);
        // TODO: 2017/9/28 考虑要不要在本类中注入XCodeService生成
//        List<Interface> interfaces = new ArrayList<>();
        // 获取系统文件分隔符
        String fileSeparator = System.getProperty("file.separator");
        // TODO: 2017/9/28 后期是否动态输入projectPkg
        String projectPkg = "com.ouyeel.xplat";

        VelocityContext velocityContext;

        String[] templates = {"service.vm", "command.vm", "returnBean.vm"};
        String[] paths = {"service", "entity/command", "entity/returnBean"};
        String[] fileNames = {".java", ".java", ".java"};

        for (Interface interface1 : interfaces){
            if (StringUtil.isEmpty(interface1.getInterfacePkg())){
                interface1.setInterfacePkg(projectPkg + "." + paths[0]);
            }
            // TODO: 2017/9/29 遍历接口中的方法的entity(Command/ReturnBean)
            List<InterfaceMethod> interfaceMethods = interface1.getInterfaceMethods();

            // interface 模板中所需要引的entity 包名
            List<String> interfaceEntityPkgs = new ArrayList<>();
            // 接口与方法一对多
            for (InterfaceMethod interfaceMethod : interfaceMethods){
                Command command = interfaceMethod.getCommand();
                StringBuilder cmdPkg = new StringBuilder(projectPkg + "." + StringUtil.replacePathToPkg(paths[1]));
                command.setCommandPkg(cmdPkg.toString());
                interfaceEntityPkgs.add(cmdPkg.append(".").append(command.getCommandName()).toString());

                ReturnBean returnBean = interfaceMethod.getReturnBean();
                StringBuilder rtbPkg = new StringBuilder(projectPkg + "." + StringUtil.replacePathToPkg(paths[2]));
                returnBean.setReturnBeanPkg(rtbPkg.toString());
                interfaceEntityPkgs.add(rtbPkg.append(".").append(returnBean.getReturnBeanName()).toString());

                if (command != null) {
                    velocityContext = new VelocityContext();
                    velocityContext.put("command", command);
//                    velocityContext.put("pkgName", projectPkg+".entity.command");
                    String outPath1 = outputPath+"/" + projectPkg.replace(".", fileSeparator) + "/" + paths[1] + "/";
                    File file1 = new File(outPath1);
                    if (!file1.exists()) {
                        file1.mkdirs();
                    }
                    Template template1 = Velocity.getTemplate("templates/"+ templates[1]);
                    template1.setEncoding("utf-8");
                    String fileName1 = outPath1 + command.getCommandName() + fileNames[1];
                    writeFiles(template1, velocityContext, fileName1);
                    logger.info("write Command File: " + fileName1);
                }
                if (returnBean != null) {
                    velocityContext = new VelocityContext();
                    velocityContext.put("returnBean", returnBean);
//                    velocityContext.put("pkgName", projectPkg+".entity.returnBean");
                    String outPath2 = outputPath+"/" + projectPkg.replace(".", fileSeparator) + "/" + paths[2] + "/";
                    File file2 = new File(outPath2);
                    if (!file2.exists()) {
                        file2.mkdirs();
                    }
                    Template template2 = Velocity.getTemplate("templates/" + templates[2]);
                    template2.setEncoding("utf-8");
                    String fileName2 = outPath2 + returnBean.getReturnBeanName() + fileNames[2];
                    writeFiles(template2, velocityContext, fileName2);
                    logger.info("write ReturnBean File: " + fileName2);
                }
            }

            // 1. 生成Interface.java
            velocityContext = new VelocityContext();
            velocityContext.put("meta", interface1);
            velocityContext.put("entityPkgs", interfaceEntityPkgs);
            String outPath0 = outputPath+"/" + projectPkg.replace(".", fileSeparator) + "/" + paths[0] + "/";
            File file = new File(outPath0);
            if (!file.exists()) {
                file.mkdirs();
            }
            Template template = Velocity.getTemplate("templates/" + templates[0]);
            template.setEncoding("utf-8");
            String fileName = outPath0 + interface1.getInterfaceName() + fileNames[0];
            writeFiles(template, velocityContext, fileName);
            logger.info("write Interface File: " + fileName);
        }
        logger.info("XCode Generator Successful");
    }

    private static void writeFiles(Template template, VelocityContext context, String fileName) throws IOException {
//        FileWriter fileWriter = new FileWriter(fileName, false);
//        template.merge(context, fileWriter);
//        fileWriter.close();
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
        template.merge(context, writer);
        writer.close();
    }
}
