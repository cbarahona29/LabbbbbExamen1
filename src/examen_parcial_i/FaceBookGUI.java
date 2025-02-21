package examen_parcial_i;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class FaceBookGUI extends JFrame {
    private JTextField userField, friendField, postContentField, commentField;
    private JTextArea displayArea;
    private JComboBox<String> userSelector;
    private DefaultComboBoxModel<String> userModel;
    private JButton commentButton;

    public FaceBookGUI() {
        setTitle("Facebook");
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
            new Main().main(new String[]{}); // Main menu creation
            // Dispose of the current window
            this.dispose();
        });
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel userAddPanel = new JPanel(new BorderLayout(5, 0));
        userField = new JTextField();
        JButton addUserButton = new JButton("Agregar Usuario");
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
        JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10)); // Changed to 4 rows to accommodate comments
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Friend Panel (unchanged)
        JPanel friendPanel = new JPanel(new BorderLayout(5, 0));
        friendField = new JTextField();
        JButton addFriendButton = new JButton("Agregar Amigo");
        friendPanel.add(new JLabel("Amigo:"), BorderLayout.WEST);
        friendPanel.add(friendField, BorderLayout.CENTER);
        friendPanel.add(addFriendButton, BorderLayout.EAST);
        
        // Post Panel (unchanged)
        JPanel postPanel = new JPanel(new BorderLayout(5, 0));
        postContentField = new JTextField();
        JButton postButton = new JButton("Publicar");
        postPanel.add(new JLabel("Post:"), BorderLayout.WEST);
        postPanel.add(postContentField, BorderLayout.CENTER);
        postPanel.add(postButton, BorderLayout.EAST);
        
        // Comment Panel
        JPanel commentPanel = new JPanel(new BorderLayout(5, 0));
        commentField = new JTextField();
        commentButton = new JButton("Comentar");
        commentPanel.add(new JLabel("Comentario:"), BorderLayout.WEST);
        commentPanel.add(commentField, BorderLayout.CENTER);
        commentPanel.add(commentButton, BorderLayout.EAST);

        panel.add(friendPanel);
        panel.add(postPanel);
        panel.add(commentPanel);
        
        addFriendButton.addActionListener(e -> addFriend());
        postButton.addActionListener(e -> addPost());
        commentButton.addActionListener(e -> addComment()); // Add comment button listener
        
        return panel;
    }
    
    private void addUser() {
        String username = userField.getText().trim();
        if (!username.isEmpty()) {
            UberSocial.agregarCuenta(username, "FACEBOOK");
            userField.setText("");
            refreshUserList();
            displayArea.append("Cuenta de Facebook agregada: " + username + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre de usuario");
        }
    }
    
    private void refreshUserList() {
        userModel.removeAllElements();
        ArrayList<String> usuariosFacebook = UberSocial.obtenerUsuariosFacebook();
        for (String user : usuariosFacebook) {
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
    
    private void addComment() {
        String selectedUser = (String) userSelector.getSelectedItem();
        String content = commentField.getText().trim();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese contenido para el comentario");
            return;
        }
        
        // Assuming postId and author are available, use UberSocial to add a comment
        int postId = 1; // Replace with actual post ID logic
        String author = selectedUser; // The author is the selected user
        
        Comment comment = new Comment(postId, author, content);
        UberSocial.agregarComentario(selectedUser, comment); // Call UberSocial to add the comment
        commentField.setText("");
        displayArea.append("Comentario publicado: " + content + "\n");
    }
    
    private void showProfile() {
        String selectedUser = (String) userSelector.getSelectedItem();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        OutputCapturer capturer = new OutputCapturer();
        capturer.start();
        UberSocial.profileFrom(selectedUser);
        displayArea.setText(capturer.stop());
    }
    
    private void showTimeline() {
        String selectedUser = (String) userSelector.getSelectedItem();
        
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario");
            return;
        }
        
        SocialClass account = UberSocial.buscar(selectedUser);
        if (account instanceof FaceBook) {
            OutputCapturer capturer = new OutputCapturer();
            capturer.start();
            ((FaceBook) account).timeLine();
            displayArea.setText(capturer.stop());
        } else {
            displayArea.setText("Error: La cuenta seleccionada no es una cuenta de Facebook.");
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
