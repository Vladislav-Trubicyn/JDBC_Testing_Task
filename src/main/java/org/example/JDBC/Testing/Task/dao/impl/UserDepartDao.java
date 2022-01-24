package org.example.JDBC.Testing.Task.dao.impl;

import org.example.JDBC.Testing.Task.common.DatabaseConnection;
import org.example.JDBC.Testing.Task.dao.Dao;
import org.example.JDBC.Testing.Task.entity.Depart;
import org.example.JDBC.Testing.Task.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDepartDao implements Dao<Depart>
{
    private Connection con = null;
    private static final String GET_DEPART_ID_QUERY = "select * from depart where depart_id = ?";

    public UserDepartDao() throws SQLException
    {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public Optional<Depart> get(int id)
    {
        try (PreparedStatement pstm = con.prepareStatement(GET_DEPART_ID_QUERY))
        {
            pstm.setInt(1, id);

            ResultSet rs = pstm.executeQuery();
            while(rs.next())
            {
                return Optional.of(new Depart(id, rs.getString("depart")));
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public List<Depart> getAll()
    {
        List<Depart> departs = new ArrayList<Depart>();

        try(Statement stm = con.createStatement())
        {
            ResultSet rs = stm.executeQuery("select * from depart");
            while(rs.next())
            {
                departs.add(new Depart(rs.getInt("depart_id"), rs.getString("depart")));
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }

        return departs;
    }

    @Override
    public void save(Depart depart)
    {
        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("insert into depart (depart_id, depart) values (" + depart.getDepartId() + ",'" + depart.getDepart() + "')");
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Depart depart)
    {
        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("update depart set depart_id = " + depart.getDepartId() + ", depart = '" + depart.getDepart() + "' where depart_id = " + depart.getDepartId());
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }

    @Override
    public void delete(Depart depart)
    {
        try (Statement stm = con.createStatement())
        {
            stm.executeUpdate("delete from depart where depart_id = '" + depart.getDepartId() + "'");
        }
        catch(SQLException se)
        {
            se.printStackTrace();
        }
    }
}
