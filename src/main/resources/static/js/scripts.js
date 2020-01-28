$( document ).ready(function() {
    $('.user-info').hide();
    $('.filter-form').hide();
    $('.chart-table').hide();
    $('.show-filter').show();
    $('.show-table').show();
    $('.hide-table').hide();


});

$('.fa-info-circle').mouseenter(function(){
    $(this).next('.user-info').show();
});



$(document).on('click','.ajax-delete-user', function () {
    var id = $(this).parents('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/admin/user",
        data: "id=" + id,
        success: function (user) {
            $('#del_user_id').val(user.user_id);

            $('.modal-body-confirm').html("<strong style='color:red'>Подтвердите удаление пользователя</strong><br><br>" +
                "<strong>Имя пользователя </strong>" + user.userName + "<br><br>");
            $('#delete-user-modal').modal();
        }
    });
});


$(document).on('click','.delete-ajax-div', function () {
    var id = $(this).parents('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/divisions/delete",
        data: "id=" + id,
        success: function (div) {
            $('#del_div_id').val(div.division_id);

            $('.modal-body-confirm').html("<strong style='color:red'>Подтвердите удаление подразделения</strong><br><br>" +
                "<strong>Название подразделения: </strong>" + div.divisionName + "<br><br>");
            $('#delete-div-modal').modal();
        }
    });
});

$(document).on('click','.ajax-delete-sub', function () {
    var id = $(this).parents('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/subscribers/delete",
        data: "id=" + id,
        success: function (sub) {
            $('#del_sub_id').val(sub.sub_id);
            if (sub.fullName == null)
                var name = "";
            else
                var name = sub.fullName;
            if (sub.internalNum == null)
                var intNum = "";
            else
                var intNum = sub.internalNum;
            if (sub.externalNum == null)
                var extNum = "";
            else
                var extNum = sub.externalNum;
            if (sub.division == null)
                var div = "";
            else
                var div = sub.division.divisionName;

            $('.modal-body-confirm').html("<strong style='color:red'>Подтвердите удаление абонента</strong><br><br><strong>ФИО: </strong>" + name + "<br>" +
                "<strong>Внутренний номер: </strong>" + intNum + "<br><strong>Внешний номер: </strong>" + extNum + "" +
                "<br><strong>Подразделение: </strong>" + div + "<br><br>");
            $('#delete-modal').modal();
        }
    });
});

$(document).on('click','.ajax-edit-sub',function () {
    var id = $(this).parents('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/subscribers/edit",
        data: "id="+ id,
        success: function(sub){
            $('.edit-form')[0].reset();
            $('#sub_id').val(sub.sub_id);
            $('#_fullName').val(sub.fullName);
            $('#_internalNum').val(sub.internalNum);
            $('#_externalNum').val(sub.externalNum);
            $('#_building').val(sub.building);
            $('#_room').val(sub.room);
            $('#_info').val(sub.subDescr);
            $('#_gpStrip').val(sub.gpStrip);
            $('#_allocation').val(sub.allocation);
            $('#_cos').val(sub.cos);
            $('#_isDigital').attr('checked', sub.digital);
            $('#_isFax').attr('checked', sub.fax);
            $('#_isSip').attr('checked',sub.sip);

            if (sub.division != null) {
                var div_id = sub.division.division_id;
            }
            else
            {
                $('#_division').val('0').prop('selected', true);
            }

            $.ajax({
                type: "GET",
                url: "/divisions/ajax",
                success: function(divs) {
                    var options = $('#_division');

                    for (var i=0; i < divs.length; i++) {
                        if (sub.division != null){
                            if (div_id === divs[i].division_id){
                                options.append('<option selected="selected" value="' + divs[i].division_id + '">' + divs[i].divisionName + '</option>');
                            }
                            else
                            {
                                options.append('<option value="' + divs[i].division_id + '">' + divs[i].divisionName + '</option>');
                            }
                        }
                        else
                        {
                            options.append('<option value="' + divs[i].division_id + '">' + divs[i].divisionName + '</option>');
                        }

                    }
                }
            });
            $('#edit-modal').modal();
        }
    });
});

$(document).on('click','.ajax-sub-create',function () {
    $.ajax({
        type: "GET",
        url: "/divisions/ajax",
        success: function (divs) {
            var options = $('#c_division');
            for (var i=0; i < divs.length; i++) {
                options.append('<option value="' + divs[i].division_id + '">' + divs[i].divisionName + '</option>');
            }
        }
    });

    $('#create-modal').modal();
});

$(document).on('click','.ajax-div-create', function () {
    $('.create-div')[0].reset();
    $('#create-modal-div').modal();
});

$(document).on('click','.ajax-edit-div',function () {
    var id = $(this).parents('tr').attr('id');
    $.ajax({
        type: "GET",
        url: "/divisions/edit",
        data: "id="+ id,
        success: function(div){
            $('.edit-div')[0].reset();
            $('#_division_id').val(div.division_id);
            $('#_divisionName').val(div.divisionName);
            $('#edit-modal-div').modal();
        }
    });
});

$('#btn-delete-user').click(function () {
    var id = $('#del_user_id').val();
    var data = {};
    data["user_id"] = id;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/admin/user",
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (d) {
                $('#delete-user-modal').modal('hide');
                $('#'+id).html("");
        }});
});

$('#btn-delete-div').click(function () {
    var id = $('#del_div_id').val();
    var data = {};
    data["division_id"] = id;
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/divisions/delete",
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (d) {
            if (d.length > 0){
                $('#delete-div-modal').modal('hide');
                var errorMsg = "";
                for (let i = 0; i<d.length; i++){
                    errorMsg+= "<li>";
                    if (d[i].fullName != null){
                        errorMsg+= d[i].fullName + ", ";
                    }
                    if (d[i].internalNum != null){
                        errorMsg+= d[i].internalNum + ", ";
                    }
                    if (d[i].externalNum != null){
                        errorMsg+= d[i].externalNum + ", ";
                    }
                    errorMsg += d[i].division.divisionName + "</li>";
                }
                $('#error-body').html(errorMsg);
                $('#error-block').css('display','block');
            }
            else
            {
                $('#delete-div-modal').modal('hide');
                $('#'+id).html("");
            }

        }});
});

$('#btn-edit-div').click(function () {
    var data = {};
    data['division_id'] = $('#_division_id').val();
    data['divisionName'] = $('#_divisionName').val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/divisions/update",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (d) {
            $('td[data-id=' + d.division_id + ']').html(d.divisionName);
            $('#edit-modal-div').modal('hide');
        }
    });

});

$('#btn-save').click(function () {
    var data = {};
    var divisionObj = {};
    data["sub_id"] = $('#sub_id').val();
    data["fullName"] = $('#_fullName').val();
    data["internalNum"] = $('#_internalNum').val();
    data["externalNum"] = $('#_externalNum').val();
    data["building"] = $('#_building').val();
    data["room"] = $('#_room').val();
    divisionObj["division_id"] = $('#_division option:selected').val();
    divisionObj["divisionName"] = $('#_division option:selected').text();
    data["subDescr"] = $('#_info').val();
    data["gpStrip"] = $('#_gpStrip').val();
    data["allocation"] = $('#_allocation').val();
    data["cos"] = $('#_cos').val();
    data["fax"] = !!$('#_isFax').prop('checked');
    data["digital"] = !!$('#_isDigital').prop('checked');
    data["sip"] = !!$('#_isSip').prop('checked');
    data["division"] = divisionObj;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/subscribers/update",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (p) {
            var row = document.getElementById(p.sub_id);
            if (p.division != null) var divisionName = p.division.divisionName;
            if (p.digital)
                var digital = "<i class='fas fa-plus'></i>";
            else
                var digital = "<i class='fas fa-minus'></i>";
            if (p.fax)
                var fax = "<i class='fas fa-plus'></i>";
            else
                var fax = "<i class='fas fa-minus'></i>";
            if (p.sip)
                var sip = "<i class='fas fa-plus'></i>";
            else
                var sip = "<i class='fas fa-minus'></i>";
            var edit = "<a href= \"#\" class='btn btn-primary btn-sm ajax-edit-sub'><i class='fas fa-user-edit sm-2'></i></a>";
            var delet = "<a href= \"#\" class='btn btn-primary btn-sm ajax-delete-sub'><i class='fas fa-user-times sm-2'></i></a>";


            row.innerHTML =
                "<td>" + p.sub_id +"</td><td>​"+ p.fullName +"</td><td>"+ p.internalNum +"</td>" +
                "<td>"+ p.externalNum +"</td><td>"+ p.building +"</td><td>"+ p.room +"</td>" +
                "<td>"+ divisionName +"</td><td>"+ p.subDescr +"</td><td>"+ p.gpStrip +"</td>" +
                "<td>"+ p.allocation +"</td><td>"+ digital +"</td><td>"+ p.cos +"</td><td>"+ fax +"</td>" +
                "<td>"+ sip +"</td><td>"+ edit +"</td><td>"+ delet +"</td>";
            $('#edit-modal').modal('hide');
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].';
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            alert(msg);
        }
    });

});

$('#btn-create').click(function () {
    var data = {};
    var divisionObj = {};
    data["fullName"] = $('#c_fullName').val();
    data["internalNum"] = $('#c_internalNum').val();
    data["externalNum"] = $('#c_externalNum').val();
    data["building"] = $('#c_building').val();
    data["room"] = $('#c_room').val();
    divisionObj["division_id"] = $('#c_division option:selected').val();
    divisionObj["divisionName"] = $('#c_division option:selected').text();
    data["subDescr"] = $('#c_info').val();
    data["gpStrip"] = $('#c_gpStrip').val();
    data["allocation"] = $('#c_allocation').val();
    data["cos"] = $('#c_cos').val();
    data["fax"] = !!$('#c_isFax').prop('checked');
    data["digital"] = !!$('#c_isDigital').prop('checked');
    data["sip"] = !!$('#c_isSip').prop('checked');
    data["division"] = divisionObj;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/subscribers/create",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (p) {
            $('#create-modal').modal('hide');
            window.location.href = "/subscribers"
        },
        error: function (jqXHR, exception) {
            var msg = '';
            if (jqXHR.status === 0) {
                msg = 'Not connect.\n Verify Network.';
            } else if (jqXHR.status == 404) {
                msg = 'Requested page not found. [404]';
            } else if (jqXHR.status == 500) {
                msg = 'Internal Server Error [500].';
            } else if (exception === 'parsererror') {
                msg = 'Requested JSON parse failed.';
            } else if (exception === 'timeout') {
                msg = 'Time out error.';
            } else if (exception === 'abort') {
                msg = 'Ajax request aborted.';
            } else {
                msg = 'Uncaught Error.\n' + jqXHR.responseText;
            }
            alert(msg);
        }
    });

});

$('#btn-delete').click(function () {
    var data = {};
    data["sub_id"] = $('#del_sub_id').val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/subscribers/delete",
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (p) {
            $('#delete-modal').modal('hide');
            $('#'+p.sub_id).html("");
        }});
});

$('#btn-create-div').click(function () {
    var data = {};
    data['divisionName'] = $('#c_divisionName').val();

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/divisions/create",
        data: JSON.stringify(data),
        dataType: 'json',
        timeout: 600000,
        success: function (p) {
            $('#create-modal-div').modal('hide');
            window.location.href = "/divisions"
        }
    });

});

$('.fa-info-circle').mouseleave(function(){
    $('.user-info').hide();
});

$('.filter-button').click(function(){
    if ( $( ".filter-form" ).first().is( ":hidden" ) ) {
        $( ".filter-form" ).show();
    } else {
        $( ".filter-form" ).hide();
    }
});

window.onload = function () {
    document.body.classList.add('loaded_hiding');
    window.setTimeout(function () {
        document.body.classList.add('loaded');
        document.body.classList.remove('loaded_hiding');
    }, 500);
}