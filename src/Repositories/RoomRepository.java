package Repositories;

import Utils.DatabaseUtils;
import Model.Room;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RoomRepository {

    public static int create(Room room) {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.execute(String.format("INSERT INTO Room (ID_Block, RoomNumber, MaxPlacesCount, FreePlacesCount) " +
                    "VALUES ('%s','%s','%s','%s')", room.getBlockId(), room.getRoomNumber(), room.getMaxPlacesCount(), room.getFreePlacesCount()));
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as id");
            resultSet.next();
            room.setRoomId(resultSet.getInt("id"));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }

        return room.getRoomId();
    }

    public static Room read(int id) {
        Connection connection = null;
        Statement statement = null;
        Room room = new Room();

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.executeQuery("SET CHARACTER SET UTF8");
            statement.executeQuery("SET CHARSET UTF8");
            statement.executeQuery("SET NAMES UTF8");

            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM Room WHERE ID_Room = %d", id));
            while(resultSet.next())
            {
                room.setBlockId(resultSet.getInt("ID_Block"));
                room.setRoomNumber(resultSet.getInt("RoomNumber"));
                room.setRoomId(resultSet.getInt("ID_Room"));
                room.setMaxPlacesCount(resultSet.getInt("MaxPlacesCount"));
                room.setFreePlacesCount(resultSet.getInt("FreePlacesCount"));
            }
        }
        catch (NamingException ex) {  }
        catch (SQLException ex) {  }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }

        return room;
    }

    public static void update(Room item) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(String.format("UPDATE Room SET ID_Block = '%d', RoomNumber = '%s', MaxPlacesCount = '%s', " +
                    "FreePlacesCount = '%s' WHERE ID_Room = '%d'", item.getBlockId(), item.getRoomNumber(), item.getMaxPlacesCount(),
                            item.getFreePlacesCount(), item.getRoomId()));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static void updateFreePlacesCount(int roomId, int newFreePlacesCount) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(String.format("UPDATE Room SET FreePlacesCount = '%s' WHERE ID_Room = '%d'", newFreePlacesCount, roomId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static void delete(int id) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.execute(String.format("DELETE FROM Room WHERE ID_Room = '%d'", id));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static void deleteAll(ArrayList<Integer> idlist) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            for (int id : idlist){
                statement.execute(String.format("DELETE FROM Room WHERE ID_Room = '%d'", id));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static void deleteByBlockId(int blockId) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.execute(String.format("DELETE FROM Room WHERE ID_Block = '%d'", blockId));
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static void deleteByDormitoryId(int dormitoryId){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Integer> idList = new ArrayList<Integer>();
        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(String.format("SELECT " +
                    "ID_Room FROM room JOIN block " +
                    "ON block.ID_Block = room.ID_Block WHERE ID_Dormitory = %d", dormitoryId));

            while(resultSet.next()) {
                idList.add(resultSet.getInt("ID_Room"));
                //statement.execute(String.format("DELETE FROM room WHERE ID_Room = '%d';", resultSet.getInt("ID_Room")));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }

        deleteAll(idList);
    }

    public static void addAll(Room[] rooms){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            for (int i = 0; i < rooms.length; i++) {
                statement.execute(String.format("INSERT INTO Room (ID_Block, RoomNumber, MaxPlacesCount, FreePlacesCount) " +
                        "VALUES ('%s','%s','%s','%s')", rooms[i].getBlockId(), rooms[i].getRoomNumber(), rooms[i].getMaxPlacesCount(), rooms[i].getFreePlacesCount()));
                resultSet = statement.executeQuery("SELECT LAST_INSERT_ID() as id");
                resultSet.next();
                rooms[i].setRoomId(resultSet.getInt("id"));
            }
        }
        catch (NamingException ex) { }
        catch (SQLException ex) { }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }
    }

    public static ArrayList<Room> readAll(){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Room> list = new ArrayList<Room>();

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.executeQuery("SET CHARACTER SET UTF8");
            statement.executeQuery("SET CHARSET UTF8");
            statement.executeQuery("SET NAMES UTF8");

            ResultSet resultSet = statement.executeQuery("SELECT ID_Room, blockRoom.ID_Block, blockRoom.ID_Dormitory, RoomNumber, " +
                    "BlockNumber, dormitory.Number, FreePlacesCount, MaxPlacesCount FROM (SELECT ID_Room, RoomNumber, FreePlacesCount," +
                    " room.ID_Block, BlockNumber, ID_Dormitory, MaxPlacesCount FROM room JOIN block ON room.ID_Block = block.ID_Block)" +
                    "AS blockRoom JOIN dormitory ON dormitory.ID_Dormitory = blockRoom.ID_Dormitory WHERE FreePlacesCount > 0 ORDER BY dormitory.Number, " +
                    "BlockNumber, RoomNumber");

            while(resultSet.next()){
                Room room = new Room();
                room.setBlockId(resultSet.getInt("ID_Block"));
                room.setRoomNumber(resultSet.getInt("RoomNumber"));
                room.setRoomId(resultSet.getInt("ID_Room"));
                room.setMaxPlacesCount(resultSet.getInt("MaxPlacesCount"));
                room.setFreePlacesCount(resultSet.getInt("FreePlacesCount"));
                room.setBlockNumber(resultSet.getInt("BlockNumber"));
                room.setDormitoryId(resultSet.getInt("ID_Dormitory"));
                room.setDormitoryNumber(resultSet.getInt("Number"));
                list.add(room);
            }
        }
        catch (NamingException ex) {  }
        catch (SQLException ex) {  }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }

        return list;
    }

    public static ArrayList<Room> readAllByBlockId(int blockId){
        Connection connection = null;
        Statement statement = null;
        ArrayList<Room> list = new ArrayList<Room>();

        try {
            connection = DatabaseUtils.getInstance().getConnection();
            statement = connection.createStatement();

            statement.executeQuery("SET CHARACTER SET UTF8");
            statement.executeQuery("SET CHARSET UTF8");
            statement.executeQuery("SET NAMES UTF8");

            ResultSet resultSet = statement.executeQuery("SELECT ID_Room, RoomNumber, FreePlacesCount," +
                    " MaxPlacesCount FROM room WHERE ID_Block = " + blockId + " ORDER BY RoomNumber");

            while(resultSet.next()){
                Room room = new Room();
                room.setBlockId(blockId);
                room.setRoomNumber(resultSet.getInt("RoomNumber"));
                room.setRoomId(resultSet.getInt("ID_Room"));
                room.setMaxPlacesCount(resultSet.getInt("MaxPlacesCount"));
                room.setFreePlacesCount(resultSet.getInt("FreePlacesCount"));
                list.add(room);
            }
        }
        catch (NamingException ex) {  }
        catch (SQLException ex) {  }
        finally {
            DatabaseUtils.closeStatement(statement);
            DatabaseUtils.closeConnection(connection);
        }

        return list;
    }
}