/*
	主页加载方法
	@eric
*/
//系统时间显示
setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
var setting = {
	data: {
		simpleData: {
			enable: true
		}
	},
	view: {
		selectedMulti: false
	},
	callback: {
		onClick:function(e, id, node){
			var zTree = $.fn.zTree.getZTreeObj("menuTree");
			if(node.isParent) {
				zTree.expandNode();
			} else {
				addTabs(node.name, node.file);
			}
		}
	}
};

var zNodes =[
	{ id:1, pId:0, name:"系统管理", open:true,iconOpen:"images/setting.png", iconClose:"images/user.png"},
	{ id:11, pId:1, name:"用户管理", file:"http://localhost:8080/GatherDemo_v2/sys/userIndex.do",icon:"images/user.png"},
	{ id:12, pId:1, name:"数据备份", file:"backup.html",icon:"images/database.png"},
	{ id:13, pId:1, name:"权限管理", file:"authority.html"},
	{ id:14, pId:1, name:"角色管理", file:"http://localhost:8080/GatherDemo_v2/sys/role/index.do"},
	{ id:2, pId:0, name:"父节点", open:false},
	{ id:21, pId:2, name:"子节点21", file:""},
	{ id:22, pId:2, name:"子节点22", file:""},
	{ id:23, pId:2, name:"子节点23", file:""},
	{ id:24, pId:2, name:"子节点211", file:""},
	{ id:25, pId:2, name:"子节点212", file:""},
	{ id:26, pId:2, name:"子节点213", file:""},
	{ id:27, pId:2, name:"子节点221", file:""},
	{ id:28, pId:2, name:"子节点222", file:""},
	{ id:29, pId:2, name:"子节点233", file:""},
	{ id:20, pId:2, name:"子节点241", file:""},
	{ id:25, pId:2, name:"子节点252", file:""},
	{ id:26, pId:2, name:"子节点263", file:""}
];

$(function() {
	$.fn.zTree.init($("#menuTree"), setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("menuTree");
	
	//中间部分tab
	$('#tabs').tabs({  
		border:false,
		fit:true,
		onSelect: function(title, index){
			var treeNode = zTree.getNodeByParam("name", title, null);
			zTree.selectNode(treeNode);
		}
	}); 
	
	$('.index_panel').panel({  
	  width:300,  
	  height:200,  
	  closable:true,
	  minimizable:true,
	  title: 'My Panel'
	});
	
});

//添加一个选项卡面板 
function addTabs(title, url, icon){
	if(!$('#tabs').tabs('exists', title)){
		$('#tabs').tabs('add',{  
			title:title,  
			content:'<iframe src="'+url+'" frameBorder="0" border="0" scrolling="no" style="width: 100%; height: 100%;"/>',  
			closable:true
		});
	} else {
		$('#tabs').tabs('select', title);
	}
}