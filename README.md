![screenshot](/src/main/resources/screenshot.png)

## Summary
Runs a webserver containg Mancala the game which is able to be played by two players. Take a look at the [example video](https://github.com/nmazaheri/MancalaWebGame/raw/master/src/main/resources/testPlay.m4v)

## Playing the game
1. mvn spring-boot:run
2. Open http://localhost:8080/ in your web browser

## Features
- JUnit tests located [here](/src/test/java/sample/mancala)
- Game prevents users from making illegal moves
- Score is overlayed on top of gameboard
- Simple design which uses Spring boot for all webserver configuration

### Frontend
Uses CSS, JSP and JSTL to deliver a single page containing the gameboard as seperate clickable [images](/src/main/webapp/resources/images).
### Backend
Uses Spring Boot and Spring MVC to create a java webserver. Stores [GameState](/src/main/java/sample/mancala/model/GameState.java) in HTTP session for security reasons, if the JSESSIONID cookie or server session is wiped then a new session will be established causing the game to reset.
