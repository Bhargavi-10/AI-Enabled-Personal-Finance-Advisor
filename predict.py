import joblib
import pandas as pd

expense_model = joblib.load("models/expense_model.pkl")
savings_model = joblib.load("models/savings_model.pkl")

def predict_expense(data):
    row = {
      "Income": float(data["income"]),
      "Savings": float(data["savings"]),
      "Month": int(data["month"])
    }
    df = pd.DataFrame([row])
    prediction = expense_model.predict(df)
    return prediction[0]

def predict_savings(data):
    row = {
      "Income": float(data["income"]),
      "Expense": float(data["expense"]),
      "Month": int(data["month"])
    }
    df = pd.DataFrame([row])
    prediction = savings_model.predict(df)
    return prediction[0]

