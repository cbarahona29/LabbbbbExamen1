package examen_parcial_i;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class UberSocial {
    private static ArrayList<SocialClass> redes = new ArrayList<SocialClass>();
    
    public static SocialClass buscar(String username) {
        return buscarRecursivo(username, 0);
    }
    
    private static SocialClass buscarRecursivo(String username, int index) {
        if (index >= redes.size()) {
            return null;
        }
        SocialClass usuario = redes.get(index);
        if (usuario.getUsername().equals(username)) {
            return usuario;
        }
        return buscarRecursivo(username, index + 1);
    }
    
    public static void agregarCuenta(String username, String tipo) {
        if (username == null || username.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío.");
            return;
        }
        
        if (buscar(username) == null) {
            if (tipo.equalsIgnoreCase("FACEBOOK")) {
                redes.add(new FaceBook(username));
                JOptionPane.showMessageDialog(null, "Cuenta de Facebook agregada exitosamente");
            } else if (tipo.equalsIgnoreCase("TWITTER")) {
                redes.add(new Twitter(username));
                JOptionPane.showMessageDialog(null, "Cuenta de Twitter agregada exitosamente");
            }
        } else {
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe. Por favor, elige otro.");
        }
    }
    
    public static void agregarPost(String username, String post) {
        if (username == null || post == null) {
            return;
        }
        
        SocialClass account = buscar(username);
        if (account != null) {
            account.addPost(post);
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
        }
    }
    
    public static void agregarAmigo(String user1, String user2) {
        // Validación básica de entrada
        if (user1 == null || user2 == null || user1.trim().isEmpty() || user2.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los nombres de usuario no pueden estar vacíos.");
            return;
        }
        
        // Verificar que no sean el mismo usuario
        if (user1.equals(user2)) {
            JOptionPane.showMessageDialog(null, "No puedes agregarte a ti mismo como amigo.");
            return;
        }
        
        // Buscar ambos usuarios
        SocialClass usuario1 = buscar(user1);
        SocialClass usuario2 = buscar(user2);
        
        // Verificar que ambos usuarios existan
        if (usuario1 == null || usuario2 == null) {
            JOptionPane.showMessageDialog(null, "Uno o ambos usuarios no existen.");
            return;
        }
        
        // Verificar si ya son amigos
        if (usuario1.getFriends().contains(user2)) {
            JOptionPane.showMessageDialog(null, "Ya son amigos.");
            return;
        }
        
        // Agregar la amistad en ambas direcciones
        if (usuario1.addFriend(user2)) {
            usuario2.addFriend(user1);
        }
    }
    
    public static void profileFrom(String user) {
        if (user == null || user.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío.");
            return;
        }
        
        SocialClass account = buscar(user);
        if (account != null) {
            account.myProfile();
        } else {
            JOptionPane.showMessageDialog(null, "El usuario no existe.");
        }
    }
    
    public static ArrayList<String> obtenerUsuariosTwitter() {
        ArrayList<String> usuariosTwitter = new ArrayList<>();
        for (SocialClass cuenta : redes) {
            if (cuenta instanceof Twitter) {
                usuariosTwitter.add(cuenta.getUsername());
            }
        }
        return usuariosTwitter;
    }
    
    public static ArrayList<String> obtenerUsuariosFacebook() {
        ArrayList<String> usuariosFacebook = new ArrayList<>();
        for (SocialClass cuenta : redes) {
            if (cuenta instanceof FaceBook) {
                usuariosFacebook.add(cuenta.getUsername());
            }
        }
        return usuariosFacebook;
    }
    public static void agregarComentario(String user, Comment comment) {
    // Logic to add the comment to the user's post
    // You may need to modify the logic based on how you are storing posts and comments
}
}