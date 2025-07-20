# 💊 MAJK – Smart medicine distribution system

**MAJK** is a cross-platform mobile application built using **Kotlin Multiplatform** and designed as part of my **Bachelor’s degree in Computer Science at Poznań University of Technology**.  
The app serves as the central control unit for an intelligent medication dispenser, enabling users to remotely manage medication schedules, container contents, user permissions, and authorization settings – all in real time.

This project was the culmination of an interdisciplinary effort combining embedded systems, cloud integration, user management, and mobile development. MAJK empowers families and caregivers to take control of complex medication routines through a unified, intelligent, and secure system.

> 🧪 Developed as a capstone project, MAJK demonstrates practical use of Kotlin Multiplatform, MVI architecture, PostgreSQL (via Supabase), and real-world healthtech UX principles.

<p>
  <img width="200" alt="app_start_page" src="https://github.com/user-attachments/assets/7460be71-6165-4a9b-8224-c45b9f41befa" />
  <img width="200" alt="app_home" src="https://github.com/user-attachments/assets/ec12c86e-39ad-4704-bd29-ecefc52a41b9" />
  <img width="200" alt="app_schedule_details" src="https://github.com/user-attachments/assets/f5be4da9-3a0a-45a6-99e1-5b420248c1df" />
  <img width="200" alt="app_drawer_admin" src="https://github.com/user-attachments/assets/f9fa0f50-d32e-4609-b1c6-be756851bce2" />
</p>

<p>
  <img heigth = "200" width="100" alt="app_register_device_screen" src="https://github.com/user-attachments/assets/c296a9f6-35fe-48f4-b5de-af8f5d1ed020" />
  <img heigth = "200" width="100" alt="app_containers_main" src="https://github.com/user-attachments/assets/445f3fc3-e8fb-448b-bf16-fa9b2c158b3d" />
  <img heigth = "200" width="100" alt="app_containers_settings" src="https://github.com/user-attachments/assets/83b27a6a-ab3b-423d-8b94-0b758805e287" />
  <img heigth = "200" width="100" alt="app_schedule_list_screen" src="https://github.com/user-attachments/assets/578612a0-0b09-4c43-ab59-3e12fb42d59f" />
  <img heigth = "200" width="100" alt="app_schedule_main_screen" src="https://github.com/user-attachments/assets/b1e0686a-6513-4bc9-83e5-bfbe112a3035" />
  <img heigth = "200" width="100" alt="app_medkit_main" src="https://github.com/user-attachments/assets/bf299981-b27c-4d1c-bc4b-158622f5e590" />
  <img heigth = "200" width="100" alt="app_edit_user" src="https://github.com/user-attachments/assets/984fa237-384e-49d6-a88d-a9b15c96734a" />
</p>

---

## 🔧 Core Features

### 📅 Medication Schedule
- View and manage an interactive calendar of upcoming doses
- Add or edit medication intake events for yourself or others

### 📜 Dose History
- Access a detailed log of every dispensed dose, including the timestamp

### 🧰 Virtual Family Medicine Cabinet
- Add medications from the Polish national registry (`rejestrymedyczne.ezdrowie.gov.pl`)
- Access official leaflets and assign drugs to schedules and containers

### 📦 Container Management
- Monitor the contents of physical containers
- Reassign medications to specific containers and update status when restocked

### 👪 User & Family Management
- Manage a group of users (family or caregivers)
- Assign roles, configure access levels, and coordinate caregiving responsibilities

### 👤 Offline Profiles
- Create profiles for users without smartphones (e.g., children or elderly)
- Track their schedules and doses through admin supervision

### 🛡️ Dose Authorization
- Choose between NFC card emulation or RFID card authorization

---

## 👥 User Roles

- **Family Administrator**  
  Registers the device, manages containers, users, schedules, and settings. Has full access to all features and can create restricted profiles.

- **User**  
  Registers via family code. Manages their own schedule and medicine cabinet.

- **Restricted Profile**  
  Created by the admin for individuals without smartphones. Controlled and monitored remotely.

---

## 🧠 Architecture & Tech Stack

- **Architecture**: MVI (Model–View–Intent)
- **Multiplatform**: Kotlin Multiplatform
- **Backend**: Supabase (PostgreSQL + Auth)
- **Communication**: RESTful integration with intelligent medicine dispenser
- **Other Tools**: Compose Multiplatform, Koin, Coroutines, NFC HCE (Android)

## 🚀 Why MAJK?

MAJK is not just an academic project — it's a **prototype for a real-world health assistant**. It was developed with the intention to:

- Improve medical adherence  
- Simplify remote caregiving  
- Provide safety and transparency  
- Help families coordinate complex routines

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

