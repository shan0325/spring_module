package com.sm.app.domainrdb.core.allowWord.repository;

import com.sm.app.domainrdb.core.allowWord.entity.AllowWord;
import com.sm.app.domainrdb.core.allowWord.repository.AllowWordRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllowWordRepository extends JpaRepository<AllowWord, Long>, AllowWordRepositoryCustom {
}
