package damdorani.dandulendar.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    private String userId;
    private String password;

}
