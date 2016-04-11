/** easyui校验规则 **/
$.extend($.fn.validatebox.defaults.rules, {
	/**汉字**/
    chs: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字'
    },
	/**邮政编码**/
    zipcode: {
        validator: function (value, param) {
            return /^[1-9]\d{5}$/.test(value);
        },
        message: '邮政编码不正确'
    },
    /**手机号**/
    mobile: {
    	validator: function (value, param) {
    		return /^((\(\d{2,3}\))|(\d{3}\-))?1(3|5|8)\d{9}$/.test(value);
    	},
    	message: '手机号码不正确'
    },
	/**座机号**/
    tel: {
        validator: function (value, param) {
        	 return /^((0\d{2,3})-)(\d{7,8})$/.test(value); 
        },
        message: '座机号码不正确,格式如：020-12345678'
    },
	/**字符**/
    varchar: {
        validator: function (value, param) {
            return /^[\u0391-\uFFE5\w]+$/.test(value);
        },
        message: '登录名称只允许汉字、英文字母、数字及下划线。'
    },
	/**数字**/
    digit: {
        validator: function (value, param) {
            return /^\d+$/.test(value);
        },
        message: '请输入数字'
    },
    /**数值**/
    number: {
    	validator: function (value, param) {
    		return !isNaN(value);
    	},
    	message: '请输入数值'
    },
	/**正的浮点数数值**/
    float: {
        validator: function (value, param) {
        	//输入的是数字且值小于等于0则提示错误
        	if(!isNaN(value) && value.valueOf()<=0)//是数字且是正数 
        	{ 
        		return isNaN(value);
        	}else {//输入的是非数字直接提示错误
        		return !isNaN(value);
        	}
        },
        message: '请输入正数'
    },
	/**身份证**/
    idcard: {
        validator: function (value, param) {
            return ((function (value) {
			    if (value.length == 18 && 18 != value.length) return false;
			    var number = value.toLowerCase();
			    var d, sum = 0, v = '10x98765432', w = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2), a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
			    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
			    if (re == null || a.indexOf(re[1]) < 0) return false;
			    if (re[2].length == 9) {
			        number = number.substr(0, 6) + '19' + number.substr(6);
			        d = ['19' + re[4], re[5], re[6]].join('-');
			    } else d = [re[9], re[10], re[11]].join('-');
			    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
			    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
			    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
			})(value));
        },
        message:'请输入正确的身份证号码'
    },
	/**安全的密码**/
    safepswd: {
    	validator:((function (value) {
			    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
			})()),
		message:'请输入安全的密码'
    },
    /** 时间开始结束校验，读取beginends数组变量， 若不存在则默认值为[['beginTimeBox','endTimeBox']] **/
    beginend: {
    	validator: function (value, param) {
    		var bes = null;
    		if(typeof(beginends) != 'undefined' && $.isArray(beginends)) bes = beginends[param?param:0];
    		if(bes == null) bes = ["#beginTimeBox","#endTimeBox"];
    		if(bes == null || bes.length != 2) return true;
    		var ob = $(bes[0]), oe = $(bes[1]);
    		if(ob.length != 1 || !ob.is('.datebox-f') || oe.length != 1 || !oe.is('.datebox-f')) return true;
    		var vb = ob.datebox('getValue');
    		var ve = oe.datebox('getValue');
    		if(vb && ve && vb > ve) return false;
            return true;
        },
		message:'结束时间必须在开始时间之后'
    },
    comboVry: {
        validator: function (value, param) {//param为默认值
            return !(value == "请选择");
        },
        message: '请选择'
    }
});

/** 是否时间格式 **/
function isDateTime(format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) {if (f3[i] != f4[i]) return false;}
    for (var j = 0; j < len; j++) o[f1[j]] = f2[j];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
}
