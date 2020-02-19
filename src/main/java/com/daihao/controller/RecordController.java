package com.daihao.controller;

import com.daihao.util.ModelMapUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/record")
public class RecordController
{
    public static final String PAGE_NAME = "record";
    public static final String PAGE_TITLE = "Quản lý điểm thưởng";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/record.jsp";

    @RequestMapping()
    public String redirectIndex()
    {
        return "redirect:/record/index";
    }

    @RequestMapping("/index")
    public String getDeparts(ModelMap model)
    {
        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }


}
