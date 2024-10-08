package com.gameloft.profiler_service.repository;

import com.gameloft.profiler_service.repository.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClanRepository extends JpaRepository<Clan, String> {
}
