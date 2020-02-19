package com.daihao.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.model.User;

@Service
public class UserDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getAll() throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            List<User> users = session.createQuery("FROM User").list();
            return users;
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

    public List<User> getCount(String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            String HQL = "FROM User u WHERE u.username LIKE :search OR u.fullname LIKE :search";
            List<User> users = session.createQuery(HQL)
                    .setParameter("search", "%"+search+"%").list();
            return users;
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

    public List<User> getAll(int start, int size, String search) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            String HQL = "FROM User u WHERE u.username LIKE :search OR u.fullname LIKE :search";
            List<User> users = session.createQuery(HQL)
                    .setFirstResult(start)
                    .setMaxResults(size)
                    .setParameter("search", "%"+search+"%").list();
            return users;
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



    public User insert(User user) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            if (findById(user.getUsername()) != null)
                throw new Exception("Tài khoản " + user.getUsername() + " này đã tồn tại");

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return user;
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

    public User update(User user) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
            return user;
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
            int count = session.createQuery("DELETE FROM User WHERE username = :id").setParameter("id", id).executeUpdate();
            session.getTransaction().commit();
            return true;
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

    public User findById(Object username) throws Exception
    {
        Session session = sessionFactory.openSession();
        try
        {
            User user = session.find(User.class, username);
            if (user == null)
                throw new Exception("Tài khoản " + username + " này không tồn tại!");
            return user;
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
