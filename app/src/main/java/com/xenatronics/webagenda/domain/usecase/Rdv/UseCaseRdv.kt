package com.xenatronics.webagenda.domain.usecase.Rdv

data class UseCaseRdv(
    val addRdv: AddRdv,
    val updateRdv: UpdateRdv,
    val getAllRdv: GetAllRdv,
    val getRdv: GetRdv,
    val cleanRdv: CleanRdv,
    val deleteRdv: DeleteRdv
)
