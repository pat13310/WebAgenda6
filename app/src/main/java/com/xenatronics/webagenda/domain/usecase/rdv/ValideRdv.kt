package com.xenatronics.webagenda.domain.usecase.rdv

import com.xenatronics.webagenda.domain.model.Rdv

class ValideRdv() {
    fun execute(rdv: Rdv): ResultRdv {
        if (rdv.nom.isBlank())
            return ResultRdv.EmptyName
        if (rdv.nom.length<3)
            return ResultRdv.BadLength

        return ResultRdv.ValideRdv
    }

    sealed class ResultRdv {
        object EmptyName : ResultRdv()
        object ValideRdv : ResultRdv()
        object BadLength : ResultRdv()

    }
}