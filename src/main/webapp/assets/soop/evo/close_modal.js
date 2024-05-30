$(document).keypress(function (e) {
    if (e.which === 13) {
        if (!$('.modal').is(':visible')) {
        } else {
            $(".modal").each(function (index) {
                if ($(this).prop('id') === '') {
                } else {
                    $(this).modal('hide');
                    if ($(this).css("display") === 'none') {
                    } else {
                        $(this).removeClass("in");
                        $(this).css("display", "none");
                    }
                }
            });
            e.preventDefault();
            return false;
        }
    }
});
