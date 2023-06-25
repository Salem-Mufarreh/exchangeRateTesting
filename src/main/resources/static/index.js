document.getElementById("conversionForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Prevent the form from submitting normally

    // Get the values of each input field
    let amount = document.getElementById("amount").value;
    let sourceCurrency = document.getElementById("from").value;
    let targetCurrency = document.getElementById("to").value;

    // Call the AJAX function with the retrieved values
    convertCurrency(amount, sourceCurrency, targetCurrency);
});
function convertCurrency(amount, sourceCurrency, targetCurrency) {
    let toastrElement = document.getElementById("toastr");
    // Perform AJAX request here and handle the response
    // You can use the $.ajax function or any other AJAX library of your choice
    $.ajax({
        url: "/api/currency/convert",
        method: "GET",
        data: {
            amount: amount,
            source: sourceCurrency,
            target: targetCurrency
        },
        success: function(response) {
            // Handle the response from the server

            toastrElement.classList.add("alert-success")
            toastrElement.innerText = "Conversion successful";
            toastrElement.hidden = false;
            console.log(response);

            // Update the webpage with the conversion result
            $("#result").text("Conversion Result: " + response);
            let rate = response/ amount;
            $("#rate").text("Conversion Rate: "+rate);
        },
        error: function(error) {
            toastrElement.classList.add("alert-danger")

            toastrElement.innerText = "Conversion Failed";
            toastrElement.hidden = false;
            // Handle any errors that occurred during the AJAX request
            console.error(error);
        }
    });
}


// Create an XMLHttpRequest object
let xhr = new XMLHttpRequest();

// Configure the request
xhr.open('GET', 'http://localhost:8080/api/currency/');

// Set the response type
xhr.responseType = 'json';

// Define the success callback function
xhr.onload = function() {
    if (xhr.status === 200) {
        // Retrieve the currency list element
        let currencyList = document.getElementById("currencyList");

        // Iterate over the currencies and create a list item for each currency
        xhr.response.forEach(currency => {
            let listItem = document.createElement("li");
            listItem.textContent = currency.sourceCurrency + " to " + currency.targetCurrency + ": " + currency.rate;
            currencyList.appendChild(listItem);
        });
    } else {
        console.error("Error fetching currencies:", xhr.status);
    }
};

// Send the request
xhr.send();
