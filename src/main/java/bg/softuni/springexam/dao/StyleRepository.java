package bg.softuni.springexam.dao;

import bg.softuni.springexam.enumeration.StyleEnum;
import bg.softuni.springexam.model.Style;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Long> {
    Style findStyleByStyleName(@NonNull StyleEnum styleName);
}