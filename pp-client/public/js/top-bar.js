define(['jquery',
  'lodash',
  'mustache',
  'app/js/templates'
], function($, _, Mustache, templates) {

  var parentId = "#top-bar",
    template = templates['top-bar'];

  function render() {
    var markup = Mustache.to_html(template);
    $(parentId).html(markup);
  }

  return {
    render: render
  };
});