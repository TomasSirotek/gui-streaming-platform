<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

# Streaming-platform-javaFx

:school_satchel: Compulsory Assignment | 2st Semester | SDE

---

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://user-images.githubusercontent.com/72190589/218590235-65100c9b-90df-4731-a3da-ee8e570823b0.png">
    <img src="https://user-images.githubusercontent.com/72190589/218590235-65100c9b-90df-4731-a3da-ee8e570823b0.png" alt="Logo" width="250">
  </a>
  <p align="center">
    Java FX GUI
    <br />
    <a href="https://github.com/TomassSimko/Private-Movie-Collection"><strong>Explore the docs »</strong></a>
    <br />
  </p>

# Problem

What is the best way to present the recommendations to the user?

# Solution

Create a GUI that shows recommendations to the user in a cool way.

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#">About The Project</a>
      <ul>
        <li><a href="#tech-stack">Tech stack</a></li>
        <li><a href="#style">Style</a></li>
      </ul>
    </li>
    <li><a href="#application-design">Application design</a></li>
    <li><a href="#features">Features</a></li>
    <li><a href="#style-guide">Style guide</a></li>
    <li><a href="#application-design">Application design</a></li>
    <li><a href="#application-design-patterns">Application design patterns</a></li>
    <li><a href="#licence">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## Tech stack

* [Java](https://www.java.com/en/)
* [Liberica 19](https://bell-sw.com/libericajdk/)
* [JavaFX](https://openjfx.io/)
* [Feign](https://github.com/OpenFeign/feign)
* [Logback-classic](https://logback.qos.ch/)

## Style

* [CSS](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference)
* [MaterialFX](https://github.com/palexdev/MaterialFX)

<!-- ABOUT THE PROJECT -->

## Features

- [x] Abstract controller
- [x] User authentication
    - [x] Basic login with user name and password
    - [x] User log out
    - [x] Simple validation for empty fields
    - [x] Complete error handling
    - [x] Enhanced user experience with multithreading
    - [x] Usage of CSS for UX enhancement
- [x] Movies dashboard
    - [x] Display of all movies for all 3 required methods
    - [x] Movies are constructed inside MFXScrollPane
    - [x] API calls to receive the images for movies
    - [x] Display of all movies with its title,year and rating
    - [x] Dynamic star rating display ★★★★☆
    - [x] Dynamically created parts of GUI
    - [x] Usage of CSS for UX enhancement (Scrollpanes)
        - [x] User is able to scroll left-to-right right-to-left in each movies recommendation to view amount that is
          displayed
        - [x] Hover effect on each movie that displays necessary informations
- [x] Multithreading in Java Fx
    - [x] Use of tasks,multithreading,tasks
    - [X] Not blocking the main JavaFx thread loading all needed files

Back end is based on <br>
https://github.com/jeppeml/MovieRecommendationSystem-CachedImplementation

## Style guide

**Typography**

* [Montserrat](https://fonts.google.com/specimen/Montserrat)

Key parts why I chose to use this popular sans-serif font

* _Legibility_
    * Clean and modern design
* _Character_
    * Distinctive character
* _Versatility_
    * Can be used in wide range of applications

**Color pallet**

* Primary color  [#0063e5]()
* Secondary color [#000000]()

**Effects**

* [Glassmorphism]()
* [Gaussian blue]()
* [DropShadow]()

**Components**

* [MaterialFx]()
* [JavaFxBasicComponents]()
* [Custom css]()

## Application design

Application consist of two FXML views

- login-view.fxml
- home-view.fxml

### Login page

![Login-page](https://user-images.githubusercontent.com/72190589/218592107-4080de78-063d-4d9b-a733-bb6bcccde2b7.png)

### Dashboard page

![Dashboard-page](https://user-images.githubusercontent.com/72190589/218592132-4e4758a6-89b5-4c07-a78a-2ed995acf889.png)

## Application design patterns

- [x] Singleton pattern (Currently logged user)
- [x] Facade design pattern

## Licence

Distributed under the MIT License. See LICENSE for more information.
I do not own any right to any assets used in this application and wont be further distributed

Made by @TomassSimko
2023 SDE cs project posted here on GitHub. <br>
Hope you will like it! <br>
Thank you for your attention!
TTT :black_nib:

## Contact

Tomas Simko - [@twitter](https://twitter.com/TomasSimko_)
-simko.t@email.cz - [@linkedIn](https://www.linkedin.com/in/tomas-simko/)

Project Link: [https://github.com/TomassSimko/gui-streaming-platform]()

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
