package com.sm.app.admin.web.sample.service;

import com.sm.app.admin.web.sample.dto.SampleBoardCreateRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardModifyRequestDto;
import com.sm.app.admin.web.sample.dto.SampleBoardResponseDto;
import com.sm.app.admin.web.sample.dto.SampleBoardsResponseDto;
import com.sm.app.common.exception.ServiceException;
import com.sm.app.domainrdb.core.sample.entity.SampleBoard;
import com.sm.app.domainrdb.core.sample.entity.SampleBoardManager;
import com.sm.app.domainrdb.core.sample.repository.SampleBoardManagerRepository;
import com.sm.app.domainrdb.core.sample.repository.sampleboard.SampleBoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SampleBoardService {

    private final SampleBoardManagerRepository sampleBoardManagerRepository;
    private final SampleBoardRepository sampleBoardRepository;
    private final ModelMapper modelMapper;

    public Page<SampleBoardsResponseDto> getSampleBoards(Long boardManagerId, Pageable pageable) {
        List<SampleBoardsResponseDto> boards = sampleBoardRepository.findSampleBoards(boardManagerId, pageable).stream()
                .map(sampleBoard -> modelMapper.map(sampleBoard, SampleBoardsResponseDto.class))
                .toList();

        Long totalCount = sampleBoardRepository.findSampleBoardsTotalCount(boardManagerId);
        return new PageImpl<>(boards, pageable, totalCount);
    }

    @Transactional
    public void createSampleBoard(SampleBoardCreateRequestDto createDto) {
        SampleBoardManager sampleBoardManager = sampleBoardManagerRepository.findById(createDto.getBoardManagerId())
                .orElseThrow(() -> new ServiceException("게시판 관리자를 찾을 수 없습니다."));

        SampleBoard sampleBoard = SampleBoard.createSampleBoard(createDto.getTitle(), createDto.getContent(), sampleBoardManager);
        sampleBoardRepository.save(sampleBoard);
    }

    public SampleBoardResponseDto getSampleBoard(Long id) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판을 찾을 수 없습니다."));

        return modelMapper.map(sampleBoard, SampleBoardResponseDto.class);
    }

    @Transactional
    public void modifySampleBoard(Long id, SampleBoardModifyRequestDto modifyDto) {
        SampleBoardManager sampleBoardManager = sampleBoardManagerRepository.findById(modifyDto.getBoardManagerId())
                .orElseThrow(() -> new ServiceException("게시판 관리자를 찾을 수 없습니다."));

        SampleBoard sampleBoard = sampleBoardRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판을 찾을 수 없습니다."));

        sampleBoard.modifySampleBoard(modifyDto.getTitle(), modifyDto.getContent(), sampleBoardManager);
    }

    @Transactional
    public void deleteSampleBoard(Long id) {
        SampleBoard sampleBoard = sampleBoardRepository.findById(id)
                .orElseThrow(() -> new ServiceException("게시판을 찾을 수 없습니다."));

        sampleBoardRepository.delete(sampleBoard);
     }
}
