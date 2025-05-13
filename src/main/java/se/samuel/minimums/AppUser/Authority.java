package se.samuel.minimums.AppUser;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import se.samuel.minimums.AppUser.AppUser;

@Entity
@Getter
@Setter
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="appuser_id")
    private AppUser appUser;
}
