package br.com.rkDev.terrain.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import br.com.rkDev.terrain.MinecraftTerrain;

public class Query {

	private Database database;
	private String query;
	private Connection con;
	private PreparedStatement statement;

	public Query(String query) {
		this.database = MinecraftTerrain.getInstance().getDatabaseManager().getDatabase();
		this.con = this.database.getConnection();
		this.query = query;
		try {
			this.statement = (PreparedStatement) this.con.prepareStatement(this.query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void execute() {
		try {
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public ResultSet getResultSet() throws SQLException {
		ResultSet rs = this.statement.executeQuery();
		return rs;
	}
	public PreparedStatement getStatement() {
		return this.statement;
	}
	
	public int insertGetID(String columns){
		try {

			int id = -1;
			String[] col = { columns };
			this.statement = (PreparedStatement)this.con.prepareStatement(this.query, col);

			this.statement.executeUpdate();

			ResultSet rs = this.statement.getGeneratedKeys();
			if(rs.next()) {
				id = rs.getInt(1);
			}
			return id;
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

}
