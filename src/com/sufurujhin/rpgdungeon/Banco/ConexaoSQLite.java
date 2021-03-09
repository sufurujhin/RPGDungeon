/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sufurujhin.rpgdungeon.Banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.sufurujhin.rpgdungeon.RPGDungeon;
import com.sufurujhin.rpgdungeon.Files.Files;

public class ConexaoSQLite {

    private Connection conexao;

    /**
     * Conecta a um banco de dados (cria o banco se ele não existir)
     *
     * @return
     */
    public boolean conectar() {

        try {
        	Files file = new Files(RPGDungeon.load());
        	file.createDB(RPGDungeon.load());
            String url = "jdbc:sqlite:"+RPGDungeon.load().getDataFolder()+"/rpgdungeon.db";
            this.conexao = DriverManager.getConnection(url);

        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("[RPGDungeon]Erro : "+ e.getMessage() );
            return false;
        }

        
        return true;
    }

    public boolean desconectar() {

        try {
        	
            if (this.conexao.isClosed() == false) {
                this.conexao.close();
            }

        } catch (SQLException e) {

        	Bukkit.getServer().getConsoleSender().sendMessage("[RPGDungeon]Erro : "+e.getMessage());
            return false;
        }
        //System.out.println("desconectou!!!");
        return true;
    }
      
    /**
     * Criar os statements para nossos sqls serem executados
     * @return 
     */
    public Statement criarStatement(){
        try{
        	conectar();
            return this.conexao.createStatement();
        }catch(SQLException e){
            return null;
        }
    }
    
    public Connection getConexao(){
    	if (conexao == null) 
    		conectar(); 
    	try {
			if (conexao.isClosed())
				conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			Bukkit.getServer().getConsoleSender().sendMessage("[RPGDungeon]Erro : "+e.getMessage());
		}
        return this.conexao;
    }
    
}
