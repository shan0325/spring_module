package com.sm.app.admin.web.allowWord.usecase;

import com.sm.app.admin.web.allowWord.dto.AllowWordCreateRequestDto;
import com.sm.app.admin.web.allowWord.dto.AllowWordModifyRequestDto;
import com.sm.app.admin.web.allowWord.dto.AllowWordResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.allowWord.entity.AllowWord;
import com.sm.app.domainrdb.core.allowWord.repository.AllowWordRepository;
import com.sm.app.domainrdb.core.allowWord.repository.query.AllowWords;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class AllowWordUseCase {
    private final AllowWordRepository allowWordRepository;

    @Transactional(readOnly = true)
    public Page<AllowWords> getAllowWords(Pageable pageable) {
        List<AllowWords> allowWords = allowWordRepository.findAllowWordsByPage(pageable);
        Long allowWordsCount = allowWordRepository.findAllowWordsCount();
        return new PageImpl<>(allowWords, pageable, allowWordsCount);
    }

    @Transactional(readOnly = true)
    public AllowWordResponseDto getAllowWord(Long id) {
        AllowWord allowWord = allowWordRepository.findById(id).orElseThrow(() -> new ServiceException("AllowWord Not Found By Id: " + id));
        return AllowWordResponseDto.from(allowWord);
    }

    public Long addAllowWord(AllowWordCreateRequestDto allowWordCreateRequestDto) {
        AllowWord allowWord = AllowWord.createAllowword(allowWordCreateRequestDto.getWord());
        allowWordRepository.save(allowWord);
        return allowWord.getId();
    }

    public void modifyAllowWord(Long id, AllowWordModifyRequestDto allowWordModifyRequestDto) {
        AllowWord allowWord = allowWordRepository.findById(id).orElseThrow(() -> new ServiceException("AllowWord Not Found ById: " + id));
        allowWord.modifyAllowWord(allowWordModifyRequestDto.getWord());
    }

    public void removeAllowWord(Long id) {
        allowWordRepository.deleteById(id);
    }
}
