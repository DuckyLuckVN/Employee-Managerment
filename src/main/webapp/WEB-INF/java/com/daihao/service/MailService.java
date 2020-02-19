package com.daihao.service;

import com.daihao.model.Record;
import com.daihao.model.Staff;
import com.daihao.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Repository
public class MailService
{
    @Autowired
    JavaMailSender mailSender;

    public boolean sendMail(String to, String title, String content)
    {
        try
        {
            //Tạo mail
            MimeMessage mail = mailSender.createMimeMessage();
            //Sử dụng lớp trợ giúp
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.addTo(to);
            helper.setFrom("abcdef@gmail.com");
            helper.setSubject(title);
            mail.setContent(content, "text/html; charset=utf-8");
//            helper.setText(content);
            mailSender.send(mail);
            return true;
            //
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

        return false;
    }
    public boolean sendMailReport(Staff staff, Record record)
    {
        String msg = "Xin chào <b>" + staff.getName() + "</b>, bạn đã nhận được " +
                "một báo cáo loại " + (record.getType() == 0 ? "<b style=\"color: red\">PHIẾU THƯỞNG</b>" : "<b style=\"color: red\">PHIẾU PHẠT</b>") +
                "\n<br><b>- Ngày tạo: </b>" + DateUtil.dateToString(record.getDate(), "dd-MM-yyyy") +
                "\n<br><b>- Nội dung báo cáo: </b>" + record.getReason();
        String title = "Thông báo ghi nhận phiếu báo cáo số #" + record.getId();

        return sendMail(staff.getEmail(), title, msg);
    }
}
