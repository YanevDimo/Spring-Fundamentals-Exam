package bg.softuni.springexam;

import bg.softuni.springexam.dao.StyleRepository;
import bg.softuni.springexam.enumeration.StyleEnum;
import bg.softuni.springexam.model.Style;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class StyleSeeder implements CommandLineRunner {
    private final StyleRepository styleRepository;

    public StyleSeeder(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (styleRepository.count() == 0) {
            Arrays.stream(StyleEnum.values())
                    .map(Style::new)
                    .forEach(styleRepository::save);
        }
    }
}