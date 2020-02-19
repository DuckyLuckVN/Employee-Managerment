package com.daihao.controller.api;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daihao.dao.DepartDAO;
import com.daihao.model.Depart;
import com.daihao.service.DepartService;
import com.daihao.util.JSONUtil;

@RestController
@RequestMapping(value = "/api/depart", produces = "json/application; charset=UTF-8")
public class DepartAPI
{
    @Autowired
    DepartService service;

    @Autowired
    DepartDAO departDAO;

//    GET ALL PHÂN TRANG
    @GetMapping(params = {"start", "size", "search"})
    public String getDepart(@RequestParam("start") int start,
                            @RequestParam("size") int size,
                            @RequestParam("search") String search,
                            HttpServletResponse response)
            throws SQLException
    {
        try
        {
            return service.getAllDTO_JSON(start, size, search);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

//    GET ALL PHÂN TRANG
    @GetMapping(value = "/all")
    public String getDepartAll(HttpServletResponse response) throws SQLException
    {
        try
        {
            return JSONUtil.getString(service.getAll());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //lấy về tổng số phòng ban
    @GetMapping(value = "/count", params = {"search"})
    public String getTotal(@RequestParam("search") String search, HttpServletResponse response) throws SQLException
    {
        try
        {
            return service.getCount(search) + "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy ra tống kê tiêu chuẩn của Depart
    @GetMapping(value = "/statistic/{month}")
    public String getStatisticStandar(@PathVariable("month") int month, HttpServletResponse response)
    {
        //Nếu month = 0 thì lấy ra thống kê của tháng hiện tại
        try {
            if (month == 0)
            {
                return service.getStatisticStander();
            }
            return service.getStatisticStander(month);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy ra tống kê tiêu chuẩn của Depart
    @GetMapping(value = "/statistic/top10record", params = {"month"})
    public String getTop10Depart(@RequestParam("month") int month, HttpServletResponse response)
    {
        //Nếu month = 0 thì lấy ra thống kê của tháng hiện tại
        try {
            return JSONUtil.getString(departDAO.getTop10WithGoodRecord(month));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy về thông tin của depart dừa vào {id}
    @GetMapping("/{id}")
    public String getDepart(@PathVariable String id, HttpServletResponse response)
    {
        try
        {
            return JSONUtil.getString(departDAO.findById(id));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Thêm Depart
    @PostMapping
    public String insertDepart(@RequestParam("id") String id,
                               @RequestParam("name") String name,
                               HttpServletResponse response) {
        Depart depart = new Depart(id, name);
        try
        {
            departDAO.insert(depart);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
        return JSONUtil.getString(depart);
    }

    @PostMapping(params = {"update"})
    public String updateDepart(@RequestParam("id") String id,
                               @RequestParam("name") String name,
                               HttpServletResponse response)
    {
        Depart depart = null;
        try
        {
            depart = departDAO.findById(id);
            depart.setName(name);
            if (depart == null)
            {
                response.setHeader("404", "Phòng ban này không tồn tại!");
                response.setStatus(417);
                return "Phòng ban không tồn tại!";
            }

            departDAO.update(depart);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.setHeader("msgError", e.getMessage());
            response.setStatus(417);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return JSONUtil.getString(depart);
    }

    @DeleteMapping("/{id}")
    public String deleteDepart(@PathVariable("id") String id, HttpServletResponse response)
    {
        try
        {
            departDAO.delete(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.setHeader("msgError", e.getMessage());
            response.setStatus(417);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "true";
    }
}
