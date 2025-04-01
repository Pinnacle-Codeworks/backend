package com.markguiang.backend.form;

import com.markguiang.backend.form.dto.FieldRequestDTO;
import com.markguiang.backend.form.dto.FormRequestDTO;
import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.service.FormService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/form")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public Form createForm(@RequestBody FormRequestDTO formDTO) {
        Form form  = FormRequestDTO.toForm(formDTO, false);
        return formService.createForm(form);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public Form editFormFields(@RequestBody FormRequestDTO formDTO) {
        Form form  = FormRequestDTO.toForm(formDTO, true);
        return formService.editFormFields(form);
    }
}
