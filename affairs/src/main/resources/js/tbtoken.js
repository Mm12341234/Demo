$(function(){
    vm.init();
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        tbToken:{},
        userIds:[],
        page:{
            pageTotal:0,
            pageNum:1,
            pageSize:10
        },
        columns: [
           {
                title: '',
                width: 60,
                align: 'center',
                type: 'selection'
            },
            {
                title: 'userId',
                key: 'userId',
                align: 'center'
            },
            {
                title: 'token',
                key: 'token',
                align: 'center'
            },
            {
                title: 'expireTime',
                key: 'expireTime',
                align: 'center'
            },
            {
                title: 'updateTime',
                key: 'updateTime',
                align: 'center'
            }
        ],
        data:[],
        q: {
            name: ''
        },
        ruleValidate:{
        }
    },
    methods: {
        init:function(){
            var url="tbtoken/list";
            var params={
                page:{pageNum:vm.page.pageNum,pageSize:vm.page.pageSize},
                map:{}
            };
            $.ajax({
                url:url,
                type:"POST",
                data:JSON.stringify(params),
                contentType:"application/json",
                success:function(data){
                    vm.data=data.page.list;
                    vm.page.pageTotal=data.page.totalCount;
                }
            })
        },
        query: function () {
			vm.reload();
		},
		add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.tbToken = {};
		},
		update: function (event) {
			if (vm.userIds.length == 0||vm.userIds.length > 1) {
                alert("请选择一条数据！");
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(vm.userIds[0]);
		},
		saveOrUpdate: function (event) {
            var url = vm.tbToken.userId == null ? "../tbtoken/save" : "../tbtoken/update";
            $.ajax({
			    url: url,
                data: JSON.stringify(vm.tbToken),
                type: "POST",
			    contentType: "application/json",
                success: function (r) {
                    alert('操作成功', function (index) {
                        vm.reload();
                    });
                }
			});
		},
		del: function (event) {
			if (vm.userIds.length == 0) {
                alert("请选择一条数据！");
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                 $.ajax({
				    url: "../tbtoken/delete",
                    data: JSON.stringify(vm.userIds),
                    type: "POST",
				    contentType: "application/json",
                    success: function () {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
					}
				});
			});
		},
		getInfo: function(userId){
            $.ajax({
                type: "POST",
                url: "../tbtoken/info/"+userId,
                dataType: "json",
                success: function(r) {
                    vm.tbToken = r.tbToken;
                }
            })
		},
		reload: function (event) {
            vm.showList = true;
            vm.userIds=[];
             for(var i=0;i<vm.data.length-1;i++){
                this.$refs.selection.objData[i]._isChecked = false;
            }
		},
        reloadSearch: function() {

        },
        handleSubmit: function (name) {
             vm.saveOrUpdate();
        },
        handleReset: function (name) {

        },
        //点击页码
        handlePage:function(value){
            vm.page.pageNum=value;
            vm.init();
        },
        handlePageSize:function(value){
            vm.page.pageSize=value;
            vm.init();
        },
        //点击复选框事件
        handleSelectRow:function(event){
            vm.userIds=[];
            for(var i=0;i<event.length;i++){
                vm.userIds.push(event[i].userId);
            }
        }
    }
});