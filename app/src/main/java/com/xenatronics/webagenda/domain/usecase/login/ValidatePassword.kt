package com.xenatronics.webagenda.domain.usecase.login

import com.xenatronics.webagenda.domain.usecase.ResultUseCase

class ValidatePassword {
    fun execute(password: String): ResultUseCase {
        if (password.isBlank())
            return ResultUseCase.Empty
        if (password.length < 6)
            return ResultUseCase.TooShort
        if (!password.any { it.isDigit() })
            return ResultUseCase.NoDigits

        return ResultUseCase.OK
    }
}



