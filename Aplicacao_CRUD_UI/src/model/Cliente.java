package model;

public class Cliente {
	private String nome;
	private String idade;
	private String telefone;
	private String cpf;
	
	public Cliente() {
		this.nome = "";
		this.idade = "";
		this.telefone = "";
		this.cpf = "";
	}
	
	public Cliente(String nome, String idade, String telefone, String cpf) {
		this.nome = nome;
		this.idade = idade;
		this.telefone = telefone;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIdade() {
		return idade;
	}

	public void setIdade(String idade) {
		this.idade = idade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Nome: " + getNome() + "\tIdade: " + getIdade() + "\tTelefone: " + getTelefone() + "\tCPF: " + getCpf();
	}
}