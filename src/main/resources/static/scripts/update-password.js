const submitBtn = document.getElementById("submitUpdateUser");

const oldPasswordField = document.getElementById("oldPassword");
const newPasswordField = document.getElementById("newPassword");
const newRepeatPasswordField = document.getElementById("newPasswordRepeat")

const errorOldPasswordEmpty = document.getElementById("emptyOldPasswordFieldErrorText");
const errorOldPasswordPattern = document.getElementById("patternErrorTextOldPassword");

const errorNewPasswordEmpty = document.getElementById("emptyPasswordFieldErrorText");
const errorNewPasswordPattern = document.getElementById("patternErrorTextNewPassword");

const errorRepeatPasswordEmpty = document.getElementById("emptyRepeatPasswordFieldErrorText");
const errorRepeatPasswordPattern = document.getElementById("noPasswordMatch");


function login(){
    console.log("updated password");
}

/**
 * This function will be called when the status of HTTP response updates.
 * Typically, when the response has come completely, but for large responses this method could be called several
 * times during the download of the response. We therefore need to check the .readyState and .status properties.
 */
const asyncRequest = new XMLHttpRequest();
asyncRequest.onreadystatechange = onResponseReceived;


function updatePassword() {
    const data = {
        oldPassword: document.getElementById("oldPassword").value.toString(),
        newPassword: document.getElementById("newPassword").value.toString(),
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "http://localhost:8080/customers/update-password");
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
    }else{
        errorNewPasswordPattern.classList.remove("errorActive");
        newPasswordField.classList.remove("input-error");
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
