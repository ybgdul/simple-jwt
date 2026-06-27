package simple_jwt.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import simple_jwt.app.entities.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long>{
    
    public Optional<AppUser> findByUsername(String username);
}
