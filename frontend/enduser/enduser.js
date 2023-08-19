document.addEventListener("DOMContentLoaded", function () {
    console.log('Attempting to connect to backend');
    fetch("http://localhost:8080/api/trip-reimbursement-requests/configuration")
        .then(response => {
            console.log('Successfully connected to backend', response);
            return response.json();
        })
        .catch(error => {
            console.log('Failed to connect to backend', error);
        });

    document.getElementById("reimbursementForm").addEventListener("submit", function (event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            tripDate: formData.get("tripDate"),
            carMileage: parseFloat(formData.get("carMileage")),
            daysOfAllowance: parseInt(formData.get("daysOfAllowance")),
            receipts: []
        };

        const receiptTypes = formData.getAll("receiptType");
        const receiptPrices = formData.getAll("price");
        for (let i = 0; i < receiptTypes.length; i++) {
            data.receipts.push({
                receiptType: receiptTypes[i],
                price: parseFloat(receiptPrices[i])
            });
        }

        fetch("localhost:8080/api/trip-reimbursement-requests", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => response.json())
            .then(responseData => {
                document.getElementById("totalReimbursementAmount").textContent = 'Total Reimbursement Amount: ${responseData.totalReimbursementAmount}';
            });
    });
});