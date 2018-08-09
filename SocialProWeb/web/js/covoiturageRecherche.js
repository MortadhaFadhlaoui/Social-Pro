$(document).ready(function(){
    $("#myTextField").on('keyup', function() { // everytime keyup event
        var input = $(this).val(); // We take the input value
        if ( input.length >= 2 ) { // Minimum characters = 2 (you can change)
            $("#listgroup").hide();
            $('#match').html('<img src="' + window.loader + '" />'); // Loader icon apprears in the <div id="match"></div>
            var data = {input: input}; // We pass input argument in Ajax
            $.ajax({
                type: "POST",
                url: ROOT_URL + "Coviturage/recherche", // call the php file ajax/tuto-autocomplete.php (check the routine we defined)
                data: data, // Send dataFields var
                dataType: 'json', // json method
                success: function(response){ // If success
                    $('#match').html(response.countryList); // Return data (UL list) and insert it in the <div id="match"></div>

                },
                error: function() { // if error
                    $('#match').text('Problem!');
                }
            });
        } else {
            $("#listgroup").show();
            $("#abc").hide();
        }
    });

});