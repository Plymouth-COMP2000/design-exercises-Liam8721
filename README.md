# COMP2000 - Restaurant Management Android Application

This repository contains the source code for a native Android application developed for the COMP2000 coursework assessment. The application serves as a mobile solution for a restaurant, providing distinct interfaces and functionalities for both **Guests** and **Staff**.

## Overview

The application is a dual-role system designed to streamline restaurant operations and enhance the customer experience.

*   **For Guests:** It offers a user-friendly way to browse the restaurant's menu, make table reservations, and manage their own bookings.
*   **For Staff:** It acts as a powerful management tool, allowing restaurant staff to perform CRUD (Create, Read, Update, Delete) operations on menu items and oversee all customer reservations.

The application is built using Java in Android Studio and features a hybrid data model: it interacts with a central RESTful API for user authentication and utilizes a local SQLite database for all operational data (menu and bookings), ensuring offline availability and performance.

## Core Features

### Guest Features
- **User Authentication:** Secure sign-up and login.
- **Browse Menu:** View all menu items, complete with images, descriptions, and prices, organized by category.
- **Make Reservations:** Select a date, time, and party size to book a table.
- **View My Bookings:** See a list of all personal upcoming reservations with the option to cancel or modify them.
- **Notification Control:** Enable or disable notifications for booking confirmations and reminders.

### Staff Features
- **Secure Staff Login:** Role-based login that directs staff to a dedicated management dashboard.
- **Full Menu Management (CRUD):**
    - **Create:** Add new items and categories to the menu.
    - **Read:** View all existing menu items.
    - **Update:** Edit the details (name, price, image) of any menu item.
    - **Delete:** Remove items from the menu.
- **Full Reservation Management (CRUD):**
    - **Create:** Manually add a booking for a customer.
    - **Read:** View a list of all reservations from all customers.
    - **Update:** Modify the details of any booking.
    - **Delete:** Cancel any customer's reservation.
- **Notification Control:** Manage personal notification preferences.

## Technical Details & Architecture

- **Language:** **Java**
- **IDE:** **Android Studio**
- **Architecture:**
    - UI Layer built with **Android Fragments** and **XML Layouts**.
    - **Android Navigation Component** used for managing all in-app navigation and the back stack.
    - **RecyclerView** with the **Adapter Pattern** used extensively for displaying dynamic lists (menu items, bookings).
- **Data Management:**
    - **Local Database:** **SQLite** is used for all menu and booking data, managed via custom `SQLiteOpenHelper` classes (`MenuDBHelper`, `BookingDBHelper`).
    - **Central API:** User authentication is handled by a **RESTful API**.
    - **Networking:** The **Volley library** is used to manage all network requests on background threads, ensuring the UI remains responsive. The **Singleton Pattern** (`VolleySingleton`) is implemented for efficient request queue management.
- **Design Principles:**
    - The project was developed following **SOLID** principles to ensure the code is maintainable, scalable, and robust.
    - Key design patterns like **Singleton**, **Adapter**, and a **Model-View-Controller (MVC)-style** architecture were employed.

## How to Run

1.  Clone the repository to your local machine:
    ```bash
    git clone https://github.com/Plymouth-COMP2000/design-exercises-Liam8721.git
    ```
2.  Open the project in Android Studio.
3.  Let Gradle sync and build the project.
4.  Run the application on an Android emulator or a physical device.

## Third-Party Resources

- **[Volley Library](https://developer.android.com/training/volley):** Used for managing network requests to the RESTful API.
- **[Material Design Components](https://material.io/develop/android):** Used for modern UI elements like `TextInputLayout` and `Buttons`.

*(Add any other libraries or resources you used here, for example, an image loading library like Glide or Picasso if you used one.)*
