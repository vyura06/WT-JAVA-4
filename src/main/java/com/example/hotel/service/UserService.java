package com.example.hotel.service;

import com.example.hotel.entity.Booking;
import com.example.hotel.entity.Room;
import com.example.hotel.entity.User;
import com.example.hotel.exception.RepositoryException;
import com.example.hotel.exception.ServiceException;
import com.example.hotel.repository.BookingRepository;
import com.example.hotel.repository.UserRepository;
import com.example.hotel.specification.Specification;
import com.example.hotel.specification.UserLoginSpecification;

import java.time.LocalDate;
import java.util.List;

public class UserService {

    public List<User> login(String login, String password) throws ServiceException {
        List<User> users;

        Specification specification = new UserLoginSpecification(login, password);
        UserRepository repository = new UserRepository();
        try {
            users = repository.query(specification);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return users;
    }

    public User register(String login, String password, String email, String firstName,
                         String lastName, String phoneNumber, String country, String birthday, boolean isAdmin)
            throws ServiceException {

        UserRepository repository = new UserRepository();
        User user = new User(login, password, firstName, lastName, email, country, phoneNumber, LocalDate.parse(birthday), isAdmin);
        try {
            repository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public Booking addBooking(String userLogin, String arrival, String departure, Room room, int guestsNumber, String guestName) throws ServiceException {
        BookingRepository repository = new BookingRepository();
        Booking booking = new Booking(userLogin, LocalDate.parse(arrival), LocalDate.parse(departure), room, guestsNumber, guestName);
        try {
            repository.add(booking);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return booking;
    }
}
