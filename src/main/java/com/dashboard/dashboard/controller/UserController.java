package com.dashboard.dashboard.controller;

import com.dashboard.dashboard.dto.responsedto.DataResponseDTO;
import com.dashboard.dashboard.dto.member.memberDTO;
import com.dashboard.dashboard.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "사용자", description = "사용자 관련 API")
public class UserController {

    private final MemberService memberService;

    @Operation(
            summary = "사용자 조회",
            description = "사용자를 조회한 정보를 반환함",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회된 사용자 정보를 반환함.",
                            content = @Content(schema = @Schema(implementation = memberDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public DataResponseDTO<memberDTO> getUser(
            @Parameter(description = "조회할 사용자의 아이디", example = "1")
            @PathVariable(name = "id", required = true) Long id
    ) {
        memberDTO member = memberService.getMemberById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return DataResponseDTO.of(member);
    }

    @GetMapping("")
    public DataResponseDTO<List<memberDTO>> getUsers() {
        List<memberDTO> members = memberService.getMembers();
        return DataResponseDTO.of(members);
    }

    @GetMapping("/phone/{numbers}")
    public DataResponseDTO<memberDTO> getUsersPhoneNumbers(
            @Parameter(description = "조회할 사용자의 핸드폰 번호", example = "1")
            @PathVariable(name = "numbers", required = true) String numbers
    ) {
        memberDTO member = memberService.getMembersByUserPhoneNumber(numbers)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회한 핸드폰 번호로 회원을 찾을 수 없습니다."));
        return DataResponseDTO.of(member);
    }

    @PostMapping("")
    public DataResponseDTO<memberDTO> createUser(
            @RequestBody memberDTO createUserDTO
    ) {
        memberDTO newMember = memberService.add(createUserDTO);
        return DataResponseDTO.of(newMember);
    }
}