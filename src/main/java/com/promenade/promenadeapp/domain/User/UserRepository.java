package com.promenade.promenadeapp.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByGoogleId(String googleId);
    Boolean existsByGoogleId(String googleId);
    User findByGoogleIdAndEmail(String googleId, String email);

    // Test
    Boolean existsByEmail(String email);
    User findByEmail(String email);
}
