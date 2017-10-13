package com.ouyeel.utils;
/**
 * 
 * 〈字符串工具类〉<br> 
 * 〈功能详细描述〉
 *
 * @author Shawn Wang
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class StringUtil {
    /**
     * 
     * 功能描述: 是否为空白字符串<br>
     * 〈功能详细描述〉
     *
     * @param o
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isEmpty(Object o) {
        return isNull(o) || o.toString().trim().equals("");
    }
    /**
     * 
     * 功能描述: 判读是否为null<br>
     * 〈功能详细描述〉
     *
     * @param o
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isNull(Object o) {
        return o == null;
    }

    /**
     * 将str字符串首字母大写
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String replacePathToPkg(String str) {
        return str.replace("/", ".");
    }

    public static String removeTheEndStr(String str, String str2){
        if (str.endsWith(str2)){
            return upperCase(str.substring(0, str.length()-1));
        }else {
            return upperCase(str);
        }
    }

    /**
     * 获取泛型对象字符串，比如List<Object>字符串，返回Object
     * @param str
     * @return
     */
    public static String getGeneratorClassStr(String str){
        int beginIndex = str.lastIndexOf("<");
        int endIndex = str.lastIndexOf(">");
        if (beginIndex != -1 && endIndex != -1){
            return upperCase(str.substring(beginIndex+1, endIndex));
        } else {
            return upperCase(str);
        }
    }

//    public static boolean isBaseTypeStr(String str) {
//        String[] strings = new String[]{"Int","int","Integer","String", "string","Boolean"};
//    }
}
