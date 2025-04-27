package com.sm.app.admin.web.banWord.controller;

import com.sm.app.admin.web.banWord.dto.BanWordCreateRequestDto;
import com.sm.app.admin.web.banWord.dto.BanWordModifyRequestDto;
import com.sm.app.admin.web.banWord.dto.BanWordResponseDto;
import com.sm.app.admin.web.banWord.service.BanWordService;
import com.sm.app.domainrdb.core.banWord.repository.query.BanWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class BanWordController {
    private final BanWordService banWordService;

    @GetMapping("/ban-words")
    public String banWords() {
        return "pages/banWord/banWords";
    }

    @GetMapping("/api/ban-words")
    public ResponseEntity<Page<BanWords>> banWords(Pageable pageable) {
        return ResponseEntity.ok(banWordService.getBanWords(pageable));
    }

    @GetMapping("/ban-words/{id}")
    public String banWord(@PathVariable Long id, Model model) {
        BanWordResponseDto banWord = banWordService.getBanWord(id);
        model.addAttribute("banWord", banWord);
        return "pages/banWord/modify";
    }

    @GetMapping("/ban-words/new")
    public String createBanWord(Model model) {
        return "pages/banWord/create";
    }

    @PostMapping("/api/ban-words/new")
    public ResponseEntity<Object> createBanWord(@RequestBody BanWordCreateRequestDto banWordCreateRequestDto) {
        banWordService.addBanWord(banWordCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/ban-words/{id}")
    public ResponseEntity<Object> modifyBanWord(@PathVariable Long id, @RequestBody BanWordModifyRequestDto banWordModifyRequestDto) {
        banWordService.modifyBanWord(id, banWordModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/ban-words/{id}")
    public ResponseEntity<Object> removeBanWord(@PathVariable Long id) {
        banWordService.removeBanWord(id);
        return ResponseEntity.ok().build();
    }
}
