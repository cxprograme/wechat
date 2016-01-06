//摇一摇部分
        var SHAKE_THRESHOLD = 1000;
        var last_update = 0;
        var last_time = 0;
        var x;
        var y;
        var z;
        var last_x;
        var last_y;
        var last_z;
        var sound = new Howl({ urls: ['sound/shake_sound.mp3'] }).load();
        var findsound = new Howl({ urls: ['sound/shake_match.mp3'] }).load();
        var curTime;
        var isShakeble = true; 

        function init() {
            if (window.DeviceMotionEvent) {
                window.addEventListener('devicemotion', deviceMotionHandler, false);
            } else {
                $("#cantshake").show();
            }
        }

        function deviceMotionHandler(eventData) {
            curTime = new Date().getTime();
            var diffTime = curTime - last_update;
            if (diffTime > 100) {
                var acceleration = eventData.accelerationIncludingGravity;
                last_update = curTime;
                x = acceleration.x;
                y = acceleration.y;
                z = acceleration.z;
                var speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD && curTime - last_time > 1100 && isShakeble) {
                     var cdisplay=$("#content").css("display");
                    var hdisplay=$("#honbao").css("display");
                    console.log("cdisplay:"+cdisplay+","+"hdisplay "+hdisplay);
                    if (cdisplay=="none"&&hdisplay=="none") {
                        shake();
                        
                    }
                    else if(hdisplay=="block"){
                        console.log("进入红包");
                        $("#honbao").hide();
                        shake();
                    }
                    else if(cdisplay=="block"){
                        console.log("contentblock");
                        $("#content").hide();
                        shake();
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }

        
        function shake() {
            var count=Math.random()*10;
            console.log(count);
            last_time = curTime;

            $("#shakeup").animate({ top: "10%" }, 700, function () {
                $("#shakeup").animate({ top: "25%" }, 700, function () {
                    
                    findsound.play();
                    $.ajax({
                    	type:'GET',
                    	url:'/wechat/honbao',
                    	dataType:'json',
                    	success:function(data){
                    		if(data){
                    			$("#honbao").show();
                             }
                             else{
                                  $("#content").show();
                    		}
                    	}
                    });
                });
            });
            $("#shakedown").animate({ top: "40%" }, 700, function () {
                $("#shakedown").animate({ top: "25%" }, 700, function () {
                });
            });
            sound.play();
            
        }
		

        function reset(){
                    var cdisplay=$("#content").css("display");
                    var hdisplay=$("#honbao").css("display");
                    console.log("cdisplay:"+cdisplay+","+"hdisplay "+hdisplay);
                    if (cdisplay=="none"&&hdisplay=="none") {
                        shake();
                        
                    }
                    else if(hdisplay=="block"){
                        console.log("进入红包");
                        $("#honbao").hide();
                        shake();
                    }
                    else if(cdisplay=="block"){
                        console.log("contentblock");
                        $("#content").hide();
                        shake();
                    }
        }
		//各种初始化
        $(document).ready(function () {
            Howler.iOSAutoEnable = false;
            FastClick.attach(document.body);
            init();
        });
		