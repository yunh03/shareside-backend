package kr.yuns.shareside_backend.domain.auth.data.dao;

import kr.yuns.shareside_backend.domain.auth.data.entity.User;
import kr.yuns.shareside_backend.domain.auth.exception.UserNotFoundException;

public interface UserDao {
    User getUserEntity(Long userId) throws UserNotFoundException;
}