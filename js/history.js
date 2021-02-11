function History() {
    var history = window.localStorage["history"];
    var save = function(){
        window.localStorage["history"] = JSON.stringify(history);
    };
    var init = function(){
        history = {};
        history.items = [];
        save(history);
    }
    if(history == undefined){
        init();
    }else{
        history = JSON.parse(history);
    }
    this.value = history;
    this.maxLength = 8;
    this.add = function(site, owner, repo){
        history.items.push({ "site": site, "owner": owner, "repo": repo });
        if(history.items.length > this.maxLength){
            history.items.splice(0, 1); //移除队首元素
        }
        save();
    };
    this.remove = function(index){
        history.items.splice(index, 1);
        save();
    }
    this.clear = function(){
        init();
    }
    this.index = function(site, owner, repo){
        for(let i = 0; i < history.items.length; i++){
            e = history.items[i];
            if(e.site.toLowerCase() == site.toLowerCase() &&
                    e.owner.toLowerCase() == owner.toLowerCase() &&
                    e.repo.toLowerCase() == repo.toLowerCase()){
                return i;
            }
        }
        return -1;
    }
}