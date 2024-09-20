package com.dashboard.dashboard.repository;

import com.dashboard.dashboard.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataJPAPostRepository extends JpaRepository<Post, Long> {
}
