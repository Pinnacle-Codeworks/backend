package com.markguiang.backend.form.repository;

import com.markguiang.backend.form.model.Form;
import org.springframework.data.repository.CrudRepository;

public interface FormRepository extends CrudRepository<Form, Long> {
}