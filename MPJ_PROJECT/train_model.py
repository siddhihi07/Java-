# =========================================
# SmartQueue ML Model (FINAL VERSION)
# =========================================

import pandas as pd
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score, classification_report
import joblib

# ------------------------------
# LOAD DATA
# ------------------------------
df = pd.read_csv("heart_disease_uci.csv")

print("Columns:", df.columns)

# ------------------------------
# CLEAN DATA
# ------------------------------
df = df.dropna()

# ------------------------------
# ENCODE DATA
# ------------------------------

# Gender encoding
df['sex'] = df['sex'].map({'Male': 1, 'Female': 0})

# Chest pain encoding
df['cp'] = df['cp'].astype('category').cat.codes

# Boolean encoding
df['exang'] = df['exang'].astype(int)

# Handle missing in 'ca'
df['ca'] = df['ca'].fillna(0)

# Encode 'thal'
df['thal'] = df['thal'].astype('category').cat.codes

# ------------------------------
# FEATURE SELECTION (IMPROVED)
# ------------------------------
features = [
    'age',
    'sex',
    'cp',
    'trestbps',
    'chol',
    'fbs',
    'thalch',
    'exang',
    'oldpeak',
    'ca',
    'thal'
]

X = df[features]

# ------------------------------
# TARGET VARIABLE
# ------------------------------
y = df['num']

def convert_risk(val):
    if val == 0:
        return 0   # Low Risk
    elif val >= 2:
        return 2   # High Risk
    else:
        return 1   # Medium Risk

y = y.apply(convert_risk)

# ------------------------------
# TRAIN TEST SPLIT
# ------------------------------
X_train, X_test, y_train, y_test = train_test_split(
    X, y, test_size=0.2, random_state=42
)

# ------------------------------
# TRAIN MODEL
# ------------------------------
model = RandomForestClassifier(
    n_estimators=200,
    max_depth=10,
    random_state=42
)

model.fit(X_train, y_train)

# ------------------------------
# EVALUATION
# ------------------------------
y_pred = model.predict(X_test)

print("\nAccuracy:", accuracy_score(y_test, y_pred))
print("\nClassification Report:")
print(classification_report(y_test, y_pred))

# ------------------------------
# SAVE MODEL
# ------------------------------
joblib.dump(model, "model_rf.pkl")

print("\nModel saved as model_rf.pkl")