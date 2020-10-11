package com.pplenty.repository;

import com.pplenty.domain.Sharing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yusik on 2020/10/09.
 */
public interface SharingRepository extends JpaRepository<Sharing, Long> {
    Sharing findByToken(String token);
}
