/**
 * Created by Mortadhafff on 26/03/2017.
 */
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
                //console.log(resp);

                if (resp.error === undefined){
                    var html;
                    resp.forEach(function (item) {
                        if (item.author.id == user_id){

                            html = '<div class="message"><div class="bubble">'+ item.messageText +'<div class="corner"></div></div></div>';
                            $('#belousovMessageZone').append(html);
                        }
                        if (item.addressee.id == user_id){
                            html = '<div class="message right"><div class="bubble">'+ item.messageText +'<div class="corner"></div></div></div>';
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
    $('.friend').click(function () {
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

                    console.log(response);
                    response.forEach(function (item) {
                        if (item.author.id == user_id){

                            html = '<div class="message"><div class="bubble">'+ item.messageText +'<div class="corner"></div></div></div>';
                            $('#belousovMessageZone').append(html);
                        }
                        if (item.addressee.id == user_id){
                            html = '<div class="message right"><div class="bubble">'+ item.messageText +'<div class="corner"></div></div></div>';
                            $('#belousovMessageZone').append(html);
                        }
                    });
                }
            }
        });
    });
});