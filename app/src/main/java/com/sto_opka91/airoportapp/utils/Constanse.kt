package com.sto_opka91.airoportapp.utils

import com.sto_opka91.airoportapp.models.local.PlaneInfo

val LIST_PLANE = listOf(
    PlaneInfo(1, "Москва", "DME", "Санкт-Петербург", "LED", "2ч 30мин", "1 октября", 150, RateFlight.FIRST_CLASS, StatusFlight.SHIPPED, "Y6-5001", "Бoeing 737", "12:00", "13 сентября ", "11:00", TransferCount.ONE, HandBagage.NO, "23-25", "A1", "N", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 3 часа", 16100),

    PlaneInfo(2, "Казань", "KZN", "Сочи", "AER", "2ч 10мин", "2 октября", 120, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5002", "Airbus A320", "15:00", "13 сентябрясреда", "13:30", TransferCount.TWO, HandBagage.YES, "26-28", "B2", "S", InfoStatus.INFO, "Начата посадка пассажиров с детьми и особыми потребностями", 16101),

    PlaneInfo(3, "Нижний Новгород", "GOJ", "Калуга", "KLF", "1ч 50мин", "3 октября", 80, RateFlight.BISNESS, StatusFlight.DETAINED, "Y6-5003", "Airbus A321", "09:30", "13 сентября", "08:00", TransferCount.ONE, HandBagage.NO, "29-31", "C3", "W", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 2 часа", 16102),

    PlaneInfo(4, "Екатеринбург", "SVX", "Новосибирск", "OVB", "2ч 40мин", "4 октября", 100, RateFlight.ECONOM, StatusFlight.SHIPPED, "Y6-5004", "Boeing 747", "20:10", "13 сентября", "18:30", TransferCount.TWO, HandBagage.YES, "32-34", "D4", "E", InfoStatus.FINISH, "Посадка успешно осуществлена", 16103),

    PlaneInfo(5, "Ростов-на-Дону", "ROV", "Краснодар", "KRR", "1ч 30мин", "5 октября", 90, RateFlight.FIRST_CLASS, StatusFlight.BOARDING, "Y6-5005", "Airbus A321", "14:00", "13 сентября", "12:30", TransferCount.ONE, HandBagage.NO, "35-37", "E5", "N", InfoStatus.INFO, "Просьба пройти на посадку через выход E5", 16104),

    PlaneInfo(6, "Уфа", "UFA", "Тюмень", "TJM", "1ч 20мин", "6 октября", 110, RateFlight.BISNESS, StatusFlight.DETAINED, "Y6-5006", "Embraer E195", "11:15", "13 сентября", "09:45", TransferCount.TWO, HandBagage.YES, "38-40", "F6", "S", InfoStatus.DANGER, "В связи с техническими причинами задержка рейса 4 часа", 16105),

    PlaneInfo(7, "Челябинск", "CEK", "Казань", "KZN", "1ч 50мин", "7 октября", 85, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5007", "Airbus A321", "08:00", "13 сентября", "06:30", TransferCount.ONE, HandBagage.NO, "41-43", "A7", "W", InfoStatus.FINISH, "Посадка успешно осуществлена", 16106),

    PlaneInfo(8, "Волгоград", "VOG", "Сочи", "AER", "1ч 40мин", "8 октября", 95, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5008", "Airbus A320", "16:00", "13 сентября", "14:30", TransferCount.TWO, HandBagage.YES, "44-46", "B8", "E", InfoStatus.INFO, "Регистрация заканчивается через 30 минут", 16107),

    PlaneInfo(9, "Пермь", "PEE", "Санкт-Петербург", "LED", "2ч 20мин", "9 октября", 75, RateFlight.BISNESS, StatusFlight.DETAINED, "Y6-5009", "Boeing 737", "13:00", "13 сентября", "11:30", TransferCount.ONE, HandBagage.NO, "47-49", "C9", "N", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 1 час", 16108),

    PlaneInfo(10, "Калуга", "KLF", "Москва", "DME", "1ч 15мин", "10 октября", 65, RateFlight.BISNESS, StatusFlight.BOARDING, "Y6-5010", "Бoeing 787", "17:45", "13 сентября ", "16:00", TransferCount.TWO, HandBagage.YES, "50-52", "D10", "S", InfoStatus.INFO, "Начата посадка бизнес-класса", 16109),

    PlaneInfo(11, "Воронеж", "VOZ", "Сочи", "AER", "1ч 45мин", "11 октября", 140, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5011", "Embraer E175", "10:30", "13 сентября", "09:00", TransferCount.ONE, HandBagage.NO, "23-25", "E11", "W", InfoStatus.FINISH, "Посадка успешно осуществлена", 161010),

    PlaneInfo(12, "Томск", "THX", "Омск", "OMS", "55мин", "12 октября", 60, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5012", "Boeing 737", "14:00", "13 сентября ", "12:30", TransferCount.TWO, HandBagage.YES, "26-28", "F12", "E", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 5 часов", 161011),

    PlaneInfo(13, "Ярославль", "IAR", "Москва", "SVO", "2ч", "13 октября", 130, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5013", "Airbus A310", "19:30", "13 сентября", "18:00", TransferCount.ONE, HandBagage.NO, "29-31", "A13", "N", InfoStatus.INFO, "Просьба пройти на посадку через выход A13", 161012),

    PlaneInfo(14, "Астрахань", "ASF", "Ростов", "ROV", "1ч 10мин", "14 октября", 100, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5014", "Airbus A330", "09:20", "13 сентября", "08:00", TransferCount.TWO, HandBagage.YES, "32-34", "B14", "S", InfoStatus.FINISH, "Посадка успешно осуществлена", 161013),

    PlaneInfo(15, "Махачкала", "MCX", "Краснодар", "KRR", "1ч 30мин", "15 октября", 90, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5015", "Boeing 737", "11:10", "13 сентября", "09:30", TransferCount.ONE, HandBagage.NO, "35-37", "C15", "W", InfoStatus.DANGER, "В связи с техническими причинами задержка рейса 2 часа", 161014),

    PlaneInfo(16, "Калуга", "KLF", "Тешино", "TES", "50мин", "16 октября", 80, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5016", "Бoeing 747", "18:00", "13 сентября ", "17:00", TransferCount.TWO, HandBagage.YES, "38-40", "D16", "E", InfoStatus.INFO, "Начата посадка пассажиров эконом-класса", 161015),

    PlaneInfo(17, "Санкт-Петербург", "LED", "Томск", "THX", "2ч 50мин", "17 октября", 150, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5017", "Embraer E175", "16:40", "13 сентября", "15:00", TransferCount.ONE, HandBagage.NO, "41-43", "E17", "N", InfoStatus.FINISH, "Посадка успешно осуществлена", 161016),

    PlaneInfo(18, "Кострома", "KOS", "Хабаровск", "KHV", "6ч", "18 октября", 200, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5018", "Airbus A320", "07:30", "13 сентября", "05:00", TransferCount.TWO, HandBagage.YES, "44-46", "F18", "S", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 6 часов", 161017),

    PlaneInfo(19, "Омск", "OMS", "Ростов", "ROV", "1ч 50мин", "19 октября", 70, RateFlight.ECONOM, StatusFlight.SHIPPED, "Y6-5019", "Airbus A321", "13:15", "13 сентября", "11:45", TransferCount.ONE, HandBagage.NO, "47-49", "A19", "W", InfoStatus.FINISH, "Посадка успешно осуществлена", 161018),

    PlaneInfo(20, "Чита", "HTA", "Москва", "DME", "6ч", "20 октября", 180, RateFlight.FIRST_CLASS, StatusFlight.BOARDING, "Y6-5020", "Boeing 777", "22:00", "13 сентября", "20:30", TransferCount.TWO, HandBagage.YES, "50-52", "B20", "E", InfoStatus.INFO, "Регистрация заканчивается через 15 минут", 161019),
    PlaneInfo(21, "Владивосток", "VVO", "Хабаровск", "KHV", "1ч 40мин", "21 октября", 160, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5021", "Airbus A320", "08:45", "13 сентября", "07:15", TransferCount.ONE, HandBagage.NO, "23-25", "C21", "N", InfoStatus.FINISH, "Посадка успешно осуществлена", 161020),

    PlaneInfo(22, "Иркутск", "IKT", "Красноярск", "KJA", "2ч 15мин", "22 октября", 140, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5022", "Boeing 737", "11:30", "13 сентября", "09:45", TransferCount.TWO, HandBagage.YES, "26-28", "D22", "S", InfoStatus.INFO, "Просьба пройти на посадку через выход D22", 161021),

    PlaneInfo(23, "Новосибирск", "OVB", "Омск", "OMS", "1ч 20мин", "23 октября", 95, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5023", "Airbus A321", "14:20", "13 сентября", "13:00", TransferCount.ONE, HandBagage.NO, "29-31", "E23", "W", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 3 часа", 161022),

    PlaneInfo(24, "Красноярск", "KJA", "Иркутск", "IKT", "2ч", "24 октября", 120, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5024", "Airbus A321", "17:00", "13 сентября", "15:30", TransferCount.TWO, HandBagage.YES, "32-34", "F24", "E", InfoStatus.FINISH, "Посадка успешно осуществлена", 161023),

    PlaneInfo(25, "Хабаровск", "KHV", "Владивосток", "VVO", "1ч 30мин", "25 октября", 110, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5025", "Boeing 747", "19:45", "13 сентября", "18:15", TransferCount.ONE, HandBagage.NO, "35-37", "A25", "N", InfoStatus.INFO, "Начата посадка пассажиров с 1 по 15 ряд", 161024),

    PlaneInfo(26, "Якутск", "YKS", "Магадан", "GDX", "2ч 30мин", "26 октября", 85, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5026", "Embraer E195", "22:30", "13 сентября", "20:00", TransferCount.TWO, HandBagage.YES, "38-40", "B26", "S", InfoStatus.DANGER, "В связи с техническими причинами задержка рейса 7 часов", 161025),

    PlaneInfo(27, "Магадан", "GDX", "Петропавловск-Камчатский", "PKC", "2ч", "27 октября", 75, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5027", "Airbus A320", "06:15", "13 сентября", "04:45", TransferCount.ONE, HandBagage.NO, "41-43", "C27", "W", InfoStatus.FINISH, "Посадка успешно осуществлена", 161026),

    PlaneInfo(28, "Южно-Сахалинск", "UUS", "Владивосток", "VVO", "2ч 10мин", "28 октября", 90, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5028", "Boeing 737", "09:00", "13 сентября", "07:30", TransferCount.TWO, HandBagage.YES, "44-46", "D28", "E", InfoStatus.INFO, "Регистрация заканчивается через 25 минут", 161027),

    PlaneInfo(29, "Петропавловск-Камчатский", "PKC", "Магадан", "GDX", "2ч 15мин", "29 октября", 80, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5029", "Airbus A321", "11:45", "13 сентября", "10:00", TransferCount.ONE, HandBagage.NO, "47-49", "E29", "N", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 4 часа", 161028),

    PlaneInfo(30, "Благовещенск", "BQS", "Хабаровск", "KHV", "1ч 45мин", "30 октября", 100, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5030", "Airbus A310", "14:30", "13 сентября", "13:15", TransferCount.TWO, HandBagage.YES, "50-52", "F30", "S", InfoStatus.FINISH, "Посадка успешно осуществлена", 161029),

    PlaneInfo(31, "Улан-Удэ", "UUD", "Иркутск", "IKT", "1ч", "1 ноября", 70, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5031", "Boeing 777", "17:15", "13 сентября", "16:15", TransferCount.ONE, HandBagage.NO, "23-25", "A31", "W", InfoStatus.INFO, "Начата посадка пассажиров с детьми", 161030),

    PlaneInfo(32, "Чита", "HTA", "Улан-Удэ", "UUD", "1ч 15мин", "2 ноября", 65, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5032", "Airbus A330", "20:00", "13 сентября", "19:15", TransferCount.TWO, HandBagage.YES, "26-28", "B32", "E", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 2 часа", 161031),

    PlaneInfo(33, "Братск", "BTK", "Красноярск", "KJA", "1ч 40мин", "3 ноября", 85, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5033", "Boeing 737", "22:45", "13 сентября", "21:35", TransferCount.ONE, HandBagage.NO, "29-31", "C33", "N", InfoStatus.FINISH, "Посадка успешно осуществлена", 161032),

    PlaneInfo(34, "Норильск", "NSK", "Новосибирск", "OVB", "2ч 45мин", "4 ноября", 110, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5034", "Airbus A321", "07:30", "13 сентября", "05:15", TransferCount.TWO, HandBagage.YES, "32-34", "D34", "S", InfoStatus.INFO, "Просьба пройти на посадку через выход D34", 161033),

    PlaneInfo(35, "Сургут", "SGC", "Тюмень", "TJM", "1ч 30мин", "5 ноября", 95, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5035", "Airbus A321", "10:15", "13 сентября", "09:15", TransferCount.ONE, HandBagage.NO, "35-37", "E35", "W", InfoStatus.DANGER, "В связи с техническими причинами задержка рейса 5 часов", 161034),

    PlaneInfo(36, "Нижневартовск", "NJC", "Сургут", "SGC", "50мин", "6 ноября", 60, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5036", "Embraer E175", "13:00", "13 сентября ", "12:10", TransferCount.TWO, HandBagage.YES, "38-40", "F36", "E", InfoStatus.FINISH, "Посадка успешно осуществлена", 161035),

    PlaneInfo(37, "Ханты-Мансийск", "HMA", "Екатеринбург", "SVX", "1ч 45мин", "7 ноября", 80, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5037", "Boeing 747", "15:45", "13 сентября", "14:30", TransferCount.ONE, HandBagage.NO, "41-43", "A37", "N", InfoStatus.INFO, "Начата посадка пассажиров с 15 по 30 ряд", 161036),

    PlaneInfo(38, "Новый Уренгой", "NUX", "Москва", "SVO", "3ч 30мин", "8 ноября", 150, RateFlight.FIRST_CLASS, StatusFlight.DETAINED, "Y6-5038", "Airbus A320", "18:30", "13 сентября", "16:00", TransferCount.TWO, HandBagage.YES, "44-46", "B38", "S", InfoStatus.DANGER, "В связи с погодными условиями задержка рейса 8 часов", 161037),

    PlaneInfo(39, "Салехард", "SLY", "Тюмень", "TJM", "2ч", "9 ноября", 70, RateFlight.BISNESS, StatusFlight.SHIPPED, "Y6-5039", "Boeing 737", "21:15", "13 сентября", "19:45", TransferCount.ONE, HandBagage.NO, "47-49", "C39", "W", InfoStatus.FINISH, "Посадка успешно осуществлена", 161038),

    PlaneInfo(40, "Надым", "NYM", "Новый Уренгой", "NUX", "1ч", "10 ноября", 55, RateFlight.ECONOM, StatusFlight.BOARDING, "Y6-5040", "Boeing 737", "23:59", "13 сентября", "22:59", TransferCount.TWO, HandBagage.YES, "50-52", "D40", "E", InfoStatus.INFO, "Регистрация заканчивается через 10 минут", 161039)
)
val LIST_DETAILED_INFO = listOf("прямой", "с пересадкой", "бизнес","эконом","ручная кладь")
