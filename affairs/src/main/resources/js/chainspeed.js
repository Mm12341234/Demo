$(function(){
    vm.init();
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        chainspeed:{},
        ids:[],
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
                title: 'platform',
                key: 'platform',
                align: 'center'
            },
            {
                title: 'productionDate',
                key: 'productionDate',
                align: 'center'
            },
            {
                title: 'edit',
                key: 'edit',
                align: 'center'
            },
            {
                title: 'isuse',
                key: 'isuse',
                align: 'center'
            },
            {
                title: 'id',
                key: 'id',
                align: 'center'
            },
            {
                title: 'line',
                key: 'line',
                align: 'center'
            },
            {
                title: 'lineId',
                key: 'lineId',
                align: 'center'
            },
            {
                title: 'farmId',
                key: 'farmId',
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
            var url="chainspeed/list";
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
            vm.chainspeed = {};
		},
		update: function (event) {
			if (vm.ids.length == 0||vm.ids.length > 1) {
                alert("请选择一条数据！");
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(vm.ids[0]);
		},
		saveOrUpdate: function (event) {
            var url = vm.chainspeed.id == null ? "../chainspeed/save" : "../chainspeed/update";
            $.ajax({
			    url: url,
                data: JSON.stringify(vm.chainspeed),
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
			if (vm.ids.length == 0) {
                alert("请选择一条数据！");
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                 $.ajax({
				    url: "../chainspeed/delete",
                    data: JSON.stringify(vm.ids),
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
		getInfo: function(id){
            $.ajax({
                type: "POST",
                url: "../chainspeed/info/"+id,
                dataType: "json",
                success: function(r) {
                    vm.chainspeed = r.chainspeed;
                }
            })
		},
		reload: function (event) {
            vm.showList = true;
            vm.ids=[];
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
            vm.ids=[];
            for(var i=0;i<event.length;i++){
                vm.ids.push(event[i].id);
            }
        }
    }
});