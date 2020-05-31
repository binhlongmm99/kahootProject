-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2020 at 06:09 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kahoot`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `acc_id` int(11) NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `create_date` date NOT NULL DEFAULT current_timestamp(),
  `ip_address` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`acc_id`, `username`, `password`, `create_date`, `ip_address`) VALUES
(1, 'long', '123', '2020-05-30', NULL),
(2, 'anh', '123', '2020-05-30', NULL),
(3, 'minh', '123', '2020-05-30', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE `question` (
  `ques_id` int(11) NOT NULL,
  `question` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option1` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option2` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option3` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `option4` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `correct_answer` varchar(1) COLLATE utf8_unicode_ci NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `question`
--

INSERT INTO `question` (`ques_id`, `question`, `option1`, `option2`, `option3`, `option4`, `correct_answer`, `topic_id`) VALUES
(1, 'SDLC stands for?', 'Software Development Life Cycle', 'System Development Life cycle', 'Software Design Life Cycle', 'System Design Life Cycle', 'A', 1),
(2, 'Which one of the following models is not suitable for accommodating any change?', 'Build & Fix Model', 'Prototyping Model', 'RAD Model', 'Waterfall Model', 'D', 1),
(3, 'Which model can be selected if user is involved in all the phases of SDLC?', 'Waterfall Model', 'Prototyping Model', 'RAD Model', 'both Prototyping Model & RAD Model', 'C', 1),
(4, 'Agile Software Development is based on', 'Incremental Development', 'Iterative Development', 'Linear Development', 'Both Incremental and Iterative Development', 'D', 1),
(5, 'How many phases are there in Scrum ?', '2', '3', '4', 'Scrum is an agile method which means it does not have phases', 'B', 1),
(6, 'Which is the first step in the software development life cycle ?', 'Analysis', 'Design', 'Problem/Opportunity Identification', 'Development and Documentation', 'C', 1),
(7, 'Who designs and implement database structures.', 'Programmers', 'Project managers', 'Technical writers', 'Database administrators', 'D', 1),
(8, '____________ is the process of translating a task into a series of commands that a computer will use to perform that task.', 'Project design', 'Installation', 'Systems analysis', 'Programming', 'D', 1),
(9, 'Debugging is:', 'creating program code', 'finding and correcting errors in the program code', 'identifying the task to be computerized', 'creating the algorithm', 'B', 1),
(10, 'The importance of software design can be summarized in a single word which is:', 'Efficiency', 'Accuracy', 'Quality', 'Complexity', 'C', 1),
(11, 'Which methods are commonly used in Server Socket class?', 'Public Output Stream get Output Stream ()', 'Public Socket accept ()', 'Public synchronized void close ()', 'Public void connect ()', 'B', 2),
(12, 'Which constructor of Datagram Socket class is used to create a datagram socket and binds it with the given Port Number?', 'Datagram Socket(int port)', 'Datagram Socket(int port, Int Address address)', 'Datagram Socket()', 'Datagram Socket(int address)', 'B', 2),
(13, 'The client in socket programming must know which information?', 'IP address of Server', 'Port number', 'Both IP address of Server & Port number', 'Only its own IP address', 'C', 2),
(14, 'TCP, FTP, Telnet, SMTP, POP etc. are examples of ___________', 'Socket', 'IP Address', 'Protocol', 'MAC Address', 'C', 2),
(15, 'What does the java.net.InetAddress class represent?', 'Socket', 'IP Address', ' Protocol', 'MAC Address', 'B', 2),
(16, 'In Inet Address class, which method returns the host name of the IP Address?', 'Public String get Hostname()', 'Public String getHostAddress()', 'Public static InetAddress get Localhost()', 'Public getByName()', 'A', 2),
(17, 'Which of the following is not applicable for IP?', 'Error reporting', 'Handle addressing conventions', 'Datagram format', 'Packet handling', 'A', 2),
(18, 'TCP process may not write and read data at the same speed. So we need __________ for storage.', 'Packets', 'Buffers', 'Segments', 'Stacks', 'B', 2),
(19, 'Which of the following is false with respect to TCP?', 'Connection oriented', 'Process to process', 'Transport layer protocol', 'Unreliable', 'D', 2),
(20, 'Which of the following is false with respect to UDP?', 'Connection oriented', 'Unreliable', 'Transport layer protocol', 'Low overhead', 'A', 2),
(21, 'Which of the following best defines a class?', 'Parent of an object', 'Instance of an object', 'Blueprint of an object', 'Scope of an object', 'B', 3),
(22, 'Which is not feature of OOP in general definitions?', 'Code reusability', 'Modularity', 'Duplicate/Redundant data', 'Efficient Code', 'C', 3),
(23, 'Which Feature of OOP illustrated the code reusability?', 'Polymorphism', ' Abstraction', 'Encapsulation', ' Inheritance', 'D', 3),
(24, 'Which definition best describes an object?', 'Instance of a class', 'Instance of itself', 'Child of a class', 'Overview of a class', 'A', 3),
(25, 'Which among the following is false?', 'Object must be created before using members of a class', 'Memory for an object is allocated only after its constructor is called', 'Objects can’t be passed by reference', 'Objects size depends on its class data members', 'C', 3),
(26, 'What is size of the object of following class (64 bit system)?\r\nclass student {  int rollno;  char  name[20];  static int studentno;  };', '20', '22', '24', '28', 'C', 3),
(27, 'When an object is returned___________', 'A temporary object is created to return the value', 'The same object used in function is used to return the value', 'The Object can be returned without creation of temporary object', 'Object are returned implicitly, we can’t say how it happens inside program', 'A', 3),
(28, ' Class is pass by _______', ' Value', 'Reference', 'Value or Reference, depending on program', 'Copy', 'B', 3),
(29, 'Instance of which type of class can’t be created?', 'Anonymous class', 'Nested class', 'Parent class', 'Abstract class', 'D', 3),
(30, 'When an object is returned___________', 'A temporary object is created to return the value', 'The same object used in function is used to return the value', 'The Object can be returned without creation of temporary object', 'Object are returned implicitly, we can’t say how it happens inside program', 'A', 3);

-- --------------------------------------------------------

--
-- Table structure for table `room`
--

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL,
  `room_name` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  `host_id` int(11) NOT NULL,
  `topic_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `score`
--

CREATE TABLE `score` (
  `score_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `score` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `topic`
--

CREATE TABLE `topic` (
  `topic_id` int(11) NOT NULL,
  `topic_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `owner_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `topic`
--

INSERT INTO `topic` (`topic_id`, `topic_name`, `owner_id`) VALUES
(1, 'Software Engineering', 1),
(2, 'Network Programming', 1),
(3, 'Object Oriented Programming', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`acc_id`);

--
-- Indexes for table `question`
--
ALTER TABLE `question`
  ADD PRIMARY KEY (`ques_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Indexes for table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`),
  ADD KEY `host_id` (`host_id`),
  ADD KEY `topic_id` (`topic_id`);

--
-- Indexes for table `score`
--
ALTER TABLE `score`
  ADD PRIMARY KEY (`score_id`),
  ADD KEY `player_id` (`player_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Indexes for table `topic`
--
ALTER TABLE `topic`
  ADD PRIMARY KEY (`topic_id`),
  ADD KEY `topic_ibfk_1` (`owner_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `acc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `question`
--
ALTER TABLE `question`
  MODIFY `ques_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `room`
--
ALTER TABLE `room`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `score`
--
ALTER TABLE `score`
  MODIFY `score_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `topic`
--
ALTER TABLE `topic`
  MODIFY `topic_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `question`
--
ALTER TABLE `question`
  ADD CONSTRAINT `question_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `room`
--
ALTER TABLE `room`
  ADD CONSTRAINT `room_ibfk_1` FOREIGN KEY (`host_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `room_ibfk_2` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `score_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `topic`
--
ALTER TABLE `topic`
  ADD CONSTRAINT `topic_ibfk_1` FOREIGN KEY (`owner_id`) REFERENCES `account` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
