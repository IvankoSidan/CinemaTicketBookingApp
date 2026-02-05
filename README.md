# CinemaTicketBooking — Android App

Многомодульное Android-приложение для бронирования билетов в кино.
Проект демонстрирует навыки уровня Android Middle/Senior: Clean Architecture, MVVM, модульная структура, Dependency Injection и масштабируемость.

---

## О проекте

**CinemaTicketBooking** — демонстрационный проект, построенный по принципам, используемым в production-приложениях. Основной акцент сделан на архитектуру и качество кода, что позволяет:

* поддерживать независимые feature-модули
* иметь чёткие границы ответственности между слоями
* соблюдать принципы SOLID и Dependency Inversion
* легко расширять функциональность и масштабировать команду

Проект может служить примером для коммерческих Android-приложений.

---

## Архитектура проекта

```text
CinemaTicketBooking/
├── app/                    # Точка входа приложения
├── core/                   # Общая инфраструктура и базовые абстракции
├── network/                # Сетевой слой
├── navigation/             # Навигация между модулями
└── features-*/             # Функциональные модули
    ├── features-auth/      # Аутентификация
    ├── features-home/      # Главный экран и каталог фильмов
    ├── features-booking/   # Бронирование билетов
    └── features-profile/   # Профиль пользователя
```

Принципы:

* Clean Architecture (Data / Domain / Presentation)
* MVVM на уровне Presentation
* Отсутствие прямых зависимостей между feature-модулями
* Dependency Inversion для всех взаимодействий между модулями

---

## Основные возможности

* Независимые feature-модули
* Аутентификация с JWT
* Каталог фильмов: популярные и предстоящие релизы
* Экран деталей фильма (описание, рейтинг, актёрский состав)
* Бронирование: выбор даты, времени и мест
* Профиль пользователя
* Push-уведомления

---

## Технологический стек

| Технология         | Назначение                   |
| ------------------ | ---------------------------- |
| Kotlin             | Основной язык разработки     |
| Clean Architecture | Архитектурный подход         |
| MVVM               | Presentation pattern         |
| Dagger 2           | Dependency Injection         |
| Coroutines + Flow  | Асинхронное программирование |
| Jetpack Navigation | Навигация между экранами     |
| Retrofit + OkHttp  | Сетевые запросы              |
| ViewBinding        | Привязка представлений       |
| Glide              | Загрузка изображений         |

---

## Модули

### app

* Точка входа
* Инициализация Dagger компонентов
* Настройка навигации
* Объединение всех feature-модулей

### core

* BaseFragment / BaseViewModel
* Общие утилиты и расширения
* Ресурсы и стили
* DI-абстракции

### network

* Retrofit, сериализация, интерцепторы
* Общие сетевые зависимости

### navigation

* Контракты навигации
* AppNavigator
* Развязка переходов между модулями

### features-*

```text
features-auth/
├── data/                   # Data layer
├── domain/                 # Domain layer
├── presentation/           # UI и ViewModel
├── di/                     # DI модуля
└── build.gradle.kts
```

---

## Сборка и запуск

Требования:

* Android Studio Flamingo или выше
* JDK 17
* Android SDK 34

Шаги:

```bash
git clone <repository-url>
```

1. Открыть проект в Android Studio
2. Дождаться синхронизации Gradle
3. Запустить на эмуляторе или устройстве

### Конфигурация сервера

В файле `network/src/main/java/com/ivan/network/di/NetworkModule.kt` укажите базовый URL сервера:

```kotlin
.baseUrl("https://your-backend-url.com/api/")
```

---

## Взаимодействие между модулями

### Через публичные интерфейсы

```kotlin
interface AuthRepository {
    suspend fun login(email: String, password: String): AuthUser
}

interface TokenProvider {
    fun getToken(): String?
}
```

### Через DI

```kotlin
@Module
abstract class AuthModule {
    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}
```

---

## Тестирование

Каждый модуль поддерживает изолированное тестирование:

```text
features-auth/
├── src/test/            # Unit-тесты
├── src/androidTest/     # Инструментальные тесты
```

---

## Демонстрация работы

Видео демонстрации:

[Смотреть работу приложения](https://drive.google.com/file/d/1jsDsKLJhky8ZLvlLI9IN1QCm6OkasRbe/view?usp=drive_link)
