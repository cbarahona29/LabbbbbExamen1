/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package examen_parcial_i;

/**
 *
 * @author Nadiesda Fuentes
 */
public class Twitter extends SocialClass{
     public Twitter(String username) {
        super(username);
    }
    
    void timeLine() {
        System.out.println("Timeline de: " + username);
        for (String post : posts) {
            System.out.println(post);
        }
    }
    
}
