package com.daihao.controller.api;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daihao.dao.RememberUserDAO;
import com.daihao.dao.UserDAO;
import com.daihao.model.RememberUser;
import com.daihao.model.User;
import com.daihao.service.AuthenticationService;
import com.daihao.util.DateUtil;
import com.daihao.util.JSONUtil;

@RestController
@RequestMapping(value = "/api/user", produces = "json/application; charset=UTF-8")
public class LoginAPI {
    @Autowired
    UserDAO userDAO;

    @Autowired
    RememberUserDAO rememberUserDAO;

    @Autowired
    AuthenticationService authService;

    //Đăng nhập tài khoản
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("remember") boolean isRemember,
                        HttpServletResponse response, HttpSession session)
    {
        try
        {
            User user = userDAO.findById(username);
            if (user != null)
            {
               if (authService.checkPassword(password, user.getPassword()))
               {
                   //trả respone về kèm đó lưu ss cho phiên này
                   session.setAttribute("user", user);

                   //Tiến hành lưu cookie nếu người dùng chọn lưu mật khẩu
                    if (isRemember)
                    {
                        RememberUser remember = new RememberUser();
                        remember.setUser(user);
                        remember.setDateLogin(new Date());
                        remember.setExpired(DateUtil.addDay(new Date(), 3));
                        rememberUserDAO.insert(remember);
                        //lưu cokie remember với value = id của rememberUser

                        ;
                        Cookie cookie = new Cookie("remember", "#" + remember.getId() + "#" + user.getPassword());
                        cookie.setMaxAge(60 * 60 * 24 * 30);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                        System.out.println("LoginAPI: da them cookie remember");
                    }

                   return JSONUtil.getString(user);
               }
               else
               {
                   response.setStatus(417);
                   return "Mật khẩu không chính xác!";
               }
            }
            else
            {
                response.setStatus(417);
                return "Tài khoản " + username + " này không tồn tại!";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    @PostMapping(value = "/changepassword", params = "update")
    public String changePassword(@RequestParam("username") String username,
                                 @RequestParam("fullname") String fullname,
                                 @RequestParam("password") String currentpassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("reNewPassword") String reNewPassword,
                                 HttpServletResponse response)
    {
        try
        {
            User user = userDAO.findById(username);
            //kiểm tra mật khẩu cũ có giống không
            if (authService.checkPassword(currentpassword, user.getPassword()))
            {
                //kiểm tra mật khẩu mới có giống nhau không
                if (newPassword.equals(reNewPassword))
                {
                    //tiến hành cập nhật lại user
                    user.setFullname(fullname);
                    user.setPassword(authService.createEncryptedPassword(newPassword));
                    userDAO.update(user);
                    return JSONUtil.getString(user);
                }
                else
                {
                    response.setStatus(417);
                    return "Mật khẩu mới không trùng khớp với nhau";
                }
            }
            else
            {
                response.setStatus(417);
                return "Mật khẩu cũ vừa nhập không chính xác!";
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Đăng xuất tài khoản
    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response, @CookieValue(value = "remember", required = false) String remember)
    {
        User user = (User) session.getAttribute("user");
        session.setAttribute("user", null);

        //Xóa dữ liệu remember trên DB
        if (remember != null) {
        	System.out.println("delete:" + remember.split("#")[1]);
        	rememberUserDAO.delete(remember.split("#")[1]);
        }

        //xóa cookie
        Cookie cookie = new Cookie("remember", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return JSONUtil.getString(user);
    }

}
