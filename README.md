# JavaFX Weather Application 

A comprehensive desktop weather application built with JavaFX. This project demonstrates the integration of RESTful APIs, geolocation services, and dynamic UI updates within a Java environment. 

The core theme of this project was the **learning journey**, understanding how to fetch asynchronous data, manage application state, and handle real-world API challenges.

This project was done for academic purposes for course CSE 4402- Visual Programming at Islamic University of Technology (IUT)

---

## 📸 Features

* 🌡️ **Real-Time Data:** Live temperature, "feels like", humidity, AQI, & local time.
* 🎨 **Dynamic Visuals:** UI adapts to conditions (Sunny ☀️, Rainy 🌧️, Snowy ❄️).
* 📅 **Forecast & History:** 3-day future forecast + 7-day past history.
* 📍 **Geolocation:** Auto-detects user location for instant updates.
* 🌍 **Global Search:** Lookup weather for any city worldwide.
* 🌗 **Dark/Light Mode:** Toggle themes for visual preference.

---

## 🛠 Tech Stack

* **Language:** Java (JDK 21)
* **Framework:** JavaFX
* **UI Design:** Scenebuilder + CSS for styling
* **IDE:** Eclipse
* **External APIs:**
    * [WeatherAPI.com](https://www.weatherapi.com) (Weather data)
    * [OpenCage Data](https://opencagedata.com/api) (Geolocation)

---

## 🚀 Installation & Setup

To run this project locally, you will need to generate your own free API keys and configure the application.

### 1. Clone the Repository

`git clone https://github.com/Loona6/Weather-App.git`

### 2. Import into Eclipse

1. Open Eclipse.
2. Go to `File > Open Projects from File System...`.
3. Select the cloned folder.

### 3. Configure API Keys (Important!)

For security reasons, the API keys are not valid in this public repository. You must insert your own.

1. Get a free API Key from [WeatherAPI](https://www.weatherapi.com).
2. Get a free API Key from [OpenCage Data](https://opencagedata.com/).
3. Open the file: `src/application/MainController.java` (or your specific package path).
4. Locate the variables for the keys and replace them:

### 4. Run the Application

Run `Main.java` as a Java Application.

---

## 🧠 The Learning Journey

Through this project I learned **API Integration** This way I got to know how to construct HTTP requests and parse JSON responses to extract meaningful weather data.

---

## 🔮 Future Improvements

- 💅 **UI Polish:** Enhance the interface for a more visually pleasing experience.
- 🔐 **Key Security:** Move API keys to environment variables/config files.
- 📈 **Graphing:** Add visual charts for 7-day temperature trends.
