package com.daihao.util;

import org.springframework.ui.ModelMap;

public class ModelMapUtil
{
    //Dùng đối tượng model map truyền vào để thêm 3 attr để cho trang index hiểu để hiển thị chính xác
    public static void setConfigPage(ModelMap modelMap, String page_name ,String page_title, String page_include_path)
    {
        modelMap.addAttribute("page_name", page_name);
        modelMap.addAttribute("page_title", page_title);
        modelMap.addAttribute("page_include_path", page_include_path);
    }


}
