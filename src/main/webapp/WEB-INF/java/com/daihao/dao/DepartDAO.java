package com.daihao.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.DTO.DepartDTO;
import com.daihao.DTO.DepartTopDTO;
import com.daihao.model.Depart;
import com.daihao.util.DateUtil;

@Service
public class DepartDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public List<Depart> getAll() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Depart> departs = new ArrayList<>();
            Query query = session.createQuery("FROM Depart");
            departs = query.list();

            return departs;
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

    public List<Depart> getAll(int start, int rows) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Depart> departs = new ArrayList<>();
            Query query = session.createQuery("FROM Depart");
            query.setFirstResult(start);
            query.setMaxResults(rows);
            departs = query.list();

            return departs;
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

    public List<DepartDTO> getAllDTO(String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<DepartDTO> departDTOs = new ArrayList<>();
            Query query = session.createQuery("SELECT new com.daihao.DTO.DepartDTO(d.id, d.name, COUNT(s.id)) " +
                                                "FROM Depart d left join Staff s on d.id = s.depart.id " +
                                                "WHERE d.id LIKE :search OR d.name LIKE :search " +
                                                "GROUP BY d.id, d.name");
            query.setParameter("search", "%" + search + "%");
            departDTOs = query.list();
            return departDTOs;
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

    public List<DepartDTO> getAllDTO(int start, int rows, String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<DepartDTO> departs = new ArrayList<>();

            Query query = session.createQuery("SELECT new com.daihao.DTO.DepartDTO(d.id, d.name, COUNT(s.id)) " +
                    "FROM Depart d left join Staff s on d.id = s.depart.id " +
                    "WHERE d.id LIKE :search OR d.name LIKE :search " +
                    "GROUP BY d.id, d.name");
            query.setFirstResult(start);
            query.setMaxResults(rows);
            query.setParameter("search", "%" + search + "%");
            departs = query.list();

            return departs;
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

    public Depart insert(Depart depart) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            if(session.find(Depart.class, depart.getId()) != null)
                throw new Exception("Phòng ban " + depart.getId() + " này đã tồn tại!");

            session.beginTransaction();
            session.save(depart);
            session.getTransaction().commit();
            return depart;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            throw e;
        }
        finally
        {
            session.close();
        }
    }

    public Depart update(Depart depart) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.update(depart);
            session.getTransaction().commit();
            return depart;
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
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
            session.delete(session.find(Depart.class, id));
            session.getTransaction().commit();
        }
        catch (Exception e)
        {
            session.getTransaction().rollback();
            e.printStackTrace();
            throw  e;
        }
        finally
        {
            session.close();
        }
//        int count = session.createQuery("DELETE FROM Depart d WHERE d.id = :id").setParameter("id", id).executeUpdate();
        return true;
    }

    public Depart findById(Object id) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Depart depart = session.get(Depart.class, (Serializable) id);
            return depart;
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

    //Trả về thống kê tiêu chuẩn với báo cáo của phòng ban trong tháng | tổng số, số xuất sắc, số kỷ luật
    public Object[] getStatisticStandar() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            int thisMonth = DateUtil.getMonth(new Date());
            Object[] data = (Object[]) session.createNativeQuery("{call sp_getStatisticDepartStandardInMonth(:month)}")
                    .setParameter("month", thisMonth)
                    .list().get(0);
            return data;
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

    //Trả về danh sách top DepartTopDTO top 10 có điểm thưởng cao nhất trong tháng truyền vào, nếu tháng = 0 thì lấy cả năm
    public List<DepartTopDTO> getTop10WithGoodRecord(int month) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            System.out.println("a");
            List<DepartTopDTO> list = new ArrayList<>();
            list = session.createNativeQuery("{call sp_getTop10DepartWithGoodRecord(:month)}")
                    .setParameter("month", month)
                    .setResultTransformer(Transformers.aliasToBean(DepartTopDTO.class))
                    .list();
            System.out.println(list.size());
            return list;
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

    //Trả về thống kê tiêu chuẩn với báo cáo của phòng ban trong tháng truyền vào | tổng số, số xuất sắc, số kỷ luật
    public Object[] getStatisticStandar(int reportMonth) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Object[] data = (Object[]) session.createNativeQuery("{call sp_getStatisticDepartStandardInMonth(:month)}").setParameter("month", reportMonth).list().get(0);
            return data;
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
}
