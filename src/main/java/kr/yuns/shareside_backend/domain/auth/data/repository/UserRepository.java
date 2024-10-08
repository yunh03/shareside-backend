package kr.yuns.shareside_backend.domain.auth.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    User getByEmail(String email);
    Optional<User> findByEmail(String email);
}