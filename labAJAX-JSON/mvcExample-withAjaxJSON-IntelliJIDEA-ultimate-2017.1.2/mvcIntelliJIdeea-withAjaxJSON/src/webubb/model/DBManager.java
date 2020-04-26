package webubb.model;

import webubb.domain.Asset;
import webubb.domain.Question;
import webubb.domain.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by forest.
 */
public class DBManager {
    private Statement stmt;

    public DBManager() {
        connect();
    }

    public void connect() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Users", "postgres", "nalardai");
            stmt = con.createStatement();
        } catch(Exception ex) {
            System.out.println("eroare la connect:"+ex.getMessage());
            ex.printStackTrace();
        }
    }

    public User authenticate(String username, String password) {
        ResultSet rs;
        User u = null;
        System.out.println(username+" "+password);

        try {
            rs = stmt.executeQuery("select * from \"Users\" where username='"+username+"' and password='"+password+"'");
            if (rs.next()) {
                u = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public ArrayList<Asset> getUserAssets(int userid) {
        ArrayList<Asset> assets = new ArrayList<Asset>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from assets where userid="+userid);
            while (rs.next()) {
                assets.add(new Asset(
                        rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getString("description"),
                        rs.getInt("value")
                ));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public boolean updateAsset(Asset asset) {
        int r = 0;
        try {
            r = stmt.executeUpdate("update assets set description='"+asset.getDescription()+"', value="+asset.getValue()+
                    " where id="+asset.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (r>0) return true;
        else return false;
    }

    public ArrayList<Question> getUserQuestions(int userid) {
        ArrayList<Question> questions = new ArrayList<Question>();
        ResultSet rs;
        try {
            rs = stmt.executeQuery("select * from \"Questions\" where userid="+userid);
            while (rs.next()) {
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getInt("userid"),
                        rs.getString("description"),
                        rs.getString("answer1"),
                        rs.getString("answer2"),
                        rs.getString("answer3"),
                        rs.getString("answer4"),
                        rs.getString("correctAnswer")));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public boolean updateQuestion(Question question) {
        int r = 0;
        try {
            r = stmt.executeUpdate("update \"Questions\" set description='"+question.getDescription()+"'"+
                    " where id="+question.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (r>0) return true;
        else return false;
    }
    public int getUserAllTimeScore(int userid) {
        ResultSet rs;
        int allTimeScore=0;
        try {
            rs = stmt.executeQuery("select * from \"Users\" where id="+userid);
            while (rs.next()) {
                allTimeScore=rs.getInt("allTimeScore");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allTimeScore;
    }
    public boolean setUserAllTimeScore(int userid, int allTimeScore) {
        int r = 0;
        try {
            r = stmt.executeUpdate("update \"Users\" set allTimeScore='"+allTimeScore+"' where id="+userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (r>0) return true;
        else return false;
    }
}