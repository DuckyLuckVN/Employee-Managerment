package com.daihao.controller;

import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daihao.config.BeanStorage;
import com.daihao.util.ModelMapUtil;

@Controller
@RequestMapping("/staff")
public class StaffController
{
    public static final String PAGE_NAME = "staff";
    public static final String PAGE_TITLE = "Quản lý nhân viên";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/staff.jsp";

    Session sessionHibernate = BeanStorage.getSession();

    @RequestMapping()
    public String redirectIndex()
    {
        return "redirect:/staff/index";
    }

    @RequestMapping("/insert-form")
    public String viewForm()
    {
        return "../included/staff/insert-form";
    }

    @RequestMapping("/view-index")
    public String viewIndex()
    {
        return "../included/staff/index";
    }

    @RequestMapping("/index")
    public String index(ModelMap model, @RequestParam(name = "search", required = false) String search) throws SQLException {
        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }

}
