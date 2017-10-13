package com.ouyeel.xplat;

import com.ouyeel.utils.StringUtil;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestStringUtil {
    public static void main(String[] args) {
//        String str = "inquiryQuoteList";
//        int i = str.lastIndexOf("List");
//        System.out.println(i);
//        System.out.println(str.substring(0, i));
//        System.out.println(StringUtil.upperCase(str.substring(0, i)));

//        String str = "List<Object>";
        String str = "List<Object";
        int start = str.lastIndexOf("<");
        int end = str.lastIndexOf(">");
        System.out.println(str.substring(start+1, end));

    }

    @Test
    public void testSubStr(){
        String filetext = "//@张小名: 25分//@李小花: 43分//@王力: 100分";
        Pattern p = Pattern.compile("\\@(.*?)\\:");//正则表达式，取=和|之间的字符串，不包括=和|
        Matcher m = p.matcher(filetext);
        while(m.find()) {
            System.out.println(m.group(0));//m.group(1)不包括这两个字符

        }
    }
}
