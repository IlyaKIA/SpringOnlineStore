# Online store
Based on Spring Framework: 
- The Spring boot project;
- page design based on Bootstrap;
- MVC data separation scheme with Thymeleaf HTML template engine;
- PostgreSQL database deployment in a Docker container with database-migration tool Flyway;
- used Spring Data and Hibernate for interaction with database;
- included REST controller with DTO conversion, for interaction with other services;
- URL and views protection and authorization implemented on Spring Security.

## Примечание
> Логины: user1 – Администратор, user2 – пользователь.  
> Пароль: 123  

При отправке первого добавления товара/пользователя/категории/отзыва необходимо несколько раз обновить страницу для синхронизации автоматически генерируемых ID с БД. В базу по умолчанию добавлено 10 товаров, 6 категорий, 2 отзыва и 2 пользователя.  

## Обзор
- Нажатие на изображение товара открывает описание (все пользователи). Зарегистрированный пользователь может добавлять комментарии и ставить оценки.
- Работают фильтры по цене, категории и поиск.

У пользователя:
- Появляется значок пользователя в правом углу шапки. Нажатие открывает форму редактирования данных пользователя.
- Продукты можно добавлять и удалять из корзины.

У администратора:
- В раскрывающемся меню шапки открываются пункты добавления товара и включения/отключения пользователей.
- У товаров появляются кнопки редактирования и удаления.

