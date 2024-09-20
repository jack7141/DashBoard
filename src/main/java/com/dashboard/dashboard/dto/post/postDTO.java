package com.dashboard.dashboard.dto.post;

import com.dashboard.dashboard.domain.member.Member;
import com.dashboard.dashboard.domain.member.MemberDetail;
import com.dashboard.dashboard.domain.post.Post;
import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.dto.member.memberDetailDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class postDTO {
    private Long author;
    private String title;
    private String content;

    public static postDTO of(Post post) {
        return postDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }


    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
