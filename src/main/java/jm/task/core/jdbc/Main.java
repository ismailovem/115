package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
       UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Михайлов", (byte) 17);
        userService.saveUser("Степан", "Воронов", (byte) 29);
        userService.saveUser("Клава", "Светова", (byte) 27);
        userService.saveUser("Захар", "Рогов", (byte) 76);

        userService.getAllUsers();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();
   }
}
