package com.sm.app.domainrdb.core.allowWord.repository;

import com.sm.app.domainrdb.core.allowWord.repository.query.AllowWords;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AllowWordRepositoryCustom {
    List<AllowWords> findAllowWordsByPage(Pageable pageable);
    Long findAllowWordsCount();
}
