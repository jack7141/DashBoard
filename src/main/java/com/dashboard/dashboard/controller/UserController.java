package com.dashboard.dashboard.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "사용자", description = "사용자 관련 API")
public class UserController {
    @Operation(
            summary = "사용자 조회",
            description = "사용자를 조회한 정보를 반환함",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회된 사용자 정보를 반환함."
                    )
            }
    )
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, String> getUser(
            @Parameter(description = "조회할 사용자의 아이디", example = "userid")
            @PathVariable(name = "id", required = true) String id
    ) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", "홍길동");


        return map;
    }
}