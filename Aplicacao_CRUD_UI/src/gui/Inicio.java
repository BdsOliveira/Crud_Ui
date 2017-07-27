package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.ClienteDAO;
import factory.ConectaBanco;
import model.Cliente;

class TelaAlterar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnAlterar;
	private JButton btnCancelar;
	private JTextField jTextFieldNome;
	private JTextField jTextFieldIdade;
	private JTextField jTextFieldTelefone;
	private JTextField jTextFieldCpf;
	private JLabel jLabelNome;
	private JLabel jLabelIdade;
	private JLabel jLabelTelefone;
	private JLabel jLabelCpf;
	private String chaveCPF;

	public void janelaAlterar(Cliente c) {
		new JFrame();
		setSize(450, 290);
		setTitle("Tela Pesquisar");
		setLocation(450, 180);
		setLayout(new FlowLayout());
		setVisible(true);
		setResizable(false);
		setLayout(new GridLayout(5, 2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		chaveCPF = c.getCpf();
		jTextFieldNome = new JTextField(c.getNome(), 15);
		jTextFieldIdade = new JTextField(c.getIdade(), 5);
		jTextFieldTelefone = new JTextField(c.getTelefone(), 12);
		jTextFieldCpf = new JTextField(c.getCpf(), 12);

		jLabelNome = new JLabel("Nome: ");
		jLabelIdade = new JLabel("Idade: ");
		jLabelTelefone = new JLabel("Telefone: ");
		jLabelCpf = new JLabel("CPF: ");

		btnAlterar = new JButton("Alterar");
		btnAlterar.setVisible(true);
		btnAlterar.setLayout(new FlowLayout());
		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClienteDAO cDao = new ClienteDAO();
				c.setNome(jTextFieldNome.getText().toString());
				c.setIdade(jTextFieldIdade.getText().toString());
				c.setTelefone(jTextFieldTelefone.getText().toString());
				c.setCpf(jTextFieldCpf.getText().toString());

				try {
					cDao.alterarCliente(c, chaveCPF);
					JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
					new TelaPesquisar().janelaPesquisar();
					dispose();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setVisible(true);
		btnCancelar.setLayout(new FlowLayout());
		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					new TelaPesquisar().janelaPesquisar();
					dispose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		add(jLabelNome);
		add(jTextFieldNome);
		add(jLabelIdade);
		add(jTextFieldIdade);
		add(jLabelTelefone);
		add(jTextFieldTelefone);
		add(jLabelCpf);
		add(jTextFieldCpf);
		add(btnAlterar);
		add(btnCancelar);
	}

}

class TelaPesquisar extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton btnAlterar;
	private JButton btnExcluir;
	private JButton btnCadastrar;
	private JTable jTableResultado;

	private Cliente c;
	private ClienteDAO cDao;

	public void janelaPesquisar() throws ClassNotFoundException, SQLException, Exception {
		new JFrame();
		setSize(450, 290);
		setTitle("Tela Pesquisar");
		setLocation(450, 180);
		setLayout(new FlowLayout());
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnAlterar = new JButton();
		btnAlterar.setText("Alterar");
		btnAlterar.setVisible(true);
		btnAlterar.setLayout(new FlowLayout());
		btnAlterar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTableResultado.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(null, "Clique para selecionar a linha a ser alterada!");
				} else {
					c = new Cliente();
					c.setNome(jTableResultado.getValueAt(jTableResultado.getSelectedRow(), 0).toString());
					c.setIdade(jTableResultado.getValueAt(jTableResultado.getSelectedRow(), 1).toString());
					c.setTelefone(jTableResultado.getValueAt(jTableResultado.getSelectedRow(), 2).toString());
					c.setCpf(jTableResultado.getValueAt(jTableResultado.getSelectedRow(), 3).toString());
					new TelaAlterar().janelaAlterar(c);
					dispose();
				}
			}
		});

		btnExcluir = new JButton();
		btnExcluir.setText("Excluir");
		btnExcluir.setVisible(true);
		btnExcluir.setLayout(new FlowLayout());
		btnExcluir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int linha = jTableResultado.getSelectedRow();
				if (linha == -1) {
					JOptionPane.showMessageDialog(null, "Clique para selecionar a linha a ser excluida!");
				} else {
					try {
						c = new Cliente();
						cDao = new ClienteDAO();
						c.setNome(jTableResultado.getValueAt(linha, 0).toString());
						c.setIdade(jTableResultado.getValueAt(linha, 1).toString());
						c.setTelefone(jTableResultado.getValueAt(linha, 2).toString());
						c.setCpf(jTableResultado.getValueAt(linha, 3).toString());
						int opcao_escolhida = JOptionPane.showConfirmDialog(null, c.getNome(),
								"Deseja realmente excluir? ", JOptionPane.YES_NO_OPTION);
						if (opcao_escolhida == JOptionPane.YES_OPTION) {
							// Chama o metodo de exclui o cliente do banco
							cDao.excluirCliente(c);
							JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
							// Atualiza a tabela
							modelTable();
						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		btnCadastrar = new JButton();
		btnCadastrar.setText("Cadastrar");
		btnCadastrar.setVisible(true);
		btnCadastrar.setLayout(new FlowLayout());
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Inicio().janelaInicial();
				dispose();
			}
		});

		jTableResultado = new JTable();
		modelTable();
		jTableResultado.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jTableResultado.setVisible(true);
		jTableResultado.setEnabled(true);

		add(btnAlterar);
		add(btnExcluir);
		add(btnCadastrar);
		add(new JScrollPane(jTableResultado), BorderLayout.CENTER);
	}

	private JTable modelTable() throws Exception {
		ArrayList<String[]> lista = new ArrayList<>();

		Connection conn = ConectaBanco.abrir();
		String nomesColunas[] = { "nome", "idade", "telefone", "cpf" };
		String sql = "SELECT * FROM cliente ORDER BY nome";

		PreparedStatement comando = conn.prepareStatement(sql);

		ResultSet resultado = comando.executeQuery();

		while (resultado.next()) {
			lista.add(new String[] { resultado.getString("nome"), String.valueOf(resultado.getInt("idade")),
					String.valueOf(resultado.getLong("telefone")), String.valueOf(resultado.getLong("cpf")) });
		}
		DefaultTableModel model = new DefaultTableModel(lista.toArray(new String[lista.size()][]), nomesColunas);
		jTableResultado.setModel(model);
		return null;
	}

}

// Tela Principal
class Inicio extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel painelNome;
	private JPanel painelIdade;
	private JPanel painelTelefone;
	private JPanel painelCpf;
	private JPanel painelBotoes;
	private JButton btnCadastrar;
	private JButton btnLimpar;
	private JButton btnPesquisar;
	private JTextField jTextFieldNome;
	private JTextField jTextFieldIdade;
	private JTextField jTextFieldTelefone;
	private JTextField jTextFieldCpf;
	private JLabel jLabelNome;
	private JLabel jLabelIdade;
	private JLabel jLabelTelefone;
	private JLabel jLabelCpf;

	private Cliente c;
	private ClienteDAO cDao;

	// Criar botões: Cadastrar - Pesquisar(Alterar - Excluir)
	public void janelaInicial() {
		new JFrame();
		setSize(400, 250);
		setTitle("Tela Inicial");
		setLocation(470, 180);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JTextFieldNome e JLabelNome
		painelNome = new JPanel();
		painelNome.setLayout(new FlowLayout());
		painelNome.setVisible(true);

		jLabelNome = new JLabel("Nome: ");
		jLabelNome.setVisible(true);
		jLabelNome.setLayout(new FlowLayout());

		jTextFieldNome = new JTextField(15);
		jTextFieldNome.setVisible(true);
		jTextFieldNome.setLayout(new FlowLayout());

		// JTextFieldIdade e JLabelIdade
		painelIdade = new JPanel();
		painelIdade.setLayout(new FlowLayout());
		painelIdade.setVisible(true);

		jLabelIdade = new JLabel("Idade: ");
		jLabelIdade.setVisible(true);
		jLabelIdade.setLayout(new FlowLayout());

		jTextFieldIdade = new JTextField(15);
		jTextFieldIdade.setVisible(true);
		jTextFieldIdade.setLayout(new FlowLayout());

		// JTextFieldTelefone e JLabelTelefone
		painelTelefone = new JPanel();
		painelTelefone.setLayout(new FlowLayout());
		painelTelefone.setVisible(true);

		jLabelTelefone = new JLabel("Telefone: ");
		jLabelTelefone.setVisible(true);
		jLabelTelefone.setLayout(new FlowLayout());

		jTextFieldTelefone = new JTextField(15);
		jTextFieldTelefone.setVisible(true);
		jTextFieldTelefone.setLayout(new FlowLayout());

		// JTextFieldCpf e JLabelCpf
		painelCpf = new JPanel();
		painelCpf.setLayout(new FlowLayout());
		painelCpf.setVisible(true);

		jLabelCpf = new JLabel("CPF: ");
		jLabelCpf.setVisible(true);
		jLabelCpf.setLayout(new FlowLayout());

		jTextFieldCpf = new JTextField(15);
		jTextFieldCpf.setVisible(true);
		jTextFieldCpf.setLayout(new FlowLayout());

		// Painel Botões
		painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout());
		painelBotoes.setVisible(true);

		// Botão Cadastrar
		btnCadastrar = new JButton();
		btnCadastrar.setText("Cadastrar");
		btnCadastrar.setVisible(true);
		btnCadastrar.setLayout(new FlowLayout());
		btnCadastrar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (jTextFieldNome.getText().isEmpty() || jTextFieldIdade.getText().isEmpty()
						|| jTextFieldTelefone.getText().isEmpty() || jTextFieldCpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar!");
				} else {
					c = new Cliente(jTextFieldNome.getText().toString(), jTextFieldIdade.getText().toString(),
							jTextFieldTelefone.getText().toString(), jTextFieldCpf.getText().toString());
					cDao = new ClienteDAO();
					try {
						cDao.cadastrarCliente(c);

						JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
						jTextFieldNome.setText("");
						jTextFieldIdade.setText("");
						jTextFieldTelefone.setText("");
						jTextFieldCpf.setText("");

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// Botão Limpar
		btnLimpar = new JButton();
		btnLimpar.setText("Limpar");
		btnLimpar.setVisible(true);
		btnLimpar.setLayout(new FlowLayout());
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTextFieldNome.setText("");
				jTextFieldIdade.setText("");
				jTextFieldTelefone.setText("");
				jTextFieldCpf.setText("");
			}
		});

		// Botão Pesquisar
		btnPesquisar = new JButton();
		btnPesquisar.setText("Pesquisar");
		btnPesquisar.setVisible(true);
		btnPesquisar.setLayout(new FlowLayout());
		btnPesquisar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jTextFieldNome.setText("");
				jTextFieldIdade.setText("");
				jTextFieldTelefone.setText("");
				jTextFieldCpf.setText("");
				try {
					new TelaPesquisar().janelaPesquisar();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});

		painelNome.add(jLabelNome);
		painelNome.add(jTextFieldNome);
		painelIdade.add(jLabelIdade);
		painelIdade.add(jTextFieldIdade);
		painelTelefone.add(jLabelTelefone);
		painelTelefone.add(jTextFieldTelefone);
		painelCpf.add(jLabelCpf);
		painelCpf.add(jTextFieldCpf);
		painelBotoes.add(btnCadastrar);
		painelBotoes.add(btnLimpar);
		painelBotoes.add(btnPesquisar);
		add(painelNome);
		add(painelIdade);
		add(painelTelefone);
		add(painelCpf);
		add(painelBotoes);

	}

	public static void main(String[] args) throws Exception {

		Inicio ctr = new Inicio();
		ctr.janelaInicial();

	}
}