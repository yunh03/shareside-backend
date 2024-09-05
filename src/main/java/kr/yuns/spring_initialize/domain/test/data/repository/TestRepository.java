package kr.yuns.spring_initialize.domain.test.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.yuns.spring_initialize.domain.test.data.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long> {
}