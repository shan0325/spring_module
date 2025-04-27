package com.sm.app.admin.web.allowWord.controller;

import com.sm.app.admin.web.allowWord.dto.AllowWordCreateRequestDto;
import com.sm.app.admin.web.allowWord.dto.AllowWordModifyRequestDto;
import com.sm.app.admin.web.allowWord.service.AllowWordService;
import com.sm.app.domainrdb.core.allowWord.repository.query.AllowWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AllowWordController {
    private final AllowWordService allowWordService;

    @GetMapping("/allow-words")
    public String allowWords() {
        return "pages/allowWord/allowWords";
    }

    @GetMapping("/api/allow-words")
    public ResponseEntity<Page<AllowWords>> allowWords(Pageable pageable) {
        return ResponseEntity.ok(allowWordService.getAllowWords(pageable));
    }

    @GetMapping("/allow-words/{id}")
    public String allowWord(@PathVariable Long id, Model model) {
        model.addAttribute("allowWord", allowWordService.getAllowWord(id));
        return "pages/allowWord/modify";
    }

    @GetMapping("/allow-words/new")
    public String createAllowWord() {
        return "pages/allowWord/create";
    }

    @PostMapping("/api/allow-words/new")
    public ResponseEntity<Object> createAllowWord(@RequestBody AllowWordCreateRequestDto allowWordCreateRequestDto) {
        Long id = allowWordService.addAllowWord(allowWordCreateRequestDto);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/api/allow-words/{id}")
    public ResponseEntity<Object> modifyAllowWord(@PathVariable Long id, @RequestBody AllowWordModifyRequestDto allowWordModifyRequestDto) {
        allowWordService.modifyAllowWord(id, allowWordModifyRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/allow-words/{id}")
    public ResponseEntity<Object> removeAllowWord(@PathVariable Long id) {
        allowWordService.removeAllowWord(id);
        return ResponseEntity.ok().build();
    }
}
