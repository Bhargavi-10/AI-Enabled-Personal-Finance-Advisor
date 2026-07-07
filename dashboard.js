const BASE_URL = "http://localhost:8080/dashboard";
const userId = localStorage.getItem("userId");
if(!userId){
    alert("please Login first");
    window.location.href = "login.html";
}
//==========================
//TOTAL EXPENSE
//==========================
async function loadTotalExpense(){
    const response = await fetch(`${BASE_URL}/totalExpense/${userId}`);
    const data = await response.json();
    document.getElementById("totalExpense").innerText=`Rs.${data}`;
}

//==========================
//CURRENT MONTH EXPENSE
//==========================
async function loadCurrentMonthExpense(){
    const response = await fetch(`${BASE_URL}/currentMonthExpense/${userId}`);
    const data = await response.json();
    document.getElementById("currentExpense").innerText=`Rs.${data}`;
}

//==========================
//BUDGET ALERTS
//==========================
async function loadBudgetAlerts(){
    const response = await fetch(`${BASE_URL}/budgetAlertCount/${userId}`);
    const data = await response.json();
    document.getElementById("budgetAlerts").innerText=`${data}`;
}

//============================
//TOP EXPENSE CATEGORY
//============================
async function loadTopCategory(){
    const response = await fetch(`${BASE_URL}/topExpenseCategory/${userId}`);
    const data = await response.json();
    document.getElementById("topCategory").innerText=`${data.category}(Rs.${data.amount})`;
}
//===============================
//TOTAL SAVINGS
//===============================
async function loadTotalSavings(){
    const response = await fetch(`${BASE_URL}/totalSavings/${userId}`);

    const savings = await response.json();

    if(savings <= 0){
       document.getElementById("totalSavings").innerText="Rs."+0; 
    }

    document.getElementById("totalSavings").innerText="Rs."+savings.toFixed(2);
}

//==================================
//FINANCIAL SCORE
//==================================
async function loadFinancialScore(){
    const response = await fetch(`${BASE_URL}/financialScore/${userId}`);

    const financialScore = await response.json();

    document.getElementById("financialScore").innerText = financialScore;
}

//==================================
//CURRENT MONTH SAVINGS
//==================================
async function loadCurrentMonthSavings(){
    const responseDetail = await fetch(`${BASE_URL}/currentMonthSavings/${userId}`);
    const detail = await responseDetail.json();

    const savings = detail.savings ?? 0;
    const reason = detail.reason;
    
    if(reason==="NO_INCOME_ENTERED"){
        alert("Enter your income for this month");
    }
    else if(reason === "EXPENSE_GREATER_THAN_INCOME"){
        alert("Expenses are greater than income for this month");
    }

    document.getElementById("currentMonthSavings").innerText = "Rs." +savings.toFixed(2);


}

//===========================
//RECENT TRANSACTIONS
//===========================
async function loadRecentTransactions(){
    const response = await fetch(`${BASE_URL}/recentTransactions/${userId}`);
    const transactions = await response.json();
    const table = document.getElementById("transactionTable");
    table.innerHTML = "";
    transactions.forEach(transaction =>{
          const row =`
          <tr>
             <td>${transaction.transactionDate}</td>
             <td>${transaction.category}</td>
             <td>${transaction.description}</td>
             <td>${transaction.amount}</td>
          </tr>`;
          table.innerHTML += row;
    });
}
//=============================
//BUDGET UTILIZATION
//=============================
async function loadBudgetUtilization(){

    const response =
    await fetch(
        `http://localhost:8080/dashboard/budgetUtilization/${userId}`
    );

    const data = await response.json();

    const container =
    document.getElementById(
        "budgetUtilizationContainer"
    );
    
    container.innerHTML = "";

    data.forEach(item=>{

        let color = "#22c55e";

        if(item.utilization >= 80){
            color = "#ef4444";
        }
        else if(item.utilization >= 60){
            color = "#f59e0b";
        }

        container.innerHTML += `
        
        <div class="utilization-item">

            <div class="utilization-header">

                <span>${item.category}</span>

                <span>${item.utilization.toFixed(1)}%</span>

            </div>

            <div class="progress-bar">

                <div
                    class="progress-fill"
                    style="
                         width:${item.utilization > 0 ? item.utilization + '%' : '0'};
                         background:${item.utilization > 0 ? color : 'transparent'};
                    "

                >
                </div>

            </div>

        </div>
        
        `;
    });
}

//=============================
//PAGE LOAD
//=============================
window.onload = () =>{
    loadTotalExpense();
    loadTopCategory();
    loadCurrentMonthExpense();
    loadBudgetAlerts();
    loadRecentTransactions();
    loadBudgetUtilization();
    loadTotalSavings();
    loadFinancialScore();
    loadCurrentMonthSavings();
}
