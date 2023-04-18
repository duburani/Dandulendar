package damdorani.dandulendar.service;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    // 그룹 생성

    // 그룹 조회
    public Group findGroup(int id){
        return groupRepository.findGroup(id);
    }
}
