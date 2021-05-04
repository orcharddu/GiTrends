function handle_cookies(){
  var cookie_name = 'cookie_consent'
  var cookie_lifespan = 365 // Days

  // Sets the cookie on the user's browser:
  var set_cookie = function (cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
  }

  var get_cookie = function (cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
      var c = ca[i];
      while (c.charAt(0) == ' ') {
        c = c.substring(1);
      }
      if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
      }
    }
    return "";
  };

  // Show the cookie modal popup if the acceptance cookie hasn't been set.
  if (!(get_cookie(cookie_name))) {
    $('#cookie-pop-up').modal('show');
  }
 
  // Modal dismiss btn - consent
  $('#cookieConsent').on('click', function () {
    set_cookie(cookie_name, 1, cookie_lifespan);
  });
}

$(function() {
  $("#cookie_modal").load("./cookie_modal.html", handle_cookies)
})