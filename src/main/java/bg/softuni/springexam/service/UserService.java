package bg.softuni.springexam.service;

import bg.softuni.springexam.bindingModel.UserLoginDto;
import bg.softuni.springexam.bindingModel.UserRegisterDto;
import bg.softuni.springexam.dao.UserRepository;
import bg.softuni.springexam.model.Song;
import bg.softuni.springexam.model.User;
import bg.softuni.springexam.session.UserSession;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserSession userSession;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession;
    }

    public boolean registerUser(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String email = userRegisterDto.getEmail();
        String password = userRegisterDto.getPassword();
        String confirmPassword = userRegisterDto.getConfirmPassword();

        if (userRepository.findUserByUsernameOrEmail(username, email).isPresent()) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            return false;
        }

        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getConfirmPassword()));
        User userEntity = modelMapper.map(userRegisterDto, User.class);
        userRepository.save(userEntity);

        initUserSession(userEntity);
        return true;
    }

    private void initUserSession(User userEntity) {
        userSession.setId(userEntity.getId());
        userSession.setUsername(userEntity.getUsername());
        userSession.setEmail(userEntity.getEmail());
    }

    public boolean loginUser(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return false;
        }

        User userEntity = optionalUser.get();
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            return false;
        }

        initUserSession(userEntity);
        return true;
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<Song> currentUserPlaylist() {
        Long sessionId = userSession.getId();
        User userEntity = userRepository.findById(sessionId).orElse(null);

        assert userEntity != null;
        return userEntity.getSongs();
    }

    public Integer findTotalPlaylistDuration() {
        Long sessionId = userSession.getId();
        User userEntity = userRepository.findById(sessionId).orElse(null);

        assert userEntity != null;
        return userEntity.getSongs().stream()
                .map(Song::getDuration)
                .reduce(0, Integer::sum);
    }

    public void logoutUser() {
        userSession.setId(null);
        userSession.setUsername(null);
        userSession.setEmail(null);
    }
}