package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import damdorani.dandulendar.dto.GroupForm;
import damdorani.dandulendar.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    // 그룹 생성

    // 그룹 조회
    public Group findGroup(Long groupId){
        Optional<Group> Group = groupRepository.findById(groupId);
        return Group.orElse(null);
//        return groupJpaRepository.findGroup(id);
    }

    @Transactional
    public Group saveGroup(GroupForm groupForm){
        Group group = Group.builder()
                .memorial_date(groupForm.getMemorial_date())
                .build();
        return groupRepository.save(group);
    }

}
