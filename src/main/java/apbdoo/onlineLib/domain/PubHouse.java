package apbdoo.onlineLib.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class PubHouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String city;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pubHouse", fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();


}
