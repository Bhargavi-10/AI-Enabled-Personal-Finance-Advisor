from flask import Flask,request,jsonify
from predict import predict_expense , predict_savings

app = Flask(__name__)

@app.route("/")
def home():
    return "Finance Advisor ML API running"

#EXPENSE PREDICTION
@app.route("/predictExpense",methods=["POST"])
def expense():
    data = request.get_json()
    prediction = predict_expense(data)
    return jsonify({
        "predictedExpense" : float(prediction)
        })


#savings prediction
@app.route("/predictSavings",methods=["POST"])
def savings():
    data = request.get_json()
    prediction = predict_savings(data)
    return jsonify({
        "predictedSavings" : float(prediction)
        })


#RUN APP
if __name__=="__main__":
    app.run()
    print("app is running")