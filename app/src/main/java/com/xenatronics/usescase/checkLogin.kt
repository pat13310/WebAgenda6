package com.xenatronics.usescase

import com.xenatronics.webagenda.data.User
import com.xenatronics.webagenda.util.MessageLogin

fun checkLogin(user: User): MessageLogin {
    var result = MessageLogin.OK
    result = checkLoginFieldsEmpty(user)
    result = checkMail(user)
    return result
}

fun checkRegister(user: User): MessageLogin {
    var result = MessageLogin.OK
    result = checkRegisterFieldsEmpty(user)
    result = checkMail(user)
    return result
}

fun checkRegisterFieldsEmpty(user: User): MessageLogin {
    if (user.mail.isEmpty())
        return MessageLogin.NOM_EMPTY
    if (user.password.isEmpty())
        return MessageLogin.PASS_EMPTY
    if (user.name.isEmpty())
        return MessageLogin.NOM_EMPTY
    return MessageLogin.OK
}

private fun checkLoginFieldsEmpty(user: User): MessageLogin {
    if (user.mail.isEmpty())
        return MessageLogin.NOM_EMPTY
    if (user.password.isEmpty())
        return MessageLogin.PASS_EMPTY
    return MessageLogin.OK
}

private fun checkMail(user: User): MessageLogin {
    if (!user.mail.contains("."))
        return MessageLogin.MAIL_INCORRECT
    if (!user.mail.contains("@"))
        return MessageLogin.MAIL_INCORRECT
    return MessageLogin.OK
}
