package com.daihao.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.DTO.StaffTopDTO;
import com.daihao.model.Staff;
@Service
public class StaffDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DepartDAO departDAO;

    public List<Staff> getAll() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Staff> staffs = session.createQuery("FROM Staff").list();
            return staffs;
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

    public List<Staff> getAll(int start, int amount, String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Query query = session.createQuery("SELECT s FROM Staff s LEFT JOIN Depart d ON d.id = s.depart.id WHERE CAST(s.id as string) LIKE :search " +
                    "OR s.name LIKE :search " +
                    "OR s.phone LIKE :search " +
                    "OR s.email LIKE :search " +
                    "OR d.id LIKE :search " +
                    "OR d.name LIKE :search");
            query.setFirstResult(start);
            query.setMaxResults(amount);
            query.setParameter("search", "%" + search + "%");

            List<Staff> staffs = query.list();
            return staffs;
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

    public List<Staff> getAll(String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Staff> staffs = session.createQuery("SELECT s FROM Staff s LEFT JOIN Depart d ON d.id = s.depart.id WHERE CAST(s.id as string) LIKE :search " +
                    "OR s.name LIKE :search " +
                    "OR s.phone LIKE :search " +
                    "OR s.email LIKE :search " +
                    "OR d.id LIKE :search " +
                    "OR d.name LIKE :search").setParameter("search", "%" + search + "%").list();

            return staffs;
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

    public List<Staff> getAllInDepart(String departId) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Staff> staffs = session.createQuery("FROM Staff s Where s.depart.id = :departId")
                    .setParameter("departId", departId).list();
            return staffs;
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


    public Staff insert(Staff staff) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.save(staff);
            session.getTransaction().commit();
            return staff;
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

    public Staff update(Staff staff) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.update(staff);
            session.getTransaction().commit();
            return staff;
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
            int count = session.createQuery("DELETE FROM Staff WHERE id = :id").setParameter("id", id).executeUpdate();
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

    //Trả về thống kê tiêu chuẩn
    //Object[0]: Tổng nhân viên
    //Object[1]: Tổng số Nam
    //Object[2]: Tổng số Nữ
    public Object[] getStatisticStandard() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Object[] statistic = null;
            statistic = (Object[]) session.createNativeQuery("{call sp_getStatisticStaffStandard()}").list().get(0);
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

    public List<StaffTopDTO> getTop10WithGoodRecord(int month)
    {

        Session session = sessionFactory.openSession();
        try
        {
            List<StaffTopDTO> list = new ArrayList<>();
            Query query = session.createNativeQuery("{call sp_getTop10StaffWithGoodRecord(:month)}");
            query.setResultTransformer(Transformers.aliasToBean(StaffTopDTO.class));
            query.setParameter("month", month);
            list = query.list();
            return list;
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
    public List<Staff> searchByNameOrId(Object search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<Staff> staffs = session.createQuery("FROM Staff WHERE cast(id as string) LIKE :search OR name LIKE :search")
                    .setParameter("search", "%"+search+"%").list();
            return staffs;
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

    public List<Staff> searchByNameOrId(Object search, int start, int amount) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Query query = session.createQuery("FROM Staff WHERE cast(id as string) LIKE :search OR name LIKE :search")
                    .setParameter("search", "%"+search+"%");
            query.setFirstResult(start);
            query.setMaxResults(amount);

            List<Staff> staffs = query.list();
            return staffs;
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

    public Staff findById(Object id) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            Staff staff = session.find(Staff.class, id);
            return staff;
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
