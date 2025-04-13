package com.markguiang.backend.form;

import com.markguiang.backend.form.mapper.FormMapper;
import com.markguiang.backend.form.dto.request.CreateFormDTO;
import com.markguiang.backend.form.dto.request.UpdateFormDTO;
import com.markguiang.backend.form.dto.response.FormResponseDTO;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.service.FormService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormController {
    private final FormService formService;
    private final FormMapper formMapper;

    public FormController(FormService formService, FormMapper formMapper) {
        this.formService = formService;
        this.formMapper = formMapper;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public FormResponseDTO createForm(@RequestBody CreateFormDTO createFormDTO) {
        Form form = this.formMapper.createFormDTOtoForm(createFormDTO);
        Form formResult = formService.createFormWithFieldList(form);
        return this.formMapper.formToFormResponseDTO(formResult);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public FormResponseDTO updateForm(@RequestBody UpdateFormDTO updateFormDTO) {
       Form form = this.formMapper.updateFormDTOtoForm(updateFormDTO);
       Form formResult = formService.updateFormWithFields(form);
       return this.formMapper.formToFormResponseDTO(formResult);
    }
}