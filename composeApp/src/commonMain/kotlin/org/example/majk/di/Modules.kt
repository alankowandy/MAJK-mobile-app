package org.example.majk.di

import org.example.majk.majk.data.repository.AuthRepositoryImpl
import org.example.majk.majk.domain.repository.AuthRepository
import org.example.majk.core.presentation.SharedViewModel
import org.example.majk.majk.data.repository.AppRepositoryImpl
import org.example.majk.majk.domain.repository.AppRepository
import org.example.majk.majk.presentation.majk_login.majk_register_device.MajkRegisterDeviceViewModel
import org.example.majk.majk.presentation.majk_login.majk_signin.MajkSignInViewModel
import org.example.majk.majk.presentation.majk_login.majk_signup.MajkSignUpViewModel
import org.example.majk.majk.presentation.majk_main.majk_add_profile.AddProfileViewModel
import org.example.majk.majk.presentation.majk_main.majk_admin_auth.AdminAuthViewModel
import org.example.majk.majk.presentation.majk_main.majk_containers_state.main_screen.ContainerStateViewModel
import org.example.majk.majk.presentation.majk_main.majk_containers_state.settings_screen.ContainerSettingsViewModel
import org.example.majk.majk.presentation.majk_main.majk_history.HistoryViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.main_screen.ManageFamilyViewModel
import org.example.majk.majk.presentation.majk_main.majk_manage_family.settings_screen.SettingsViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.edit_screen.MyMedkitEditViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_medkit.main_screen.MyMedicamentViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.SelectedMedicineViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.add_medication_screen.AddScheduleViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.main_screen.ScheduleViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_details_screen.DetailsViewModel
import org.example.majk.majk.presentation.majk_main.majk_my_schedule.schedule_medicine_list.ScheduledMedicineListViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::AppRepositoryImpl).bind<AppRepository>()
    singleOf(::SharedViewModel).bind<SharedViewModel>()

    viewModelOf(::MajkSignInViewModel)
    viewModelOf(::MajkSignUpViewModel)
    viewModelOf(::MajkRegisterDeviceViewModel)
    viewModelOf(::ManageFamilyViewModel)
    viewModelOf(::AddProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AdminAuthViewModel)
    viewModelOf(::MyMedicamentViewModel)
    viewModelOf(::ScheduleViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::ContainerStateViewModel)
    viewModelOf(::ContainerSettingsViewModel)
    viewModelOf(::MyMedkitEditViewModel)
    viewModelOf(::ScheduledMedicineListViewModel)
    viewModelOf(::AddScheduleViewModel)
    viewModelOf(::SelectedMedicineViewModel)
    viewModelOf(::HistoryViewModel)
}