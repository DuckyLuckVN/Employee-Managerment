package com.daihao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daihao.util.ModelMapUtil;

@Controller
@RequestMapping("/depart")
public class DepartController
{
    public static final String PAGE_NAME = "depart";
    public static final String PAGE_TITLE = "Quản lý phòng ban";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/depart.jsp";

    @RequestMapping()
    public String redirectIndex()
    {
        return "redirect:/depart/index";
    }

    @RequestMapping("/index")
    public String getDeparts(ModelMap model)
    {

        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }


}
