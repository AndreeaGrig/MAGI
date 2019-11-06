package apbdoo.onlineLib.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
    private Set<User> users;

    public Role(String name) {
        this.name = name;
    }

    public Role(){}

}
