var Menu = function() {};
Menu.prototype.addListener = function(content, callback, fail) {
	return PhoneGap.exec( function(args) {
		callback(args);
	}, function(args) {
		fail(args);
	}, 'Menu', 'addListener', [content]);
};
PhoneGap.addConstructor(function() {
	PhoneGap.addPlugin('Menu', new Menu());
});