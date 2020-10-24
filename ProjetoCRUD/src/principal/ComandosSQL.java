package principal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.PreparedStatement;

import conexao.Conexao;

public class ComandosSQL {
	
	public static void main(String[] args) throws SQLException {
		
		Conexao conexao = new Conexao();
		
		String sql = "select * from usuarios";
		String sql2 = "select count(nome) from usuarios";
		String sql3 = "select sum(codigo) from usuarios";
		String sql4 = "truncate table usuarios";
		String sql5 = "insert into usuarios (nome) values (?)";
		String sql6 = "select * from usuarios where codigo > ?";
		
		PreparedStatement pstmt = (PreparedStatement) conexao.conectar().prepareStatement(sql6);
		pstmt.setInt(1,3);
		
		//pstmt.execute();
		
		//Statement stmt = conexao.conectar().createStatement();
		
		//stmt.execute(sql4);
		
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
        	System.out.println(rs.getInt("codigo")+" - "+rs.getString("Nome"));
        }
	}
	}
//		while(rs.next()) {
//			System.out.println(rs.getInt("Codigo"));
//		}
		


