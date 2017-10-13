package com.ouyeel.service;

import com.ouyeel.dto.*;
import com.ouyeel.utils.Constant;
import com.ouyeel.utils.StringUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

@Service
public class XCodeService {
    private static Logger logger = LoggerFactory.getLogger(XCodeService.class);

    /**
     * 功能描述:处理上传的excel文件 <br>
     * 〈功能详细描述〉
     *
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public List<Interface> uploadFileHandler(HttpServletRequest request) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        List<Interface> interfaces = convertToInterfaceList(multipartFile);
        return interfaces;
    }

    private List<Interface> convertToInterfaceList(MultipartFile multipartFile) {
        List<Interface> interfaceList = new ArrayList<>();

        //解析表格索引标识
        int crtInterfaceCursor = -1;
        int crtMethodCursor = -1;
        int cmdOrRtBeanFlag = -1; // 0-command 1-returnBean
        int crtF1Cursor = -1; // F1
        int crtF2Cursor = -1; // F2
        int crtF3Cursor = -1; // F3
        //POI 解析excel
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(multipartFile.getInputStream());
            // TODO: 2017/9/28 考虑后面要不要一个Interface一个sheet
            Sheet sheet = wb.getSheetAt(0);
            if (sheet != null && sheet.getLastRowNum() != 0) {
                for (int num = 1; num < sheet.getLastRowNum() + 1; num++) {
                    Row row = sheet.getRow(num);
                    Interface crtInterface = null;
                    InterfaceMethod crtMethod = null;// 接口
                    Command crtCommand = null;// Column A 接口名称
                    ReturnBean crtReturnBean = null;
                    Field crtF1 = null;
                    Field crtF2 = null;
                    Field crtF3 = null;
                    String interfaceName = getCellValueString(row, 0);
                    if (!StringUtil.isEmpty(interfaceName)) {
                        crtInterface = new Interface();
                        crtInterface.setInterfaceName(interfaceName);
                        interfaceList.add(crtInterface);
                        crtInterfaceCursor++;
                        crtMethodCursor = -1;
//                        crt
                    }else {
                        crtInterface = interfaceList.get(crtInterfaceCursor);
                    }
                    // Column B 接口说明(注释)
                    String interfaceDes = getCellValueString(row, 1);
                    if (!StringUtil.isEmpty(interfaceDes)) {
                        crtInterface.setInterfaceDes(interfaceDes);
                    }
                    // 方法
                    if (crtInterface.getInterfaceMethods() == null) {
                        List<InterfaceMethod> methodList = new ArrayList<>();
                        crtInterface.setInterfaceMethods(methodList);
                    }
                    // Column C 接口中的方法名称
                    String methodName = getCellValueString(row, 2);
                    if (!StringUtil.isEmpty(methodName)) {
                        crtMethod = new InterfaceMethod();
                        crtMethod.setMethodName(methodName);
                        crtInterface.getInterfaceMethods().add(crtMethod);
                        crtMethodCursor++;
                    } else {
                        crtMethod = crtInterface.getInterfaceMethods().get(crtMethodCursor);
                    }
                    // Column D 方法说明(注释)
                    String methodDes = getCellValueString(row, 3);
                    if (!StringUtil.isEmpty(methodDes)) {
                        crtMethod.setMethodDes(methodDes);
                    }
//                    crtInterface.setInterfaceMethods();

                    // Command & ReturnBean
                    String cmdOrRetBean = getCellValueString(row, 4);
                    if (!StringUtil.isEmpty(cmdOrRetBean)) {
                        if (cmdOrRetBean.toUpperCase().equals(Constant.COMMAND)) {
                            crtCommand = new Command();
                            crtCommand.setCommandName(StringUtil.upperCase(crtMethod.getMethodName()) + "Command");
                            cmdOrRtBeanFlag = 0;
                        } else if (cmdOrRetBean.toUpperCase().equals(Constant.RETURNBEAN)) {
                            crtReturnBean = new ReturnBean();
                            crtReturnBean.setReturnBeanName(StringUtil.upperCase(crtMethod.getMethodName()) + "ReturnBean");
                            cmdOrRtBeanFlag = 1;
                        }
                        crtF1Cursor = -1;
                    } else {
                        if (cmdOrRtBeanFlag == 0) {
                            crtCommand = crtMethod.getCommand();
                        } else if (cmdOrRtBeanFlag == 1) {
                            crtReturnBean = crtMethod.getReturnBean();
                        }
                    }
                    // field
                    String fieldCName = getCellValueString(row, 5);
                    String fieldEName = getCellValueString(row, 6);
                    String fieldType = getCellValueString(row, 7);
                    String isHas = getCellValueString(row, 8);
                    String mark = getCellValueString(row, 9);
//                    if (crtF1 == null) {
//                        ArrayList<Field> f2List = new ArrayList<>();
//                        crtF1.setFields(f2List);
//                    }
//                    Field f1 = null;
                    if (!StringUtil.isEmpty(fieldEName) && !StringUtil.isEmpty(fieldType)){
//                        assert fieldType != null;
                        if (fieldType.equals("JSON")) {
                            if (fieldEName.contains("List")){
                                // 如果field 为 List<JSON>类型，则fieldName为方法名，如方法名inquiryQuoteList，则为List<InquiryQuoteVo> inquiryQuoteList
                                crtF1 = new Field(fieldCName,fieldEName,
                                        delEndListStr(crtMethod.getMethodName(),1)+"Page",isHas,mark);
                            } else {
                                crtF1 = new Field(fieldCName, fieldEName, fieldType, isHas, mark);
//                                crtF1 = new Field(fieldCName,delEndListStr(crtMethod.getMethodName(),0)+"PageList",
//                                        "List<"+delEndListStr(crtMethod.getMethodName(),1)+  +">",isHas,mark);
                            }
                            ArrayList<Field> f2List = new ArrayList<>();
                            crtF1.setFields(f2List);
                            crtF1Cursor++;

                        }else {
                            crtF1 = new Field(fieldCName,fieldEName,fieldType,isHas,mark);
                            crtF1Cursor++;
                        }
                        crtF2Cursor = -1;
                        List<Field> fields = null;
                        if (cmdOrRtBeanFlag == 0) {
                            if (crtCommand.getFields() == null) {
                                fields = new ArrayList<>();
                                fields.add(crtF1);
//                            crtF1Cursor++;
                                crtCommand.setFields(fields);
                                crtMethod.setCommand(crtCommand);
                            } else {
                                crtCommand.getFields().add(crtF1);
                            }
                        } else if (cmdOrRtBeanFlag == 1){
                            if (crtReturnBean.getFields() == null) {
                                fields = new ArrayList<>();
                                fields.add(crtF1);
//                            crtF1Cursor++;
                                crtReturnBean.setFields(fields);
                                crtMethod.setReturnBean(crtReturnBean);
                            } else {
                                crtReturnBean.getFields().add(crtF1);
                            }
                        }
                    }else {
                        if (cmdOrRtBeanFlag == 0) {
                            crtF1 = crtCommand.getFields().get(crtF1Cursor);
                        }else if (cmdOrRtBeanFlag == 1) {
                            try {
                                crtF1 = crtReturnBean.getFields().get(crtF1Cursor);
                            } catch (Exception e) {
                                System.out.println("当去读到表格第" + num + "行抛出异常");
                                e.printStackTrace();
                            }
                        }
                    }

                    // fieldList
                    String fieldListCName = getCellValueString(row, 10);
                    String fieldListEName = getCellValueString(row, 11);
                    String fieldListType = getCellValueString(row, 12);
                    String fieldListMark = getCellValueString(row, 13);
//                    Field f2 = null;
                    if (!StringUtil.isEmpty(fieldListEName)&& !StringUtil.isEmpty(fieldListType)){
                        if (fieldListType.equals("JSON")) {

                            if (fieldListEName.contains("Lists")){
                                crtF2 = new Field(fieldListCName,fieldListEName,
                                        "List<"+delEndListStr(crtMethod.getMethodName(),1)+"Result>",null,fieldListMark);
                            } else {
                                crtF2 = new Field(fieldListCName,fieldListEName,
                                        delEndListStr(crtMethod.getMethodName(),1)+fieldListEName,null,fieldListMark);
                            }
                            ArrayList<Field> f3List = new ArrayList<>();
                            crtF2.setFields(f3List);
                            crtF2Cursor++;
                        }else {
                            crtF2 = new Field(fieldListCName,fieldListEName,fieldListType,null,fieldListMark);
                            crtF2Cursor++;
                        }
                        crtF3Cursor = -1;
                        crtF1.getFields().add(crtF2);
                    } else {
                        if (crtF1 != null && crtF1.getFields() != null) {
                            crtF2 = crtF1.getFields().get(crtF2Cursor);
                        }
                    }

                    // fieldVoList
                    String fieldListVoCName = getCellValueString(row, 14);
                    String fieldListVoEName = getCellValueString(row, 15);
                    String fieldListVoType = getCellValueString(row, 16);
                    String fieldListVoMark = getCellValueString(row, 17);
                    if (!StringUtil.isEmpty(fieldListVoEName)&& !StringUtil.isEmpty(fieldListVoType)){
                        crtF3 = new Field(fieldListVoCName, fieldListVoEName, fieldListVoType, null, fieldListVoMark);
                        try {
                            crtF2.getFields().add(crtF3);
                        } catch (Exception e) {
                            System.out.println("当去读到表格第" + num + "行抛出异常");
                            e.printStackTrace();
                        }
                    }

                }
            } else {
                System.out.println("上传表格为空");
            }
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return interfaceList;
    }

    /**
     * 判断字符串是否以List结尾，如果以List结尾则去除List返回字符串
     * @param str
     * @param a 1-首字母大写 0-首字母小写
     * @return
     */
    public String delEndListStr(String str, int a) {
        int i = str.lastIndexOf("List"); //如果str不以字符串List结尾，则返回-1
        if (i != -1){
//            return StringUtil.upperCase(str.substring(0, i));
            if (a == 1) {
                return StringUtil.upperCase(str.substring(0, i));
            } else {
                return str.substring(0, i);
            }
        }else {
            return StringUtil.upperCase(str);
        }
    }

    /**
     * 获取某行第i列的值
     * @param row excel 行对象
     * @param i   某行的第i列
     * @return
     */
    private String getCellValueString(Row row, int i) {
        return row.getCell(i) == null ? null : row.getCell(i).getRichStringCellValue().getString();
    }

    /**
     * 功能描述:将数据转化成map集合形式 <br>
     * 〈功能详细描述〉
     *
     * @param multipartFile
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
//    private String convertToMap(MultipartFile multipartFile) {
//        Map<String, List<Table>> tableMap = new LinkedHashMap<String, List<Table>>();
//        String code = "";
//        String pkName = "";
//        try {
//            //POI 解析excel
//            Workbook wb = WorkbookFactory.create(multipartFile.getInputStream());
//            Sheet sheet = wb.getSheetAt(0);
//            if (sheet != null && sheet.getLastRowNum() != 0) {
//                String tableName = sheet.getSheetName().toLowerCase();
//                List<Table> tableList = new ArrayList<Table>();
//                for (int num = 1; num < sheet.getLastRowNum() + 1; num++) {
//                    Row row = sheet.getRow(num);
//                    //字段名
//                    String name = row.getCell(0) == null ? null : row.getCell(0).getRichStringCellValue().getString();
//                    //类型
//                    String type = row.getCell(1) == null ? null : row.getCell(1).getRichStringCellValue().getString();
//                    //长度
//                    Integer length = (int) Math.floor(Float.valueOf(row.getCell(2) == null ? null : row.getCell(2).toString()));
//                    //精度
//                    Integer precision = (int) Math.floor(Float.valueOf(row.getCell(3) == null ? null : row.getCell(3).toString()));
//                    //非空（0/1）
//                    String notnull = row.getCell(4) == null ? null : row.getCell(4).toString();
//                    //主键（0/1）
//                    Integer pk = (int) Math.floor(Float.valueOf(row.getCell(5) == null ? null : row.getCell(5).toString()));
//                    //判断是否为主键
//                    if (Integer.valueOf(1).equals(pk)) {
//                        pkName = name;
//                    }
//                    //备注
//                    String comment = row.getCell(6) == null ? null : row.getCell(6).getRichStringCellValue().getString();
//                    tableList.add(new Table(pk, name, type, length, precision, notnull, comment));
//                    tableMap.put(tableName, tableList);
//                }
//            }
//            code = this.generateCode(tableMap, pkName);
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            throw new RuntimeException(e.getMessage(), e);
//        }
//        return code;
//
//    }

    /**
     * 功能描述:获取SQL和Entity代码 <br>
     * 〈功能详细描述〉
     *
     * @param tableMap
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
//    private String generateCode(Map<String, List<Table>> tableMap, String pkName) {
//        StringBuilder code = new StringBuilder("<br/>");
//        //处理SQL
//        Map<String, Object> sqlMap = new HashMap<String, Object>();
//        sqlMap.put("codeMap", tableMap);
//        sqlMap.put("pkName", pkName);
//        String sql = TemplateParse.parse(sqlMap, TemplateContext.getTemplate("sql"));
//        code.append(sql + "</br>");
//        //处理Entity
//        String className = "";
//        List<Table> fieldList = new ArrayList<Table>();
//        List<Method> methodList = new ArrayList<Method>();
//        for (Entry<String, List<Table>> entry : tableMap.entrySet()) {
//            className = entry.getKey();
//            fieldList = entry.getValue();
//            if (fieldList != null) {
//                for (Table table : fieldList) {
//                    //首字母大写
//                    String upperFieldName = table.getName().substring(0, 1).toUpperCase() + table.getName().substring(1, table.getName().length());
//                    //备注
//                    String comment = table.getComment();
//                    //字段名
//                    String fieldName = table.getName();
//                    //主键
//                    Integer pk = table.getPk();
//                    //类型
//                    String type = MapUtil.getType(table.getType());
//                    methodList.add(new Method(upperFieldName, fieldName, type, comment, pk));
//                }
//            }
//        }
//        Map<String, Object> beanMap = new HashMap<String, Object>();
//        beanMap.put("className", className);
//        beanMap.put("methodList", methodList);
//        String bean = TemplateParse.parse(beanMap, TemplateContext.getTemplate("entity"));
//        code.append(bean);
//        return code.toString();
//    }
}
