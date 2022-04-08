const submitBtn = document.getElementById("submitLogin");

const emailField = document.getElementById("form-input-email");
const passwordField = document.getElementById("password");
const repeatPasswordField = document.getElementById("passwordRepeat");
const firstNameField = document.getElementById("form-input-firstName");
const lastNameField = document.getElementById("form-input-lastName");
const phoneField = document.getElementById("form-input-phone");

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

function login(){
    console.log("logged in");
}

function createUser(){
    console.log("user created")
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
        if (!(/^\d+$/.test(phoneField.value))){
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

function makeButtonClickableForLogin(){
    if (passwordField.value && emailField.value){
        if ((/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(passwordField.value)) && (/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value))){
            submitBtn.disabled = false;
            submitBtn.style.cursor = "pointer";
        }
    }
}

function makeButtonClickableForCreateUser(){
    if (passwordField.value && emailField.value && firstNameField.value && lastNameField.value && phoneField.value && repeatPasswordField.value){
        if ((/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(passwordField.value)) && (/(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/.test(emailField.value))){
            if (repeatPasswordField.value === passwordField.value){
                if (/^\d+$/.test(phoneField.value)){
                    submitBtn.disabled = false;
                    submitBtn.style.cursor = "pointer";
                }
            }
        }
    }else{
        submitBtn.disabled = true;
        submitBtn.style.cursor = "default";
    }
}