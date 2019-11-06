package apbdoo.onlineLib.services;

import apbdoo.onlineLib.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    User findByEmail(String email);

    void save(User registration);

}
