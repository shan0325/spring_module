package com.sm.app.domainrdb.core.sample.repository;

import com.sm.app.domainrdb.core.sample.entity.SampleBoardManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleBoardManagerRepository extends JpaRepository<SampleBoardManager, Long> {
}
