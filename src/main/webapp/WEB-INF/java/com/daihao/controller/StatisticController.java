package com.daihao.controller;

import com.daihao.util.ModelMapUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistic")
public class StatisticController
{
    public static final String PAGE_NAME = "statistic";
    public static final String PAGE_TITLE = "Thống kê";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/statistic.jsp";

    @RequestMapping()
    public String redirectIndex()
    {
        return "redirect:/statistic/index";
    }

    @RequestMapping("/index")
    public String getDeparts(ModelMap model)
    {
        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }


}
