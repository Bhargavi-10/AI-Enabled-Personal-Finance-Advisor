BASE_URL = "http://localhost:8080/budget";
const userId = localStorage.getItem("userId");
if(!userId){
    alert("please login");
    window.location.href="login.html";
}
let selectedBudgetId = null;

//====================
//LOAD BUDGETS
//====================
async function loadBudgets(){
    const response = await fetch(`${BASE_URL}/viewBudget/${userId}`);
    const budgets = await response.json();
    console.log(budgets);
    const tableBody = document.getElementById("budgetBody");
    tableBody.innerHTML = "";
    budgets.forEach(bgts => {
        const row = `
        <tr>
          <td>${bgts.year}</td>
          <td>${getMonthName(bgts.month)}</td>
          <td>${bgts.category}</td>
          <td>Rs.${bgts.monthlyAmount}</td>
          <td>
            <button class="edit-btn" onclick='editBudget(${bgts.budgetId},"${bgts.year}","${bgts.category}","${bgts.month}",${bgts.monthlyAmount})'>EDIT</button>
            <button class="delete-btn" onclick="deleteBudget(${bgts.budgetId})">DELETE</button></td>
          </tr>`;
          tableBody.innerHTML += row;
    })
}


//==============================
//ADD BUDGET
//==============================

document.getElementById("addBtn").addEventListener("click" , addBudget);

async function addBudget(){
    const budgetData = {
        userId:userId,
        year: parseInt(document.getElementById("year").value),
        month: parseInt(document.getElementById("month").value),
        category: document.getElementById("category").value,
        monthlyAmount: parseFloat(document.getElementById("amount").value),
    };

    if(
    !budgetData.year||
    !budgetData.category ||
    !budgetData.month ||
    !budgetData.monthlyAmount
    ){
    alert("Please fill all fields");
    return;
    }
    if (!/^(?!null$)(?!NaN$)[A-Za-z\s]+$/.test(budgetData.category)){
        alert("category must contain only letters and cannot be 'null' or 'NaN'.");
        return;
    }
    if(isNaN(budgetData.monthlyAmount)||budgetData.monthlyAmount<=0){
        alert("The entered amount is not valid!! . Please enter valid amount");
        return;
    }
    const response = await fetch(`${BASE_URL}/add`,
        {
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(budgetData)
        }
    );

    const result = await response.text();
    alert(result);
   
    document.getElementById("month").value = "";
    document.getElementById("year").value = "";
    document.getElementById("category").value = "";
    document.getElementById("amount").value = "";

    loadBudgets();
}

//===============================
//DELETE
//===============================
async function deleteBudget(id){
    const confirmDelete = confirm("Delete this budget?");
    if (!confirmDelete) return;
    const response = await fetch(`${BASE_URL}/delete/${id}`,{
        method : "DELETE"
    });
    const result = await response.text();
    alert(result);
    loadBudgets();
}

//===============================
//EDIT
//===============================
function editBudget(id,year,category,month,amount){
    loadYearDropDown();
    selectedBudgetId = id;
    document.getElementById("editMonth").value=month;
    document.getElementById("editCategory").value=category;
    document.getElementById("editYear").value=year;
    document.getElementById("editAmount").value=amount;
    document.getElementById("editModal").style.display="flex";
    console.log(selectedBudgetId);

   
}
    //CLOSE BUTTON
    document.getElementById("closeBtn").addEventListener("click" ,()=>{
        document.getElementById("editModal").style.display="none";
    });


    //UPDATE TRANSACTION
    document.getElementById("updateBtn").addEventListener("click" , updateBudget);

async function updateBudget(){
    
    const updateData = {
        year : parseInt(document.getElementById("editYear").value),
        category: document.getElementById("editCategory").value,
        month:parseInt(document.getElementById("editMonth").value),
        monthlyAmount:parseFloat(document.getElementById("editAmount").value)
    };


    if (!/^(?!null$)(?!NaN$)[A-Za-z\s]+$/.test(updateData.category)){
        alert("category must contain only letters and cannot be 'null' or 'NaN'.");
        return;
    }
    if(isNaN(updateData.monthlyAmount)||updateData.monthlyAmount<=0){
        alert("The entered amount is not valid!! . Please enter valid amount");
        return;
    }
    
   //send request to backend

    try{
        const response = await fetch(`${BASE_URL}/update/${selectedBudgetId}`,
            {
                method:"PUT",
                headers:{
                    "Content-Type":"application/json"
                },
                body:JSON.stringify(updateData)
            }
        );
        const result = await response.text();
        alert(result);
        document.getElementById("editModal").style.display = "none";
        loadBudgets();
    }catch(error){
        console.error(error);
        alert("update failed")
    }
}
//================================
//YEAR DROPDOWN
//================================
function loadYearDropDown(){
    const yearSelect = document.getElementById("year");
    const currYear = new Date().getFullYear();

    for(let i = currYear ; i<currYear+5 ; i++){
        yearSelect.innerHTML += `<option value="${i}">${i}</option>`;
    }
}
//===============================
//EDIT YEAR DROPDOWN
//================================
function loadEditYearDropDown(){
    const yearSelect = document.getElementById("editYear");
    const currYear = new Date().getFullYear();

    for(let i = currYear ; i<currYear+5 ; i++){
        yearSelect.innerHTML += `<option value="${i}">${i}</option>`;
    }
}
//===============================
//GET MONTHS NAME
//===============================
function getMonthName(month){

    const months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ];

    return months[month-1];
}
//================================
//PAGE LOAD
//================================
window.onload = () =>{
  loadBudgets();
  loadYearDropDown();
  loadEditYearDropDown();
};