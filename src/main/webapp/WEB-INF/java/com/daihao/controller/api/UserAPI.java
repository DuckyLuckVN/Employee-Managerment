package com.daihao.controller.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daihao.dao.UserDAO;
import com.daihao.model.User;
import com.daihao.service.AuthenticationService;
import com.daihao.util.JSONUtil;

@RestController
@RequestMapping(value = "/api/user", produces = "json/application; charset=UTF-8")
public class UserAPI
{
    @Autowired
    UserDAO userDAO;

    @Autowired
    AuthenticationService authService;

    //Lấy dữ liệu trên bảng user từ start -> start + size với search
    @RequestMapping(method = RequestMethod.GET, params = {"start", "size", "search"}, produces = "json/application; charset=UTF-8")
    public String getDepart(@RequestParam("start") int start,
                            @RequestParam("size") int size,
                            @RequestParam("search") String search,
                            HttpServletResponse response)
    {

        List<User> users = null;
        try
        {
            users = userDAO.getAll(start, size, search);
            return JSONUtil.getString(users);
        }
        catch (Exception e) {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Action tùy chọn
    @GetMapping(value = "/count", produces = "json/application")
    private String getTotal(HttpServletResponse response) {
        int count = 0;
        try
        {
            count = userDAO.getAll().size();
            return count + "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/count", params = {"search"})
    private String getTotal(@RequestParam("search") String search, HttpServletResponse response) {
        int count = 0;
        try
        {
            count = userDAO.getCount(search).size();
            return count + "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy thông tin user có username = {username}
    @GetMapping("/{username}")
    public String getUser(@PathVariable("username") String username, HttpServletResponse response)
    {

        try
        {
            return JSONUtil.getString(userDAO.findById(username));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Thêm user mới
    @PostMapping()
    public String insertUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("fullname") String fullname,
                             HttpServletResponse response)
    {
        User user = new User(username, authService.createEncryptedPassword(password), fullname);
        try
        {
            userDAO.insert(user);
            return JSONUtil.getString(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //cập nhật user mới
    @PostMapping(params = {"update"})
    public String updateUser(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("fullname") String fullname,
                             HttpServletResponse response)
    {

        try
        {
            User user = userDAO.findById(username);
            user.setFullname(fullname);

            if (password.length() > 0)
                user.setPassword(authService.createEncryptedPassword(password));

            userDAO.update(user);
            return JSONUtil.getString(user);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Xóa user có username = {username}
    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable("username") String username, HttpServletResponse response)
    {
        try
        {
            userDAO.delete(username);
            return "true";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

}
