package com.sm.app.admin.web.banWord.service;

import com.sm.app.admin.web.banWord.dto.BanWordCreateRequestDto;
import com.sm.app.admin.web.banWord.dto.BanWordModifyRequestDto;
import com.sm.app.admin.web.banWord.dto.BanWordResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.banWord.entity.BanWord;
import com.sm.app.domainrdb.core.banWord.repository.BanWordRepository;
import com.sm.app.domainrdb.core.banWord.repository.query.BanWords;
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
public class BanWordService {
    private final BanWordRepository banWordRepository;

    @Transactional(readOnly = true)
    public Page<BanWords> getBanWords(Pageable pageable) {
        List<BanWords> banWords = banWordRepository.findBanWordsByPage(pageable);
        Long banWordsCount = banWordRepository.findBanWordsCount();
        return new PageImpl<>(banWords, pageable, banWordsCount);
    }

    @Transactional(readOnly = true)
    public BanWordResponseDto getBanWord(Long id) {
        BanWord banWord = banWordRepository.findById(id).orElseThrow(() -> new ServiceException("BanWord Not Found By Id: " + id));
        return BanWordResponseDto.from(banWord);
    }

    public Long addBanWord(BanWordCreateRequestDto banWordCreateRequestDto) {
        BanWord banWord = BanWord.createBanWord(banWordCreateRequestDto.getWord());
        banWordRepository.save(banWord);
        return banWord.getId();
    }

    public void modifyBanWord(Long id, BanWordModifyRequestDto banWordModifyRequestDto) {
        BanWord banWord = banWordRepository.findById(id).orElseThrow(() -> new ServiceException("Banword Not Found ById: " + id));
        banWord.modifyBanWord(banWordModifyRequestDto.getWord());
    }

    public void removeBanWord(Long id) {
        banWordRepository.deleteById(id);
    }
}
