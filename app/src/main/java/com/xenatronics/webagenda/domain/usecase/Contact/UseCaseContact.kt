package com.xenatronics.webagenda.domain.usecase.Contact

import com.xenatronics.webagenda.domain.usecase.Contact.*

class UseCaseContact(
    val addContact: AddContact,
    val updateContact: UpdateContact,
    val getAllContact: GetAllContact,
    val getContact: GetContact,
    val cleanContact: CleanContact,
    val deleteContact: DeleteContact
)

