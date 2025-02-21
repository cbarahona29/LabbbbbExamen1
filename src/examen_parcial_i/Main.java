package examen_parcial_i;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("UberSocial - Menu Principal");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 250);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 0, 10)); 
        
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
            
        JButton facebookButton = new JButton("Abrir Facebook");
        JButton twitterButton = new JButton("Abrir Twitter");
        JButton exitButton = new JButton("Salir");
        
        buttonPanel.add(facebookButton);
        buttonPanel.add(twitterButton);
        buttonPanel.add(exitButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainFrame.add(mainPanel);
        
        facebookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FaceBookGUI();
                mainFrame.dispose();
            }
        });
        
        twitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TwitterGUI();
                mainFrame.dispose();
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}