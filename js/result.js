
function getParam(name){
    var regex = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var result = window.location.search.substr(1).match(regex);
    return result != null ? unescape(result[2]) : undefined;
}

function initChart(watchers, stars, forks){
    new Chart($("#myChart"), {
        type: 'pie',
        data: {
            labels: ["Watchers", "Stars", "Forks"],
            datasets: [{
                label: '# of Votes',
                data: [watchers, stars, forks],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(255, 206, 86, 0.8)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)',
                    'rgba(255, 206, 86, 1)'
                ],
                borderWidth: 0,
                hoverBorderWidth: 1
            }]
        },
        options: {
            legend: {
                position: "bottom"
            }
        }
    });
}

var site = getParam("site");
var owner = getParam("owner");
var repo = getParam("repo");
var forks, stars, watchers, description;

function getGitHubStats(onGetStatsSuccess, onGetStatsException) {
    $.ajax({
        type: "GET",
        url: "https://api.github.com/repos/" + owner + "/" + repo,
        success: function(result){
            onGetStatsSuccess(result);
        },
        error: function(e){
            onGetStatsException(e);
        }
    });
}

function getGitLabStats(onGetStatsSuccess, onGetStatsException) {
    $.ajax({
        type: "GET",
        url: "https://api.gitrends.com/stats/" + site + "/" + owner + "/" + repo,
        success: function(result){
            onGetStatsSuccess(result);
        },
        error: function(e){
            onGetStatsException(e);
        }
    });
}

function getTweets() {
    $(".result-twitter .spinner").css("visibility", "visible");
    $(".comments").css("display", "none");
    var insertComments = "<li class=\"comment\"></li>";
    $.ajax({
        type: "GET",
        url: "https://api.gitrends.com/analysis/twitter/" + repo,
        success: function(result){
            for(var i in result.commentList) {
                var item = result.commentList[i];
                $(".comments").append(insertComments);
                $(".comments .comment:last").text(item.text);
            }
            $(".comments .comment:last").css("border", "none");
            $(".result-twitter .spinner").stop().fadeTo(1000, 0, function() {
                $(this).css("visibility", "hidden");
                $(".comments").css("display", "block");
           });
        },
        error: function(e){
            console.log(e);
        }
    });
}


var onShow = function() {
    $(".content").css("filter", "none");
    initChart(watchers, stars, forks);
}

var onGetStatsSuccess = function(result) {
    forks = result.forks;
    stars = result.stargazers_count;
    watchers = result.subscribers_count;
    description = result.description == null || result.description == "" ? "No description" : result.description;
    $("#name").text(repo);
    $("#owner").text(owner);
    $("#watchers").text(watchers);
    $("#stars").text(stars);
    $("#forks").text(forks);
    $("#description").text(description);
    $(".content").css("filter", "blur(2px)");
    $(".mask").stop().fadeTo(1000, 0, function() {
        $(this).css("visibility", "hidden");
        onShow();
    });
    getTweets();
}

var onGetStatsException = function(e) {
    console.log(e);
    $(".mask .loading").stop().fadeTo(800, 0, function(){
        $(this).css("visibility", "hidden");
        $(".mask .error").css("visibility", "visible"); 
    });
}

$(function() {
    $(".result-title a").text(owner + "/" + repo);
    if(site == "github"){
        $(".result-title a").attr("href", "https://github.com/" + owner + "/" + repo);
        $(".result-title span").text("GitHub");
        getGitHubStats(onGetStatsSuccess);
    }else if(site == "gitlab"){
        $(".result-title a").attr("href", "https://gitlab.com/" + owner + "/" + repo);
        $(".result-title span").text("GitLab");
        getGitLabStats(onGetStatsSuccess);
    } else {
        $(".mask").stop().fadeTo(1000, 0, function() {
            $(this).css("visibility", "hidden");
            $(".content").css("filter", "none");
            initChart(12, 44, 77);
        });
    }
    
    $(".mask .error button").click(function() {
        window.location.href = "./";
    })
});


