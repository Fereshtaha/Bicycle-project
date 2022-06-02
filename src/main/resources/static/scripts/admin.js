const divToBeFilled = document.getElementById("users-to-be-filled-from-db");

const asyncRequest = new XMLHttpRequest();

const queryString = window.location.pathname;
const lastSegment = queryString.split("/").pop();

if (lastSegment === "admin"){
    getUserInfoFromDB();
}else if(lastSegment === "all-bikes"){
    getBikeInfoFromDB()
    document.getElementById("add-user").addEventListener("click", createNewBike);
}else if(lastSegment === "all-products"){
    divToBeFilled.innerText = "Not implemented yet."
}

function getUserInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/customers");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;



    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);
        if (responseJson.length !== 0){
            for (let i = 0; i<responseJson.length;i++){
                let customerJson = responseJson[i];
                let firstName = customerJson.firstName;
                let email = customerJson.email;
                let active = customerJson.active;

                let tag = document.createElement("div");

                if (active.toString() === "true"){
                    tag.innerHTML =
                        '<label for="firstName">Firstname</label>' + '<input class="firstName" type="text" value="'+ firstName +'">' +
                        '<label for="email">Email</label>' + '<input class="email" type="email" value="'+ email +'">' +
                        '<label for="active">Active</label>' + '<input class="active" type="checkbox" checked>'
                }else{
                    tag.innerHTML =
                        '<label for="firstName">Firstname</label>' + '<input class="firstName" type="text" value="'+ firstName +'">' +
                        '<label for="email">Email</label>' + '<input class="email" type="email" value="'+ email +'">' +
                        '<label for="active">Active</label>' + '<input class="active" type="checkbox">'
                }
                divToBeFilled.appendChild(tag);
            }
        }else{
            divToBeFilled.innerText = "No products in database";
        }
    }
}

function getBikeInfoFromDB(){
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/api/bicycle");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;

    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);
        if (responseJson.length !== 0){
            for (let i = 0; i<responseJson.length;i++){
                let bikeJson = responseJson[i];
                let bikeId = bikeJson.id;
                let color = bikeJson.color;
                let location = bikeJson.location;
                let pricePerMinute = bikeJson.pricePerMinute;
                let status = bikeJson.status;

                let tag = document.createElement("div");
                let selectTag = document.createElement("select");
                selectTag.id = "bikeStatus" +bikeId.toString();

                if (status === "NEW"){
                    selectTag.innerHTML =
                        '<option value="NEW" selected>NEW</option>'+
                        '<option value="AVAILABLE">AVAILABLE</option>'+
                        '<option value="RENTED">RENTED</option>'+
                        '<option value="DISABLED">DISABLED</option>'
                }else if(status === "AVAILABLE"){
                    selectTag.innerHTML =
                        '<option value="NEW">NEW</option>'+
                        '<option value="AVAILABLE" selected>AVAILABLE</option>'+
                        '<option value="RENTED">RENTED</option>'+
                        '<option value="DISABLED">DISABLED</option>'
                }if(status === "DISABLED"){
                    selectTag.innerHTML =
                        '<option value="NEW">NEW</option>'+
                        '<option value="AVAILABLE">AVAILABLE</option>'+
                        '<option value="RENTED">RENTED</option>'+
                        '<option value="DISABLED" selected>DISABLED</option>'
                }else{
                    selectTag.innerHTML =
                        '<option value="NEW">NEW</option>'+
                        '<option value="AVAILABLE" selected>AVAILABLE</option>'+
                        '<option value="RENTED" selected>RENTED</option>'+
                        '<option value="DISABLED">DISABLED</option>'
                }


                tag.innerHTML =
                    '<form>' +
                    '<select id="bikeColor'+bikeId+'">' +
                        '<option value="Brown">Brown</option>' +
                        '<option value="Green">Green</option>' +
                        '<option value="Yellow">Yellow</option>' +
                    '</select> '+
                    '<label for="location">Location</label>' + '<input class="location" id="bikeLocation'+ bikeId + '" type="text" value="'+ location +'">' +
                    '<label for="pricePerMinute">Price per minute (latitude , longitude)</label>' + '<input id="bikePrice'+ bikeId + '" type="number" value="'+ pricePerMinute +'">' +
                    '<label for="status">Status</label>' +
                    '</form>'

                let form = tag.firstChild;
                let select = form.firstChild;

                for (let i = 0; i < select.length; i++) {
                    let tableChild = select[i];
                    if (tableChild.value === color){
                        select.selectedIndex = i;
                    }
                }

                form.appendChild(selectTag);

                const submitBtn = document.createElement("button");
                submitBtn.innerText = "SAVE"
                submitBtn.setAttribute("onclick", 'updateBikeInfo('+ bikeId +')');
                form.appendChild(submitBtn);

                const deleteBtn = document.createElement("button");
                deleteBtn.innerText = "DELETE"
                deleteBtn.setAttribute("onclick", 'deleteBike('+ bikeId +')');
                form.appendChild(deleteBtn);

                divToBeFilled.insertBefore(tag, divToBeFilled.firstChild);

            }
        }else{
            divToBeFilled.innerText = "No products in database";
        }
    }
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

function createNewBike(){
    let tag = document.createElement("div");

    tag.innerHTML +=
        '<form>' +
            '<label for="Color">Color</label>' +
            '<select id="newBikeColor">' +
                '<option value="Brown">Brown</option>' +
                '<option value="Green">Green</option>' +
                '<option value="Yellow">Yellow</option>' +
            '</select>'+
            '<label for="location">Location</label>' +
            '<input id="newBikeLocation" type="text" class="location" name="location">' +
            '<label for="pricePerMinute">Price per minute</label>' +
            '<input id="newBikePrice" type="number" class="pricePerMinute" name="pricePerMinute">' +
            '<button class="submitNewBikeBtn" type="button">Create</button>' +
        '</form>'

    divToBeFilled.insertBefore(tag, divToBeFilled.firstChild);

    const submit = document.querySelectorAll(".submitNewBikeBtn");

    submit.forEach((btn) => {
        btn.addEventListener("click", sendBikeToServer);
    });
}



function sendBikeToServer(){
    const color = document.getElementById("newBikeColor");
    const location = document.getElementById("newBikeLocation");
    const price = document.getElementById("newBikePrice")
    const status = "NEW";

    const data = {
        color : color.value,
        location : location.value,
        pricePerMinute : price.value,
        status : status.value
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "/api/bicycle");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(formData);

    asyncRequest.onreadystatechange = onResponseReceivedFromPOST;

    function onResponseReceivedFromPOST() {
        if (this.readyState === 4) {
            if (this.status === 200) { // handle success
                alert("Bike added");
                setTimeout(function(){
                    window.location.reload();
                });
            }else{
                console.log(this.status);
                alert("Something went wrong in post method");
            }
        }
    }
}


function updateBikeInfo(bikeNo){
    const color = document.getElementById("bikeColor" + bikeNo).value;
    const location = document.getElementById("bikeLocation" + bikeNo).value;
    const pricePerMinute = document.getElementById("bikePrice" + bikeNo).value;
    const status = document.getElementById("bikeStatus" + bikeNo).value;

    console.log("UPDATING...");
    const data = {
        id : bikeNo,
        color : color,
        location : location,
        pricePerMinute : parseInt(pricePerMinute),
        status : status
    }
    console.log(data);
    let formData = JSON.stringify(data);
    asyncRequest.open("PUT", "/api/bicycle", false);
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(formData);

    asyncRequest.onreadystatechange = onResponseReceivedFromPOST;

    function onResponseReceivedFromPOST() {
        if (this.readyState === 4) {
            if (this.status === 200) { // handle success
                alert("Bike added");
                setTimeout(function(){
                    window.location.reload();
                });
            }else{
                console.log(this.status);
                alert("Something went wrong in put method");
            }
        }
    }
}

function deleteBike(bikeId){
    let confirmAction = confirm("Are you sure you want to delete this bike?");
    if (confirmAction) {
        console.log(bikeId);
        asyncRequest.open("DELETE", "/api/bicycle/" + bikeId,false);
        asyncRequest.onreadystatechange = onResponseReceivedFromGET;
        asyncRequest.send();

        getBikeInfoFromDB()
    }
}