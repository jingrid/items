/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: DE
 */
jQuery.extend(jQuery.validator.messages, {
	required: "必选字段",
	maxlength: jQuery.validator.format("输入不得大于 {0} 个字符"),
	minlength: jQuery.validator.format("输入不得小于 {0} 个字符"),
	rangelength: jQuery.validator.format("输入一个在 {0} 和 {1} 个字符长度之间的值"),
	email: "请输入一个有效的电子邮件地址",
	url: "请输入一个有效的URL",
	date: "请输入一个有效的日期",
	number: "请输入一个有效的数字",
	digits: "请输入一个有效的浮点数字",
	equalTo: "请再次输入同样的值",
	range: jQuery.validator.format("请输入一个 {0} 和 {1} 之间的值"),
	max: jQuery.validator.format("请输入一个小于或等于 {0} 的值"),
	min: jQuery.validator.format("请输入一个大于或等于 {0} 的值"),
	creditcard: "请输入一个有效的信用卡卡号"
});