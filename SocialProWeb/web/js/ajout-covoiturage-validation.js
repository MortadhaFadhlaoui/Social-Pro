/**
 * Created by Choura on 4/3/2017.
 */
$(function () {

    $.validator.setDefaults({
        errorClass: 'help-block animation-slideDown',
        highlight: function (element) {
            $(element)
                .closest('.form-group')
                .addClass('has-error');
        },
        unhighlight: function (element) {
            $(element)
                .closest('.form-group')
                .removeClass('has-error').addClass('has-success has-feedback');
        },
        errorPlacement: function (error, element) {
            if (element.prop('type') === 'checkbox') {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        }
    });


    $.validator.addMethod('validDate', function (value, element) {
        return this.optional(element)
            || new Date(value).getDate() >= new Date().getDate()
            && new Date(value).getYear() >= new Date().getYear()
            && new Date(value).getMonth() >= new Date().getMonth();
    }, 'La date de départ doit être supérieure à la date d\'aujourd\'hui.');

    $.validator.addMethod('validDate2', function (value, element) {
        var timeNow = new Date();
        var tm = value;
        var timeParts = tm.split(":");
        var inputTime = new Date(timeNow.getYear(), timeNow.getMonth(), timeNow.getDate(), parseInt(timeParts[0]), parseInt(timeParts[1]), 0, 0);
        if (new Date(document.getElementById('date').value).getDate() == new Date().getDate()
            && new Date(document.getElementById('date').value).getYear()== new Date().getYear()
            && new Date(document.getElementById('date').value).getMonth() == new Date().getMonth()) {
            if (inputTime.getHours() > new Date().getHours())
                return true;
            else if (inputTime.getMinutes() > new Date().getMinutes())
                return true;
            else
                return false;
        }
        else if (new Date(document.getElementById('date').value).getDate() > new Date().getDate())
            return true;
        else
            return false;
    }, 'L\'Heure Doit Etre Supéireure à l\'Heure Actuelle');

    $.validator.addMethod('geoTrue', function (value) {
        var b = true;
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode( { 'address': value}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (document.getElementById('depart').value != results[0].formatted_address){
                    document.getElementById('hint').innerHTML = results[0].formatted_address;
                    document.getElementById('hintAsk').style.display = '';
                }
                b = true;
            }
            else
                b=false;
        });
        return b;
    }, 'Départ Invalid');

    $.validator.addMethod('geoTrue1', function (value) {
        var b = true;
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode( { 'address': value}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                if (document.getElementById('arrivee').value != results[0].formatted_address){
                    document.getElementById('hint1').innerHTML = results[0].formatted_address;
                    document.getElementById('hintAsk1').style.display = '';
                }
                b = true;
            }
            else
                b=false;
        });
        return b;
    }, 'Arrivée Invalid');

    $.validator.addMethod('same', function (value) {
        if ((document.getElementById('arrivee').value == document.getElementById('depart').value) && document.getElementById('arrivee').value != null && document.getElementById('depart').value != null )
            b=false;
        else
            b=true;
        return b;
    }, 'Le Départ et l\'Arrivée Doivent être Différents' );

    $("#addForm").validate({
        rules: {
            depart: {
                required: true,
                geoTrue: true,
                same: true
            },
            arrivee: {
                required: true,
                geoTrue1: true,
                same: true
            },
            date: {
                required: true,
                validDate: true
            },
            heure: {
                required: true,
                validDate2: true
            }
        },
        messages: {
            depart: {
                required: 'Ce Champ est Requis.'
            },
            arrivee: {
                required: 'Ce Champ est Requis.'
            },
            date: {
                required: 'Ce Champ est Requis.'
            },
            heure: {
                required: 'Ce Champ est Requis.'
            }
        }
    });

});