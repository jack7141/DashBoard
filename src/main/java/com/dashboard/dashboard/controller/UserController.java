package com.dashboard.dashboard.controller;

import com.dashboard.dashboard.domain.Member;
import com.dashboard.dashboard.repository.DataJPAMemberRepository;
import com.dashboard.dashboard.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "사용자", description = "사용자 관련 API")
public class UserController {
    private final MemberService memberService; // 'final' 키워드 추가


    @Operation(
            summary = "사용자 조회",
            description = "사용자를 조회한 정보를 반환함",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회된 사용자 정보를 반환함.",
                            content = @Content(schema = @Schema(implementation = Member.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Member> getUser(
            @Parameter(description = "조회할 사용자의 아이디", example = "1")
            @PathVariable(name = "id", required = true) Long id

    ) {
        Member member = memberService.getMemberById(id)
                .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

        return ResponseEntity.ok(member);

    }
}