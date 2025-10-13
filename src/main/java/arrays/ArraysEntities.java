package arrays;

import com.codeborne.selenide.SelenideElement;
import static locators.Locators.blocksTitle;
import static web.MainPage.*;

public class ArraysEntities {

    // массив с блоками сущности "Условия реализации"
    public static SelenideElement[] tabsImplTerms = {
            blocksTitle("Рассрочка"),
            blocksTitle("Ипотека"),
            blocksTitle("Льгота"),
            blocksTitle("Преимущество"),
            blocksTitle("Бронирование")};

    // массив сущностей имеющих вкладки
    public static String[] entitiesWithTabs = {"Заселение", "CSI Ипотека", "Импорт ОН", "Календарь", "Уведомления"};

    // массив селенид элементов в сущности "Сервисы"
    public static SelenideElement[] serviceEntities = {TRADE_IN, SBER_CITY, SCORING, INFOMETRICS};

    // массив с блоками сущности "Заселение"
    public static String[] tabsSettlements = {"Заселение", "Шахматка", "График дежурств", "Календарь"};

    // массив с блоками сущности "Инфометрика"
    public static String[] tabsInfometrics = {"CSI Ипотека", "CSI ОП", "CSI КЦ", "NPS ДДУ", "NPS ручной опрос"};

    // массив с блоками сущности "Импорт ОН""
    public static String[] tabsImport = {"Импорт ОН", "Отложенный импорт", "Планировки", "Планировки этажей", "История изменений"};

    // массив с блоками сущности "Календарь"
    public static String[] tabsCalendar = {"Календарь", "График дежурств", "Занятость переговорных"};

    // массив с блоками сущности "Уведомления"
    public static String[] tabsNotifications = {"Уведомления", "Интеграционные уведомления"};

    // массив кнопок в созданном деле
    public static String[] meetingButton = {"Отменить", "Завершить", "Клиент в офисе", "Редактировать", "Комментарии", "Удалить"};
}
