package com.dashboard.dashboard.services;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.domain.post.Post;
import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.dto.post.postDTO;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import com.dashboard.dashboard.repository.DataJPAPostRepository;
import com.exceptons.DuplicateMemberException;
import com.exceptons.MemberNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final DataJPAPostRepository dataJPAPostRepository;
    private final DataJPAMemberRepository dataJPAMemberRepository;

    @Autowired
    public PostService(DataJPAPostRepository dataJPAPostRepository, DataJPAMemberRepository dataJPAMemberRepository) {
        this.dataJPAPostRepository = dataJPAPostRepository;
        this.dataJPAMemberRepository = dataJPAMemberRepository;
    }

    @Transactional
    public postDTO register(postDTO postDTO) {
        Member author = findMemberOrThrow(postDTO.getAuthor());
        Post newPost = Post.createPost(postDTO.getTitle(), postDTO.getContent(), author);
        Post savedPost = dataJPAPostRepository.save(newPost);
        return postDTO.of(savedPost);
    }


    private Member findMemberOrThrow(Long authorId) {
        return dataJPAMemberRepository.findByMemberId(authorId)
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다. 회원 아이디: " + authorId));
    }
}
