package com.daihao.controller.api;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daihao.dao.RecordDAO;
import com.daihao.model.Record;
import com.daihao.model.Staff;
import com.daihao.service.MailService;
import com.daihao.service.RecordService;
import com.daihao.util.JSONUtil;

@RestController
@RequestMapping(value = "/api/record", produces = "json/application; charset=UTF-8")
public class RecordAPI
{
    @Autowired
    RecordService service;

    @Autowired
    RecordDAO recordDAO;

    @Autowired
    MailService mailService;

//    GET ALL PHÂN TRANG
    @GetMapping(params = {"start", "size", "month", "search"})
    public String getDepart(@RequestParam("start") int start,
                            @RequestParam("size") int size,
                            @RequestParam("search") String search,
                            @RequestParam("month") int month,
                            HttpServletResponse response)
            throws SQLException
    {
        try
        {
            return service.getAllDTO_JSON(start, size, month, search);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy ra tổng số record theo tháng
    @GetMapping(value = "/count", params = {"month", "search"})
    public String getTotal(@RequestParam("month") int month,
                           @RequestParam("search") String search,
                           HttpServletResponse response) throws SQLException {
        if (search.length() == 0)
            search = null;
        try
        {
            return service.getCountInMonth(month, search) + "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    @GetMapping(value = "/statistic/{month}")
    public String getStatisticStandard(@PathVariable("month") int month, HttpServletResponse response)
    {
        try
        {
            return JSONUtil.getString(recordDAO.getStatisticStandard(month));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Lấy ra tất cả record của {staffId} trong tháng month truyền vào
    @GetMapping(value = "/staff/{staffId}")
    public String getStatisticStandard(@PathVariable("staffId") int staffId,
                                       @RequestParam("month") int month,
                                       HttpServletResponse response)
    {
        try
        {
            return JSONUtil.getString(recordDAO.getRecordsByStaffId(staffId, month));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Trả về đối tượng record có id {recordId}
    @GetMapping("/{recordId}")
    public String getRecord(@PathVariable("recordId") int recordId, HttpServletResponse response)
    {
        try
        {
            return JSONUtil.getString(recordDAO.findById(recordId));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //thêm một record mới
    @PostMapping
    public String getRecord(@RequestParam("type") int type,
                            @RequestParam("reason") String reason,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                            @RequestParam("staffId") int staffId,
                            HttpServletResponse response)
    {
        try
        {
            Staff staff = new Staff();
            staff.setId(staffId);
            //Lấy về staff có id truyền vào
            System.out.println("type record: " + type);
            Record record = new Record(0, type, reason, date, staff);
            recordDAO.insert(record);

            //gửi mail thông báo cho nhân viên
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mailService.sendMailReport(record.getStaff(), record);
                }
            }).start();

            return JSONUtil.getString(record);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //cập nhật một record
    @PostMapping(params = {"update"})
    public String getRecord(@RequestParam("recordId") int recordId,
                            @RequestParam("type") int type,
                            @RequestParam("reason") String reason,
                            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                            @RequestParam("staffId") int staffId,
                            HttpServletResponse response)
    {
        try
        {
            System.out.println("đã vào update");
            Staff staff = new Staff();
            staff.setId(staffId);

            Record record = recordDAO.findById(recordId);
            record.setType(type);
            record.setDate(date);
            record.setStaff(staff);
            record.setReason(reason);

            recordDAO.update(record);
            return JSONUtil.getString(record);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            response.setStatus(417);
            return e.getMessage();
        }
    }

    //Xóa đối tượng record có id {recordId}
    @DeleteMapping("/{recordId}")
    public String deleteRecord(@PathVariable("recordId") int recordId, HttpServletResponse response)
    {
        try
        {
            recordDAO.delete(recordId);
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
