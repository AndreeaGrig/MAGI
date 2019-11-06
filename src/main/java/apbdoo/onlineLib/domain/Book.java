package apbdoo.onlineLib.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Required field!")
    private String title;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Required field!")
    private Category category;

    private Date addDate;

    @Lob
    private Byte[] cover;

    private String urlPdf;

    @Min(value=5, message = "Min value 5!")
    @NotNull(message = "Required field!")
    private Integer numberOfPages;


    @ManyToOne
    @NotNull(message = "Required field!")
    private PubHouse pubHouse;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @NotNull(message = "Required field!")
    private Set<Author> authors = new HashSet<Author>();

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull(message = "Required field!")
    private BookInfo bookInfo;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "book")
    private Set<Review> reviews = new HashSet<Review>();

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "bookFav")
    private Set<Favourites> favourites = new HashSet<Favourites>();

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        bookInfo.setBook(this);
    }

    public void addAuthor(Author author){
        this.authors.add(author);
        author.addBook(this);
    }

    public void setAuthors(Set<Author> authors ){
        this.authors = authors;
        for (Author author:authors) {
            author.addBook(this);
        }
    }

    public Integer getNoOfFavs(){
        return this.getFavourites().size();
    }

}
