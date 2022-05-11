package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.domain.model.Rdv
import com.xenatronics.webagenda.domain.usecase.ResultUseCase

class ValidateRdv() {
    fun execute(rdv: Rdv): ResultUseCase {
        if (rdv.nom.isBlank())
            return ResultUseCase.Empty
        if (!rdv.nom.any { it.isLetter() })
            return ResultUseCase.NoLetters
        if (rdv.nom.length < 3)
            return ResultUseCase.TooShort

        return ResultUseCase.OK
    }
}