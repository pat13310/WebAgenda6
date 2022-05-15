package com.xenatronics.webagenda.common.events

import com.xenatronics.webagenda.common.util.Action
import com.xenatronics.webagenda.domain.usecase.ResultUseCase

sealed interface UIEvent{
    object PopBackStack : UIEvent
    object Update : UIEvent
    data class Navigate(val route: String) : UIEvent
    data class ShowSnackBar(val action: String, val message: String) : UIEvent
    data class ShowErrorMessage(val resultUseCase: ResultUseCase, val focus: String = "") : UIEvent
    data class ShowAlertDialog(val title: String, val message: String, val action: Action) : UIEvent
}