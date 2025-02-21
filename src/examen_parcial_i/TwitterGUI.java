package examen_parcial_i;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class TwitterGUI extends JFrame {
    private JTextField userField, friendField, postContentField;
    private JTextArea displayArea;
    private JComboBox<String> userSelector;
    private DefaultComboBoxModel<String> userModel;

    public TwitterGUI() {
        setTitle("Twitter");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        
        // Create return button panel
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton("Regresar al Menú");
        returnButton.addActionListener(e -> returnToMenu());
        returnPanel.add(returnButton);
        
        // Add all panels
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        add(returnPanel, BorderLayout.EAST);
        
        setVisible(true);
    }
    
    private void returnToMenu() {
        // Create and show the main menu
        SwingUtilities.invokeLater(() -> {
            new Main().main(new String[]{});
            // Dispose of the current window
            this.dispose();
        });
    }
    
    // Rest of the existing TwitterGUI code remains the same...
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel userAddPanel = new JPanel(new BorderLayout(5, 0));
        userField = new JTextField();
        JButton addUserButton = new JButton("Agregar");
        userAddPanel.add(new JLabel("Usuario:"), BorderLayout.WEST);
        userAddPanel.add(userField, BorderLayout.CENTER);
        userAddPanel.add(addUserButton, BorderLayout.EAST);
        
        JPanel selectorPanel = new JPanel(new BorderLayout(5, 0));
        userModel = new DefaultComboBoxModel<>();
        userSelector = new JComboBox<>(userModel);
        selectorPanel.add(new JLabel("Seleccionar:"), BorderLayout.WEST);
        selectorPanel.add(userSelector, BorderLayout.CENTER);
        
        JPanel actionPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        JButton profileButton = new JButton("Ver Perfil");
        JButton timelineButton = new JButton("Timeline");
        actionPanel.add(profileButton);
        actionPanel.add(timelineButton);
        
        panel.add(userAddPanel);
        panel.add(selectorPanel);
        panel.add(actionPanel);
        
        addUserButton.addActionListener(e -> addUser());
        profileButton.addActionListener(e -> showProfile());
        timelineButton.addActionListener(e -> showTimeline());
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel friendPanel = new JPanel(new BorderLayout(5, 0));
        friendField = new JTextField();
        JButton addFriendButton = new JButton("Agregar Amigo");
        friendPanel.add(new JLabel("Amigo:"), BorderLayout.WEST);
        friendPanel.add(friendField, BorderLayout.CENTER);
        friendPanel.add(addFriendButton, BorderLayout.EAST);
        
        JPanel postPanel = new JPanel(new BorderLayout(5, 0));
        postContentField = new JTextField();
        JButton postButton = new JButton("Publicar");
        postPanel.add(new JLabel("Post:"), BorderLayout.WEST);
        postPanel.add(postContentField, BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        
        panel.add(friendPanel);
        panel.add(postPanel);
        
        addFriendButton.addActionListener(e -> addFriend());
        postButton.addActionListener(e -> addPost());
        
        return panel;
    }

    // All other existing methods remain exactly the same...
    private void addUser() {
        String username = userField.getText().trim();
        if (!username.isEmpty()) {
            UberSocial.agregarCuenta(username, "TWITTER");
            userField.setText("");
            refreshUserList();
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre de usuario");
        }
    }
    
    private void refreshUserList() {
        userModel.removeAllElements();
        ArrayList<String> usuariosTwitter = UberSocial.obtenerUsuariosTwitter();
        for (String user : usuariosTwitter) {
            userModel.addElement(user);
        }
    }
    
    private void addFriend() {
        String selectedUser = (String) userSelector.getSelectedItem();
        String friendName = friendField.getText().trim();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        if (friendName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese nombre del amigo");
            return;
        }
        
        UberSocial.agregarAmigo(selectedUser, friendName);
        friendField.setText("");
    }
    
    private void addPost() {
        String selectedUser = (String) userSelector.getSelectedItem();
        String content = postContentField.getText().trim();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese contenido para el post");
            return;
        }
        
        UberSocial.agregarPost(selectedUser, content);
        postContentField.setText("");
        displayArea.append("Post publicado: " + content + "\n");
    }
    
    private void showProfile() {
        String selectedUser = (String) userSelector.getSelectedItem();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        SocialClass account = UberSocial.buscar(selectedUser);
        if (account != null) {
            displayArea.setText("Perfil de " + selectedUser + "\n\n");
            account.myProfile(); 
            
            ArrayList<String> amigos = account.getFriends();
            if (!amigos.isEmpty()) {
                displayArea.append("\nAmigos:\n");
                for (String amigo : amigos) {
                    displayArea.append("- " + amigo + "\n");
                }
            } else {
                displayArea.append("\nNo tiene amigos aún.\n");
            }
        } else {
            JOptionPane.showMessageDialog(this, "El usuario no existe.");
        }
    }
    
    private void showTimeline() {
        String selectedUser = (String) userSelector.getSelectedItem();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        SocialClass account = UberSocial.buscar(selectedUser);
        if (account instanceof Twitter) {
            OutputCapturer capturer = new OutputCapturer();
            capturer.start();
            ((Twitter) account).timeLine();
            displayArea.setText(capturer.stop());
        } else {
            displayArea.setText("Error: La cuenta seleccionada no es una cuenta de Twitter.");
        }
    }
    
    private class OutputCapturer {
        private java.io.ByteArrayOutputStream outputStream;
        private java.io.PrintStream originalOut;
        
        public void start() {
            outputStream = new java.io.ByteArrayOutputStream();
            originalOut = System.out;
            System.setOut(new java.io.PrintStream(outputStream));
        }
        
        public String stop() {
            System.out.flush();
            System.setOut(originalOut);
            return outputStream.toString();
        }
    }
}