package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import factory.ConectaBanco;
import model.Cliente;

public class ClienteDAO {
	private Connection conn;

	private void abrirConexao() throws Exception {
		try {
			conn = ConectaBanco.abrir();
		} catch (Exception e) {
			System.err.println("Banco não conectado!");
			e.printStackTrace();
		}
	}

	private void fecharConexao() throws SQLException {
		conn.close();
	}

	// Cadastra o cliente na base de dados
	public void cadastrarCliente(Cliente cliente) throws Exception {
		abrirConexao();

		String sql = "INSERT INTO cliente VALUES (?,?,?,?)";
		PreparedStatement stm;
		try {
			stm = conn.prepareStatement(sql);
			stm.setString(1, cliente.getNome());
			stm.setString(2, cliente.getIdade());
			stm.setString(3, cliente.getTelefone());
			stm.setString(4, cliente.getCpf());
			stm.execute();
			stm.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		fecharConexao();
	}

	// Altera o cliente na base de dados
	public boolean alterarCliente(Cliente cliente, String chaveCPF) throws Exception {
		abrirConexao();
		String sql = "UPDATE cliente SET ";
		sql += "nome='" + cliente.getNome() + "',";
		sql += "idade='" + cliente.getIdade() + "',";
		sql += "telefone='" + cliente.getTelefone() + "',";
		sql += "cpf='" + cliente.getCpf() + "' ";
		sql += "WHERE cpf='" + chaveCPF + "'";
		PreparedStatement stm = conn.prepareStatement(sql);
		if (stm.execute() == true) {
			return true;
		}
		return false;
	}

	// Exclui o cliente na base de dados
	public boolean excluirCliente(Cliente cliente) throws Exception {
		abrirConexao();
		String sql = "DELETE FROM cliente WHERE cpf='" + (cliente.getCpf() + "'");
		PreparedStatement stm = conn.prepareStatement(sql);
		if (stm.execute() == true) {
			stm.close();
			fecharConexao();
			return true;
		}
		return false;
	}
}