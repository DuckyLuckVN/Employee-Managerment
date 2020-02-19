package com.daihao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daihao.util.ModelMapUtil;

@Controller
@RequestMapping("/index")
public class MainController
{
    public static final String PAGE_NAME = "main";
    public static final String PAGE_TITLE = "Trang chính";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/main.jsp";

    @RequestMapping
    public String getIndex(ModelMap model)
    {
        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }
}
