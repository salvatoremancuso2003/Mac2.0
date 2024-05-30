var FormInputMask = function (){
    var a = function () {
        $("#quantity112231").inputmask("999,999.999", {numericInput:!0});
    };
    return{init: function () {
            a();
        }};
}();
App.isAngularJsApp() === !1 && jQuery(document).ready(function () {
    FormInputMask.init();
});