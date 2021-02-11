
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


$(function() {
   
    var site = getParam("site");
    var owner = getParam("owner");
    var repo = getParam("repo");
    var forks, stars, watchers;
    $(".result-title a").text(owner + "/" + repo);
    if(site == "github"){
        $(".result-title a").attr("href", "https://github.com/" + owner + "/" + repo);
    }else if(site == "gitlab"){
        $(".result-title a").attr("href", "https://gitlab.com/" + owner + "/" + repo);
    }
    

    // $(".content").css("filter", "blur(2px)");
    // $(".mask").stop().fadeTo(1000, 0, function() {
    //     $(this).css("visibility", "hidden");
    //     $(".content").css("filter", "none");
    //     initChart(watchers, stars, forks);
    // });

    $.ajax({
        type: "GET",
        url: "https://api.github.com/repos/" + owner + "/" + repo,
        success: function(result){
            forks = result.forks;
            stars = result.stargazers_count;
            watchers = result.subscribers_count;
            $("#name").text(repo);
            $("#owner").text(owner);
            $("#watchers").text(watchers);
            $("#stars").text(stars);
            $("#forks").text(forks);
            $(".content").css("filter", "blur(2px)");
            $(".mask").stop().fadeTo(1000, 0, function() {
                $(this).css("visibility", "hidden");
                $(".content").css("filter", "none");
                initChart(watchers, stars, forks);
            });
        },
        error: function(e){
            console.log(e);
            $(".mask .loading").stop().fadeTo(800, 0, function(){
                $(this).css("visibility", "hidden");
                $(".mask .error").css("visibility", "visible"); 
            });
        }
     });
     
     $(".mask .error button").click(function() {
        window.location.href = "./";
    })
});




