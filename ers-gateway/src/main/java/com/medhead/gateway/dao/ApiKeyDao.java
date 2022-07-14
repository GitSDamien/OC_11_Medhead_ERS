package com.medhead.gateway.dao;

import com.medhead.gateway.model.ApiKey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyDao extends CrudRepository<ApiKey, Long> {
    public Iterable<ApiKey> findAllByRoute(String routeId);
}
