package com.markguiang.backend.form;

import com.markguiang.backend.form.model.Field;
import com.markguiang.backend.form.model.Form;
import com.markguiang.backend.form.service.FormService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/form")
public class FormController {
    private final FormService formService;

    public FormController(FormService formService) {
        this.formService = formService;
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PostMapping("")
    public Form createForm(@RequestBody Form form) {
        form.clearIds();
        return formService.createForm(form);
    }

    @PreAuthorize("hasAuthority('permission:write')")
    @PatchMapping("")
    public Form editFormFields(@RequestBody Form form) {
        return formService.editFormFields(form);
    }
}
