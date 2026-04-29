# SmartQueue: AI Patient Triage System

## Overview

SmartQueue is an intelligent patient triage and queue management system that leverages machine learning to predict cardiovascular disease risk and automatically prioritize patients based on their health risk level. The system combines a **Java backend** for console-based operations with a **Python/Streamlit web interface** to provide real-time patient registration, symptom analysis, and intelligent queue management.

The system uses a trained Random Forest machine learning model to assess patient risk based on medical symptoms and health indicators, ensuring that high-risk patients are prioritized in the queue for timely medical attention.

## Features

- **Risk Prediction**: Machine learning-powered prediction of cardiovascular disease risk (Low, Medium, High)
- **Intelligent Queue Management**: Automatic prioritization of patients based on predicted risk level
- **Patient Registration**: Easy registration through web interface or console
- **Data Persistence**: Daily patient records stored in CSV files
- **Dual Interface**: Both web-based (Streamlit) and console-based (Java) interfaces
- **Follow-up Support**: Handles both first visits and follow-up consultations

## Technology Stack

### Backend (Java):
- Java (Object-Oriented Architecture)
- Priority Queue Data Structure for risk-based patient ordering

### Frontend & ML (Python):
- Python 3
- Streamlit (Web UI framework)
- scikit-learn (Machine Learning - Random Forest Classifier)
- pandas (Data manipulation)
- joblib (Model serialization)

### Data:
- UCI Heart Disease Dataset (`heart_disease_uci.csv`) - Training data
- Daily patient records (`records_YYYY-MM-DD.csv`) - Persistent storage

## Installation & Setup

### Prerequisites

1. **Python 3.7+** with pip
2. **Java JDK 8+** (for Java components)
3. Required Python packages (install via pip)

### Python Setup

1. Install required Python packages:
   ```bash
   pip install streamlit pandas scikit-learn joblib
   ```

2. Train the machine learning model:
   ```bash
   python train_model.py
   ```
   This will create `model_rf.pkl` file containing the trained Random Forest model.

### Java Setup

1. Ensure Java JDK is installed and `javac`/`java` are in your PATH
2. Compile the Java classes:
   ```bash
   javac *.java
   ```

## Usage

### Python Web Interface (Recommended)

1. Start the Streamlit web application:
   ```bash
   streamlit run app.py
   ```

2. Open your browser to the displayed URL (usually `http://localhost:8501`)

3. Use the interface to:
   - Register new patients with their medical details
   - View predicted risk levels
   - Manage patient queue based on risk priority
   - Handle follow-up consultations

### Java Console Interface

1. Run the Java application:
   ```bash
   java MainApp
   ```

2. Follow the prompts to:
   - Enter the number of patients
   - Input patient details (name, age, chest pain level, blood pressure, cholesterol)
   - View risk predictions and queue management

## Machine Learning Model

### Training Data
The model is trained on the UCI Heart Disease Dataset, which includes:
- Age, sex, chest pain type
- Resting blood pressure, cholesterol levels
- Exercise-induced angina, number of major vessels
- And other cardiac health indicators

### Model Details
- **Algorithm**: Random Forest Classifier
- **Parameters**:
  - 200 estimators (trees)
  - Maximum depth: 10 levels
  - Random state: 42 (for reproducibility)
- **Features Used**: age, sex, cp, trestbps, chol, fbs, restecg, thalach, exang, oldpeak, slope, ca, thal

### Risk Categories
- **Low Risk (0)**: No immediate attention required
- **Medium Risk (1)**: Monitor and follow-up recommended
- **High Risk (2)**: Urgent medical attention required

## Project Structure

```
MPJ_PROJECT/
├── app.py                 # Streamlit web application
├── train_model.py         # ML model training script
├── model_rf.pkl          # Trained Random Forest model (generated)
├── heart_disease_uci.csv # Training dataset
├── records_YYYY-MM-DD.csv # Daily patient records (generated)
├── MainApp.java          # Java console application entry point
├── Patient.java           # Patient data model
├── QueueManager.java      # Queue management logic
├── RiskPredictor.java     # Risk prediction interface
└── PROJECT_REPORT.md      # Detailed project documentation
```

## Data Flow

1. **Model Training**: `train_model.py` processes the UCI dataset and trains the Random Forest model
2. **Patient Input**: Patients enter details via web interface or console
3. **Risk Prediction**: ML model predicts risk level based on symptoms
4. **Queue Management**: Patients are added to priority queue based on risk
5. **Data Storage**: Patient records are saved to daily CSV files

## Contributing

To contribute to this project:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test both Python and Java components
5. Submit a pull request

## License

This project is developed for educational and demonstration purposes.

---

**Project Date:** April 28, 2026</content>
<parameter name="filePath">c:\Users\91789\OneDrive\Desktop\MPJ_PROJECT\README.md