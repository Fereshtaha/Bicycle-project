const divToBeFilled = document.getElementById("users-to-be-filled-from-db");

const asyncRequest = new XMLHttpRequest();

const queryString = window.location.pathname;
const lastSegment = queryString.split("/").pop();

if (lastSegment === "all-users"){
    getUserInfoFromDB();
}else if(lastSegment === "all-bikes"){
    getBikeInfoFromDB()
    document.getElementById("add-user").addEventListener("click", addNewBike);
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
                let color = bikeJson.color;
                let location = bikeJson.location;
                let pricePerMinute = bikeJson.pricePerMinute;
                let status = bikeJson.status;

                let tag = document.createElement("div");
                let selectTag = document.createElement("select");

                if (status.toString() === "NEW"){
                    selectTag.innerHTML =
                        '<select class="status">' +
                        '<option value="new" selected>NEW</option>'+
                        '<option value="available">AVAILABLE</option>'+
                        '<option value="rented">RENTED</option>'+
                        '</select>'
                }else if(status.toString() === "AVAILABLE"){
                    selectTag.innerHTML =
                        '<select class="status">' +
                        '<option value="new">NEW</option>'+
                        '<option value="available" selected>AVAILABLE</option>'+
                        '<option value="rented">RENTED</option>'+
                        '</select>'
                }else{
                    selectTag.innerHTML =
                        '<select class="status">' +
                        '<option value="new">NEW</option>'+
                        '<option value="available" selected>AVAILABLE</option>'+
                        '<option value="rented" selected>RENTED</option>'+
                        '</select>'
                }


                tag.innerHTML =
                    '<form method="post" action="/hjkahakhs">' +
                    '<label for="color">Color</label>' + '<input class="color" type="text" value="'+ color +'">' +
                    '<label for="location">Location</label>' + '<input class="location" type="text" value="'+ location +'">' +
                    '<label for="pricePerMinute">Price per minute (latitude , longitude)</label>' + '<input id="pricePerMinute" type="number" value="'+ pricePerMinute +'">' +
                    '<label for="status">Status</label>' +
                    '</form>'

                let form = tag.firstChild;
                form.appendChild(selectTag);

                const submitBtn = document.createElement("button");
                submitBtn.innerText = "Save"
                form.appendChild(submitBtn);

                divToBeFilled.appendChild(tag);

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

function addNewBike(){
    let tag = document.createElement("div");

    tag.innerHTML +=
        '<form method="post" action="/hjkahakhs">' +
        '<label for="color">Color</label>' +
        '<input type="text" class="color" name="color">' +
        '<label for="location">Location</label>' +
        '<input type="text" class="location" name="location">' +
        '<label for="pricePerMinute">Price per minute</label>' +
        '<input type="text" class="pricePerMinute" name="pricePerMinute">' +
        '<button type="submit">Create</button>' +
        '</form>'

    divToBeFilled.insertBefore(tag, divToBeFilled.firstChild);
}
