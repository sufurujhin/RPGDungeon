package com.sufurujhin.rpgdungeon.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import com.sufurujhin.rpgdungeon.Banco.ConexaoSQLite;

public class SpawnTimer {

	private static ConexaoSQLite c;
	private static PreparedStatement ps;
	private static Statement stmt;
	private static ResultSet rs;

	public int spawn_id;
	public int mob_id;
	public int secunds;
	public int location_x;
	public int location_y;
	public int location_z;
	public String location_world;
	public String observacao;

	public String uuid;
	public boolean status;

	public SpawnTimer() {
	}

	public List<String> Select(boolean status) {
		List<String> listPathConfig = new ArrayList<>();
		String SQL = "SELECT uuid FROM spawn where status= " + status;

		try {

			c = new ConexaoSQLite();
			stmt = c.criarStatement();
			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				listPathConfig.add(rs.getString("uuid"));
			}
			c.desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 53 : " + e.getMessage());
		}finally
		{
			try {
				rs.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPathConfig;
	}

	public List<String> Select() {
		List<String> listPathConfig = new ArrayList<>();
		String SQL = "SELECT spawn_id, observacao,secunds FROM spawn where status= 1" ;

		try {
			c = new ConexaoSQLite();
			stmt = c.criarStatement();

			rs = stmt.executeQuery(SQL);

			while (rs.next()) {
				listPathConfig.add("Nome: "+rs.getString("observacao")+" | ID: " +rs.getString("spawn_id")+" | Cooldown: " +rs.getString("secunds")+"segundos");
			}
			c.desconectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 74 : " + e.getMessage());
		}finally
		{
			try {
				rs.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return listPathConfig;
	}
	
	public boolean Select(int spawn_id) {
		String SQL = "SELECT spawn_id," + "mob_id," + "secunds," + "location_x," + "location_y," + "location_z,"
				+ "location_world," + "uuid," + "status, observacao " + "FROM spawn where spawn_id= " + spawn_id;

		try {
			c = new ConexaoSQLite();
			
			stmt = c.criarStatement();

			rs = stmt.executeQuery(SQL);

			this.spawn_id = rs.getInt("spawn_id");
			mob_id = rs.getInt("mob_id");
			secunds = rs.getInt("secunds");
			location_x = rs.getInt("location_x");
			location_y = rs.getInt("location_y");
			location_z = rs.getInt("location_z");
			location_world = rs.getString("location_world");
			uuid = rs.getString("uuid");
			status = rs.getBoolean("status");
			observacao = rs.getString("observacao");

			c.desconectar();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 111 : " + e.getMessage());
			return false;
		}finally
		{
			try {
				rs.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public boolean Select(String UUID) {
		String SQL = "SELECT spawn_id," + "mob_id," + "secunds," + "location_x," + "location_y," + "location_z,"
				+ "location_world," + "uuid," + "status, observacao " + "FROM spawn where uuid = \"" + UUID+"\"";

		try {
			c = new ConexaoSQLite();
			stmt = c.criarStatement();
			rs = stmt.executeQuery(SQL);
			if (!rs.isClosed()){
			this.spawn_id = rs.getInt("spawn_id");
			mob_id = rs.getInt("mob_id");
			secunds = rs.getInt("secunds");
			location_x = rs.getInt("location_x");
			location_y = rs.getInt("location_y");
			location_z = rs.getInt("location_z");
			location_world = rs.getString("location_world");
			uuid = rs.getString("uuid");
			status = rs.getBoolean("status");
			observacao = rs.getString("observacao");
			
			return true;
			} else
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 146 : " + e.getMessage());
			return false;
		}finally
		{
			try {
				rs.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void update(int spawn_id) {
		String SQL = "update spawn set mob_id = ?,secunds = ?,location_x = ?,location_y = ? ,location_z = ? ,location_world = ? ,"
				+ "uuid = ? ,status = ? , observacao = ? " + " where spawn_id = ?";

		try {
			c = new ConexaoSQLite();
			
			ps = c.getConexao().prepareStatement(SQL);
			popularParametro();
			ps.setInt(10, this.spawn_id);

			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 172 : " + e.getMessage());
		} finally
		{
			try {
				ps.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void insert() {
		String SQL = "insert into spawn (mob_id,secunds,location_x,location_y ,location_z ,location_world ,"
				+ "uuid ,status,observacao , spawn_id ) values (?,?,?,?,?,?,?,?,?,?)";

		try {
			c = new ConexaoSQLite();
			
			ps = c.getConexao().prepareStatement(SQL);

			popularParametro();
			ps.setInt(10, SelectNextID());
			ps.executeUpdate();
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 198 : " + e.getMessage());
		} finally
		{
			try {
				ps.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void popularParametro() throws SQLException {
		ps.setInt(1, mob_id);
		ps.setInt(2, secunds);
		ps.setInt(3, location_x);
		ps.setInt(4, location_y);
		ps.setInt(5, location_z);
		ps.setString(6, location_world);
		ps.setString(7, uuid);
		ps.setBoolean(8, status);
		ps.setString(9, observacao);
	}

	public int SelectNextID() {
		String SQL = "SELECT max(coalesce(spawn_id,1)) as spawn_id FROM spawn ";

		try {
			//
			c = new ConexaoSQLite();
				
			stmt = c.criarStatement();

			rs = stmt.executeQuery(SQL);
			return  (rs.getInt("spawn_id")+1);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("§l§4[RPGDungeon]Erro linha 230 : " + e.getMessage());
		} finally
		{
			try {
				rs.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}

	public String delete(int spawn_id) {
		try {
			String SQL = "DELETE FROM spawn WHERE spawn_id= " + spawn_id;

			c = new ConexaoSQLite();
			
			stmt = c.criarStatement();

			stmt.executeUpdate(SQL);
			c.desconectar();
			return "";
		} catch (SQLException ex) {
			return ex.getMessage() +  " linha 248";
		}finally
		{
			try {
				stmt.close();
				c.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
