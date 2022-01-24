package org.example.JDBC.Testing.Task;

import org.example.JDBC.Testing.Task.dao.impl.UserDao;
import org.example.JDBC.Testing.Task.dao.impl.UserDepartDao;
import org.example.JDBC.Testing.Task.entity.Depart;
import org.example.JDBC.Testing.Task.entity.User;

import java.sql.SQLException;

public class JdbcMain
{
    public static void main(String[] args) throws SQLException
    {
        UserDao ud = new UserDao();

        //System.out.println(ud.get(1));
        //System.out.println(ud.getAll());

        /*Depart departFromDB = new UserDepartDao().get(3).get();
        ud.save(new User(0, "jon", departFromDB));
        System.out.println(ud.getAll());*/

        User userFromDB = ud.get(2).get();
        userFromDB.setUsername(userFromDB.getUsername().replace("_edit",""));
        Depart departFromDB = new UserDepartDao().get(1).get();
        //departFromDB.setDepart("salary");
        userFromDB.setDepart_id(departFromDB);
        ud.update(userFromDB);
        //ud.delete(userFromDB);
        System.out.println(ud.getAll());

        /*Depart departFromDB = new UserDepartDao().get(4).get();
        departFromDB.setDepart(departFromDB.getDepart() + "_edit");
        //udd.save(new Depart(4, "bad"));
        //udd.update(departFromDB);
        udd.delete(departFromDB);
        System.out.println(udd.getAll());*/

        //CONSTRAINT
        //alter table users add CONSTRAINT FK_User_DepartID FOREIGN KEY(depart_id) REFERENCES depart(depart_id) ON DELETE CASCADE;
    }
}
