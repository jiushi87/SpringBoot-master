var xmlhttp = new getXMLObject();

function locationUrl(url) {
    debugger;
    if (xmlhttp) {
        xmlhttp.open("POST", url, true);
        xmlhttp.onreadystatechange = handleServerResponse;
        xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
        xmlhttp.send();
    }
}

function getXMLObject() {
    var xmlHttp = false;
    try {
        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP") // For Old Microsoft
        // Browsers
    } catch (e) {
        try {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP") // For Microsoft
            // IE 6.0+
        } catch (e2) {
            xmlHttp = false // No Browser接受XMLHTTP Object然后为false
        }
    }
    if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
        xmlHttp = new XMLHttpRequest(); // For Mozilla, Opera Browsers
    }
    return xmlHttp; // 强制语句返回创建的ajax对象
}

function handleServerResponse() {
    debugger;
    if (xmlhttp.readyState == 4) {
        var text = xmlhttp.responseText;
        if (text.indexOf("<title>Favorites error Page</title>") >= 0) {
            window.location.href = "/error.html";
        } else {
            $("#content").html(xmlhttp.responseText);
        }
    }
}

function profileServerResponse() {
    debugger;
    if (xmlhttp.readyState == 4) {
        var text = xmlhttp.responseText;
        if (text.indexOf("<title>Favorites error Page</title>") >= 0) {
            window.location.href = "/error.html";
        } else {
            $("#information").html(xmlhttp.responseText);
        }
    }
}



