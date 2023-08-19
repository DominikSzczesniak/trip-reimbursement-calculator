document.addEventListener("DOMContentLoaded", function() {
    fetch("http://localhost:8080/api/trip-reimbursement-requests/configuration")
        .then(response => response.json())
        .then(data => {
            document.querySelector("input[name='dailyAllowanceRate']").value = data.dailyAllowanceRate;
            document.querySelector("input[name='carMileageRate']").value = data.carMileageRate;
            document.querySelector("input[name='totalReimbursementLimit']").value = data.totalReimbursementLimit;
            document.querySelector("input[name='distancePriceLimit']").value = data.distancePriceLimit;

            data.availableReceipts.forEach(receipt => {
                document.querySelector(`input[value='${receipt.type}']`).checked = true;
                document.querySelector(`input[name='${receipt.type}receiptLimit']`).value = receipt.limit;
            });
        });

    document.getElementById("addReceiptType").addEventListener("click", function(event) {
        event.preventDefault();

        const receiptsContainer = document.getElementById("receiptsContainer");
        const receiptType = prompt("Enter receipt type:");

        if (receiptType) {
            const html = `
        <input type="checkbox" id="${receiptType}" name="receipts" value="${receiptType}">
        <label for="${receiptType}">${receiptType}</label>
        <input type="number" name="${receiptType}ReceiptLimit"><br>
      `;

            receiptsContainer.innerHTML += html;
        }
    });

    document.getElementById("adminForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const formData = new FormData(event.target);
        const data = {
            dailyAllowanceRate: formData.get("dailyAllowanceRate"),
            carMileageRate: formData.get("carMileageRate"),
            totalReimbursementLimit: formData.get("totalReimbursementLimit"),
            distancePriceLimit: formData.get("distancePriceLimit"),
            availableReceipts: []
        };

        const receiptTypes = formData.getAll("receipts");
        receiptTypes.forEach(receiptType => {
            data.availableReceipts.push({
                receiptType: receiptType,
                receiptLimit: formData.get(`${receiptType}ReceiptLimit`)
            });
        });

        fetch("http://localhost:8080/api/trip-reimbursement-requests/configuration", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    alert("Settings updated successfully!");
                } else {
                    alert("Failed to update settings.");
                }
            });
    });
});