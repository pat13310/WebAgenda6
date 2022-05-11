package com.xenatronics.webagenda.di

import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.data.repository.RepositoryLogin
import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.usecase.contact.*
import com.xenatronics.webagenda.domain.usecase.login.*
import com.xenatronics.webagenda.domain.usecase.rdv.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    @Provides
    fun provideUseCaseLogin(repository: RepositoryLogin) = UseCaseLogin(
        login = Login(repository),
        logout = Logout(repository),
        register = Register(repository),
        validateMail = ValidateMail(),
        validateName = ValidateName(),
        validatePassword = ValidatePassword()
    )

    @Provides
    fun provideUseCasesRdv(repository: RepositoryRdv) = UseCaseRdv(
        getAllRdv = GetAllRdv(repository),
        getRdv = GetRdv(repository),
        addRdv = AddRdv(repository),
        deleteRdv = DeleteRdv(repository),
        cleanRdv = CleanRdv(repository),
        updateRdv = UpdateRdv(repository),
        validateRdv = ValidateRdv(),

    )

    @Provides
    fun provideUseCasesContact(repository: RepositoryContact) = UseCaseContact(
        addContact = AddContact(repository),
        deleteContact = DeleteContact(repository),
        updateContact = UpdateContact(repository),
        getAllContact = GetAllContact(repository),
        getContact = GetContact(repository),
        cleanContact = CleanContact(repository)
    )
}