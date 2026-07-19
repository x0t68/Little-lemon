Little Lemon Android App 🍋
Little Lemon is a modern Mediterranean restaurant application built for Android. This project demonstrates a complete development lifecycle, including a custom onboarding flow, remote data fetching, local persistence, and a dynamic user interface using the latest Android technologies.
🚀 Key Features
•
User Onboarding: A seamless registration flow that collects user details (First Name, Last Name, Email) and persists them locally using SharedPreferences.
•
Dynamic Menu Integration: Fetches real-time menu data from a remote API endpoint using Ktor.
•
Offline-First Support: Implements Room Database to cache menu items, ensuring the app remains functional even without an internet connection.
•
Smart Search & Filtering:
◦
Real-time search functionality by dish name or description.
◦
Category-based filtering (Starters, Mains, Desserts, Drinks) using a horizontal scrollable UI.
•
Profile Management: A dedicated profile screen for users to view their information and manage their session (Log Out).
•
Modern UI/UX: Built entirely with Jetpack Compose, featuring a responsive design, custom branding colors, and smooth transitions.
🛠 Tech Stack
•
UI Framework: Jetpack Compose (Declarative UI)
•
Networking: Ktor Client (Asynchronous HTTP client)
•
Local Persistence: Room Database (SQLite abstraction)
•
Serialization: Kotlinx Serialization (JSON parsing)
•
Image Loading: Glide (Compose Support)
•
Navigation: Jetpack Compose Navigation
•
Dependency Management: Gradle Version Catalog (libs.versions.toml)
📖 Project Architecture
•
MainActivity.kt: The entry point that initializes the database, network client, and handles the main application state.
•
MenuRepository.kt: Implements the Repository pattern to manage data synchronization between the remote API and the local Room database.
•
Home.kt: The core screen featuring the Hero section, search bar, and the dynamic menu list.
•
NavigationComposable.kt: Controls the navigation graph and handles the logic for the initial landing page based on login status.
•
Database.kt / Network.kt: Define the schemas and data models for local storage and network responses.
⚙️ Getting Started
Prerequisites
•
Android Studio Iguana or newer.
•
JDK 11 or higher.
•
Basic knowledge of Kotlin and Jetpack Compose.
Installation
1.
Clone the repository:
Shell Script
git clone https://github.com/YOUR_USERNAME/Little-lemon.git
2.
Open the project in Android Studio.
3.
Sync the project with Gradle files.
4.
Run the app on an Emulator or a physical device.
📸 UI Preview
[App Screenshot](ReadMeImg/photo_2026-07-19_20-02-49.jpg)
•
Onboarding: Simple and clean registration.
•
Home: Interactive menu with search and filters.
•
Profile: Clean layout for user details.
Developed as part of the Meta Android Developer Professional Certificate.# Little-lemon