package com.example.hotel.repository;

import com.example.hotel.connection.ConnectionPool;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.RoomType;
import com.example.hotel.exception.RepositoryException;
import com.example.hotel.specification.Specification;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository extends DbAbstractRepository<Room> implements Repository<Room> {
    @Override
    public void add(Room entity) {
    }

    @Override
    public void remove(Room entity) {
    }

    @Override
    public void update(Room entity) {
    }

    @Override
    public List<Room> query(Specification specification) throws RepositoryException {
        List<Room> rooms = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = specification.specify(connection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int number = resultSet.getInt(1);
                RoomType type = RoomType.valueOf(resultSet.getString(2).toUpperCase());
                int sleeps = resultSet.getInt(3);
                BigDecimal cost = BigDecimal.valueOf(resultSet.getDouble(4));
                Room room = new Room(number, type, sleeps, cost);
                rooms.add(room);
            }
        } catch (InterruptedException | SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return rooms;
    }
}