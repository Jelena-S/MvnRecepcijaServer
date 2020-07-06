-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 06, 2020 at 05:19 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `test_recepcija`
--

-- --------------------------------------------------------

--
-- Table structure for table `gost`
--

CREATE TABLE `gost` (
  `gostID` bigint(20) NOT NULL,
  `imeGosta` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prezimeGosta` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `brojLicneKarte` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `brojTelefona` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `recepcionerID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `katalog`
--

CREATE TABLE `katalog` (
  `katalogID` bigint(20) NOT NULL,
  `nazivKataloga` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `recepcioner`
--

CREATE TABLE `recepcioner` (
  `recepcionerID` bigint(20) NOT NULL,
  `imeRecepcionera` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `prezimeRecepcionera` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `soba`
--

CREATE TABLE `soba` (
  `sobaID` bigint(20) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `vrstaSobeID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `stavkakataloga`
--

CREATE TABLE `stavkakataloga` (
  `katalogID` bigint(20) NOT NULL,
  `stavkaKatalogaID` bigint(20) NOT NULL,
  `nazivStavkeKataloga` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `usluganajma`
--

CREATE TABLE `usluganajma` (
  `stavkaKatalogaID` bigint(20) NOT NULL,
  `uslugaNajmaID` bigint(20) NOT NULL,
  `nazivUslugeNajma` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `cenaUsluge` double NOT NULL,
  `vrstaSobeID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vrstasobe`
--

CREATE TABLE `vrstasobe` (
  `vrstaSobeID` bigint(20) NOT NULL,
  `nazivVrsteSobe` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `brojKreveta` int(11) NOT NULL,
  `opis` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `zakupsobe`
--

CREATE TABLE `zakupsobe` (
  `gostZakupljujeID` bigint(20) NOT NULL,
  `zakupljenaSobaID` bigint(20) NOT NULL,
  `zakupID` bigint(20) NOT NULL,
  `datumOd` date DEFAULT NULL,
  `datumDo` date DEFAULT NULL,
  `cena` double DEFAULT NULL,
  `recepcionerID` bigint(20) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `gost`
--
ALTER TABLE `gost`
  ADD PRIMARY KEY (`gostID`),
  ADD KEY `gost_ibfk_1` (`recepcionerID`);

--
-- Indexes for table `katalog`
--
ALTER TABLE `katalog`
  ADD PRIMARY KEY (`katalogID`);

--
-- Indexes for table `recepcioner`
--
ALTER TABLE `recepcioner`
  ADD PRIMARY KEY (`recepcionerID`);

--
-- Indexes for table `soba`
--
ALTER TABLE `soba`
  ADD PRIMARY KEY (`sobaID`),
  ADD KEY `soba_ibfk_1` (`vrstaSobeID`);

--
-- Indexes for table `stavkakataloga`
--
ALTER TABLE `stavkakataloga`
  ADD PRIMARY KEY (`katalogID`,`stavkaKatalogaID`),
  ADD KEY `stavkaKatalogaID` (`stavkaKatalogaID`);

--
-- Indexes for table `usluganajma`
--
ALTER TABLE `usluganajma`
  ADD PRIMARY KEY (`stavkaKatalogaID`,`uslugaNajmaID`,`vrstaSobeID`),
  ADD KEY `uslugaNajmaID` (`uslugaNajmaID`),
  ADD KEY `usluganajma_ibfk_2` (`vrstaSobeID`);

--
-- Indexes for table `vrstasobe`
--
ALTER TABLE `vrstasobe`
  ADD PRIMARY KEY (`vrstaSobeID`),
  ADD KEY `vrstaSobeID` (`vrstaSobeID`);

--
-- Indexes for table `zakupsobe`
--
ALTER TABLE `zakupsobe`
  ADD PRIMARY KEY (`gostZakupljujeID`,`zakupljenaSobaID`,`zakupID`),
  ADD KEY `zakupID` (`zakupID`),
  ADD KEY `zakupljenaSobaID` (`zakupljenaSobaID`),
  ADD KEY `zakupsobe_ibfk_1` (`recepcionerID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `gost`
--
ALTER TABLE `gost`
  MODIFY `gostID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;

--
-- AUTO_INCREMENT for table `katalog`
--
ALTER TABLE `katalog`
  MODIFY `katalogID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1112;

--
-- AUTO_INCREMENT for table `recepcioner`
--
ALTER TABLE `recepcioner`
  MODIFY `recepcionerID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `stavkakataloga`
--
ALTER TABLE `stavkakataloga`
  MODIFY `stavkaKatalogaID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=334;

--
-- AUTO_INCREMENT for table `usluganajma`
--
ALTER TABLE `usluganajma`
  MODIFY `uslugaNajmaID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `vrstasobe`
--
ALTER TABLE `vrstasobe`
  MODIFY `vrstaSobeID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2223;

--
-- AUTO_INCREMENT for table `zakupsobe`
--
ALTER TABLE `zakupsobe`
  MODIFY `zakupID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44445;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `gost`
--
ALTER TABLE `gost`
  ADD CONSTRAINT `gost_ibfk_1` FOREIGN KEY (`recepcionerID`) REFERENCES `recepcioner` (`recepcionerID`);

--
-- Constraints for table `soba`
--
ALTER TABLE `soba`
  ADD CONSTRAINT `soba_ibfk_1` FOREIGN KEY (`vrstaSobeID`) REFERENCES `vrstasobe` (`vrstaSobeID`);

--
-- Constraints for table `stavkakataloga`
--
ALTER TABLE `stavkakataloga`
  ADD CONSTRAINT `stavkakataloga_ibfk_1` FOREIGN KEY (`katalogID`) REFERENCES `katalog` (`katalogID`);

--
-- Constraints for table `usluganajma`
--
ALTER TABLE `usluganajma`
  ADD CONSTRAINT `usluganajma_ibfk_1` FOREIGN KEY (`stavkaKatalogaID`) REFERENCES `stavkakataloga` (`stavkaKatalogaID`),
  ADD CONSTRAINT `usluganajma_ibfk_2` FOREIGN KEY (`vrstaSobeID`) REFERENCES `vrstasobe` (`vrstaSobeID`);

--
-- Constraints for table `zakupsobe`
--
ALTER TABLE `zakupsobe`
  ADD CONSTRAINT `zakupsobe_ibfk_1` FOREIGN KEY (`recepcionerID`) REFERENCES `recepcioner` (`recepcionerID`),
  ADD CONSTRAINT `zakupsobe_ibfk_2` FOREIGN KEY (`zakupljenaSobaID`) REFERENCES `soba` (`sobaID`),
  ADD CONSTRAINT `zakupsobe_ibfk_3` FOREIGN KEY (`gostZakupljujeID`) REFERENCES `gost` (`gostID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
