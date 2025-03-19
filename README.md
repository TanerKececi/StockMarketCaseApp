# StockMarketCaseApp

This project is an Android application developed to display and filter stock market data. Users can dynamically filter and visualize the data. The app fetches data through an API and presents it to the user based on selected filters.

## Features
- **Stock Data Display**: Displays stock market data fetched from an API.
- **Dynamic Filtering**: Users can filter data by selecting various criteria.
- **BottomSheetDialogFragment**: Options for selecting filters are shown in a BottomSheet dialog.
- **Responsive Design**: The app is designed to be responsive and adapt to different device sizes.

## Technologies
- **Kotlin**: The primary programming language used for application development.
- **Android SDK**: The development environment used for building Android applications.
- **Retrofit**: A library used for communication with the API.
- **DataBinding**: For binding UI components to data.
- **RecyclerView**: Used for displaying lists of items.
- **BottomSheetDialogFragment**: Visual component for displaying filter selection.

## Usage
1. Install the app on your Android device or emulator.
2. The main screen will display stock market data in a list.
3. Tap the button in the top right corner to open the filter selection panel.
4. Choose the filters as needed, and the filtered data will be displayed on the screen.

## Setup
To run the project locally, follow these steps:

1. Clone the repository:
   ```bash
   git clone https://github.com/TanerKececi/StockMarketCaseApp.git

2. Open local.properties file and enter necessary url such as "apiKey=YOUR_API_KEY". For privacy reasons it is not added in the project.
