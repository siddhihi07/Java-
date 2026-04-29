# SmartQueue: AI Patient Triage System - Project Report

**Project Date:** April 28, 2026

---

## 1. Project Overview

**SmartQueue** is an intelligent patient triage and queue management system that leverages machine learning to predict cardiovascular disease risk and automatically prioritize patients based on their health risk level. The system combines a **Java backend** with a **Python/Streamlit web interface** to provide real-time patient registration, symptom analysis, and intelligent queue management.

### Key Objectives:
- Predict patient risk level based on medical symptoms and health indicators
- Automatically prioritize patients in queue based on predicted risk
- Maintain patient history and medical records
- Provide an intuitive interface for patient registration and diagnosis
- Support follow-up consultations

---

## 2. Technology Stack

### Backend:
- **Java** (Object-Oriented Architecture)
- **Priority Queue Data Structure** (Risk-based patient ordering)

### Frontend & ML:
- **Python 3**
- **Streamlit** (Web UI framework)
- **scikit-learn** (Machine Learning)
- **pandas** (Data manipulation)
- **joblib** (Model serialization)

### Data:
- **heart_disease_uci.csv** (UCI Heart Disease Dataset - Training data)
- **records_YYYY-MM-DD.csv** (Daily patient records - Persistent storage)

---

## 3. System Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     SmartQueue System                        │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────────┐          ┌──────────────────┐          │
│  │   Python Layer   │          │    Java Layer    │          │
│  ├──────────────────┤          ├──────────────────┤          │
│  │ • Streamlit UI   │          │ • MainApp        │          │
│  │ • ML Model       │          │ • QueueManager   │          │
│  │ • Data Storage   │          │ • RiskPredictor  │          │
│  │                  │          │ • Patient        │          │
│  └──────────────────┘          └──────────────────┘          │
│           │                              │                    │
│           └──────────────────────────────┘                    │
│                      │                                         │
│                      ▼                                         │
│           ┌──────────────────┐                               │
│           │  ML Model (.pkl) │                               │
│           │  Random Forest   │                               │
│           └──────────────────┘                               │
│                      │                                         │
│                      ▼                                         │
│         ┌──────────────────────────┐                         │
│         │   Patient Database &      │                         │
│         │   Daily Records (CSV)     │                         │
│         └──────────────────────────┘                         │
└─────────────────────────────────────────────────────────────┘
```

---

## 4. Machine Learning Implementation

### 4.1 Model Training Pipeline

**File:** `train_model.py`

#### Data Source:
- **UCI Heart Disease Dataset** (`heart_disease_uci.csv`)
- Comprehensive cardiac health indicators with disease presence labels

#### Machine Learning Model:
- **Algorithm:** Random Forest Classifier
- **Parameters:**
  - Number of estimators: 200 trees
  - Max depth: 10 levels
  - Random state: 42 (reproducibility)

#### Data Processing Steps:

1. **Data Cleaning:**
   - Remove null/missing values
   - Ensure data integrity

2. **Feature Encoding:**
   - `sex`: Male (1) / Female (0)
   - `cp` (Chest Pain): Category encoding (0-3)
   - `exang` (Exercise Angina): Boolean to int
   - `ca` (Vessels): Fill missing values with 0
   - `thal` (Thalassemia): Category encoding (0-2)

3. **Feature Set (11 features):**
   - `age` - Patient age
   - `sex` - Gender (encoded)
   - `cp` - Chest pain type (encoded)
   - `trestbps` - Resting blood pressure
   - `chol` - Serum cholesterol
   - `fbs` - Fasting blood sugar > 120 mg/dl
   - `thalch` - Maximum heart rate achieved
   - `exang` - Exercise induced angina (encoded)
   - `oldpeak` - ST depression
   - `ca` - Number of major vessels (0-4)
   - `thal` - Thalassemia type (encoded)

4. **Risk Classification:**
   - **Low Risk** (0): No presence of disease
   - **Medium Risk** (1): Mild presence of disease
   - **High Risk** (2): Significant presence of disease

5. **Train-Test Split:**
   - 80% training data
   - 20% test data
   - Random state: 42 for reproducibility

#### Model Output:
- Trained model serialized to `model_rf.pkl` using joblib
- Ready for prediction in production

---

### 4.2 ML Model Integration in Web Application

**File:** `app.py` (Streamlit Application)

#### Model Loading:
```python
model = joblib.load("model_rf.pkl")
```

#### Real-time Risk Prediction:
1. **Input Collection:**
   - Gathers 11 health indicators matching training features
   - User enters symptom and health data through Streamlit UI

2. **Data Encoding:**
   - Encodes categorical inputs (chest pain type, thalassemia, etc.)
   - Matches exact encoding used during training

3. **Feature Alignment:**
   - Reorders features to match model's expected input order
   - Uses `model.feature_names_in_` for consistency

4. **Prediction:**
   ```python
   pred = model.predict(input_data)[0]
   # Returns: 0 (Low), 1 (Medium), or 2 (High)
   ```

5. **Queue Management:**
   - High-risk patients prioritized in queue
   - Enables intelligent patient scheduling

---

### 4.3 Prediction Workflow

```
Patient Data Input (11 features)
            ↓
    Feature Encoding
            ↓
    Feature Alignment
            ↓
    Random Forest Model
            ↓
    Risk Score (0, 1, or 2)
            ↓
    Queue Priority Assignment
            ↓
    Patient Served Based on Risk
```

---

## 5. Core Components

### 5.1 Java Backend Components

#### **Patient.java**
- Encapsulates patient information
- Attributes: name, age, chest pain level, blood pressure, cholesterol
- Provides getter methods for accessing patient data

#### **QueueManager.java**
- Manages patient queue using **Priority Queue** data structure
- Sorts patients by risk score (high-risk first)
- Methods:
  - `addPatient()` - Add patient to priority queue
  - `getNextPatient()` - Retrieve highest priority patient

#### **RiskPredictor.java**
- Rule-based risk assessment (used as backup/comparison)
- Scoring system:
  - +2 points: Chest pain >= 2
  - +2 points: Age > 50
  - +2 points: BP > 140
  - +2 points: Cholesterol > 240
  - Score >= 6: High Risk
  - Score >= 3: Medium Risk
  - Score < 3: Low Risk

#### **MainApp.java**
- Command-line interface entry point
- Processes multiple patients with manual input
- Demonstrates Java-based triage system

---

### 5.2 Python/Streamlit Frontend

**File:** `app.py`

#### Features:

1. **Patient Registration (First Visit)**
   - Collect: Name, Age, Phone Number
   - Generate unique 8-character Patient ID
   - Store in session state database

2. **Patient Lookup (Second Consultation)**
   - Search existing patients by phone number
   - Retrieve consultation history
   - Display previous medical records

3. **Symptom Collection & ML Prediction**
   - Input form with 11 health indicators
   - Real-time ML prediction
   - Display predicted risk level
   - Add patient to queue

4. **Queue Management**
   - Display current patient queue
   - Input diagnosis for current patient
   - Serve patient (pop from queue, record diagnosis)

5. **Record Persistence**
   - Daily CSV storage (`records_YYYY-MM-DD.csv`)
   - Tracks all patient interactions
   - Searchable patient history

---

## 6. Data Flow

### First Visit Workflow:
```
1. User selects "First Visit"
   ↓
2. Enter: Name, Age, Phone
   ↓
3. System generates unique Patient ID
   ↓
4. Patient stored in database
   ↓
5. Enter 11 health indicators
   ↓
6. ML model predicts risk
   ↓
7. Patient added to queue with priority
   ↓
8. Serve patient → Save diagnosis & record
```

### Second Consultation Workflow:
```
1. User selects "Second Consultation"
   ↓
2. Enter Phone Number
   ↓
3. System retrieves patient from database
   ↓
4. Display previous consultation history
   ↓
5. New symptom input & ML prediction
   ↓
6. Add to queue with updated risk assessment
```

---

## 7. Machine Learning Key Metrics

### Model Characteristics:
- **Supervised Learning:** Classification task
- **Target Variable:** 3-class risk prediction (Low, Medium, High)
- **Features:** 11 quantitative and encoded categorical features
- **Training Data:** UCI Heart Disease Dataset

### Model Advantages:
- **Interpretability:** Random Forest provides feature importance insights
- **Robustness:** Handles both numeric and categorical features
- **Performance:** Ensemble method reduces overfitting risk
- **Speed:** Fast inference for real-time predictions

### Expected Performance:
- Provides baseline cardiovascular disease risk assessment
- Supports clinical triage decision-making
- **Note:** Not a replacement for professional medical diagnosis

---

## 8. Data Storage

### Training Data:
- **heart_disease_uci.csv** - UCI Heart Disease Dataset
  - Medical features from patient cohorts
  - Disease presence indicator
  - Used for model training

### Runtime Records:
- **records_YYYY-MM-DD.csv** - Daily patient records
  - Patient ID, Name, Phone, Age
  - ML-predicted Risk Level
  - Consultation Date
  - Diagnosis entered by medical professional
  - Updated daily

### Session Memory:
- Patient database stored in Streamlit session state
- Persistent across single session
- Reset on application restart

---

## 9. How Machine Learning is Being Used

### 1. **Intelligent Risk Assessment**
   - ML model predicts cardiovascular disease risk based on 11 clinical features
   - Replaces static rule-based scoring (shown in `RiskPredictor.java`)
   - Provides probabilistic risk classification

### 2. **Automated Patient Prioritization**
   - Queue sorting based on ML-predicted risk scores
   - High-risk patients automatically prioritized for consultation
   - Optimizes clinical resource allocation

### 3. **Clinical Decision Support**
   - Assists medical professionals in triage decisions
   - Provides data-driven risk stratification
   - Enables evidence-based patient scheduling

### 4. **Historical Pattern Recognition**
   - Patient records stored for trend analysis
   - Enables future ML enhancements with patient history
   - Foundation for predictive analytics

### 5. **Model Scalability**
   - Trained model encapsulated in `model_rf.pkl`
   - Can handle batch predictions if needed
   - Easy model retraining with new data

---

## 10. File Inventory

| File | Type | Purpose |
|------|------|---------|
| `app.py` | Python | Streamlit web application with ML integration |
| `train_model.py` | Python | ML model training pipeline |
| `MainApp.java` | Java | Command-line application entry point |
| `Patient.java` | Java | Patient data model |
| `QueueManager.java` | Java | Priority queue management |
| `RiskPredictor.java` | Java | Rule-based backup risk predictor |
| `heart_disease_uci.csv` | Data | UCI Heart Disease training dataset |
| `records_2026-04-28.csv` | Data | Daily patient records (generated) |
| `model_rf.pkl` | Model | Serialized trained Random Forest model |

---

## 11. Workflow Summary

```
┌─────────────────────────────────────────────────────────────┐
│                    SmartQueue Workflow                       │
└─────────────────────────────────────────────────────────────┘

Step 1: Train ML Model
   └─→ train_model.py
       • Load heart_disease_uci.csv
       • Clean & encode features
       • Train Random Forest (200 estimators)
       • Save model_rf.pkl

Step 2: Patient Registration (Streamlit UI)
   └─→ app.py (First Visit)
       • Collect basic patient info
       • Generate unique ID
       • Store in database

Step 3: Symptom Collection & ML Prediction
   └─→ app.py (Symptoms Form)
       • Input 11 health indicators
       • Load model_rf.pkl
       • Encode input data
       • ML model predicts risk (0, 1, or 2)
       • Display risk level

Step 4: Queue Management
   └─→ QueueManager (Java) / Session (Streamlit)
       • Sort by predicted risk
       • High-risk prioritized
       • Serve patient in order

Step 5: Record & Persistence
   └─→ records_YYYY-MM-DD.csv
       • Store patient visit
       • Save diagnosis
       • Track historical data

Step 6: Follow-up Consultation
   └─→ app.py (Second Consultation)
       • Lookup by phone number
       • View history
       • Repeat risk prediction with new data
```

---

## 12. Key Insights

### ML Usage Highlights:
1. **Dual Architecture:** Combines Java-based rule engine with Python ML model
2. **Real-time Predictions:** Sub-second risk assessment per patient
3. **Data-Driven:** Moves beyond hardcoded rules to ML-based decision making
4. **Production Ready:** Uses joblib for model serialization and deployment
5. **Extensible:** Foundation for future enhancements (ensemble models, deep learning)

### Clinical Benefits:
- **Faster Triage:** Automated risk assessment speeds up patient evaluation
- **Consistent Results:** ML model applies uniform criteria across all patients
- **Resource Optimization:** Prioritizes high-risk patients for faster care
- **Audit Trail:** Records stored for medical review and compliance

### Future Enhancement Opportunities:
- Add more clinical features to improve model accuracy
- Implement patient outcome tracking for model validation
- Develop personalized risk factors per demographic group
- Create continuous learning pipeline to retrain model
- Build API layer for hospital system integration
- Add explainability features to show contributing factors

---

## 13. Conclusion

**SmartQueue** is a comprehensive healthcare system that demonstrates effective integration of machine learning into clinical workflows. By combining a Random Forest classifier trained on cardiovascular disease data with an intuitive web interface, the system automates patient risk assessment and prioritization. The architecture supports both web-based (Python/Streamlit) and command-line (Java) usage patterns, providing flexibility for different deployment scenarios.

The machine learning model serves as the core decision-making engine, enabling data-driven triage that moves beyond static rule-based approaches. With proper clinical validation and ongoing model refinement, this system has potential for real-world healthcare applications.

---

**Report Generated:** April 28, 2026
**Project Type:** Healthcare AI Application
**ML Model:** Random Forest Classifier (200 estimators, max depth 10)
**Status:** Development Complete
