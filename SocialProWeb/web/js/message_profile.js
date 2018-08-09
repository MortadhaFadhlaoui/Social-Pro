/**
 * Created by Mortadhafff on 26/03/2017.
 */
$(document).ready(function(){
    setInterval(function() {
        if (ajax) { ajax.abort(); }
        getMessage();
    },30000);
    setInterval(function() {
        setVu();
    },30000);
    var ajax;
    $(".searchfieldd").on('keyup', function() {
        var input = $(this).val();
        if ( input.length > 1 ) {
            $('#match').html('<img src="' + window.loader + '" />');
            $('.friendsforever').hide();
            var data = {input: input};
            $.ajax({
                type: "POST",
                url: ROOT_URL + "ajax/autocomplete/update/data",
                data: data,
                cache: "false",
                dataType: 'json',
                success: function(response){
                    $('#match').html(response.usersList);
                },
                error: function() { // if error
                    $('#match').text('Nothing Found!');
                }
            });
        } else {
            //$('#match').text(''); // If less than 2 characters, clear the <div id="match"></div>
            $('.friendsforsearch').hide();
            $('.friendsforever').show();
            //$("#match").replaceWith(divClone.clone()); // Restore element with a copy of divClone
        }
    });
    $('body').on('mouseup', '.belousovMessageText' ,function(){
    //$('.belousovMessageText').mouseup(function(){
        var addressee = $('#belousovChat').attr('data-addressee');
        var urlll = $('#belousovChat').attr('data-vu');
        $.ajax({
            type: "POST",
            url: urlll,
            data: {'addressee' : addressee},
            cache: "false",
            dataType: "json",
            success: function (response) {
            }
        });
    });
    $('body').on('click', '.belousovSendMessage' ,function(){
        //$('.belousovSendMessage').click(function () {
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
                    resp.body.forEach(function (item) {
                        var monnow =  new Date().getMonth();
                        var daynow =  new Date().getDate();
                        var sec =  new Date(item.dateEnvoyer.date).getSeconds();
                        var her =  new Date(item.dateEnvoyer.date).getHours();
                        var min =  new Date(item.dateEnvoyer.date).getMinutes();
                        var day =  new Date(item.dateEnvoyer.date).getDate();
                        var mon =  new Date(item.dateEnvoyer.date).getMonth();
                        var year =  new Date(item.dateEnvoyer.date).getFullYear();
                        var datestring;
                        console.log(daynow);
                        console.log(day);
                        if ((monnow-mon)==0)
                        {
                            if ((daynow-day)==0)
                            {
                                datestring = (her+1) + "h:" + min+"min";
                            }
                            else
                            {
                                 datestring = daynow-day+" days ago";
                            }
                        }else
                        {
                            datestring = day  + "-" + (mon+1) + "-" + year;
                        }
                        if (item.author.id == user_id){
                            if (resp.profileuser == undefined)
                            {
                                html = '<div class="message"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;padding-left: 20px;">'+datestring+'</span></div></div></div>';
                            }
                            else
                            {
                                html = '<div class="message"><img src="/Social_Pro/web/img/authors/'+resp.profileuser+'" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;padding-left: 20px;">'+datestring+'</span></div></div></div>';
                            }
                            //<img src="/Social_Pro/web/img/authors/'+resp.profileuser+'" />
                            $('#belousovMessageZone').append(html);
                        }
                        if (item.addressee.id == user_id){
                            if (resp.profileadd == undefined)
                            {
                                html = '<div class="message right"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;">'+datestring+'</span></div></div></div>';
                            }
                            else
                            {
                                html = '<div class="message right"><img src="/Social_Pro/web/img/authors/'+resp.profileadd+'" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;">'+datestring+'</span></div></div></div>';
                            }
                            //<img src="/Social_Pro/web/img/authors/'+resp.profileadd+'" />
                            $('#belousovMessageZone').append(html);
                        }
                    });
                    $.ajax({
                        type: 'POST',
                        url: url,
                        data: {'user_id': user_id, 'addressee_id': addressee, 'update' : true},
                        dataType: 'json',
                        success: function (resp) {
                            console.log("hello");
                        }
                    });
                }
                setTimeout(function(){
                    getMessage()
                },1000)
            }
        });
    }
   $('body').on('click', '.friend' ,function(){
       // $('.friend').click(function () {
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
                    response.body.forEach(function (item) {
                        var monnow =  new Date().getMonth();
                        var daynow =  new Date().getDate();
                        var sec =  new Date(item.dateEnvoyer.date).getSeconds();
                        var her =  new Date(item.dateEnvoyer.date).getHours();
                        var min =  new Date(item.dateEnvoyer.date).getMinutes();
                        var day =  new Date(item.dateEnvoyer.date).getDate();
                        var mon =  new Date(item.dateEnvoyer.date).getMonth();
                        var year =  new Date(item.dateEnvoyer.date).getFullYear();
                        var datestring;
                        console.log(daynow);
                        console.log(day);
                        if ((monnow-mon)==0)
                        {
                            if ((daynow-day)==0)
                            {
                                datestring = her + "h:" + min+"min";
                            }
                            else
                                {
                                    datestring = daynow-day+" days ago";
                                }
                        }else
                        {
                            datestring = day  + "-" + (mon+1) + "-" + year;
                        }
                        if (item.author.id == user_id){
                            if (response.profileuser == undefined)
                            {
                                html = '<div class="message"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;padding-left: 20px;">'+datestring+'</span></div></div></div>';
                            }
                            else
                            {
                                html = '<div class="message"><img src="/Social_Pro/web/img/authors/'+response.profileuser+'" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;padding-left: 20px;">'+datestring+'</span></div></div></div>';
                            }
                        //<img src="/Social_Pro/web/img/authors/'+item.profileuser+'" />
                            $('#belousovMessageZone').append(html);
                        }
                        if (item.addressee.id == user_id){
                            if (response.profileadd == undefined)
                            {
                                html = '<div class="message right"><img src="/Social_Pro/web/img/authors/user-avatar-placeholder.png" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;">'+datestring+'</span></div></div></div>';
                            }
                            else
                            {
                                html = '<div class="message right"><img src="/Social_Pro/web/img/authors/'+response.profileadd+'" /><div class="bubble">'+ item.messageText +'<div class="corner"><span style="width: 80px;">'+datestring+'</span></div></div></div>';
                            }
                       // <img src="/Social_Pro/web/img/authors/'+item.profileadd+'" />
                            $('#belousovMessageZone').append(html);
                        }
                    });
                }
            }
        });
    });
    function setVu() {
        var urll = $('#Nonvu').attr('data-nonvu');
        var sp = $('#nonlu span').text();
        $.ajax({
            type: "POST",
            url: urll,
            data: {'sp' : sp},
            cache: "false",
            dataType: "json",
            success: function (response) {
                var html;
                $('#nonlu').html('');
                html = '<span class="label label-success">'+ response.NonVu +'</span>';
                $('#nonlu').append(html);
                setTimeout(function(){
                    setVu()
                },1000)
            }
        });
    }
});