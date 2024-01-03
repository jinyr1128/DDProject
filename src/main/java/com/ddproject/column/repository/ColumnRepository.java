package com.ddproject.column.repository;

import com.ddproject.column.entity.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column, Long> {

}
