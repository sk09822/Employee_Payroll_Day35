package com.bz.day35;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Scanner;

public class Employee_Payroll {
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        MySQLConnection mySQLConnection = new MySQLConnection();
        Connection connection = mySQLConnection.getConnection();
        /*** use case 1 to add new employees */
//        addNewEmployees(connection);
        /**use case 2 to implement er diagram*/
//    insertIntoDiagram(connection);
        /** TO CHECK ALL TABLES*/
        checkEmployeeDetails(connection);
        /** TO DELETE EMPLOYEE*/
        deleteEmployeeCascade(connection);
    }

    private static void deleteEmployeeCascade(@NotNull Connection connection) {
        System.out.print("Enter name to delete data = ");
        String name = sc.next();
        String sql = "delete from employee WHERE EmployeeName = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,name);
            System.out.println("**************data deleted successfully**********");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void checkEmployeeDetails(@NotNull Connection connection) {
        String sql = "SELECT EmpID, EmployeeName, Payroll, CompName , DepName from employee INNER JOIN company ON company.compID = employee.comID INNER JOIN department ON department.depID = employee.depID";
        Statement statement = null;
        try {
            statement= connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()){
                System.out.println("ID = " + set.getInt(1)+
                        ", Name = "+ set.getString(2)+
                        ", salary = "+set.getInt(3)+
                        ", company = "+ set.getString(4)+
                        ", department = " + set.getString(5)                );
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (connection != null) connection.close();
                if (statement != null) statement.close();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();

            }
        }
    }

    private static void insertIntoDiagram(@NotNull Connection connection) {
        System.out.print("Employee Name = ");
        String name = sc.next();
        System.out.print("Employee payroll = ");
        Integer pay = sc.nextInt();
        System.out.print("Employee company id = ");
        int compID = sc.nextInt();
        System.out.print("Employee department id = ");
        int depID = sc.nextInt();

        System.out.println("*********to add data in company table******************");
        System.out.print("Enter company ID = ");
        int companyID = sc.nextInt();
        System.out.print("Enter company name = ");
        String companyName = sc.next();

        System.out.println("*********to add data in department table******************");
        System.out.print("Enter department id = ");
        int departmentID = sc.nextInt();
        System.out.print("Enter department name = ");
        String departmentName = sc.next();

        PreparedStatement preparedStatementEmpSQL = null;
        PreparedStatement preparedStatementCompSQL = null;
        PreparedStatement preparedStatementDepSQL = null;


        String empSql = "insert into employee (EmployeeName, payroll , comID, DepID) values (?,?,?,?)";
        String companySql = "insert into company values (?,?)";
        String depSql  ="insert into department values (?,?)";
        try {
            connection.setAutoCommit(false);

            preparedStatementEmpSQL  = connection.prepareStatement(empSql);
            preparedStatementDepSQL = connection.prepareStatement(depSql);

            preparedStatementEmpSQL.setString(1,name);
            preparedStatementEmpSQL.setInt(2,pay);
            preparedStatementEmpSQL.setInt(3,compID);
            preparedStatementEmpSQL.setInt(4,depID);
            preparedStatementEmpSQL.executeUpdate();

            preparedStatementCompSQL = connection.prepareStatement(companySql);
            preparedStatementCompSQL.setInt(1,companyID);
            preparedStatementCompSQL.setString(2,companyName);
            preparedStatementCompSQL.executeUpdate();

            preparedStatementDepSQL = connection.prepareStatement(depSql);
            preparedStatementDepSQL.setInt(1,departmentID);
            preparedStatementDepSQL.setString(2,departmentName);
            preparedStatementDepSQL.executeUpdate();

            connection.commit();
        }catch (SQLException e){
            e.printStackTrace();
            try {
                connection.rollback();
            }catch (SQLException sqlException){
                sqlException.printStackTrace();
            }
        }finally {
            try {
                if(connection != null) connection.close();
                if (preparedStatementCompSQL != null) preparedStatementCompSQL.close();
                if(preparedStatementCompSQL != null) preparedStatementCompSQL.close();
                if(preparedStatementDepSQL != null) preparedStatementDepSQL.close();
            }catch (SQLException sql){
                sql.printStackTrace();
            }
        }

    }

    private static void addNewEmployees(@NotNull Connection connection) {
        System.out.print("Enter id = ");
        int id = sc.nextInt();
        System.out.print("Enter name = ");
        String name = sc.next();
        System.out.print("Enter salary = ");
        int salary = sc.nextInt();
        System.out.print("Enter start date (YYYY-MM-DD) = ");
        String data = sc.next();

        String sql = "insert into EmployeePayroll(id, name , salary ,startDate ) values (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, salary);
            preparedStatement.setString(4, data);
            int i = preparedStatement.executeUpdate();
            System.out.println("The data inserted successfully ");
            System.out.println("rows affected = " + i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }
}

