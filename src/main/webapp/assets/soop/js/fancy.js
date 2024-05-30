$('.fancybox').fancybox();
$("a.fancyBoxRaf").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    iframe: {
        preload: false // fixes issue with iframe and IE
    },
    centerOnScroll: true,
    width: 1400,
    overlayOpacity: 0,
    overlayShow: true
});
$("a.fancyBoxRafFULL").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    iframe: {
        preload: false // fixes issue with iframe and IE
    },
    fitToView: false,
    centerOnScroll: true,
    overlayOpacity: 0,
    overlayShow: true,
    autoSize        :  false,
    autoScale     : false,
    width         : '97%',
    height        : '97%'
});
$("a.fancyBoxRafreload").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    iframe: {
        preload: false // fixes issue with iframe and IE
    },
    centerOnScroll: true,
    width: 1400,
    afterClose: function () {
        location.reload();
    },
    overlayOpacity: 0,
    overlayShow: true
});
$("a.fancyBoxRafsubmitfirst").fancybox({
    
    
    
    
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    iframe: {
        preload: false // fixes issue with iframe and IE
    },
    centerOnScroll: true,
    width: 1400,
    afterClose: function () {
        // alert(document.forms[0].name);
//        document.forms[0].submit();
    },
    overlayOpacity: 0,
    overlayShow: true
});



