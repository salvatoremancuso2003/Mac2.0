$.fn.once = function (events, callback) {
    return this.each(function () {
        $(this).on(events, myCallback);
        function myCallback(e) {
            $(this).off(events, myCallback);
            callback.call(this, e);
        }
    });
};
$("body").on('select2:closing', function (e) {
    var $sel2 = $(e.target).data("select2");
    var $sel = $sel2.$element;
    var $selDropdown = $sel2.$results.find(".select2-results__option--highlighted");
    var newValue = $selDropdown.data("data").element.value;
    $("html").once('keyup mouseup', function (e) {
        var KEYS = {UP: 38, DOWN: 40, TAB: 9};
        if (e.keyCode === KEYS.TAB) {
            if (newValue !== undefined) {
                $sel.val(newValue);
                $sel.trigger('change');
            }
        }
    });
});