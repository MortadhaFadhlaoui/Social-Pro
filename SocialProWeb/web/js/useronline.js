/**
 * Created by Mortadhafff on 06/04/2017.
 */
$(document).ready(function(){

    setInterval(function() {
        useronline();
    },30000);
    var ajax;
    function useronline() {
        $.ajax({
            type: "POST",
            url: ROOT_URL + "useronline/user",
            data: {},
            dataType: 'json',
            success: function(response){
                $('.online').html(response.userslist);
                $('#user-profiles-list-basic').addClass("#user-profiles-list-basic");
                $('#friendsideshow').addClass("#friendsideshow");
            },
            error: function() {
                console.log("no notif")
            }

        });
    }
    $('body').on('click', '#friendsideshow' ,function(){
        var e=document.getElementById("minim-chat");
        e.style.display="block";
        var e=document.getElementById("maxi-chat");
        e.style.display="none";
        var e=document.getElementById("chatbox");
        e.style.margin="0";
    });
    $('body').on('click', '.deletee' ,function(){
        $(this).parent().attr('data-id');
    });
});