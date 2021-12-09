package com.example.hotel.repository;


import com.example.hotel.connection.ConnectionPool;
import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.RoomType;
import com.example.hotel.exception.RepositoryException;
import com.example.hotel.specification.Specification;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends DbAbstractRepository<Booking> implements Repository<Booking> {
    private static final String ADD_BOOKING = "INSERT INTO booking(user_login, room_number," +
            " arrival_date, departure_date, number_of_guests, guests)" +
            "VALUES (?,?,?,?,?,?);";

    private static final String DELETE_BOOKING = "DELETE FROM booking WHERE booking.booking_id = ?";

    @Override
    public void add(Booking booking) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = preparedStatement(ADD_BOOKING);
            preparedStatement.setString(1, booking.getUserLogin());
            preparedStatement.setInt(2, booking.getRoom().getNumber());
            preparedStatement.setString(3, booking.getArrival().toString());
            preparedStatement.setString(4, booking.getDeparture().toString());
            preparedStatement.setInt(5, booking.getGuestsNumber());
            preparedStatement.setString(6, booking.getGuestName());
            preparedStatement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public void remove(Booking booking) throws RepositoryException {
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = preparedStatement(DELETE_BOOKING);
            preparedStatement.setInt(1, booking.getBookingId());
            preparedStatement.executeUpdate();
        } catch (InterruptedException | SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
    }

    @Override
    public void update(Booking entity) {
    }

    @Override
    public List<Booking> query(Specification specification) throws RepositoryException {
        List<Booking> bookings = new ArrayList<>();
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Booking booking;

        try {
            connection = ConnectionPool.getInstance().takeConnection();
            preparedStatement = specification.specify(connection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int bookingId = resultSet.getInt(1);
                String userLogin = resultSet.getString(2);
                int roomNumber = resultSet.getInt(3);
                LocalDate arrival = LocalDate.parse(resultSet.getString(4));
                LocalDate departure = LocalDate.parse(resultSet.getString(5));
                int guests = resultSet.getInt(6);
                String guestsName = resultSet.getString(7);
                RoomType roomType = RoomType.valueOf(resultSet.getString(8).toUpperCase());
                int sleeps = resultSet.getInt(9);
                BigDecimal cost = BigDecimal.valueOf(resultSet.getDouble(10));

                Room room = new Room(roomNumber, roomType, sleeps, cost);
                booking = new Booking(bookingId, userLogin, arrival, departure, room, guests, guestsName);

                bookings.add(booking);
            }
        } catch (InterruptedException | SQLException e) {
            throw new RepositoryException(e);
        } finally {
            closeResultSet(resultSet);
            closeStatement(preparedStatement);
            closeConnection(connection);
        }
        return bookings;
    }
}