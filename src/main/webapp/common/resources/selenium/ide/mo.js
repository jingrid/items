

if(! window['Mo'] ){
    window['Mo'] = {};
}
if( !window.localStorage ){
    alert('This browser does NOT support localStorage');
}

Mo.base36chars = "0123456789qwertyuioplkjhgfdsazxcvbnm";
Mo.randomString = function randomChar( len )  {
    var x = Mo.base36chars;
    var serialArray = new Array( len );
    var len36 = Mo.base36chars.length;
    for( var i=0; i<len; i++ ) {
        serialArray[i] = x.charAt( Math.floor( Math.random()*100000000 )%len36 );
    }
    return serialArray.join('');
};

Mo.Log = function( category ){
    var log = this;
    var self = this;
    this.category = category;
    
    function LogLevel(level, name) {
        this.level = level;
        this.name = name;
        var self = this;
        log[name.toLowerCase()] = function(msg) { log.log(self, msg) };
    }

    this.DEBUG = new LogLevel(1, "DEBUG");
    this.INFO = new LogLevel(2, "INFO");
    this.WARN = new LogLevel(3, "WARN");
    this.ERROR = new LogLevel(4, "ERROR");

    this.log = function(level, msg) {
        var threshold = this[this._getThreshold()];
        if (level.level >= threshold.level) {
            this._write( level.name + "] " + this._formatDate(new Date()) + " " + self.category + ": " + msg);
        }
    }
};


Mo.Log.prototype = {
    _getThreshold: function() {
        return 'DEBUG';
    },

    _formatDate: function(date) {
        return date.getFullYear() + 
          "-" + this._formatDigits(date.getMonth() + 1, 2) + 
          "-" + this._formatDigits(date.getDate(), 2) +
          " " + this._formatDigits(date.getHours(), 2) +
          ":" + this._formatDigits(date.getMinutes(), 2) +
          ":" + this._formatDigits(date.getSeconds(), 2) +
          "." + this._formatDigits(date.getMilliseconds(), 3);
    },

    _formatDigits: function(n, digits) {
        var s = n.toString();
        var pre = digits - s.length;
        var result = "";
        for (var i = 0; i < pre; i++) {
            result += "0";
        }
        result += s;
        return result;
    },

    _write: function(message) {
        return;
    }
};

Mo.Record = function( data){
    this.data = data;
//              var encodedTarget = $().crypt({method: "b64enc",source: target});
//              var b64dec = $().crypt({method: "b64dec",source: encodedTarget});
};

Mo.Record.prototype = {
    toString: function(){
        return JSON.stringify( this.data );
    }
};

Mo.Record.newInstance = function( jsonData ){
    return new Mo.Record( jsonData );
};

Mo.Record.fromString = function(data){
    var json = null;
    json = JSON.parse( data ); //error handling
    return new Mo.Record( json );
};

Mo.RecordBatch = function( records ){
    this.data = {};
    this.data.records = records;
};

Mo.RecordBatch.prototype = {
    toString: function(){
        return JSON.stringify( this.data );
    }
};

Mo.RecordBatch.newInstance = function( records ){
    return new Mo.RecordBatch( records );
};

Mo.ActionStore = function(){};

Mo.ActionStore.getInstance = function(){
    var index = parseInt( window.localStorage['mo.actionIndex'] );
    var instance = new Mo.ActionStore();
    if( window.isNaN( index ) ){ 
        instance.index = 0;
        window.localStorage['mo.actionIndex'] = '0';
    }
    else{
        instance.index = index;
    }
    return instance;
};

Mo.ActionStore.getCurrentInstance = function(){
    if( !Mo.ActionStore.current ){
        Mo.ActionStore.current = Mo.ActionStore.getInstance();
    }
    return Mo.ActionStore.current;
};

Mo.ActionStore.prototype.add = function( command ){
    var index = this.getIndex();
    window.localStorage[ 'mo.actionSequence.' + index ] = command;
    index++;
    window.localStorage['mo.actionIndex'] = index;
};

Mo.ActionStore.prototype.getIndex = function(){
    var index = parseInt( window.localStorage['mo.actionIndex'] );
    if( window.isNaN( index ) ){ 
        this.setIndex( 0 );
        return 0;
    }
    else{
        return index;
    }
};

Mo.ActionStore.prototype.setIndex = function( i ){
    window.localStorage['mo.actionIndex'] = '' + i;
};

Mo.ActionStore.prototype.list = function(flush){
    var index = this.getIndex();
    if( index==0 ){
        return [];
    }
    
    var pool = [];
    for(var i=0, len=index; i<len; i++){
        var key = 'mo.actionSequence.' + i;
        var value = window.localStorage[ key ];
        if(value!=null){
            var record = null;
            try{
                record = Mo.Record.fromString( value );
                pool.push(record.data);
            }
            catch(e){}
        }
        if( flush ){
            window.localStorage.removeItem( key );
        }
    }
    if( flush ){
        window.localStorage['mo.actionIndex'] = 0;
    }
    return pool;
};

Mo.PageStore = function(){};

Mo.PageStore.getInstance = function(){
    var index = parseInt( window.localStorage['mo.pageIndex'] );
    var instance = new Mo.PageStore();
    if( window.isNaN( index ) ){ 
        instance.index = 0;
        window.localStorage['mo.pageIndex'] = '0';
    }
    else{
        instance.index = index;
    }
    return instance;
};

Mo.PageStore.getCurrentInstance = function(){
    if( !Mo.PageStore.current ){
        Mo.PageStore.current = Mo.PageStore.getInstance();
    }
    return Mo.PageStore.current;
};

Mo.PageStore.prototype.add = function( command ){
    var index = this.getIndex();
    window.localStorage[ 'mo.pageSequence.' + index ] = command;
    index++;
    window.localStorage['mo.pageIndex'] = index;
};

Mo.PageStore.prototype.getIndex = function(){
    var index = parseInt( window.localStorage['mo.pageIndex'] );
    if( window.isNaN( index ) ){ 
        this.setIndex( 0 );
        return 0;
    }
    else{
        return index;
    }
};

Mo.PageStore.prototype.setIndex = function( i ){
    window.localStorage['mo.pageIndex'] = '' + i;
};

Mo.PageStore.prototype.list = function(flush){
    var index = this.getIndex();
    if( index==0 ){
        return [];
    }
    
    var pool = [];
    for(var i=0, len=index; i<len; i++){
        var key = 'mo.pageSequence.' + i;
        var value = window.localStorage[ key ];
        if(value!=null){
            var record = null;
            try{
                record = Mo.Record.fromString( value );
                pool.push(record.data);
            }
            catch(e){}
        }
        if( flush ){
            window.localStorage.removeItem( key );
        }
    }
    if( flush ){
        window.localStorage['mo.pageIndex'] = 0;
    }
    return pool;
};

Mo.StoreObserver = function(){
    this.recordingEnabled = true;
    this.count = 0;
    this.store = Mo.ActionStore.getCurrentInstance();
};

Mo.StoreObserver.prototype.addCommand = function( command, target, value, window ){
    var url = window.location.href;
    var timestamp = new Date().getTime();
    var record = Mo.Record.newInstance({
        url: url,
        timestamp: timestamp,
        command: command, 
        target: this.serializeTarget(target), 
        value: value 
    });
    
    this.store.add( record.toString() );
};

Mo.StoreObserver.prototype.serializeTarget = function( target ){
    var retString = null;
    if( $.isArray( target ) ){
        var len = target.length;
        var retArray = new Array( len );
        for(var i=0; i<len; i++){
            if( $.isArray( target[i] ) ){
                retArray[i] = target[i][1] + ':' + target[i][0];
            }
        }
        retString = retArray.join(',');
    }
    else{
        retString = target;
    }
    return retString;
};

Mo.PageObserver = {};
Mo.PageObserver.currentPageRecord = Mo.Record.newInstance({
    url: null,
    duration: null,
    enterTime: null, 
    leaveTime: null
});

Mo.PageObserver.init = function(){
    Mo.PageObserver.currentPageRecord.data.url = window.location.href;
    Mo.PageObserver.currentPageRecord.data.enterTime = new Date().getTime();

    jQuery(window).bind('beforeunload',function(){
        Mo.PageObserver.currentPageRecord.data.leaveTime = new Date().getTime();
        Mo.PageObserver.currentPageRecord.data.duration = Mo.PageObserver.currentPageRecord.data.leaveTime - Mo.PageObserver.currentPageRecord.data.enterTime;
        Mo.PageStore.getCurrentInstance().add( Mo.PageObserver.currentPageRecord.toString() );
    });
    
};

/*        
Mo.StoreObserver.prototype.onUnloadDocument = function( doc ){
};
*/

Mo.getUserBehaviorRecorder = function(){
    if( !Recorder.currentRecorder ){
        Recorder.currentRecorder = Recorder.register( new Mo.StoreObserver(), window);
    }
    return Recorder.currentRecorder;
};

//window._sendMoData = function(){ 
Mo.sendInterval = 3000;
Mo.sendStatisticData = function(){ 
    if( Mo.ActionStore.getCurrentInstance().getIndex()!=0) {
        var records = Mo.ActionStore.getCurrentInstance().list(true);
        var actionBatch = Mo.RecordBatch.newInstance( records );
        $.ajax({
            type: 'POST', 
            contentType: 'application/json',
            url: 'https://www.realsaas.com/realpaas-linker-web/public/record-action', 
            data: JSON.stringify( actionBatch.data ),
            dataType: 'json',
            success: function(data){},
            error: function(){}
        });
    }
    
    if( Mo.PageStore.getCurrentInstance().getIndex()!=0) {
        var records = Mo.PageStore.getCurrentInstance().list(true);
        var pageBatch = Mo.RecordBatch.newInstance( records );
        var dataString = JSON.stringify( pageBatch.data );
        //alert( dataString );
        $.ajax({
            type: 'POST', 
            contentType: 'application/json',
            url: 'https://www.realsaas.com/realpaas-linker-web/public/record-page', 
            data: dataString,
            dataType: 'json',
            success: function(data){},
            error: function(){}
        });
    }
    
};

$(document).ready(function(){
    Mo.PageObserver.init();
    setInterval("Mo.sendStatisticData()", Mo.sendInterval);
    Mo.getUserBehaviorRecorder();
});

function classObservable(clazz) {
    clazz.addObserver = function(observer) {
        if (!this.observers) this.observers = [];
        this.observers.push(observer);
    };

    clazz.removeObserver = function(observer) {
        if (!this.observers) return;
        this.observers["delete"](observer);
    };

    clazz.notify = function(event) {
        if (this.log) {
            this.log.debug("notify " + event);
        }
        if (!this.observers) return;
        var args = [];
        for (var i = 1; i < arguments.length; i++) {
            args.push(arguments[i]);
        }
        for (var i = 0; i < this.observers.length; i++) {
            var observer = this.observers[i];
            if (observer[event]) {
                try {
                    observer[event].apply(observer, args);
                } catch(e) {
                    //continue with the rest even if one observer fails
                }
            }
        }
    };
}

// Samit: Ref: Split the fn to allow both objects of a class as well as the class itself to be notifiable as required
function observable(clazz) {
    classObservable(clazz.prototype);
}

function objectExtend(destination, source) {
  for (var property in source) {
    destination[property] = source[property];
  }
  return destination;
}

function exactMatchPattern(string) {
    if (string != null && (string.match(/^\w*:/) || string.indexOf('?') >= 0 || string.indexOf('*') >= 0)) {
        return "exact:" + string;
    } else {
        return string;
    }
}
