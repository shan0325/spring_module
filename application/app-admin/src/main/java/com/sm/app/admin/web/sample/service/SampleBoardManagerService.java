package com.sm.app.admin.web.sample.service;

import com.sm.app.admin.web.sample.dto.SampleBoardManagerCreateRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardManagerModifyRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardManagerResponseDto;
import com.sm.app.admin.web.sample.dto.SampleBoardManagersResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.sample.entity.SampleBoardManager;
import com.sm.app.domainrdb.core.sample.repository.SampleBoardManagerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = true) // 성능 향상을 위한 기본적으로 읽기전용(readOnly = true)으로 설정
@RequiredArgsConstructor
@Service
public class SampleBoardManagerService {

    private final SampleBoardManagerRepository sampleBoardManagerRepository;
    private final ModelMapper modelMapper;

    public List<SampleBoardManagersResponseDto> getSampleBoardManagers() {
        return sampleBoardManagerRepository.findAll().stream()
                .map(bm -> modelMapper.map(bm, SampleBoardManagersResponseDto.class))
                .toList();
    }

    @Transactional // 실제 readOnly = false로 사용할곳에 @Transactional 설정
    public void createSampleBoardManager(SampleBoardManagerCreateRequestDto createDto) {
        SampleBoardManager sampleBoardManager = SampleBoardManager.createSampleBoardManager(createDto.getName(), createDto.getDescription(),
                createDto.getBoardType(), createDto.getUseYn());

        sampleBoardManagerRepository.save(sampleBoardManager);
    }

    public SampleBoardManagerResponseDto getSampleBoardManager(Long id) {
        SampleBoardManager sampleBoardManager = sampleBoardManagerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판 관리자를 찾을 수 없습니다."));

        return modelMapper.map(sampleBoardManager, SampleBoardManagerResponseDto.class);
    }

    @Transactional
    public void modifySampleBoardManager(Long id, SampleBoardManagerModifyRequestDto modifyDto) {
        SampleBoardManager sampleBoardManager = sampleBoardManagerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판 관리자를 찾을 수 없습니다."));

        sampleBoardManager.modifySampleBoardManager(modifyDto.getName(), modifyDto.getDescription(),
                modifyDto.getBoardType(), modifyDto.getUseYn());
    }

    @Transactional
    public void deleteSampleBoardManager(Long id) {
        SampleBoardManager sampleBoardManager = sampleBoardManagerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판 관리자를 찾을 수 없습니다."));

        sampleBoardManagerRepository.delete(sampleBoardManager);
    }
}
