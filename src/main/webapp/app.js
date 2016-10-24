var kajapp = {};

/**
 *  Facebook login stuff
 */
function checkLoginState() {
FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
});
}

function statusChangeCallback(response) {
if (response.status === 'connected') {
    testAPI();
} else if (response.status === 'not_authorized') {
    document.getElementById('status').innerHTML = 'Please log into this app.';
} else {
    console.log('WHAT');
    document.getElementById('status').innerHTML = 'Please log into Facebook.';
}
}

window.fbAsyncInit = function() {
FB.init({
    appId      : 297459477289247,
    cookie     : true,  
    xfbml      : true,  
    version    : 'v2.5' 
});

FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
});
};

(function(d, s, id) {
var js, fjs = d.getElementsByTagName(s)[0];
if (d.getElementById(id)) return;
js = d.createElement(s); js.id = id;
js.src = "//connect.facebook.net/en_US/sdk.js";
fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function testAPI() {
console.log('Welcome!  Fetching your information.... ');
FB.api('/me', function(response) {

    document.getElementById('status').innerHTML = '<h4>Thanks for logging in, ' + response.name + '!</h4>';

    FB.api('/168956843129213/posts', function(response){
        handleClubResponse(response);
    });

    FB.api('KantinSteakBurger/photos?fields=name,images,page_story_id&type=uploaded',function(response){
        handleKantinResponse(response);
    });

    FB.api('belvarosidisznotoros/photos?fields=images,created_time&type=uploaded',function(response){
        handleTorosResponse(response);
    });

    FB.api('212613358756081/photos?fields=name,images,page_story_id&type=uploaded',function(response){
        handleAndreasResponse(response);
    });

    $.getJSON('/api/restaurant/intenzo', function(response){
        handleIntenzoResponse(response);
    });

    FB.api('/verandagrillandwine/posts',function(response){
        //console.log(response);
        handleVerandaResponse(response);
    });


});
}


String.prototype.containsAny = function(keywords){
    for(var i = 0; i < keywords.length; i++){
        if(this.toUpperCase().indexOf(keywords[i].toUpperCase()) != -1)
            return true;
    }
    return false;
}


/**
 * Response parsers
 */


//club
function handleClubResponse(response){
    kajapp.clubLatest = response.data.slice(0,5);
    var clubHolder = document.getElementById("club");
    var clubContent = kajapp.clubLatest[0].message.replace(/\n/g,'<br>');
        clubContent += '<br>@ ' + kajapp.clubLatest[0].created_time.split("T")[0];
    clubHolder.innerHTML = clubContent;
}

//kantin
function handleKantinResponse(response){
    
    let optionalWidth = 320;
    let keywords = ['menü','heti','hét','napi','hétfő','kedd','szerda','csütörtök','péntek'];
    kajapp.kantin = [];
    var data = response.data;

    for(var j = 0; j < data.length; j++){
        if(data[j].name && data[j].name.containsAny(keywords)){
            var optionalImg = data[j].images[0].source;
            var msg = data[j].name;
            var d = calcDifference(optionalWidth,data[j].images[0].width);
            for(var i = 0; i < data[j].images.length; i++){
                if(d > calcDifference(optionalWidth,data[j].images[i].width)){
                    d = calcDifference(optionalWidth,data[j].images[i].width);
                    optionalImg = data[j].images[i].source;
                }
            }
            kajapp.kantin.push({img : optionalImg, msg : msg});
        }
    }

    var kantinHolder = document.getElementById('kantin');
    var kantinContent = '<img src="'+ kajapp.kantin[0].img +'">';
    kantinHolder.innerHTML = kantinContent;
}

function handleTorosResponse(response){
    var data = response.data.slice(0,5);
    kajapp.torosLatest = [];

    let optionalWidth = 320;
    for(var j = 0; j < data.length; j++){
        var optionalImg = data[j].images[0].source;
        var d = calcDifference(optionalWidth,data[j].images[0].width);
        for(var i = 0; i < data[j].images.length; i++){
            if(d > calcDifference(optionalWidth,data[j].images[i].width)){
                d = calcDifference(optionalWidth,data[j].images[i].width);
                optionalImg = data[j].images[i].source;
            }
        }
        kajapp.torosLatest.push({img : optionalImg, date : data[j].created_time.split('T')[0]});
    }

    var torosHolder = document.getElementById('toros');
    var torosContent = kajapp.torosLatest[0].date + '<br><img src="'+ kajapp.torosLatest[0].img +'">';
    torosHolder.innerHTML = torosContent;
    
}

function handleAndreasResponse(response){
    var data = response.data.slice(0,10);
    kajapp.andreasLatest = [];

    let optionalWidth = 320;
    for(var j = 0; j < data.length; j++){
        var optionalImg = data[j].images[0].source;
        var d = calcDifference(optionalWidth,data[j].images[0].width);
        for(var i = 0; i < data[j].images.length; i++){
            if(d > calcDifference(optionalWidth,data[j].images[i].width)){
                d = calcDifference(optionalWidth,data[j].images[i].width);
                optionalImg = data[j].images[i].source;
            }
        }
        kajapp.andreasLatest.push(optionalImg);
    }

    var andreasHolder = document.getElementById('andreas');
    var andreasContent = '<img src="'+ kajapp.andreasLatest[0] +'">';
    andreasHolder.innerHTML = andreasContent;
    
}

function handleIntenzoResponse(response){
    var intenzoHolder = document.getElementById('intenzo');
    var intenzoContent = "";
        for(var i = 0; i < response.menu.length; i++){
            var pieces = response.menu[i].split(' ');
            var day = pieces[0];
            pieces.shift();
            intenzoContent += '<b>' + day + ' </b><p>' + pieces.join(' ') + '</p>';
        }    
    intenzoHolder.innerHTML = intenzoContent;
}

function handleVerandaResponse(response){
    console.log(response);
    let keywords = ['menü','heti','hét','napi','hétfő','kedd','szerda','csütörtök','péntek'];
    let data = response.data;
    kajapp.verandaLatest = [];
    
    var i = 0;
    while(kajapp.verandaLatest.length < 5 || i === data.length){
        if(data[i].message && data[i].message.containsAny(keywords))
            kajapp.verandaLatest.push({
                msg: data[i].message, 
                date: data[i].created_time.split('T')[0]}
            );
        i++;
    }
    
    var verandaHolder = document.getElementById('veranda');
    var verandaContent = '<p>' + kajapp.verandaLatest[0].msg.replace(/\n/g,'<br>') + '</p>';
        verandaContent += '<span>' + kajapp.verandaLatest[0].date + '</span>';
    verandaHolder.innerHTML = verandaContent;
    console.log(verandaContent);

}

function calcDifference(a,b){
    return  Math.abs(a - b);
}