package com.example.hotel.repository;

import com.example.hotel.entity.Entity;
import com.example.hotel.exception.RepositoryException;

import java.sql.*;

public abstract class DbAbstractRepository<T extends Entity> implements Repository<T> {
    protected Connection connection;

    public PreparedStatement preparedStatement(String sql) throws RepositoryException {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RepositoryException(e);
        }
        return ps;
    }

    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }
}
