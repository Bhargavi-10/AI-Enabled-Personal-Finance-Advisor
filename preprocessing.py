import pandas as pd;
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import LabelEncoder

def preprocess() : 
    df = pd.read_csv("dataset/finance.csv")
    
    #total monthly expense
    df["Expense"] = (
        df["Groceries"]
        + df["Transport"]
        + df["Eating_Out"]
        + df["Entertainment"]
        + df["Utilities"]
        + df["Healthcare"]
        + df["Education"]
        + df["Miscellaneous"]
        )
    
    
    #savings
    df["Savings"] = df["Desired_Savings"]
    
    #month
    df["Month"] = 1
    
    return df

#===================
#Expense dataset
#===================

def load_expense_data():
    df = preprocess()
    x = df[["Income","Savings" , "Month"]]
    y = df["Expense"]
    return train_test_split(x , y , test_size=0.2 , random_state=42)

#=====================
#Savings dataset
#=====================

def load_savings_data():
    df = preprocess()
    x = df[["Income" , "Expense" , "Month"]]
    y = df["Savings"]
    return train_test_split(x,y,test_size=0.2,random_state=42)