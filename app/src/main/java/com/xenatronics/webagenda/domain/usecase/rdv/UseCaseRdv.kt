package com.xenatronics.webagenda.domain.usecase.rdv

data class UseCaseRdv(
    val addRdv: AddRdv,
    val updateRdv: UpdateRdv,
    val getAllRdv: GetAllRdv,
    val getRdv: GetRdv,
    val cleanRdv: CleanRdv,
    val deleteRdv: DeleteRdv
)
