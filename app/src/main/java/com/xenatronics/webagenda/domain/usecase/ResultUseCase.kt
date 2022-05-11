package com.xenatronics.webagenda.domain.usecase

sealed class ResultUseCase{
    object Empty:ResultUseCase()
    object TooShort:ResultUseCase()
    object OK:ResultUseCase()
    object PatternInvalid:ResultUseCase()
    object NoLetters:ResultUseCase()
    object NoDigits:ResultUseCase()
    object InternetFailure:ResultUseCase()
}


