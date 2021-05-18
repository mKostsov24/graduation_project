package org.example.utils;

public class ErrorsList {
    public static final String STRING_POST_NOT_FOUND = "Пост с идентификатором '%d' не найден!";
    public static final String STRING_SETTING_NOT_FOUND = "В базе нет таких настроек";
    public static final String STRING_FIELD_CANT_BE_BLANK = "Поле не может быть пустым";
    public static final String STRING_AUTH_SHORT_PASSWORD = "Слишком короткий пароль";
//    -----validateUser------
    public static final String STRING_AUTH_EMAIL_ALREADY_REGISTERED = "Почта уже зарегистрирована";
    public static final int INT_AUTH_MIN_PASSWORD_LENGTH = 6 ;
    public static final int INT_AUTH_MAX_PASSWORD_LENGTH = 40 ;
    public static final String STRING_AUTH_INVALID_PASSWORD_LENGTH = String.format("Пароль короче " +
            "%d символов или длиннее %s", INT_AUTH_MIN_PASSWORD_LENGTH, INT_AUTH_MAX_PASSWORD_LENGTH);
    public static final String STRING_AUTH_INVALID_CAPTCHA = "Неверный код с captcha";
//    -----validatePost------
    public static final String STRING_POST_TITLE_EMPTY = "Заголовок пуст";
    public static final String STRING_POST_TITLE_SHORT = "Заголовок короткий";
    public static final String STRING_POST_TEXT_EMPTY = "Текст пуст";
    public static final String STRING_POST_TEXT_SHORT = "Текст короткий";
//    -----validateComment------
    public static final String STRING_COMMENT_TEXT_SHORT = "Текст комментария не задан или слишком короткий";
    //    -----validatePasswordRestore------
    public static final String STRING_CODE_IS_OLD = "Ссылка для восстановления пароля устарела. " +
            "<a href=\"/login/restore-password\">запросить ссылку снова</a>";
    public static final String STRING_PASSWORD_SHORT =  "Пароль короче 6-ти символов";
    public static final String STRING_CAPTCHA_INVALID =  "Код с картинки введён неверно";
    public static final String STRING_AUTH_MAIL_SUBJECT = "Ссылка на восстановление пароля";
    public static final String STRING_AUTH_SERVER_URL = "http://%s";
    public static final String STRING_AUTH_MAIL_MESSAGE = "Для восстановления пароля, " +
            "пройдите по этой ссылке: %s/login/change-password/%s";
    //    -----validateProfileUpdate------
    public static final String STRING_AUTH_WRONG_NAME = "Имя указано неверно.";

}
