from preprocessing import load_savings_data
from sklearn.ensemble import RandomForestRegressor

from sklearn.metrics import mean_absolute_error

import joblib

x_train, x_test , y_train , y_test = load_savings_data()
model = RandomForestRegressor(n_estimators=100,random_state=42)
model.fit(x_train,y_train)
prediction = model.predict(x_test)
print("savings MAE : ",mean_absolute_error(y_test,prediction))
joblib.dump(model,"models/savings_model.pkl")
print("Savings model saved")

