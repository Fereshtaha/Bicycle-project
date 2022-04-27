const submitBtn = document.getElementById("submitLogin");

const emailField = document.getElementById("form-input-email");
const passwordField = document.getElementById("password");

const errorEmailEmpty = document.getElementById("emptyEmailFieldErrorText");
const errorEmailPattern = document.getElementById("patternEmailFieldErrorText");

const errorPasswordEmpty = document.getElementById("emptyPasswordFieldErrorText");
const errorPasswordPattern = document.getElementById("patternErrorText");

/**
 * This function will be called when the status of HTTP response updates.
 * Typically, when the response has come completely, but for large responses this method could be called several
 * times during the download of the response. We therefore need to check the .readyState and .status properties.
 */
function onResponseReceived() {
    if (asyncRequest.readyState === XMLHttpRequest.DONE) {
        if (asyncRequest.status === 200) {
            console.log("POST successful");
        } else {
            console.error("Error " + asyncRequest.status);
        }
    }
}

const queryString = window.location.search;
let responseMessage = document.getElementById("successMessageContainer");
if (queryString === "?error"){
    console.log("vis error melding");
    responseMessage.removeAttribute("hidden");
    responseMessage.classList.add("errorResponse");
    responseMessage.innerHTML = "<p id='successMessageText'>Invalid credentials, please try again!</p>";
}if (queryString === "?logout"){
    console.log("vis error melding");
    responseMessage.removeAttribute("hidden");
    responseMessage.classList.add("successResponse");
    responseMessage.innerHTML = "<p id='successMessageText'>Successfully logged out!</p>";
}


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
    makeButtonClickableForLogin();
}

function checkPassword(){
    if (!passwordField.value || passwordField.value === ""){
        errorPasswordEmpty.classList.add("errorActive");
        errorPasswordPattern.classList.remove("errorActive");
        passwordField.classList.add("input-error");
    }else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(passwordField.value)){
        errorPasswordEmpty.classList.remove("errorActive");
        errorPasswordPattern.classList.add("errorActive");
        passwordField.classList.add("input-error");
    }else{
        errorPasswordPattern.classList.remove("errorActive");
        passwordField.classList.remove("input-error");
    }
    makeButtonClickableForLogin();
}



function makeButtonClickableForLogin(){
    if (passwordField.value && emailField.value){
        if ((/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(passwordField.value)) && (/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value))){
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
