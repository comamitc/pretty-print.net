define(['app/js/top-bar',
  'app/js/page-content'
],
function(topBar, pageContent) {
  //TODO;
  var parentId = "";

  function init() {
    topBar.render();
  }

  return {
    init: init
  };

});