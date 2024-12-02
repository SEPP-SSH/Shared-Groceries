package ssh.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Objects;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() throws Exception{
        if (Objects.isNull(sessionFactory)) {
            try {
                sessionFactory = new Configuration().configure().buildSessionFactory();
            } catch (Throwable ex) {
                throw new Exception("Failed to create session factory", ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() throws Exception{
        //getSessionFactory().close();
    }
}
