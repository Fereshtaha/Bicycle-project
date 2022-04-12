const asyncRequest = new XMLHttpRequest();
asyncRequest.onreadystatechange = onResponseReceived;


function createUser() {
    const data = {
        firstName: document.getElementById("form-input-firstName").value.toString(),
        lastName: document.getElementById("form-input-lastName").value.toString(),
        email: document.getElementById("form-input-email").value.toString(),
        dob: document.getElementById("birthday").value.toString(),
        password: document.getElementById("password").value.toString(),
        phone: document.getElementById("form-input-phone").value
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "http://localhost:8080/customers");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(formData);

}

/**
 * This function will be called when the status of HTTP response updates.
 * Typically, when the response has come completely, but for large responses this method could be called several
 * times during the download of the response. We therefore need to check the .readyState and .status properties.
 */
function onResponseReceived() {
    if (this.readyState === XMLHttpRequest.DONE) {
        if (this.status == 200) { // handle success
            console.log("Successful request, redirecting now...")
            location.href = "/account";
        } else{
            console.log("Request was unsuccessful");
            console.log("Status code is: " + this.status)
        }
    }

}

