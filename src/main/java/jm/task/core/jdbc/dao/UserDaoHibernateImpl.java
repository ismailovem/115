package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Util util = new Util();
    private String sql;
    private Transaction transaction = null;


    @Override
    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key,name varchar(10),lastName varchar(10),age int(2))";
        try (Session session = util.getSessionConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("Таблица создана");
    }

    @Override
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        try (Session session = util.getSessionConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("Таблица удалена");
    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        try (Session session = util.getSessionConnection().openSession()) {
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        System.out.println("User" + " с именем - " + name + " добавлен в базу данных");
    }
    @Override
    public void removeUserById(long id) {
        try (Session session = util.getSessionConnection().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User where id = :id")
                    .setParameter("id", id).executeUpdate();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("Удален user с id = " + id);
    }
    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Session session = util.getSessionConnection().openSession()) {
            list = session.createQuery("from User", User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        for (User user : list) {
            System.out.println(user.toString());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = util.getSessionConnection().openSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        System.out.println("Таблица очищена");
    }
}