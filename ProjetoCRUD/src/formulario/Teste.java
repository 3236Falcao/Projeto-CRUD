package formulario;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.mysql.jdbc.Statement;

import conexao.Conexao;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class Teste extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNome;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Teste frame = new Teste();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Teste() {
		setTitle("Cadastro de clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				RowSpec.decode("default:grow"),}));

		JPanel panel = new JPanel();
		contentPane.add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel = new JLabel("C\u00F3digo");
		panel.add(lblNewLabel, "2, 2, right, default");

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		panel.add(txtCodigo, "4, 2, fill, default");
		txtCodigo.setColumns(10);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "1, 3, fill, fill");
		panel_1.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu"),
						FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
				new RowSpec[] { FormSpecs.UNRELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, }));

		JLabel lblNewLabel_1 = new JLabel("Nome");
		panel_1.add(lblNewLabel_1, "2, 2, right, default");

		txtNome = new JTextField();
		panel_1.add(txtNome, "4, 2, fill, default");
		txtNome.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		contentPane.add(panel_4, "1, 5, center, fill");
		panel_4.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblContador = new JLabel("(0/0)");
		lblContador.setFont(new Font("Tahoma", Font.BOLD, 14));
		panel_4.add(lblContador, "1, 1");

		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, "1, 7, center, fill");

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nome = txtNome.getText();
				if (nome.trim().equals("")) {
					JOptionPane.showMessageDialog(null, "Por favor insira um nome válido!");
				} else {
					try {
						Conexao conexao = new Conexao();
						String sql = "insert into usuarios (nome) values (?)";
						PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
						pstmt.setString(1, nome.trim());
						pstmt.execute();
						JOptionPane.showMessageDialog(null, "Usuário " + nome.trim() + " cadastrado com sucesso!");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Falha ao cadastrar usuário!");
						e.printStackTrace();
					}
				}
				table.setModel(listarDados());
			}
		});
		panel_2.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(80dlu;pref):grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("max(80dlu;pref):grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px:grow"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("81px:grow"),},
			new RowSpec[] {
				RowSpec.decode("23px"),}));
		panel_2.add(btnCadastrar, "2, 1, fill, center");

		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int opcao = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja altear o cadastro deste usuário?", "Pergunta",
						JOptionPane.YES_NO_OPTION);
				AbstractButton btnExcluir = null;
				if (opcao == JOptionPane.YES_OPTION) {
					Integer codigo = Integer.parseInt(txtCodigo.getText());
					String nome = txtNome.getText();
					String sql = "update usuarios set nome = ? where codigo = ?";
					try {
						Conexao conexao = new Conexao();
						PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
						pstmt.setInt(2, codigo);
						pstmt.setString(1, nome);
						pstmt.execute();
						JOptionPane.showMessageDialog(null, "Usuário alterado com sucesso!");
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, "Falha ao alterar usuário!");
					}
					txtCodigo.setText("");
					txtNome.setText("");
					table.setModel(listarDados());
					btnCadastrar.setEnabled(true);
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
				table.setModel(listarDados());
				txtCodigo.setText("");
				txtNome.setText("");
				btnCadastrar.setEnabled(true);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
				
			}
		});
		panel_2.add(btnAlterar, "4, 1, fill, center");

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int opcao = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir o cadastro deste usuário?", "Pergunta",
						JOptionPane.YES_NO_OPTION);
				if (opcao == JOptionPane.YES_OPTION) {
					Integer codigo = Integer.parseInt(txtCodigo.getText());
					String sql = "delete from usuarios where codigo = ?";
					try {
						Conexao conexao = new Conexao();
						PreparedStatement pstmt = conexao.conectar().prepareStatement(sql);
						pstmt.setInt(1, codigo);
						pstmt.execute();
						JOptionPane.showMessageDialog(null, "Usuário excluido com sucesso!");
					} catch (Exception erro) {
						JOptionPane.showMessageDialog(null, "Falha ao excluir usuário!");
					}
					txtCodigo.setText("");
					table.setModel(listarDados());
					btnCadastrar.setEnabled(true);
					btnAlterar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}
				table.setModel(listarDados());
				txtCodigo.setText("");
				btnCadastrar.setEnabled(true);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}
		});
		panel_2.add(btnExcluir, "6, 1, fill, center");

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnCadastrar.setEnabled(true);
				btnAlterar.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnCancelar.setEnabled(true);
				int LinhaSelecionada = table.getSelectedRow();
				table.getSelectionModel().removeSelectionInterval(LinhaSelecionada, 0);
			}
		});
		panel_2.add(btnCancelar, "8, 1, fill, center");

		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, "1, 8, fill, fill");
		panel_3.setLayout(
				new FormLayout(new ColumnSpec[] { FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"), },
						new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));

		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane, "2, 2, fill, fill");

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int LinhaSelecionada = table.getSelectedRow();
				txtCodigo.setText(table.getValueAt(LinhaSelecionada, 0).toString());
				txtNome.setText(table.getValueAt(LinhaSelecionada, 1).toString());
				btnCadastrar.setEnabled(false);
				btnAlterar.setEnabled(true);
				btnExcluir.setEnabled(true);
				lblContador.setText("(" + (LinhaSelecionada + 1) + "/" + table.getRowCount() +")");
			}
		});
		table.setModel(listarDados());
		scrollPane.setViewportView(table);
	}
	
	private static DefaultTableModel listarDados() {
		
		DefaultTableModel dados = new DefaultTableModel();
		dados.addColumn("Código");
		dados.addColumn("Nome");
		
		try {
			Conexao conexao = new Conexao();
			String sql = "select * from usuarios";
			Statement stmt = (Statement) conexao.conectar().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				dados.addRow(new Object [] {rs.getInt(1), rs.getNString(2)});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível carregar os dados");
		}
		
		
		return dados;
	}
	
}
