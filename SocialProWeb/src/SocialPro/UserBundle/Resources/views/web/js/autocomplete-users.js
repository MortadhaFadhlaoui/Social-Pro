$(document).ready(function(){
    setInterval(function() {
        if (ajax) { ajax.abort(); }
        getMessage();
    },30000);
    var ajax;
    $('.belousovSendMessage').click(function () {
        var message = $('.belousovMessageText');
        var addressee = $('#belousovChat').attr('data-addressee');
        var url = $('form[name=chat]').attr('action');
        if (addressee == "" || message == "") {
            alert('change dialog');
        }
        $.ajax({
            type: "POST",
            url: url,
            data: {'messageText': message.val(), 'addressee' : addressee},
            cache: "false",
            dataType: "json",
            success: function (response) {
                if (response.error === undefined){
                    message.val('');
                }
            }
        });
    });
    function getMessage(){
        var chat = $('#belousovChat');
        var user_id = chat.attr('data-author');
        var addressee = chat.attr('data-addressee');
        var url = chat.attr('data-action');
        ajax = $.ajax({
            type:'POST',
            url:url,
            data:{'user_id' : user_id, 'addressee_id' : addressee},
            dataType:'json',
            success:function(resp){
                if (resp.error === undefined){
                    var html;
                    resp.forEach(function (item) {
                        if (item.author.id == user_id){
                            html = '<div class="message"><img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" /><div class="bubble">'+ item.messageText +'<div class="corner"></div><span>3 min</span></div></div>';
                            $('#belousovMessageZone').append(html);
                        }
                        if (item.addressee.id == user_id){
                            html = '<div class="message right"><img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/2_copy.jpg" /><div class="bubble">'+ item.messageText +'<div class="corner"></div><span>1 min</span></div></div>';
                            $('#belousovMessageZone').append(html);
                        }
                    });
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: {'user_id': user_id, 'addressee_id': addressee, 'update' : true},
                        dataType: 'json',
                        success: function (resp) {
                        }
                    });
                }
                setTimeout(function(){
                    getMessage()
                },1000)
            }
        });
    }
    var divClone = $("#match").clone(); // Do this on $(document).ready(function() { ... })
    $("#searchfield").on('keyup', function() { // everytime keyup event
        var input = $(this).val(); // We take the input value
        if ( input.length >= 1 ) { // Minimum characters = 2 (you can change)
            //$('#match').html('<img src="' + window.loader + '" />'); // Loader icon apprears in the <div id="match"></div>
            $('#friends').hide();
            var data = {input: input}; // We pass input argument in Ajax
            $.ajax({
                type: "POST",
                url: ROOT_URL + "ajax/autocomplete/update/data", // call the php file ajax/tuto-autocomplete.php (check the routine we defined)
                data: data, // Send dataFields var
                dataType: 'json', // json method
                timeout: 3000,
                success: function(response){ // If success
                    $('#match').html(response.usersList); // Return data (UL list) and insert it in the <div id="match"></div>
                    $('#friends div').on('click', function() { // When click on an element in the list
                        var childOffset = $(this).offset();
                        var parentOffset = $(this).parent().parent().offset();
                        var childTop = childOffset.top - parentOffset.top;
                        var clone = $(this).find('img').eq(0).clone();
                        var top = childTop+12+"px";

                        $(clone).css({'top': top}).addClass("floatingImg").appendTo("#chatboxx");

                        setTimeout(function(){$("#profile p").addClass("animate");$("#profile").addClass("animate");}, 100);
                        setTimeout(function(){
                            $("#belousovMessageZone").addClass("animate");
                            $('.cx, .cy').addClass('s1');
                            setTimeout(function(){$('.cx, .cy').addClass('s2');}, 100);
                            setTimeout(function(){$('.cx, .cy').addClass('s3');}, 200);
                        }, 150);

                        $('.floatingImg').animate({
                            'width': "68px",
                            'left':'108px',
                            'top':'20px'
                        }, 200);
                        var name = $(this).find("p strong").html();
                        var email = $(this).find("p span").html();
                        $("#profile p").html(name);
                        $("#profile span").html(email);

                        $(".message").not(".right").find("img").attr("src", $(clone).attr("src"));
                        $('#friendslist').fadeOut();
                        $('#belousovChat').fadeIn();


                        $('#close').unbind("click").click(function(){
                            $("#belousovMessageZone, #profile, #profile p").removeClass("animate");
                            $('.cx, .cy').removeClass("s1 s2 s3");
                            $('.floatingImg').animate({
                                'width': "40px",
                                'top':top,
                                'left': '12px'
                            }, 200, function(){$('.floatingImg').remove()});

                            setTimeout(function(){
                                $('#belousovChat').fadeOut();
                                $('#friendslist').fadeIn();
                            }, 50);
                        });




                        var addressee = $(this).attr('data-number');
                        var chat = $('#belousovChat');
                        var url = chat.attr('data-action');
                        var user_id = chat.attr('data-author');
                        chat.attr('data-addressee', addressee);
                        $.ajax({
                            type: "POST",
                            url: url,
                            data: {'friend' : true, 'user_id' : addressee},
                            cache: "false",
                            dataType: "json",
                            success: function (response) {
                                var html;
                                $('#belousovMessageZone').html('');
                                if (ajax) { ajax.abort(); }
                                getMessage();
                                if (response) {
                                    response.forEach(function (item) {
                                        if (item.author.id == user_id){
                                            html = '<div class="message"><img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/1_copy.jpg" /><div class="bubble">'+ item.messageText +'<div class="corner"></div><span>3 min</span></div></div>';
                                            $('#belousovMessageZone').append(html);
                                        }
                                        if (item.addressee.id == user_id){
                                            html = '<div class="message right"><img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/245657/2_copy.jpg" /><div class="bubble">'+ item.messageText +'<div class="corner"></div><span>1 min</span></div></div>';
                                            $('#belousovMessageZone').append(html);
                                        }
                                    });
                                }
                            }
                        });
                        $('#match').text(''); // Clear the <div id="match"></div>
                            });
                    //$('#matchList li').on('click', function() { // When click on an element in the list
                      //  $('#searchfield').val($(this).text()); // Update the field with the new element
                        //$('#match').text(''); // Clear the <div id="match"></div>
                    //});
                },
                error: function() { // if error
                    $('#match').text('Nothing Found!');
                }
            });
        } else {
            $('#match').text(''); // If less than 2 characters, clear the <div id="match"></div>
            $('#friends').show();
            //$("#match").replaceWith(divClone.clone()); // Restore element with a copy of divClone
        }
    });
});