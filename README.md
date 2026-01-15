# Мобильное приложение "CinemaTicketBooking"

Это Android-приложение для бронирования билетов в кино. Проект построен с использованием современной многоуровневой архитектуры (Clean Architecture) и паттерна MVVM, что обеспечивает его масштабируемость, тестируемость и удобство в поддержке.

## Основные возможности

*   **Аутентификация:** Регистрация и вход пользователей с использованием токенов для безопасного доступа.
*   **Главный экран:** Отображает анимированный слайдер с баннерами, списки популярных и скоро выходящих фильмов.
*   **Каталог и Поиск:** Возможность просмотра всех фильмов и поиска по названию.
*   **Детализация фильма:** Подробный экран с описанием фильма, рейтингом IMDb, списком актеров и жанров.
*   **Процесс бронирования:** Интерактивный выбор даты, времени сеанса и мест на схеме зала.
*   **Профиль пользователя:** Отображение информации о пользователе и доступ к его бронированиям.
*   **Система уведомлений:** Просмотр уведомлений, связанных с бронированиями и другими событиями, с индикатором непрочитанных сообщений.

## Технологический стек

*   **Язык:** Kotlin
*   **Архитектура:** MVVM + Clean Architecture
*   **Асинхронность:** Kotlin Coroutines & Flow
*   **Внедрение зависимостей (DI):** ViewModelFactory (ручное внедрение)
*   **Навигация:** Jetpack Navigation Component
*   **Работа с сетью:** Retrofit & OkHttp
*   **Жизненный цикл компонентов:** Jetpack ViewModel & Lifecycle
*   **Отображение UI:** ViewBinding, Android Views (XML), RecyclerView, ViewPager2
*   **Загрузка изображений:** Glide
*   **Сериализация:** Gson

## Архитектура

Проект строго разделен на три независимых слоя, что является ключевым принципом чистой архитектуры.

### 1. Presentation Layer (Слой представления)

Отвечает за все, что связано с пользовательским интерфейсом.

*   **UI (Fragments & Activities):** Экраны для взаимодействия с пользователем.
*   **ViewModel:** Управляет состоянием UI, обрабатывает действия пользователя и обращается к `UseCases` для получения данных или выполнения бизнес-логики.

*Пример: `BookingViewModel.kt`*
```kotlin
class BookingViewModel(
    private val createBookingUseCase: CreateBookingUseCase,
    private val getUserBookingsUseCase: GetUserBookingsUseCase,
    private val cancelBookingUseCase: CancelBookingUseCase,
    private val getSeatStatusesUseCase: GetSeatStatusesUseCase
) : ViewModel() {

    private val _seats = MutableStateFlow<List<Seat>>(emptyList())
    val seats: StateFlow<List<Seat>> = _seats

    private val _bookingState = MutableStateFlow<BookingState>(BookingState.Idle)
    val bookingState: StateFlow<BookingState> = _bookingState
    
    // ...

    fun onTimeSelected(time: String, movieId: Long) {
        this.selectedTime = time
        loadSeats(movieId, this.selectedDate, time)
    }

    fun loadSeats(movieId: Long, date: LocalDate, time: String) {
        viewModelScope.launch {
            try {
                val localDateTime = LocalDateTime.of(date, LocalTime.parse(time))
                val showTime = localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                // Обращение к UseCase для получения данных
                _seats.value = getSeatStatusesUseCase(movieId, showTime)
                updateSelectedSeatInfo()
            } catch (e: Exception) {
                _seats.value = emptyList()
            }
        }
    }
}
```

### 2. Domain Layer (Слой бизнес-логики)

Содержит основную бизнес-логику и правила приложения. Этот слой полностью независим от Android SDK.

*   **Use Cases:** Инкапсулируют отдельные бизнес-операции (например, отмена бронирования).
*   **Domain Models:** Простые классы данных, описывающие ключевые сущности (например, `Movie`, `Booking`).
*   **Repository Interfaces:** Абстракции, определяющие контракты для получения данных извне.

*Пример: `CancelBookingUseCase.kt`*
```kotlin
class CancelBookingUseCase(private val repository: BookingRepository) {
    // Один публичный метод, который выполняет конкретное действие
    suspend operator fun invoke(bookingId: Long) = repository.cancelBooking(bookingId)
}
```

### 3. Data Layer (Слой данных)

Отвечает за получение данных из различных источников (в данном случае — из сети).

*   **API Service (Retrofit):** Интерфейсы, описывающие эндпоинты REST API.
*   **Repositories Implementation:** Реализация интерфейсов репозиториев из `Domain` слоя. Здесь происходит вызов API и преобразование DTO в доменные модели.
*   **Data Models (DTO):** Классы данных, соответствующие структуре JSON-ответов от сервера.

*Пример: `MovieRepositoryImpl.kt`*
```kotlin
class MovieRepositoryImpl(private val api: MovieApi) : MovieRepository {
    // Реализация метода из интерфейса в Domain слое
    override suspend fun getAllMovies() = api.getAllMovies().map { 
        // Преобразование DTO (MovieDto) в доменную модель (Movie)
        it.toDomain() 
    }

    override suspend fun getMovieById(id: Long) = api.getMovieById(id).toDomain()

    override suspend fun getTopMovies() = api.getTopMovies().map { it.toDomain() }
}
```

## Структура проекта

Файлы сгруппированы по слоям архитектуры для обеспечения четкого разделения ответственности.

```text
C:.
├───common
│       AuthInterceptor.kt
│       Extension.kt
│       ...
│
├───data
│   ├───api
│   │       AuthApi.kt
│   │       ...
│   ├───model
│   │       AuthRequestDto.kt
│   │       MovieDto.kt
│   │       ...
│   └───repository
│           AuthRepositoryImpl.kt
│           MovieRepositoryImpl.kt
│           ...
│
├───domain
│   ├───model
│   │       Booking.kt
│   │       Movie.kt
│   │       User.kt
│   │       ...
│   ├───repository
│   │       AuthRepository.kt
│   │       MovieRepository.kt
│   │       ...
│   └───usecase
│           CancelBookingUseCase.kt
│           GetAllMoviesUseCase.kt
│           LoginUseCase.kt
│           ...
│
└───presentation
    ├───adapters
    │       BannerSliderAdapter.kt
    │       MovieAdapter.kt
    │       SeatAdapter.kt
    │       ...
    ├───factories
    │       ViewModelFactory.kt
    │
    ├───fragments
    │       AuthFragment.kt
    │       ExplorerFragment.kt
    │       FragmentFilmDetails.kt
    │       ...
    ├───ui
    │       MainActivity.kt
    │
    └───viewmodel
            AuthViewModel.kt
            BookingViewModel.kt
            MovieViewModel.kt
            ...
```

## Демонстрация

[Ссылка на видео-демонстрацию работы приложения](https://drive.google.com/drive/folders/1KPLCPgothKIdBFDxAlr2KuJCQP43HMSL?usp=sharing)
