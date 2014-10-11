// leanModal v1.0 by Ray Stone - http://finelysliced.com.au
(function($) {
    $.fn.extend({
        leanModal : function(options) {
            var defautOptions = {
                top : 100,
                overlay : 0.5
            };
            options = $.extend(defautOptions, options);
            return this.each(function() {
                var o = options;
                $(this).click(function(e) {
                    var maskDiv = $("<div id='lean_overlay'></div>");
                    var box = $(this).attr("href");
                    $("body").append(maskDiv);
                    $("#lean_overlay").click(function() {
                        show(box);
                    });
                    var boxHeight = $(box).outerHeight();
                    var boxWidth = $(box).outerWidth();
                    $("#lean_overlay").css({
                        "display" : "block",
                        opacity : 0
                    });
                    $("#lean_overlay").fadeTo(200, o.overlay);
                    $(box).css({
                        "display" : "block",
                        "position" : "fixed",
                        opacity : 0,
                        "z-index" : 11000,
                        "left" : 50 + "%",
                        "margin-left" : -(boxWidth / 2) + "px",
                        "top" : o.top + "px"
                    });
                    $(box).fadeTo(200, 1);
                    e.preventDefault();
                });
            });
            function show(box) {
                $("#lean_overlay").fadeOut(200);
                $(box).css({
                    "display" : "none"
                });
            };
        }
    });
})(jQuery); 