


$(document).ready(function(){

    let formData = JSON.stringify($("#create-user-form").serializeArray());
    
    // click on button submit
    $("#submit").on('click', function(){
        // send ajax
        $.ajax({
            type: "POST",
            url: "localhost:8080/customers",
            data: formData,
            dataType: "json",
            contentType : "application/json",
            success : function() {
                console.log("success");
            },
            error: function() {
                console.log("success");
            }
        })
    });
});