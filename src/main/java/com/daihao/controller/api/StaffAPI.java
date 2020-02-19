package com.daihao.controller.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daihao.dao.DepartDAO;
import com.daihao.dao.StaffDAO;
import com.daihao.model.Staff;
import com.daihao.util.DataUtil;
import com.daihao.util.JSONUtil;

@RestController
@RequestMapping(value = "/api/staff", produces = "json/application; charset=UTF-8")
public class StaffAPI {
    @Autowired
    ServletContext servletContext;

    @Autowired
    StaffDAO staffDAO;

    @Autowired
    DepartDAO departDAO;
    //Lấy dữ liệu trên bảng staff từ start -> start + size
    @GetMapping(params = {"start", "size", "search"})
    public String getDepart(@RequestParam("start") int start,
                            @RequestParam("size") int size,
                            @RequestParam("search") String search,
                            HttpServletResponse response) {

        List<Staff> staffs = null;
        try
        {
            staffs = staffDAO.getAll(start, size, search);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
        return JSONUtil.getString(staffs);
    }

    //Action tùy chọn
    @GetMapping(value = "/count")
    private String getTotal(@RequestParam("search") String search, HttpServletResponse response) {
        int count = 0;
        try
        {
            count = staffDAO.getAll(search).size();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
        return count + "";
    }

    //Lấy về dữ liệu thống kê
    @GetMapping(value = "/statistic")
    public String getStatisticStandard(HttpServletResponse response) {
        try
        {
            return JSONUtil.getString(staffDAO.getStatisticStandard());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }
    //Lấy về danh sách nhân viên của phòng có id {departId}
    @GetMapping(value = "/depart/{departId}")
    public String getStaffInDepart(@PathVariable("departId") String departId, HttpServletResponse response) {
        try
        {
            return JSONUtil.getString(staffDAO.getAllInDepart(departId));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy về dữ liệu thống kê top 10
    @GetMapping(value = "/statistic/top10record")
    public String getTop10Staff(@RequestParam("month") int month, HttpServletResponse response) {
        try
        {
            return JSONUtil.getString(staffDAO.getTop10WithGoodRecord(month));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy về thông tin nhân viên đã được chọn {id}
    @GetMapping(value = "/{staff_id}")
    public String getStaff(@PathVariable("staff_id") int staffId, HttpServletResponse response) {
        try
        {
            return JSONUtil.getString(staffDAO.findById(staffId));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Thêm mới một nhân viên
    @PostMapping()
    public String getStaff(@RequestParam("name") String name,
                           @RequestParam("gender") boolean gender,
                           @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                           @RequestParam(name = "photo", required = false) MultipartFile photo,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("salary") double salary,
                           @RequestParam("notes") String notes,
                           @RequestParam("departId") String departId,
                           HttpServletResponse response) {
        try {

            Staff staff = new Staff(0, name, gender, null, email, phone, birthday, salary, notes, departDAO.findById(departId));

            //Tiến hành ghi file nếu file tồn tại;
            if (photo != null && !photo.isEmpty()) {   //filename = <tên file>_<số thời gian>.<đuôi file>
                String fileName = DataUtil.splitFileName(photo.getOriginalFilename())[0] + "_" + new Date().getTime();
                String fileExtension = DataUtil.splitFileName(photo.getOriginalFilename())[1];
                String path = servletContext.getRealPath("/resources/image/staff/" + fileName + fileExtension);
                System.out.println(path);
                System.out.println(new Date().getTime() + " A ");
                photo.transferTo(new File(path));
                staff.setPhoto(fileName + fileExtension);
            }
            staffDAO.insert(staff);

            System.out.println(new Date().getTime() + " C ");
            return JSONUtil.getString(staff);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return "Lỗi truy xuất dữ liệu: " + e.getErrorCode() + " -> " + e.getMessage();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return  "Lỗi lưu trữ file: " + e.getMessage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Cập nhật lại thông tin nhân viên
    @PostMapping(params = {"update"})
    public String updateStaff(@RequestParam("id") @RequestBody int id,
                           @RequestParam("name") String name,
                           @RequestParam("gender") boolean gender,
                           @RequestParam("birthday") @DateTimeFormat(pattern = "yyyy-MM-dd") Date birthday,
                           @RequestParam("photo") MultipartFile photo,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("salary") double salary,
                           @RequestParam("notes") String notes,
                           @RequestParam("departId") String departId,
                           HttpServletResponse response)
    {
        try {
            Staff staff = staffDAO.findById(id);
            if (staff == null) {
                response.setStatus(400);
                return "Không tìm thấy nhân viên có mã " + id;
            }

            staff.setName(name);
            staff.setGender(gender);
            staff.setBirthday(birthday);
            staff.setEmail(email);
            staff.setPhone(phone);
            staff.setSalary(salary);
            staff.setDepart(departDAO.findById(departId));
            staff.setNotes(notes);

            //Nếu có upload kèm file ảnh thì xóa ảnh cũ, và lưu ảnh mới;
            if (photo != null && !photo.isEmpty())
            {
                //Lấy ra file ảnh cũ của staff và xóa đi
                File oldPhoto = new File(servletContext.getRealPath("/resources/image/staff/" + staff.getPhoto()));
                oldPhoto.deleteOnExit();

                //Tiến hành lưu file ảnh mới vừa upload lên

                //filename = <tên file>_<số thời gian>.<đuôi file>
                String fileName = DataUtil.splitFileName(photo.getOriginalFilename())[0] + "_" + new Date().getTime();
                String fileExtension = DataUtil.splitFileName(photo.getOriginalFilename())[1];

                //Lấy ra đường dẫn cần lưu file
                String path = servletContext.getRealPath("/resources/image/staff/" + fileName + fileExtension);
//                String path = "D:\\J5_test" + fileName + fileExtension;

//                photo.transferTo(new File(path));
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(photo.getBytes());
                fos.flush();
                fos.close();

                //Set tên photo = tên file ảnh vừa upload
                staff.setPhoto(fileName + fileExtension);
            }

            staffDAO.update(staff);
            System.out.println(new Date().getTime() + " C ");
            return JSONUtil.getString(staff);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return  "Lỗi truy xuất dữ liệu: " + e.getErrorCode() + " -> " + e.getMessage();
        }
        catch (IOException e)
        {
            response.setStatus(417);
            e.printStackTrace();
            return  "Lỗi lưu trữ file: " + e.getMessage();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }


    @DeleteMapping(value = "/{staff_id}")
    public String deleteStaff(@PathVariable("staff_id") int staffId, HttpServletResponse response)
    {
        try
        {
            return staffDAO.delete(staffId) + "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }
}
