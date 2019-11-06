package apbdoo.onlineLib.domain;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Username is required!")
    @Size(min = 5, message = "Minimum size for username is 5!")
    private String username;

    @NotBlank(message = "Password is required!")
    @Size(min = 7, message = "Minimum size for password is 7!")
    private String password;

    @Email(message = "Email address is not valid!")
    @NotBlank(message = "Email is required!")
    private String email;

    @Transient
    private String confirmPassword;

    @Transient
    @AssertTrue(message = "You have to accept terms and conditions!")
    private Boolean terms;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;


    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private Set<Review> reviews = new HashSet<Review>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, mappedBy = "userFav")
    private Set<Favourites> favourites = new HashSet<Favourites>();

}
