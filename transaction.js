const BASE_URL = "http://localhost:8080/transactions";
const userId = localStorage.getItem("userId");
if(!userId){
    alert("Please login first");
    window.location.href = "login.html";
}
let selectedTransactionId = null;

//====================
//LOAD TRANSACTIONS
//====================
async function loadTransactions(){
    const response = await fetch(`${BASE_URL}/viewTransactions/${userId}`);
    const transactions = await response.json();
    const tableBody = document.getElementById("transactionBody");
    tableBody.innerHTML = "";
    transactions.forEach(trans => {
        const row = `
        <tr>
          <td>${trans.transactionDate}</td>
          <td>${trans.category}</td>
          <td>${trans.description}</td>
          <td>Rs.${trans.amount}</td>
          <td>
            <button class="edit-btn" onclick='editTransaction(${trans.transactionId},"${trans.transactionDate}","${trans.category}","${trans.description}",${trans.amount})'>EDIT</button>
            <button class="delete-btn" onclick="deleteTransaction(${trans.transactionId})">DELETE</button></td>
          </tr>`;
          tableBody.innerHTML += row;
    })
}

//==============================
//ADD TRANSACTION
//==============================

document.getElementById("addBtn").addEventListener("click" , addTransaction);

async function addTransaction(){
    const transactionData = {
        userId:userId,
        transactionDate: document.getElementById("transactionDate").value,
        category: document.getElementById("category").value,
        description: document.getElementById("description").value,
        amount: parseFloat(document.getElementById("amount").value),
    };
    if (!/^(?!null$)(?!NaN$)[A-Za-z\s]+$/.test(transactionData.category)){
        alert("category must contain only letters and cannot be 'null' or 'NaN'.");
        return;
    }
    if (!/^(?!null$)(?!NaN$)[A-Za-z\s]+$/.test(transactionData.description)){
        alert("Description must contain only letters and cannot be 'null' or 'NaN'.");
        return;
    }
    if(isNaN(transactionData.amount)||transactionData.amount<=0){
        alert("The entered amount is not valid!! . Please enter valid amount");
        return;
    }

    if(
    !transactionData.transactionDate ||
    !transactionData.category ||
    !transactionData.description ||
    !transactionData.amount
    ){
    alert("Please fill all fields");
    return;
    }



    const response = await fetch(`${BASE_URL}/add`,
        {
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify(transactionData)
        }
    );

    const result = await response.text();
    alert(result);
   
    document.getElementById("transactionDate").value = "";
    document.getElementById("category").value = "";
    document.getElementById("description").value = "";
    document.getElementById("amount").value = "";

    loadTransactions();
}

//===============================
//DELETE
//===============================
async function deleteTransaction(id){
    const confirmDelete = confirm("Delete this Transaction?");
    if (!confirmDelete) return;
    const response = await fetch(`${BASE_URL}/delete/${id}`,{
        method : "DELETE"
    });
    const result = await response.text();
    alert(result);
    loadTransactions();
}


//===============================
//EDIT
//===============================
function editTransaction(id,date,category,description,amount){
    selectedTransactionId = id;
    document.getElementById("editDate").value=date;
    document.getElementById("editCategory").value=category;
    document.getElementById("editDescription").value=description;
    document.getElementById("editAmount").value=amount;
    document.getElementById("editModal").style.display="flex";
    console.log(selectedTransactionId);

   
}
    //CLOSE BUTTON
    document.getElementById("closeBtn").addEventListener("click" ,()=>{
        document.getElementById("editModal").style.display="none";
    });


    //UPDATE TRANSACTION
    document.getElementById("updateBtn").addEventListener("click" , updateTransaction);

async function updateTransaction(){
    const updateData = {
        transactionDate : document.getElementById("editDate").value,
        category: document.getElementById("editCategory").value,
        description:document.getElementById("editDescription").value,
        amount:parseFloat(document.getElementById("editAmount").value)
    };


    if (!/^(?!null$)(?!NaN$)[A-Za-z\s]+$/.test(updateData.category)){
        alert("category must contain only letters and cannot be 'null' or 'NaN'.");
        return;
    }
    if(isNaN(updateData.amount)||updateData.amount<=0){
        alert("The entered amount is not valid!! . Please enter valid amount");
        return;
    }


    //send request to backend
    try{
        const response = await fetch(`${BASE_URL}/update/${selectedTransactionId}`,
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
        loadTransactions();
    }catch(error){
        console.error(error);
        alert("update failed")
    }
}
//================================
//PAGE LOAD
//================================
window.onload = () =>{
  loadTransactions();
};