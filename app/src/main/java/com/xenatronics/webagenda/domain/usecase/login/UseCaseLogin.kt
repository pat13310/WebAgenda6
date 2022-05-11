package com.xenatronics.webagenda.domain.usecase.login

data class UseCaseLogin(
    val login:Login,
    val logout: Logout,
    val register: Register,
    val validateMail: ValidateMail,
    val validateName: ValidateName,
    val validatePassword: ValidatePassword,
)
