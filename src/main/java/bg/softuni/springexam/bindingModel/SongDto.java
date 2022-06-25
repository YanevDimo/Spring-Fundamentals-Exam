package bg.softuni.springexam.bindingModel;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SongDto {

    @Size(min = 3, max = 20, message = "Performer length must be between 3 and 20 characters (inclusive 3 and 20)")
    private String performer;

    @Size(min = 2, max = 20, message = "Title length must be btween 2 and 20 (inclusive 2 and 20)")
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "The date cannot be in the future!")
    private LocalDate releaseDate;

    @Positive(message = "Duration must be positive")
    private Integer duration;

    @NotBlank(message = "You must select a style")
    private String style;
}