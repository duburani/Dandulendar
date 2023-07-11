package damdorani.dandulendar.repository;

import damdorani.dandulendar.domain.Group;
import damdorani.dandulendar.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GroupRepositoryTest {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void test(){
        String userId = "naver_oli9OENtDPDqJh6Z1pmFUqvev9UAlnSMkVIJdk-52Ho";
//        Optional<Group> groupByUserId = groupRepository.findGroupByUserId(userId);
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();

    }

}