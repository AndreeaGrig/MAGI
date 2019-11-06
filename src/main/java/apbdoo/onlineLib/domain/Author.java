package apbdoo.onlineLib.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    private String nationality;

    @ManyToMany(mappedBy = "authors",fetch = FetchType.EAGER)
    private Set<Book> books = new HashSet<Book>();

    public void addBook(Book book){
        this.books.add(book);
    }

    public String getFirstLastName(){
        return this.firstName+" "+this.lastName;
    }
}
