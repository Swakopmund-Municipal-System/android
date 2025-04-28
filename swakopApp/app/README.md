# Swakopmund App

A mobile application for residents and tourists of Swakopmund, Namibia.

## Architecture: MVVM

This project uses the Model-View-ViewModel (MVVM) pattern to keep code organized and maintainable.

### What is MVVM?

MVVM separates our app into three main parts:

1. **Model**: Handles data and business rules
2. **View**: Shows information to users and captures their interactions
3. **ViewModel**: Connects the Model and View, preparing data for display

### Project Structure

```
com.example.swakopmundapp
├── data/            # Where all data handling happens (Model)
│   ├── local/       # Local storage (database, preferences)
│   ├── remote/      # Network data (API calls)
│   └── model/       # Data classes
├── repository/      # Connects data sources together (Model)
├── ui/              # All screens and UI components (View)
│   ├── auth/        # Login/signup screens
│   ├── home/        # Home screen
│   ├── resident/    # Resident services screens
│   ├── tourism/     # Tourism screens
│   ├── shared/      # Reusable UI components
│   └── navigation/  # Screen navigation
├── utils/           # Helper functions
├── viewmodel/       # ViewModels for each screen
└── MainActivity.kt  # App entry point
```

### What Goes in Each Part?

#### Model Layer (`data/` and `repository/`)

- **Data Classes**: Information structures like `User`, `MunicipalService`
- **Local Data**: Database operations, saved preferences
- **Remote Data**: API calls and responses
- **Repositories**: Coordinate where data comes from (local or remote)

#### ViewModel Layer (`viewmodel/`)

- Hold data for the UI
- Process user actions
- Fetch data from repositories
- Survive screen rotations

#### View Layer (`ui/`)

- Screen layouts and components
- Display data from ViewModels
- Pass user actions to ViewModels
- No business logic here

#### Utils (`utils/`)

- Helpful functions used throughout the app
- Constants and extensions

### How Data Flows

1. View shows data from ViewModel
2. User interacts with View
3. View tells ViewModel about the interaction
4. ViewModel gets/updates data through Repository
5. Repository works with local or remote data sources
6. Updated data flows back to View through ViewModel

## Technologies

- Kotlin
- Jetpack Compose
- Navigation Compose
- Material 3 Design