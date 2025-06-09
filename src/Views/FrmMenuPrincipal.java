package Views;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FrmMenuPrincipal extends JFrame {

    public FrmMenuPrincipal() {
        setTitle("Covid-25 Monitoramento - Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel menu = new JPanel(new GridLayout(2, 4, 10, 10));

        JButton btnPacientes = new JButton("Pacientes");
        JButton btnTestes = new JButton("Testes");
        JButton btnObitos = new JButton("Óbitos");
        JButton btnImportarExportar = new JButton("Importação/Exportação");
        JButton btnEstatisticas = new JButton("Dashboard / Estatísticas");
        JButton btnSobre = new JButton("Sobre");
        JButton btnSair = new JButton("Sair");

        btnSobre.addActionListener(e -> new TelaSobre().setVisible(true));
        btnSair.addActionListener(e -> System.exit(0));

        // Ações futuras: abrir outras telas ou dialog
        menu.add(btnPacientes);
        menu.add(btnTestes);
        menu.add(btnObitos);
        menu.add(btnImportarExportar);
        menu.add(btnEstatisticas);
        menu.add(btnSobre);
        menu.add(btnSair);

        JLabel resumo = new JLabel("Estatísticas resumidas aqui...", SwingConstants.CENTER);
        resumo.setFont(new Font("SansSerif", Font.ITALIC, 14));

        add(menu, BorderLayout.CENTER);
        add(resumo, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmMenuPrincipal().setVisible(true));
    }
}
