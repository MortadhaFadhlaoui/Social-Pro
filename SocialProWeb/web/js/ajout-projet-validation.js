/**
 * Created by Choura on 4/2/2017.
 */
$(function() {

    $.validator.setDefaults({
        errorClass: 'help-block animation-slideDown',
        highlight: function(element) {
            $(element)
                .closest('.form-group')
                .addClass('has-error');
        },
        unhighlight: function(element) {
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


    $.validator.addMethod('validDate', function(value, element) {
        return this.optional(element)
            || new Date(value) > new Date();
    }, 'La date de début du projet doit être supérieure à la date d\'aujourd\'hui.');

    $.validator.addMethod('validDate2', function(value, element) {
        return this.optional(element)
            || new Date(value) > new Date(document.getElementById('dated').value);
    }, 'La Date de Din du Projet Doit Etre Supérieure à Date Fin du Projet');



    $("#addForm").validate({
        rules: {
            nom: {
                required: true
            },
            desc: {
                required: true
            },
            dated: {
                required: true,
                validDate: true
            },
            datef: {
                required: true,
                validDate2: true
            }
        },
        messages: {
            nom: {
                required: 'Ce Champ est Requis.'
            },
            desc: {
                required: 'Ce Champ est Requis.'
            },
            dated: {
                required: 'Ce Champ est Requis.'
            },
            datef: {
                required: 'Ce Champ est Requis.'
            }
        }
    });

});