package com.daihao.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daihao.model.RememberUser;

@Service
public class RememberUserDAO
{
    @Autowired
    private SessionFactory sessionFactory;

    public RememberUser insert(RememberUser rememberUser)
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.save(rememberUser);
            session.getTransaction().commit();
            return rememberUser;
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

    public RememberUser update(RememberUser rememberUser)
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.update(rememberUser);
            session.getTransaction().commit();
            return rememberUser;
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

    public RememberUser findById(Object id)
    {
        System.out.println("find rememebr id: " + id);
        Session session = sessionFactory.openSession();
        try
        {
            return session.find(RememberUser.class, Integer.parseInt(id.toString()));
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

    public boolean delete(Object id)
    {
        Session session = sessionFactory.openSession();
        try
        {
            session.beginTransaction();
            session.createQuery("DELETE FROM RememberUser r WHERE r.id = :id").setParameter("id", Integer.parseInt(id.toString())).executeUpdate();
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
}
