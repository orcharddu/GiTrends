function searchGithub(repoName, onSuccess, onFailure) {
    var querystring = 'q=' + encodeURIComponent(repoName);
    $.ajax({
        type: "GET",
        url: "https://api.github.com/search/repositories?" + querystring,
        success: function(result){
            var repositories = []
            for (var x=0; x < result.items.length && x < 3; x++){
                repositories.push({
                    name: result.items[x].name,
                    description: result.items[x].description,
                    url: result.items[x].html_url
                })
            }
            onSuccess(repositories)
        },
        error: function(e){
            onFailure(e)
        }
    });
}

function populateAutocomplete(currentString, spinner, autocompleteList){
    // Get the string entered so far:
    searchGithub(currentString, function(repositories){
        // Populate the autocomplete with the repositories:
        var newList = ""
        for (var x=0; x < repositories.length; x++){
            console.log(repositories[x].url)
            newList += "<li class='search-auto-item' data-repo-url='" + repositories[x].url + "'>" + repositories[x].name + "<br><small data-repo-url='" + repositories[x].url + "'>" + repositories[x].description + "</small></li>"
        }
        autocompleteList.html(newList)
        spinner.addClass("hidden")
    }, function(e){
        autocompleteList.html('<div class="alert alert-danger" role="alert">Too many requests!</div>')
        spinner.addClass("hidden")
    })

}

function handleAutocomplete(){
    var lastKeyPress;
    var fired;

    var timeBeforeFire = 600 // milliseconds
    var spinner = $(".autocomplete-spinner")
    var autocompleteList = $(".autocomplete-list")

    // On initial keypress display loading wheel: 
    $(".search-bar input").keyup(function(e){
        var currentString = $(".search-bar input").val()
        autocompleteList.empty()
        if (currentString !== null && currentString !== ""){
            if (spinner.hasClass("hidden")){
                spinner.removeClass("hidden")
            }
            fired = false;
            lastKeyPress = new Date().getTime();
            // If the user stops typing for 1 second, FIRE
            setTimeout(function(){
                var newTime = new Date().getTime();
                if (!(fired) && (newTime - lastKeyPress > timeBeforeFire)){
                    populateAutocomplete(currentString, spinner, autocompleteList)
                    fired = true
                }
            }, timeBeforeFire + 50)
        }
        return true;
    });

    // On click user should be taken to the repo's results:
    $(".autocomplete-list").on("mousedown", ".search-auto-item", function(e){
        // Get the url from the data attribute: 
        var url = e.target.getAttribute("data-repo-url")
        // Input the url automatically:
       
        $(".search-bar input").val(url);
        $(".search-bar button").triggerHandler("click");
        return false;
    });

}


$(function() {
    handleAutocomplete()

    var history = new History();
    var historyIns = "<li class=\"search-auto-item\"><span></span><span class=\"search-history-remove\"></span></li>";
    history.value.items.forEach(function(e) {
        $(".search-auto ul.history-list").prepend(historyIns);
        $(".search-auto-item:first span:first").text(e.site + ".com/" + e.owner + "/" + e.repo);
    });

    $(".search-bar input").click(function(){
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
            // if($(".search-auto>ul.history-list>li").length > 0) {
            $(".search-auto").css("display", "inline").stop().fadeTo(100, 1);
            // }
        return false;
    });

    $(".search-bar input").blur(function() { 
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
        $(".search-auto").stop().fadeTo(100, 0, function(){ $(this).css("display", "none") });
        return false;
    });

    $(".search-bar input").keydown(function(e){
        $(".search-tips").stop().fadeTo(100, 0, function() { $(this).css("visibility", "hidden") });
        if(e.keyCode == 13) {
            $(".search-bar button").triggerHandler("click");
            return false;
        }
        return true;
    });

    $(".search-bar button").click(function(){
        var url = $(".search-bar input").val().trim();
        var regex =  /^(https?:\/\/|)(www.|)(github|gitlab)(.com|)\/([\d\w.-]+)\/([\d\w.-]+)(.git|)\/?$/i;
        var result = url.match(regex);
        if(result){
            var site = result[3].toLowerCase(), owner = result[5], repo = result[6];
            if(history.index(site, owner, repo) == -1){
                //add to history
                $(".search-auto ul.history-list").prepend(historyIns);
                $(".search-auto-item:first span:first").text(site + ".com/" + owner + "/" + repo);
                history.add(site, owner, repo);
                var itemNum = $(".search-auto>ul.history-list>li").length;
                if(itemNum > history.maxLength){
                    $(".search-auto-item:last").remove();
                }
            }
            window.location.href = "./result.html?site=" + site + "&owner=" + owner + "&repo=" + repo;
        }else{
            $(".search-tips").css("visibility", "visible").stop().fadeTo(100, 1);
        }
        return false;
    });

    $(".search-auto").on("mousedown", ".search-auto-item", function(){
        var itemNum = $(".search-auto>ul.history-list>li").length;
        var index = itemNum - $(this).index() - 1;
        var e = history.value.items[index];
        $(".search-bar input").val(e.site + ".com/" + e.owner + "/" + e.repo);
        $(".search-bar button").triggerHandler("click");
        return false;
    });

    $(".search-auto").on("mousedown", ".search-history-remove", function(){
        var $item = $(this).parent();
        var itemNum = $(".search-auto>ul.history-list>li").length;
        var index = itemNum - $item.index() - 1;
        history.remove(index);
        if(itemNum > 1){
            $item.remove(); 
        }else{
            $(".search-auto").stop().fadeTo(100, 0, function(){
                $item.remove();
            });
        }
        return false;
    });

    // setTimeout(function(){
    //     $(".search-bar input").click()
    // }, 200)
});