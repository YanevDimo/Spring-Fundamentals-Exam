package bg.softuni.springexam.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "songs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Song extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String performer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer duration;

    @Column
    private LocalDate releaseDate;

    @ManyToMany(mappedBy = "songs")
    private List<User> users;

    @ManyToOne
    private Style style;
}