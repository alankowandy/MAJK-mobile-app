package org.example.majk.di

import org.example.majk.majk.data.repository.AuthRepositoryImpl
import org.example.majk.majk.domain.AuthRepository
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.repository.AppRepositoryImpl
import org.example.majk.majk.domain.AppRepository
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::AppRepositoryImpl).bind<AppRepository>()

    viewModelOf(::SharedViewModel)
    viewModelOf(::MajkSignInViewModel)
    viewModelOf(::MajkSignUpViewModel)
    viewModelOf(::MajkRegisterDeviceViewModel)
    viewModelOf(::ManageFamilyViewModel)
    viewModelOf(::AddProfileViewModel)
    viewModelOf(::SettingsViewModel)
}