// const userId = localStorage.getItem("userId");

// if(!userId && !window.location.pathname.includes("login.html")){
//     window.location.href = "login.html";
// }

const logoutBtn = document.getElementById("logoutBtn");
if(logoutBtn){
    logoutBtn.addEventListener("click" , logout);
}

function logout(){
    const confirmLogout = confirm("Are you sure you want to logout? ");
  
    if(!confirmLogout){
        return ;
    }

    localStorage.removeItem("userId");

    window.location.href = "login.html";
}