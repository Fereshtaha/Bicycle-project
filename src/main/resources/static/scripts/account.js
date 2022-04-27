const asyncRequest = new XMLHttpRequest();

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/customers/authenticated-customer");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;


    let firstNameField = document.getElementById("form-input-firstName");
    let lastNameField = document.getElementById("form-input-lastName");
    let emailField = document.getElementById("form-input-email");
    let dob = document.getElementById("form-input-dob");
    let phone = document.getElementById("form-input-phone");

    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);

        firstNameField.value = responseJson.firstName;
        lastNameField.value = responseJson.lastName;
        dob.value = responseJson.dob;
        phone.value = responseJson.phone;
        emailField.value = responseJson.email;
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

const submitBtn = document.getElementById("submitBtn");

const oldPasswordField = document.getElementById("form-input-oldPassword");
const newPasswordField = document.getElementById("form-input-newPassword");
const newRepeatPasswordField = document.getElementById("form-input-repeatPassword");

const errorOldPasswordEmpty = document.getElementById("emptyOldPasswordFieldErrorText");
const errorOldPasswordPattern = document.getElementById("patternErrorTextOldPassword");

const errorSamePassword = document.getElementById("newPasswordIsSameAsOld");
const errorNewPasswordEmpty = document.getElementById("emptyPasswordFieldErrorText");
const errorNewPasswordPattern = document.getElementById("patternErrorTextNewPassword");

const errorRepeatPasswordEmpty = document.getElementById("emptyRepeatPasswordFieldErrorText");
const errorRepeatPasswordPattern = document.getElementById("noPasswordMatch");

function updatePassword() {
    const data = {
        oldPassword: document.getElementById("form-input-oldPassword").value.toString(),
        newPassword: document.getElementById("form-input-newPassword").value.toString(),
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "/customers/update-password");
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
                responseMessage.innerHTML = "<p id='successMessageText'>Password updated.</p>";
            } else if (this.status === 401){
                responseMessage.removeAttribute("hidden");
                responseMessage.classList.add("errorResponse");
                responseMessage.innerHTML = "<p id='successMessageText'>The password doesn't match this account.</p>";
            }else{
                responseMessage.removeAttribute("hidden");
                responseMessage.classList.add("errorResponse");
                responseMessage.innerHTML = "<p id='successMessageText'>An error occurred, please try again.</p>";
            }
            oldPasswordField.value = "";
            newPasswordField.value = "";
            newRepeatPasswordField.value = "";
        }
    }
}




function checkOldPassword(){
    if (!oldPasswordField.value || oldPasswordField.value === ""){
        errorOldPasswordEmpty.classList.add("errorActive");
        errorOldPasswordPattern.classList.remove("errorActive");
        oldPasswordField.classList.add("input-error");
    }else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(oldPasswordField.value)){
        errorOldPasswordEmpty.classList.remove("errorActive");
        errorOldPasswordPattern.classList.add("errorActive");
        oldPasswordField.classList.add("input-error");
    }else{
        errorOldPasswordPattern.classList.remove("errorActive");
        oldPasswordField.classList.remove("input-error");
    }
    makeButtonClickableForUpdatePassword();
}

function checkNewPassword(){
    if (!newPasswordField.value || newPasswordField.value === ""){
        errorNewPasswordEmpty.classList.add("errorActive");
        errorNewPasswordPattern.classList.remove("errorActive");
        newPasswordField.classList.add("input-error");
    }else if (!/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(newPasswordField.value)){
        errorNewPasswordEmpty.classList.remove("errorActive");
        errorNewPasswordPattern.classList.add("errorActive");
        newPasswordField.classList.add("input-error");
    }else if (newPasswordField.value === oldPasswordField.value){
        errorNewPasswordPattern.classList.remove("errorActive");
        newPasswordField.classList.remove("input-error");
        errorSamePassword.classList.add("errorActive");
    }else{
        errorNewPasswordPattern.classList.remove("errorActive");
        newPasswordField.classList.remove("input-error");
        errorSamePassword.classList.remove("errorActive");
    }
    makeButtonClickableForUpdatePassword();
}

function checkPasswordRepeat(){
    if (!newRepeatPasswordField.value  || newRepeatPasswordField === ""){
        errorRepeatPasswordEmpty.classList.add("errorActive");
        newRepeatPasswordField.classList.add("input-error");
    }else{
        if (!(newRepeatPasswordField.value === newPasswordField.value)){
            errorRepeatPasswordPattern.classList.add("errorActive");
            newRepeatPasswordField.classList.add("input-error");
        }else{
            newRepeatPasswordField.classList.remove("input-error");
            errorRepeatPasswordPattern.classList.remove("errorActive");
            errorRepeatPasswordEmpty.classList.remove("errorActive");
        }
    }
    makeButtonClickableForUpdatePassword();
}



function makeButtonClickableForUpdatePassword(){
    if (newPasswordField.value && oldPasswordField.value && newRepeatPasswordField.value &&
        (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(newRepeatPasswordField.value)) && (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(newPasswordField.value)) && (/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/.test(oldPasswordField.value)) &&  (newRepeatPasswordField.value === newPasswordField.value) && (oldPasswordField.value !== newPasswordField.value)){
        submitBtn.disabled = false;
        submitBtn.style.cursor = "pointer";
    }else{
        submitBtn.disabled = true;
        submitBtn.style.cursor = "default";
    }
}



