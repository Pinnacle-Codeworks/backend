package com.markguiang.backend.form;

import com.markguiang.backend.form.model.FormAnswers;
import com.markguiang.backend.form.service.FormAnswersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/form")
public class FormAnswerController {
    public final FormAnswersService formAnswersService;

    public FormAnswerController(FormAnswersService formAnswersService) {
        this.formAnswersService = formAnswersService;
    }

    @PostMapping("/answers")
    public FormAnswers answerForm(@RequestBody FormAnswers formAnswers) {
        return formAnswersService.answerForm(formAnswers);
    }
}
