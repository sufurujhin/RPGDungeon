package com.sufurujhin.rpgdungeon.classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sufurujhin.rpgdungeon.Banco.ConexaoSQLite;

public class EquipmentClass {

	private static ConexaoSQLite c;
	private static Connection conn;
	private static PreparedStatement ps;

	public EquipmentClass() {
		c = new ConexaoSQLite();
		conn = c.getConexao();
//		ps = conn.prepareStatement(SQL);
	}
	
	public void Select(int item_id){
		
		
	}
	
}
