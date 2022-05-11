package com.xenatronics.webagenda.domain.usecase.login

import com.xenatronics.webagenda.domain.usecase.ResultUseCase

class ValidateName {
    fun execute(name: String): ResultUseCase {
        if (name.isBlank())
            return ResultUseCase.Empty
        if (name.length < 6)
            return ResultUseCase.TooShort

        return ResultUseCase.OK
    }
}