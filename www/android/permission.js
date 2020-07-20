var exec = require('cordova/exec');

exports.permissions = {
    "READ_CALENDAR" : "READ_CALENDAR",
    "WRITE_CALENDAR" : "WRITE_CALENDAR",
    "CAMERA" : "CAMERA",
    "READ_CONTACTS" : "READ_CONTACTS",
    "WRITE_CONTACTS" : "WRITE_CONTACTS",
    "GET_ACCOUNTS" : "GET_ACCOUNTS",
    "ACCESS_FINE_LOCATION" : "ACCESS_FINE_LOCATION",
    "ACCESS_COARSE_LOCATION" : "ACCESS_COARSE_LOCATION",
    "ACCESS_BACKGROUND_LOCATION": "ACCESS_BACKGROUND_LOCATION",
    "RECORD_AUDIO" : "RECORD_AUDIO",
    "READ_PHONE_STATE" : "READ_PHONE_STATE",
    "CALL_PHONE" : "CALL_PHONE",
    "READ_CALL_LOG" : "READ_CALL_LOG",
    "WRITE_CALL_LOG" : "WRITE_CALL_LOG",
    "ADD_VOICEMAIL" : "ADD_VOICEMAIL",
    "USE_SIP" : "USE_SIP",
    "PROCESS_OUTGOING_CALLS" : "PROCESS_OUTGOING_CALLS",
    "BODY_SENSORS" : "BODY_SENSORS",
    "SEND_SMS" : "SEND_SMS",
    "RECEIVE_SMS" : "RECEIVE_SMS",
    "READ_SMS" :"READ_SMS",
    "RECEIVE_WAP_PUSH" : "RECEIVE_WAP_PUSH",
    "RECEIVE_MMS" : "RECEIVE_MMS",
    "READ_EXTERNAL_STORAGE": "READ_EXTERNAL_STORAGE",
    "WRITE_EXTERNAL_STORAGE": "WRITE_EXTERNAL_STORAGE"    
};

exports.permissionStatus = {
    "GRANTED" : "GRANTED",
    "DENIED_ONCE" : "DENIED_ONCE",
    "DENIED_ALWAYS" : "DENIED_ALWAYS",
    "NOT_REQUESTED" : "NOT_REQUESTED"
};

exports.checkPermission = function (success, error, permission) {
    exec(success, error, 'Permissions', 'checkPermission', [permission]);
};

exports.requestPermission = function(success, error, permission){
    exec(success, error, 'Permissions', 'requestPermission', [permission]);
}

exports.forceAskPermission = function(success, error){
    exec(success, error, 'Permissions', 'forceAskPermission', []);
}

exports.checkPermissions = function(success, error, permissions){
    exec(success, error, 'Permissions', 'checkPermissions', permissions);
}

exports.requestPermissions = function(success, error, permissions){
    exec(success, error, 'Permissions', 'requestPermissions', permissions);
}
