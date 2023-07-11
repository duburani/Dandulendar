package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.repository.GroupJpaRepository;
import damdorani.dandulendar.repository.UserJpaRepository;
import damdorani.dandulendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final GroupJpaRepository groupJpaRepository;
    private final UserRepository userRepository;

    public User findUser(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Transactional
    public void saveUser(UserForm userForm) {
        User user = User.builder()
                .user_id(userForm.getUser_id())
                .user_name(userForm.getUser_name())
                .phone(userForm.getPhone())
                .email(userForm.getEmail())
                .provider(userForm.getProvider())
                .build();
        userRepository.save(user);
    }

    public User findUserByCoupleCode(String coupleCode) {
        return userRepository.findUserByCoupleCode(coupleCode).orElse(null);
    }

    @Transactional
    public void setGroup(String userId, Group group){
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);
        user.setGroup(group);
    }
}
