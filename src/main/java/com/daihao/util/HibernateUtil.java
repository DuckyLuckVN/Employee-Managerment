package com.daihao.util;

import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;

import com.daihao.model.Depart;
import com.daihao.model.Record;
import com.daihao.model.RememberUser;
import com.daihao.model.Staff;
import com.daihao.model.User;

public class HibernateUtil
{
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory()
    {
        if (sessionFactory == null )
        {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Properties settings = new Properties();

            settings.put(Environment.DRIVER, "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            settings.put(Environment.URL, "jdbc:sqlserver://localhost;databaseName=J5_ASM");
            settings.put(Environment.USER, "J5_ASM_QTV");
            settings.put(Environment.PASS, "123");
            settings.put(Environment.POOL_SIZE, 50);
            settings.put(Environment.SHOW_SQL, true);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.SQLServer2008Dialect");
            registry = registryBuilder.applySettings(settings).build();
            MetadataSources sources = new MetadataSources(registry);
            sources.addAnnotatedClass(User.class)
                    .addAnnotatedClass(Depart.class)
                    .addAnnotatedClass(Staff.class)
                    .addAnnotatedClass(Record.class)
                    .addAnnotatedClass(RememberUser.class);


            Metadata metadata = sources.buildMetadata();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        }
        return sessionFactory;
    }

    public static void Shutdown()
    {
        if (registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }

    public static Query mapping(Query query, Object ... params)
    {
        if (query != null)
        {
            for (int i = 0; i < params.length; i++)
            {
                query.setParameter(i, params[i]);
            }
        }
        return query;
    }

    public static void main(String[] args) {
        Session ss = getSessionFactory().openSession();
        ss.beginTransaction();

        List<Staff> list = ss.createQuery("FROM Staff").list();

        System.out.println(list.size());
//        System.out.println(list.get(0).getStaffs().get(0).getName());
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();
//        session.createQuery("From Staff").list();
//        session.getTransaction().commit();;
//        session.close();
//        System.out.println("1");
//
//        Session session2 = HibernateUtil.getSessionFactory().openSession();
//        session2.beginTransaction();
//        session2.createQuery("From Staff").list();
//        session2.getTransaction().commit();;
//        session2.close();
//        System.out.println("2");

    }
}
