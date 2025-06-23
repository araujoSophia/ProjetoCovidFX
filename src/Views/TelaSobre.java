package Views;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TelaSobre extends JFrame {

    public TelaSobre() {
        setTitle("Sobre o Programa");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        JTextArea info = new JTextArea();

        info.setText("""
                Programa: Covid-25 Monitoramento
                Versão: v1.0

                Descrição:
                Sistema para monitoramento de dados de pacientes, testes e óbitos de COVID-25.

                Integrantes:
                - Jonas Bastos (22204806160024)
                - Sophia Rodrigues de Siqueira Araujo (2310480611066)
                - Kevin Christian Azarias Soares (2410480611014)
                """);

        info.setEditable(false);
        info.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(new JScrollPane(info), BorderLayout.CENTER);

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(e -> dispose());
        panel.add(fechar, BorderLayout.SOUTH);

        add(panel);
    }
}
