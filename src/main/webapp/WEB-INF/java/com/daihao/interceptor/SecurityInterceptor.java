package com.daihao.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.daihao.dao.RememberUserDAO;
import com.daihao.dao.UserDAO;
import com.daihao.model.RememberUser;
import com.daihao.model.User;
import com.daihao.service.AuthenticationService;
import com.daihao.util.DateUtil;

@Service
public class SecurityInterceptor implements HandlerInterceptor
{
    @Autowired
    RememberUserDAO rememberUserDAO;

    @Autowired
    AuthenticationService authService;

    @Autowired
    UserDAO userDAO;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        HttpSession session = request.getSession();
        Cookie cookieRemember = null;
        RememberUser rememberUser = null;
        if (session.getAttribute("user") != null) { return true; }
        else
        {
            //lấy về cookie remember kiểm tra với database
            for (Cookie cookie : request.getCookies())
            {
                if (cookie.getName().equals("remember"))
                {
                    cookieRemember = cookie;
                    System.out.println("tim thay cookie remember: " + cookieRemember.getValue());
                    rememberUser = rememberUserDAO.findById(cookieRemember.getValue().split("#")[1]);
                    System.out.println("Tim thay rememberUser" + rememberUser);
                }
            }


            //kiểm tra xác thực và thời gian quá hạn của remember
            if (rememberUser != null)
            {
                //kiểm tra xem đã quá hạn chưa
                boolean isExpired = rememberUser.getExpired().before(new Date());
                if (isExpired)
                {
                    //xóa cookie và xóa remember trên database
                    cookieRemember.setMaxAge(0);
                    response.addCookie(cookieRemember);
                    rememberUserDAO.delete(rememberUser.getId());

                    response.sendRedirect("/login");
                    return false;
                }
                else
                {
                    //kiểm tra xem mật khẩu user đó có bị thay đổi không
                    User user =  userDAO.findById(rememberUser.getUser().getUsername());

                    if (user.getPassword().equals(cookieRemember.getValue().split("#")[2]))
                    {
                        //Thêm ngày hết hạn vào và lưu lại
                        rememberUser.setDateLogin(new Date());
                        rememberUser.setExpired(DateUtil.addDay(rememberUser.getDateLogin(), 3));

                        rememberUserDAO.update(rememberUser);
                        //Lấy về User từ remember và gán vào session
                        session.setAttribute("user", user);
                        return true;
                    }
                    else
                    {
                        //xóa cookie và xóa remember trên database
                        cookieRemember.setMaxAge(0);
                        response.addCookie(cookieRemember);
                        rememberUserDAO.delete(rememberUser.getId());

                        response.sendRedirect("/login");
                        return false;
                    }

                }
            }
        }

        response.sendRedirect("/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {

    }
}
