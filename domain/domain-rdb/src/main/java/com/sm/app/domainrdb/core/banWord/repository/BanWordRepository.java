package com.sm.app.domainrdb.core.banWord.repository;

import com.sm.app.domainrdb.core.banWord.entity.BanWord;
import com.sm.app.domainrdb.core.banWord.repository.BanWordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanWordRepository extends JpaRepository<BanWord, Long>, BanWordRepositoryCustom {
}
