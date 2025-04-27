package com.sm.app.domainrdb.core.sample.repository.sampleboard;

import com.sm.app.domainrdb.core.sample.entity.SampleBoard;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SampleBoardRepositoryCustom {
    List<SampleBoard> findSampleBoards(Long boardManagerId, Pageable pageable);

    Long findSampleBoardsTotalCount(Long boardManagerId);
}
