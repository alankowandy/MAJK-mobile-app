package org.example.majk.di

import org.example.majk.majk.data.repository.AuthApiImpl
import org.example.majk.majk.domain.AuthApi
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    single {  }
    singleOf(::AuthApiImpl).bind<AuthApi>()

    viewModelOf(::MajkSignInViewModel)
}