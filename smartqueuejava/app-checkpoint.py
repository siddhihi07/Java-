import streamlit as st
import pandas as pd
import numpy as np
import joblib

# Load the trained model
model = joblib.load("model_rf_clean.pkl")

# Define feature groups
symptom_groups = {
    "🫀 Cardiovascular Symptoms": [
        'chest pain', 'radiation into arms nack and jaw',
        'missing in heartbeat rythom/abnormal rythom',
        'low blood presure', 'heart rate normal and faster'
    ],
    "🫁 Respiratory Symptoms": [
        'shortest of breath', 'abnormal breathing',
        'need pellow or prefer to sleep in chair', 'congestion or burning'
    ],
    "🧠 Neurological Symptoms": [
        'trobale with balance', 'syncopal attack', 'debilitation',
        'losing flat cause', 'decrease of sterngth'
    ],
    "🩺 Other Clinical Indicators": [
        'trouble with sawllowing', 'fever', 'clubbing', 'rash'
    ],
    "📋 Medical History / Lifestyle": [
        'smocking', 'hypertension', 'hyperchoies terolamia', 'myocandial'
    ],
    "🧍 Basic Info": [
        'age', 'sex', 'heaviness or tightness'
    ]
}

# Flatten features
all_features = [feat for group in symptom_groups.values() for feat in group]

# Page setup
st.set_page_config(page_title="SmartQueue Risk Predictor", layout="centered")
st.title("🧠 SmartQueue: AI-Powered Patient Triage System")

st.markdown("This app predicts the **health risk level** of a patient based on symptoms to help prioritize OPD cases.")

# Dummy data generator
if st.button("🔁 Generate Dummy Patient"):
    st.session_state.generated_input = {feat: np.random.randint(0, 4) for feat in all_features}
else:
    st.session_state.generated_input = st.session_state.get("generated_input", {feat: 1 for feat in all_features})

# Form
with st.form("symptom_form"):
    st.subheader("📋 Enter Patient Symptoms")
    input_data = {}
    for group_title, group_features in symptom_groups.items():
        with st.expander(group_title, expanded=False):
            for feature in group_features:
                input_data[feature] = st.slider(
                    feature.replace("_", " ").capitalize(),
                    0, 3, int(st.session_state.generated_input.get(feature, 1))
                )
    submitted = st.form_submit_button("🚨 Predict Patient Risk")

# Label mapping
def map_risk_label(value):
    return ["🟢 Low Risk", "🟡 Medium Risk", "🔴 High Risk"][value]

# On submit
if submitted:
    df_input = pd.DataFrame([input_data])
    
# Ensure column order matches training
df_input = pd.DataFrame([input_data])
df_input = df_input[model.feature_names_in_]  

prediction = model.predict(df_input)[0]
risk_label = map_risk_label(prediction)

st.subheader("📊 Prediction Result")
st.metric("Predicted Risk Level", risk_label)
st.progress((prediction + 1) / 3)
st.balloons()

if st.checkbox("🔍 Show Patient Input Summary"):
   st.dataframe(df_input.T, use_container_width=True)
