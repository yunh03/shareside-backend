package kr.yuns.shareside_backend.domain.auth.data.dao.Impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import kr.yuns.shareside_backend.domain.auth.data.dao.UserDao;
import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.auth.data.repository.UserRepository;
import kr.yuns.shareside_backend.domain.auth.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private UserRepository userRepository;

    @Override
    public User getUserEntity(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()) {
            return user.get;
        } else {
            throw new UserNotFoundException("해당 사용자를 찾을 수 없습니다.");
        }
    }
}
