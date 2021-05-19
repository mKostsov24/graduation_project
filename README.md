# Дипломный проект «Java-разработчик c нуля»
Бэкенд блогового движка

## Требования к окружению и компонентам дипломного проекта
| Версия JDK | 11  |
|---|----|
| **Система контроля версий** | **git** |
| **Версия Spring Boot** | **2.2.4** |
| **База данных** | **PostgreSQL 11**|
| **Сборщик проекта** | **Maven**|
| **Миграция** | **Liqubase**|


# Реализации дипломного проекта

1. Создано Spring-приложение:
   1.  С помощью Maven подключены необходимые зависимости.
   2.  Создан класс и метод ```Main```, в котором заупскается Spring-приложение: ```SpringApplication.run(Main.class, args);```
   3.  Создан файл конфигурации ```application.yml```
   4.  В пакете ```controller``` созданы классы:
      -  **```DefaultController```** - для обычных запросов не через API (главная страница - /, в частности)
      -  **```ApiPostController```** - обрабатывает все запросы ```/api/post/*```
      -  **```ApiAuthController```** - обрабатывает все запросы ```/api/auth/*```
      -  **```ApiGeneralController```** - для прочих запросов к API.
2. К приложению подключен frontend, размещен в подпапках папки ```resources```: ```static```, ```templates```.
3. При входе на главную страницу открывался шаблон ```index.html``` .
4. Создана структура базы данных с помощью сущностей Hibernate в пакете ```model```. Структура базы данных описана в файле [DB_schema](DB_schema.md).
8. Реализованы методы API для получения информации о блоге (```/api/init```) и для получения постов (```/api/post```), и все остальные методы API в соответствии с документацией в файле [API](API.md).

### Ссылки на материалы
 - [описание API приложения](API.md)
 - [структура базы данных приложения](DB_schema.md)
