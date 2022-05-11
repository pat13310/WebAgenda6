package com.xenatronics.webagenda.domain.usecase

import com.xenatronics.webagenda.domain.model.Credentials
import com.xenatronics.webagenda.common.util.MessageLogin

fun checkLogin(user: Credentials): MessageLogin {
    var result = checkLoginFieldsEmpty(user)
    if (result!=MessageLogin.OK)
        return result

    return result
}

fun checkRegister(user: Credentials): MessageLogin {
    var result = checkRegisterFieldsEmpty(user)
    if (result!=MessageLogin.OK)
        return result

    result = checkMail(user)
    return result
}

fun checkRegisterFieldsEmpty(user: Credentials): MessageLogin {
    if (user.mail.isBlank())
        return MessageLogin.NOM_EMPTY
    if (user.password.isBlank())
        return MessageLogin.PASS_EMPTY
    if (user.name.isBlank())
        return MessageLogin.NOM_EMPTY
    return MessageLogin.OK
}

private fun checkLoginFieldsEmpty(user: Credentials): MessageLogin {
    if (user.name.isBlank())
        return MessageLogin.NOM_EMPTY
    if (user.password.isBlank())
        return MessageLogin.PASS_EMPTY
    return MessageLogin.OK
}

private fun checkMail(user: Credentials): MessageLogin {
    if (!user.mail.contains("."))
        return MessageLogin.MAIL_INCORRECT
    if (!user.mail.contains("@"))
        return MessageLogin.MAIL_INCORRECT
    return MessageLogin.OK
}
