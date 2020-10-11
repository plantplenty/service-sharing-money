package com.pplenty.repository;

import com.pplenty.domain.Taking;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yusik on 2020/10/09.
 */
public interface TakingRepository extends JpaRepository<Taking, Long> {
}
