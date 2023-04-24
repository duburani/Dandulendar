package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserForm userForm){
        User user = User.builder()
                .user_id(userForm.getUser_id())
                .user_name(userForm.getUser_name())
                .phone(userForm.getPhone())
                .email(userForm.getEmail())
                .provider(userForm.getProvider())
                .build();
        userRepository.saveUser(user);
    }

    public User findUserById(String id){
        return userRepository.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
