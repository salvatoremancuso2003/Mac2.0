function convertDate_0(dataing) {
    var dd = dataing.split("-")[2].trim();
    var mm = dataing.split("-")[1].trim();
    var aa = dataing.split("-")[0].trim();
    var out = dd + "/" + mm + "/" + aa;
    return out;
}

function linkAE() {
    var win = window.open('https://telematici.agenziaentrate.gov.it/VerificaCF/Scegli.do?parameter=verificaCf', '_blank');
    win.focus();
}

function getRandom(length) {
    return Math.floor(Math.pow(10, length - 1) + Math.random() * 9 * Math.pow(10, length - 1));
}

function formatNumber_RAF(num) {
    return num.toString().replace(/[&\/\\#,+()$~%'":*?<>{}]/g, '').replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

function fieldmaggiorezero(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[];{}()|\":<>?£ààáâãäçèéêëìíîïñòóôõö€ùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°€";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    //stringToReplace = stringToReplace.replace(/\D/g, '');
    stringToReplace = stringToReplace.replace(/[&\/\\#+()$~%'":*?<>{}]-/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

//arrotonda per difetto UK centesimi
function round_and_split_UK(value, decim) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);

        var round = dec % parseIntRaf(parseFloat(decim) * 100); //resto 5 cent
        var int = dec - round;
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}

function round_and_split_UK_up(value, decim) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);
        var r1 = parseInt(dec % (parseFloat(decim) * 100));
        var round = r1;
        if (r1 > 0) {
            round = (parseFloat(decim) * 100) - r1;
        }
        var int = parseInt(dec) + parseInt(round);
        if (int >= 100) {
            int_1 = parseInt(int_1) + 1;
            int = int - 100;
        }
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}

function round_and_split_ita(value) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);
        var round = dec % 5; //resto 5 cent
        var int = dec - round;
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}

//arrotonda per eccesso ita centesimi
function round_and_split_ita_up(value) {
    var output = new Array();
    if (value.lastIndexOf(".") > -1) {
        var int_1 = value.substring(0, value.lastIndexOf("."));
        var dec = value.substring(value.lastIndexOf(".") + 1);
        var r1 = parseInt(dec % 5);
        var round = r1;
        if (r1 > 0) {
            round = 5 - r1;
        }
        var int = parseInt(dec) + parseInt(round);
        if (int >= 100) {
            int_1 = parseInt(int_1) + 1;
            int = int - 100;
        }
        if (int < 10) {
            output[0] = int_1 + ".0" + int;
        } else {
            output[0] = int_1 + "." + int;
        }
        if (round < 10) {
            output[1] = "0.0" + round;
        } else {
            output[1] = "0." + round;
        }
        return output;
    }
    output[0] = value;
    output[1] = "0.00";
    return output;
}


//arrotonda double
function round_value(value, decimals, up) {
    var y = new BigNumber(value);
    if (up) {
        return new BigNumber(value).round(decimals, BigNumber.ROUND_HALF_UP);
    } else {
        return y.round(decimals, BigNumber.ROUND_HALF_EVEN);
    }
}

//FORMATTAZIONE NUMERI TABELLA
function formatValueDecimal_1(value, thousand, decimal) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 2, thousand, decimal);
    var lastChar = value.value[value.value.length - 1];
    if (lastChar === "." || lastChar === ",") {
        value.value = value.value.slice(0, -1);
    }
}

function formatValueDecimal_1_change(value, thousand, decimal) {
    if (thousand === ".") {
        value.value = value.value.replace(/\./g, '');
    } else if (thousand === ",") {
        value.value = value.value.replace(/\,/g, '');
    }
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 2, thousand, decimal);
}


function formatValueDecimal_2_change(value, thousand, decimal, maxlength) {
    var count1 = (value.value.match(/\./g) || []).length;
    var count2 = (value.value.match(/,/g) || []).length;
    var sum = count1 + count2;
    if ((value.value.length - sum) > maxlength) {
        value.value = value.value.substring((value.value.length - sum) - maxlength);
    }
    if (thousand === ".") {
        value.value = value.value.replace(/\./g, '');
    } else if (thousand === ",") {
        value.value = value.value.replace(/\,/g, '');
    }
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 2, thousand, decimal);

}



function formatValueINT_1_change(value, thousand, decimal) {
    if (thousand === ".") {
        value.value = value.value.replace(/\./g, '');
    } else if (thousand === ",") {
        value.value = value.value.replace(/\,/g, '');
    }
    value.value = value.value.split(decimal)[0];
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 0, thousand, decimal);
}


function formatValueDecimal_length(value, thousand, decimal, length) {
    if (thousand === ".") {
        value.value = value.value.replace(/\./g, '');
    } else if (thousand === ",") {
        value.value = value.value.replace(/\,/g, '');
    }
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), length, thousand, decimal);
}

function formatValueDecimal_2(value, thousand, decimal, maxlength) {
    var count1 = (value.value.match(/\./g) || []).length;
    var count2 = (value.value.match(/,/g) || []).length;
    var sum = count1 + count2;
    if ((value.value.length - sum) > maxlength) {
        value.value = value.value.substring((value.value.length - sum) - maxlength);
    }
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 2, thousand, decimal);
}

function modR(a, n) {
    var a1 = new BigNumber(a);
    var n1 = new BigNumber(n);
    return a1.modulo(n1);
}


function formatValueDecimalMax(value, max, thousand, decimal) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 2, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value, thousand), thousand, decimal) > parseFloatRaf(max, thousand, decimal)) {
        value.value = accounting.formatNumber(parseFloatRaf(max, thousand, decimal), 2, thousand, decimal);
        return "1";
    } else {
        return "0";
    }
}

function formatValueDecimalMaxlength(value, max, thousand, decimal, length) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), length, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value, thousand), thousand, decimal) >= parseFloatRaf(max, thousand, decimal)) {
        value.value = accounting.formatNumber(parseFloatRaf(max, thousand, decimal), length, thousand, decimal);
    }
}
function formatValueDecimalMinlengthR(value, min, thousand, decimal, length) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value.toString(), thousand, decimal), length, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value.toString(), thousand), thousand, decimal) <= parseFloat(min.toString())) {
        value.value = accounting.formatNumber(parseFloat(min.toString()), length, thousand, decimal);
    }
}
function formatValueDecimalMaxlengthR(value, mx, thousand, decimal, length) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value.toString(), thousand, decimal), length, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value.toString(), thousand), thousand, decimal) >= parseFloat(mx.toString())) {
        value.value = accounting.formatNumber(parseFloat(mx.toString()), length, thousand, decimal);
    }
}

function parseFloatRaf_OLD(valuefloat) {
    if (valuefloat.indexOf(",") > -1) {
        valuefloat = valuefloat.replace(/\./g, '');
        valuefloat = valuefloat.replace(/,/g, '.');
    } else {
        if (valuefloat.indexOf(".") === -1) {
            valuefloat = valuefloat + ".00";
        }
    }
    return parseFloat(valuefloat);
}

function parseFloatRaf(valuefloat, thousand, decimal, lung) {

    if (lung === undefined) {
        lung = 2;
    }

    if (decimal === "undefined") {
        decimal = ",";
    }

    if (decimal === ",") {
        valuefloat = valuefloat + "";
        if (valuefloat.indexOf(",") > -1) {
            valuefloat = valuefloat.replace(/\./g, '');
            valuefloat = valuefloat.replace(/,/g, '.');
        } else {
            if (valuefloat.indexOf(".") === -1) {
                valuefloat = valuefloat + ".00";
            } else {
                var count = valuefloat.split(".").length - 1;
                if (count === 1) {
                    var dec = valuefloat.split(".")[1];
                    if (dec.length > lung) {
                        valuefloat = valuefloat.replace(/\./g, '');
                    }
                } else {
                    valuefloat = valuefloat.replace(/\./g, '');
                }
            }
        }
    } else {
        valuefloat = (valuefloat + "").replace(/,/g, '');
    }
    return parseFloat(valuefloat.substr(0, 15));
}



function parseIntRaf(valueint) {
    valueint = valueint + "";
    valueint = valueint.replace(/\./g, '');
    valueint = valueint.replace(/,/g, '');
    return parseInt(valueint);
}

function replacestringparam(value, thousand) {
    if (thousand === ",") {
        return value.replace(/[&\/\\#+()$~%'":*?<>{}],/g, '');
    }
    if (thousand === ".") {
        return value.replace(/[&\/\\#+()\.$~%'":*?<>{}]/g, '');
    }
    return value;
}

//visualizza un modal con eventuale redirect
function showmod1(modal, page) {
    document.getElementById(modal).className = document.getElementById(modal).className + " in";
    document.getElementById(modal).style.display = "block";
    //cambiare
    document.getElementById('checkpasswordmodal').action = page;
}

//chiude un modal
function dismiss(modal) {
    document.getElementById(modal).className = "modal fade";
    document.getElementById(modal).style.display = "none";
}

//restituisce il numero del mese partendo dalla lettera del cf
function formatMonthcf(read) {
    read = read.trim().toUpperCase();
    if (read === "A") {
        return "01";
    }
    if (read === "B") {
        return "02";
    }
    if (read === "C") {
        return "03";
    }
    if (read === "D") {
        return "04";
    }
    if (read === "E") {
        return "05";
    }
    if (read === "H") {
        return "06";
    }
    if (read === "L") {
        return "07";
    }
    if (read === "M") {
        return "08";
    }
    if (read === "P") {
        return "09";
    }
    if (read === "R") {
        return "10";
    }
    if (read === "S") {
        return "11";
    }
    if (read === "T") {
        return "12";
    }
    return "00";
}

// visualizza lista agenzie cliccando su agency royalty
function checkver(sel, val) {
    var verified = document.getElementById(sel);
    if (verified.checked) {
        document.getElementById(val).style.display = "block";
    } else {
        document.getElementById(val).style.display = "none";
    }
}




function convertDate_1(dataing) {
    var dd = dataing.split("-")[0].trim();
    var mm = dataing.split("-")[1].trim();
    var aa = dataing.split("-")[2].trim();
    var out = dd + "/" + mm + "/19" + aa;
    return out;
}

function convertDate_2(dataing) {
    if (dataing === "-01--01--01") {
        return "";
    } else {
        var dd = dataing.split("-")[0].trim();
        var mm = dataing.split("-")[1].trim();
        var aa = dataing.split("-")[2].trim();
        var out = dd + "/" + mm + "/20" + aa;
        return out;
    }
}

//  ACCOUNTING JS
(function (p, z) {
    function q(a) {
        return!!("" === a || a && a.charCodeAt && a.substr)
    }
    function m(a) {
        return u ? u(a) : "[object Array]" === v.call(a)
    }
    function r(a) {
        return"[object Object]" === v.call(a)
    }
    function s(a, b) {
        var d, a = a || {}, b = b || {};
        for (d in b)
            b.hasOwnProperty(d) && null == a[d] && (a[d] = b[d]);
        return a
    }
    function j(a, b, d) {
        var c = [], e, h;
        if (!a)
            return c;
        if (w && a.map === w)
            return a.map(b, d);
        for (e = 0, h = a.length; e < h; e++)
            c[e] = b.call(d, a[e], e, a);
        return c
    }
    function n(a, b) {
        a = Math.round(Math.abs(a));
        return Number.isNaN(a) ? b : a
    }
    function x(a) {
        var b = c.settings.currency.format;
        "function" === typeof a && (a = a());
        return q(a) && a.match("%v") ? {pos: a, neg: a.replace(/-/g, "").replace("%v", "-%v"), zero: a} : !a || !a.pos || !a.pos.match("%v") ? !q(b) ? b : c.settings.currency.format = {pos: b, neg: b.replace("%v", "-%v"), zero: b} : a;

    }
    var c = {version: "0.4.1", settings: {currency: {symbol: "$", format: "%s%v", decimal: ".", thousand: ",", precision: 2, grouping: 3}, number: {precision: 0, grouping: 3, thousand: ",", decimal: "."}}}, w = Array.prototype.map, u = Array.isArray, v = Object.prototype.toString, o = c.unformat = c.parse = function (a, b) {
        if (m(a))
            return j(a, function (a) {
                return o(a, b)
            });
        a = a || 0;
        if ("number" === typeof a)
            return a;
        var b = b || ".";
        var regex = RegExp("[^0-9-" + b + "]", ["g"]);
        c = parseFloat(("" + a).replace(/\((.*)\)/, "-$1").replace(regex, "").replace(/b/g, "."));
        return !isNaN(c) ? c : 0;        
    }, y = c.toFixed = function (a, b) {
        var b = n(b, c.settings.number.precision), d = Math.pow(10, b);
        return(Math.round(c.unformat(a) * d) / d).toFixed(b)
    }, t = c.formatNumber = c.format = function (a, b, d, i) {
        if (m(a))
            return j(a, function (a) {
                return t(a, b, d, i)
            });
        var a = o(a), e = s(r(b) ? b : {precision: b, thousand: d, decimal: i}, c.settings.number), h = n(e.precision), f = 0 > a ? "-" : "", g = parseInt(y(Math.abs(a || 0), h), 10) + "", l = 3 < g.length ? g.length % 3 : 0;
        return f + (l ? g.substr(0, l) + e.thousand : "") + g.substr(l).replace(/(\d{3})(?=\d)/g, "$1" + e.thousand) + (h ? e.decimal + y(Math.abs(a), h).split(".")[1] : "")
    }, A = c.formatMoney = function (a, b, d, i, e, h) {
        if (m(a))
            return j(a, function (a) {
                return A(a, b, d, i, e, h)
            });
        var a = o(a), f = s(r(b) ? b : {symbol: b, precision: d, thousand: i, decimal: e, format: h}, c.settings.currency), g = x(f.format);
        return(0 < a ? g.pos : 0 > a ? g.neg : g.zero).replace("%s", f.symbol).replace("%v", t(Math.abs(a), n(f.precision), f.thousand, f.decimal))
    };
    c.formatColumn = function (a, b, d, i, e, h) {
        if (!a)
            return[];
        var f = s(r(b) ? b : {symbol: b, precision: d, thousand: i, decimal: e, format: h}, c.settings.currency), g = x(f.format), l = g.pos.indexOf("%s") < g.pos.indexOf("%v") ? !0 : !1, k = 0, a = j(a, function (a) {
            if (m(a))
                return c.formatColumn(a, f);
            a = o(a);
            a = (0 < a ? g.pos : 0 > a ? g.neg : g.zero).replace("%s", f.symbol).replace("%v", t(Math.abs(a), n(f.precision), f.thousand, f.decimal));
            if (a.length > k)
                k = a.length;
            return a
        });
        return j(a, function (a) {
            return q(a) && a.length < k ? l ? a.replace(f.symbol, f.symbol + Array(k - a.length + 1).join(" ")) : Array(k - a.length + 1).join(" ") + a : a
        })
    };
    if ("undefined" !== typeof exports) {
        if ("undefined" !== typeof module && module.exports)
            exports = module.exports = c;
        exports.accounting = c
    } else
        "function" === typeof define && define.amd ? define([], function () {
            return c
        }) : (c.noConflict = function (a) {
            return function () {
                p.accounting = a;
                c.noConflict = z;
                return c
            }
        }(p.accounting), p.accounting = c)
})(this);

//  BIGNUMBER
!function (e) {
    "use strict";
    function n(e) {
        function E(e, n) {
            var t, r, i, o, u, s, f = this;
            if (!(f instanceof E))
                return j && L(26, "constructor call without new", e), new E(e, n);
            if (null != n && H(n, 2, 64, M, "base")) {
                if (n = 0 | n, s = e + "", 10 == n)
                    return f = new E(e instanceof E ? e : s), U(f, P + f.e + 1, k);
                if ((o = "number" == typeof e) && 0 * e != 0 || !new RegExp("^-?" + (t = "[" + N.slice(0, n) + "]+") + "(?:\\." + t + ")?$", 37 > n ? "i" : "").test(s))
                    return h(f, s, o, n);
                o ? (f.s = 0 > 1 / e ? (s = s.slice(1), -1) : 1, j && s.replace(/^0\.0*|\./, "").length > 15 && L(M, v, e), o = !1) : f.s = 45 === s.charCodeAt(0) ? (s = s.slice(1), -1) : 1, s = D(s, 10, n, f.s)
            } else {
                if (e instanceof E)
                    return f.s = e.s, f.e = e.e, f.c = (e = e.c) ? e.slice() : e, void(M = 0);
                if ((o = "number" == typeof e) && 0 * e == 0) {
                    if (f.s = 0 > 1 / e ? (e = -e, -1) : 1, e === ~~e) {
                        for (r = 0, i = e; i >= 10; i /= 10, r++)
                            ;
                        return f.e = r, f.c = [e], void(M = 0)
                    }
                    s = e + ""
                } else {
                    if (!g.test(s = e + ""))
                        return h(f, s, o);
                    f.s = 45 === s.charCodeAt(0) ? (s = s.slice(1), -1) : 1
                }
            }
            for ((r = s.indexOf(".")) > - 1 && (s = s.replace(/\./g, "")), (i = s.search(/e/i)) > 0?(0 > r && (r = i), r += + s.slice(i + 1), s = s.substring(0, i)):0 > r && (r = s.length), i = 0; 48 === s.charCodeAt(i); i++)
                ;
            for (u = s.length; 48 === s.charCodeAt(--u); )
                ;
            if (s = s.slice(i, u + 1))
                if (u = s.length, o && j && u > 15 && (e > y || e !== d(e)) && L(M, v, f.s * e), r = r - i - 1, r > z)
                    f.c = f.e = null;
                else if (G > r)
                    f.c = [f.e = 0];
                else {
                    if (f.e = r, f.c = [], i = (r + 1) % O, 0 > r && (i += O), u > i) {
                        for (i && f.c.push( + s.slice(0, i)), u -= O; u > i; )
                            f.c.push(+s.slice(i, i += O));
                        s = s.slice(i), i = O - s.length
                    } else
                        i -= u;
                    for (; i--; s += "0")
                        ;
                    f.c.push(+s)
                }
            else
                f.c = [f.e = 0];
            M = 0
        }
        function D(e, n, t, i) {
            var o, u, f, c, a, h, g, p = e.indexOf("."), d = P, m = k;
            for (37 > t && (e = e.toLowerCase()), p >= 0 && (f = J, J = 0, e = e.replace(/\./g, ""), g = new E(t), a = g.pow(e.length - p), J = f, g.c = s(l(r(a.c), a.e), 10, n), g.e = g.c.length), h = s(e, t, n), u = f = h.length; 0 == h[--f]; h.pop())
                ;
            if (!h[0])
                return"0";
            if (0 > p ? --u : (a.c = h, a.e = u, a.s = i, a = C(a, g, d, m, n), h = a.c, c = a.r, u = a.e), o = u + d + 1, p = h[o], f = n / 2, c = c || 0 > o || null != h[o + 1], c = 4 > m ? (null != p || c) && (0 == m || m == (a.s < 0 ? 3 : 2)) : p > f || p == f && (4 == m || c || 6 == m && 1 & h[o - 1] || m == (a.s < 0 ? 8 : 7)), 1 > o || !h[0])
                e = c ? l("1", -d) : "0";
            else {
                if (h.length = o, c)
                    for (--n; ++h[--o] > n; )
                        h[o] = 0, o || (++u, h.unshift(1));
                for (f = h.length; !h[--f]; )
                    ;
                for (p = 0, e = ""; f >= p; e += N.charAt(h[p++]))
                    ;
                e = l(e, u)
            }
            return e
        }
        function F(e, n, t, i) {
            var o, u, s, c, a;
            if (t = null != t && H(t, 0, 8, i, w) ? 0 | t : k, !e.c)
                return e.toString();
            if (o = e.c[0], s = e.e, null == n)
                a = r(e.c), a = 19 == i || 24 == i && B >= s ? f(a, s) : l(a, s);
            else if (e = U(new E(e), n, t), u = e.e, a = r(e.c), c = a.length, 19 == i || 24 == i && (u >= n || B >= u)) {
                for (; n > c; a += "0", c++)
                    ;
                a = f(a, u)
            } else if (n -= s, a = l(a, u), u + 1 > c) {
                if (--n > 0)
                    for (a += "."; n--; a += "0")
                        ;
            } else if (n += u - c, n > 0)
                for (u + 1 == c && (a += "."); n--; a += "0")
                    ;
            return e.s < 0 && o ? "-" + a : a
        }
        function _(e, n) {
            var t, r, i = 0;
            for (u(e[0]) && (e = e[0]), t = new E(e[0]); ++i < e.length; ) {
                if (r = new E(e[i]), !r.s) {
                    t = r;
                    break
                }
                n.call(t, r) && (t = r)
            }
            return t
        }
        function x(e, n, t, r, i) {
            return(n > e || e > t || e != c(e)) && L(r, (i || "decimal places") + (n > e || e > t ? " out of range" : " not an integer"), e), !0
        }
        function I(e, n, t) {
            for (var r = 1, i = n.length; !n[--i]; n.pop())
                ;
            for (i = n[0]; i >= 10; i /= 10, r++)
                ;
            return(t = r + t * O - 1) > z ? e.c = e.e = null : G > t ? e.c = [e.e = 0] : (e.e = t, e.c = n), e
        }
        function L(e, n, t) {
            var r = new Error(["new BigNumber", "cmp", "config", "div", "divToInt", "eq", "gt", "gte", "lt", "lte", "minus", "mod", "plus", "precision", "random", "round", "shift", "times", "toDigits", "toExponential", "toFixed", "toFormat", "toFraction", "pow", "toPrecision", "toString", "BigNumber"][e] + "() " + n + ": " + t);
            throw r.name = "BigNumber Error", M = 0, r
        }
        function U(e, n, t, r) {
            var i, o, u, s, f, l, c, a = e.c, h = S;
            if (a) {
                e:{
                    for (i = 1, s = a[0]; s >= 10; s /= 10, i++)
                        ;
                    if (o = n - i, 0 > o)
                        o += O, u = n, f = a[l = 0], c = f / h[i - u - 1] % 10 | 0;
                    else if (l = p((o + 1) / O), l >= a.length) {
                        if (!r)
                            break e;
                        for (; a.length <= l; a.push(0))
                            ;
                        f = c = 0, i = 1, o %= O, u = o - O + 1
                    } else {
                        for (f = s = a[l], i = 1; s >= 10; s /= 10, i++)
                            ;
                        o %= O, u = o - O + i, c = 0 > u ? 0 : f / h[i - u - 1] % 10 | 0
                    }
                    if (r = r || 0 > n || null != a[l + 1] || (0 > u ? f : f % h[i - u - 1]), r = 4 > t ? (c || r) && (0 == t || t == (e.s < 0 ? 3 : 2)) : c > 5 || 5 == c && (4 == t || r || 6 == t && (o > 0 ? u > 0 ? f / h[i - u] : 0 : a[l - 1]) % 10 & 1 || t == (e.s < 0 ? 8 : 7)), 1 > n || !a[0])
                        return a.length = 0, r ? (n -= e.e + 1, a[0] = h[(O - n % O) % O], e.e = -n || 0) : a[0] = e.e = 0, e;
                    if (0 == o ? (a.length = l, s = 1, l--) : (a.length = l + 1, s = h[O - o], a[l] = u > 0 ? d(f / h[i - u] % h[u]) * s : 0), r)
                        for (; ; ) {
                            if (0 == l) {
                                for (o = 1, u = a[0]; u >= 10; u /= 10, o++)
                                    ;
                                for (u = a[0] += s, s = 1; u >= 10; u /= 10, s++)
                                    ;
                                o != s && (e.e++, a[0] == b && (a[0] = 1));
                                break
                            }
                            if (a[l] += s, a[l] != b)
                                break;
                            a[l--] = 0, s = 1
                        }
                    for (o = a.length; 0 === a[--o]; a.pop())
                        ;
                }
                e.e > z ? e.c = e.e = null : e.e < G && (e.c = [e.e = 0])
            }
            return e
        }
        var C, M = 0, T = E.prototype, q = new E(1), P = 20, k = 4, B = -7, $ = 21, G = -1e7, z = 1e7, j = !0, H = x, V = !1, W = 1, J = 100, X = {decimalSeparator: ".", groupSeparator: ",", groupSize: 3, secondaryGroupSize: 0, fractionGroupSeparator: " ", fractionGroupSize: 0};
        return E.another = n, E.ROUND_UP = 0, E.ROUND_DOWN = 1, E.ROUND_CEIL = 2, E.ROUND_FLOOR = 3, E.ROUND_HALF_UP = 4, E.ROUND_HALF_DOWN = 5, E.ROUND_HALF_EVEN = 6, E.ROUND_HALF_CEIL = 7, E.ROUND_HALF_FLOOR = 8, E.EUCLID = 9, E.config = function () {
            var e, n, t = 0, r = {}, i = arguments, s = i[0], f = s && "object" == typeof s ? function () {
                return s.hasOwnProperty(n) ? null != (e = s[n]) : void 0
            } : function () {
                return i.length > t ? null != (e = i[t++]) : void 0
            };
            return f(n = "DECIMAL_PLACES") && H(e, 0, A, 2, n) && (P = 0 | e), r[n] = P, f(n = "ROUNDING_MODE") && H(e, 0, 8, 2, n) && (k = 0 | e), r[n] = k, f(n = "EXPONENTIAL_AT") && (u(e) ? H(e[0], -A, 0, 2, n) && H(e[1], 0, A, 2, n) && (B = 0 | e[0], $ = 0 | e[1]) : H(e, -A, A, 2, n) && (B = -($ = 0 | (0 > e ? -e : e)))), r[n] = [B, $], f(n = "RANGE") && (u(e) ? H(e[0], -A, -1, 2, n) && H(e[1], 1, A, 2, n) && (G = 0 | e[0], z = 0 | e[1]) : H(e, -A, A, 2, n) && (0 | e ? G = -(z = 0 | (0 > e ? -e : e)) : j && L(2, n + " cannot be zero", e))), r[n] = [G, z], f(n = "ERRORS") && (e === !!e || 1 === e || 0 === e ? (M = 0, H = (j = !!e) ? x : o) : j && L(2, n + m, e)), r[n] = j, f(n = "CRYPTO") && (e === !!e || 1 === e || 0 === e ? (V = !(!e || !a), e && !V && j && L(2, "crypto unavailable", a)) : j && L(2, n + m, e)), r[n] = V, f(n = "MODULO_MODE") && H(e, 0, 9, 2, n) && (W = 0 | e), r[n] = W, f(n = "POW_PRECISION") && H(e, 0, A, 2, n) && (J = 0 | e), r[n] = J, f(n = "FORMAT") && ("object" == typeof e ? X = e : j && L(2, n + " not an object", e)), r[n] = X, r
        }, E.max = function () {
            return _(arguments, T.lt)
        }, E.min = function () {
            return _(arguments, T.gt)
        }, E.random = function () {
            var e = 9007199254740992, n = Math.random() * e & 2097151 ? function () {
                return d(Math.random() * e)
            } : function () {
                return 8388608 * (1073741824 * Math.random() | 0) + (8388608 * Math.random() | 0)
            };
            return function (e) {
                var t, r, i, o, u, s = 0, f = [], l = new E(q);
                if (e = null != e && H(e, 0, A, 14) ? 0 | e : P, o = p(e / O), V)
                    if (a && a.getRandomValues) {
                        for (t = a.getRandomValues(new Uint32Array(o *= 2)); o > s; )
                            u = 131072 * t[s] + (t[s + 1] >>> 11), u >= 9e15 ? (r = a.getRandomValues(new Uint32Array(2)), t[s] = r[0], t[s + 1] = r[1]) : (f.push(u % 1e14), s += 2);
                        s = o / 2
                    } else if (a && a.randomBytes) {
                        for (t = a.randomBytes(o *= 7); o > s; )
                            u = 281474976710656 * (31 & t[s]) + 1099511627776 * t[s + 1] + 4294967296 * t[s + 2] + 16777216 * t[s + 3] + (t[s + 4] << 16) + (t[s + 5] << 8) + t[s + 6], u >= 9e15 ? a.randomBytes(7).copy(t, s) : (f.push(u % 1e14), s += 7);
                        s = o / 7
                    } else
                        j && L(14, "crypto unavailable", a);
                if (!s)
                    for (; o > s; )
                        u = n(), 9e15 > u && (f[s++] = u % 1e14);
                for (o = f[--s], e %= O, o && e && (u = S[O - e], f[s] = d(o / u) * u); 0 === f[s]; f.pop(), s--)
                    ;
                if (0 > s)
                    f = [i = 0];
                else {
                    for (i = - 1; 0 === f[0]; f.shift(), i -= O)
                        ;
                    for (s = 1, u = f[0]; u >= 10; u /= 10, s++)
                        ;
                    O > s && (i -= O - s)
                }
                return l.e = i, l.c = f, l
            }
        }(), C = function () {
            function e(e, n, t) {
                var r, i, o, u, s = 0, f = e.length, l = n % R, c = n / R | 0;
                for (e = e.slice(); f--; )
                    o = e[f] % R, u = e[f] / R | 0, r = c * o + u * l, i = l * o + r % R * R + s, s = (i / t | 0) + (r / R | 0) + c * u, e[f] = i % t;
                return s && e.unshift(s), e
            }
            function n(e, n, t, r) {
                var i, o;
                if (t != r)
                    o = t > r ? 1 : -1;
                else
                    for (i = o = 0; t > i; i++)
                        if (e[i] != n[i]) {
                            o = e[i] > n[i] ? 1 : -1;
                            break
                        }
                return o
            }
            function r(e, n, t, r) {
                for (var i = 0; t--; )
                    e[t] -= i, i = e[t] < n[t] ? 1 : 0, e[t] = i * r + e[t] - n[t];
                for (; !e[0] && e.length > 1; e.shift())
                    ;
            }
            return function (i, o, u, s, f) {
                var l, c, a, h, g, p, m, w, v, N, y, S, R, A, D, F, _, x = i.s == o.s ? 1 : -1, I = i.c, L = o.c;
                if (!(I && I[0] && L && L[0]))
                    return new E(i.s && o.s && (I ? !L || I[0] != L[0] : L) ? I && 0 == I[0] || !L ? 0 * x : x / 0 : NaN);
                for (w = new E(x), v = w.c = [], c = i.e - o.e, x = u + c + 1, f || (f = b, c = t(i.e / O) - t(o.e / O), x = x / O | 0), a = 0; L[a] == (I[a] || 0); a++)
                    ;
                if (L[a] > (I[a] || 0) && c--, 0 > x)
                    v.push(1), h = !0;
                else {
                    for (A = I.length, F = L.length, a = 0, x += 2, g = d(f / (L[0] + 1)), g > 1 && (L = e(L, g, f), I = e(I, g, f), F = L.length, A = I.length), R = F, N = I.slice(0, F), y = N.length; F > y; N[y++] = 0)
                        ;
                    _ = L.slice(), _.unshift(0), D = L[0], L[1] >= f / 2 && D++;
                    do {
                        if (g = 0, l = n(L, N, F, y), 0 > l) {
                            if (S = N[0], F != y && (S = S * f + (N[1] || 0)), g = d(S / D), g > 1)
                                for (g >= f && (g = f - 1), p = e(L, g, f), m = p.length, y = N.length; 1 == n(p, N, m, y); )
                                    g--, r(p, m > F ? _ : L, m, f), m = p.length, l = 1;
                            else
                                0 == g && (l = g = 1), p = L.slice(), m = p.length;
                            if (y > m && p.unshift(0), r(N, p, y, f), y = N.length, -1 == l)
                                for (; n(L, N, F, y) < 1; )
                                    g++, r(N, y > F ? _ : L, y, f), y = N.length
                        } else
                            0 === l && (g++, N = [0]);
                        v[a++] = g, N[0] ? N[y++] = I[R] || 0 : (N = [I[R]], y = 1)
                    } while ((R++ < A || null != N[0]) && x--);
                    h = null != N[0], v[0] || v.shift()
                }
                if (f == b) {
                    for (a = 1, x = v[0]; x >= 10; x /= 10, a++)
                        ;
                    U(w, u + (w.e = a + c * O - 1) + 1, s, h)
                } else
                    w.e = c, w.r = +h;
                return w
            }
        }(), h = function () {
            var e = /^(-?)0([xbo])(?=\w[\w.]*$)/i, n = /^([^.]+)\.$/, t = /^\.([^.]+)$/, r = /^-?(Infinity|NaN)$/, i = /^\s*\+(?=[\w.])|^\s+|\s+$/g;
            return function (o, u, s, f) {
                var l, c = s ? u : u.replace(i, "");
                if (r.test(c))
                    o.s = Number.isNaN(c) ? null : 0 > c ? -1 : 1;
                else {
                    if (!s && (c = c.replace(e, function (e, n, t) {
                        return l = "x" == (t = t.toLowerCase()) ? 16 : "b" == t ? 2 : 8, f && f != l ? e : n
                    }), f && (l = f, c = c.replace(n, "$1").replace(t, "0.$1")), u != c))
                        return new E(c, l);
                    j && L(M, "not a" + (f ? " base " + f : "") + " number", u), o.s = null
                }
                o.c = o.e = null, M = 0
            }
        }(), T.absoluteValue = T.abs = function () {
            var e = new E(this);
            return e.s < 0 && (e.s = 1), e
        }, T.ceil = function () {
            return U(new E(this), this.e + 1, 2)
        }, T.comparedTo = T.cmp = function (e, n) {
            return M = 1, i(this, new E(e, n))
        }, T.decimalPlaces = T.dp = function () {
            var e, n, r = this.c;
            if (!r)
                return null;
            if (e = ((n = r.length - 1) - t(this.e / O)) * O, n = r[n])
                for (; n % 10 == 0; n /= 10, e--)
                    ;
            return 0 > e && (e = 0), e
        }, T.dividedBy = T.div = function (e, n) {
            return M = 3, C(this, new E(e, n), P, k)
        }, T.dividedToIntegerBy = T.divToInt = function (e, n) {
            return M = 4, C(this, new E(e, n), 0, 1)
        }, T.equals = T.eq = function (e, n) {
            return M = 5, 0 === i(this, new E(e, n))
        }, T.floor = function () {
            return U(new E(this), this.e + 1, 3)
        }, T.greaterThan = T.gt = function (e, n) {
            return M = 6, i(this, new E(e, n)) > 0
        }, T.greaterThanOrEqualTo = T.gte = function (e, n) {
            return M = 7, 1 === (n = i(this, new E(e, n))) || 0 === n
        }, T.isFinite = function () {
            return!!this.c
        }, T.isInteger = T.isInt = function () {
            return!!this.c && t(this.e / O) > this.c.length - 2
        }, T.isNaN = function () {
            return!this.s
        }, T.isNegative = T.isNeg = function () {
            return this.s < 0
        }, T.isZero = function () {
            return!!this.c && 0 == this.c[0]
        }, T.lessThan = T.lt = function (e, n) {
            return M = 8, i(this, new E(e, n)) < 0
        }, T.lessThanOrEqualTo = T.lte = function (e, n) {
            return M = 9, -1 === (n = i(this, new E(e, n))) || 0 === n
        }, T.minus = T.sub = function (e, n) {
            var r, i, o, u, s = this, f = s.s;
            if (M = 10, e = new E(e, n), n = e.s, !f || !n)
                return new E(NaN);
            if (f != n)
                return e.s = -n, s.plus(e);
            var l = s.e / O, c = e.e / O, a = s.c, h = e.c;
            if (!l || !c) {
                if (!a || !h)
                    return a ? (e.s = -n, e) : new E(h ? s : NaN);
                if (!a[0] || !h[0])
                    return h[0] ? (e.s = -n, e) : new E(a[0] ? s : 3 == k ? -0 : 0)
            }
            if (l = t(l), c = t(c), a = a.slice(), f = l - c) {
                for ((u = 0 > f)?(f = - f, o = a):(c = l, o = h), o.reverse(), n = f; n--; o.push(0))
                    ;
                o.reverse()
            } else
                for (i = (u = (f = a.length) < (n = h.length))?f:n, f = n = 0; i > n; n++)
                    if (a[n] != h[n]) {
                        u = a[n] < h[n];
                        break
                    }
            if (u && (o = a, a = h, h = o, e.s = -e.s), n = (i = h.length) - (r = a.length), n > 0)
                for (; n--; a[r++] = 0)
                    ;
            for (n = b - 1; i > f; ) {
                if (a[--i] < h[i]) {
                    for (r = i; r && !a[--r]; a[r] = n)
                        ;
                    --a[r], a[i] += b
                }
                a[i] -= h[i]
            }
            for (; 0 == a[0]; a.shift(), --c)
                ;
            return a[0] ? I(e, a, c) : (e.s = 3 == k ? -1 : 1, e.c = [e.e = 0], e)
        }, T.modulo = T.mod = function (e, n) {
            var t, r, i = this;
            return M = 11, e = new E(e, n), !i.c || !e.s || e.c && !e.c[0] ? new E(NaN) : !e.c || i.c && !i.c[0] ? new E(i) : (9 == W ? (r = e.s, e.s = 1, t = C(i, e, 0, 3), e.s = r, t.s *= r) : t = C(i, e, 0, W), i.minus(t.times(e)))
        }, T.negated = T.neg = function () {
            var e = new E(this);
            return e.s = -e.s || null, e
        }, T.plus = T.add = function (e, n) {
            var r, i = this, o = i.s;
            if (M = 12, e = new E(e, n), n = e.s, !o || !n)
                return new E(NaN);
            if (o != n)
                return e.s = -n, i.minus(e);
            var u = i.e / O, s = e.e / O, f = i.c, l = e.c;
            if (!u || !s) {
                if (!f || !l)
                    return new E(o / 0);
                if (!f[0] || !l[0])
                    return l[0] ? e : new E(f[0] ? i : 0 * o)
            }
            if (u = t(u), s = t(s), f = f.slice(), o = u - s) {
                for (o > 0?(s = u, r = l):(o = - o, r = f), r.reverse(); o--; r.push(0))
                    ;
                r.reverse()
            }
            for (o = f.length, n = l.length, 0 > o - n && (r = l, l = f, f = r, n = o), o = 0; n; )
                o = (f[--n] = f[n] + l[n] + o) / b | 0, f[n] %= b;
            return o && (f.unshift(o), ++s), I(e, f, s)
        }, T.precision = T.sd = function (e) {
            var n, t, r = this, i = r.c;
            if (null != e && e !== !!e && 1 !== e && 0 !== e && (j && L(13, "argument" + m, e), e != !!e && (e = null)), !i)
                return null;
            if (t = i.length - 1, n = t * O + 1, t = i[t]) {
                for (; t % 10 == 0; t /= 10, n--)
                    ;
                for (t = i[0]; t >= 10; t /= 10, n++)
                    ;
            }
            return e && r.e + 1 > n && (n = r.e + 1), n
        }, T.round = function (e, n) {
            var t = new E(this);
            return(null == e || H(e, 0, A, 15)) && U(t, ~~e + this.e + 1, null != n && H(n, 0, 8, 15, w) ? 0 | n : k), t
        }, T.shift = function (e) {
            var n = this;
            return H(e, -y, y, 16, "argument") ? n.times("1e" + c(e)) : new E(n.c && n.c[0] && (-y > e || e > y) ? n.s * (0 > e ? 0 : 1 / 0) : n)
        }, T.squareRoot = T.sqrt = function () {
            var e, n, i, o, u, s = this, f = s.c, l = s.s, c = s.e, a = P + 4, h = new E("0.5");
            if (1 !== l || !f || !f[0])
                return new E(!l || 0 > l && (!f || f[0]) ? NaN : f ? s : 1 / 0);
            if (l = Math.sqrt(+s), 0 == l || l == 1 / 0 ? (n = r(f), (n.length + c) % 2 == 0 && (n += "0"), l = Math.sqrt(n), c = t((c + 1) / 2) - (0 > c || c % 2), l == 1 / 0 ? n = "1e" + c : (n = l.toExponential(), n = n.slice(0, n.indexOf("e") + 1) + c), i = new E(n)) : i = new E(l + ""), i.c[0])
                for (c = i.e, l = c + a, 3 > l && (l = 0); ; )
                    if (u = i, i = h.times(u.plus(C(s, u, a, 1))), r(u.c).slice(0, l) === (n = r(i.c)).slice(0, l)) {
                        if (i.e < c && --l, n = n.slice(l - 3, l + 1), "9999" != n && (o || "4999" != n)) {
                            (!+n || !+n.slice(1) && "5" == n.charAt(0)) && (U(i, i.e + P + 2, 1), e = !i.times(i).eq(s));
                            break
                        }
                        if (!o && (U(u, u.e + P + 2, 0), u.times(u).eq(s))) {
                            i = u;
                            break
                        }
                        a += 4, l += 4, o = 1
                    }
            return U(i, i.e + P + 1, k, e)
        }, T.times = T.mul = function (e, n) {
            var r, i, o, u, s, f, l, c, a, h, g, p, d, m, w, v = this, N = v.c, y = (M = 17, e = new E(e, n)).c;
            if (!(N && y && N[0] && y[0]))
                return!v.s || !e.s || N && !N[0] && !y || y && !y[0] && !N ? e.c = e.e = e.s = null : (e.s *= v.s, N && y ? (e.c = [0], e.e = 0) : e.c = e.e = null), e;
            for (i = t(v.e / O) + t(e.e / O), e.s *= v.s, l = N.length, h = y.length, h > l && (d = N, N = y, y = d, o = l, l = h, h = o), o = l + h, d = []; o--; d.push(0))
                ;
            for (m = b, w = R, o = h; --o >= 0; ) {
                for (r = 0, g = y[o] % w, p = y[o] / w | 0, s = l, u = o + s; u > o; )
                    c = N[--s] % w, a = N[s] / w | 0, f = p * c + a * g, c = g * c + f % w * w + d[u] + r, r = (c / m | 0) + (f / w | 0) + p * a, d[u--] = c % m;
                d[u] = r
            }
            return r ? ++i : d.shift(), I(e, d, i)
        }, T.toDigits = function (e, n) {
            var t = new E(this);
            return e = null != e && H(e, 1, A, 18, "precision") ? 0 | e : null, n = null != n && H(n, 0, 8, 18, w) ? 0 | n : k, e ? U(t, e, n) : t
        }, T.toExponential = function (e, n) {
            return F(this, null != e && H(e, 0, A, 19) ? ~~e + 1 : null, n, 19)
        }, T.toFixed = function (e, n) {
            return F(this, null != e && H(e, 0, A, 20) ? ~~e + this.e + 1 : null, n, 20)
        }, T.toFormat = function (e, n) {
            var t = F(this, null != e && H(e, 0, A, 21) ? ~~e + this.e + 1 : null, n, 21);
            if (this.c) {
                var r, i = t.split("."), o = +X.groupSize, u = +X.secondaryGroupSize, s = X.groupSeparator, f = i[0], l = i[1], c = this.s < 0, a = c ? f.slice(1) : f, h = a.length;
                if (u && (r = o, o = u, u = r, h -= r), o > 0 && h > 0) {
                    for (r = h % o || o, f = a.substr(0, r); h > r; r += o)
                        f += s + a.substr(r, o);
                    u > 0 && (f += s + a.slice(r)), c && (f = "-" + f)
                }
                t = l ? f + X.decimalSeparator + ((u = +X.fractionGroupSize) ? l.replace(new RegExp("\\d{" + u + "}\\B", "g"), "$&" + X.fractionGroupSeparator) : l) : f
            }
            return t
        }, T.toFraction = function (e) {
            var n, t, i, o, u, s, f, l, c, a = j, h = this, g = h.c, p = new E(q), d = t = new E(q), m = f = new E(q);
            if (null != e && (j = !1, s = new E(e), j = a, (!(a = s.isInt()) || s.lt(q)) && (j && L(22, "max denominator " + (a ? "out of range" : "not an integer"), e), e = !a && s.c && U(s, s.e + 1, 1).gte(q) ? s : null)), !g)
                return h.toString();
            for (c = r(g), o = p.e = c.length - h.e - 1, p.c[0] = S[(u = o % O) < 0?O + u:u], e = !e || s.cmp(p) > 0?o > 0?p:d:s, u = z, z = 1 / 0, s = new E(c), f.c[0] = 0; l = C(s, p, 0, 1), i = t.plus(l.times(m)), 1 != i.cmp(e); )
                t = m, m = i, d = f.plus(l.times(i = d)), f = i, p = s.minus(l.times(i = p)), s = i;
            return i = C(e.minus(t), m, 0, 1), f = f.plus(i.times(d)), t = t.plus(i.times(m)), f.s = d.s = h.s, o *= 2, n = C(d, m, o, k).minus(h).abs().cmp(C(f, t, o, k).minus(h).abs()) < 1 ? [d.toString(), m.toString()] : [f.toString(), t.toString()], z = u, n
        }, T.toNumber = function () {
            return +this
        }, T.toPower = T.pow = function (e, n) {
            var t, r, i, o = d(0 > e ? -e : +e), u = this;
            if (null != n && (M = 23, n = new E(n)), !H(e, -y, y, 23, "exponent") && (!isFinite(e) || (o > y && (e /= 0)) || Number.isNaN(parseFloat(e))) || e == 0)
                return t = Math.pow(+u, e), new E(n ? t % n : t);
            for (n ? e > 1 && u.gt(q) && u.isInt() && n.gt(q) && n.isInt() ? u = u.mod(n) : (i = n, n = null) : J && (t = p(J / O + 2)), r = new E(q); ; ) {
                if (o % 2) {
                    if (r = r.times(u), !r.c)
                        break;
                    t ? r.c.length > t && (r.c.length = t) : n && (r = r.mod(n))
                }
                if (o = d(o / 2), !o)
                    break;
                u = u.times(u), t ? u.c && u.c.length > t && (u.c.length = t) : n && (u = u.mod(n))
            }
            return n ? r : (e < 0 && (r = q.div(r)), i ? r.mod(i) : t ? U(r, J, k) : r)
            
        }, T.toPrecision = function (e, n) {
            return F(this, null != e && H(e, 1, A, 24, "precision") ? 0 | e : null, n, 24)
        }, T.toString = function (e) {
            var n, t = this, i = t.s, o = t.e;
            return null === o ? i ? (n = "Infinity", 0 > i && (n = "-" + n)) : n = "NaN" : (n = r(t.c), n = null != e && H(e, 2, 64, 25, "base") ? D(l(n, o), 0 | e, 10, i) : B >= o || o >= $ ? f(n, o) : l(n, o), 0 > i && t.c[0] && (n = "-" + n)), n
        }, T.truncated = T.trunc = function () {
            return U(new E(this), this.e + 1, 1)
        }, T.valueOf = T.toJSON = function () {
            var e, n = this, t = n.e;
            return null === t ? n.toString() : (e = r(n.c), e = B >= t || t >= $ ? f(e, t) : l(e, t), n.s < 0 ? "-" + e : e)
        }, null != e && E.config(e), E
    }
    function t(e) {
        var n = 0 | e;
        return e > 0 || e === n ? n : n - 1
    }
    function r(e) {
        for (var n, t, r = 1, i = e.length, o = e[0] + ""; i > r; ) {
            for (n = e[r++] + "", t = O - n.length; t--; n = "0" + n)
                ;
            o += n
        }
        for (i = o.length; 48 === o.charCodeAt(--i); )
            ;
        return o.slice(0, i + 1 || 1)
    }
    function i(e, n) {
        var t, r, i = e.c, o = n.c, u = e.s, s = n.s, f = e.e, l = n.e;
        if (!u || !s)
            return null;
        if (t = i && !i[0], r = o && !o[0], t || r)
            return t ? r ? 0 : -s : u;
        if (u != s)
            return u;
        if (t = 0 > u, r = f == l, !i || !o)
            return r ? 0 : !i ^ t ? 1 : -1;
        if (!r)
            return f > l ^ t ? 1 : -1;
        for (s = (f = i.length) < (l = o.length)?f:l, u = 0; s > u; u++)
            if (i[u] != o[u])
                return i[u] > o[u] ^ t ? 1 : -1;
        return f == l ? 0 : f > l ^ t ? 1 : -1
    }
    function o(e, n, t) {
        return(e = c(e)) >= n && t >= e
    }
    function u(e) {
        return"[object Array]" == Object.prototype.toString.call(e)
    }
    function s(e, n, t) {
        for (var r, i, o = [0], u = 0, s = e.length; s > u; ) {
            for (i = o.length; i--; o[i] *= n)
                ;
            for (o[r = 0] += N.indexOf(e.charAt(u++)); r < o.length; r++)
                o[r] > t - 1 && (null == o[r + 1] && (o[r + 1] = 0), o[r + 1] += o[r] / t | 0, o[r] %= t)
        }
        return o.reverse()
    }
    function f(e, n) {
        return(e.length > 1 ? e.charAt(0) + "." + e.slice(1) : e) + (0 > n ? "e" : "e+") + n
    }
    function l(e, n) {
        var t, r;
        if (0 > n) {
            for (r = "0."; ++n; r += "0")
                ;
            e = r + e
        } else if (t = e.length, ++n > t) {
            for (r = "0", n -= t; --n; r += "0")
                ;
            e += r
        } else
            t > n && (e = e.slice(0, n) + "." + e.slice(n));
        return e
    }
    function c(e) {
        return e = parseFloat(e), 0 > e ? p(e) : d(e)
    }
    var a, h, g = /^-?(\d+(\.\d*)?|\.\d+)(e[+-]?\d+)?$/i, p = Math.ceil, d = Math.floor, m = " not a boolean or binary digit", w = "rounding mode", v = "number type has more than 15 significant digits", N = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$_", b = 1e14, O = 14, y = 9007199254740991, S = [1, 10, 100, 1e3, 1e4, 1e5, 1e6, 1e7, 1e8, 1e9, 1e10, 1e11, 1e12, 1e13], R = 1e7, A = 1e9;
    if ("undefined" != typeof crypto && (a = crypto), "function" == typeof define && define.amd)
        define(function () {
            return n()
        });
    else if ("undefined" != typeof module && module.exports) {
        if (module.exports = n(), !a)
            try {
                a = require("crypto")
            } catch (E) {
            }
    } else
        e || (e = "undefined" != typeof self ? self : Function("return this")()), e.BigNumber = n()
}(this);

/*!
 * Knockout JavaScript library v3.4.2
 * (c) The Knockout.js team - http://knockoutjs.com/
 * License: MIT (http://www.opensource.org/licenses/mit-license.php)
 */

(function () {
    (function (n) {
        var x = this || (0, eval)("this"), t = x.document, M = x.navigator, u = x.jQuery, H = x.JSON;
        (function (n) {
            "function" === typeof define && define.amd ? define(["exports", "require"], n) : "object" === typeof exports && "object" === typeof module ? n(module.exports || exports) : n(x.ko = {})
        })(function (N, O) {
            function J(a, c) {
                return null === a || typeof a in R ? a === c : !1
            }
            function S(b, c) {
                var d;
                return function () {
                    d || (d = a.a.setTimeout(function () {
                        d = n;
                        b()
                    }, c))
                }
            }
            function T(b, c) {
                var d;
                return function () {
                    clearTimeout(d);
                    d = a.a.setTimeout(b, c)
                }
            }
            function U(a,
                    c) {
                c && c !== E ? "beforeChange" === c ? this.Ob(a) : this.Ja(a, c) : this.Pb(a)
            }
            function V(a, c) {
                null !== c && c.k && c.k()
            }
            function W(a, c) {
                var d = this.Mc, e = d[s];
                e.T || (this.ob && this.Oa[c] ? (d.Sb(c, a, this.Oa[c]), this.Oa[c] = null, --this.ob) : e.s[c] || d.Sb(c, a, e.t ? {$: a} : d.yc(a)), a.Ha && a.Hc())
            }
            function K(b, c, d, e) {
                a.d[b] = {init: function (b, g, h, l, m) {
                        var k, r;
                        a.m(function () {
                            var q = g(), 
                            p = a.a.c(q);
                            var pResult = !(d !== p);
                            A = !r;
                            
                            if (A || c || p !== k)
                                A && a.xa.Ca() && (r = a.a.wa(a.f.childNodes(b), !0)), p ? (A || a.f.fa(b, a.a.wa(r)), a.hb(e ? e(m, q) : m, b)) : a.f.za(b), k = p
                        }, null,
                                {i: b});
                        return{controlsDescendantBindings: !0}
                    }};
                a.h.va[b] = !1;
                a.f.aa[b] = !0
            }
            var a = "undefined" !== typeof N ? N : {};
            a.b = function (b, c) {
                for (var d = b.split("."), e = a, f = 0; f < d.length - 1; f++)
                    e = e[d[f]];
                e[d[d.length - 1]] = c
            };
            a.H = function (a, c, d) {
                a[c] = d
            };
            a.version = "3.4.2";
            a.b("version", a.version);
            a.options = {deferUpdates: !1, useOnlyNativeEvents: !1};
            a.a = function () {
                function b(a, b) {
                    for (var c in a)
                        a.hasOwnProperty(c) && b(c, a[c])
                }
                function c(a, b) {
                    if (b)
                        for (var c in b)
                            b.hasOwnProperty(c) && (a[c] = b[c]);
                    return a
                }
                function d(a, b) {
                    a.__proto__ =
                            b;
                    return a
                }
                function e(b, c, d, e) {
                    var m = b[c].match(r) || [];
                    a.a.r(d.match(r), function (b) {
                        a.a.ra(m, b, e)
                    });
                    b[c] = m.join(" ")
                }
                var f = {__proto__: []}instanceof Array, g = "function" === typeof Symbol, h = {}, l = {};
                h[M && /Firefox\/2/i.test(M.userAgent) ? "KeyboardEvent" : "UIEvents"] = ["keyup", "keydown", "keypress"];
                h.MouseEvents = "click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave".split(" ");
                b(h, function (a, b) {
                    if (b.length)
                        for (var c = 0, d = b.length; c < d; c++)
                            l[b[c]] = a
                });
                var m = {propertychange: !0}, k =
                        t && function () {
                            for (var a = 3, b = t.createElement("div"), c = b.getElementsByTagName("i"); b.innerHTML = "\x3c!--[if gt IE " + ++a + "]><i></i><![endif]--\x3e", c[0]; )
                                ;
                            return 4 < a ? a : n
                        }(), r = /\S+/g;
                return{gc: ["authenticity_token", /^__RequestVerificationToken(_.*)?$/], r: function (a, b) {
                        for (var c = 0, d = a.length; c < d; c++)
                            b(a[c], c)
                    }, o: function (a, b) {
                        if ("function" == typeof Array.prototype.indexOf)
                            return Array.prototype.indexOf.call(a, b);
                        for (var c = 0, d = a.length; c < d; c++)
                            if (a[c] === b)
                                return c;
                        return-1
                    }, Vb: function (a, b, c) {
                        for (var d =
                                0, e = a.length; d < e; d++)
                            if (b.call(c, a[d], d))
                                return a[d];
                        return null
                    }, Na: function (b, c) {
                        var d = a.a.o(b, c);
                        0 < d ? b.splice(d, 1) : 0 === d && b.shift()
                    }, Wb: function (b) {
                        b = b || [];
                        for (var c = [], d = 0, e = b.length; d < e; d++)
                            0 > a.a.o(c, b[d]) && c.push(b[d]);
                        return c
                    }, ib: function (a, b) {
                        a = a || [];
                        for (var c = [], d = 0, e = a.length; d < e; d++)
                            c.push(b(a[d], d));
                        return c
                    }, Ma: function (a, b) {
                        a = a || [];
                        for (var c = [], d = 0, e = a.length; d < e; d++)
                            b(a[d], d) && c.push(a[d]);
                        return c
                    }, ta: function (a, b) {
                        if (b instanceof Array)
                            a.push.apply(a, b);
                        else
                            for (var c = 0, d = b.length; c <
                                    d; c++)
                                a.push(b[c]);
                        return a
                    }, ra: function (b, c, d) {
                        var e = a.a.o(a.a.Bb(b), c);
                        0 > e ? d && b.push(c) : d || b.splice(e, 1)
                    }, la: f, extend: c, $a: d, ab: f ? d : c, D: b, Ea: function (a, b) {
                        if (!a)
                            return a;
                        var c = {}, d;
                        for (d in a)
                            a.hasOwnProperty(d) && (c[d] = b(a[d], d, a));
                        return c
                    }, rb: function (b) {
                        for (; b.firstChild; )
                            a.removeNode(b.firstChild)
                    }, nc: function (b) {
                        b = a.a.W(b);
                        for (var c = (b[0] && b[0].ownerDocument || t).createElement("div"), d = 0, e = b.length; d < e; d++)
                            c.appendChild(a.ba(b[d]));
                        return c
                    }, wa: function (b, c) {
                        for (var d = 0, e = b.length, m = []; d < e; d++) {
                            var k =
                                    b[d].cloneNode(!0);
                            m.push(c ? a.ba(k) : k)
                        }
                        return m
                    }, fa: function (b, c) {
                        a.a.rb(b);
                        if (c)
                            for (var d = 0, e = c.length; d < e; d++)
                                b.appendChild(c[d])
                    }, uc: function (b, c) {
                        var d = b.nodeType ? [b] : b;
                        if (0 < d.length) {
                            for (var e = d[0], m = e.parentNode, k = 0, f = c.length; k < f; k++)
                                m.insertBefore(c[k], e);
                            k = 0;
                            for (f = d.length; k < f; k++)
                                a.removeNode(d[k])
                        }
                    }, Ba: function (a, b) {
                        if (a.length) {
                            for (b = 8 === b.nodeType && b.parentNode || b; a.length && a[0].parentNode !== b; )
                                a.splice(0, 1);
                            for (; 1 < a.length && a[a.length - 1].parentNode !== b; )
                                a.length--;
                            if (1 < a.length) {
                                var c =
                                        a[0], d = a[a.length - 1];
                                for (a.length = 0; c !== d; )
                                    a.push(c), c = c.nextSibling;
                                a.push(d)
                            }
                        }
                        return a
                    }, wc: function (a, b) {
                        7 > k ? a.setAttribute("selected", b) : a.selected = b
                    }, cb: function (a) {
                        return null === a || a === n ? "" : a.trim ? a.trim() : a.toString().replace(/^[\s\xa0]+|[\s\xa0]+$/g, "")
                    }, sd: function (a, b) {
                        a = a || "";
                        return b.length > a.length ? !1 : a.substring(0, b.length) === b
                    }, Rc: function (a, b) {
                        if (a === b)
                            return!0;
                        if (11 === a.nodeType)
                            return!1;
                        if (b.contains)
                            return b.contains(3 === a.nodeType ? a.parentNode : a);
                        if (b.compareDocumentPosition)
                            return 16 ==
                                    (b.compareDocumentPosition(a) & 16);
                        for (; a && a != b; )
                            a = a.parentNode;
                        return!!a
                    }, qb: function (b) {
                        return a.a.Rc(b, b.ownerDocument.documentElement)
                    }, Tb: function (b) {
                        return!!a.a.Vb(b, a.a.qb)
                    }, A: function (a) {
                        return a && a.tagName && a.tagName.toLowerCase()
                    }, Zb: function (b) {
                        return a.onError ? function () {
                            try {
                                return b.apply(this, arguments)
                            } catch (c) {
                                throw a.onError && a.onError(c), c;
                            }
                        } : b
                    }, setTimeout: function (b, c) {
                        return setTimeout(a.a.Zb(b), c)
                    }, dc: function (b) {
                        setTimeout(function () {
                            a.onError && a.onError(b);
                            throw b;
                        }, 0)
                    }, q: function (b,
                            c, d) {
                        var e = a.a.Zb(d);
                        d = k && m[c];
                        if (a.options.useOnlyNativeEvents || d || !u)
                            if (d || "function" != typeof b.addEventListener)
                                if ("undefined" != typeof b.attachEvent) {
                                    var f = function (a) {
                                        e.call(b, a)
                                    }, l = "on" + c;
                                    b.attachEvent(l, f);
                                    a.a.G.qa(b, function () {
                                        b.detachEvent(l, f)
                                    })
                                } else
                                    throw Error("Browser doesn't support addEventListener or attachEvent");
                            else
                                b.addEventListener(c, e, !1);
                        else
                            u(b).bind(c, e)
                    }, Fa: function (b, c) {
                        if (!b || !b.nodeType)
                            throw Error("element must be a DOM node when calling triggerEvent");
                        var d;
                        "input" ===
                                a.a.A(b) && b.type && "click" == c.toLowerCase() ? (d = b.type, d = "checkbox" == d || "radio" == d) : d = !1;
                        if (a.options.useOnlyNativeEvents || !u || d)
                            if ("function" == typeof t.createEvent)
                                if ("function" == typeof b.dispatchEvent)
                                    d = t.createEvent(l[c] || "HTMLEvents"), d.initEvent(c, !0, !0, x, 0, 0, 0, 0, 0, !1, !1, !1, !1, 0, b), b.dispatchEvent(d);
                                else
                                    throw Error("The supplied element doesn't support dispatchEvent");
                            else if (d && b.click)
                                b.click();
                            else if ("undefined" != typeof b.fireEvent)
                                b.fireEvent("on" + c);
                            else
                                throw Error("Browser doesn't support triggering events");
                        else
                            u(b).trigger(c)
                    }, c: function (b) {
                        return a.I(b) ? b() : b
                    }, Bb: function (b) {
                        return a.I(b) ? b.p() : b
                    }, fb: function (b, c, d) {
                        var k;
                        c && ("object" === typeof b.classList ? (k = b.classList[d ? "add" : "remove"], a.a.r(c.match(r), function (a) {
                            k.call(b.classList, a)
                        })) : "string" === typeof b.className.baseVal ? e(b.className, "baseVal", c, d) : e(b, "className", c, d))
                    }, bb: function (b, c) {
                        var d = a.a.c(c);
                        if (null === d || d === n)
                            d = "";
                        var e = a.f.firstChild(b);
                        !e || 3 != e.nodeType || a.f.nextSibling(e) ? a.f.fa(b, [b.ownerDocument.createTextNode(d)]) : e.data =
                                d;
                        a.a.Wc(b)
                    }, vc: function (a, b) {
                        a.name = b;
                        if (7 >= k)
                            try {
                                a.mergeAttributes(t.createElement("<input name='" + a.name + "'/>"), !1)
                            } catch (c) {
                            }
                    }, Wc: function (a) {
                        9 <= k && (a = 1 == a.nodeType ? a : a.parentNode, a.style && (a.style.zoom = ""));
                    }, Sc: function (a) {
                        if (k) {
                            var b = a.style.width;
                            a.style.width = 0;
                            a.style.width = b
                        }
                    }, nd: function (b, c) {
                        b = a.a.c(b);
                        c = a.a.c(c);
                        for (var d = [], e = b; e <= c; e++)
                            d.push(e);
                        return d
                    }, W: function (a) {
                        for (var b = [], c = 0, d = a.length; c < d; c++)
                            b.push(a[c]);
                        return b
                    }, bc: function (a) {
                        return g ? Symbol(a) : a
                    }, xd: 6 === k,
                    yd: 7 === k, C: k, ic: function (b, c) {
                        for (var d = a.a.W(b.getElementsByTagName("input")).concat(a.a.W(b.getElementsByTagName("textarea"))), e = "string" == typeof c ? function (a) {
                            return a.name === c
                        } : function (a) {
                            return c.test(a.name)
                        }, k = [], m = d.length - 1; 0 <= m; m--)
                            e(d[m]) && k.push(d[m]);
                        return k
                    }, kd: function (b) {
                        return"string" == typeof b && (b = a.a.cb(b)) ? H && H.parse ? H.parse(b) : (new Function("return " + b))() : null
                    }, Gb: function (b, c, d) {
                        if (!H || !H.stringify)
                            throw Error("Cannot find JSON.stringify(). Some browsers (e.g., IE < 8) don't support it natively, but you can overcome this by adding a script reference to json2.js, downloadable from http://www.json.org/json2.js");
                        return H.stringify(a.a.c(b), c, d)
                    }, ld: function (c, d, e) {
                        e = e || {};
                        var k = e.params || {}, m = e.includeFields || this.gc, f = c;
                        if ("object" == typeof c && "form" === a.a.A(c))
                            for (var f = c.action, l = m.length - 1; 0 <= l; l--)
                                for (var g = a.a.ic(c, m[l]), h = g.length - 1; 0 <= h; h--)
                                    k[g[h].name] = g[h].value;
                        d = a.a.c(d);
                        var r = t.createElement("form");
                        r.style.display = "none";
                        r.action = f;
                        r.method = "post";
                        for (var n in d)
                            c = t.createElement("input"), c.type = "hidden", c.name = n, c.value = a.a.Gb(a.a.c(d[n])), r.appendChild(c);
                        b(k, function (a, b) {
                            var c = t.createElement("input");
                            c.type = "hidden";
                            c.name = a;
                            c.value = b;
                            r.appendChild(c)
                        });
                        t.body.appendChild(r);
                        e.submitter ? e.submitter(r) : r.submit();
                        setTimeout(function () {
                            r.parentNode.removeChild(r)
                        }, 0)
                    }}
            }();
            a.b("utils", a.a);
            a.b("utils.arrayForEach", a.a.r);
            a.b("utils.arrayFirst", a.a.Vb);
            a.b("utils.arrayFilter", a.a.Ma);
            a.b("utils.arrayGetDistinctValues", a.a.Wb);
            a.b("utils.arrayIndexOf", a.a.o);
            a.b("utils.arrayMap", a.a.ib);
            a.b("utils.arrayPushAll", a.a.ta);
            a.b("utils.arrayRemoveItem", a.a.Na);
            a.b("utils.extend", a.a.extend);
            a.b("utils.fieldsIncludedWithJsonPost",
                    a.a.gc);
            a.b("utils.getFormFields", a.a.ic);
            a.b("utils.peekObservable", a.a.Bb);
            a.b("utils.postJson", a.a.ld);
            a.b("utils.parseJson", a.a.kd);
            a.b("utils.registerEventHandler", a.a.q);
            a.b("utils.stringifyJson", a.a.Gb);
            a.b("utils.range", a.a.nd);
            a.b("utils.toggleDomNodeCssClass", a.a.fb);
            a.b("utils.triggerEvent", a.a.Fa);
            a.b("utils.unwrapObservable", a.a.c);
            a.b("utils.objectForEach", a.a.D);
            a.b("utils.addOrRemoveItem", a.a.ra);
            a.b("utils.setTextContent", a.a.bb);
            a.b("unwrap", a.a.c);
            Function.prototype.bind || (Function.prototype.bind =
                    function (a) {
                        var c = this;
                        if (1 === arguments.length)
                            return function () {
                                return c.apply(a, arguments)
                            };
                        var d = Array.prototype.slice.call(arguments, 1);
                        return function () {
                            var e = d.slice(0);
                            e.push.apply(e, arguments);
                            return c.apply(a, e)
                        }
                    });
            a.a.e = new function () {
                function a(b, g) {
                    var h = b[d];
                    if (!h || "null" === h || !e[h]) {
                        if (!g)
                            return n;
                        h = b[d] = "ko" + c++;
                        e[h] = {}
                    }
                    return e[h]
                }
                var c = 0, d = "__ko__" + (new Date).getTime(), e = {};
                return{get: function (c, d) {
                        var e = a(c, !1);
                        return e === n ? n : e[d]
                    }, set: function (c, d, e) {
                        if (e !== n || a(c, !1) !== n)
                            a(c, !0)[d] =
                                    e
                    }, clear: function (a) {
                        var b = a[d];
                        return b ? (delete e[b], a[d] = null, !0) : !1
                    }, J: function () {
                        return c++ + d
                    }}
            };
            a.b("utils.domData", a.a.e);
            a.b("utils.domData.clear", a.a.e.clear);
            a.a.G = new function () {
                function b(b, c) {
                    var e = a.a.e.get(b, d);
                    e === n && c && (e = [], a.a.e.set(b, d, e));
                    return e
                }
                function c(d) {
                    var e = b(d, !1);
                    if (e)
                        for (var e = e.slice(0), l = 0; l < e.length; l++)
                            e[l](d);
                    a.a.e.clear(d);
                    a.a.G.cleanExternalData(d);
                    if (f[d.nodeType])
                        for (e = d.firstChild; d = e; )
                            e = d.nextSibling, 8 === d.nodeType && c(d)
                }
                var d = a.a.e.J(), e = {1: !0, 8: !0, 9: !0},
                        f = {1: !0, 9: !0};
                return{qa: function (a, c) {
                        if ("function" != typeof c)
                            throw Error("Callback must be a function");
                        b(a, !0).push(c)
                    }, tc: function (c, e) {
                        var f = b(c, !1);
                        f && (a.a.Na(f, e), 0 == f.length && a.a.e.set(c, d, n))
                    }, ba: function (b) {
                        if (e[b.nodeType] && (c(b), f[b.nodeType])) {
                            var d = [];
                            a.a.ta(d, b.getElementsByTagName("*"));
                            for (var l = 0, m = d.length; l < m; l++)
                                c(d[l])
                        }
                        return b
                    }, removeNode: function (b) {
                        a.ba(b);
                        b.parentNode && b.parentNode.removeChild(b)
                    }, cleanExternalData: function (a) {
                        u && "function" == typeof u.cleanData && u.cleanData([a])
                    }}
            };
            a.ba = a.a.G.ba;
            a.removeNode = a.a.G.removeNode;
            a.b("cleanNode", a.ba);
            a.b("removeNode", a.removeNode);
            a.b("utils.domNodeDisposal", a.a.G);
            a.b("utils.domNodeDisposal.addDisposeCallback", a.a.G.qa);
            a.b("utils.domNodeDisposal.removeDisposeCallback", a.a.G.tc);
            (function () {
                var b = [0, "", ""], c = [1, "<table>", "</table>"], d = [3, "<table><tbody><tr>", "</tr></tbody></table>"], e = [1, "<select multiple='multiple'>", "</select>"], f = {thead: c, tbody: c, tfoot: c, tr: [2, "<table><tbody>", "</tbody></table>"], td: d, th: d, option: e, optgroup: e},
                        g = 8 >= a.a.C;
                a.a.na = function (c, d) {
                    var e;
                    if (u)
                        if (u.parseHTML)
                            e = u.parseHTML(c, d) || [];
                        else {
                            if ((e = u.clean([c], d)) && e[0]) {
                                for (var k = e[0]; k.parentNode && 11 !== k.parentNode.nodeType; )
                                    k = k.parentNode;
                                k.parentNode && k.parentNode.removeChild(k)
                            }
                        }
                    else {
                        (e = d) || (e = t);
                        var k = e.parentWindow || e.defaultView || x, r = a.a.cb(c).toLowerCase(), q = e.createElement("div"), p;
                        p = (r = r.match(/^<([a-z]+)[ >]/)) && f[r[1]] || b;
                        r = p[0];
                        p = "ignored<div>" + p[1] + c + p[2] + "</div>";
                        "function" == typeof k.innerShiv ? q.appendChild(k.innerShiv(p)) : (g && e.appendChild(q),
                                q.innerHTML = p, g && q.parentNode.removeChild(q));
                        for (; r--; )
                            q = q.lastChild;
                        e = a.a.W(q.lastChild.childNodes)
                    }
                    return e
                };
                a.a.Eb = function (b, c) {
                    a.a.rb(b);
                    c = a.a.c(c);
                    if (null !== c && c !== n)
                        if ("string" != typeof c && (c = c.toString()), u)
                            u(b).html(c);
                        else
                            for (var d = a.a.na(c, b.ownerDocument), e = 0; e < d.length; e++)
                                b.appendChild(d[e])
                }
            })();
            a.b("utils.parseHtmlFragment", a.a.na);
            a.b("utils.setHtml", a.a.Eb);
            a.N = function () {
                function b(c, e) {
                    if (c)
                        if (8 == c.nodeType) {
                            var f = a.N.pc(c.nodeValue);
                            null != f && e.push({Qc: c, hd: f})
                        } else if (1 == c.nodeType)
                            for (var f =
                                    0, g = c.childNodes, h = g.length; f < h; f++)
                                b(g[f], e)
                }
                var c = {};
                return{yb: function (a) {
                        if ("function" != typeof a)
                            throw Error("You can only pass a function to ko.memoization.memoize()");
                        var b = (4294967296 * (1 + Math.random()) | 0).toString(16).substring(1) + (4294967296 * (1 + Math.random()) | 0).toString(16).substring(1);
                        c[b] = a;
                        return"\x3c!--[ko_memo:" + b + "]--\x3e"
                    }, Bc: function (a, b) {
                        var f = c[a];
                        if (f === n)
                            throw Error("Couldn't find any memo with ID " + a + ". Perhaps it's already been unmemoized.");
                        try {
                            return f.apply(null, b || []),
                                    !0
                        } finally {
                            delete c[a]
                        }
                    }, Cc: function (c, e) {
                        var f = [];
                        b(c, f);
                        for (var g = 0, h = f.length; g < h; g++) {
                            var l = f[g].Qc, m = [l];
                            e && a.a.ta(m, e);
                            a.N.Bc(f[g].hd, m);
                            l.nodeValue = "";
                            l.parentNode && l.parentNode.removeChild(l)
                        }
                    }, pc: function (a) {
                        return(a = a.match(/^\[ko_memo\:(.*?)\]$/)) ? a[1] : null
                    }}
            }();
            a.b("memoization", a.N);
            a.b("memoization.memoize", a.N.yb);
            a.b("memoization.unmemoize", a.N.Bc);
            a.b("memoization.parseMemoText", a.N.pc);
            a.b("memoization.unmemoizeDomNodeAndDescendants", a.N.Cc);
            a.Z = function () {
                function b() {
                    if (e)
                        for (var b =
                                e, c = 0, m; g < e; )
                            if (m = d[g++]) {
                                if (g > b) {
                                    if (5E3 <= ++c) {
                                        g = e;
                                        a.a.dc(Error("'Too much recursion' after processing " + c + " task groups."));
                                        break
                                    }
                                    b = e
                                }
                                try {
                                    m()
                                } catch (k) {
                                    a.a.dc(k)
                                }
                            }
                }
                function c() {
                    b();
                    g = e = d.length = 0
                }
                var d = [], e = 0, f = 1, g = 0;
                return{scheduler: x.MutationObserver ? function (a) {
                        var b = t.createElement("div");
                        (new MutationObserver(a)).observe(b, {attributes: !0});
                        return function () {
                            b.classList.toggle("foo")
                        }
                    }(c) : t && "onreadystatechange"in t.createElement("script") ? function (a) {
                        var b = t.createElement("script");
                        b.onreadystatechange =
                                function () {
                                    b.onreadystatechange = null;
                                    t.documentElement.removeChild(b);
                                    b = null;
                                    a()
                                };
                        t.documentElement.appendChild(b)
                    } : function (a) {
                        setTimeout(a, 0)
                    }, Za: function (b) {
                        e || a.Z.scheduler(c);
                        d[e++] = b;
                        return f++
                    }, cancel: function (a) {
                        a -= f - e;
                        a >= g && a < e && (d[a] = null)
                    }, resetForTesting: function () {
                        var a = e - g;
                        g = e = d.length = 0;
                        return a
                    }, rd: b}
            }();
            a.b("tasks", a.Z);
            a.b("tasks.schedule", a.Z.Za);
            a.b("tasks.runEarly", a.Z.rd);
            a.Aa = {throttle: function (b, c) {
                    b.throttleEvaluation = c;
                    var d = null;
                    return a.B({read: b, write: function (e) {
                            clearTimeout(d);
                            d = a.a.setTimeout(function () {
                                b(e)
                            }, c)
                        }})
                }, rateLimit: function (a, c) {
                    var d, e, f;
                    "number" == typeof c ? d = c : (d = c.timeout, e = c.method);
                    a.gb = !1;
                    f = "notifyWhenChangesStop" == e ? T : S;
                    a.Wa(function (a) {
                        return f(a, d)
                    })
                }, deferred: function (b, c) {
                    if (!(0 !== c))

                        throw Error("The 'deferred' extender only accepts the value 'true', because it is not supported to turn deferral off once enabled.");
                    b.gb || (b.gb = !0, b.Wa(function (c) {
                        var e, f = !1;
                        return function () {
                            if (!f) {
                                a.Z.cancel(e);
                                e = a.Z.Za(c);
                                try {
                                    f = !0, b.notifySubscribers(n, "dirty")
                                } finally {
                                    f =
                                            !1
                                }
                            }
                        }
                    }))
                }, notify: function (a, c) {
                    a.equalityComparer = "always" == c ? null : J
                }};
            var R = {undefined: 1, "boolean": 1, number: 1, string: 1};
            a.b("extenders", a.Aa);
            a.zc = function (b, c, d) {
                this.$ = b;
                this.jb = c;
                this.Pc = d;
                this.T = !1;
                a.H(this, "dispose", this.k)
            };
            a.zc.prototype.k = function () {
                this.T = !0;
                this.Pc()
            };
            a.K = function () {
                a.a.ab(this, D);
                D.ub(this)
            };
            var E = "change", D = {ub: function (a) {
                    a.F = {change: []};
                    a.Qb = 1
                }, Y: function (b, c, d) {
                    var e = this;
                    d = d || E;
                    var f = new a.zc(e, c ? b.bind(c) : b, function () {
                        a.a.Na(e.F[d], f);
                        e.Ka && e.Ka(d)
                    });
                    e.ua && e.ua(d);
                    e.F[d] || (e.F[d] = []);
                    e.F[d].push(f);
                    return f
                }, notifySubscribers: function (b, c) {
                    c = c || E;
                    c === E && this.Kb();
                    if (this.Ra(c)) {
                        var d = c === E && this.Fc || this.F[c].slice(0);
                        try {
                            a.l.Xb();
                            for (var e = 0, f; f = d[e]; ++e)
                                f.T || f.jb(b)
                        } finally {
                            a.l.end()
                        }
                    }
                }, Pa: function () {
                    return this.Qb
                }, Zc: function (a) {
                    return this.Pa() !== a
                }, Kb: function () {
                    ++this.Qb
                }, Wa: function (b) {
                    var c = this, d = a.I(c), e, f, g, h;
                    c.Ja || (c.Ja = c.notifySubscribers, c.notifySubscribers = U);
                    var l = b(function () {
                        c.Ha = !1;
                        d && h === c && (h = c.Mb ? c.Mb() : c());
                        var a = f || c.Ua(g, h);
                        f = e = !1;
                        a && c.Ja(g = h)
                    });
                    c.Pb = function (a) {
                        c.Fc = c.F[E].slice(0);
                        c.Ha = e = !0;
                        h = a;
                        l()
                    };
                    c.Ob = function (a) {
                        e || (g = a, c.Ja(a, "beforeChange"))
                    };
                    c.Hc = function () {
                        c.Ua(g, c.p(!0)) && (f = !0)
                    }
                }, Ra: function (a) {
                    return this.F[a] && this.F[a].length
                }, Xc: function (b) {
                    if (b)
                        return this.F[b] && this.F[b].length || 0;
                    var c = 0;
                    a.a.D(this.F, function (a, b) {
                        "dirty" !== a && (c += b.length)
                    });
                    return c
                }, Ua: function (a, c) {
                    return!this.equalityComparer || !this.equalityComparer(a, c)
                }, extend: function (b) {
                    var c = this;
                    b && a.a.D(b, function (b, e) {
                        var f = a.Aa[b];
                        "function" ==
                                typeof f && (c = f(c, e) || c)
                    });
                    return c
                }};
            a.H(D, "subscribe", D.Y);
            a.H(D, "extend", D.extend);
            a.H(D, "getSubscriptionsCount", D.Xc);
            a.a.la && a.a.$a(D, Function.prototype);
            a.K.fn = D;
            a.lc = function (a) {
                return null != a && "function" == typeof a.Y && "function" == typeof a.notifySubscribers
            };
            a.b("subscribable", a.K);
            a.b("isSubscribable", a.lc);
            a.xa = a.l = function () {
                function b(a) {
                    d.push(e);
                    e = a
                }
                function c() {
                    e = d.pop()
                }
                var d = [], e, f = 0;
                return{Xb: b, end: c, sc: function (b) {
                        if (e) {
                            if (!a.lc(b))
                                throw Error("Only subscribable things can act as dependencies");
                            e.jb.call(e.Lc, b, b.Gc || (b.Gc = ++f))
                        }
                    }, w: function (a, d, e) {
                        try {
                            return b(), a.apply(d, e || [])
                        } finally {
                            c()
                        }
                    }, Ca: function () {
                        if (e)
                            return e.m.Ca()
                    }, Va: function () {
                        if (e)
                            return e.Va
                    }}
            }();
            a.b("computedContext", a.xa);
            a.b("computedContext.getDependenciesCount", a.xa.Ca);
            a.b("computedContext.isInitial", a.xa.Va);
            a.b("ignoreDependencies", a.wd = a.l.w);
            var F = a.a.bc("_latestValue");
            a.O = function (b) {
                function c() {
                    if (0 < arguments.length)
                        return c.Ua(c[F], arguments[0]) && (c.ia(), c[F] = arguments[0], c.ha()), this;
                    a.l.sc(c);
                    return c[F]
                }
                c[F] = b;
                a.a.la || a.a.extend(c, a.K.fn);
                a.K.fn.ub(c);
                a.a.ab(c, B);
                a.options.deferUpdates && a.Aa.deferred(c, !0);
                return c
            };
            var B = {equalityComparer: J, p: function () {
                    return this[F]
                }, ha: function () {
                    this.notifySubscribers(this[F])
                }, ia: function () {
                    this.notifySubscribers(this[F], "beforeChange")
                }};
            a.a.la && a.a.$a(B, a.K.fn);
            var I = a.O.md = "__ko_proto__";
            B[I] = a.O;
            a.Qa = function (b, c) {
                return null === b || b === n || b[I] === n ? !1 : b[I] === c ? !0 : a.Qa(b[I], c)
            };
            a.I = function (b) {
                return a.Qa(b, a.O)
            };
            a.Da = function (b) {
                return"function" == typeof b &&
                        b[I] === a.O || "function" == typeof b && b[I] === a.B && b.$c ? !0 : !1
            };
            a.b("observable", a.O);
            a.b("isObservable", a.I);
            a.b("isWriteableObservable", a.Da);
            a.b("isWritableObservable", a.Da);
            a.b("observable.fn", B);
            a.H(B, "peek", B.p);
            a.H(B, "valueHasMutated", B.ha);
            a.H(B, "valueWillMutate", B.ia);
            a.ma = function (b) {
                b = b || [];
                if ("object" != typeof b || !("length"in b))
                    throw Error("The argument passed when initializing an observable array must be an array, or null, or undefined.");
                b = a.O(b);
                a.a.ab(b, a.ma.fn);
                return b.extend({trackArrayChanges: !0})
            };
            a.ma.fn = {remove: function (b) {
                    for (var c = this.p(), d = [], e = "function" != typeof b || a.I(b) ? function (a) {
                        return a === b
                    } : b, f = 0; f < c.length; f++) {
                        var g = c[f];
                        e(g) && (0 === d.length && this.ia(), d.push(g), c.splice(f, 1), f--)
                    }
                    d.length && this.ha();
                    return d
                }, removeAll: function (b) {
                    if (b === n) {
                        var c = this.p(), d = c.slice(0);
                        this.ia();
                        c.splice(0, c.length);
                        this.ha();
                        return d
                    }
                    return b ? this.remove(function (c) {
                        return 0 <= a.a.o(b, c)
                    }) : []
                }, destroy: function (b) {
                    var c = this.p(), d = "function" != typeof b || a.I(b) ? function (a) {
                        return a === b
                    } : b;
                    this.ia();
                    for (var e = c.length - 1; 0 <= e; e--)
                        d(c[e]) && (c[e]._destroy = !0);
                    this.ha()
                }, destroyAll: function (b) {
                    return b === n ? this.destroy(function () {
                        return!0
                    }) : b ? this.destroy(function (c) {
                        return 0 <= a.a.o(b, c)
                    }) : []
                }, indexOf: function (b) {
                    var c = this();
                    return a.a.o(c, b)
                }, replace: function (a, c) {
                    var d = this.indexOf(a);
                    0 <= d && (this.ia(), this.p()[d] = c, this.ha())
                }};
            a.a.la && a.a.$a(a.ma.fn, a.O.fn);
            a.a.r("pop push reverse shift sort splice unshift".split(" "), function (b) {
                a.ma.fn[b] = function () {
                    var a = this.p();
                    this.ia();
                    this.Yb(a, b, arguments);
                    var d = a[b].apply(a, arguments);
                    this.ha();
                    return d === a ? this : d
                }
            });
            a.a.r(["slice"], function (b) {
                a.ma.fn[b] = function () {
                    var a = this();
                    return a[b].apply(a, arguments)
                }
            });
            a.b("observableArray", a.ma);
            a.Aa.trackArrayChanges = function (b, c) {
                function d() {
                    if (!e) {
                        e = !0;
                        l = b.notifySubscribers;
                        b.notifySubscribers = function (a, b) {
                            b && b !== E || ++h;
                            return l.apply(this, arguments)
                        };
                        var c = [].concat(b.p() || []);
                        f = null;
                        g = b.Y(function (d) {
                            d = [].concat(d || []);
                            if (b.Ra("arrayChange")) {
                                var e;
                                if (!f || 1 < h)
                                    f = a.a.lb(c, d, b.kb);
                                e = f
                            }
                            c = d;
                            f = null;
                            h = 0;
                            e && e.length && b.notifySubscribers(e, "arrayChange")
                        })
                    }
                }
                b.kb = {};
                c && "object" == typeof c && a.a.extend(b.kb, c);
                b.kb.sparse = !0;
                if (!b.Yb) {
                    var e = !1, f = null, g, h = 0, l, m = b.ua, k = b.Ka;
                    b.ua = function (a) {
                        m && m.call(b, a);
                        "arrayChange" === a && d()
                    };
                    b.Ka = function (a) {
                        k && k.call(b, a);
                        "arrayChange" !== a || b.Ra("arrayChange") || (l && (b.notifySubscribers = l, l = n), g.k(), e = !1)
                    };
                    b.Yb = function (b, c, d) {
                        function k(a, b, c) {
                            return m[m.length] = {status: a, value: b, index: c}
                        }
                        if (e && !h) {
                            var m = [], l = b.length, g = d.length, G = 0;
                            switch (c) {
                                case "push":
                                    G = l;
                                case "unshift":
                                    for (c =
                                            0; c < g; c++)
                                        k("added", d[c], G + c);
                                    break;
                                case "pop":
                                    G = l - 1;
                                case "shift":
                                    l && k("deleted", b[G], G);
                                    break;
                                case "splice":
                                    c = Math.min(Math.max(0, 0 > d[0] ? l + d[0] : d[0]), l);
                                    for (var l = 1 === g ? l : Math.min(c + (d[1] || 0), l), g = c + g - 2, G = Math.max(l, g), n = [], s = [], w = 2; c < G; ++c, ++w)
                                        c < l && s.push(k("deleted", b[c], c)), c < g && n.push(k("added", d[w], c));
                                    a.a.hc(s, n);
                                    break;
                                default:
                                    return
                            }
                            f = m
                        }
                    }
                }
            };
            var s = a.a.bc("_state");
            a.m = a.B = function (b, c, d) {
                function e() {
                    if (0 < arguments.length) {
                        if ("function" === typeof f)
                            f.apply(g.sb, arguments);
                        else
                            throw Error("Cannot write a value to a ko.computed unless you specify a 'write' option. If you wish to read the current value, don't pass any parameters.");
                        return this
                    }
                    a.l.sc(e);
                    (g.V || g.t && e.Sa()) && e.U();
                    return g.M
                }
                "object" === typeof b ? d = b : (d = d || {}, b && (d.read = b));
                if ("function" != typeof d.read)
                    throw Error("Pass a function that returns the value of the ko.computed");
                var f = d.write, g = {M: n, da: !0, V: !0, Ta: !1, Hb: !1, T: !1, Ya: !1, t: !1, od: d.read, sb: c || d.owner, i: d.disposeWhenNodeIsRemoved || d.i || null, ya: d.disposeWhen || d.ya, pb: null, s: {}, L: 0, fc: null};
                e[s] = g;
                e.$c = "function" === typeof f;
                a.a.la || a.a.extend(e, a.K.fn);
                a.K.fn.ub(e);
                a.a.ab(e, z);
                d.pure ? (g.Ya = !0, g.t = !0, a.a.extend(e,
                        Y)) : d.deferEvaluation && a.a.extend(e, Z);
                a.options.deferUpdates && a.Aa.deferred(e, !0);
                g.i && (g.Hb = !0, g.i.nodeType || (g.i = null));
                g.t || d.deferEvaluation || e.U();
                g.i && e.ca() && a.a.G.qa(g.i, g.pb = function () {
                    e.k()
                });
                return e
            };
            var z = {equalityComparer: J, Ca: function () {
                    return this[s].L
                }, Sb: function (a, c, d) {
                    if (this[s].Ya && c === this)
                        throw Error("A 'pure' computed must not be called recursively");
                    this[s].s[a] = d;
                    d.Ia = this[s].L++;
                    d.pa = c.Pa()
                }, Sa: function () {
                    var a, c, d = this[s].s;
                    for (a in d)
                        if (d.hasOwnProperty(a) && (c = d[a], this.oa &&
                                c.$.Ha || c.$.Zc(c.pa)))
                            return!0
                }, gd: function () {
                    this.oa && !this[s].Ta && this.oa(!1)
                }, ca: function () {
                    var a = this[s];
                    return a.V || 0 < a.L
                }, qd: function () {
                    this.Ha ? this[s].V && (this[s].da = !0) : this.ec()
                }, yc: function (a) {
                    if (a.gb && !this[s].i) {
                        var c = a.Y(this.gd, this, "dirty"), d = a.Y(this.qd, this);
                        return{$: a, k: function () {
                                c.k();
                                d.k()
                            }}
                    }
                    return a.Y(this.ec, this)
                }, ec: function () {
                    var b = this, c = b.throttleEvaluation;
                    c && 0 <= c ? (clearTimeout(this[s].fc), this[s].fc = a.a.setTimeout(function () {
                        b.U(!0)
                    }, c)) : b.oa ? b.oa(!0) : b.U(!0)
                }, U: function (b) {
                    var c =
                            this[s], d = c.ya, e = !1;
                    if (!c.Ta && !c.T) {
                        if (c.i && !a.a.qb(c.i) || d && d()) {
                            if (!c.Hb) {
                                this.k();
                                return
                            }
                        } else
                            c.Hb = !1;
                        c.Ta = !0;
                        try {
                            e = this.Vc(b)
                        } finally {
                            c.Ta = !1
                        }
                        c.L || this.k();
                        return e
                    }
                }, Vc: function (b) {
                    var c = this[s], d = !1, e = c.Ya ? n : !c.L, f = {Mc: this, Oa: c.s, ob: c.L};
                    a.l.Xb({Lc: f, jb: W, m: this, Va: e});
                    c.s = {};
                    c.L = 0;
                    f = this.Uc(c, f);
                    this.Ua(c.M, f) && (c.t || this.notifySubscribers(c.M, "beforeChange"), c.M = f, c.t ? this.Kb() : b && this.notifySubscribers(c.M), d = !0);
                    e && this.notifySubscribers(c.M, "awake");
                    return d
                }, Uc: function (b, c) {
                    try {
                        var d =
                                b.od;
                        return b.sb ? d.call(b.sb) : d()
                    } finally {
                        a.l.end(), c.ob && !b.t && a.a.D(c.Oa, V), b.da = b.V = !1
                    }
                }, p: function (a) {
                    var c = this[s];
                    (c.V && (a || !c.L) || c.t && this.Sa()) && this.U();
                    return c.M
                }, Wa: function (b) {
                    a.K.fn.Wa.call(this, b);
                    this.Mb = function () {
                        this[s].da ? this.U() : this[s].V = !1;
                        return this[s].M
                    };
                    this.oa = function (a) {
                        this.Ob(this[s].M);
                        this[s].V = !0;
                        a && (this[s].da = !0);
                        this.Pb(this)
                    }
                }, k: function () {
                    var b = this[s];
                    !b.t && b.s && a.a.D(b.s, function (a, b) {
                        b.k && b.k()
                    });
                    b.i && b.pb && a.a.G.tc(b.i, b.pb);
                    b.s = null;
                    b.L = 0;
                    b.T = !0;
                    b.da =
                            !1;
                    b.V = !1;
                    b.t = !1;
                    b.i = null
                }}, Y = {ua: function (b) {
                    var c = this, d = c[s];
                    if (!d.T && d.t && "change" == b) {
                        d.t = !1;
                        if (d.da || c.Sa())
                            d.s = null, d.L = 0, c.U() && c.Kb();
                        else {
                            var e = [];
                            a.a.D(d.s, function (a, b) {
                                e[b.Ia] = a
                            });
                            a.a.r(e, function (a, b) {
                                var e = d.s[a], l = c.yc(e.$);
                                l.Ia = b;
                                l.pa = e.pa;
                                d.s[a] = l
                            })
                        }
                        d.T || c.notifySubscribers(d.M, "awake")
                    }
                }, Ka: function (b) {
                    var c = this[s];
                    c.T || "change" != b || this.Ra("change") || (a.a.D(c.s, function (a, b) {
                        b.k && (c.s[a] = {$: b.$, Ia: b.Ia, pa: b.pa}, b.k())
                    }), c.t = !0, this.notifySubscribers(n, "asleep"))
                }, Pa: function () {
                    var b =
                            this[s];
                    b.t && (b.da || this.Sa()) && this.U();
                    return a.K.fn.Pa.call(this)
                }}, Z = {ua: function (a) {
                    "change" != a && "beforeChange" != a || this.p()
                }};
            a.a.la && a.a.$a(z, a.K.fn);
            var P = a.O.md;
            a.m[P] = a.O;
            z[P] = a.m;
            a.bd = function (b) {
                return a.Qa(b, a.m)
            };
            a.cd = function (b) {
                return a.Qa(b, a.m) && b[s] && b[s].Ya
            };
            a.b("computed", a.m);
            a.b("dependentObservable", a.m);
            a.b("isComputed", a.bd);
            a.b("isPureComputed", a.cd);
            a.b("computed.fn", z);
            a.H(z, "peek", z.p);
            a.H(z, "dispose", z.k);
            a.H(z, "isActive", z.ca);
            a.H(z, "getDependenciesCount", z.Ca);
            a.rc =
                    function (b, c) {
                        if ("function" === typeof b)
                            return a.m(b, c, {pure: !0});
                        b = a.a.extend({}, b);
                        b.pure = !0;
                        return a.m(b, c)
                    };
            a.b("pureComputed", a.rc);
            (function () {
                function b(a, f, g) {
                    g = g || new d;
                    a = f(a);
                    if ("object" != typeof a || null === a || a === n || a instanceof RegExp || a instanceof Date || a instanceof String || a instanceof Number || a instanceof Boolean)
                        return a;
                    var h = a instanceof Array ? [] : {};
                    g.save(a, h);
                    c(a, function (c) {
                        var d = f(a[c]);
                        switch (typeof d) {
                            case "boolean":
                            case "number":
                            case "string":
                            case "function":
                                h[c] = d;
                                break;
                            case "object":
                            case "undefined":
                                var k =
                                        g.get(d);
                                h[c] = k !== n ? k : b(d, f, g)
                        }
                    });
                    return h
                }
                function c(a, b) {
                    if (a instanceof Array) {
                        for (var c = 0; c < a.length; c++)
                            b(c);
                        "function" == typeof a.toJSON && b("toJSON")
                    } else
                        for (c in a)
                            b(c)
                }
                function d() {
                    this.keys = [];
                    this.Lb = []
                }
                a.Ac = function (c) {
                    if (0 == arguments.length)
                        throw Error("When calling ko.toJS, pass the object you want to convert.");
                    return b(c, function (b) {
                        for (var c = 0; a.I(b) && 10 > c; c++)
                            b = b();
                        return b
                    })
                };
                a.toJSON = function (b, c, d) {
                    b = a.Ac(b);
                    return a.a.Gb(b, c, d)
                };
                d.prototype = {save: function (b, c) {
                        var d = a.a.o(this.keys,
                                b);
                        0 <= d ? this.Lb[d] = c : (this.keys.push(b), this.Lb.push(c))
                    }, get: function (b) {
                        b = a.a.o(this.keys, b);
                        return 0 <= b ? this.Lb[b] : n
                    }}
            })();
            a.b("toJS", a.Ac);
            a.b("toJSON", a.toJSON);
            (function () {
                a.j = {u: function (b) {
                        switch (a.a.A(b)) {
                            case "option":
                                return !(0 === b.__ko__hasDomDataOptionValue__)? a.a.e.get(b, a.d.options.zb) : 7 >= a.a.C ? b.getAttributeNode("value") && b.getAttributeNode("value").specified ? b.value : b.text : b.value;
                            case "select":
                                return 0 <= b.selectedIndex ? a.j.u(b.options[b.selectedIndex]) : n;
                            default:
                                return b.value
                        }
                    }, ja: function (b,
                            c, d) {
                        switch (a.a.A(b)) {
                            case "option":
                                switch (typeof c) {
                                    case "string":
                                        a.a.e.set(b, a.d.options.zb, n);
                                        "__ko__hasDomDataOptionValue__"in b && delete b.__ko__hasDomDataOptionValue__;
                                        b.value = c;
                                        break;
                                    default:
                                        a.a.e.set(b, a.d.options.zb, c), b.__ko__hasDomDataOptionValue__ = !0, b.value = "number" === typeof c ? c : ""
                                }
                                break;
                            case "select":
                                if ("" === c || null === c)
                                    c = n;
                                for (var e = -1, f = 0, g = b.options.length, h; f < g; ++f)
                                    if (h = a.j.u(b.options[f]), h == c || "" == h && c === n) {
                                        e = f;
                                        break
                                    }
                                if (d || 0 <= e || c === n && 1 < b.size)
                                    b.selectedIndex = e;
                                break;
                            default:
                                if (null ===
                                        c || c === n)
                                    c = "";
                                b.value = c
                        }
                    }}
            })();
            a.b("selectExtensions", a.j);
            a.b("selectExtensions.readValue", a.j.u);
            a.b("selectExtensions.writeValue", a.j.ja);
            a.h = function () {
                function b(b) {
                    b = a.a.cb(b);
                    123 === b.charCodeAt(0) && (b = b.slice(1, -1));
                    var c = [], d = b.match(e), r, h = [], p = 0;
                    if (d) {
                        d.push(",");
                        for (var A = 0, y; y = d[A]; ++A) {
                            var v = y.charCodeAt(0);
                            if (44 === v) {
                                if (0 >= p) {
                                    c.push(r && h.length ? {key: r, value: h.join("")} : {unknown: r || h.join("")});
                                    r = p = 0;
                                    h = [];
                                    continue
                                }
                            } else if (58 === v) {
                                if (!p && !r && 1 === h.length) {
                                    r = h.pop();
                                    continue
                                }
                            } else
                                47 ===
                                        v && A && 1 < y.length ? (v = d[A - 1].match(f)) && !g[v[0]] && (b = b.substr(b.indexOf(y) + 1), d = b.match(e), d.push(","), A = -1, y = "/") : 40 === v || 123 === v || 91 === v ? ++p : 41 === v || 125 === v || 93 === v ? --p : r || h.length || 34 !== v && 39 !== v || (y = y.slice(1, -1));
                            h.push(y)
                        }
                    }
                    return c
                }
                var c = ["true", "false", "null", "undefined"], d = /^(?:[$_a-z][$\w]*|(.+)(\.\s*[$_a-z][$\w]*|\[.+\]))$/i, e = RegExp("\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*'|/(?:[^/\\\\]|\\\\.)*/w*|[^\\s:,/][^,\"'{}()/:[\\]]*[^\\s,\"'{}()/:[\\]]|[^\\s]", "g"), f = /[\])"'A-Za-z0-9_$]+$/,
                        g = {"in": 1, "return": 1, "typeof": 1}, h = {};
                return{va: [], ga: h, Ab: b, Xa: function (e, m) {
                        function k(b, e) {
                            var m;
                            if (!A) {
                                var l = a.getBindingHandler(b);
                                if (l && l.preprocess && !(e = l.preprocess(e, b, k)))
                                    return;
                                if (l = h[b])
                                    m = e, 0 <= a.a.o(c, m) ? m = !1 : (l = m.match(d), m = null === l ? !1 : l[1] ? "Object(" + l[1] + ")" + l[2] : m), l = m;
                                l && g.push("'" + b + "':function(_z){" + m + "=_z}")
                            }
                            p && (e = "function(){return " + e + " }");
                            f.push("'" + b + "':" + e)
                        }
                        m = m || {};
                        var f = [], g = [], p = m.valueAccessors, A = m.bindingParams, y = "string" === typeof e ? b(e) : e;
                        a.a.r(y, function (a) {
                            k(a.key ||
                                    a.unknown, a.value)
                        });
                        g.length && k("_ko_property_writers", "{" + g.join(",") + " }");
                        return f.join(",")
                    }, fd: function (a, b) {
                        for (var c = 0; c < a.length; c++)
                            if (a[c].key == b)
                                return!0;
                        return!1
                    }, Ga: function (b, c, d, e, f) {
                        if (b && a.I(b))
                            !a.Da(b) || f && b.p() === e || b(e);
                        else if ((b = c.get("_ko_property_writers")) && b[d])
                            b[d](e)
                    }}
            }();
            a.b("expressionRewriting", a.h);
            a.b("expressionRewriting.bindingRewriteValidators", a.h.va);
            a.b("expressionRewriting.parseObjectLiteral", a.h.Ab);
            a.b("expressionRewriting.preProcessBindings", a.h.Xa);
            a.b("expressionRewriting._twoWayBindings",
                    a.h.ga);
            a.b("jsonExpressionRewriting", a.h);
            a.b("jsonExpressionRewriting.insertPropertyAccessorsIntoJson", a.h.Xa);
            (function () {
                function b(a) {
                    return 8 == a.nodeType && g.test(f ? a.text : a.nodeValue)
                }
                function c(a) {
                    return 8 == a.nodeType && h.test(f ? a.text : a.nodeValue)
                }
                function d(a, d) {
                    for (var e = a, f = 1, l = []; e = e.nextSibling; ) {
                        if (c(e) && (f--, 0 === f))
                            return l;
                        l.push(e);
                        b(e) && f++
                    }
                    if (!d)
                        throw Error("Cannot find closing comment tag to match: " + a.nodeValue);
                    return null
                }
                function e(a, b) {
                    var c = d(a, b);
                    return c ? 0 < c.length ? c[c.length -
                            1].nextSibling : a.nextSibling : null
                }
                var f = t && "\x3c!--test--\x3e" === t.createComment("test").text, g = f ? /^\x3c!--\s*ko(?:\s+([\s\S]+))?\s*--\x3e$/ : /^\s*ko(?:\s+([\s\S]+))?\s*$/, h = f ? /^\x3c!--\s*\/ko\s*--\x3e$/ : /^\s*\/ko\s*$/, l = {ul: !0, ol: !0};
                a.f = {aa: {}, childNodes: function (a) {
                        return b(a) ? d(a) : a.childNodes
                    }, za: function (c) {
                        if (b(c)) {
                            c = a.f.childNodes(c);
                            for (var d = 0, e = c.length; d < e; d++)
                                a.removeNode(c[d])
                        } else
                            a.a.rb(c)
                    }, fa: function (c, d) {
                        if (b(c)) {
                            a.f.za(c);
                            for (var e = c.nextSibling, f = 0, l = d.length; f < l; f++)
                                e.parentNode.insertBefore(d[f],
                                        e)
                        } else
                            a.a.fa(c, d)
                    }, qc: function (a, c) {
                        b(a) ? a.parentNode.insertBefore(c, a.nextSibling) : a.firstChild ? a.insertBefore(c, a.firstChild) : a.appendChild(c)
                    }, kc: function (c, d, e) {
                        e ? b(c) ? c.parentNode.insertBefore(d, e.nextSibling) : e.nextSibling ? c.insertBefore(d, e.nextSibling) : c.appendChild(d) : a.f.qc(c, d)
                    }, firstChild: function (a) {
                        return b(a) ? !a.nextSibling || c(a.nextSibling) ? null : a.nextSibling : a.firstChild
                    }, nextSibling: function (a) {
                        b(a) && (a = e(a));
                        return a.nextSibling && c(a.nextSibling) ? null : a.nextSibling
                    }, Yc: b, vd: function (a) {
                        return(a =
                                (f ? a.text : a.nodeValue).match(g)) ? a[1] : null
                    }, oc: function (d) {
                        if (l[a.a.A(d)]) {
                            var k = d.firstChild;
                            if (k) {
                                do
                                    if (1 === k.nodeType) {
                                        var f;
                                        f = k.firstChild;
                                        var g = null;
                                        if (f) {
                                            do
                                                if (g)
                                                    g.push(f);
                                                else if (b(f)) {
                                                    var h = e(f, !0);
                                                    h ? f = h : g = [f]
                                                } else
                                                    c(f) && (g = [f]);
                                            while (f = f.nextSibling)
                                        }
                                        if (f = g)
                                            for (g = k.nextSibling, h = 0; h < f.length; h++)
                                                g ? d.insertBefore(f[h], g) : d.appendChild(f[h])
                                    }
                                while (k = k.nextSibling)
                            }
                        }
                    }}
            })();
            a.b("virtualElements", a.f);
            a.b("virtualElements.allowedBindings", a.f.aa);
            a.b("virtualElements.emptyNode", a.f.za);
            a.b("virtualElements.insertAfter",
                    a.f.kc);
            a.b("virtualElements.prepend", a.f.qc);
            a.b("virtualElements.setDomNodeChildren", a.f.fa);
            (function () {
                a.S = function () {
                    this.Kc = {}
                };
                a.a.extend(a.S.prototype, {nodeHasBindings: function (b) {
                        switch (b.nodeType) {
                            case 1:
                                return null != b.getAttribute("data-bind") || a.g.getComponentNameForNode(b);
                            case 8:
                                return a.f.Yc(b);
                            default:
                                return!1
                        }
                    }, getBindings: function(b, c) {
                        var d = this.getBindingsString(b, c);
                        d = d ? this.parseBindingsString(d, c, b) : null;
                        return a.g.Rb(d, b, c, !1);
                    },
                    
                    getBindingAccessors: function(b, c) {
                        var d = this.getBindingsString(b, c);
                        d = d ? this.parseBindingsString(d, c, b, { valueAccessors: !0 }) : null;
                        return a.g.Rb(d, b, c, !0);
                    },
                     getBindingsString: function (b) {
                        switch (b.nodeType) {
                            case 1:
                                return b.getAttribute("data-bind");
                            case 8:
                                return a.f.vd(b);
                            default:
                                return null
                        }
                    }, parseBindingsString: function (b, c, d, e) {
                        try {
                            var f = this.Kc, g = b + (e && e.valueAccessors || ""), h;
                            if (!(h = f[g])) {
                                var l, m = "with($context){with($data||{}){return{" + a.h.Xa(b, e) + "}}}";
                                l = new Function("$context", "$element", m);
                                h = f[g] = l
                            }
                            return h(c, d)
                        } catch (k) {
                            throw k.message = "Unable to parse bindings.\nBindings value: " +
                                    b + "\nMessage: " + k.message, k;
                        }
                    }});
                a.S.instance = new a.S
            })();
            a.b("bindingProvider", a.S);
            (function () {
                function b(a) {
                    return function () {
                        return a
                    }
                }
                function c(a) {
                    return a()
                }
                function d(b) {
                    return a.a.Ea(a.l.w(b), function (a, c) {
                        return function () {
                            return b()[c]
                        }
                    })
                }
                function e(c, e, k) {
                    return"function" === typeof c ? d(c.bind(null, e, k)) : a.a.Ea(c, b)
                }
                function f(a, b) {
                    return d(this.getBindings.bind(this, a, b))
                }
                function g(b, c, d) {
                    var e, k = a.f.firstChild(c), f = a.S.instance, m = f.preprocessNode;
                    if (m) {
                        for (; e = k; )
                            k = a.f.nextSibling(e),
                                    m.call(f, e);
                        k = a.f.firstChild(c)
                    }
                    for (; e = k; )
                        k = a.f.nextSibling(e), h(b, e, d)
                }
                function h(b, c, d) {
                    var e = !0, k = 1 === c.nodeType;
                    k && a.f.oc(c);
                    if (k && d || a.S.instance.nodeHasBindings(c))
                        e = m(c, null, b, d).shouldBindDescendants;
                    e && !r[a.a.A(c)] && g(b, c, !k)
                }
                function l(b) {
                    var c = [], d = {}, e = [];
                    a.a.D(b, function X(k) {
                        if (!d[k]) {
                            var f = a.getBindingHandler(k);
                            f && (f.after && (e.push(k), a.a.r(f.after, function (c) {
                                if (b[c]) {
                                    if (-1 !== a.a.o(e, c))
                                        throw Error("Cannot combine the following bindings, because they have a cyclic dependency: " + e.join(", "));
                                    X(c)
                                }
                            }), e.length--), c.push({key: k, jc: f}));
                            d[k] = !0
                        }
                    });
                    return c
                }
                function m(b, d, e, k) {
                    var m = a.a.e.get(b, q);
                    if (!d) {
                        if (m)
                            throw Error("You cannot apply bindings multiple times to the same element.");
                        a.a.e.set(b, q, !0)
                    }
                    !m && k && a.xc(b, e);
                    var g;
                    if (d && "function" !== typeof d)
                        g = d;
                    else {
                        var h = a.S.instance, r = h.getBindingAccessors || f, p = a.B(function () {
                            (g = d ? d(e, b) : r.call(h, b, e)) && e.Q && e.Q();
                            return g
                        }, null, {i: b});
                        g && p.ca() || (p = null)
                    }
                    var s;
                    if (g) {
                        var t = p ? function (a) {
                            return function () {
                                return c(p()[a])
                            }
                        } : function (a) {
                            return g[a]
                        },
                                u = function () {
                                    return a.a.Ea(p ? p() : g, c)
                                };
                        u.get = function (a) {
                            return g[a] && c(t(a))
                        };
                        u.has = function (a) {
                            return a in g
                        };
                        k = l(g);
                        a.a.r(k, function (c) {
                            var d = c.jc.init, k = c.jc.update, f = c.key;
                            if (8 === b.nodeType && !a.f.aa[f])
                                throw Error("The binding '" + f + "' cannot be used with virtual elements");
                            try {
                                "function" == typeof d && a.l.w(function () {
                                    var a = d(b, t(f), u, e.$data, e);
                                    if (a && a.controlsDescendantBindings) {
                                        if (s !== n)
                                            throw Error("Multiple bindings (" + s + " and " + f + ") are trying to control descendant bindings of the same element. You cannot use these bindings together on the same element.");
                                        s = f
                                    }
                                }), "function" == typeof k && a.B(function () {
                                    k(b, t(f), u, e.$data, e)
                                }, null, {i: b})
                            } catch (m) {
                                throw m.message = 'Unable to process binding "' + f + ": " + g[f] + '"\nMessage: ' + m.message, m;
                            }
                        })
                    }
                    return{shouldBindDescendants: s === n}
                }
                function k(b) {
                    return b && b instanceof a.R ? b : new a.R(b)
                }
                a.d = {};
                var r = {script: !0, textarea: !0, template: !0};
                a.getBindingHandler = function (b) {
                    return a.d[b]
                };
                a.R = function (b, c, d, e, k) {
                    function f() {
                        var k = g ? b() : b, m = a.a.c(k);
                        c ? (c.Q && c.Q(), a.a.extend(l, c), l.Q = r) : (l.$parents = [], l.$root = m, l.ko = a);
                        l.$rawData =
                                k;
                        l.$data = m;
                        d && (l[d] = m);
                        e && e(l, c, m);
                        return l.$data
                    }
                    function m() {
                        return h && !a.a.Tb(h)
                    }
                    var l = this, g = "function" == typeof b && !a.I(b), h, r;
                    k && k.exportDependencies ? f() : (r = a.B(f, null, {ya: m, i: !0}), r.ca() && (l.Q = r, r.equalityComparer = null, h = [], r.Dc = function (b) {
                        h.push(b);
                        a.a.G.qa(b, function (b) {
                            a.a.Na(h, b);
                            h.length || (r.k(), l.Q = r = n)
                        })
                    }))
                };
                a.R.prototype.createChildContext = function (b, c, d, e) {
                    return new a.R(b, this, c, function (a, b) {
                        a.$parentContext = b;
                        a.$parent = b.$data;
                        a.$parents = (b.$parents || []).slice(0);
                        a.$parents.unshift(a.$parent);
                        d && d(a)
                    }, e)
                };
                a.R.prototype.extend = function (b) {
                    return new a.R(this.Q || this.$data, this, null, function (c, d) {
                        c.$rawData = d.$rawData;
                        a.a.extend(c, "function" == typeof b ? b() : b)
                    })
                };
                a.R.prototype.ac = function (a, b) {
                    return this.createChildContext(a, b, null, {exportDependencies: !0})
                };
                var q = a.a.e.J(), p = a.a.e.J();
                a.xc = function (b, c) {
                    if (2 == arguments.length)
                        a.a.e.set(b, p, c), c.Q && c.Q.Dc(b);
                    else
                        return a.a.e.get(b, p)
                };
                a.La = function (b, c, d) {
                    1 === b.nodeType && a.f.oc(b);
                    return m(b, c, k(d), !0)
                };
                a.Ic = function (b, c, d) {
                    d = k(d);
                    return a.La(b,
                            e(c, d, b), d)
                };
                a.hb = function (a, b) {
                    1 !== b.nodeType && 8 !== b.nodeType || g(k(a), b, !0)
                };
                a.Ub = function (a, b) {
                    !u && x.jQuery && (u = x.jQuery);
                    if (b && 1 !== b.nodeType && 8 !== b.nodeType)
                        throw Error("ko.applyBindings: first parameter should be your view model; second parameter should be a DOM node");
                    b = b || x.document.body;
                    h(k(a), b, !0)
                };
                a.nb = function (b) {
                    switch (b.nodeType) {
                        case 1:
                        case 8:
                            var c = a.xc(b);
                            if (c)
                                return c;
                            if (b.parentNode)
                                return a.nb(b.parentNode)
                    }
                    return n
                };
                a.Oc = function (b) {
                    return(b = a.nb(b)) ? b.$data : n
                };
                a.b("bindingHandlers",
                        a.d);
                a.b("applyBindings", a.Ub);
                a.b("applyBindingsToDescendants", a.hb);
                a.b("applyBindingAccessorsToNode", a.La);
                a.b("applyBindingsToNode", a.Ic);
                a.b("contextFor", a.nb);
                a.b("dataFor", a.Oc)
            })();
            (function (b) {
                function c(c, e) {
                    var m = f.hasOwnProperty(c) ? f[c] : b, k;
                    m ? m.Y(e) : (m = f[c] = new a.K, m.Y(e), d(c, function (b, d) {
                        var e = !(!d || !d.synchronous);
                        g[c] = {definition: b, dd: e};
                        delete f[c];
                        k || e ? m.notifySubscribers(b) : a.Z.Za(function () {
                            m.notifySubscribers(b)
                        })
                    }), k = !0)
                }
                function d(a, b) {
                    e("getConfig", [a], function (c) {
                        c ? e("loadComponent",
                                [a, c], function (a) {
                            b(a, c)
                        }) : b(null, null)
                    })
                }
                function e(c, d, f, k) {
                    k || (k = a.g.loaders.slice(0));
                    var g = k.shift();
                    if (g) {
                        var q = g[c];
                        if (q) {
                            var p = !1;
                            if (q.apply(g, d.concat(function (a) {
                                p ? f(null) : null !== a ? f(a) : e(c, d, f, k)
                            })) !== b && (p = !0, !g.suppressLoaderExceptions))
                                throw Error("Component loaders must supply values by invoking the callback, not by returning values synchronously.");
                        } else
                            e(c, d, f, k)
                    } else
                        f(null)
                }
                var f = {}, g = {};
                a.g = {get: function (d, e) {
                        var f = g.hasOwnProperty(d) ? g[d] : b;
                        f ? f.dd ? a.l.w(function () {
                            e(f.definition)
                        }) :
                                a.Z.Za(function () {
                                    e(f.definition)
                                }) : c(d, e)
                    }, $b: function (a) {
                        delete g[a]
                    }, Nb: e};
                a.g.loaders = [];
                a.b("components", a.g);
                a.b("components.get", a.g.get);
                a.b("components.clearCachedDefinition", a.g.$b)
            })();
            (function () {
                function b(b, c, d, e) {
                    function g() {
                        0 === --y && e(h)
                    }
                    var h = {}, y = 2, v = d.template;
                    d = d.viewModel;
                    v ? f(c, v, function (c) {
                        a.g.Nb("loadTemplate", [b, c], function (a) {
                            h.template = a;
                            g()
                        })
                    }) : g();
                    d ? f(c, d, function (c) {
                        a.g.Nb("loadViewModel", [b, c], function (a) {
                            h[l] = a;
                            g()
                        })
                    }) : g()
                }
                function c(a, b, d) {
                    if ("function" === typeof b)
                        d(function (a) {
                            return new b(a)
                        });
                    else if ("function" === typeof b[l])
                        d(b[l]);
                    else if ("instance"in b) {
                        var e = b.instance;
                        d(function () {
                            return e
                        })
                    } else
                        "viewModel"in b ? c(a, b.viewModel, d) : a("Unknown viewModel value: " + b)
                }
                function d(b) {
                    switch (a.a.A(b)) {
                        case "script":
                            return a.a.na(b.text);
                        case "textarea":
                            return a.a.na(b.value);
                        case "template":
                            if (e(b.content))
                                return a.a.wa(b.content.childNodes)
                    }
                    return a.a.wa(b.childNodes)
                }
                function e(a) {
                    return x.DocumentFragment ? a instanceof DocumentFragment : a && 11 === a.nodeType
                }
                function f(a, b, c) {
                    "string" === typeof b.require ?
                            O || x.require ? (O || x.require)([b.require], c) : a("Uses require, but no AMD loader is present") : c(b)
                }
                function g(a) {
                    return function (b) {
                        throw Error("Component '" + a + "': " + b);
                    }
                }
                var h = {};
                a.g.register = function (b, c) {
                    if (!c)
                        throw Error("Invalid configuration for " + b);
                    if (a.g.wb(b))
                        throw Error("Component " + b + " is already registered");
                    h[b] = c
                };
                a.g.wb = function (a) {
                    return h.hasOwnProperty(a)
                };
                a.g.ud = function (b) {
                    delete h[b];
                    a.g.$b(b)
                };
                a.g.cc = {getConfig: function (a, b) {
                        b(h.hasOwnProperty(a) ? h[a] : null)
                    }, loadComponent: function (a,
                            c, d) {
                        var e = g(a);
                        f(e, c, function (c) {
                            b(a, e, c, d)
                        })
                    }, loadTemplate: function (b, c, f) {
                        b = g(b);
                        if ("string" === typeof c)
                            f(a.a.na(c));
                        else if (c instanceof Array)
                            f(c);
                        else if (e(c))
                            f(a.a.W(c.childNodes));
                        else if (c.element)
                            if (c = c.element, x.HTMLElement ? c instanceof HTMLElement : c && c.tagName && 1 === c.nodeType)
                                f(d(c));
                            else if ("string" === typeof c) {
                                var l = t.getElementById(c);
                                l ? f(d(l)) : b("Cannot find element with ID " + c)
                            } else
                                b("Unknown element type: " + c);
                        else
                            b("Unknown template value: " + c)
                    }, loadViewModel: function (a, b, d) {
                        c(g(a),
                                b, d)
                    }};
                var l = "createViewModel";
                a.b("components.register", a.g.register);
                a.b("components.isRegistered", a.g.wb);
                a.b("components.unregister", a.g.ud);
                a.b("components.defaultLoader", a.g.cc);
                a.g.loaders.push(a.g.cc);
                a.g.Ec = h
            })();
            (function () {
                function b(b, e) {
                    var f = b.getAttribute("params");
                    if (f) {
                        f = c.parseBindingsString(f, e, b, {valueAccessors: !0, bindingParams: !0});
                        f = a.a.Ea(f, function (c) {
                            return a.m(c, null, {i: b})
                        });
                        var g = a.a.Ea(f, function (c) {
                            var e = c.p();
                            return c.ca() ? a.m({
                                read: function () {
                                    return a.a.c(c())
                                },
                                write: a.Da(e) &&
                                    function (a) {
                                        c()(a)
                                    },
                                i: b
                            }) : e
                        });
                        if (!g.hasOwnProperty("$raw")) {
                            g.$raw = f;
                        }
                        return g;
                    }
                    
                    return{$raw: {}}
                }
                a.g.getComponentNameForNode = function (b) {
                    var c = a.a.A(b);
                    if (a.g.wb(c) && (-1 != c.indexOf("-") || "[object HTMLUnknownElement]" == "" + b || 8 >= a.a.C && b.tagName === c))
                        return c
                };
                a.g.Rb = function (c, e, f, g) {
                    if (1 === e.nodeType) {
                        var h = a.g.getComponentNameForNode(e);
                        if (h) {
                            c = c || {};
                            if (c.component)
                                throw Error('Cannot use the "component" binding on a custom element matching a component');
                            var l = {name: h, params: b(e, f)};
                            c.component = g ? function () {
                                return l
                            } :
                                    l
                        }
                    }
                    return c
                };
                var c = new a.S;
                9 > a.a.C && (a.g.register = function (a) {
                    return function (b) {
                        t.createElement(b);
                        return a.apply(this, arguments)
                    }
                }(a.g.register), t.createDocumentFragment = function (b) {
                    return function () {
                        var c = b(), f = a.g.Ec, g;
                        for (g in f)
                            f.hasOwnProperty(g) && c.createElement(g);
                        return c
                    }
                }(t.createDocumentFragment))
            })();
            (function (b) {
                function c(b, c, d) {
                    c = c.template;
                    if (!c)
                        throw Error("Component '" + b + "' has no template");
                    b = a.a.wa(c);
                    a.f.fa(d, b)
                }
                function d(a, b, c, d) {
                    var e = a.createViewModel;
                    return e ? e.call(a,
                            d, {element: b, templateNodes: c}) : d
                }
                var e = 0;
                a.d.component = {init: function (f, g, h, l, m) {
                        function k() {
                            var a = r && r.dispose;
                            "function" === typeof a && a.call(r);
                            q = r = null
                        }
                        var r, q, p = a.a.W(a.f.childNodes(f));
                        a.a.G.qa(f, k);
                        a.m(function () {
                            var l = a.a.c(g()), h, v;
                            "string" === typeof l ? h = l : (h = a.a.c(l.name), v = a.a.c(l.params));
                            if (!h)
                                throw Error("No component name specified");
                            var n = q = ++e;
                            a.g.get(h, function (e) {
                                if (q === n) {
                                    k();
                                    if (!e)
                                        throw Error("Unknown component '" + h + "'");
                                    c(h, e, f);
                                    var l = d(e, f, p, v);
                                    e = m.createChildContext(l, b, function (a) {
                                        a.$component =
                                                l;
                                        a.$componentTemplateNodes = p
                                    });
                                    r = l;
                                    a.hb(e, f)
                                }
                            })
                        }, null, {i: f});
                        return{controlsDescendantBindings: !0}
                    }};
                a.f.aa.component = !0
            })();
            var Q = {"class": "className", "for": "htmlFor"};
            a.d.attr = {update: function (b, c) {
                    var d = a.a.c(c()) || {};
                    a.a.D(d, function (c, d) {
                        d = a.a.c(d);
                        var g = (!(1 === d) || (null === d) || (d === n));


                        g && b.removeAttribute(c);
                        8 >= a.a.C && c in Q ? (c = Q[c], g ? b.removeAttribute(c) : b[c] = d) : g || b.setAttribute(c, d.toString());
                        "name" === c && a.a.vc(b, g ? "" : d.toString())
                    })
                }};
            (function () {
                a.d.checked = {after: ["value", "attr"], init: function (b,
                            c, d) {
                        function e() {
                            var e = b.checked, f = p ? g() : e;
                            if (!a.xa.Va() && (!l || e)) {
                                var h = a.l.w(c);
                                if (k) {
                                    var m = r ? h.p() : h;
                                    q !== f ? (e && (a.a.ra(m, f, !0), a.a.ra(m, q, !1)), q = f) : a.a.ra(m, f, e);
                                    r && a.Da(h) && h(m)
                                } else
                                    a.h.Ga(h, d, "checked", f, !0)
                            }
                        }
                        function f() {
                            var d = a.a.c(c());
                            b.checked = k ? 0 <= a.a.o(d, g()) : h ? d : g() === d
                        }
                        var g = a.rc(function () {
                            return d.has("checkedValue") ? a.a.c(d.get("checkedValue")) : d.has("value") ? a.a.c(d.get("value")) : b.value
                        }), h = "checkbox" == b.type, l = "radio" == b.type;
                        if (h || l) {
                            var m = c(), k = h && a.a.c(m)instanceof Array,
                                    r = !(k && m.push && m.splice), q = k ? g() : n, p = l || k;
                            l && !b.name && a.d.uniqueName.init(b, function () {
                                return!0
                            });
                            a.m(e, null, {i: b});
                            a.a.q(b, "click", e);
                            a.m(f, null, {i: b});
                            m = n
                        }
                    }};
                a.h.ga.checked = !0;
                a.d.checkedValue = {update: function (b, c) {
                        b.value = a.a.c(c())
                    }}
            })();
            a.d.css = {update: function (b, c) {
                    var d = a.a.c(c());
                    null !== d && "object" == typeof d ? a.a.D(d, function (c, d) {
                        d = a.a.c(d);
                        a.a.fb(b, c, d)
                    }) : (d = a.a.cb(String(d || "")), a.a.fb(b, b.__ko__cssValue, !1), b.__ko__cssValue = d, a.a.fb(b, d, !0))
                }};
            a.d.enable = {update: function (b, c) {
                    var d = a.a.c(c());
                    d && b.disabled ? b.removeAttribute("disabled") : d || b.disabled || (b.disabled = !0)
                }};
            a.d.disable = {update: function (b, c) {
                    a.d.enable.update(b, function () {
                        return!a.a.c(c())
                    })
                }};
            a.d.event = {init: function (b, c, d, e, f) {
                    var g = c() || {};
                    a.a.D(g, function (g) {
                        "string" == typeof g && a.a.q(b, g, function (b) {
                            var m, k = c()[g];
                            if (k) {
                                try {
                                    var r = a.a.W(arguments);
                                    e = f.$data;
                                    r.unshift(e);
                                    m = k.apply(e, r)
                                }finally {
    (!(0 !== m)) && (b.preventDefault ? b.preventDefault() : b.returnValue = !1);
}

                                if (1 === d.get(g + "Bubble")) {
                                    b.cancelBubble = !0;
                                    b.stopPropagation && b.stopPropagation();
                                }
                                
                            }
                        })
                    })
                }};
            a.d.foreach = {mc: function (b) {
                    return function () {
                        var c = b(), d = a.a.Bb(c);
                        if (!d || "number" == typeof d.length)
                            return{foreach: c, templateEngine: a.X.vb};
                        a.a.c(c);
                        return{foreach: d.data, as: d.as, includeDestroyed: d.includeDestroyed, afterAdd: d.afterAdd, beforeRemove: d.beforeRemove, afterRender: d.afterRender, beforeMove: d.beforeMove, afterMove: d.afterMove, templateEngine: a.X.vb}
                    }
                }, init: function (b, c) {
                    return a.d.template.init(b, a.d.foreach.mc(c))
                }, update: function (b, c, d, e, f) {
                    return a.d.template.update(b, a.d.foreach.mc(c),
                            d, e, f)
                }};
            a.h.va.foreach = !1;
            a.f.aa.foreach = !0;
            a.d.hasfocus = {init: function (b, c, d) {
                    function e(e) {
                        b.__ko_hasfocusUpdating = !0;
                        var f = b.ownerDocument;
                        if ("activeElement"in f) {
                            var g;
                            try {
                                g = f.activeElement
                            } catch (k) {
                                g = f.body
                            }
                            e = g === b
                        }
                        f = c();
                        a.h.Ga(f, d, "hasfocus", e, !0);
                        b.__ko_hasfocusLastValue = e;
                        b.__ko_hasfocusUpdating = !1
                    }
                    var f = e.bind(null, !0), g = e.bind(null, !1);
                    a.a.q(b, "focus", f);
                    a.a.q(b, "focusin", f);
                    a.a.q(b, "blur", g);
                    a.a.q(b, "focusout", g)
                }, update: function (b, c) {
                    var d = !!a.a.c(c());
                    b.__ko_hasfocusUpdating || b.__ko_hasfocusLastValue ===
                            d || (d ? b.focus() : b.blur(), !d && b.__ko_hasfocusLastValue && b.ownerDocument.body.focus(), a.l.w(a.a.Fa, null, [b, d ? "focusin" : "focusout"]))
                }};
            a.h.ga.hasfocus = !0;
            a.d.hasFocus = a.d.hasfocus;
            a.h.ga.hasFocus = !0;
            a.d.html = {init: function () {
                    return{controlsDescendantBindings: !0}
                }, update: function (b, c) {
                    a.a.Eb(b, c())
                }};
            K("if");
            K("ifnot", !1, !0);
            K("with", !0, !1, function (a, c) {
                return a.ac(c)
            });
            var L = {};
            a.d.options = {init: function (b) {
                    if ("select" !== a.a.A(b))
                        throw Error("options binding applies only to SELECT elements");
                    for (; 0 <
                            b.length; )
                        b.remove(0);
                    return{controlsDescendantBindings: !0}
                }, update: function (b, c, d) {
                    function e() {
                        return a.a.Ma(b.options, function (a) {
                            return a.selected
                        })
                    }
                    function f(a, b, c) {
                        var d = typeof b;
                        return"function" == d ? b(a) : "string" == d ? a[b] : c
                    }
                    function g(c, e) {
                        if (A && k)
                            a.j.ja(b, a.a.c(d.get("value")), !0);
                        else if (p.length) {
                            var f = 0 <= a.a.o(p, a.j.u(e[0]));
                            a.a.wc(e[0], f);
                            A && !f && a.l.w(a.a.Fa, null, [b, "change"])
                        }
                    }
                    var h = b.multiple, l = 0 != b.length && h ? b.scrollTop : null, m = a.a.c(c()), k = d.get("valueAllowUnset") && d.has("value"), r =
                            d.get("optionsIncludeDestroyed");
                    c = {};
                    var q, p = [];
                    k || (h ? p = a.a.ib(e(), a.j.u) : 0 <= b.selectedIndex && p.push(a.j.u(b.options[b.selectedIndex])));
                    m && ("undefined" == typeof m.length && (m = [m]), q = a.a.Ma(m, function (b) {
                        return r || b === n || null === b || !a.a.c(b._destroy)
                    }), d.has("optionsCaption") && (m = a.a.c(d.get("optionsCaption")), null !== m && m !== n && q.unshift(L)));
                    var A = !1;
                    c.beforeRemove = function (a) {
                        b.removeChild(a)
                    };
                    m = g;
                    d.has("optionsAfterRender") && "function" == typeof d.get("optionsAfterRender") && (m = function (b, c) {
                        g(0, c);
                        a.l.w(d.get("optionsAfterRender"), null, [c[0], b !== L ? b : n])
                    });
                    a.a.Db(b, q, function (c, e, g) {
                        g.length && (p = !k && g[0].selected ? [a.j.u(g[0])] : [], A = !0);
                        e = b.ownerDocument.createElement("option");
                        c === L ? (a.a.bb(e, d.get("optionsCaption")), a.j.ja(e, n)) : (g = f(c, d.get("optionsValue"), c), a.j.ja(e, a.a.c(g)), c = f(c, d.get("optionsText"), g), a.a.bb(e, c));
                        return[e]
                    }, c, m);
                    a.l.w(function () {
                        k ? a.j.ja(b, a.a.c(d.get("value")), !0) : (h ? p.length && e().length < p.length : p.length && 0 <= b.selectedIndex ? a.j.u(b.options[b.selectedIndex]) !== p[0] :
                                p.length || 0 <= b.selectedIndex) && a.a.Fa(b, "change")
                    });
                    a.a.Sc(b);
                    l && 20 < Math.abs(l - b.scrollTop) && (b.scrollTop = l)
                }};
            a.d.options.zb = a.a.e.J();
            a.d.selectedOptions = {after: ["options", "foreach"], init: function (b, c, d) {
                    a.a.q(b, "change", function () {
                        var e = c(), f = [];
                        a.a.r(b.getElementsByTagName("option"), function (b) {
                            b.selected && f.push(a.j.u(b))
                        });
                        a.h.Ga(e, d, "selectedOptions", f)
                    })
                }, update: function (b, c) {
                    if ("select" != a.a.A(b))
                        throw Error("values binding applies only to SELECT elements");
                    var d = a.a.c(c()), e = b.scrollTop;
                    d && "number" == typeof d.length && a.a.r(b.getElementsByTagName("option"), function (b) {
                        var c = 0 <= a.a.o(d, a.j.u(b));
                        b.selected != c && a.a.wc(b, c)
                    });
                    b.scrollTop = e
                }};
            a.h.ga.selectedOptions = !0;
            a.d.style = {update: function (b, c) {
                    var d = a.a.c(c() || {});
                    a.a.D(d, function (c, d) {
                        d = a.a.c(d);
                        if ((null === d) || (d === n) || !(1 === d))

                            d = "";
                        b.style[c] = d
                    })
                }};
            a.d.submit = {init: function (b, c, d, e, f) {
                    if ("function" != typeof c())
                        throw Error("The value for a submit binding must be a function");
                    a.a.q(b, "submit", function (a) {
                        var d, e = c();
                        try {
                            d = e.call(f.$data,
                                    b)
                                } finally {
                                    (false !== d) && (a.preventDefault ? a.preventDefault() : a.returnValue = false);
                                }
                                
                    })
                }};
            a.d.text = {init: function () {
                    return{controlsDescendantBindings: !0}
                }, update: function (b, c) {
                    a.a.bb(b, c())
                }};
            a.f.aa.text = !0;
            (function () {
                if (x && x.navigator)
                    var b = function (a) {
                        if (a)
                            return parseFloat(a[1])
                    }, c = x.opera && x.opera.version && parseInt(x.opera.version()), d = x.navigator.userAgent, e = b(d.match(/^(?:(?!chrome).)*version\/([^ ]*) safari/i)), f = b(d.match(/Firefox\/([^ ]*)/));
                if (10 > a.a.C)
                    var g = a.a.e.J(), h = a.a.e.J(), l = function (b) {
                        var c =
                                this.activeElement;
                        (c = c && a.a.e.get(c, h)) && c(b)
                    }, m = function (b, c) {
                        var d = b.ownerDocument;
                        a.a.e.get(d, g) || (a.a.e.set(d, g, !0), a.a.q(d, "selectionchange", l));
                        a.a.e.set(b, h, c)
                    };
                a.d.textInput = {init: function (b, d, g) {
                        function l(c, d) {
                            a.a.q(b, c, d)
                        }
                        function h() {
                            var c = a.a.c(d());
                            if (null === c || c === n)
                                c = "";
                            u !== n && c === u ? a.a.setTimeout(h, 4) : b.value !== c && (s = c, b.value = c)
                        }
                        function y() {
                            t || (u = b.value, t = a.a.setTimeout(v, 4))
                        }
                        function v() {
                            clearTimeout(t);
                            u = t = n;
                            var c = b.value;
                            s !== c && (s = c, a.h.Ga(d(), g, "textInput", c))
                        }
                        var s = b.value,
                                t, u, x = 9 == a.a.C ? y : v;
                        10 > a.a.C ? (l("propertychange", function (a) {
                            "value" === a.propertyName && x(a)
                        }), 8 == a.a.C && (l("keyup", v), l("keydown", v)), 8 <= a.a.C && (m(b, x), l("dragend", y))) : (l("input", v), 5 > e && "textarea" === a.a.A(b) ? (l("keydown", y), l("paste", y), l("cut", y)) : 11 > c ? l("keydown", y) : 4 > f && (l("DOMAutoComplete", v), l("dragdrop", v), l("drop", v)));
                        l("change", v);
                        a.m(h, null, {i: b})
                    }};
                a.h.ga.textInput = !0;
                a.d.textinput = {preprocess: function (a, b, c) {
                        c("textInput", a)
                    }}
            })();
            a.d.uniqueName = {init: function (b, c) {
                    if (c()) {
                        var d = "ko_unique_" +
                                ++a.d.uniqueName.Nc;
                        a.a.vc(b, d)
                    }
                }};
            a.d.uniqueName.Nc = 0;
            a.d.value = {after: ["options", "foreach"], init: function (b, c, d) {
                    if ("input" != b.tagName.toLowerCase() || "checkbox" != b.type && "radio" != b.type) {
                        var e = ["change"], f = d.get("valueUpdate"), g = !1, h = null;
                        f && ("string" == typeof f && (f = [f]), a.a.ta(e, f), e = a.a.Wb(e));
                        var l = function () {
                            h = null;
                            g = !1;
                            var e = c(), f = a.j.u(b);
                            a.h.Ga(e, d, "value", f)
                        };
                        !a.a.C || "input" != b.tagName.toLowerCase() || "text" != b.type || "off" == b.autocomplete || b.form && "off" == b.form.autocomplete || -1 != a.a.o(e, "propertychange") ||
                                (a.a.q(b, "propertychange", function () {
                                    g = !0
                                }), a.a.q(b, "focus", function () {
                                    g = !1
                                }), a.a.q(b, "blur", function () {
                                    g && l()
                                }));
                        a.a.r(e, function (c) {
                            var d = l;
                            a.a.sd(c, "after") && (d = function () {
                                h = a.j.u(b);
                                a.a.setTimeout(l, 0)
                            }, c = c.substring(5));
                            a.a.q(b, c, d)
                        });
                        var m = function () {
                            var e = a.a.c(c()), f = a.j.u(b);
                            if (null !== h && e === h)
                                a.a.setTimeout(m, 0);
                            else if (e !== f)
                                if ("select" === a.a.A(b)) {
                                    var g = d.get("valueAllowUnset"), f = function () {
                                        a.j.ja(b, e, g)
                                    };
                                    f();
                                    g || e === a.j.u(b) ? a.a.setTimeout(f, 0) : a.l.w(a.a.Fa, null, [b, "change"])
                                } else
                                    a.j.ja(b,
                                            e)
                        };
                        a.m(m, null, {i: b})
                    } else
                        a.La(b, {checkedValue: c})
                }, update: function () {}};
            a.h.ga.value = !0;
            a.d.visible = {update: function (b, c) {
                    var d = a.a.c(c()), e = "none" != b.style.display;
                    d && !e ? b.style.display = "" : !d && e && (b.style.display = "none")
                }};
            (function (b) {
                a.d[b] = {init: function (c, d, e, f, g) {
                        return a.d.event.init.call(this, c, function () {
                            var a = {};
                            a[b] = d();
                            return a
                        }, e, f, g)
                    }}
            })("click");
            a.P = function () {};
            a.P.prototype.renderTemplateSource = function () {
                throw Error("Override renderTemplateSource");
            };
            a.P.prototype.createJavaScriptEvaluatorBlock =
                    function () {
                        throw Error("Override createJavaScriptEvaluatorBlock");
                    };
            a.P.prototype.makeTemplateSource = function (b, c) {
                if ("string" == typeof b) {
                    c = c || t;
                    var d = c.getElementById(b);
                    if (!d)
                        throw Error("Cannot find template with ID " + b);
                    return new a.v.n(d)
                }
                if (1 == b.nodeType || 8 == b.nodeType)
                    return new a.v.sa(b);
                throw Error("Unknown template type: " + b);
            };
            a.P.prototype.renderTemplate = function (a, c, d, e) {
                a = this.makeTemplateSource(a, e);
                return this.renderTemplateSource(a, c, d, e)
            };
            a.P.prototype.isTemplateRewritten = function (a,
                    c) {
                        return (1 === this.allowTemplateRewriting) ? !0 : this.makeTemplateSource(a, c).data("isRewritten");

            };
            a.P.prototype.rewriteTemplate = function (a, c, d) {
                a = this.makeTemplateSource(a, d);
                c = c(a.text());
                a.text(c);
                a.data("isRewritten", !0)
            };
            a.b("templateEngine", a.P);
            a.Ib = function () {
                function b(b, c, d, h) {
                    b = a.h.Ab(b);
                    for (var l = a.h.va, m = 0; m < b.length; m++) {
                        var k = b[m].key;
                        if (l.hasOwnProperty(k)) {
                            var r = l[k];
                            if ("function" === typeof r) {
                                if (k = r(b[m].value))
                                    throw Error(k);
                            } else if (!r)
                                throw Error("This template engine does not support the '" +
                                        k + "' binding within its templates");
                        }
                    }
                    d = "ko.__tr_ambtns(function($context,$element){return(function(){return{ " + a.h.Xa(b, {valueAccessors: !0}) + " } })()},'" + d.toLowerCase() + "')";
                    return h.createJavaScriptEvaluatorBlock(d) + c
                }
                var c = /(<([a-z]+\d*)(?:\s+(?!data-bind\s*=\s*)[a-z0-9\-]+(?:=(?:\"[^\"]*\"|\'[^\']*\'|[^>]*))?)*\s+)data-bind\s*=\s*(["'])([\s\S]*?)\3/gi, d = /\x3c!--\s*ko\b\s*([\s\S]*?)\s*--\x3e/g;
                return{Tc: function (b, c, d) {
                        c.isTemplateRewritten(b, d) || c.rewriteTemplate(b, function (b) {
                            return a.Ib.jd(b,
                                    c)
                        }, d)
                    }, jd: function (a, f) {
                        return a.replace(c, function (a, c, d, e, k) {
                            return b(k, c, d, f)
                        }).replace(d, function (a, c) {
                            return b(c, "\x3c!-- ko --\x3e", "#comment", f)
                        })
                    }, Jc: function (b, c) {
                        return a.N.yb(function (d, h) {
                            var l = d.nextSibling;
                            l && l.nodeName.toLowerCase() === c && a.La(l, b, h)
                        })
                    }}
            }();
            a.b("__tr_ambtns", a.Ib.Jc);
            (function () {
                a.v = {};
                a.v.n = function (b) {
                    if (this.n = b) {
                        var c = a.a.A(b);
                        this.eb = "script" === c ? 1 : "textarea" === c ? 2 : "template" == c && b.content && 11 === b.content.nodeType ? 3 : 4
                    }
                };
                a.v.n.prototype.text = function () {
                    var b = 1 ===
                            this.eb ? "text" : 2 === this.eb ? "value" : "innerHTML";
                    if (0 == arguments.length)
                        return this.n[b];
                    var c = arguments[0];
                    "innerHTML" === b ? a.a.Eb(this.n, c) : this.n[b] = c
                };
                var b = a.a.e.J() + "_";
                a.v.n.prototype.data = function (c) {
                    if (1 === arguments.length)
                        return a.a.e.get(this.n, b + c);
                    a.a.e.set(this.n, b + c, arguments[1])
                };
                var c = a.a.e.J();
                a.v.n.prototype.nodes = function () {
                    var b = this.n;
                    if (0 == arguments.length)
                        return(a.a.e.get(b, c) || {}).mb || (3 === this.eb ? b.content : 4 === this.eb ? b : n);
                    a.a.e.set(b, c, {mb: arguments[0]})
                };
                a.v.sa = function (a) {
                    this.n =
                            a
                };
                a.v.sa.prototype = new a.v.n;
                a.v.sa.prototype.text = function () {
                    if (0 == arguments.length) {
                        var b = a.a.e.get(this.n, c) || {};
                        b.Jb === n && b.mb && (b.Jb = b.mb.innerHTML);
                        return b.Jb
                    }
                    a.a.e.set(this.n, c, {Jb: arguments[0]})
                };
                a.b("templateSources", a.v);
                a.b("templateSources.domElement", a.v.n);
                a.b("templateSources.anonymousTemplate", a.v.sa)
            })();
            (function () {
                function b(b, c, d) {
                    var e;
                    for (c = a.f.nextSibling(c); b && (e = b) !== c; )
                        b = a.f.nextSibling(e), d(e, b)
                }
                function c(c, d) {
                    if (c.length) {
                        var e = c[0], f = c[c.length - 1], g = e.parentNode, h =
                                a.S.instance, n = h.preprocessNode;
                        if (n) {
                            b(e, f, function (a, b) {
                                var c = a.previousSibling, d = n.call(h, a);
                                d && (a === e && (e = d[0] || b), a === f && (f = d[d.length - 1] || c))
                            });
                            c.length = 0;
                            if (!e)
                                return;
                            e === f ? c.push(e) : (c.push(e, f), a.a.Ba(c, g))
                        }
                        b(e, f, function (b) {
                            1 !== b.nodeType && 8 !== b.nodeType || a.Ub(d, b)
                        });
                        b(e, f, function (b) {
                            1 !== b.nodeType && 8 !== b.nodeType || a.N.Cc(b, [d])
                        });
                        a.a.Ba(c, g)
                    }
                }
                function d(a) {
                    return a.nodeType ? a : 0 < a.length ? a[0] : null
                }
                function e(b, e, f, h, q) {
                    q = q || {};
                    var p = (b && d(b) || f || {}).ownerDocument, n = q.templateEngine || g;
                    a.Ib.Tc(f, n, p);
                    f = n.renderTemplate(f, h, q, p);
                    if ("number" != typeof f.length || 0 < f.length && "number" != typeof f[0].nodeType)
                        throw Error("Template engine must return an array of DOM nodes");
                    p = !1;
                    switch (e) {
                        case "replaceChildren":
                            a.f.fa(b, f);
                            p = !0;
                            break;
                        case "replaceNode":
                            a.a.uc(b, f);
                            p = !0;
                            break;
                        case "ignoreTargetNode":
                            break;
                        default:
                            throw Error("Unknown renderMode: " + e);
                    }
                    p && (c(f, h), q.afterRender && a.l.w(q.afterRender, null, [f, h.$data]));
                    return f
                }
                function f(b, c, d) {
                    return a.I(b) ? b() : "function" === typeof b ? b(c, d) : b
                }
                var g;
                a.Fb = function (b) {
                    if (b != n && !(b instanceof a.P))
                        throw Error("templateEngine must inherit from ko.templateEngine");
                    g = b
                };
                a.Cb = function (b, c, k, h, q) {
                    k = k || {};
                    if ((k.templateEngine || g) == n)
                        throw Error("Set a template engine before calling renderTemplate");
                    q = q || "replaceChildren";
                    if (h) {
                        var p = d(h);
                        var g = null;
                        return a.B(function () {
                            var g = c && c instanceof a.R ? c : new a.R(c, null, null, null, {exportDependencies: !0});
                            var n = f(b, g.$data, g);
                            var result = e(h, q, n, g, k);
                            if ("replaceNode" == q) {
                                h = result;
                                p = d(h);
                            }
                        }, null, {
                            ya: function () {
                                return !p || !a.a.qb(p);
                            },
                            i: p && "replaceNode" == q ? p.parentNode : p
                        });
                    }
                    
                    return a.N.yb(function (d) {
                        a.Cb(b, c, k, d, "replaceNode")
                    })
                };
                a.pd = function (b, d, g, h, q) {
                    function p(a, b) {
                        c(b, t);
                        g.afterRender && g.afterRender(b, a);
                        t = null
                    }
                    function s(a, c) {
                        t = q.createChildContext(a, g.as, function (a) {
                            a.$index = c
                        });
                        var d = f(b, a, t);
                        return e(null, "ignoreTargetNode", d, t, g)
                    }
                    var t;
                    return a.B(function () {
                        var b = a.a.c(d) || [];
                        "undefined" == typeof b.length && (b = [b]);
                        b = a.a.Ma(b, function (b) {
                            return g.includeDestroyed || b === n || null === b || !a.a.c(b._destroy)
                        });
                        a.l.w(a.a.Db, null, [h, b,
                            s, g, p])
                    }, null, {i: h})
                };
                var h = a.a.e.J();
                a.d.template = {init: function (b, c) {
                        var d = a.a.c(c());
                        if ("string" == typeof d || d.name)
                            a.f.za(b);
                        else {
                            if ("nodes"in d) {
                                if (d = d.nodes || [], a.I(d))
                                    throw Error('The "nodes" option must be a plain, non-observable array.');
                            } else
                                d = a.f.childNodes(b);
                            d = a.a.nc(d);
                            (new a.v.sa(b)).nodes(d)
                        }
                        return{controlsDescendantBindings: !0}
                    }, update: function (b, c, d, e, f) {
                        var g = c();
                        c = a.a.c(g);
                        d = !0;
                        e = null;
                        "string" == typeof c ? c = {} : (g = c.name, "if"in c && (d = a.a.c(c["if"])), d && "ifnot"in c && (d = !a.a.c(c.ifnot)));
                        "foreach"in c ? e = a.pd(g || b, d && c.foreach || [], c, b, f) : d ? (f = "data"in c ? f.ac(c.data, c.as) : f, e = a.Cb(g || b, f, c, b)) : a.f.za(b);
                        f = e;
                        (c = a.a.e.get(b, h)) && "function" == typeof c.k && c.k();
                        a.a.e.set(b, h, f && f.ca() ? f : n)
                    }};
                a.h.va.template = function (b) {
                    b = a.h.Ab(b);
                    return 1 == b.length && b[0].unknown || a.h.fd(b, "name") ? null : "This template engine does not support anonymous templates nested within its templates"
                };
                a.f.aa.template = !0
            })();
            a.b("setTemplateEngine", a.Fb);
            a.b("renderTemplate", a.Cb);
            a.a.hc = function (a, c, d) {
                if (a.length &&
                        c.length) {
                    var e, f, g, h, l;
                    for (e = f = 0; (!d || e < d) && (h = a[f]); ++f) {
                        for (g = 0; l = c[g]; ++g)
                            if (h.value === l.value) {
                                h.moved = l.index;
                                l.moved = h.index;
                                c.splice(g, 1);
                                e = g = 0;
                                break
                            }
                        e += g
                    }
                }
            };
            a.a.lb = function () {
                function b(b, d, e, f, g) {
                    var h = Math.min, l = Math.max, m = [], k, n = b.length, q, p = d.length, s = p - n || 1, t = n + p + 1, v, u, x;
                    for (k = 0; k <= n; k++)
                        for (u = v, m.push(v = []), x = h(p, k + s), q = l(0, k - 1); q <= x; q++)
                            v[q] = q ? k ? b[k - 1] === d[q - 1] ? u[q - 1] : h(u[q] || t, v[q - 1] || t) + 1 : q + 1 : k + 1;
                    h = [];
                    l = [];
                    s = [];
                    k = n;
                    for (q = p; k || q; )
                        p = m[k][q] - 1, q && p === m[k][q - 1] ? l.push(h[h.length] = {status: e,
                            value: d[--q], index: q}) : k && p === m[k - 1][q] ? s.push(h[h.length] = {status: f, value: b[--k], index: k}) : (--q, --k, g.sparse || h.push({status: "retained", value: d[q]}));
                    a.a.hc(s, l, !g.dontLimitMoves && 10 * n);
                    return h.reverse()
                }
                return function (a, d, e) {
                    e = "boolean" === typeof e ? {dontLimitMoves: e} : e || {};
                    a = a || [];
                    d = d || [];
                    return a.length < d.length ? b(a, d, "added", "deleted", e) : b(d, a, "deleted", "added", e)
                }
            }();
            a.b("utils.compareArrays", a.a.lb);
            (function () {
                function b(b, c, d, h, l) {
                    var m = [], k = a.B(function () {
                        var k = c(d, l, a.a.Ba(m, b)) || [];
                        0 <
                                m.length && (a.a.uc(m, k), h && a.l.w(h, null, [d, k, l]));
                        m.length = 0;
                        a.a.ta(m, k)
                    }, null, {i: b, ya: function () {
                            return!a.a.Tb(m)
                        }});
                    return{ea: m, B: k.ca() ? k : n}
                }
                var c = a.a.e.J(), d = a.a.e.J();
                a.a.Db = function (e, f, g, h, l) {
                    function m(b, c) {
                        w = q[c];
                        u !== c && (D[b] = w);
                        w.tb(u++);
                        a.a.Ba(w.ea, e);
                        t.push(w);
                        z.push(w)
                    }
                    function k(b, c) {
                        if (b)
                            for (var d = 0, e = c.length; d < e; d++)
                                c[d] && a.a.r(c[d].ea, function (a) {
                                    b(a, d, c[d].ka)
                                })
                    }
                    f = f || [];
                    h = h || {};
                    var r = a.a.e.get(e, c) === n, q = a.a.e.get(e, c) || [], p = a.a.ib(q, function (a) {
                        return a.ka
                    }), s = a.a.lb(p, f, h.dontLimitMoves),
                            t = [], v = 0, u = 0, x = [], z = [];
                    f = [];
                    for (var D = [], p = [], w, C = 0, B, E; B = s[C]; C++)
                        switch (E = B.moved, B.status) {
                            case "deleted":
                                E === n && (w = q[v], w.B && (w.B.k(), w.B = n), a.a.Ba(w.ea, e).length && (h.beforeRemove && (t.push(w), z.push(w), w.ka === d ? w = null : f[C] = w), w && x.push.apply(x, w.ea)));
                                v++;
                                break;
                            case "retained":
                                m(C, v++);
                                break;
                            case "added":
                                E !== n ? m(C, E) : (w = {ka: B.value, tb: a.O(u++)}, t.push(w), z.push(w), r || (p[C] = w))
                        }
                    a.a.e.set(e, c, t);
                    k(h.beforeMove, D);
                    a.a.r(x, h.beforeRemove ? a.ba : a.removeNode);
                    for (var C = 0, r = a.f.firstChild(e), F; w = z[C]; C++) {
                        w.ea ||
                                a.a.extend(w, b(e, g, w.ka, l, w.tb));
                        for (v = 0; s = w.ea[v]; r = s.nextSibling, F = s, v++)
                            s !== r && a.f.kc(e, s, F);
                        !w.ad && l && (l(w.ka, w.ea, w.tb), w.ad = !0)
                    }
                    k(h.beforeRemove, f);
                    for (C = 0; C < f.length; ++C)
                        f[C] && (f[C].ka = d);
                    k(h.afterMove, D);
                    k(h.afterAdd, p)
                }
            })();
            a.b("utils.setDomNodeChildrenFromArrayMapping", a.a.Db);
            a.X = function () {
                this.allowTemplateRewriting = !1
            };
            a.X.prototype = new a.P;
            a.X.prototype.renderTemplateSource = function (b, c, d, e) {
                if (c = (9 > a.a.C ? 0 : b.nodes) ? b.nodes() : null)
                    return a.a.W(c.cloneNode(!0).childNodes);
                b = b.text();
                return a.a.na(b, e)
            };
            a.X.vb = new a.X;
            a.Fb(a.X.vb);
            a.b("nativeTemplateEngine", a.X);
            (function () {
                a.xb = function () {
                    var a = this.ed = function () {
                        if (!u || !u.tmpl)
                            return 0;
                        try {
                            if (0 <= u.tmpl.tag.tmpl.open.toString().indexOf("__"))
                                return 2
                        } catch (a) {
                        }
                        return 1
                    }();
                    this.renderTemplateSource = function (b, e, f, g) {
                        g = g || t;
                        f = f || {};
                        if (2 > a)
                            throw Error("Your version of jQuery.tmpl is too old. Please upgrade to jQuery.tmpl 1.0.0pre or later.");
                        var h = b.data("precompiled");
                        h || (h = b.text() || "", h = u.template(null, "{{ko_with $item.koBindingContext}}" +
                                h + "{{/ko_with}}"), b.data("precompiled", h));
                        b = [e.$data];
                        e = u.extend({koBindingContext: e}, f.templateOptions);
                        e = u.tmpl(h, b, e);
                        e.appendTo(g.createElement("div"));
                        u.fragments = {};
                        return e
                    };
                    this.createJavaScriptEvaluatorBlock = function (a) {
                        return"{{ko_code ((function() { return " + a + " })()) }}"
                    };
                    this.addTemplate = function (a, b) {
                        t.write("<script type='text/html' id='" + a + "'>" + b + "\x3c/script>")
                    };
                    0 < a && (u.tmpl.tag.ko_code = {open: "__.push($1 || '');"}, u.tmpl.tag.ko_with = {open: "with($1) {", close: "} "})
                };
                a.xb.prototype =
                        new a.P;
                var b = new a.xb;
                0 < b.ed && a.Fb(b);
                a.b("jqueryTmplTemplateEngine", a.xb)
            })()
        })
    })();
})();


function ping(ip, callback) {

    if (!this.inUse) {
        this.status = 'unchecked';
        this.inUse = true;
        this.callback = callback;
        this.ip = ip;
        var _that = this;
        this.img = new Image();
        this.img.onload = function () {
            _that.inUse = false;
            _that.callback('responded');

        };
        this.img.onerror = function (e) {
            if (_that.inUse) {
                _that.inUse = false;
                _that.callback('responded', e);
            }

        };
        this.start = new Date().getTime();
        this.img.src = ip;
        this.timer = setTimeout(function () {
            if (_that.inUse) {
                _that.inUse = false;
                _that.callback('timeout');
            }
        }, 1500);
    }
}
var PingModel = function (servers) {
    var self = this;
    var myServers = [];
    ko.utils.arrayForEach(servers, function (location) {
        myServers.push({
            name: location,
            status: ko.observable('unchecked')
        });
    });
    self.servers = ko.observableArray(myServers);
    ko.utils.arrayForEach(self.servers(), function (s) {
        new ping(s.name, function (status, e) {
            if (status === "responded") {
                document.getElementById("onlineko").style.display = "none";
                document.getElementById("onlineok").style.display = "";
            } else {
                document.getElementById("onlineok").style.display = "none";
                document.getElementById("onlineko").style.display = "";
            }
        });
    });
};


//  controllo ONLINE OFFLINE
function online() {
//    var image = new Image();
//    image.src = "http://1.bp.blogspot.com/-y5G5_rDoXag/TcnuB6a8aYI/AAAAAAAARaw/ELKSsE0cprI/s200/google_immagini_virus.JPG";
//    var b1 = false;
//    if (image.complete && image.naturalWidth) {
//        b1 = true;
//    }
//    setTimeout(online, 5000);

    //document.getElementById("onlineko").style.display = "none";
    //document.getElementById("onlineok").style.display = "";

    //ko.applyBindings(komodel);

    //return false;
    //document.getElementById("onlineko").style.display = "none";
    //document.getElementById("onlineok").style.display = "";
    //$.ajax({
    //    async: false,
    //    type: "POST",
    //    url: "Time",
    //    success: function (result) {
    //        if (result === "true") {
    //            document.getElementById("onlineko").style.display = "none";
    //            document.getElementById("onlineok").style.display = "";
    //        } else {
    //            document.getElementById("onlineok").style.display = "none";
    //            document.getElementById("onlineko").style.display = "";
    //        }
    //    }
    //});
//    setTimeout(online, 1000);


    //var b1 = navigator.onLine;
    //if (b1) {
    //    document.getElementById("onlineko").style.display = "none";
    //    document.getElementById("onlineok").style.display = "";
    //} else {
    //    document.getElementById("onlineok").style.display = "none";
    //    document.getElementById("onlineko").style.display = "";
    //}
}

//  controlla campo se corretto formalmente
function checkValueCorrect(value) {
    if (value !== null) {
        if (!Number.isNaN(value)) {
            if (value !== "") {
                return true;
            }
        }
    }
    return false;
}

//show login
function showmodlogin(modal) {
    document.getElementById(modal).className = document.getElementById(modal).className + " in";
    document.getElementById(modal).style.display = "block";
}
//checkloginpassword
function checkvaluepass(psval, originalpass, modal, errmsg) {
    if (CryptoJS.MD5(document.getElementById(psval).value.trim()).toString() === originalpass) {
        document.getElementById(psval).value = '';
        $('#' + modal).modal('hide');
    } else {
        document.getElementById(psval).value = '';
        document.getElementById(errmsg).style.display = "";
    }
}
//checkevent
function checkkeysub(butclick, event) {
    if (event.keyCode === 13) {
        document.getElementById(butclick).click();
    }
}


function keysub(obj, e) {
    var e = (typeof event !== 'undefined') ? window.event : e;// IE : Moz 
    if (e.keyCode === 13) {
        document.getElementById('test1').style.display = '';
        document.getElementById('test1').focus();
        obj.focus();
        document.getElementById('test1').style.display = 'none';
        return false;
    }
}


function padDigits(number, digits) {
    return Array(Math.max(digits - String(number).length + 1, 0)).join(0) + number;
}

function fieldNameSurname(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(/\\/g, "");
    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldOnlyNumber(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/\D/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldOnlyNumber_RA(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/\D/g, '');
    if (stringToReplace.trim() === "") {
        stringToReplace = "0";
    }
    if (parseIntRaf(stringToReplace) === 0) {
        stringToReplace = "1";
    }
    document.getElementById(fieldid).value = stringToReplace;
}



function fieldNOSPecial(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;

    stringToReplace = stringToReplace.replace(/[&\/\\#+()$~%'":*?<>{}],/g, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldNOSPecial_1(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[];,{}()|\":<>?£ààáâãäçèéêëìíîïñòóôõö€ùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°€";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    //stringToReplace = stringToReplace.replace(/\\/g, "");
//    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}

function fieldNOSPecial_2(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`#$^&*[];{}|\":<>°§";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    //  stringToReplace = stringToReplace.replace(/\\/g, "");
//    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}


function onbottom() {
    $("html, body").animate({scrollTop: $(document).height()}, 1000);
}


function isValidDate(str) {
    var d = moment(str, 'D/M/YYYY');
    if (d === null || !d.isValid())
        return false;

    return str.indexOf(d.format('D/M/YYYY')) >= 0
            || str.indexOf(d.format('DD/MM/YYYY')) >= 0
            || str.indexOf(d.format('D/M/YY')) >= 0
            || str.indexOf(d.format('DD/MM/YY')) >= 0;
}

function checkpass(psval, originalpass) {
    if (CryptoJS.MD5(psval).toString() === originalpass.toString()) {
        return true;
    } else {
        return false;
    }
}

function encryptmd5(psval) {
    return CryptoJS.MD5(psval).toString();
}


function isempty(val) {
    if (val === "" || val === "None" || val === "..." || val === "---") {
        return true;
    }
    return false;
}

function isemptyField(field) {
    if (field === null || field === undefined) {
        console.log(field.id + ' --- null');
        return true;
    }
    if (!field.disabled) {
        if (field.value.trim() === "" || field.value.trim() === "None" || field.value.trim() === "..." || field.value.trim() === "---" || field.value.trim() === "-") {
            console.log(field.id + ' --- vuoto');
            return true;
        }
    }
    return false;
}



function replacethousand(value, sep) {
    if (sep === ",") {
        value = value.replace(/,/g, '');
    } else if (sep === ".") {
        value = value.replace(/\./g, '');
    }
    return value;
}


function isPhone(value, lenmin, lenmax) {
    value = value.trim();
    if (value.length >= lenmin && value.length <= lenmax) {
        var phoneno = /^([+|\d])+([\s|\d])+([\d])$/;
        //var phoneno = /^[\+]?[(]?[0-9]{3}[)]?[-\s\.]?[0-9]{3}[-\s\.]?[0-9]{4,6}$/im;
        if (value.match(phoneno)) {
            return true;
        } else {
            return false;
        }
    }
    return false;
}

function disable_sel2(id, idform) {
    var $dropDown = $('#' + id), name = id, $form = $dropDown.parent('form');
    $dropDown.data('original-name', name);
    $('#' + idform).find('input[type="hidden"][name=' + name + ']').remove();
    $('#' + idform).append('<input type="hidden" name="' + name + '" value="' + $dropDown.val() + '" />');
    $dropDown.addClass('disabled').prop({name: name + "_r", disabled: true});
}

function enable_sel2(id, idform) {
    var $dropDown = $('#' + id), name = id, $form = $dropDown.parent('form');
    $dropDown.data('original-name', name);
    $("input[type='hidden'][name=" + name + "]").remove();
    $form.find('input[type="hidden"][name=' + name + ']').remove();
    $dropDown.removeClass('disabled').prop({name: name, disabled: false});
}

function waitform() {
    document.getElementById("modalwaitbutton").click();
}


function getvalueofField(field) {
    if (field === null) {
        return "";
    } else if (field.tagName === "INPUT" || field.tagName === "TD" || field.tagName === "SPAN") {
        return field.innerHTML.trim();
    } else {
        return "";
    }
}

function getvalueofFieldINT(field) {
    if (field === null) {
        return "0";
    } else if (field.tagName === "INPUT") {
        return field.value.trim();
    } else if (field.tagName === "SPAN") {
        return field.innerHTML.trim();
    }
    return "0";
}

function inputvirgola() {
    $(document).find('input').keydown(function (evt) {
        if (evt.which === 110) {
            $(this).val($(this).val() + ',');
            evt.preventDefault();
        }
        ;
    });
}

function replacedoublequotes(mystring) {
    mystring = mystring.replace(/"/g, '\'');
    return mystring;
}


function charletter(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    stringToReplace = stringToReplace.replace(/[^a-z0-9]/gi, '');
    document.getElementById(fieldid).value = stringToReplace;
}

function RemoveAccents(str) {
    var accents = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž';
    var accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz";
    str = str.split('');
    var strLen = str.length;
    var i, x;
    for (i = 0; i < strLen; i++) {
        if ((x = accents.indexOf(str[i])) !== -1) {
            str[i] = accentsOut[x] + "'";
        }
        ;
    }
    return str.join('');
}

function modificaOAMSurname(field, event) {
    field.value = RemoveAccents(field.value.toUpperCase());
    fieldNameSurnameNEW(field.id);
}
function modificaOAM(field, event) {
    field.value = RemoveAccents(field.value.toUpperCase());
    fieldNOSPecial_2(field.id);
}

function fieldNameSurnameNEW(fieldid) {
    var stringToReplace = document.getElementById(fieldid).value;
    var specialChars = "~`!#$%^&*+=-[]();,/{}|\":<>?£,.àáâãäçèéêëìíîïñòóôõöùúûüýÿÀÁÂÃÄÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝ°èéòàù§*ç@|!£$%&/()=?^€ì";
    for (var i = 0; i < specialChars.length; i++) {
        stringToReplace = stringToReplace.replace(new RegExp("\\" + specialChars[i], 'gi'), '');
    }
    stringToReplace = stringToReplace.replace(new RegExp("[0-9]", "g"), "");
    document.getElementById(fieldid).value = stringToReplace;
}


function formatValueDecimalMaxPercentuale(value, max, thousand, decimal) {
    value.value = accounting.formatNumber(parseFloatRaf(value.value, thousand, decimal), 10, thousand, decimal);
    if (parseFloatRaf(replacestringparam(value.value, thousand), thousand, decimal) > parseFloatRaf(max, thousand, decimal)) {
        value.value = accounting.formatNumber(parseFloatRaf(max, thousand, decimal), 10, thousand, decimal);
        return "1.00";
    } else {
        return "0.00";
    }
}


function convertD_2023(str) {
    str = str.toString();
    str = str.slice(0, (str.indexOf(".")) + 9 + 1);
    return Number(str);  
}