package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.dto.GroupForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;

    @Test
    void findGroupByUserId() {
        String userId = "naver_oli9OENtDPDqJh6Z1pmFUqvev9UAlnSMkVIJdk-52Ho";
//        List<GroupForm> groupByUserId = groupRepository.findGroupByUserId(userId);
        groupRepository.findGroupByUserId(userId);
    }
}