const asyncRequest = new XMLHttpRequest();

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "http://localhost:8080/customers/authenticated-address");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;


    let firstNameField = document.getElementById("form-input-firstName");
    let lastNameField = document.getElementById("form-input-lastName");
    let addressField = document.getElementById("form-input-address");
    let countryField = document.getElementById("form-input-country");
    let postalCodeField = document.getElementById("form-input-postalCode");
    let cityField = document.getElementById("form-input-city");

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
                //location.href = "/account";
            } else{
                console.log("Request was unsuccessful");
                console.log("Status code is: " + this.status)
            }
        }
    }
}
getInfoFromDB();

