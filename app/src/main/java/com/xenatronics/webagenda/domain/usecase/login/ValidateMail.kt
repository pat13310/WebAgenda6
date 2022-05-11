package com.xenatronics.webagenda.domain.usecase.login

import android.util.Patterns
import com.xenatronics.webagenda.domain.usecase.ResultUseCase

class ValidateMail() {

    fun execute(mail: String): ResultUseCase {
        if (mail.isBlank())
            return ResultUseCase.Empty
        if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches())
            return ResultUseCase.PatternInvalid
        return ResultUseCase.OK
    }
}
