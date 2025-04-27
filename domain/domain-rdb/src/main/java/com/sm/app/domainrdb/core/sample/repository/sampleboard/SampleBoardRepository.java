package com.sm.app.domainrdb.core.sample.repository.sampleboard;

import com.sm.app.domainrdb.core.sample.entity.SampleBoard;
import com.sm.app.domainrdb.core.sample.repository.sampleboard.SampleBoardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleBoardRepository extends JpaRepository<SampleBoard, Long>, SampleBoardRepositoryCustom {
}
