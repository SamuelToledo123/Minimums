package se.samuel.minimums.Child;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.samuel.minimums.AppUser.AppUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int age;
    private String allergies;
    private String recommended;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

}


