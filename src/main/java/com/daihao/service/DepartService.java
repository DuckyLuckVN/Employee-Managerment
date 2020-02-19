package com.daihao.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.DTO.DepartDTO;
import com.daihao.dao.DepartDAO;
import com.daihao.model.Depart;
import com.daihao.util.JSONUtil;

@Service
public class DepartService
{
    @Autowired
    DepartDAO departDAO;

    public List<Depart> getAll() throws Exception {
        return departDAO.getAll();
    }

    public int getCount(String search) throws Exception
    {
        return departDAO.getAllDTO(search).size();
    }

    //Trả về danh sách  list DepartDTO
    public List<DepartDTO> getAllDTO(int start, int size, String search) throws Exception
    {
        return departDAO.getAllDTO(start, size, search);
    }

    //Trả về chuỗi JSON của danh sách DepartDTO
    public String getAllDTO_JSON(int start, int size, String search) throws Exception
    {
        return JSONUtil.getString(departDAO.getAllDTO(start, size, search));
    }

    //Trả về String của list Object chứa thống kê - Tổng phòng, Phòng xuất sắc, Phòng kỷ luật
    public String getStatisticStander(int month) throws Exception {
        return JSONUtil.getString(departDAO.getStatisticStandar(month));
    }

    public String getStatisticStander() throws Exception {
        return JSONUtil.getString(departDAO.getStatisticStandar());
    }
}
