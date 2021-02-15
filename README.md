## Start node-red server

```bash
node-red --userDir ./front-end/src/
```

> **Notice: directory "node-red" is removed.**



## app is now running on [http://localhost:1880/](http://localhost:1880/)



## server back-end is on [http://localhost:1880/admin](http://localhost:1880/admin)

> **For security reasons you need to login to access server backend.**
>
> username: gitrends
>
> password: bristol

**Since front-end is now static, I recommend to use node-red as a router to host static contents, the router is already set up, so you don't need change the staff on node-red anymore.**

**The static contents is in ./front-end/src/static, you can edit them. By the time you read this, I've already set up another server providing mock API for front-end developing, you can interact with API server using ajax. The API document is at the end of document.**

node-red doesn't support relative path, so I used some trick to load static files in relative path, don't use file node in node-red, they only support absolute path!



## public webpage is on  [https://gitrends.com/](https://gitrends.com/)

> It is using node-red and also running in a docker container, and I also get a SSL certificate for it, so it is under https protocol using 443 port now. Since node-red doesn't support neither port forwarding nor enabling http and https at the same time, our page now only support https, using http will take you to 404.



## API server is on [http://api.gitrends.com/](http://api.gitrends.com/)

**This will be the mock API for the front-end to mock up while back-end is developing, it is well defined. You can choose the data you find useful.**

Hope this will be helpful for front-end development.



## API documentation

**API is divided to three part: stats, analysis, trending**

**All HTTP Request Methods are all using GET Methods**

**Any exception (e.g. repo not exists) will response with a status code of 404, so you can handle any exception/error in ajax error function**

### stats

**stats is used to get the stats or information of a repo from github/gitlab.**

```
Method: GET
URL: 	http://api.gitrends.com/stats/(github|gitlab)/{owner}/{repo}
```

Example:

```
Method: GET
URL: 	http://api.gitrends.com/stats/github/axios/axios
```

Example jquery using ajax:

```javascript
$.ajax({
        type: "GET",
        url: "http://api.gitrends.com/stats/github/axios/axios",
        success: function(result){
            console.log(result);
        },
        error: function(e){
            //handle exception/error here
            console.log(e);
        }
    });
```

Response json

```json
{
    "site": "github",
    "owner": "axios",
    "repo": "axios",
    "watchers": 100,
    "stars": 200,
    "forks": 300
}
```

### analysis

**analysis is used to get the sentiment analysis result for the social media.**

```
Method: GET
URL: 	http://api.gitrends.com/analysis/(twitter|stackoverflow)/{repo}
```

Example:

```
Method: GET
URL: 	http://api.gitrends.com/analysis/stackoverflow/axios
```

Response json

```json
{
    "site": "stackoverflow",
    "repo": "axios",
    "commentList": [
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        },
        {
            "site": "stackoverflow",
            "repo": "axios",
            "comment": "example comment for axios",
            "positive": 0.6,
            "negative": 0.314
        }
    ]
}
```

### Trending

**trending will show trending repos in github or gitlab. **

it will be exactly same data as [https://github.com/trending](https://github.com/trending) in the future.

```
Method: GET
URL: 	http://api.gitrends.com/trending/(github|gitlab)
```

Example:

```
Method: GET
URL: 	http://api.gitrends.com/trending/github
```

Response json

```json
{
    "site": "github",
    "trendingList": [
        {
            "site": "github",
            "owner": "example owner1",
            "repo": "example repo1",
            "description": "example description1",
            "stars": 100,
            "starsToday": 10,
            "forks": 1000
        },
        {
            "site": "github",
            "owner": "example owner2",
            "repo": "example repo2",
            "description": "example description2",
            "stars": 200,
            "starsToday": 20,
            "forks": 2000
        },
        {
            "site": "github",
            "owner": "example owner3",
            "repo": "example repo3",
            "description": "example description3",
            "stars": 300,
            "starsToday": 30,
            "forks": 3000
        },
        {
            "site": "github",
            "owner": "example owner4",
            "repo": "example repo4",
            "description": "example description4",
            "stars": 400,
            "starsToday": 40,
            "forks": 4000
        },
        {
            "site": "github",
            "owner": "example owner5",
            "repo": "example repo5",
            "description": "example description5",
            "stars": 500,
            "starsToday": 50,
            "forks": 5000
        },
        {
            "site": "github",
            "owner": "example owner6",
            "repo": "example repo6",
            "description": "example description6",
            "stars": 600,
            "starsToday": 60,
            "forks": 6000
        },
        {
            "site": "github",
            "owner": "example owner7",
            "repo": "example repo7",
            "description": "example description7",
            "stars": 700,
            "starsToday": 70,
            "forks": 7000
        },
        {
            "site": "github",
            "owner": "example owner8",
            "repo": "example repo8",
            "description": "example description8",
            "stars": 800,
            "starsToday": 80,
            "forks": 8000
        },
        {
            "site": "github",
            "owner": "example owner9",
            "repo": "example repo9",
            "description": "example description9",
            "stars": 900,
            "starsToday": 90,
            "forks": 9000
        },
        {
            "site": "github",
            "owner": "example owner10",
            "repo": "example repo10",
            "description": "example description10",
            "stars": 1000,
            "starsToday": 100,
            "forks": 10000
        }
    ]
}
```



















