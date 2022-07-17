-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : sam. 18 juin 2022 à 09:14
-- Version du serveur : 5.7.38
-- Version de PHP : 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `testdb1`
--

-- --------------------------------------------------------

--
-- Structure de la table `NHSSPEC`
--

CREATE TABLE `NHSSPEC` (
  `id` int(11) NOT NULL,
  `groupe` varchar(128) NOT NULL,
  `spec` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `NHSSPEC`
--

INSERT INTO `NHSSPEC` (`id`, `groupe`, `spec`) VALUES
(1, 'Anesthésie', 'Anesthésie'),
(2, 'Anesthésie', 'Soins intensifs'),
(3, 'Oncologie clinique', 'Oncologie clinique'),
(4, 'Groupe dentaire', 'Spécialités dentaires supplémentaires'),
(5, 'Groupe dentaire', 'Radiologie dentaire et maxillo-faciale'),
(6, 'Groupe dentaire', 'Endodontie'),
(7, 'Groupe dentaire', 'Chirurgie buccale et maxillo-faciale'),
(8, 'Groupe dentaire', 'Pathologie buccale et maxillo-faciale'),
(9, 'Groupe dentaire', 'Médecine buccale'),
(10, 'Groupe dentaire', 'Chirurgie buccale'),
(11, 'Groupe dentaire', 'Orthodontie'),
(12, 'Groupe dentaire', 'Dentisterie pédiatrique'),
(13, 'Groupe dentaire', 'Parodontie'),
(14, 'Groupe dentaire', 'Prosthodontie'),
(15, 'Groupe dentaire', 'Dentisterie restauratrice'),
(16, 'Groupe dentaire', 'Dentisterie de soins spéciaux'),
(17, 'Médecine d\'urgence', 'Médecine d\'urgence'),
(18, 'Groupe de médecine générale', 'Médecine interne de soins aigus'),
(19, 'Groupe de médecine générale', 'Allergie'),
(20, 'Groupe de médecine générale', 'Médecine audiovestibulaire'),
(21, 'Groupe de médecine générale', 'Cardiologie'),
(22, 'Groupe de médecine générale', 'Génétique clinique'),
(23, 'Groupe de médecine générale', 'Neurophysiologie clinique'),
(24, 'Groupe de médecine générale', 'Pharmacologie clinique et thérapeutique'),
(25, 'Groupe de médecine générale', 'Dermatologie'),
(26, 'Groupe de médecine générale', 'Endocrinologie et diabète sucré'),
(27, 'Groupe de médecine générale', 'Gastroentérologie'),
(28, 'Groupe de médecine générale', 'Médecine générale (interne)'),
(29, 'Groupe de médecine générale', 'Médecin généraliste'),
(30, 'Groupe de médecine générale', 'Médecine générale (GP) 6 mois'),
(31, 'Groupe de médecine générale', 'Médecine génito-urinaire'),
(32, 'Groupe de médecine générale', 'Médecine gériatrique'),
(33, 'Groupe de médecine générale', 'Maladies infectieuses'),
(34, 'Groupe de médecine générale', 'Oncologie médicale'),
(35, 'Groupe de médecine générale', 'Ophtalmologie médicale'),
(36, 'Groupe de médecine générale', 'Neurologie'),
(37, 'Groupe de médecine générale', 'Médecine du travail'),
(38, 'Groupe de médecine générale', 'Autre'),
(39, 'Groupe de médecine générale', 'Médecine palliative'),
(40, 'Groupe de médecine générale', 'Médecine de réadaptation'),
(41, 'Groupe de médecine générale', 'Médecine rénale'),
(42, 'Groupe de médecine générale', 'Médecine respiratoire'),
(43, 'Groupe de médecine générale', 'Rhumatologie'),
(44, 'Groupe de médecine générale', 'Médecine du sport et de l\'exercice'),
(45, 'Obstétrique et gynécologie', 'Santé publique sexuelle et procréative'),
(46, 'Groupe pédiatrique', 'Cardiologie pédiatrique'),
(47, 'Groupe pédiatrique', 'Pédiatrie'),
(48, 'Groupe de pathologie', 'Pathologie chimique'),
(49, 'Groupe de pathologie', 'Neuropathologie diagnostique'),
(50, 'Groupe de pathologie', 'Histopathologie médico-légale'),
(51, 'Groupe de pathologie', 'Pathologie générale'),
(52, 'Groupe de pathologie', 'Hématologie'),
(53, 'Groupe de pathologie', 'Histopathologie'),
(54, 'Groupe de pathologie', 'Immunologie'),
(55, 'Groupe de pathologie', 'Microbiologie médicale'),
(56, 'Groupe de pathologie', 'Pathologie pédiatrique et périnatale'),
(57, 'Groupe de pathologie', 'Virologie'),
(58, 'Groupe Pronostiques et gestion de la santé/Santé communautaire', 'Service de santé communautaire dentaire'),
(59, 'Groupe Pronostiques et gestion de la santé/Santé communautaire', 'Service de santé communautaire médical'),
(60, 'Groupe Pronostiques et gestion de la santé/Santé communautaire', 'Santé publique dentaire'),
(61, 'Groupe Pronostiques et gestion de la santé/Santé communautaire', 'Praticien de l’art dentaire'),
(62, 'Groupe Pronostiques et gestion de la santé/Santé communautaire', 'Santé publique'),
(63, 'Groupe de psychiatrie', 'Psychiatrie infantile et adolescente'),
(64, 'Groupe de psychiatrie', 'Psychiatrie légale'),
(65, 'Groupe de psychiatrie', 'Psychiatrie générale'),
(66, 'Groupe de psychiatrie', 'Psychiatrie de la vieillesse'),
(67, 'Groupe de psychiatrie', 'Psychiatrie des troubles d\'apprentissage'),
(68, 'Groupe de psychiatrie', 'Psychothérapie'),
(69, 'Groupe de radiologie', 'Radiologie clinique'),
(70, 'Groupe de radiologie', 'Médecine nucléaire'),
(71, 'Groupe chirurgical', 'Chirurgie cardiothoracique'),
(72, 'Groupe chirurgical', 'Chirurgie générale'),
(73, 'Groupe chirurgical', 'Neurochirurgie'),
(74, 'Groupe chirurgical', 'Ophtalmologie'),
(75, 'Groupe chirurgical', 'Otolaryngologie'),
(76, 'Groupe chirurgical', 'Chirurgie pédiatrique'),
(77, 'Groupe chirurgical', 'Chirurgie plastique'),
(78, 'Groupe chirurgical', 'Traumatologie et chirurgie orthopédique'),
(79, 'Groupe chirurgical', 'Urologie'),
(80, 'Groupe chirurgical', 'Chirurgie vasculaire');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `NHSSPEC`
--
ALTER TABLE `NHSSPEC`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `NHSSPEC`
--
ALTER TABLE `NHSSPEC`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
