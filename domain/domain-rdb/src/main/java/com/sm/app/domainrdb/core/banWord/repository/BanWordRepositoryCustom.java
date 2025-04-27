package com.sm.app.domainrdb.core.banWord.repository;

import com.sm.app.domainrdb.core.banWord.repository.query.BanWords;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BanWordRepositoryCustom {
    List<BanWords> findBanWordsByPage(Pageable pageable);
    Long findBanWordsCount();
}
