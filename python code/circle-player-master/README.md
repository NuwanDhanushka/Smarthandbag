# circle-player
This is a very simple html5 circle audio player. Not a lot of features, and it relies heavily on browser html5 compatibility, but super easy to implement. Simply include one js file, one css file, and one line of jQuery to initialize.

I created it because I was tired of all of the messy html5 media players that are rediculously overcomplicated. Because of that this might be rediculously undercomplicated and missing the features you want.

# Requires
1. This is a jQuery plugin and therefore requires jQuery. https://jquery.com/
2. It also uses font awesome. This is not totally necessary and could be removed, but most people already use and so its easier this way. https://fortawesome.github.io/Font-Awesome/

Both of these are also available from cdns because they are so popular. See the demo for details.

# Include
Include these in the html head
```
<link rel="stylesheet" type="text/css" href="circle-player.css"/>
<script src="circle-player.js"></script>
```

# Initialize
Use a jquery selector to get a container to place the player in. The call the CirclePlayer function passing the url to the audio file.
```
$('#id-of-container').CirclePlayer("http://foo.bar/path/to/audio.file");
```
