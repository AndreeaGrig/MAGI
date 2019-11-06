package apbdoo.onlineLib.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;
    private Date addDate;
    private  Date lastEditDate;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private Book book;

    @ManyToOne(cascade = CascadeType.REFRESH)
    private User user;


}
