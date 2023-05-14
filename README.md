# Simple CRUD Note Android App

## Introduction

This application is a simple CRUD Note application built using Kotlin and Jetpack Compose following Clean Architecture and MVVM design patterns. It allows users to create, read, update, and delete notes.

The UI/UX design of this application is inspired by the GoodNotes Collaborative Notes Mobile App designed by Ngoc Nguyen on Dribbble. The design can be found here: https://dribbble.com/shots/20017643-GoodNotes-Collaborative-Notes-Mobile-App

## Architecture

This application follows the Clean Architecture pattern. It is divided into three main layers:

1. Presentation Layer
2. Domain Layer
3. Data Layer

### Presentation Layer

The Presentation Layer is responsible for displaying data to the user and receiving input from them. In this application, the Presentation Layer is implemented using Jetpack Compose. 

### Domain Layer

The Domain Layer is responsible for implementing the business logic of the application. It contains use cases that define the actions that can be performed in the application. 

### Data Layer

The Data Layer is responsible for providing data to the application. In this application, the Data Layer is implemented using Room, a SQLite database library for Android.

## Design Pattern

This application follows the MVVM (Model-View-ViewModel) design pattern. This pattern separates the user interface (View) from the business logic (ViewModel) and the data (Model).

## Features

This application has the following features:

- Create a new note
- Read an existing note
- Update an existing note
- Delete an existing note

## Dependencies

This application uses the following dependencies:

- Compose
- Coroutines
- Dagger - Hilt
- Room
- Kotlin Extensions and Coroutines support for Room
