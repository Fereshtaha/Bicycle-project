const asyncRequest = new XMLHttpRequest();
asyncRequest.onreadystatechange = onResponseReceived;


function resetPassword() {
    const data = {
        email: document.getElementById("form-input-email").value.toString(),
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "http://localhost:8080/customers/reset-password");
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
        if (this.status === 200) { // handle success
            console.log("Successful request, redirecting now...")
            location.href = "/account";
        } else{
            console.log("Request was unsuccessful");
            console.log("Status code is: " + this.status)
        }
    }
}



const submitBtn = document.getElementById("submitLogin");
const emailField = document.getElementById("form-input-email");

const errorEmailEmpty = document.getElementById("emptyEmailFieldErrorText");
const errorEmailPattern = document.getElementById("patternEmailFieldErrorText");

function checkEmail(){
    if (!emailField.value || emailField.value === ""){
        errorEmailEmpty.classList.add("errorActive");
        errorEmailPattern.classList.remove("errorActive");
        emailField.classList.add("input-error");
    }else if (!/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value)){
        errorEmailEmpty.classList.remove("errorActive");
        errorEmailPattern.classList.add("errorActive");
        emailField.classList.add("input-error");
    }else{
        errorEmailPattern.classList.remove("errorActive");
        emailField.classList.remove("input-error");
    }
    makeButtonClickableForResetPassword();
}

function makeButtonClickableForResetPassword(){
    if (emailField.value){
        if ((/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value))){
            submitBtn.disabled = false;
            submitBtn.style.cursor = "pointer";
        }else{
            submitBtn.disabled = true;
            submitBtn.style.cursor = "default";
        }
    }else{
        submitBtn.disabled = true;
        submitBtn.style.cursor = "default";
    }
}