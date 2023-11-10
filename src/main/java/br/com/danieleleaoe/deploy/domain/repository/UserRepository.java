package  br.com.danieleleaoe.deploy.domain.repository;

import java.util.List;

import  br.com.danieleleaoe.deploy.domain.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long>  {

    @Query("SELECT u FROM User u WHERE u.login = ?1 and u.senha = ?2")
    User findUserByLoginAndPassword(String login, String password);

}
