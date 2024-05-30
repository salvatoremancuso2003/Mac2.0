function getconsonants(str) {
    return str.replace(/[^BCDFGHJKLMNPQRSTVWXYZ]/gi, '');
}

function getvocal(str) {
    return str.replace(/[^AEIOU]/gi, '');
}

function getStringName(name) {
    var codname = getconsonants(name);
    if (codname.length >= 4) {
        codname = codname.charAt(0) + codname.charAt(2) + codname.charAt(3);
    } else {
        codname += getvocal(name);
        codname += 'XXX';
        codname = codname.substr(0, 3);
    }
    return codname.toUpperCase();
}

function getStringSurname(surname) {
    var codesurn = getconsonants(surname);
    codesurn += getvocal(surname);
    codesurn += 'XXX';
    codesurn = codesurn.substr(0, 3);
    return codesurn.toUpperCase();
}

function checkCF(cf) {
    var validi, i, s, set1, set2, setpari, setdisp;
    if (cf === '') {
        return false;
    }
    cf = cf.toUpperCase();
    if (cf.length !== 16) {
        return false;
    }
    validi = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for (i = 0; i < 16; i++) {
        if (validi.indexOf(cf.charAt(i)) === -1)
            return false;
    }
    set1 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    set2 = "ABCDEFGHIJABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setpari = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    setdisp = "BAKPLCQDREVOSFTGUHMINJWZYX";
    s = 0;
    for (i = 1; i <= 13; i += 2) {
        s += setpari.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    for (i = 0; i <= 14; i += 2) {
        s += setdisp.indexOf(set2.charAt(set1.indexOf(cf.charAt(i))));
    }
    if (s % 26 !== cf.charCodeAt(15) - 'A'.charCodeAt(0)) {
        return false;
    }
    return true;
}