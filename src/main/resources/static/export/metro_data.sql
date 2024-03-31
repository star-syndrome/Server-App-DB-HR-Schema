-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Mar 31, 2024 at 01:02 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `metro_data`
--

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

CREATE TABLE `country` (
  `id` int NOT NULL,
  `code` varchar(2) NOT NULL,
  `name` varchar(50) NOT NULL,
  `region_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `country`
--

INSERT INTO `country` (`id`, `code`, `name`, `region_id`) VALUES
(1, 'ID', 'Indonesia', 1),
(2, 'MY', 'Malaysia', 1),
(3, 'SG', 'Singapore', 1),
(4, 'IT', 'Italy', 2),
(5, 'FR', 'France', 2),
(6, 'SW', 'Switzerland', 2);

-- --------------------------------------------------------

--
-- Table structure for table `region`
--

CREATE TABLE `region` (
  `id` int NOT NULL,
  `name` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `region`
--

INSERT INTO `region` (`id`, `name`) VALUES
(1, 'Asia'),
(2, 'Europe');

-- --------------------------------------------------------

--
-- Table structure for table `role_privilege`
--

CREATE TABLE `role_privilege` (
  `role_id` int NOT NULL,
  `privilege_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `role_privilege`
--

INSERT INTO `role_privilege` (`role_id`, `privilege_id`) VALUES
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(1, 1),
(1, 2),
(1, 3),
(1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `tb_employee`
--

CREATE TABLE `tb_employee` (
  `id` int NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(50) NOT NULL,
  `phone` varchar(13) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_employee`
--

INSERT INTO `tb_employee` (`id`, `email`, `name`, `phone`) VALUES
(1, 'muhamadbintangalfarizki@gmail.com', 'Muhamad Bintang Alfarizki', '085156169412'),
(2, 'alfarizky0912@gmail.com', 'Bintang', '081234567890');

-- --------------------------------------------------------

--
-- Table structure for table `tb_privilege`
--

CREATE TABLE `tb_privilege` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_privilege`
--

INSERT INTO `tb_privilege` (`id`, `name`) VALUES
(1, 'CREATE_ADMIN'),
(5, 'CREATE_USER'),
(4, 'DELETE_ADMIN'),
(8, 'DELETE_USER'),
(2, 'READ_ADMIN'),
(6, 'READ_USER'),
(3, 'UPDATE_ADMIN'),
(7, 'UPDATE_USER');

-- --------------------------------------------------------

--
-- Table structure for table `tb_role`
--

CREATE TABLE `tb_role` (
  `id` int NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_role`
--

INSERT INTO `tb_role` (`id`, `name`) VALUES
(1, 'ADMIN'),
(2, 'USER');

-- --------------------------------------------------------

--
-- Table structure for table `tb_user`
--

CREATE TABLE `tb_user` (
  `id` int NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `tb_user`
--

INSERT INTO `tb_user` (`id`, `password`, `username`) VALUES
(1, '$2a$10$PPp2Ir6VIJCWpQlT/dJbRONNR386lPJUGPNxNvXEn6b2X0Gw172dm', 'starsyndrome'),
(2, '$2a$10$XiLJodXFBKMBFMP5fb2CVeA.9mAoQGHA8WxQ3jGXeJBrlzz/WUHVO', 'alfariz.ki');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `country`
--
ALTER TABLE `country`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code_unique` (`code`),
  ADD KEY `fk_country` (`region_id`);

--
-- Indexes for table `region`
--
ALTER TABLE `region`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_unique` (`name`);

--
-- Indexes for table `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD KEY `FK1hd7c4hm13p5265k7vb0j0vby` (`privilege_id`),
  ADD KEY `FKkb5ee83ys6omn5gnvs7fggyao` (`role_id`);

--
-- Indexes for table `tb_employee`
--
ALTER TABLE `tb_employee`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_em9lvg7whqbrro5fbav5bt0gy` (`email`),
  ADD UNIQUE KEY `UK_etos8s0ejytr6lh54h6bvt496` (`phone`);

--
-- Indexes for table `tb_privilege`
--
ALTER TABLE `tb_privilege`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_t4vvgri0dgkkyq3uu04gfa3hu` (`name`);

--
-- Indexes for table `tb_role`
--
ALTER TABLE `tb_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_unique` (`name`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_4wv83hfajry5tdoamn8wsqa6x` (`username`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `fk_user3` (`role_id`),
  ADD KEY `fk_user2` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `country`
--
ALTER TABLE `country`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `region`
--
ALTER TABLE `region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_employee`
--
ALTER TABLE `tb_employee`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tb_privilege`
--
ALTER TABLE `tb_privilege`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tb_role`
--
ALTER TABLE `tb_role`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `country`
--
ALTER TABLE `country`
  ADD CONSTRAINT `fk_country` FOREIGN KEY (`region_id`) REFERENCES `region` (`id`);

--
-- Constraints for table `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD CONSTRAINT `FK1hd7c4hm13p5265k7vb0j0vby` FOREIGN KEY (`privilege_id`) REFERENCES `tb_privilege` (`id`),
  ADD CONSTRAINT `FKkb5ee83ys6omn5gnvs7fggyao` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`);

--
-- Constraints for table `tb_user`
--
ALTER TABLE `tb_user`
  ADD CONSTRAINT `fk_user1` FOREIGN KEY (`id`) REFERENCES `tb_employee` (`id`);

--
-- Constraints for table `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `fk_user2` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  ADD CONSTRAINT `fk_user3` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
