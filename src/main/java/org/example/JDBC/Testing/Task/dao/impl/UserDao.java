package org.example.JDBC.Testing.Task.dao.impl;

import org.example.JDBC.Testing.Task.common.DatabaseConnection;
import org.example.JDBC.Testing.Task.dao.Dao;
import org.example.JDBC.Testing.Task.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDao implements Dao<User>
{
    private Connection con = null;
    private UserDepartDao userDepartDao = new UserDepartDao();

    private static final String GET_DEPART_ID_QUERY = "select * from depart where depart_id = ?";

    public UserDao() throws SQLException
    {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Optional<User> get(int id)
    {
        try (Statement stm = con.createStatement())
        {
            ResultSet rs = stm.executeQuery("select * from users where id = " + id);
            while (rs.next())
            {
                User user = new User(id, rs.getString("username"), userDepartDao.get(rs.getInt("depart_id")).orElse(null));
                return Optional.of(user);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAll()
    {
        List<User> userList = new ArrayList<User>();

        try (Statement stm = con.createStatement())
        {
            ResultSet rs = stm.executeQuery("select * from users");
            while (rs.next())
            {
                userList.add(new User(rs.getInt("id"), rs.getString("username"), userDepartDao.get(rs.getInt("depart_id")).orElse(null)));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return userList;
    }

    @Override
    public void save(User user)
    {
        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("insert into users (username, depart_id) values ('" + user.getUsername() + "','" + user.getDepart_id().getDepartId() + "')");
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }

    @Override
    public void update(User user)
    {
        userDepartDao.update(user.getDepart_id());

        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("update users set username = '" + user.getUsername() + "', depart_id = " + user.getDepart_id().getDepartId() + " where id = " + user.getId());
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }

    @Override
    public void delete(User user)
    {
        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("delete from users where id = " + user.getId());
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }
}
