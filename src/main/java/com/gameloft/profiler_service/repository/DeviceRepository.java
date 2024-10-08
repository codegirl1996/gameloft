package com.gameloft.profiler_service.repository;

import com.gameloft.profiler_service.repository.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
}
