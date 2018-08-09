$(document).ready(function(){
    setInterval(function() {
        sendnotif();
    },30000);
    var ajax;
    var value,
        element = parseInt(document.getElementById('cont'));
    if (element != null) {
        value = element.value;
    }else {
        value = null;
    }
    value = isNaN(value) ? 0 : value;


    var valuegroupe,
        elementgroupe = parseInt(document.getElementById('contgroupe'));
    if (elementgroupe != null) {
        valuegroupe = elementgroupe.value;
    }else {
        valuegroupe = null;
    }
    valuegroupe = isNaN(valuegroupe) ? 0 : valuegroupe;
    $(".searchfielddd").on('keyup', function() {
        var input = $(this).val();
        if ( input.length > 1 ) {
            //$('#usersinvit').show();
            $('#usersinvit').html('<img src="' + window.loader1 + '" />');
            var data = {input: input};
            $.ajax({
                type: "POST",
                url: ROOT_URL + "ajax/autocomplete/search",
                data: data,
                dataType: 'json',
                success: function(response){
                    var fruits = [];
                    var hi = response.countryList;

                    //var content4 = temp.html();
                    $('#list div').each(function () {
                        var rest = $(this).text().substring(0, $(this).text().length-1);
                        fruits.push(rest);
                            for(var ii = 0; ii <response.resultat.length; ii++ ){
                                var
                                    item = response.resultat[ii];

                                 for(var jj = 0; jj <fruits.length; jj++ ){
                               if (response.resultat[ii]==fruits[jj])
                               {
                                   $('#usersinvit').hide(hi);
                               }else
                               {
                                   //$('#usersinvit').html(hi);
                                   $('#usersinvit').show();
                               }

                             }

                            }

                    });
                    $('#usersinvit').html(hi); // Return data (UL list) and insert it in the <div id="match"></div>
                    $('#matchList li').on('click', function() { // When click on an element in the list
                        //$('#hello').val($(this).text()); // Update the field with the new element
                        value++;
                        html = '<div><input id="'+value+'" name="'+value+'" value="'+$(this).text()+'" class="tag label label-info">'+$(this).text() +'<button id="closeuser" class="close" type="button" aria-hidden="true" style="padding-right: 340px;margin-top: -6px;">×</button><br><br></div>';
                        $('#list').append(html);
                        document.getElementById('cont').value = value;
                       // $('.searchfielddd').val('');
                        $('#usersinvit').text(''); // Clear the <div id="usersinvit"></div>
                    });
                },
                error: function() { // if error
                    $('#usersinvit').text('nothing found!');
                }

            });
        }
        else
            {
                $('#matchList').hide();
            }
    });
    $(document).on('click', '#closeuser', function() {
        $(this).parent().remove();
        value--;
        document.getElementById('cont').value = value;
    });

    function sendnotif() {
        $.ajax({
            type: "POST",
            url: ROOT_URL + "ajax/autocomplete/sendnotif",
            data: {},
            dataType: 'json',
            success: function(response){ // If success
                var hi = response.countryList;
                $('#nott').html(response.countryList); // Return data (UL list) and insert it in the <div id="match"></div>
                $('#numnotif').html(response.nombrenotif);
                $('#txtnotif').html(response.txtnotif);
            },
            error: function() {
                console.log("no notif")
            }

        });
    }
    $('body').on('click', '#accepter' ,function() {
        $(this).parent().attr('data-id');
        var accepter = $('#accepter');
        var input = $(this).parent().attr('data-id');
        console.log(input);
        $.ajax({
            type: "POST",
            url: ROOT_URL + "ajax/autocomplete/accepternotif",
            data: {'input' : input},
            dataType: 'json',
            success: function(response){
                console.log(input);
            },
            error: function() {
                console.log(input);
            }

        });
    });
    $('body').on('click','#refuser', function() {
        var accepter = $('#refuser');
        var input = accepter.attr('data-id');
        $(this).parent().parent().hide();
        console.log("accepter");
        $.ajax({
            type: "POST",
            url: ROOT_URL + "ajax/autocomplete/refusernotif",
            data: {'input' : input},
            dataType: 'json',
            success: function(response){
                console.log("refuser");
            },
            error: function() {
                console.log("refuserrr");
            }

        });
    });
    $(".searchgroups").on('keyup', function() {
        var input = $(this).val();
        if ( input.length > 1 ) {
            $('.matchvosgroupes').html('<img src="' + window.loader2 + '" />');
            $('#vosgroupespanel').hide();
            $('#groupesvosgerepanel').hide();
            var data = {input: input};
            $.ajax({
                type: "POST",
                url: ROOT_URL + "searchgroups/ajax",
                data: data,
                cache: "false",
                dataType: 'json',
                success: function(response){
                   $('.matchvosgroupes').html(response.vosgroupsList);
                   $('.matchgroupesgere').html(response.groupsgerer);
                   console.log(response.groupsgerer);
                    //$('.matchgroupesgere').html(response.groupsgerer);
                },
                error: function() { // if error
                    $('.matchvosgroupes').text('Nothing Found!');
                }
            });
        } else {
            //$('#match').text(''); // If less than 2 characters, clear the <div id="match"></div>
            $('#vosgroupespanel').show();
            $('#groupesvosgerepanel').show();
            $('#lolking').hide();
            $('#lolkinglolking').hide();
            //$("#match").replaceWith(divClone.clone()); // Restore element with a copy of divClone
        }
    });
    $(".searchfieldowner").on('keyup', function() {
        var input = $(this).val();
        if ( input.length > 1 ) {
            //$('#usersinvit').show();
            $('#usersinvit').html('<img src="' + window.loader1 + '" />');
            var data = {input: input};
            $.ajax({
                type: "POST",
                url: ROOT_URL + "ajax/autocomplete/search/owner",
                data: data,
                dataType: 'json',
                success: function(response){
                    var fruits = [];
                    var hi = response.countryList;

                    //var content4 = temp.html();
                    $('#list div').each(function () {
                        var rest = $(this).text().substring(0, $(this).text().length-1);
                        fruits.push(rest);
                        for(var ii = 0; ii <response.resultat.length; ii++ ){
                            var
                                item = response.resultat[ii];

                            for(var jj = 0; jj <fruits.length; jj++ ){
                                if (response.resultat[ii]==fruits[jj])
                                {
                                    $('#usersinvit').hide(hi);
                                }else
                                {
                                    //$('#usersinvit').html(hi);
                                    $('#usersinvit').show();
                                }

                            }

                        }

                    });
                    $('#usersinvit').html(hi); // Return data (UL list) and insert it in the <div id="match"></div>
                    $('#matchList li').on('click', function() { // When click on an element in the list
                        //$('#hello').val($(this).text()); // Update the field with the new element
                        valuegroupe++;
                        html = '<div><input id="'+valuegroupe+'" name="'+valuegroupe+'" value="'+$(this).text()+'" class="tag label label-info">'+$(this).text() +'<button id="closeuserr" class="close" type="button" aria-hidden="true" style="padding-right: 340px;margin-top: -6px;">×</button><br><br></div>';
                        $('#list').append(html);
                        document.getElementById('contgroupe').valuegroupe = valuegroupe;
                        // $('.searchfielddd').val('');
                        $('#usersinvit').text(''); // Clear the <div id="usersinvit"></div>
                    });
                },
                error: function() { // if error
                    $('#usersinvit').text('nothing found!');
                }

            });
        }
        else
        {
            $('#matchList').hide();
        }
    });
    $(document).on('click', '#closeuserr', function() {
        $(this).parent().remove();
        valuegroupe--;
        document.getElementById('contgroupe').valuegroupe = valuegroupe;
    });

});