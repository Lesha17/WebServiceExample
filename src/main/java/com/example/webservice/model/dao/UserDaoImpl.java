package com.example.webservice.model.dao;

import com.example.webservice.model.connection.ConnectionFactory;
import com.example.webservice.model.entities.User;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserDaoImpl implements UserDao{

    private static final String FIND_USER_BY_LOGIN_SQL = "SELECT (login) FROM users where login = ?;";
    private static final String CREATE_USER_SQL = "INSERT INTO users (login, password, balance) VALUES (?, ?, ?);";
    private static final String CHECK_PASSWORD_SQL = "SELECT CASE WHEN password = ? THEN 1 ELSE 0 END password_correct\n" +
            "    FROM users\n" +
            "    WHERE login = ?;";
    private static final String GET_BALANCE_SQL = "SELECT (balance) FROM users WHERE login = ?";

    private final ConnectionFactory connectionFactory;

    @Inject
    public UserDaoImpl(ConnectionFactory connectionFactory) throws SQLException {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void createIfNotExists(User user) throws ServerException, UserAlreadyExistsException {
        try(Connection connection = connectionFactory.getConnection()) {

            PreparedStatement findUserStatement = connection.prepareStatement(FIND_USER_BY_LOGIN_SQL);
            PreparedStatement createUserStatement = connection.prepareStatement(CREATE_USER_SQL);

            try {
                findUserStatement.setString(1, user.getLogin());
                ResultSet queryResult = findUserStatement.executeQuery();

                if(queryResult.next()) {
                   throw new UserAlreadyExistsException();
                }

                createUserStatement.setString(1, user.getLogin());
                createUserStatement.setString(2, user.getPassword());
                createUserStatement.setDouble(3, user.getBalance());

                createUserStatement.executeUpdate();
            } finally {
                findUserStatement.close();
                createUserStatement.close();
            }
        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public double checkPasswordAnGetBalance(String login, String password) throws ServerException, IncorrectPasswordException, UserNotExistsException {
        try(Connection connection = connectionFactory.getConnection()) {
            PreparedStatement checkPasswordStatement = connection.prepareStatement(CHECK_PASSWORD_SQL);
            PreparedStatement getBalanceStatement = connection.prepareStatement(GET_BALANCE_SQL);

            try {
                checkPasswordStatement.setString(1, password);
                checkPasswordStatement.setString(2, login);

                ResultSet checkPasswordResult = checkPasswordStatement.executeQuery();
                if(checkPasswordResult.next()) {
                    if(checkPasswordResult.getInt(1) == 1) {
                        getBalanceStatement.setString(1, login);
                        ResultSet getBalanceResult = getBalanceStatement.executeQuery();
                        if(getBalanceResult.next()) {
                            return getBalanceResult.getDouble(1);
                        } else {
                            throw new AssertionError();
                        }
                    } else {
                        throw new IncorrectPasswordException();
                    }
                } else {
                    throw new UserNotExistsException();
                }
            } finally {
                checkPasswordStatement.close();
                getBalanceStatement.close();
            }

        } catch (SQLException e) {
            throw new ServerException(e);
        }
    }

    @Override
    public Long create(User entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public User read(Long key) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void update(Long key, User entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long key) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Map<Long, User> getAll() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
