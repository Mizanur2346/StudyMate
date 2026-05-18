# StudyMate 
### Student Revision & Task Manager Android Application

A fully functional Android application developed.

---

##  About the App

StudyMate is a student productivity application designed to help university and college students manage their academic workload effectively. The app combines assignment tracking, flashcard-based revision, and daily motivational quotes into one clean and intuitive platform.

---

##  Features

-  **Home Dashboard** — Daily motivational quote fetched from ZenQuotes API and upcoming assignments overview
-  **Assignment Tracker** — Add, complete, and delete assignments with title, subject, due date and priority level
-  **Flashcard Manager** — Create and study flashcard decks with Show/Hide Answer card-flip mode
-  **Settings** — Light and dark mode toggle

---

##  Tech Stack

| Technology | Purpose |
|-----------|---------|
| Kotlin | Primary programming language |
| Android Studio | Development environment |
| MVVM Architecture | Software design pattern |
| Room Database | Local persistent storage |
| Retrofit | Network communication |
| ZenQuotes API | Daily motivational quotes |
| Navigation Component | Multi-screen navigation |
| Material Design 3 | UI design guidelines |
| Kotlin Coroutines | Asynchronous programming |
| StateFlow | Reactive UI updates |

---


---

## Project Structure

com.example.studymate
├── data
│   ├── dao          — Database Access Objects
│   ├── database     — Room Database setup
│   └── model        — Entity data classes
├── network          — Retrofit API service
├── ui
│   ├── assignments  — Assignment screen & adapter
│   ├── flashcards   — Flashcard screen & adapter
│   ├── home         — Home dashboard
│   └── settings     — Settings screen
├── viewmodel        — ViewModels for each feature
└── MainActivity     — Navigation host


---

##  How to Run

1. Clone the repository:

git clone https://github.com/Mizanur2346/StudyMate.git
```
2. Open the project in **Android Studio**
3. Wait for Gradle to sync
4. Run the app on an emulator or physical device

---

## Requirements

- Android Studio Hedgehog or later
- Android SDK API 26+
- Internet connection (for daily quote feature)
- Kotlin 1.9.22

---



