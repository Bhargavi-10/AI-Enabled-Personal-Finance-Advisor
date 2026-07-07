const BASE_URL = "http://localhost:8080/visualizations"
const userId = localStorage.getItem("userId");

if(!userId){
    alert("please login");
    window.location.href="login.html";
}

//============================
//MONTHLY SPENDING
//============================
async function loadMonthlySpending(){
    const response = await fetch(`${BASE_URL}/monthlySpending/${userId}`);
    const data = await response.json();
    const labels = data.map(item=>"Month "+item.month);
    const values = data.map(item=>item.amount);
    const ctx = document.getElementById("monthlySpendingChart")
    new Chart(
        ctx,
        {
            type:"line",
            data:{
                labels:labels,
                datasets:[{
                        label: "Monthly Spending",
                        data: values,
                        borderColor: "#2563EB",
                        backgroundColor: "rgba(37, 99, 235, 0.2)",
                        pointBackgroundColor: "#2563EB",
                        pointBorderColor: "#1D4ED8",
                        pointRadius: 5,
                        tension: 0.4,
                        fill: true
                }]
                
            },

            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
        }
    );
}

//=================================
//DAILY SPENDING
//=================================
async function loadDailySpending(){

    const response =
    await fetch(`${BASE_URL}/dailySpending/${userId}`);

    const data = await response.json();

    const labels =
    data.map(item => item.date);

    const values =
    data.map(item => item.amount);
    ctx =  document.getElementById("dailySpendingChart")
    new Chart(
       ctx,
        {
            type:"line",

            data:{
                labels:labels,

                datasets:[
                    {
                        label: "Daily Spending",
                        data: values,
                        borderColor: "#EF4444",
                        backgroundColor: "rgba(239, 68, 68, 0.2)",
                        pointBackgroundColor: "#DC2626",
                        pointRadius: 4,
                        tension: 0.4,
                        fill: true
                    }
                ]
            },
                        options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
        }
    );
}
//==================================
// CATEGORY SPENDING
//==================================

async function loadCategorySpending(){

    const response =
    await fetch(`${BASE_URL}/categorySpending/${userId}`);

    const data = await response.json();

    const labels =
    data.map(item =>
        item.category + " M" + item.month
    );

    const values =
    data.map(item => item.amount);
    ctx =document.getElementById("categorySpendingChart") 
    new Chart(
        ctx,
        {
            type:"bar",

            data:{
                labels:labels,

                datasets:[
                    {
                    label: "Category Spending",
                    data: values,
                    backgroundColor: [
                        "#3B82F6",
                        "#10B981",
                        "#F59E0B",
                        "#EF4444",
                        "#8B5CF6",
                        "#14B8A6",
                        "#EC4899",
                        "#6366F1",
                        "#F97316",
                        "#84CC16"
                    ],
                    borderRadius: 8
                    }
                ]
            },
                        options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
        }
    );
}


//==================================
// MONTHLY SAVINGS
//==================================

async function loadMonthlySavings(){

    const response =
    await fetch(`${BASE_URL}/monthlySavings/${userId}`);

    const data = await response.json();

    const labels =
    data.map(item => "Month " + item.month);

    const values = data.map(item => item.amount);
    ctx = document.getElementById("monthlySavingsChart")
    new Chart(
        ctx,
        {
            type:"bar",

            data:{
                labels:labels,

                datasets:[
                    {
                        label: "Monthly Savings",
                        data: values,
                        backgroundColor: "#22C55E",
                        borderColor: "#15803D",
                        borderWidth: 1,
                        borderRadius: 8
                    }
                ]
            },
                        options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
        }
    );
}

//===================================
//INCOME VS EXPENSE
//===================================
async function loadIncomeVsExpense(){
    const response = await fetch(`${BASE_URL}/IncomeVsExpense/${userId}`);
    const data = await response.json();

    const labels = data.map(item => "Month "+item.month);
    const income = data.map(item => item.income);
    const expense = data.map(item => item.expense);
    const ctx = document.getElementById("incomeExpenseChart");

    new Chart(ctx , {
        type : "bar",
        data :{
            labels:labels,
            
            datasets:[
            {
                label: "Income",
                data: income,
                backgroundColor: "#10B981",
                borderRadius: 8
            },
            {
                label: "Expense",
                data: expense,
                backgroundColor: "#EF4444",
                borderRadius: 8
            }
            ]
        },
                    options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
    });
}
//=================================
//BUDGET VS ACTUAL SPENDING
//=================================
async function loadBudgetVsActual(){
    const response = await fetch(`${BASE_URL}/budgetVsActualSpending/${userId}`);
    const data = await response.json();
    const labels = data.map(item=>item.category);
    const budget = data.map(item=>item.budget);
    const actual = data.map(item=>item.actual);
    const ctx = document.getElementById("budgetActualChart");

    new Chart(ctx , {
        type:"bar",
        data:{
            labels:labels,
            datasets:[
                    {
                        label: "Budget",
                        data: budget,
                        backgroundColor: "#6366F1",
                        borderRadius: 8
                    },
                    {
                        label: "Actual",
                        data: actual,
                        backgroundColor: "#F97316",
                        borderRadius: 8
                    }
                ]
        },
                    options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        labels: {
                            color: "#374151",
                            font: {
                                size: 13,
                                weight: "bold"
                            }
                        }
                    }
                },
                scales: {
                    x: {
                        grid: {
                            display: false
                        }
                    },
                    y: {
                        beginAtZero: true,
                        grid: {
                            color: "#E5E7EB"
                        }
                    }
                }
            }
    })
}


//==================================
// PAGE LOAD
//==================================

window.onload = () => {

    loadMonthlySpending();
    loadDailySpending();
    loadCategorySpending();
    loadMonthlySavings();
    loadBudgetVsActual();
    loadIncomeVsExpense();
};