package bg.softuni.springexam.web;

import bg.softuni.springexam.service.SongService;
import bg.softuni.springexam.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final SongService songService;
    private final UserService userService;

    public HomeController(SongService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("popSongs", songService.findAllPopSongs());
        model.addAttribute("rockSongs", songService.findAllRockSongs());
        model.addAttribute("jazzSongs", songService.findAllJazz());
        model.addAttribute("totalSongDuration", userService.findTotalPlaylistDuration());
        model.addAttribute("userSongs", userService.currentUserPlaylist());
        return "home";
    }
}