package bg.softuni.springexam.service;

import bg.softuni.springexam.dao.StyleRepository;
import bg.softuni.springexam.enumeration.StyleEnum;
import bg.softuni.springexam.model.Style;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StyleService {
    private final StyleRepository styleRepository;

    public StyleService(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    public Style findStyleByStyleName(String styleName) {
        StyleEnum styleEnum = StyleEnum.valueOf(styleName);
        return styleRepository.findStyleByStyleName(styleEnum);
    }

    public List<Style> findAllStyles() {
        return styleRepository.findAll();
    }
}