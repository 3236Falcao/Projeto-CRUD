package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
	
	public static void main(String[] args) {
		
		Conexao conexao = new Conexao();
		conexao.conectar();
	}

	public  Connection conectar() {
		
		Connection retornoConexao = null;
		
		try {
			
		String url = "jdbc:mysql://localhost:3306/crud";
		String usuario = "root";
		String senha = "apex";
		
		retornoConexao = DriverManager.getConnection(url, usuario, senha);
		System.out.println("Conectado com sucesso!");
		}catch(Exception e ) {
			System.out.println(e.getMessage());
		}
		return retornoConexao;
	}
		
	}
