 $(".date-picker").datepicker({
    rtl: App.isRTL(),
    orientation: "left",
    autoclose: true
}).on("change", function (e) {
    var firstDate = $(this).val();
    if (firstDate.length === 6 || firstDate.length === 8) {
        if (firstDate.includes("/")) {
            if (firstDate.length === 8) {
                var dd = firstDate.substr(0, 2);
                var mm = firstDate.substr(3, 2);
                var yy = firstDate.substr(6, 2);
                if (yy < 30) {
                    yy = parseInt("20" + yy);
                }
                $('.date-picker').datepicker("setDate", new Date(yy, mm - 1, dd));
            }
        } else {

            if (firstDate.length === 8) {
                var dd = firstDate.substr(0, 2);
                var mm = firstDate.substr(2, 2);
                var yyyy = firstDate.substr(4, 4);
                $('.date-picker').datepicker("setDate", new Date(yyyy, mm - 1, dd));
            }
            if (firstDate.length === 6) {
                var dd = firstDate.substr(0, 2);
                var mm = firstDate.substr(2, 2);
                var yy = firstDate.substr(4, 2);
                if (yy < 30) {
                    yy = parseInt("20" + yy);
                }
                $('.date-picker').datepicker("setDate", new Date(yy, mm - 1, dd));
            }
        }
    }
});