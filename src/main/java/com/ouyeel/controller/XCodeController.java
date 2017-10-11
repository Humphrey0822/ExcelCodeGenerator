package com.ouyeel.controller;

import com.ouyeel.dto.Interface;
import com.ouyeel.service.XCodeService;
import com.ouyeel.utils.XCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class XCodeController {

    @Autowired
    private XCodeService xCodeService;

    /**
     * 功能描述:首页 <br>
     * 〈功能详细描述〉
     *
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("/main.action")
    public ModelAndView main() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("main");
        return mv;
    }

    /**
     * 功能描述:处理上传文件 <br>
     * 〈功能详细描述〉
     *
     * @param request
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping("/uploadFile.action")
//    @ResponseBody
    public String uploadFile(HttpServletRequest request) {
        // TODO: 2017/9/28 后面考虑是否可以设置包名
//        String outputPath = request.getParameter("outputPath");
        String outputPath = "D:/temp/";
        List<Interface> interfaces = null;
        try {
            interfaces = xCodeService.uploadFileHandler(request);
            XCode.run(outputPath, interfaces);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/main.action";
    }
}
