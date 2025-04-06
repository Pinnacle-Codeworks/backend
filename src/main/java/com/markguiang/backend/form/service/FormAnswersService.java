package com.markguiang.backend.form.service;

import com.markguiang.backend.form.model.FieldAnswer;
import com.markguiang.backend.form.model.FormAnswers;
import com.markguiang.backend.form.repository.FieldAnswerRepository;
import com.markguiang.backend.form.repository.FormAnswersRepository;
import com.markguiang.backend.user.UserContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FormAnswersService {
    private final FieldAnswerRepository fieldAnswerRepository;
    private final FormAnswersRepository formAnswersRepository;
    private final UserContext userContext;

    public FormAnswersService(FieldAnswerRepository fieldAnswerRepository, FormAnswersRepository formAnswersRepository, UserContext userContext) {
        this.fieldAnswerRepository = fieldAnswerRepository;
        this.formAnswersRepository = formAnswersRepository;
        this.userContext = userContext;
    }

    @Transactional
    public FormAnswers answerForm(FormAnswers formAnswers) {
        formAnswers.clearIds();
        formAnswers.setUserId(userContext.getUser().getUserId());

        FormAnswers formAnswersDb = formAnswersRepository.save(formAnswers);
        List<FieldAnswer> fieldAnswerList = formAnswers.getFieldAnswerList();
        for (FieldAnswer fieldAnswer: fieldAnswerList)  {
            fieldAnswer.setFormAnswersId(formAnswers.getFormAnswersId());
        }
        fieldAnswerRepository.saveAll(fieldAnswerList);

        return  formAnswersDb;
    }
}
