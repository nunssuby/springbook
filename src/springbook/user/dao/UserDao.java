package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Driver;

import springbook.user.domain.User;

public class UserDao {
	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://10.10.2.222/springbook?characterEncoding=UTF-8", "nunssuby", "sindo2007");

		/*Java의 Derby 사용하려고 했음 */
//		Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
//		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//		Connection c = DriverManager.getConnection("jdbc:derby:C:\\Users\\nunssuby\\MyDB;create=true");
//		Connection c = DriverManager.getConnection("jdbc:derby:memory:MYDB;create=true");
		
		PreparedStatement ps = c.prepareStatement(
			"insert into users(id, name, password) values(?,?,?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());

		ps.executeUpdate();

		ps.close();
		c.close();
	}


	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://10.10.2.222/springbook?characterEncoding=UTF-8", "nunssuby", "sindo2007");
		
		/*Java의 Derby 사용하려고 했음 */
//		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//		Connection c = DriverManager.getConnection("jdbc:derby:C:\\Users\\nunssuby\\MyDB;create=true");
		PreparedStatement ps = c
				.prepareStatement("select * from users where id = ?");
		ps.setString(1, id);

		ResultSet rs = ps.executeQuery();
		rs.next();
		User user = new User();
		user.setId(rs.getString("id"));
		user.setName(rs.getString("name"));
		user.setPassword(rs.getString("password"));

		rs.close();
		ps.close();
		c.close();

		return user;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao dao = new UserDao();

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		dao.add(user);
			
		System.out.println(user.getId() + " 등록 성공");
		
		User user2 = dao.get(user.getId());
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
			
		System.out.println(user2.getId() + " 조회 성공");
	}

}
