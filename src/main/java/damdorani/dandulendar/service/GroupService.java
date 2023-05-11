package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.domain.UserGroup;
import damdorani.dandulendar.dto.GroupForm;
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
public class GroupService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    // 그룹 생성

    // 그룹 조회
    public Group findGroup(int id){
        return groupRepository.findGroup(id);
    }

    @Transactional
    public int saveGroup(GroupForm groupForm){
        Group group = Group.builder()
                .memorial_date(groupForm.getMemorial_date())
                .build();
        return groupRepository.saveGroup(group);
    }

    @Transactional
    public void saveUserGroup(UserGroupForm userGroupForm, int groupId){

        // user -> group -> userGroup
        Optional<User> user = userRepository.findByUserId(userGroupForm.getUser_id());
        Group group = groupRepository.findGroup(groupId);

        UserGroup userGroup = UserGroup.builder()
                .user(user.get())
                .group(group)
                .build();
        userRepository.saveUserGroup(userGroup);
    }
}
