package com.xenatronics.webagenda.common.util

enum class Action {
    ADD,
    UPDATE,
    DELETE,
    DELETE_ALL,
    UNDO,
    NO_ACTION
}


enum class MessageLogin{
    NOM_EMPTY,
    PASS_EMPTY,
    PASS_INCORRECT,
    MAIL_EMPTY,
    MAIL_INCORRECT,
    OK
}

enum class StatusLogin {
    Ok,
    Failure,
    None
}