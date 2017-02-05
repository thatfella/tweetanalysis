package org.thatfella.analyzers;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class DBConnect {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //method to get all our previous analysis results
    public void getAll() {
        SqlRowSet rs = null;
        try {

            rs = jdbcTemplate.queryForRowSet("SELECT * FROM mytwanres");
            System.out.println("Made it ");
            while (rs.next()) {
                System.out.println(rs.getString("idmytweetres"));
                System.out.println(rs.getString("hashtagmytweetres"));
                System.out.println(rs.getString("resultmytweetres"));
            }
            System.out.println("Done OUTPUT");
        } catch (Exception e) {
            System.out.println("OOOPS! Exception here");
            System.out.println(e.getStackTrace());
        }
    }

    public void addAnResult(String hsh, String res) {
        //method to get insert our new analysis results
        jdbcTemplate.update("INSERT INTO mytwanres(hashtagmytweetres,resultmytweetres) VALUES(?,?);", new Object[]{hsh, res});

        System.out.println("Added that resultString to results table");

    }
}
