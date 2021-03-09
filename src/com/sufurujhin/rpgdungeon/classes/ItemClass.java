package com.sufurujhin.rpgdungeon.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.sufurujhin.rpgdungeon.Banco.ConexaoSQLite;

public class ItemClass {
	private static ConexaoSQLite c;
	private static Connection conn;
	private static PreparedStatement ps;
    private static Statement stmt;
    private static ResultSet rs;
    
    
    public int item_id;
    public String type;
    public String slot;
    public int defense ;
    public int attack;
    public String color ;
    public double unbreakable_chance;
    public double moveSpeed ;
    public int health;
    public double attackSpeed ;
    
	public ItemClass() {
		c = new ConexaoSQLite();
		conn = c.getConexao();
	}

	public void Select(int item_id) {
		String SQL = "SELECT item_id," + "type," + "slot," + "defense," + "attack," + "color," + "unbreakable_chance,"
				+ "moveSpeed," + "health," + "attackSpeed" + "FROM item where item_id= ?";

		try {
//			ps = conn.prepareStatement(SQL);
//			// ps.setString(1, "Jos√©");
//			// ps.setString(2, "32321");
//			
//			ps.setInt(1, item_id);
//			ps.executeUpdate();
//			ps.close();
//			
			stmt = conn.createStatement();
            
            rs = stmt.executeQuery(SQL);
            
			this.item_id = rs.getInt("item_id");
			type = rs.getString("type");
			slot = rs.getString("slot");
			defense = rs.getInt("defense");
			attack = rs.getInt("attack");
			color = rs.getString("color");
			unbreakable_chance = rs.getDouble("unbreakable_chance");
			moveSpeed = rs.getDouble("moveSpeed");
			health = rs.getInt("health");
			attackSpeed = rs.getDouble("attackSpeed");
            
//            while(rs.next()){
//                System.out.println(rs.getInt("id"));
//                System.out.println(rs.getString("nome"));
//                System.out.println(rs.getString("matricula"));
//                System.out.println("--------------------------");
//            }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("ßlß4[RPGDungeon]Erro : "+e.getMessage());
		}

	}
	public void update(int item_id) {
		String SQL = "update item set type = ?," + "slot = ?," + "defense = ?," + "attack = ?," + "color = ?," + "unbreakable_chance = ?,"
				+ "moveSpeed = ?," + "health = ?," + "attackSpeed = ?" + " where item_id = ?";

		try {
			ps = conn.prepareStatement(SQL);
			 ps.setString(1, "Jos√©");
			 ps.setString(2, "32321");
			
			ps.setInt(1, item_id);
			ps.executeUpdate();
			ps.close();
//			
			stmt = conn.createStatement();
            
            rs = stmt.executeQuery(SQL);
            
            popularParametro();
    		ps.setInt(10,this.item_id );
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("ßlß4[RPGDungeon]Erro : "+e.getMessage());
		}

	}
	public void insert() {
		String SQL = "insert into item (type,slot,defense,attack ,color ,unbreakable_chance ,"
				+ "moveSpeed ,health ,attackSpeed, item_id) values (?,?,?,?,?,?,?,?,?,?)";

		try {
			ps = conn.prepareStatement(SQL);
			 ps.setString(1, "Jos√©");
			 ps.setString(2, "32321");
			
			ps.setInt(1, item_id);
			ps.executeUpdate();
			ps.close();
//			
			stmt = conn.createStatement();
            
            rs = stmt.executeQuery(SQL);
            
            popularParametro();
    		ps.setInt(10,SelectNextID());
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("ßlß4[RPGDungeon]Erro : "+e.getMessage());
		}

	}

	private void popularParametro() throws SQLException {
		ps.setString(1,type );
		ps.setString(2,slot);
		ps.setInt(3,defense);
		ps.setInt(4,attack );
		ps.setString(5,color);
		ps.setDouble(6,unbreakable_chance);
		ps.setDouble(7,moveSpeed );
		ps.setInt(8,health );
		ps.setDouble(9,attackSpeed );
	}
	
	public int SelectNextID() {
		String SQL = "SELECT (max(coalesce(item_id,0))+1) FROM item ";

		try {
//			
			stmt = conn.createStatement();
            
            rs = stmt.executeQuery(SQL);
            
			return rs.getInt("item_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Bukkit.getServer().getConsoleSender().sendMessage("ßlß4[RPGDungeon]Erro : "+e.getMessage());
		}
		return 0;

	}
}
