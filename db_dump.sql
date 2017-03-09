-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Wersja serwera:               10.1.10-MariaDB - mariadb.org binary distribution
-- Serwer OS:                    Win32
-- HeidiSQL Wersja:              9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Zrzut struktury bazy danych projectmgr
DROP DATABASE IF EXISTS `projectmgr`;
CREATE DATABASE IF NOT EXISTS `projectmgr` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `projectmgr`;


-- Zrzut struktury tabela projectmgr.daty_logowania
DROP TABLE IF EXISTS `daty_logowania`;
CREATE TABLE IF NOT EXISTS `daty_logowania` (
  `idDaty_logowania` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Pracownicy_idPracownika` int(10) unsigned NOT NULL,
  `data_logowania` date DEFAULT NULL,
  PRIMARY KEY (`idDaty_logowania`),
  KEY `Daty_logowania_FKIndex1` (`Pracownicy_idPracownika`)
) ENGINE=InnoDB AUTO_INCREMENT=113 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.daty_logowania: ~87 rows (około)
DELETE FROM `daty_logowania`;
/*!40000 ALTER TABLE `daty_logowania` DISABLE KEYS */;
INSERT INTO `daty_logowania` (`idDaty_logowania`, `Pracownicy_idPracownika`, `data_logowania`) VALUES
	(1, 1, '2016-04-07'),
	(2, 1, '2016-04-08'),
	(3, 1, '2016-04-08'),
	(4, 1, '2016-04-10'),
	(5, 1, '2016-04-11'),
	(6, 1, '2016-04-12'),
	(7, 1, '2016-04-12'),
	(8, 1, '2016-04-12'),
	(9, 2, '2016-04-12'),
	(10, 3, '2016-04-12'),
	(11, 1, '2016-04-12'),
	(12, 1, '2016-04-12'),
	(13, 2, '2016-04-07'),
	(14, 2, '2016-04-14'),
	(15, 1, '2016-04-12'),
	(16, 1, '2016-04-12'),
	(17, 1, '2016-04-12'),
	(18, 1, '2016-05-10'),
	(19, 4, '2016-05-10'),
	(20, 4, '2016-05-10'),
	(21, 4, '2016-05-10'),
	(22, 4, '2016-05-10'),
	(23, 4, '2016-05-10'),
	(24, 4, '2016-05-10'),
	(25, 4, '2016-05-10'),
	(26, 4, '2016-05-10'),
	(27, 4, '2016-05-10'),
	(28, 4, '2016-05-10'),
	(29, 4, '2016-05-10'),
	(30, 4, '2016-05-10'),
	(31, 4, '2016-05-10'),
	(32, 4, '2016-05-10'),
	(33, 4, '2016-05-10'),
	(34, 4, '2016-05-10'),
	(35, 4, '2016-05-10'),
	(36, 4, '2016-05-10'),
	(37, 4, '2016-05-10'),
	(38, 4, '2016-05-10'),
	(39, 4, '2016-05-10'),
	(40, 4, '2016-05-10'),
	(41, 4, '2016-05-10'),
	(42, 4, '2016-05-10'),
	(43, 4, '2016-05-10'),
	(44, 4, '2016-05-10'),
	(45, 1, '2016-05-10'),
	(46, 1, '2016-05-11'),
	(47, 1, '2016-05-11'),
	(48, 4, '2016-05-11'),
	(49, 4, '2016-05-11'),
	(50, 4, '2016-05-11'),
	(51, 4, '2016-05-11'),
	(52, 4, '2016-05-11'),
	(53, 1, '2016-05-11'),
	(54, 1, '2016-05-11'),
	(55, 1, '2016-05-11'),
	(56, 1, '2016-05-11'),
	(57, 1, '2016-05-11'),
	(58, 1, '2016-05-11'),
	(59, 1, '2016-05-11'),
	(60, 1, '2016-05-11'),
	(61, 4, '2016-05-16'),
	(62, 1, '2016-05-16'),
	(63, 1, '2016-05-16'),
	(64, 2, '2016-05-16'),
	(65, 1, '2016-05-16'),
	(66, 2, '2016-05-16'),
	(67, 2, '2016-05-16'),
	(68, 1, '2016-05-17'),
	(69, 2, '2016-05-17'),
	(70, 2, '2016-05-17'),
	(71, 2, '2016-05-17'),
	(72, 2, '2016-05-17'),
	(73, 2, '2016-05-17'),
	(74, 2, '2016-05-17'),
	(75, 1, '2016-05-17'),
	(76, 1, '2016-05-17'),
	(77, 1, '2016-05-17'),
	(78, 1, '2016-05-17'),
	(79, 1, '2016-05-17'),
	(80, 1, '2016-05-17'),
	(81, 1, '2016-05-17'),
	(82, 1, '2016-05-17'),
	(83, 1, '2016-05-23'),
	(84, 1, '2016-05-23'),
	(85, 2, '2016-05-23'),
	(86, 2, '2016-05-23'),
	(87, 2, '2016-05-23'),
	(88, 1, '2016-05-23'),
	(89, 1, '2016-05-24'),
	(90, 1, '2016-05-24'),
	(91, 1, '2016-05-24'),
	(92, 1, '2016-05-24'),
	(93, 1, '2016-05-24'),
	(94, 1, '2016-05-24'),
	(95, 1, '2016-05-24'),
	(96, 1, '2016-05-24'),
	(97, 1, '2016-05-24'),
	(98, 1, '2016-05-24'),
	(99, 1, '2016-05-24'),
	(100, 1, '2016-05-24'),
	(101, 1, '2016-05-24'),
	(102, 1, '2016-05-24'),
	(103, 1, '2016-05-24'),
	(104, 1, '2016-05-24'),
	(105, 1, '2016-05-24'),
	(106, 1, '2016-05-24'),
	(107, 1, '2016-05-24'),
	(108, 1, '2016-05-24'),
	(109, 1, '2016-05-24'),
	(110, 1, '2016-05-24'),
	(111, 1, '2016-05-24'),
	(112, 1, '2016-05-24');
/*!40000 ALTER TABLE `daty_logowania` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.notatki
DROP TABLE IF EXISTS `notatki`;
CREATE TABLE IF NOT EXISTS `notatki` (
  `idNotatki` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Zadania_idZadania` int(10) unsigned NOT NULL,
  `nazwa` varchar(45) DEFAULT NULL,
  `opis` text,
  PRIMARY KEY (`idNotatki`),
  KEY `Notatki_FKIndex1` (`Zadania_idZadania`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.notatki: ~0 rows (około)
DELETE FROM `notatki`;
/*!40000 ALTER TABLE `notatki` DISABLE KEYS */;
/*!40000 ALTER TABLE `notatki` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.pracownicy
DROP TABLE IF EXISTS `pracownicy`;
CREATE TABLE IF NOT EXISTS `pracownicy` (
  `idPracownika` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idProjektu` int(10) unsigned DEFAULT NULL,
  `imie` varchar(45) DEFAULT NULL,
  `nazwisko` varchar(45) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `haslo` varchar(20) DEFAULT NULL,
  `pesel` varchar(45) DEFAULT NULL,
  `kierownik` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`idPracownika`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `pesel` (`pesel`),
  KEY `FK_pracownicy_projekty` (`idProjektu`),
  CONSTRAINT `FK_pracownicy_projekty` FOREIGN KEY (`idProjektu`) REFERENCES `projekty` (`idProjektu`) ON DELETE SET NULL ON UPDATE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.pracownicy: ~4 rows (około)
DELETE FROM `pracownicy`;
/*!40000 ALTER TABLE `pracownicy` DISABLE KEYS */;
INSERT INTO `pracownicy` (`idPracownika`, `idProjektu`, `imie`, `nazwisko`, `login`, `haslo`, `pesel`, `kierownik`) VALUES
	(1, NULL, NULL, NULL, 'admin', 'admin', NULL, 1),
	(2, 1, 'Piotr', 'J', 'piotrj', 'demo', '12345678910', 1),
	(3, 1, 'Antonio', 'M', 'antoniom', 'test', '00000000', 0),
	(4, 1, 'Stefan', 'G', 'stefang', 'nie', '932456789', 0);
/*!40000 ALTER TABLE `pracownicy` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.priorytet
DROP TABLE IF EXISTS `priorytet`;
CREATE TABLE IF NOT EXISTS `priorytet` (
  `idPriorytet` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(45) NOT NULL,
  PRIMARY KEY (`idPriorytet`),
  UNIQUE KEY `nazwa` (`nazwa`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.priorytet: ~3 rows (około)
DELETE FROM `priorytet`;
/*!40000 ALTER TABLE `priorytet` DISABLE KEYS */;
INSERT INTO `priorytet` (`idPriorytet`, `nazwa`) VALUES
	(3, 'High'),
	(1, 'Low'),
	(2, 'Medium');
/*!40000 ALTER TABLE `priorytet` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.projekty
DROP TABLE IF EXISTS `projekty`;
CREATE TABLE IF NOT EXISTS `projekty` (
  `idProjektu` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(45) DEFAULT NULL,
  `opis` varchar(255) DEFAULT NULL,
  `data_rozpoczecia` date DEFAULT NULL,
  `data_zakonczenia` date DEFAULT NULL,
  `id_kierownika` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`idProjektu`),
  UNIQUE KEY `nazwa` (`nazwa`),
  KEY `FK_projekty_pracownicy` (`id_kierownika`),
  CONSTRAINT `FK_projekty_pracownicy` FOREIGN KEY (`id_kierownika`) REFERENCES `pracownicy` (`idPracownika`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.projekty: ~3 rows (około)
DELETE FROM `projekty`;
/*!40000 ALTER TABLE `projekty` DISABLE KEYS */;
INSERT INTO `projekty` (`idProjektu`, `nazwa`, `opis`, `data_rozpoczecia`, `data_zakonczenia`, `id_kierownika`) VALUES
	(1, 'Nowy', 'Takie tam', '2016-04-12', '2016-04-30', 2),
	(2, 'Inny', 'Nie', '2016-04-12', '2016-04-14', 2),
	(3, 'test', '', '2016-05-17', '2016-05-19', 2);
/*!40000 ALTER TABLE `projekty` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.status
DROP TABLE IF EXISTS `status`;
CREATE TABLE IF NOT EXISTS `status` (
  `idstatusu` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nazwa` varchar(45) NOT NULL,
  PRIMARY KEY (`idstatusu`),
  UNIQUE KEY `nazwa` (`nazwa`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.status: ~5 rows (około)
DELETE FROM `status`;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` (`idstatusu`, `nazwa`) VALUES
	(5, 'Completed'),
	(2, 'In progress'),
	(1, 'Not started'),
	(4, 'Suspended'),
	(3, 'Waiting');
/*!40000 ALTER TABLE `status` ENABLE KEYS */;


-- Zrzut struktury tabela projectmgr.zadania
DROP TABLE IF EXISTS `zadania`;
CREATE TABLE IF NOT EXISTS `zadania` (
  `idZadania` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idProjektu` int(10) unsigned NOT NULL,
  `idPracownika` int(10) unsigned DEFAULT NULL,
  `idStatus` int(10) unsigned NOT NULL DEFAULT '1',
  `idPriorytet` int(10) unsigned NOT NULL DEFAULT '1',
  `nazwa` varchar(45) NOT NULL,
  `progress` int(3) NOT NULL DEFAULT '0',
  `notatka` text NOT NULL,
  PRIMARY KEY (`idZadania`),
  KEY `FK_zadania_projekty` (`idProjektu`),
  KEY `FK_zadania_pracownicy` (`idPracownika`),
  KEY `FK_zadania_priorytet` (`idPriorytet`),
  KEY `FK_zadania_status` (`idStatus`),
  CONSTRAINT `FK_zadania_pracownicy` FOREIGN KEY (`idPracownika`) REFERENCES `pracownicy` (`idPracownika`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `FK_zadania_priorytet` FOREIGN KEY (`idPriorytet`) REFERENCES `priorytet` (`idPriorytet`),
  CONSTRAINT `FK_zadania_projekty` FOREIGN KEY (`idProjektu`) REFERENCES `projekty` (`idProjektu`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_zadania_status` FOREIGN KEY (`idStatus`) REFERENCES `status` (`idstatusu`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Zrzucanie danych dla tabeli projectmgr.zadania: ~2 rows (około)
DELETE FROM `zadania`;
/*!40000 ALTER TABLE `zadania` DISABLE KEYS */;
INSERT INTO `zadania` (`idZadania`, `idProjektu`, `idPracownika`, `idStatus`, `idPriorytet`, `nazwa`, `progress`, `notatka`) VALUES
	(1, 1, 3, 2, 2, 'Zrób to', 0, ''),
	(2, 1, 4, 2, 1, 'Inne jakie?', 75, 'A cośtam dłubie óżźćńąłę'),
	(3, 1, 4, 2, 2, 'DOit', 0, '');
/*!40000 ALTER TABLE `zadania` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
