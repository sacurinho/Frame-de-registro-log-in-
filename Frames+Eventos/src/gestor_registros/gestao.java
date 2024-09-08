package gestor_registros;

import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class gestao implements ActionListener {
	JFrame frame;
	JPanel painel1, painel2, painel3, painel4, painel5, painel6, painel7, painel8, painel9;

	JLabel jl_registro, jl_vazio, jl_nome, jl_data, jl_estado, jl_tipoUser, jl_user, jl_user2, jl_pass, jl_pass2,
			jl_passConfirm, jl_entrar;

	JTextField nomeTf, userTf, userTf2;
	JFormattedTextField dataTf;
	JPasswordField password, password2, confirmPass;

	JButton registrar, limpar, entrar, fechar;
	JRadioButton solteiro, casado, outro;
	ButtonGroup bg;
	JCheckBox normal, admin;

	private List<String[]> registeredUsers = new ArrayList<>();

	public gestao() {
		frame = new JFrame("Gestor de Senhas ");
		frame.setSize(750, 500);
		frame.setLocation(150, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10, 1));

		painel1 = new JPanel();
		painel2 = new JPanel();
		painel3 = new JPanel();
		painel4 = new JPanel();
		painel5 = new JPanel();
		painel6 = new JPanel();
		painel7 = new JPanel();
		painel8 = new JPanel();
		painel9 = new JPanel();

		// p1
		painel1.setLayout(new FlowLayout());

		jl_registro = new JLabel("Registro");

		// p2
		painel2.setLayout(new GridLayout(2, 2));
		jl_nome = new JLabel("Nome: ");
		nomeTf = new JTextField();

		jl_data = new JLabel("Data de Nascimento: ");
		dataTf = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));

		// p3
		painel3.setLayout(new GridLayout(1, 4));
		jl_estado = new JLabel("Estado Civil:");
		solteiro = new JRadioButton("Solteiro");
		casado = new JRadioButton("Casado");
		outro = new JRadioButton("Outro");
		bg = new ButtonGroup();
		bg.add(solteiro);
		bg.add(casado);
		bg.add(outro);

		// p4
		painel4.setLayout(new GridLayout(1, 3, 5, 5));
		jl_user = new JLabel("Usuario: ");
		userTf = new JTextField();

		jl_pass = new JLabel("Password ");
		password = new JPasswordField();
		password.setEchoChar('x');

		jl_passConfirm = new JLabel("Confirmar Password:");
		confirmPass = new JPasswordField();
		confirmPass.setEchoChar('x');

		painel5.setLayout(new GridLayout(3, 2, 0, 0));
		jl_tipoUser = new JLabel("Tipo de Usuario: ");
		normal = new JCheckBox("Normal");
		admin = new JCheckBox("Admin");

		// p6
		painel6.setLayout(new FlowLayout());
		registrar = new JButton("Registrar");
		limpar = new JButton("Limpar");

		// p7
		painel7.setLayout(new FlowLayout());
		jl_entrar = new JLabel("Entrar");

		// p8
		painel8.setLayout(new GridLayout(2, 2, 5, 5));
		jl_user2 = new JLabel("Usuario: ");
		userTf2 = new JTextField(20);
		jl_pass2 = new JLabel("Password: ");
		password2 = new JPasswordField(20);
		password2.setEchoChar('x');

		// p9
		painel9.setLayout(new FlowLayout());
		entrar = new JButton("Entrar");
		fechar = new JButton("Fechar");

		// adding to paineis
		painel1.add(jl_registro);

		painel2.add(jl_nome);
		painel2.add(nomeTf);
		painel2.add(jl_data);
		painel2.add(dataTf);

		painel3.add(jl_estado);
		painel3.add(solteiro);
		painel3.add(casado);
		painel3.add(outro);

		painel4.add(jl_tipoUser);
		painel4.add(normal);
		painel4.add(admin);

		painel5.add(jl_user);
		painel5.add(userTf);
		painel5.add(jl_pass);
		painel5.add(password);
		painel5.add(jl_passConfirm);
		painel5.add(confirmPass);

		painel6.add(registrar);
		painel6.add(limpar);

		painel7.add(jl_entrar);

		painel8.add(jl_user2);
		painel8.add(userTf2);
		painel8.add(jl_pass2);
		painel8.add(password2);

		painel9.add(entrar);
		painel9.add(fechar);

// adding to frame
		frame.add(painel1);
		frame.add(painel2);
		frame.add(painel3);
		frame.add(painel4);
		frame.add(painel5);
		frame.add(painel6);
		frame.add(painel7);
		frame.add(painel8);
		frame.add(painel9);

		registrar.addActionListener(this);
		limpar.addActionListener(this);
		entrar.addActionListener(this);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String p1 = new String(password.getPassword());
		if (e.getSource() == registrar) {
			if (verificarPass()) {

				String[] userData = { nomeTf.getText(), dataTf.getText(), estadoCivil(), userTf.getText(), p1,
						tipoDeusuario() };
				registeredUsers.add(userData);
				if (!verificarUser(userTf.getText(), "")) {  // Pass an empty password for username check only
	                String[] userData1 = {nomeTf.getText(), dataTf.getText(), estadoCivil(), userTf.getText(),
	                        p1, tipoDeusuario()};
	                registeredUsers.add(userData1);
				}
				try {

					FileWriter file = new FileWriter("src/registered_users.txt", true);
					BufferedWriter escritor = new BufferedWriter(file);
					escritor.write(nomeTf.getText() + "," + dataTf.getText() + "," + userTf.getText() + "," + p1 + ","
							+ estadoCivil() + "," + tipoDeusuario());
					escritor.newLine();
					escritor.close(); 
				} catch (IOException i) {
					JOptionPane.showMessageDialog(frame, "\nFalha no Registro, tente novamente.");
				}

				JOptionPane.showMessageDialog(frame, "\nUser Registered Successfully");
			}else {
				  JOptionPane.showMessageDialog(frame, "Username already exists. Please choose another.");
			}

		}
		if (e.getSource() == limpar) {

			nomeTf.setText(" ");
			password.setText("");
			dataTf.setText(" ");
			confirmPass.setText("");
			userTf.setText(" ");
			bg.clearSelection();

		}

		if (e.getSource() == entrar) {
			String username = userTf2.getText();
			String password3 = new String(password2.getPassword());

			if (verificarUser(username, password3)) {

				JOptionPane.showMessageDialog(frame, "Username: " + username + "\nEstado Civil: " + estadoCivil()
						+ "\nTipo de Usuario: " + tipoDeusuario());
			} else {
				JOptionPane.showMessageDialog(frame, "Invalid username or password.");
			}
		}
	}

	private boolean verificarPass() {

		String p1 = new String(password.getPassword());
		String p2 = new String(confirmPass.getPassword());
		if (nomeTf.getText().isEmpty() || dataTf.getText().isEmpty() || userTf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "Please fill in all required fields properly.");
			return false;
		}
		if (!p1.equals(p2)) {
			JOptionPane.showMessageDialog(frame, "Passwords do not match.");
			return false;
		}
		return true;
	}

	private boolean verificarUser(String username, String password3) {
		try (BufferedReader reader = new BufferedReader(new FileReader("src/registered_users.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] userData = line.split(",");
				if (userData[2].equals(username) && userData[3].equals(password3)) {
					return true;
				}
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame, "Error reading user data");
		}
		return false;
	}

	private String tipoDeusuario() {
		StringBuilder checkboxData = new StringBuilder();
		if (normal.isSelected()) {
			checkboxData.append("Normal, ");
		}
		if (admin.isSelected()) {
			checkboxData.append("Admin, ");
		}
		return checkboxData.toString().trim();

	}

	private String estadoCivil() {

		if (solteiro.isSelected()) {
			return "Solteiro";
		}
		if (casado.isSelected()) {
			return "Casado";
		}
		if (outro.isSelected()) {
			return "Outro";
		}
		return null;

	}

}
