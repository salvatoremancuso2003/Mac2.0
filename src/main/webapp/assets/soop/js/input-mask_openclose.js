var FormInputMask = function () {
    var a = function () {
//        for (var x = 0; x < 1000; x++) {
        //for (var x = 0; x < 100; x++) {
            //$("#nc_quantnow" + x).inputmask({mask: "9", repeat: 14, greedy: !1});
            //if (x < 100) {
          //      for (var y = 0; y < 20; y++) {
           //         $("#quantnow" + x + "_" + y).inputmask({mask: "9", repeat: 14, greedy: !1});
           //     }
            //}
//        }
    };
    return{init: function () {
            a();
        }};
}();
App.isAngularJsApp() === !1 && jQuery(document).ready(function () {
    FormInputMask.init();
});