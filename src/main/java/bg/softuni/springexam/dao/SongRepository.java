package bg.softuni.springexam.dao;

import bg.softuni.springexam.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findSongByPerformer(String performer);
}