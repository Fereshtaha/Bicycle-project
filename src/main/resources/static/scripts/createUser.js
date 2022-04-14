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
const passwordField = document.getElementById("password");
const repeatPasswordField = document.getElementById("passwordRepeat");
const firstNameField = document.getElementById("form-input-firstName");
const lastNameField = document.getElementById("form-input-lastName");
const phoneField = document.getElementById("form-input-phone");
const birthdayField = document.getElementById("birthday");

const errorEmailEmpty = document.getElementById("emptyEmailFieldErrorText");
const errorEmailPattern = document.getElementById("patternEmailFieldErrorText");

const errorPasswordEmpty = document.getElementById("emptyPasswordFieldErrorText");
const errorPasswordPattern = document.getElementById("patternErrorText");

const errorFirstNameEmpty = document.getElementById("emptyFirstNameFieldErrorText");

const errorLastNameEmpty = document.getElementById("emptyLastNameFieldErrorText")

const errorPhoneEmpty = document.getElementById("emptyPhoneFieldErrorText")

const errorRepeatPasswordEmpty = document.getElementById("emptyRepeatPasswordFieldErrorText");

const errorNoPasswordMatch = document.getElementById("noPasswordMatch");

const errorNoRegexMatchPhone = document.getElementById("phoneFieldErrorRegex");

const errorEmptyBirthday = document.getElementById("birthday");

function makeButtonClickableForCreateUser(){
    if (passwordField.value && emailField.value && firstNameField.value && lastNameField.value && phoneField.value && repeatPasswordField.value && birthdayField.value){
        if ((/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(passwordField.value)) && (/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value))){
            if (/^[0-9]{8}$/.test(phoneField.value)){
                if (passwordField.value === repeatPasswordField.value){
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
        }else{
            submitBtn.disabled = true;
            submitBtn.style.cursor = "default";
        }
    }else{
        submitBtn.disabled = true;
        submitBtn.style.cursor = "default";
    }
}

function checkBirthday(){
    if (!birthdayField.value || birthdayField.value === ""){
        errorEmptyBirthday.classList.add("errorActive");
        birthdayField.classList.add("input-error");
        console.log(birthdayField.value);
    }else{
        errorEmptyBirthday.classList.remove("errorActive");
        birthdayField.classList.remove("input-error");
    }
    makeButtonClickableForCreateUser();
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
    makeButtonClickableForCreateUser();
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
    makeButtonClickableForCreateUser();
}

function checkFirstName(){
    if (!firstNameField.value){
        errorFirstNameEmpty.classList.add("errorActive");
        firstNameField.classList.add("input-error")
    }else{
        errorFirstNameEmpty.classList.remove("errorActive");
        firstNameField.classList.remove("input-error")
    }
    makeButtonClickableForCreateUser();
}

function checkLastName(){
    if (!lastNameField.value){
        errorLastNameEmpty.classList.add("errorActive");
        lastNameField.classList.add("input-error")
    }else{
        errorLastNameEmpty.classList.remove("errorActive");
        lastNameField.classList.remove("input-error")
    }
    makeButtonClickableForCreateUser();
}

function checkPhone(){
    if (!phoneField.value){
        errorPhoneEmpty.classList.add("errorActive");
        phoneField.classList.add("input-error")
    }else{
        if (!(/^[0-9]{8}$/.test(phoneField.value))){
            errorNoRegexMatchPhone.classList.add("errorActive");
        }else {
            errorPhoneEmpty.classList.remove("errorActive");
            phoneField.classList.remove("input-error")
            errorNoRegexMatchPhone.classList.remove("errorActive");
        }
    }
    makeButtonClickableForCreateUser();
}

function checkPasswordMatch(){
    if (!repeatPasswordField.value){
        errorRepeatPasswordEmpty.classList.add("errorActive");
        repeatPasswordField.classList.add("input-error");
    }else{
        if (!(repeatPasswordField.value === passwordField.value)){
            errorNoPasswordMatch.classList.add("errorActive");
            repeatPasswordField.classList.add("input-error");
        }else{
            repeatPasswordField.classList.remove("input-error");
            errorNoPasswordMatch.classList.remove("errorActive");
            errorRepeatPasswordEmpty.classList.remove("errorActive");
        }
    }
    makeButtonClickableForCreateUser();
}

