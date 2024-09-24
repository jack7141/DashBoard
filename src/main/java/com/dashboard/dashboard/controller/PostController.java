package com.dashboard.dashboard.controller;


import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.dto.post.postDTO;
import com.dashboard.dashboard.dto.responsedto.DataResponseDTO;
import com.dashboard.dashboard.services.MemberService;
import com.dashboard.dashboard.services.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Tag(name = "게시물", description = "게시물 관련 API")
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<DataResponseDTO<postDTO>> createPost(@RequestBody postDTO postDTO) {
        postDTO createdPost = postService.register(postDTO);
        return ResponseEntity.ok(DataResponseDTO.of(createdPost, "게시글 작성이 성공적으로 완료되었습니다."));
    }
}
