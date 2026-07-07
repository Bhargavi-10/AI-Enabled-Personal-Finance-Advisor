const loginBtn  =document.getElementById("loginBtn");
const registerBtn  = document.getElementById("registerBtn");

const BASE_URL = "http://localhost:8080/users";

//====================================
//LOGIN
//====================================

loginBtn.addEventListener("click" , async()=>{

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if(email==="" || password===""){
        alert("Email and Password required");
        return;
    }

    const userData = {
        email: email,
        password: password
    };
    try{
        const response = await fetch(`${BASE_URL}/login`,{method:"POST",
             headers:{
                "Content-Type":"application/json"
             } ,
             body: JSON.stringify(userData)
            }
        );

        const data = await response.json();

        alert(data.message);

        if(data.message === "Login Successful"){
            localStorage.setItem(
                "userId",
                data.userId
            );

            window.location.href = "dashboard.html";
        }
    }catch(error){
        console.error(error);

        alert("Server error");
    }

});


//===============================
//REGISTER
//===============================

registerBtn.addEventListener("click" ,async()=>{

    const username = document.getElementById("username").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if(username===""||email===""||password===""){
        alert("Fill all Fields");
        return;
    }

    const userData = {
        userName:username,
        email:email,
        password:password
    };

    try{
        const response = await fetch(`${BASE_URL}/register`,
            {
                method:"POST",
                headers:{
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(userData)
            }
        );

        const data = await response.json();

        alert(data.message);

        if(data.message==="Registration Successful"){
            localStorage.setItem(
                "userId",
                data.userId
            );
        }

    }catch(error){
        alert("Server error");
        console.error(error);
    }

});