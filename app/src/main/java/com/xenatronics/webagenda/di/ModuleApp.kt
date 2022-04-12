package com.xenatronics.webagenda.di

import com.xenatronics.webagenda.data.repository.RepositoryContact
import com.xenatronics.webagenda.data.repository.RepositoryLogin
import com.xenatronics.webagenda.data.repository.RepositoryRdv
import com.xenatronics.webagenda.domain.usecase.Contact.*
import com.xenatronics.webagenda.domain.usecase.Login.Login
import com.xenatronics.webagenda.domain.usecase.Login.Logout
import com.xenatronics.webagenda.domain.usecase.Login.Register
import com.xenatronics.webagenda.domain.usecase.Login.UseCaseLogin
import com.xenatronics.webagenda.domain.usecase.Rdv.*
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
        register = Register(repository)
    )

    @Provides
    fun provideUseCasesRdv(repository: RepositoryRdv) = UseCaseRdv(
        getAllRdv = GetAllRdv(repository),
        getRdv = GetRdv(repository),
        addRdv = AddRdv(repository),
        deleteRdv = DeleteRdv(repository),
        cleanRdv = CleanRdv(repository),
        updateRdv = UpdateRdv(repository),
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