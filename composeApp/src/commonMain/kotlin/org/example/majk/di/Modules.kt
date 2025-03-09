package org.example.majk.di

import org.example.majk.majk.data.repository.AuthRepositoryImpl
import org.example.majk.majk.domain.AuthRepository
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

    viewModelOf(::MajkSignInViewModel)
    viewModelOf(::MajkSignUpViewModel)
    viewModelOf(::MajkRegisterDeviceViewModel)
}