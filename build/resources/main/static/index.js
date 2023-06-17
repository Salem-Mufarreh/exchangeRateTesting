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

            toastrElement.style.backgroundColor = "green";
            toastrElement.innerText = "Conversion successful";
            toastrElement.hidden = false;
            console.log(response);

            // Update the webpage with the conversion result
            $("#result").text("Conversion Result: " + response);
            let rate = response/ amount;
            $("#rate").text("Conversion Rate: "+rate);
        },
        error: function(error) {
            toastrElement.style.backgroundColor = "red";
            toastrElement.innerText = "Conversion Failed";
            toastrElement.hidden = false;
            // Handle any errors that occurred during the AJAX request
            console.error(error);
        }
    });
}