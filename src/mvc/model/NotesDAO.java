package mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotesDAO {
	private Connection connection = null;

	
	
	
	
	String url = System.getenv("mysql_url");
	String user = System.getenv("mysql_user");
	String password = System.getenv("mysql_password");
	
	public NotesDAO() throws SQLException{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void postNotas(Notes notinhas) {
		
		String sql = "INSERT INTO notas (pessoa, titulo_nota, notas) values(?, ?, ?)";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, notinhas.getUser());
			stmt.setString(2, notinhas.getTitulo());
			stmt.setString(3, notinhas.getNota());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public User getNotas(String login) {
			// PRECISO ARRUMAR E COLOCAR PRA GET NOTAS
			User user = new User();
			String sql = "SELECT * FROM login WHERE login=?";
			try {
				PreparedStatement stmt = connection.prepareStatement(sql);
				stmt.setString(1, login);
				ResultSet rs = stmt.executeQuery();
				while (rs.next()) {
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					return user;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			user.setLogin(null);
			return user;
			
		}
	
	public void editarNotas(Notes notinhas) {
		
		String sql = "UPDATE notas SET titulo_nota=?, notas=? WHERE id=?";
		try {
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, notinhas.getTitulo());
			stmt.setString(2, notinhas.getNota());
			stmt.setInt(3, notinhas.getId());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

	public void deletarNotas(Integer id) throws SQLException {
		
		PreparedStatement stmt = connection.prepareStatement("DELETE FROM notas WHERE id=?");
		stmt.setInt(1, id);

		stmt.execute();
		stmt.close();
		
	}
	
	public List<Notes> getLista(String user) throws SQLException{
		List<Notes> notas = new ArrayList<Notes>();
		
		PreparedStatement stmt = connection.
				 prepareStatement("SELECT * FROM notas WHERE pessoa = ?");
		stmt.setString(1, user);
		ResultSet rs = stmt.executeQuery();
				
		while (rs.next()) {
				Notes nota = new Notes();
				nota.setUser(rs.getString("pessoa"));
				nota.setTitulo(rs.getString("titulo_nota"));
				nota.setNota(rs.getString("notas"));
				nota.setId(rs.getInt("id"));
				notas.add(nota);
				}
				rs.close();
				stmt.close();
				return notas;
	}
	
	public void close() throws SQLException {
		connection.close();
	}
}
