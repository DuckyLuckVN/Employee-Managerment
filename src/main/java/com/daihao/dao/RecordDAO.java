package com.daihao.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.DTO.RecordDTO;
import com.daihao.model.Record;

@Service
public class RecordDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private StaffDAO staffDAO;

    //Lấy toàn bộ dữ liệu từ bảng Record
    public List<Record> getAll() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Record> records = new ArrayList<>();
            records = session.createQuery("FROM Record").list();
            return records;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    //Lấy toàn bộ dữ liệu từ bảng Record theo tháng
    public List<Record> getAllInMonth(int month) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Record> records = new ArrayList<>();
            records = session.createQuery("FROM Record r WHERE MONTH(r.date) = :month").setParameter("month", month).list();
            return records;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    //Lấy ra toàn bộ record của nhân viên có id = staffId và số tháng truyền vào
    //Nếu tháng = 0 thì lấy ra record nhân viên đó trong cả năm
    public List<Record> getRecordsByStaffId(int staffId, int month) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Record> records = new ArrayList<>();

            String HQL = "FROM Record r WHERE r.staff.id = :staffId AND YEAR(r.date) = YEAR(current_date())";
            if (month > 0)  { HQL += " AND MONTH(r.date) = :month"; }

            Query query = session.createQuery(HQL);

            query.setParameter("staffId", staffId);
            if (month > 0)  { query.setParameter("month", month); }



            records = query.list();
            return records;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    //Lấy dữ liệu từ bắt đầu từ start và lấy ra rows dòng dữ liệu
    public List<Record> getAll(int start, int rows) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Record> records = new ArrayList<>();
            Query query = session.createQuery("FROM Record");
            query.setFirstResult(start).setMaxResults(rows);
            records = query.list();
            return records;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    //Lấy dữ liệu từ bắt đầu từ start và lấy ra rows dòng dữ liệu DTO với tháng truyền vào
    public List<RecordDTO> getAllDTO(int start, int size, int month, String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<RecordDTO> recordDTOs = new ArrayList<>();
            Query query = session.createNativeQuery("{call sp_getRecordDTO(:month, :start, :size, :search)}");

            query.setParameter("month", month);
            query.setParameter("start", start);
            query.setParameter("size", size);
            query.setParameter("search", search);

            query.setResultTransformer(Transformers.aliasToBean(RecordDTO.class));
            recordDTOs = query.list();
            return recordDTOs;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public Object[] getStatisticStandard(int month) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Object[] statistic = (Object[]) session.createNativeQuery("{call sp_getStatisticRecordStandardInMonth(:month)}")
                    .setParameter("month", month)
                    .list().get(0);
            return statistic;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }


    public Record insert(Record record) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            record.setStaff(staffDAO.findById(record.getStaff().getId()));
            session.save(record);
            session.getTransaction().commit();
            return record;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public Record update(Record record) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            record.setStaff(staffDAO.findById(record.getStaff().getId()));
            session.update(record);
            session.getTransaction().commit();
            return record;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public boolean delete(Object id) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            int count = session.createQuery("DELETE FROM Record WHERE id = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return count > 0;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public Record findById(Object id) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Record record = session.find(Record.class, id);
            return record;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
        finally
        {
            session.close();
        }
    }


}
