package com.sm.app.admin.web.sample.controller;

import com.sm.app.admin.web.sample.dto.SampleBoardManagerCreateRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardManagerModifyRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardManagerResponseDto;
import com.sm.app.admin.web.sample.usecase.SampleBoardManagerUseCase;
import com.sm.app.domainrdb.core.sample.enums.BoardTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class SampleBoardManagerController {


    private final SampleBoardManagerUseCase sampleBoardManagerUseCase;
    // 게시판 관리자 목록 페이지 이동
    @GetMapping("/sample/board-managers")
    public String sampleBoardManagers(Model model) {
        model.addAttribute("boardManagers", sampleBoardManagerUseCase.getSampleBoardManagers());
        return "pages/sample/boardManagers";
    }

    // 게시판 관리자 등록 페이지 이동
    @GetMapping("/sample/board-managers/create")
    public String sampleBoardManagersCreate(Model model) {
        model.addAttribute("boardTypes", BoardTypeEnum.values());
        model.addAttribute("boardManager", new SampleBoardManagerResponseDto());
        return "pages/sample/boardManager";
    }

    // 게시판 관리자 등록 API
    @PostMapping("/api/sample/board-managers")
    public ResponseEntity<Object> createSampleBoardManager(@RequestBody @Valid SampleBoardManagerCreateRequestDto createDto) {
        sampleBoardManagerUseCase.createSampleBoardManager(createDto);
        return ResponseEntity.ok().build();
    }

    // 게시판 관리자 수정 페이지 이동
    @GetMapping("/sample/board-managers/{id}")
    public String sampleBoardManager(@PathVariable Long id, Model model) {
        model.addAttribute("boardTypes", BoardTypeEnum.values());
        model.addAttribute("boardManager", sampleBoardManagerUseCase.getSampleBoardManager(id));
        return "pages/sample/boardManager";
    }

    // 게시판 관리자 수정 API
    @PutMapping("/api/sample/board-managers/{id}")
    public ResponseEntity<Object> modifySampleBoardManager(@PathVariable Long id,
                                                           @RequestBody @Valid SampleBoardManagerModifyRequestDto modifyDto) {
        sampleBoardManagerUseCase.modifySampleBoardManager(id, modifyDto);
        return ResponseEntity.ok().build();
    }

    // 게시판 관리자 삭제 API
    @DeleteMapping("/api/sample/board-managers/{id}")
    public ResponseEntity<Object> deleteSampleBoardManager(@PathVariable Long id) {
        sampleBoardManagerUseCase.deleteSampleBoardManager(id);
        return ResponseEntity.ok().build();
    }
}
