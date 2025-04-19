package com.markguiang.backend.form.service;

import com.markguiang.backend.form.model.FieldAnswer;
import com.markguiang.backend.form.model.FormAnswers;
import com.markguiang.backend.form.repository.FieldAnswerRepository;
import com.markguiang.backend.form.repository.FormAnswersRepository;
import com.markguiang.backend.user.UserContext;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormAnswersService {
    private final FieldAnswerRepository fieldAnswerRepository;
    private final FormAnswersRepository formAnswersRepository;

    public FormAnswersService(
            FieldAnswerRepository fieldAnswerRepository, FormAnswersRepository formAnswersRepository) {
        this.fieldAnswerRepository = fieldAnswerRepository;
        this.formAnswersRepository = formAnswersRepository;
    }

    @Transactional
    public FormAnswers answerForm(FormAnswers formAnswers) {

        // crosscutting
        formAnswers.setUserId(UserContext.getUser().get().getUserId());

        FormAnswers formAnswersDb = formAnswersRepository.save(formAnswers);
        List<FieldAnswer> fieldAnswerList = formAnswers.getFieldAnswerList();
        for (FieldAnswer fieldAnswer : fieldAnswerList) {
            fieldAnswer.setFormAnswersId(formAnswers.getFormAnswersId());
        }
        fieldAnswerRepository.saveAll(fieldAnswerList);

        return formAnswersDb;
    }
}
