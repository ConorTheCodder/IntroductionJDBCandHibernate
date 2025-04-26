package org.example;

import org.example.dao.UserDAO;
import org.example.dao.UserDAOJDBCImpl;
import org.example.service.UserService;
import org.example.service.UserServiceHibernateImpl;
import org.example.service.UserServiceJDBCImpl;
import org.example.util.Util;

public class Main {
    public static void main(String[] args) {

        //script(new UserServiceJDBCImpl());
        script(new UserServiceHibernateImpl());
    }

    private static void script(UserService userService) {
        userService.createUserTable();
        userService.saveUser("testName1", "testLastName1", (byte) 96);
        userService.saveUser("testName2", "testLastName2", (byte) 97);
        userService.saveUser("testName3", "testLastName3", (byte) 98);
        userService.saveUser("testName4", "testLastName4", (byte) 99);

        System.out.println("---");
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("---");
        userService.removeUserById(2L);
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("---");
        userService.clearUserTable();
        userService.getAllUsers().forEach(System.out::println);
        System.out.println("---");
        userService.dropUserTable();
    }
}