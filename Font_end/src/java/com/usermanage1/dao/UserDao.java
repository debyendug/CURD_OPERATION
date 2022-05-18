
package com.usermanage.dao;

import com.usermanage.bean.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.catalina.User;




public  class UserDao {
    
        private String urldb = "jdbc:mysql://localhost:3306/user";
        private String user1 = "root";
        private String psw = "";
        private String jdbcdriver = "mysql-connector-java-8.0.29.jar";
        
        private static final String INSERT_SQL = "INSERT INTO userdb(`id`, `name`, `email`, `ph`)" + "VALUES (?,?,?,?)";
        
        private static final String SELECT_USER_BY_ID = "select id,name,email,ph from userdb where id =?";
	private static final String SELECT_ALL_USERS = "select * from userdb";
	private static final String DELETE_USERS_SQL = "delete from userdb where id = ?;";
	private static final String UPDATE_USERS_SQL = "update userdb set name = ?,email= ?, ph =? where id = ?;";

    public UserDao() {
    }
    
    // jdbc connector
    protected Connection getConnection() throws SQLException{
		Connection con = null;
		try {
			Class.forName(jdbcdriver);
			con = DriverManager.getConnection(urldb, user1, psw);
                }
                catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();    
		}
		return con;
	}
  
    //insert user
    
    public void insertUser(User user) throws SQLException {
		System.out.println(INSERT_SQL);
		// try-with-resource statement will auto close the connection.
		try (Connection con = getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(INSERT_SQL)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPhone());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} 
  }
    
    //select user by id
    
    public  User selectUser(int id) {
		User user = null;
		// Step 1: Establishing a Connection
		try (Connection con = getConnection();
		// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = con.prepareStatement(SELECT_USER_BY_ID);) {
			preparedStatement.setInt(1, id);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

                // Step 4: Process the ResultSet object.
			while (rs.next()) {
				String name = rs.getString("name");
				String email = rs.getString("email");
				String ph = rs.getString("phone");
                                User(id, name, email, ph); 
                                
                            }
			
		} catch (SQLException e) {
			printSQLException(e);
		}
		return user;
	}

    
    // select all
    
    public List<User> selectAllUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<User> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection con = getConnection();

				// Step 2:Create a statement using connection object
			PreparedStatement preparedStatement = con.prepareStatement(SELECT_ALL_USERS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				int ph = rs.getInt("phone");
				users.add((User) new user(id, name, email,ph)); // for custing 
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return users;
	}
    
    //update
    
    public boolean updateUser(User user2) throws SQLException {
		boolean rowUpdated;
		try (Connection con = getConnection();
			PreparedStatement statement = con.prepareStatement(UPDATE_USERS_SQL);) {
			System.out.println("updated User:"+statement);
			statement.setString(1, user2.getName());
			statement.setString(2, user2.getEmail());
			statement.setString(3, user2.getPhone());
			statement.setInt(4, user2.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
    
    //delete methord
    
    public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

    private void User(int id, String name, String email, String ph) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

       
    private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
        
}
