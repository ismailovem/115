package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
       UserService userDaoHibernate = new UserServiceImpl();

        userDaoHibernate.createUsersTable();

        userDaoHibernate.saveUser("Иван", "Михайлов", (byte) 17);
        userDaoHibernate.saveUser("Степан", "Воронов", (byte) 29);
        userDaoHibernate.saveUser("Клава", "Светова", (byte) 27);
        userDaoHibernate.saveUser("Захар", "Рогов", (byte) 76);

        userDaoHibernate.getAllUsers();
        userDaoHibernate.removeUserById(1);
        userDaoHibernate.cleanUsersTable();
        userDaoHibernate.dropUsersTable();
   }
}
