/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sufurujhin.rpgdungeon.Banco;

import com.sufurujhin.rpgdungeon.Banco.ConexaoSQLite;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

public class CriarBancoSQLite {

    private final ConexaoSQLite conexaoSQLite;

    public CriarBancoSQLite(ConexaoSQLite pConexaoSQLite) {
        this.conexaoSQLite = pConexaoSQLite;
    }

    public void monster(){
    	String sql = "CREATE TABLE IF NOT EXISTS mob"
                + "("
                + "mob_id integer PRIMARY KEY,"
                + "type text NOT NULL,"
                + "displayname text,"
                + "cor text,"
                + "nv integer,"
                + "health integer,"
                + "attack integer,"
                + "speed decimal,"
                + "money integer,"
                + "effect text,"
                + "twoHand boolean,"
                + "glowing boolean,"
                + "xp integer"
                + ");";
    	createTable(sql);
    }
    
    public void skill(){
    	String sql = "CREATE TABLE IF NOT EXISTS skill"
                + "("
                + "skill_id integer PRIMARY KEY,"
                + "tiposkill text NOT NULL,"
                + "mensagem text,"
                + "raio integer,"
                + "damage integer,"
                + "nivel integer,"
                + "chance_lance decimal,"
                + "duracao integer,"
                + "cor text"
                + ");";
    	createTable(sql);
    }
    
    public void equipment(){
    	String sql = "CREATE TABLE IF NOT EXISTS equipment"
                + "("
                + "equipment_id integer PRIMARY KEY,"
                + "item_id integer"
                + ");";
    	createTable(sql);
    }
    public void enchant(){
    	String sql = "CREATE TABLE IF NOT EXISTS enchant"
                + "("
                + "enchant_id integer PRIMARY KEY,"
                + "nome text,"
                + "nivel integer"
                + ");";
    	createTable(sql);
    } 
    
    public void lores(){
    	String sql = "CREATE TABLE IF NOT EXISTS lores"
                + "("
                + "lores_id integer PRIMARY KEY,"
                + "nome text,"
                + "nivel integer,"
                + "item_id integer"
                + ");";
    	createTable(sql);
    }
    
    public void item(){
    	String sql = "CREATE TABLE IF NOT EXISTS item"
                + "("
                + "item_id integer PRIMARY KEY,"
                + "type text,"
                + "slot text,"
                + "defense integer,"
                + "attack integer,"
                + "color text,"
                + "unbreakable_chance decimal,"
                + "moveSpeed decimal,"
                + "health integer,"
                + "attackSpeed decimal"
                + ");";
    	createTable(sql);
    }
    
    public void spawnMobTimer(){
    	String sql = "CREATE TABLE IF NOT EXISTS spawn"
                + "("
                + "spawn_id integer PRIMARY KEY,"
                + "mob_id integer,"
                + "secunds integer,"
                + "location_x integer,"
                + "location_y integer,"
                + "location_z text,"
                + "location_world text,"
                + "uuid text,"
                + "status boolean, observacao text"
                + ");";
    	
    	createTable(sql);
    }
//    public void locateDungeon(){
//    	String sql = "CREATE TABLE IF NOT EXISTS llc"
//                + "("
//                + "item_id integer PRIMARY KEY,"
//                + "type text,"
//                + "slot text,"
//                + "defense integer,"
//                + "attack integer,"
//                + "color text,"
//                + "unbreakable_chance decimal,"
//                + "moveSpeed decimal,"
//                + "health integer,"
//                + "attackSpeed integer"
//                + ");";
//    	createTable(sql);
//    }
    public void createTable(String sql) {
    	
        //executando o sql de criar tabelas
        boolean conectou = false;

        try {
            conectou = this.conexaoSQLite.conectar();
            
            Statement stmt = this.conexaoSQLite.criarStatement();
            
            stmt.execute(sql);
            

        } catch (SQLException e) {
            Bukkit.getServer().getConsoleSender().sendMessage("[RPGDungeon]Erro ao criar tabela: "+ e.getMessage());
        } finally {
            if(conectou){
                this.conexaoSQLite.desconectar();
            }
        }

      
        
    }
    public void StartTable()
    {
    	Bukkit.getConsoleSender().sendMessage("§c§l[RPGDungeon]Checando tabelas de banco de dados.");
    	monster();
    	equipment();
    	skill();
    	item();
    	lores();
    	enchant();
    	spawnMobTimer();
    	Bukkit.getConsoleSender().sendMessage("§c§l[RPGDungeon]checagem por tabelas concluída!");
    	
    }
}
