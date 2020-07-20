# Cordova-Plugin-Permissions

- [Overview](#overview)
    - [Minimum supported version](#minimum-supported-version)
        - [Android](#android)
- [Installation](#installation)
- [Usage](#usage)
    - [Android](#android)
        - [Permissions](#permissions)
        - [permissionStatus Constants](#permissionstatus-constants)
        - [checkPermission()](#checkPermission())
            - [Parameters](#parameters)
            - [Example usage](#example-usage)
        - [checkPermissions()](#checkPermissions())
            - [Parameters](#parameters-1)
            - [Example usage](#example-usage-1)
        - [requestPermission()](#requestPermission())
            - [Parameters](#parameters-2)
            - [Example usage](#example-usage-2)
        - [requestPermissions()](#requestPermissions())
            - [Parameters](#parameters-3)
            - [Example usage](#example-usage-3)
        - [forceAskPermission()](#forceAskPermission())
            - [Parameters](#parameters-4)
            - [Example usage](#example-usage-4)
<br>

## Overview
Cordova plugin to ask runtime permissions.<br>
### Minimum supported version
#### Android
This Plugin supports android api levels 23 to 29.

## Installation
    cordova plugin add https://github.com/dvenkat507/Cordova-Plugin-Permissions.git

## Usage

### Android

### Permissions
- `cordova.plugins.diagnostic.permission.READ_CALENDAR`
- `cordova.plugins.diagnostic.permission.WRITE_CALENDAR`
- `cordova.plugins.diagnostic.permission.CAMERA`
- `cordova.plugins.diagnostic.permission.READ_CONTACTS`
- `cordova.plugins.diagnostic.permission.WRITE_CONTACTS`
- `cordova.plugins.diagnostic.permission.GET_ACCOUNTS`
- `cordova.plugins.diagnostic.permission.ACCESS_FINE_LOCATION`
- `cordova.plugins.diagnostic.permission.ACCESS_COARSE_LOCATION`
- `cordova.plugins.diagnostic.permission.ACCESS_BACKGROUND_LOCATION`
- `cordova.plugins.diagnostic.permission.RECORD_AUDIO`
- `cordova.plugins.diagnostic.permission.READ_PHONE_STATE`
- `cordova.plugins.diagnostic.permission.CALL_PHONE`
- `cordova.plugins.diagnostic.permission.READ_CALL_LOG`
- `cordova.plugins.diagnostic.permission.WRITE_CALL_LOG`
- `cordova.plugins.diagnostic.permission.ADD_VOICEMAIL`
- `cordova.plugins.diagnostic.permission.USE_SIP`
- `cordova.plugins.diagnostic.permission.PROCESS_OUTGOING_CALLS`
- `cordova.plugins.diagnostic.permission.BODY_SENSORS`
- `cordova.plugins.diagnostic.permission.SEND_SMS`
- `cordova.plugins.diagnostic.permission.RECEIVE_SMS`
- `cordova.plugins.diagnostic.permission.READ_SMS`
- `cordova.plugins.diagnostic.permission.RECEIVE_WAP_PUSH`
- `cordova.plugins.diagnostic.permission.RECEIVE_MMS`
- `cordova.plugins.diagnostic.permission.READ_EXTERNAL_STORAGE`
- `cordova.plugins.diagnostic.permission.WRITE_EXTERNAL_STORAGE`



### permissionStatus constants
The following permission states are defined for Android:

- `NOT_REQUESTED` - App has not yet requested access to this permission.
App can request permission and user will be prompted to allow/deny.
- `DENIED_ONCE` - User denied access to this permission (without checking "Never Ask Again" box).
App can request permission again and user will be prompted again to allow/deny again.
- `DENIED_ALWAYS` - User denied access to this permission and checked "Never Ask Again" box.
App can never ask for permission again.
The only way around this is to instruct the user to manually change the permission on the app permissions page in Settings.
- `GRANTED` - User granted access to this permission.<br>
<br>
### checkPermission()
This function returns the permission status for a given permission. 
#### Parameters
- {Function} successCallback - function to call on successful retrieval of status.
The function is passed a single string parameter which defines the current [permission status](#permissionstatus-constants).
- {Function} errorCallback - function to call on failure to retrieve permission status.
The function is passed a single string parameter containing the error message.
- {String} permission - permission to request permission status for, defined as a [permissions](#permissions).
#### Example usage
    var permission = cordova.plugins.permission.CAMERA;
    cordova.plugins.permission.checkPermission(
            function (permissionStatus) {
                switch (permissionStatus) {
                    case cordova.plugins.permission.permissionStatus.GRANTED :
                         console.log("Permission " + permission +" is Granted");
                         break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ONCE :
                         console.log("Permission " + permission +" is Denied Once");
                         break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ALWAYS :
                         console.log("Permission " + permission +" is Denied Always");
                         break;
                    case cordova.plugins.permission.permissionStatus.NOT_REQUESTED:
                         console.log("Permission " + permission +" is Not requested");
                         break;
                }  
            },
            function (error){ 
                console.log(error); 
            },
            permission
        );


### checkPermissions()
This function returns the permission status for a given  multiple permissions. 
#### Parameters
- {Function} successCallback - function to call on successful retrieval of status.
The function is passed a array of string as a parameter which defines the [permission status](#permissionstatus-constants).
- {Function} errorCallback - function to call on failure to retrieve permissions status.
The function is passed a single string parameter containing the error message.
- {Array} permissions - multiple permissions to request permission status for, defined as a [permissions](#permissions).
#### Example usage
    var permissionList = [cordova.plugins.permission.permissions.WRITE_EXTERNAL_STORAGE,
                          cordova.plugins.permission.permissions.READ_EXTERNAL_STORAGE];
    cordova.plugins.permission.checkPermissions(
            function (permissionsStatus) {
                for(var i in permissionsStatus){
                    switch (permissionsStatus[i]) {
                        case cordova.plugins.permission.permissionStatus.GRANTED :
                            console.log("Permission " + permission +" is Granted");
                            break;
                        case cordova.plugins.permission.permissionStatus.DENIED_ONCE :
                            console.log("Permission " + permission +" is Denied Once");
                            break;
                        case cordova.plugins.permission.permissionStatus.DENIED_ALWAYS :
                            console.log("Permission " + permission +" is Denied Always");
                            break;
                        case cordova.plugins.permission.permissionStatus.NOT_REQUESTED:
                            console.log("Permission " + permission +" is Not Requested");
                            break;
                    }
                }
            },
            function (error){
                console.log(error);
            },
            permissionList
        );

### requestPermission()
Requests app to be granted authorisation for a runtime permission. For this method `NOT_REQUESTED` permission status is not used. 
#### Parameters
- {Function} successCallback - function to call on successful retrieval of status.
The function is passed a single string parameter which defines the resulting [permission status](#permissionstatus-constants).
- {Function} errorCallback - function to call on failure to retrieve permission status.
The function is passed a single string parameter containing the error message.
- {String} permission - permission to request permission status for, defined as a [permissions](#permissions).
#### Example usage
    var permission = cordova.plugins.permission.CAMERA;
    cordova.plugins.permission.requestPermission(
            function (permissionStatus) {
                switch (permissionStatus) {
                    case cordova.plugins.permission.permissionStatus.GRANTED :
                         console.log("Permission " + permission +" is Granted");
                         break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ONCE :
                         console.log("Permission " + permission +" is Denied Once");
                         break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ALWAYS :
                         console.log("Permission " + permission +" is Denied Always");
                         break;
                }  
            },
            function (error){ 
                console.log(error); 
            },
            permission
        );

### requestPermissions()
Requests app to be granted authorisation for multiple runtime permission. For this method `NOT_REQUESTED` permission status is not used. 
#### Parameters
- {Function} successCallback - function to call on successful retrieval of status.
The function is passed a array of string as a parameter which defines the [permission status](#permissionstatus-constants).
- {Function} errorCallback - function to call on failure to retrieve permissions status.
The function is passed a single string parameter containing the error message.
- {Array} permissions - multiple permissions to request permission status for, defined as a [permissions](#permissions).
#### Example usage
    var permissionList = [cordova.plugins.permission.permissions.WRITE_EXTERNAL_STORAGE,
                          cordova.plugins.permission.permissions.READ_EXTERNAL_STORAGE];
    cordova.plugins.permission.requestPermissions(function (permissionsStatus) {
            for(var i in permissionsStatus){
                switch (permissionsStatus[i]) {
                    case cordova.plugins.permission.permissionStatus.GRANTED :
                        console.log("Permission " + permission +" is Granted");
                        break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ONCE :
                        console.log("Permission " + permission +" is Denied Once");
                        break;
                    case cordova.plugins.permission.permissionStatus.DENIED_ALWAYS :
                        console.log("Permission " + permission +" is Denied Always");
                        break;
                    case cordova.plugins.permission.permissionStatus.NOT_REQUESTED:
                        console.log("Permission " + permission +" is Not Requested");
                        break;
                }
            }
            app.validate();
        },function (e) {
            console.log(e);
        },permissions);

### forceAskPermission()
This function is used to ask permissions if the user is checked `Never Ask Again` in previous request. This function opens a app info activity and user need to give permissions manually. 
#### Parameters
- {Function} successCallback - function to call on successful open of the activity.
The function is passed a single string which is resulted with `'Successful'` string.
- {Function} errorCallback - function to call on failure to open the app info activity.
The function is passed a single string parameter containing the error message.
#### Example usage
    cordova.plugins.permission.forceAskPermission(
        function (success) {
            console.log(success);
        },function (error) {
            console.log(error);
        }
    );