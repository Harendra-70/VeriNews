# VeriNews 📰

A modern Android news application that fetches and displays the latest news articles from various categories using the NewsAPI. Built with MVVM architecture, Retrofit, and Material Design 3.

## Features ✨

- **Multiple News Categories**: Browse news across 5 different categories
  - Home (General News)
  - Science
  - Sports
  - Health
  - Entertainment
- **Real-time Updates**: Pull-to-refresh functionality for latest news
- **In-App Browser**: Read full articles within the app using WebView
- **Material Design 3**: Modern UI with dynamic theming support
- **Offline Handling**: Graceful error handling with user-friendly messages
- **Smooth Navigation**: Bottom navigation with fragment management
- **Image Loading**: Efficient image loading with Glide

## Screenshots 📱

<img width="1080" height="2400" alt="Image" src="https://github.com/user-attachments/assets/2c05dda9-ff1a-4db0-ba40-abb9b72d2984" />

<img width="1080" height="2400" alt="Image" src="https://github.com/user-attachments/assets/9b20744f-45a5-4707-8315-fe9c9d0309e9" />

<img width="1080" height="2400" alt="Image" src="https://github.com/user-attachments/assets/26389bdc-bb82-46e0-85d8-9f0243e8f3f6" />

<img width="1080" height="2400" alt="Image" src="https://github.com/user-attachments/assets/4a0f6ac4-f85b-475b-a161-cd1656e96dff" />

<img width="1080" height="2400" alt="Image" src="https://github.com/user-attachments/assets/29c2b002-9639-4bfa-94df-eade0f5b938d" />

## Architecture 🏗️

This app follows the **MVVM (Model-View-ViewModel)** architecture pattern:

- **Model**: Data classes for news articles (`Model`, `NewsArticle`, `Source`)
- **View**: Activities and Fragments (UI layer)
- **ViewModel**: `NewsViewModel` manages UI-related data and business logic
- **Repository Pattern**: `ApiUtilities` and `ApiInterface` handle data operations

## Tech Stack 🛠️

### Core
- **Language**: Java
- **Minimum SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Architecture**: MVVM with LiveData

### Libraries & Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| [Retrofit](https://square.github.io/retrofit/) | 2.9.0 | REST API communication |
| [Gson](https://github.com/google/gson) | 2.9.0 | JSON parsing |
| [Glide](https://github.com/bumptech/glide) | 4.16.0 | Image loading and caching |
| [OkHttp Logging Interceptor](https://square.github.io/okhttp/) | 4.9.0 | Network request logging |
| [Material Components](https://material.io/develop/android) | 1.12.0 | Material Design 3 UI components |
| [SwipeRefreshLayout](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout) | 1.1.0 | Pull-to-refresh functionality |
| [Multidex](https://developer.android.com/studio/build/multidex) | 2.0.1 | Support for apps with 65K+ methods |

## Project Structure 📁

```
com.shivamsingh.verinews
├── adapter
│   └── Adapter.java                 # RecyclerView adapter for news items
├── model
│   ├── Model.java                   # News article data model
│   ├── NewsArticle.java             # API response wrapper
│   └── Source.java                  # News source data model
├── network
│   ├── ApiInterface.java            # Retrofit API endpoints
│   └── ApiUtilities.java            # Retrofit singleton instance
├── ui
│   ├── activity
│   │   ├── MainActivity.java        # Main screen with bottom navigation
│   │   ├── SplashActivity.java      # Splash screen
│   │   └── WebViewActivity.java     # In-app browser for articles
│   └── fragment
│       ├── HomeFragment.java        # General news
│       ├── ScienceFragment.java     # Science news
│       ├── SportsFragment.java      # Sports news
│       ├── HealthFragment.java      # Health news
│       └── EntertainmentFragment.java # Entertainment news
└── viewmodel
    └── NewsViewModel.java           # ViewModel for managing news data
```

## Getting Started 🚀

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17 or higher
- Android SDK with API 34
- NewsAPI Key (Get it from [newsapi.org](https://newsapi.org))

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Harendra-70/VeriNews.git
   cd verinews
   ```

2. **Get your NewsAPI Key**
   - Visit [https://newsapi.org](https://newsapi.org)
   - Sign up for a free account
   - Copy your API key

3. **Configure API Key**
   
   Create a `local.properties` file in the root directory (if it doesn't exist) and add:
   ```properties
   newsApiKey=YOUR_API_KEY_HERE
   ```
   
   ⚠️ **Important**: Never commit your `local.properties` file to version control. It's already included in `.gitignore`.

4. **Build and Run**
   - Open the project in Android Studio
   - Sync Gradle files
   - Run the app on an emulator or physical device

## API Configuration 🔧

This app uses the [NewsAPI](https://newsapi.org) to fetch news articles.

### Endpoints Used

- **Top Headlines**: `/v2/top-headlines`
  - Parameters: `country`, `category`, `pageSize`, `apiKey`

### API Key Security

The API key is stored securely in `local.properties` and accessed via BuildConfig:
```java
static final String API_KEY = BuildConfig.NEWS_API_KEY;
```

## Features in Detail 📋

### News Categories
- **Home**: Latest news from all categories (US)
- **Science**: Scientific discoveries and technology
- **Sports**: Sports news and updates
- **Health**: Health, wellness, and medical news
- **Entertainment**: Entertainment and celebrity news

### Pull-to-Refresh
Each category supports pull-to-refresh to fetch the latest articles.

### WebView Integration
- Opens full articles within the app
- JavaScript enabled for rich content
- Custom user agent for mobile compatibility
- Back navigation support within WebView
- Progress bar for loading indication

### Error Handling
- Network failure handling
- Empty response handling
- User-friendly error messages via Toast
- Loading states with progress indicators

## Key Components 🔑

### NewsViewModel
Manages news data and communicates with the API:
- `getNewsListObserver()`: LiveData for news articles
- `getLoadingState()`: LiveData for loading state
- `getToastMessage()`: LiveData for error messages
- `makeApicall()`: Fetches news from API

### Adapter
RecyclerView adapter that displays news items with:
- Title
- Description
- Author
- Published date
- Source name
- Article image (via Glide)

### Fragment Management
Uses hide/show pattern for better performance instead of replace.

## Build Configuration ⚙️

```gradle
android {
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.shivamsingh.verinews"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}
```

## Permissions 📜

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Contributing 🤝

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Future Enhancements 🔮

- [ ] Search functionality
- [ ] Bookmark/Save articles
- [ ] Share articles
- [ ] Dark mode toggle
- [ ] Multiple country support
- [ ] Pagination for articles
- [ ] Offline caching
- [ ] Article categories filter
- [ ] User preferences

## License 📄

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments 🙏

- [NewsAPI](https://newsapi.org) for providing the news data
- [Material Design](https://material.io) for design guidelines
- [Retrofit](https://square.github.io/retrofit/) for networking
- [Glide](https://github.com/bumptech/glide) for image loading

## Contact 📧

Harendra Singh – [bishtharendra90@gmail.com](mailto:bishtharendra90@gmail.com)

Project Link: [https://github.com/Harendra-70/VeriNews](https://github.com/Harendra-70/VeriNews)

---

**Made with ❤️ by Harendra Singh**
