package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {
    private String sql;
    private Util util = new Util();

    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
        sql = "CREATE TABLE IF NOT EXISTS users (id int auto_increment primary key,name varchar(10),lastName varchar(10),age int(2))";
        try (Statement statement = util.getConnection().createStatement()) {
            int result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица успешно создана");
    }
    public void dropUsersTable() {
        sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = util.getConnection().createStatement()) {
            int result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица удалена");
    }
    public void saveUser(String name, String lastName, byte age) {
        sql = "INSERT INTO users (name,lastName,age) VALUE (?,?,?)";
        try (Connection connection = util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("User" + " с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeUserById(long id) {
        sql = "DELETE FROM users WHERE id=" + id;
        try (Connection connection = util.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            preparedStatement.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            System.out.println("Удален user с id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        User user = new User();
        sql = "SELECT * FROM users";
        try (PreparedStatement preparedStatement = util.getConnection().prepareStatement(sql)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                list.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        sql = "TRUNCATE TABLE users";
        try (Statement statement = util.getConnection().createStatement()) {
            int result = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Таблица очищена");
    }
}

