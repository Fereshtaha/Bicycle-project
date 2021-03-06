function loadColorsOnWindowLoad(){
    document.getElementById("bike-seat").classList.add("black-color")
    document.getElementById("bike-base").classList.add("grey-color");
    document.getElementById("bike-wheels").classList.add("black-color")
}
loadColorsOnWindowLoad();

function createEventsForColorButton(buttonType, imageType){
    for (let i=0; i<buttonType.length; i++) {
        buttonType[i].addEventListener('click', function (){
            for (let y = 0;y<imageType.classList.length;y++){
                if (imageType.classList[y].endsWith("-color")){
                    imageType.classList.remove(imageType.classList[y]);
                }
            }for (let x=0; x<buttonType.length; x++) {
                buttonType[x].classList.remove("color-button-selected");
            }
            imageType.classList.add(buttonType[i].value + "-color");
            buttonType[i].classList.add("color-button-selected");
        })
    }
}

function showMethodNotImplemented(){
    alert("method not implemented");
}

createEventsForColorButton(document.querySelectorAll(".color-button-base"),document.getElementById("bike-base"));
createEventsForColorButton(document.querySelectorAll(".color-button-seat"),document.getElementById("bike-seat"));
createEventsForColorButton(document.querySelectorAll(".color-button-wheel"),document.getElementById("bike-wheels"));



