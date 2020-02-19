package com.daihao.controller;

import java.sql.SQLException;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daihao.config.BeanStorage;
import com.daihao.util.ModelMapUtil;

@Controller
@RequestMapping("/user")
public class UserController
{
    public static final String PAGE_NAME = "user";
    public static final String PAGE_TITLE = "Quản lý người dùng";
    public static final String PAGE_INCLUDE_PATH = "/WEB-INF/views/user.jsp";

    @Autowired
    Session sessionHibernate = BeanStorage.getSession();

    @RequestMapping()
    public String redirectIndex()
    {
        return "redirect:/user/index";
    }


    @RequestMapping("/index")
    public String index(ModelMap model) throws SQLException {

        ModelMapUtil.setConfigPage(model, PAGE_NAME, PAGE_TITLE, PAGE_INCLUDE_PATH);
        return "index";
    }



}
