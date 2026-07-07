const BASE_URL = "http://localhost:8080/income";
const userId = localStorage.getItem("userId");

let editingIncomeId = null;

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("addIncomeBtn").addEventListener("click", saveIncome);
    document.getElementById("closeBtn").addEventListener("click", () => {
        document.getElementById("editModal").style.display = "none";
    });
    document.getElementById("updateBtn").addEventListener("click", saveUpdatedIncome);

    loadIncome();
});

async function loadIncome() {
    const response = await fetch(`${BASE_URL}/viewIncome/${userId}`);
    const incomes = await response.json();

    const tbody = document.getElementById("incomeTableBody");
    tbody.innerHTML = "";

    incomes.forEach(income => {
        tbody.innerHTML += `
            <tr>
                <td>${income.source}</td>
                <td>Rs. ${income.amount}</td>
                <td>${income.date}</td>
                <td>
                    <button class="edit-btn" onclick='editIncome(${income.incomeId},"${income.date}","${income.source}",${income.amount})'>EDIT</button>
                    <button class="delete-btn" onclick="deleteIncome(${income.incomeId})">Delete</button>
                </td>
            </tr>
        `;
    });

    updateIncomeSummary(incomes);
}

async function saveIncome() {
    const source = document.getElementById("incomeSource").value;
    const amount = parseFloat(document.getElementById("incomeAmount").value);
    const date = document.getElementById("incomeDate").value;

    if (source === "" || isNaN(amount) || date === "") {
        alert("Please fill all fields");
        return;
    }

    if (amount <= 0) {
        alert("Please enter valid amount");
        return;
    }

    const incomeData = {
        source: source,
        amount: amount,
        date: date,
        userId: userId
    };

    const response = await fetch(`${BASE_URL}/add/${userId}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(incomeData)
    });

    const result = await response.text();
    alert(result);
    clearForm();
    loadIncome();
}

async function deleteIncome(incomeId) {
    const confirmDelete = confirm("Delete this income?");
    if (!confirmDelete) return;

    const response = await fetch(`${BASE_URL}/delete/${incomeId}`, {
        method: "DELETE"
    });

    const result = await response.text();
    alert(result);
    loadIncome();
}

function editIncome(id, date, source, amount) {
    editingIncomeId = id;
    document.getElementById("editDate").value = date;
    document.getElementById("editSource").value = source;
    document.getElementById("editAmount").value = amount;
    document.getElementById("editModal").style.display = "flex";
}

async function saveUpdatedIncome() {
    const updateData = {
        date: document.getElementById("editDate").value,
        source: document.getElementById("editSource").value,
        amount: parseFloat(document.getElementById("editAmount").value)
    };

    if (isNaN(updateData.amount) || updateData.amount <= 0) {
        alert("The entered amount is not valid. Please enter a valid amount.");
        return;
    }

    try {
        const response = await fetch(`${BASE_URL}/update/${editingIncomeId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updateData)
        });

        const result = await response.text();
        alert(result);
        document.getElementById("editModal").style.display = "none";
        loadIncome();
    } catch (error) {
        console.error(error);
        alert("Update failed");
    }
}

function updateIncomeSummary(incomes) {
    let totalIncome = 0; //all income till date
    let monthlyIncome = 0; //total income from all sources this month

    const currMonth = new Date().getMonth();
    const currYear = new Date().getFullYear();

    incomes.forEach(income => {
        totalIncome += income.amount;

        const incomeDate = new Date(income.date);
        if (incomeDate.getMonth() === currMonth && incomeDate.getFullYear() === currYear) {
            monthlyIncome += income.amount;
        }
    });

    document.getElementById("totalIncome").innerText = "Rs. " + totalIncome.toFixed(2);
    document.getElementById("monthlyIncome").innerText = "Rs. " + monthlyIncome.toFixed(2);
}
function clearForm() {
    document.getElementById("incomeSource").value = "";
    document.getElementById("incomeAmount").value = "";
    document.getElementById("incomeDate").value = "";
}