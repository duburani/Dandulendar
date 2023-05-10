package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.domain.UserGroup;
import damdorani.dandulendar.dto.UserForm;
import damdorani.dandulendar.dto.UserGroupForm;
import damdorani.dandulendar.repository.GroupRepository;
import damdorani.dandulendar.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

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



}
