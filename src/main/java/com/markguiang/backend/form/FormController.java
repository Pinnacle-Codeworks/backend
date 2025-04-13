package com.markguiang.backend.form;

import com.markguiang.backend.form.dto.request.CreateFormDTO;
import com.markguiang.backend.form.dto.request.UpdateFormDTO;
import com.markguiang.backend.form.dto.response.FormResponseDTO;
import com.markguiang.backend.form.dto.mapper.FormRequestMapper;
import com.markguiang.backend.form.dto.mapper.FormResponseMapper;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.service.FormService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormController {
    private final FormService formService;
    private final FormRequestMapper formRequestMapper;
    private final FormResponseMapper formResponseMapper;

    public FormController(FormService formService, FormRequestMapper formRequestMapper, FormResponseMapper formResponseMapper) {
        this.formService = formService;
        this.formRequestMapper = formRequestMapper;
        this.formResponseMapper = formResponseMapper;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public FormResponseDTO createForm(@RequestBody CreateFormDTO createFormDTO) {
        Form form = this.formRequestMapper.createFormDTOtoForm(createFormDTO);
        Form formResult = formService.createFormWithFieldList(form);
        return this.formResponseMapper.formToFormResponseDTO(formResult);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public FormResponseDTO updateForm(@RequestBody UpdateFormDTO updateFormDTO) {
       Form form = this.formRequestMapper.updateFormDTOtoForm(updateFormDTO);
       Form formResult = formService.updateFormWithFields(form);
       return this.formResponseMapper.formToFormResponseDTO(formResult);
    }
}