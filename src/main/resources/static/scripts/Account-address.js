



function getInfoFromDB() {
    const asyncRequest = new XMLHttpRequest();

    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/customers/authenticated-address");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;


    let addressForm = document.getElementById("address-form");
    let firstNameField = document.getElementById("form-input-firstName");
    let lastNameField = document.getElementById("form-input-lastName");
    let addressField = document.getElementById("form-input-address");
    let countryField = document.getElementById("form-input-country");
    let postalCodeField = document.getElementById("form-input-postalCode");
    let cityField = document.getElementById("form-input-city");
    let submitBtn = document.getElementById("submitBtn");

    addressForm.addEventListener("change", function (event){
        submitBtn.disabled = false;
        submitBtn.style.cursor = "pointer";
    })

    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);

        firstNameField.value = responseJson.firstName;
        lastNameField.value = responseJson.lastName;
        addressField.value = responseJson.address;
        countryField.value = responseJson.country;
        postalCodeField.value = responseJson.postalCode;
        cityField.value = responseJson.city;
    }
    function onResponseReceivedFromGET() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                console.log("Successful GET request")
            } else{
                console.log("Request was unsuccessful");
                console.log("Status code is: " + this.status)
            }
        }
    }
}
getInfoFromDB();

function updateAddress(){
    const asyncRequest = new XMLHttpRequest();

    const data = {
        firstName: document.getElementById("form-input-firstName").value.toString(),
        lastName: document.getElementById("form-input-lastName").value.toString(),
        address: document.getElementById("form-input-address").value.toString(),
        country: document.getElementById("form-input-country").value.toString(),
        postalCode: document.getElementById("form-input-postalCode").value.toString(),
        city: document.getElementById("form-input-city").value.toString()
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "/customers/authenticated-address");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(formData);
    asyncRequest.onreadystatechange = onResponseReceivedFromPOST;

    function onResponseReceivedFromPOST() {
        if (this.readyState === XMLHttpRequest.DONE) {
            let responseMessage = document.getElementById("successMessageContainer");
            if (this.status === 200) { // handle success
                responseMessage.classList.add("successResponse");
                responseMessage.removeAttribute("hidden");
                responseMessage.innerHTML = "<p id='successMessageText'>Profile has been successfully updated!</p>";

            } else{
                responseMessage.removeAttribute("hidden");
                responseMessage.classList.add("errorResponse");
                responseMessage.innerHTML = "<p id='successMessageText'>An error occurred. Profile not updated!</p>";
            }
        }
    }
}

