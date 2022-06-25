package bg.softuni.springexam.web;

import bg.softuni.springexam.bindingModel.SongDto;
import bg.softuni.springexam.service.SongService;
import bg.softuni.springexam.service.StyleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/songs")
public class SongController {
    private final SongService songService;
    private final StyleService styleService;

    public SongController(SongService songService, StyleService styleService) {
        this.songService = songService;
        this.styleService = styleService;
    }

    @ModelAttribute("songModel")
    public SongDto songDto(Model model) {
        model.addAttribute("styles", styleService.findAllStyles());
        return new SongDto();
    }

    @GetMapping("/add")
    public String addSongPage() {
        return "song-add";
    }

    @PostMapping("/add")
    public String addSong(@Valid SongDto songDto,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("songModel", songDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.songModel", bindingResult);
            return "redirect:/songs/add";
        }

        boolean songIsAdded = songService.addSong(songDto);
        if (!songIsAdded) {
            redirectAttributes.addFlashAttribute("songModel", songDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.songModel", bindingResult);
            redirectAttributes.addFlashAttribute("styles", styleService.findAllStyles());
            return "redirect:/songs/add";
        }
        return "redirect:/home";
    }

    @GetMapping("/add/{songId}")
    public String addSongToPlaylist(@PathVariable Long songId) {
        songService.saveSongToPlaylist(songId);
        return "redirect:/home";
    }

    @GetMapping("/remove-all")
    public String removeSongsFromPlaylist() {
        songService.removeSongsFromPlaylist();
        return "redirect:/home";
    }
}