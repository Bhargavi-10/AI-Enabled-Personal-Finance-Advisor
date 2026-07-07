const BASE_URL = "http://localhost:8080/ai";
const userId = localStorage.getItem("userId");

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("sendBtn").addEventListener("click", sendMessage);
    document.getElementById("clearBtn").addEventListener("click", clearChat);
    loadPredictions();
});

async function sendMessage() {
    const input = document.getElementById("userMessage");
    const msg = input.value.trim();

    if (msg === "") return;

    const chatBox = document.getElementById("chatBox");

    chatBox.innerHTML += `<div class="user-message">${escapeHtml(msg)}</div>`;

    const loadingId = "loading-" + Date.now();
    chatBox.innerHTML += `
        <div class="bot-message" id="${loadingId}">
            📊 Analyzing your financial data...
        </div>
    `;

    chatBox.scrollTop = chatBox.scrollHeight;

    const messages = [
        "📊 Analyzing your financial data...",
        "💰 Reviewing spending patterns...",
        "📈 Evaluating budgets and savings...",
        "🤖 Preparing personalized advice..."
    ];

    let index = 0;
    const interval = setInterval(() => {
        const loadingElement = document.getElementById(loadingId);
        if (loadingElement) {
            loadingElement.innerText = messages[index % messages.length];
        }
        index++;
    }, 1000);

    try {
        const response = await fetch(`${BASE_URL}/chat/${userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ message: msg })
        });

        const reply = await response.text();
        clearInterval(interval);

        const loadingElement = document.getElementById(loadingId);
        if (loadingElement) {
            loadingElement.innerHTML = formatAiReply(reply);
        }
    } catch (error) {
        clearInterval(interval);
        const loadingElement = document.getElementById(loadingId);
        if (loadingElement) {
            loadingElement.innerHTML = "❌ Unable to contact AI Advisor.";
        }
        console.error(error);
    }

    input.value = "";
    chatBox.scrollTop = chatBox.scrollHeight;
}

function clearChat() {
    document.getElementById("chatBox").innerHTML = `<div class="chat-title">CHATBOT</div>`;
}

async function loadPredictions() {
    const response = await fetch(`${BASE_URL}/predictions/${userId}`);
    const data = await response.json();

    const spending = data.predictedSpending || 0;
    const savings = data.predictedSavings || 0;

    document.getElementById("predictedSpending").innerText = "Rs. " + spending.toFixed(2);
    document.getElementById("predictedSavings").innerText = "Rs. " + savings.toFixed(2);
}

function escapeHtml(text) {
    return text
        .replaceAll("&", "&amp;")
        .replaceAll("<", "&lt;")
        .replaceAll(">", "&gt;")
        .replaceAll('"', "&quot;")
        .replaceAll("'", "&#039;");
}

function formatAiReply(reply) {
    const lines = reply.split("\n").map(line => line.trim()).filter(line => line !== "");
    let html = "";

    lines.forEach(line => {
        if (line.startsWith("Title:")) {
            html += `<div class="message-title">${line}</div>`;
        } else if (line.startsWith("Summary:") || line.startsWith("Insights:") || line.startsWith("Action Plan:")) {
            html += `<div class="message-section"><strong>${line}</strong></div>`;
        } else if (line.startsWith("-") || line.match(/^\d+\./)) {
            html += `<div class="message-section">${line}</div>`;
        } else {
            html += `<div class="message-section">${line}</div>`;
        }
    });

    return html;
}