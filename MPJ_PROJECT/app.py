import streamlit as st
import pandas as pd
import joblib
import uuid
from datetime import date
import os

# ------------------------------
# LOAD MODEL
# ------------------------------
model = joblib.load("model_rf.pkl")

# ------------------------------
# STORAGE FILE (DAILY)
# ------------------------------
file_name = f"records_{date.today()}.csv"

if os.path.exists(file_name):
    stored_df = pd.read_csv(file_name)
else:
    stored_df = pd.DataFrame()

def save_record(record):
    global stored_df
    new_df = pd.DataFrame([record])
    stored_df = pd.concat([stored_df, new_df], ignore_index=True)
    stored_df.to_csv(file_name, index=False)

# ------------------------------
# SESSION STATE
# ------------------------------
if "database" not in st.session_state:
    st.session_state.database = []

if "queue" not in st.session_state:
    st.session_state.queue = []

# ------------------------------
# UI
# ------------------------------
st.title("🧠 SmartQueue: AI Patient Triage System")

visit = st.radio("Visit Type", ["First Visit", "Second Consultation"])

# ------------------------------
# FIRST VISIT
# ------------------------------
if visit == "First Visit":

    st.subheader("Register Patient")

    name = st.text_input("Name")
    age = st.number_input("Age", 1, 100)
    phone = st.text_input("Phone Number")

    if st.button("Register"):
        pid = str(uuid.uuid4())[:8]

        patient = {
            "Patient ID": pid,
            "Name": name,
            "Age": age,
            "Phone": phone
        }

        st.session_state.database.append(patient)
        st.session_state.current_patient = patient

        st.success(f"Registered. Patient ID: {pid}")

# ------------------------------
# SECOND CONSULTATION
# ------------------------------
if visit == "Second Consultation":

    phone = st.text_input("Enter Phone Number")

    if st.button("Search"):

        found = None
        for p in st.session_state.database:
            if p["Phone"] == phone:
                found = p
                break

        if found:
            st.session_state.current_patient = found
            st.success("Patient Found")

            history = stored_df[stored_df["Phone"] == phone]
            if not history.empty:
                st.subheader("Previous Records")
                st.dataframe(history)
        else:
            st.error("Patient not found")

# ------------------------------
# SYMPTOMS + ML
# ------------------------------
if "current_patient" in st.session_state:

    st.subheader("Enter Symptoms")

    # Inputs mapped to model features
    cp = st.selectbox("Chest Pain Type", ["typical", "atypical", "non-anginal", "asymptomatic"])
    trestbps = st.number_input("Resting BP", 80, 200, 120)
    chol = st.number_input("Cholesterol", 100, 400, 200)
    fbs = st.selectbox("Fasting Blood Sugar > 120?", ["No", "Yes"])
    thalch = st.number_input("Max Heart Rate", 60, 200, 120)
    exang = st.selectbox("Exercise Induced Angina", ["No", "Yes"])
    oldpeak = st.number_input("Oldpeak", 0.0, 10.0, 1.0)
    ca = st.number_input("Number of Major Vessels", 0, 4, 0)
    thal = st.selectbox("Thal", ["normal", "fixed defect", "reversible defect"])
    sex = st.selectbox("Sex", ["Male", "Female"])

    if st.button("Predict & Add to Queue"):

        p = st.session_state.current_patient

        # Encode inputs SAME as training
        cp_val = ["typical", "atypical", "non-anginal", "asymptomatic"].index(cp)
        fbs_val = 1 if fbs == "Yes" else 0
        exang_val = 1 if exang == "Yes" else 0
        sex_val = 1 if sex == "Male" else 0
        thal_val = ["normal", "fixed defect", "reversible defect"].index(thal)

        input_data = pd.DataFrame([{
            "age": p["Age"],
            "sex": sex_val,
            "cp": cp_val,
            "trestbps": trestbps,
            "chol": chol,
            "fbs": fbs_val,
            "thalch": thalch,
            "exang": exang_val,
            "oldpeak": oldpeak,
            "ca": ca,
            "thal": thal_val
        }])

        input_data = input_data[model.feature_names_in_]

        pred = model.predict(input_data)[0]

        risk_map = ["Low", "Medium", "High"]
        risk = risk_map[pred]

        record = {
            "Patient ID": p["Patient ID"],
            "Name": p["Name"],
            "Phone": p["Phone"],
            "Age": p["Age"],
            "Risk": risk,
            "Date": str(date.today()),
            "Diagnosis": ""
        }

        st.session_state.queue.append(record)
        st.success(f"Added to Queue → {risk} Risk")

# ------------------------------
# QUEUE
# ------------------------------
st.subheader("Patient Queue")

if st.session_state.queue:

    df = pd.DataFrame(st.session_state.queue)
    st.dataframe(df)

    diagnosis = st.text_area("Enter Diagnosis")

    if st.button("Serve Patient"):

        patient = st.session_state.queue.pop(0)
        patient["Diagnosis"] = diagnosis

        save_record(patient)

        st.success("Patient served & stored")

# ------------------------------
# ALL RECORDS
# ------------------------------
st.subheader("All Records")

if not stored_df.empty:
    st.dataframe(stored_df)