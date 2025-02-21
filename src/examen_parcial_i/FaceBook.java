/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_parcial_i;

/**
 *
 * @author Nadiesda Fuentes
 */
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class FaceBook extends SocialClass implements Comentable {
    private ArrayList<Comment> comment;
    
    public FaceBook(String username){
        super(username);
        comment = new ArrayList<>();
    }
    
    public boolean addComment(Comment comment){
        if(comment.getpostId()>=0 && comment.getpostId()<getPost()){
            this.comment.add(comment);
        JOptionPane.showMessageDialog(null, "Comentario agregado.");
            return true;
        }else
        JOptionPane.showMessageDialog(null, "Este post no existe");
        return false;
    }

    
    public void timeLine(){
   for(int i=0;i<getPost(); i++ ){
       System.out.println("POST: "+posts.get(i));
       System.out.println("Comment: ");
       for(Comment comentario: comment){
           if(comentario.getpostId()==i){
               comentario.getComentario();
               comentario.print();
               
           }
       }
   }
    }
    
    
}
