package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public void saveUser(UserForm userForm){
//        User user = new User(userForm.getUser_id(), userForm.getUser_name(), userForm.getPhone(), userForm.getEmail(), userForm.getProvider());
    }
}
