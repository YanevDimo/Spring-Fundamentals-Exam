package bg.softuni.springexam.service;

import bg.softuni.springexam.bindingModel.SongDto;
import bg.softuni.springexam.dao.SongRepository;
import bg.softuni.springexam.model.Song;
import bg.softuni.springexam.model.Style;
import bg.softuni.springexam.model.User;
import bg.softuni.springexam.session.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository songRepository;
    private final ModelMapper modelMapper;
    private final StyleService styleService;
    private final UserSession userSession;
    private final UserService userService;

    public SongService(SongRepository songRepository, ModelMapper modelMapper, StyleService styleService, UserSession userSession, UserService userService) {
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
        this.styleService = styleService;
        this.userSession = userSession;
        this.userService = userService;
    }

    public boolean addSong(SongDto songDto) {
        String performer = songDto.getPerformer();
        String styleName = songDto.getStyle();

        if (songRepository.findSongByPerformer(performer).isPresent()) {
            return false;
        }
        Style styleEntity = styleService.findStyleByStyleName(styleName);
        Song songEntity = modelMapper.map(songDto, Song.class);
        songEntity.setStyle(styleEntity);

        songRepository.save(songEntity);
        return true;
    }

    public List<Song> findAllPopSongs() {
        return songRepository.findAll()
                .stream()
                .filter(song -> song.getStyle().getStyleName().name().toUpperCase(Locale.ROOT).equals("POP"))
                .collect(Collectors.toList());
    }

    public List<Song> findAllRockSongs() {
        return songRepository.findAll()
                .stream()
                .filter(song -> song.getStyle().getStyleName().name().toUpperCase(Locale.ROOT).equals("ROCK"))
                .collect(Collectors.toList());
    }

    public List<Song> findAllJazz() {
        return songRepository.findAll()
                .stream()
                .filter(song -> song.getStyle().getStyleName().name().toUpperCase(Locale.ROOT).equals("JAZZ"))
                .collect(Collectors.toList());
    }

    public void saveSongToPlaylist(Long id) {
        Song songEntity = songRepository.findById(id).orElse(null);
        Long sessionId = userSession.getId();
        User userEntity = userService.findUserById(sessionId);

        if (userEntity.getSongs().contains(songEntity)) {
            return;
        }

        userEntity.getSongs().add(songEntity);
        userService.saveUser(userEntity);
    }

    public void removeSongsFromPlaylist() {
        Long sessionId = userSession.getId();
        User userEntity = userService.findUserById(sessionId);
        userEntity.setSongs(new ArrayList<>());
        userService.saveUser(userEntity);
    }
}