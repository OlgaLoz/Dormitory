package Repositories;

import Model.Student;
import Model.StudentStatus;
import Utils.DatabaseUtils;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;

public class StudentRepository {

    public int create(Student item) {

        Connection connection = null;
        PreparedStatement statement = null;
        int resultId = -1;

        try {
            connection = DatabaseUtils.getInstance().getConnection();

            Statement state = connection.createStatement();
            ResultSet resultSet = state.executeQuery(String.format("SELECT ID_Status FROM StudentStatus WHERE StatusName='%s'",
                    item.getStudentStatus()));
            resultSet.next();
            int statusId = resultSet.getInt("ID_Status");

            statement = connection.prepareStatement("INSERT INTO Student (FirstName, MidName, LastName, DateOfBirth, " +
                    "GroupNumber, ID_Status, ID_User) VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, item.getFirstName());
            statement.setString(2, item.getMidName());
            statement.setString(3, item.getLastName());
            statement.setDate(4, item.getDateOfBirth());
            statement.setString(5, item.getGroupNumber());
            statement.setInt(6, statusId);
            statement.setInt(7, item.getUserId());
            statement.executeUpdate();

            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() AS id");
            resultSet.next();
            resultId = resultSet.getInt("id");
            item.setStudentId(resultId);
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {

            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return resultId;
    }

    public Student read(int id) {
        Connection connection = null;
        Statement statement = null;
        Student student = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM Student JOIN StudentStatus " +
                    "ON Student.ID_Status = StudentStatus.ID_Status Where ID_Student = %d", id));
            while (resultSet.next()) {
                student = new Student();
                student.setStudentId(resultSet.getInt("ID_Student"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setMidName(resultSet.getString("MidName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                student.setGroupNumber(resultSet.getString("GroupNumber"));
                student.setStatement(resultSet.getString("Statement"));
                student.setDateOfSettlement(resultSet.getDate("DateOfSettlement"));
                student.setOrder(resultSet.getString("Order"));
                student.setContract(resultSet.getString("Contract"));
                student.setRoomId(resultSet.getInt("ID_Room"));
                student.setUserId(resultSet.getInt("ID_User"));
                student.setStudentStatus(StudentStatus.valueOf(resultSet.getString("StatusName")));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return student;
    }

    public Student getStudentByUserId(int userId) {
        Connection connection = null;
        Statement statement = null;
        Student student = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM Student JOIN StudentStatus " +
                    "ON Student.ID_Status = StudentStatus.ID_Status Where ID_User = %d", userId));
            while (resultSet.next()) {
                student = new Student();
                student.setStudentId(resultSet.getInt("ID_Student"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setMidName(resultSet.getString("MidName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                student.setGroupNumber(resultSet.getString("GroupNumber"));
                student.setStatement(resultSet.getString("Statement"));
                student.setDateOfSettlement(resultSet.getDate("DateOfSettlement"));
                student.setOrder(resultSet.getString("Order"));
                student.setContract(resultSet.getString("Contract"));
                student.setRoomId(resultSet.getInt("ID_Room"));
                student.setUserId(resultSet.getInt("ID_User"));
                student.setStudentStatus(StudentStatus.valueOf(resultSet.getString("StatusName")));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) {  }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return student;
    }

    public ArrayList<Student> readAll() {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM Student JOIN StudentStatus " +
                    "ON Student.ID_Status = StudentStatus.ID_Status;");
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("ID_Student"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setMidName(resultSet.getString("MidName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                student.setGroupNumber(resultSet.getString("GroupNumber"));
                student.setStatement(resultSet.getString("Statement"));
                student.setDateOfSettlement(resultSet.getDate("DateOfSettlement"));
                student.setOrder(resultSet.getString("Order"));
                student.setContract(resultSet.getString("Contract"));
                student.setRoomId(resultSet.getInt("ID_Room"));
                student.setUserId(resultSet.getInt("ID_User"));
                student.setStudentStatus(StudentStatus.valueOf(resultSet.getString("StatusName")));
                students.add(student);
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return students;
    }

    public ArrayList<Student> readAllByStatus(StudentStatus status) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM Student JOIN StudentStatus " +
                    "ON Student.ID_Status = StudentStatus.ID_Status Where StatusName = '%s'", status));
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("ID_Student"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setMidName(resultSet.getString("MidName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                student.setGroupNumber(resultSet.getString("GroupNumber"));
                student.setStatement(resultSet.getString("Statement"));
                student.setDateOfSettlement(resultSet.getDate("DateOfSettlement"));
                student.setOrder(resultSet.getString("Order"));
                student.setContract(resultSet.getString("Contract"));
                student.setRoomId(resultSet.getInt("ID_Room"));
                student.setUserId(resultSet.getInt("ID_User"));
                student.setStudentStatus(status);
                students.add(student);
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return students;
    }

    public ArrayList<Student> readAllByLastName(String lastName) {
        Connection connection = null;
        Statement statement = null;
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM Student JOIN StudentStatus " +
                    "ON Student.ID_Status = StudentStatus.ID_Status Where LastName = '%s'", lastName));
            while (resultSet.next()) {
                Student student = new Student();
                student.setStudentId(resultSet.getInt("ID_Student"));
                student.setFirstName(resultSet.getString("FirstName"));
                student.setMidName(resultSet.getString("MidName"));
                student.setLastName(resultSet.getString("LastName"));
                student.setDateOfBirth(resultSet.getDate("DateOfBirth"));
                student.setGroupNumber(resultSet.getString("GroupNumber"));
                student.setStatement(resultSet.getString("Statement"));
                student.setDateOfSettlement(resultSet.getDate("DateOfSettlement"));
                student.setOrder(resultSet.getString("Order"));
                student.setContract(resultSet.getString("Contract"));
                student.setUserId(resultSet.getInt("ID_User"));
                student.setRoomId(resultSet.getInt("ID_Room"));
                student.setStudentStatus(StudentStatus.valueOf(resultSet.getString("StatusName")));
                students.add(student);
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
        return students;
    }

    public void updateStatus(int studentId, StudentStatus status) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT ID_Status FROM StudentStatus WHERE StatusName='%s'", status));
            resultSet.next();
            int statusId = resultSet.getInt("ID_Status");
            statement.executeUpdate(String.format("UPDATE Student SET ID_Status=%d WHERE ID_Student=%d", statusId, studentId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void updateStatement(int studentId, String statement) {
        if (statement == null){
            return;
        }

        Connection connection = null;
        Statement stmt = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            stmt = connection.createStatement();
            stmt.executeUpdate(String.format("UPDATE Student SET Statement='%s' WHERE ID_Student=%d", statement, studentId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(stmt);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void updateDateOfSettlement(int studentId, Date dateOfSettlement) {
        if (dateOfSettlement == null){
            return;
        }

        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.prepareStatement("UPDATE Student SET DateOfSettlement=? WHERE ID_Student=?");
            statement.setDate(1, dateOfSettlement);
            statement.setInt(2, studentId);
            statement.executeUpdate();
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void updateOrder(int studentId, String order) {
        if (order == null){
            return;
        }

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE Student SET Student.Order='%s' WHERE ID_Student=%d", order, studentId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void updateContract(int studentId, String contract) {
        if (contract == null){
            return;
        }

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE Student SET Contract='%s' WHERE ID_Student=%d", contract, studentId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void updateRoomId(int studentId, int roomId) {
        if (roomId <= 0){
            return;
        }

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(String.format("UPDATE Student SET ID_Room=%d WHERE ID_Student=%d", roomId, studentId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void removeAllFromBlock(int blockId) {

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT ID_Status FROM StudentStatus WHERE StatusName='%s'",
                    StudentStatus.Candidate));
            resultSet.next();
            int statusId = resultSet.getInt("ID_Status");

            resultSet = statement.executeQuery(String.format("SELECT Student.ID_Student AS id FROM Student " +
                    "JOIN Room ON Student.ID_Room = Room.ID_Room WHERE Room.ID_Block = %d" , blockId));

            while (resultSet.next()) {
                int studentId = resultSet.getInt("id");
                statement.executeUpdate(String.format("UPDATE Student SET ID_Room = null, ID_Status = %d WHERE ID_Student=%d",
                        statusId, studentId));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public void delete(int id) {

        Connection connection = null;
        Statement statement = null;
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();
            statement.execute(String.format("DELETE FROM Student WHERE ID_Student = %d", id));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }
}
