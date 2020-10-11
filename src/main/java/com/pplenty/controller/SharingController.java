package com.pplenty.controller;

import com.pplenty.domain.Sharing;
import com.pplenty.dto.ApiResponse;
import com.pplenty.dto.SharingHeaderDto;
import com.pplenty.dto.SharingRequestDto;
import com.pplenty.dto.SharingResponseDto;
import com.pplenty.service.SharingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Created by yusik on 2020/10/08.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sharing")
public class SharingController {

    private final SharingService sharingService;

    @PostMapping
    public ApiResponse<String> shareMoney(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") long roomId,
            @RequestBody SharingRequestDto requestDto) {

        requestDto.setHeaders(userId, roomId);
        Sharing sharing = sharingService.generateSharing(requestDto);
        log.debug("sharing: {}", sharing);
        return ApiResponse.ok(sharing.getToken());
    }

    @PutMapping("{token}")
    public ApiResponse<Long> takeMoney(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") long roomId,
            @PathVariable String token) {

        return ApiResponse.ok(sharingService.takeMoney(token, SharingHeaderDto.of(userId, roomId)));
    }

    @GetMapping("{token}")
    public ApiResponse<SharingResponseDto> getTakenStatus(
            @RequestHeader("X-USER-ID") long userId,
            @RequestHeader("X-ROOM-ID") long roomId,
            @PathVariable String token) {
        return ApiResponse.ok(sharingService.getTakenStatusByToken(token, SharingHeaderDto.of(userId, roomId)));
    }
}
