DELETE FROM LNK_TYPES_ITEMS;
DELETE FROM HUB_TYPES;
DELETE FROM HUB_ITEMS;
INSERT INTO HUB_TYPES(ID, NAME) VALUES (1, 'MUSIC');
INSERT INTO HUB_TYPES(ID, NAME) VALUES (2, 'BOOK');
INSERT INTO HUB_TYPES(ID, NAME) VALUES (3, 'MOVIE');


INSERT INTO HUB_ITEMS(ID, CODE) VALUES (1, 'B00001');
INSERT INTO LNK_TYPES_ITEMS(ID, HUB_ITEM, HUB_TYPE) VALUES (1, 1, 2);

INSERT INTO HUB_ITEMS(ID, CODE) VALUES (2, 'B00002');
INSERT INTO LNK_TYPES_ITEMS(ID, HUB_ITEM, HUB_TYPE) VALUES (2, 2, 2);

INSERT INTO HUB_ITEMS(ID, CODE) VALUES (3, 'B00003');
INSERT INTO LNK_TYPES_ITEMS(ID, HUB_ITEM, HUB_TYPE) VALUES (3, 3, 2);

INSERT INTO HUB_ITEMS(ID, CODE) VALUES (4, 'B00004');
INSERT INTO LNK_TYPES_ITEMS(ID, HUB_ITEM, HUB_TYPE) VALUES (4, 4, 2);

INSERT INTO HUB_ITEMS(ID, CODE) VALUES (5, 'B00005');
INSERT INTO LNK_TYPES_ITEMS(ID, HUB_ITEM, HUB_TYPE) VALUES (5, 5, 2);

DELETE FROM SAT_BOOKS;
INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (1, 1, 'Л.Н. Толстой', 'Война и мир', 'Русская классика', 'Роман', 2006, 'Русская литература, Москва', 'Твёрдый переплёт' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (2, 1, 'И.С. Тургенев', 'Отцы и дети', 'Русская классика', 'Роман', 2007, 'Русская литература, Москва', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (3, 1, 'А.С. Пушкин', 'Сборник стихов', 'Русская классика', 'Поэзия', 1999, 'Русская литература, Москва', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (4, 1, 'А.А. Фет', 'Времена года', 'Русская классика', 'Поэзия', 2010, 'Пересвет, СПб', '135 страниц' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (5, 1, 'А.А. Шибаев', 'Взялись за руки друзья', 'Детская литература', 'Поэзия', 2010, 'Детская книга, СПб', '205 страниц с иллюстрациями' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (6, 2, 'Р.А. Сворень', 'Электроника: шаг за шагом', 'Наука и техника', 'Радиолюбительские конструкции', 2006, 'Радио и Связь, Москва', '250 с.' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (7, 2, 'Я. Войцеховский', 'Радиоэлектронные игрушки', 'Наука и техника', 'Радиолюбительские конструкции', 2011, 'Радио и связь, Москва', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (8, 2, 'Г.В. Дорофеев', 'Пособие по математике', 'Высшее образование', 'Учебная книга', 1969, 'Наука, Москва', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (9, 3, '', 'Химия и жизнь', 'Подшивка журналов', 'Научно-популярная литература', NULL, 'Химия и жизнь', '1982,1986,1989' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (10, 3, '', 'Техника молодёжи', 'Подшивка журналов', 'Научно-популярная литература', NULL, 'Техника молодёжи', '1991,1992' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (11, 3, '', 'Здоровье', 'Подшивка журналов', 'Научно-популярная литература', NULL, 'Здоровье', '1985,1986,1987,1988,1989' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (12, 4, 'Кристофер Александер', 'Язык шаблонов', 'Дизайн и архитектура', 'Научно-популярная литература', 2015, 'Архитектура и Строительство, Москва', 'Подарочное издание' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (13, 4, 'Р. Докинз', 'Слепой часовщик', 'Эволюция', 'Научно-популярная литература', 2010, 'Высшая школа, СПб', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (14, 4, 'Ж. Бодрийяр', 'Симуляция и симулякры', 'Философия', 'Трактат', 2010, 'Высшая школа, СПб', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (15, 5, 'В.О. Пелевин', 'Любовь к трём Цукербринам', 'Современная проза', 'Роман', 2016, 'Эксмо, Москва', '' );

INSERT INTO SAT_BOOKS(ID, HUB_ITEM, AUTHOR, NAME, SERIES, GENRE, PBYEAR, PUBLISHER, NOTE)
VALUES (16, 5, 'Аркадий и Борис Стругацкие', 'Улитка на склоне', 'Советская фантастика', 'Роман', 2010, 'Пересвет, СПб', '135 страниц' );
