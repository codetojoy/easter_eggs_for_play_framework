$ ->
  $.get "/players", (players) ->
    $.each players, (index, player) ->
      $("#players").append $("<li>").text player.username
