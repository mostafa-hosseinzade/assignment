package com.assignment.SourceCodeData.dao;

import com.assignment.SourceCodeData.model.SourceCodeData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISourceCodeDataRepository extends CrudRepository<SourceCodeData, Long> {

    Optional<SourceCodeData> findByCode(String code);
}
