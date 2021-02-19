
function loadGitHubTrending(show) {
    var insertGitHub = "";
    insertGitHub += "<li class=\"item\">";
    insertGitHub += "    <div class=\"item-index\"></div>";
    insertGitHub += "    <div class=\"item-content\">";
    insertGitHub += "        <div><span class=\"item-content-name\"></span></div>";
    insertGitHub += "        <div class=\"item-content-description\"></div>";
    insertGitHub += "        <div class=\"item-content-stats\">";
    insertGitHub += "            <span class=\"item-content-stars\"\"></span>";
    insertGitHub += "            <span>stars</span>";
    insertGitHub += "            |";
    insertGitHub += "            <span class=\"item-content-forks\"></span>";
    insertGitHub += "            <span>forks</span>";
    insertGitHub += "            |";
    insertGitHub += "            <span class=\"item-content-stars-today\">2000</span>";
    insertGitHub += "            <span>stars today</span>";
    insertGitHub += "        </div>";
    insertGitHub += "    </div>";
    insertGitHub += "</li>";

    $.ajax({
        type: "GET",
        url: "https://api.gitrends.com/trending/github",
        success: function(result) {
            for(var i in result.trendingList) {
                var item = result.trendingList[i];
                $(".trending-github ul").append(insertGitHub);
                $(".trending-github .item-index:last").text(Number(i) + 1);
                $(".trending-github .item-content-name:last").text(item.owner + " / " + item.repo);
                $(".trending-github .item-content-description:last").text(item.description);
                $(".trending-github .item-content-stars:last").text(item.stars);
                $(".trending-github .item-content-forks:last").text(item.forks);
                $(".trending-github .item-content-stars-today:last").text(item.starsToday);
            }
            $(".trending-github .item:last").css("border", "none");
            
            $(".trending-github").on("click", ".item", function() {
                var i = $(this).index();
                var item = result.trendingList[i];
                analyzeUrl(item.url);
                return false;
            });
            if(show) {
                showGitHubTrending();
            } 
        },
        error: function(e) {
            console.log(e);
        }
    });
}

function loadGitLabTrending(show) {
    var insertGitLab = "";
    insertGitLab += "<li class=\"item\">";
    insertGitLab += "    <div class=\"item-index\"></div>";
    insertGitLab += "    <div class=\"item-content\">";
    insertGitLab += "        <div><span class=\"item-content-name\"></span></div>";
    insertGitLab += "        <div class=\"item-content-description\"></div>";
    insertGitLab += "        <div class=\"item-content-stats\">";
    insertGitLab += "            <span class=\"item-content-stars\"\"></span>";
    insertGitLab += "            <span>stars</span>";
    insertGitLab += "            |";
    insertGitLab += "            <span class=\"item-content-forks\"></span>";
    insertGitLab += "            <span>forks</span>";
    insertGitLab += "        </div>";
    insertGitLab += "    </div>";
    insertGitLab += "</li>";

    $.ajax({
        type: "GET",
        url: "https://api.gitrends.com/trending/gitlab",
        success: function(result) {
            for(var i in result.trendingList) {
                var item = result.trendingList[i];
                $(".trending-gitlab ul").append(insertGitLab);
                $(".trending-gitlab .item-index:last").text(Number(i) + 1);
                $(".trending-gitlab .item-content-name:last").text(item.owner + " / " + item.repo);
                $(".trending-gitlab .item-content-description:last").text(item.description);
                $(".trending-gitlab .item-content-stars:last").text(item.stars);
                $(".trending-gitlab .item-content-forks:last").text(item.forks);
            }
            $(".trending-gitlab .item:last").css("border", "none");
            $(".trending-gitlab").on("click", ".item", function() {
                var i = $(this).index();
                var item = result.trendingList[i];
                analyzeUrl(item.url);
                return false;
            });
            if(show) {
                showGitLabTrending();
            } 
        },
        error: function(e) {
            console.log(e);
        }
    });

}

function showGitHubTrending() {
    window.location.hash = "github"
    if($(".spinner").css("visibility") == "visible") {
        $(".spinner").stop().fadeTo(1000, 0, function() {
             $(this).css("visibility", "hidden");
             $(".trending-github").css("display", "block");
             $(".trending-gitlab").css("display", "none");
        });
    } else {
        $(".trending-github").css("display", "block");
        $(".trending-gitlab").css("display", "none");
    }
}

function showGitLabTrending() {
    window.location.hash = "gitlab"
    if($(".spinner").css("visibility") == "visible") {
        $(".spinner").stop().fadeTo(1000, 0, function() {
             $(this).css("visibility", "hidden");
             $(".trending-gitlab").css("display", "block");
             $(".trending-github").css("display", "none");
        });
    } else {
        $(".trending-gitlab").css("display", "block");
        $(".trending-github").css("display", "none");
    }
}

function analyzeUrl(url) {
    var regex =  /^(https?:\/\/|)(www.|)(github|gitlab)(.com|)\/([\d\w.-]+)\/([\d\w.-]+)(.git|)\/?$/i;
        var result = url.match(regex);
        var site = result[3].toLowerCase(), owner = result[5], repo = result[6];
        var newUrl = "./result.html?site=" + site + "&owner=" + owner + "&repo=" + repo;
        window.open(newUrl, "_blank");
}


$(function() {
    if(window.location.hash == "#gitlab") {
        loadGitHubTrending(false);
        loadGitLabTrending(true);
    } else {
        loadGitHubTrending(true);
        loadGitLabTrending(false);
    }
    
    
    $(".trending-title-github").click(function() {
        showGitHubTrending();
        return false;
    });
    $(".trending-title-gitlab").click(function() {
        showGitLabTrending();
        return false;
    });
});