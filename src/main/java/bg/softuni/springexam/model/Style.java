package bg.softuni.springexam.model;

import bg.softuni.springexam.enumeration.StyleEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "styles")
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Style extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    @NonNull
    private StyleEnum styleName;

    @Column
    private String description;

    @OneToMany(mappedBy = "style")
    private List<Song> songs;
}