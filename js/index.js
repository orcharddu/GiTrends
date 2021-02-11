
        // add:function() {
            
        //     isValid = this.checkValid(this.input)
        //     if(isValid.valid) {
        //         //this.arr.push(isValid.type + " " + isValid.url);
        //         var tmp = this.input.split(/.+.com\//)[1].split(/\//);
        //         var user = tmp[0];
        //         var repo = tmp[1].split(".git")[0];
        //         var githubAPI = "https://api.github.com/repos/" + user + "/" + repo;
        //         var vue = this;
        //         console.log(githubAPI);
        //         axios.get(githubAPI).then(
        //             function(resp){
        //                 vue.arr.push(resp.data);
        //                 console.log(resp);
        //             },
        //             function(err) {
        //                 console.log(err);
        //             }
        //         )
        //     }
            

$(function() {
    
    var history = new History();
    var historyIns = "<li class=\"search-history-item\"><span></span><span class=\"search-history-remove\"></span></li>";
    history.value.items.forEach(e => {
        $(".search-history ul").prepend(historyIns);
        $(".search-history-item:first span:first").text(e.site + ".com/" + e.owner + "/" + e.repo);
    });

    $(".search-bar input").click(function(){
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
            if($(".search-history>ul>li").length > 0) {
                $(".search-history").css("display", "inline").stop().fadeTo(100, 1);
            }
        return false;
    });

    $(".search-bar input").blur(function() { 
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
        $(".search-history").stop().fadeTo(100, 0, function(){ $(this).css("display", "none") });
        return false;
    });

    $(".search-bar input").keydown(function(e){
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
        if(e.keyCode == 13) {
            $(".search-bar button").triggerHandler("click");
            return false;
        }
        return true
    });

    $(".search-bar button").click(function(){
        var url = $(".search-bar input").val().trim();
        var regex =  /^(https?:\/\/|)(www.|)(github|gitlab)(.com|)\/([\d\w-]+)\/([\d\w-]+)(.git|)\/?$/i;
        var result = url.match(regex);
        if(result){
            var site = result[3].toLowerCase(), owner = result[5], repo = result[6];
            if(history.index(site, owner, repo) == -1){
                //add to history
                $(".search-history ul").prepend(historyIns);
                $(".search-history-item:first span:first").text(site + ".com/" + owner + "/" + repo);
                history.add(site, owner, repo);
                var itemNum = $(".search-history>ul>li").length;
                if(itemNum > history.maxLength){
                    $(".search-history-item:last").remove();
                }
            }
            window.location.href = "./result.html?site=" + site + "&owner=" + owner + "&repo=" + repo;
        }else{
            $(".search-tips").css("visibility", "visible").stop().fadeTo(100, 1);
        }
        return false;
    });

    $(".search-history").on("click", ".search-history-item", function(){
        var itemNum = $(".search-history>ul>li").length;
        var index = itemNum - $(this).index() - 1;
        var e = history.value.items[index];
        $(".search-bar input").val(e.site + ".com/" + e.owner + "/" + e.repo);
        $(".search-bar button").triggerHandler("click");
        return false;
    });

    $(".search-history").on("mousedown", ".search-history-remove", function(){ 
        var $item = $(this).parent();
        var itemNum = $(".search-history>ul>li").length;
        var index = itemNum - $item.index() - 1;
        history.remove(index);
        if(itemNum > 1){
            $item.remove(); 
        }else{
            $(".search-history").stop().fadeTo(100, 0, function(){
                $item.remove();
            });
        }
        return false;
    });
});


