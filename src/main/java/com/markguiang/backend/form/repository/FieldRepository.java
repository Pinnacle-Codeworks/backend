package com.markguiang.backend.form.repository;

import com.markguiang.backend.form.model.Field;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FieldRepository extends CrudRepository<Field, Long> {
    @Query(value = "SELECT f FROM Field f WHERE f.form.formId = :formId")
    List<Field> findFieldByFormId(@Param("formId") Long formId);
}
