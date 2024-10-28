package exam.rea.examothmane.bean;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Employee {
    @Id 
    @GeneratedValue
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    
    // Constructeur avec tous les attributs
    public Employee(Long id, String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }
    
    public Employee() {
        this.id = null;
        this.nom = null;
        this.prenom = null;
        this.email = null;
    }

    // Getter pour l'id
    public Long getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter pour le nom
    public String getName() {
        return nom;
    }

    // Setter pour le nom
    public void setName(String name) {
        this.nom = name;
    }

    // Getter pour le prenom
    public String getPrenom() {
        return prenom;
    }

    // Setter pour le prenom
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Getter pour l email
    public String getEmail() {
        return email;
    }

    // Setter pour l email
    public void setEmail(String email) {
        this.email = email;
    }
    
}






