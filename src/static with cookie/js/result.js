
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
                    'rgba(255, 99, 132, 1)',
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
            },
            plugins: {
                datalabels: {
                    // hide datalabels for all datasets
                    display: false
                }
            }        
        },
    });
}

function initSentimentGauge(sentimentValue){
    var chart = new Chart($("#sentimentGauge"), {
      type: 'gauge',
      data: {
        labels: ["Neutral", "Positive"],
        datasets: [{
          value: (3*(sentimentValue+1))/2,
          data: [1.5, 3],
          backgroundColor: ['rgba(255, 206, 86, 1)', 'rgba(126, 178, 109, 1)'],
        }]
      },
      options: {
        needle: {
          radiusPercentage: 2,
          widthPercentage: 3.2,
          lengthPercentage: 80,
          color: 'rgba(0, 0, 0, 1)'
        },
        valueLabel: {
            display: false,
        },
        plugins: {
          datalabels: {
            display: true,
            formatter:  function (value, context) {
              return context.chart.data.labels[context.dataIndex];
            },
            //color: function (context) {
            //  return context.dataset.backgroundColor;
            //},
            color: 'rgba(0, 0, 0, 1.0)',
            //color: 'rgba(255, 255, 255, 1.0)',
            backgroundColor: null,
            font: {
              size: 12,
              weight: 'bold'
            }
          }
        }
        // valueLabel: {
        //   display: true,
        //   formatter: (value) => {
        //     return '$' + Math.round(value);
        //   },
        //   color: 'rgba(255, 255, 255, 1)',
        //   backgroundColor: 'rgba(0, 0, 0, 1)',
        //   borderRadius: 5,
        //   padding: {
        //     top: 10,
        //     bottom: 10
        //   }
        // },       
      }
    });
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

function initCategories(categories){
    // First sort the categories based on the score they received:
    categories.sort(function(category1, category2) {
        if (category1.score < category2.score){
            return 1;
        }
        if (category1.score > category2.score) {
            return -1;
        }
        return 0;
    })

    var finalCategories = []
    for (var x=0; x < categories.length; x++){
        var subCategories = categories[x].labelSegments
        for (var y=0; y < subCategories.length; y++){
            var category = capitalizeFirstLetter(subCategories[y])
            if (!(finalCategories.includes(category))){
                finalCategories.push(category)
            }
        }
    }

    // Limit to maximum 5 categories:
    finalCategories = finalCategories.slice(0, 5)

    insertCategoriesHTML = ""
    for (var z=0; z < finalCategories.length; z++){
        insertCategoriesHTML += "<li class=\"category\">" + finalCategories[z] + "</li>"
    }
    $(".categories").append(insertCategoriesHTML)    
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
            watchers = result.subscribers_count;
            stars = result.stargazers_count;
            forks = result.forks;
            description = result.description == null || result.description == "" ? "No description" : result.description;
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
        url: API_URL + "stats/" + site + "/" + owner + "/" + repo,
        success: function(result){
            watchers = result.watchers;
            stars = result.stars;
            forks = result.forks;
            description = result.description == null || result.description == "" ? "No description" : result.description;
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
        url: API_URL + "analysis/twitter/" + repo,
        success: function(result){
            if(result.sentiment == undefined){
                //related tweets or sentiment not exists
                var alert_text = `No related tweets found for "${repo}" repository.`
                $(".no-tweets-alert").text(alert_text).removeClass("hidden")
                $("#analysis-section").addClass("hidden")
                
            } else {
                initSentimentGauge(result.sentiment.score);
                initCategories(result.categories)
                for(var i in result.commentList) {
                    var item = result.commentList[i];
                    $(".comments").append(insertComments);
                    $(".comments .comment:last").text(item.text);
                }
                $(".comments .comment:last").css("border", "none");
            }
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
        getGitHubStats(onGetStatsSuccess, onGetStatsException);
    }else if(site == "gitlab"){
        $(".result-title a").attr("href", "https://gitlab.com/" + owner + "/" + repo);
        $(".result-title span").text("GitLab");
        getGitLabStats(onGetStatsSuccess, onGetStatsException);
    }
    //  else {
    //     $(".mask").stop().fadeTo(1000, 0, function() {
    //         $(this).css("visibility", "hidden");
    //         $(".content").css("filter", "none");
    //         initChart(12, 44, 77);
    //     });
    // }
    
    $(".mask .error button").click(function() {
        window.location.href = "./";
    })
});


