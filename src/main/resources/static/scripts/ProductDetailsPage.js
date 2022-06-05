const elementToBeFilled = document.getElementById("productsToBeFilledFromDB");

const asyncRequest = new XMLHttpRequest();

const queryString = window.location.pathname;
const lastSegment = queryString.split("/").pop();
console.log(lastSegment);

class Product {
    constructor(id, name, imageUrl , description, price) {
        this._id = id;
        this._name = name;
        this._imageUrl = imageUrl;
        this._price = price;
        this._description = description;
    }
    get id(){
        return this._id
    }

    set id(value){
        this._id = value;
    }

    get name() {
        return this._name;
    }

    set name(value) {
        this._name = value;
    }

    get imageUrl() {
        return this._imageUrl;
    }

    set imageUrl(value) {
        this._imageUrl = value;
    }

    get description() {
        return this._description;
    }

    set description(value) {
        this._description = value;
    }

    get price() {
        return this._price;
    }

    set price(value) {
        this._price = value;
    }
}

let product;

function getInfoFromDB() {
    asyncRequest.addEventListener("load", fillFieldsWithResponse);

    asyncRequest.open("GET", "/api/products/" + lastSegment);
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send();
    asyncRequest.onreadystatechange = onResponseReceivedFromGET;



    function fillFieldsWithResponse() {
        let responseJson = JSON.parse(this.responseText);

        let productId = lastSegment;
        let productName = responseJson.productName;
        let imageUrl = responseJson.imageUrl;
        let description = responseJson.description;
        let price = responseJson.price;

        product = new Product(productId,productName,imageUrl,description,price);

        elementToBeFilled.innerHTML =
            "<div id='image-container'>" +
            "<img class='productImage' src='/Images/" + imageUrl + "' alt='" + imageUrl+ "'>" +
            "</div>"+
            "<div id='description-container'>" +
            "<p class='productName'>" + productName + "</p>" +
            "<p class='productPrice'>" + price + " NOK" +  "</p>" +
            "<p class='productDescription'>" + description + "</p>" +
            "<button id='add-to-cart-button' onclick='addToCart()'>Add to chart</button>" +
            "</div>"



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
}

function addToCart(){
    const data = {
        id : lastSegment
    }
    let formData = JSON.stringify(data);
    asyncRequest.open("POST", "/api/products/addToCart");
    asyncRequest.setRequestHeader("Accept", "application/json");
    asyncRequest.setRequestHeader("Content-Type", "application/json");
    asyncRequest.send(formData);

    asyncRequest.onreadystatechange = onResponseReceivedFromPOST;

    function onResponseReceivedFromPOST() {
        if (this.readyState === XMLHttpRequest.DONE) {
            if (this.status === 200) { // handle success
                alert("Added to cart");
            } else if(this.status === 401){
                alert("Added to cart");
                let products = [];

                if(localStorage.getItem('products')){
                    products = JSON.parse(localStorage.getItem('products'));
                }


                products.push(product);
                console.log(product);
                console.log(localStorage.getItem("products"))


                localStorage.setItem("products",JSON.stringify(products))
            }else{
                console.log(this.status);
                alert("Something went wrong");
            }
        }
    }
}
getInfoFromDB();
