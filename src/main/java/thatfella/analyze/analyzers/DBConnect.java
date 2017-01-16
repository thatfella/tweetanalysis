package thatfella.analyze.analyzers;


import com.mysql.fabric.jdbc.FabricMySQLDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import javax.sql.RowSet;
import java.sql.*;


public class DBConnect {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//method to get all our previous analysis results
    public void getAll() {
        SqlRowSet rs = null;
        try {

            rs = jdbcTemplate.queryForRowSet("SELECT * FROM mytweetres");
            System.out.println("Made it ");
            while (rs.next()) {
                System.out.println(rs.getString("idmytweetres"));
                System.out.println(rs.getString("hashtagmytweetres"));
                System.out.println(rs.getString("timemytweetres"));
                System.out.println(rs.getString("resultmytweetres"));
            }
        } catch (Exception e) {
            System.out.println("OOOPS! Exception here");
            System.out.println(e.getStackTrace());
        }
    }

    public void addAnResult(String hsh, String res) {
        //method to get insert our new analysis results
        jdbcTemplate.update("INSERT INTO mytweetres(hashtagmytweetres,timemytweetres,resultmytweetres) VALUES(?, CURRENT_DATE ,?);", new Object[]{hsh, res});

        System.out.println("Added that resultString ");

    }

}
