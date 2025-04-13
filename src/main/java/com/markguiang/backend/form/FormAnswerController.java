package com.markguiang.backend.form;

import com.markguiang.backend.form.dto.request.AnswerFormDTO;
import com.markguiang.backend.form.dto.mapper.FormRequestMapper;
import com.markguiang.backend.form.model.FormAnswers;
import com.markguiang.backend.form.service.FormAnswersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
public class FormAnswerController {
    private final FormAnswersService formAnswersService;
    private final FormRequestMapper formRequestMapper;

    public FormAnswerController(FormAnswersService formAnswersService, FormRequestMapper formRequestMapper) {
        this.formAnswersService = formAnswersService;
        this.formRequestMapper = formRequestMapper;
    }

    @PostMapping("/answers")
    public FormAnswers answerForm(@RequestBody AnswerFormDTO answerFormDTO) {
        FormAnswers formAnswers = this.formRequestMapper.answerFormDTOtoFormAnswers(answerFormDTO);
        return formAnswersService.answerForm(formAnswers);
    }
}