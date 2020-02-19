package com.daihao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.dao.DepartDAO;
import com.daihao.dao.RecordDAO;
import com.daihao.util.JSONUtil;

@Service	
public class RecordService
{
    @Autowired
    RecordDAO recordDAO;

    @Autowired
    DepartDAO departDAO;


    public int getCount() throws Exception
    {
        return recordDAO.getAll().size();
    }

    public int getCountInMonth(int month, String search) throws Exception
    {

       return recordDAO.getAllDTO(0, 10000, month, search).size();
    }


    //Trả về chuỗi JSON của danh sách RecordDTO
    public String getAllDTO_JSON(int start, int size, int month, String search) throws Exception
    {
        return JSONUtil.getString(recordDAO.getAllDTO(start, size, month, search));
    }

    //Trả về String của list Object chứa thống kê - Tổng phòng, Phòng xuất sắc, Phòng kỷ luật
//    public String getStatisticStander(int month) {
//        return JSONUtil.getString(DepartDAO.getStatisticStandar(month));
//    }

    public String getStatisticStander() throws Exception {
        return JSONUtil.getString(departDAO.getStatisticStandar());
    }
}
