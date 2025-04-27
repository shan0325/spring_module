package com.sm.app.admin.web.sample.controller;

import com.sm.app.admin.web.sample.dto.SampleBoardCreateRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardModifyRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardResponseDto;
import com.sm.app.admin.web.sample.dto.SampleBoardsResponseDto;
import com.sm.app.admin.web.sample.service.SampleBoardManagerService;
import com.sm.app.admin.web.sample.service.SampleBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class SampleBoardController {

    private final SampleBoardManagerService sampleBoardManagerService;
    private final SampleBoardService sampleBoardService;


    // 게시판 목록 페이지 이동
    @GetMapping("/sample/boards")
    public String getSampleBoards(@RequestParam(required = false) Long boardManagerId,
                                  Pageable pageable,
                                  Model model) {

        Page<SampleBoardsResponseDto> sampleBoards = sampleBoardService.getSampleBoards(boardManagerId, pageable);

        model.addAttribute("boardManagerId", boardManagerId);
        model.addAttribute("boardManagers", sampleBoardManagerService.getSampleBoardManagers());
        model.addAttribute("boards", sampleBoards.getContent());
        model.addAttribute("paging", sampleBoards);
        return "pages/sample/boards";
    }

    @GetMapping("/api/sample/boards")
    public ResponseEntity<Page<SampleBoardsResponseDto>> getApiSampleBoards(
            @RequestParam(required = false) Long boardManagerId,
            Pageable pageable) {

        return ResponseEntity.ok(sampleBoardService.getSampleBoards(boardManagerId, pageable));
    }

    // 게시판 등록 페이지 이동
    @GetMapping("/sample/boards/create")
    public String sampleBoardCreate(@RequestParam Long boardManagerId, Model model) {
        model.addAttribute("boardManagerId", boardManagerId);
        model.addAttribute("board", new SampleBoardResponseDto());
        return "pages/sample/board";
    }

    // 게시판 등록 API
    @PostMapping("/api/sample/boards")
    public ResponseEntity<Object> createSampleBoard(@RequestBody @Valid SampleBoardCreateRequestDto createDto) {
        sampleBoardService.createSampleBoard(createDto);
        return ResponseEntity.ok().build();
    }

    // 게시판 수정 페이지 이동
    @GetMapping("/sample/boards/{id}")
    public String sampleBoard(@PathVariable Long id, @RequestParam Long boardManagerId, Model model) {
        model.addAttribute("boardManagerId", boardManagerId);
        model.addAttribute("board", sampleBoardService.getSampleBoard(id));
        return "pages/sample/board";
    }

    // 게시판 수정 API
    @PutMapping("/api/sample/boards/{id}")
    public ResponseEntity<Object> modifySampleBoard(@PathVariable Long id,
                                                    @RequestBody @Valid SampleBoardModifyRequestDto modifyDto) {
        sampleBoardService.modifySampleBoard(id, modifyDto);
        return ResponseEntity.ok().build();
    }

    // 게시판 삭제 API
    @DeleteMapping("/api/sample/boards/{id}")
    public ResponseEntity<Object> deleteSampleBoard(@PathVariable Long id) {
        sampleBoardService.deleteSampleBoard(id);
        return ResponseEntity.ok().build();
    }
}
