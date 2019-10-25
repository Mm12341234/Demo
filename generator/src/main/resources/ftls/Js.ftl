$(function(){
    vm.init();
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        ${EntityName}:{},
        ${PrimaryKey.attrname}s:[],
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
            <#list columns! as column>
            {
                title: '${column.attrname}',
                key: '${column.attrname}',
                align: 'center'
            }<#if column_has_next>,</#if>
            </#list>
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
            var url="${pathName}/list";
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
            vm.${EntityName} = {};
		},
		update: function (event) {
			if (vm.${PrimaryKey.attrname}s.length == 0||vm.${PrimaryKey.attrname}s.length > 1) {
                alert("请选择一条数据！");
				return;
			}
			vm.showList = false;
            vm.title = "修改";

            vm.getInfo(vm.${PrimaryKey.attrname}s[0]);
		},
		saveOrUpdate: function (event) {
            var url = vm.${EntityName}.${PrimaryKey.attrname} == null ? "../${pathName}/save" : "../${pathName}/update";
            $.ajax({
			    url: url,
                data: JSON.stringify(vm.${EntityName}),
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
			if (vm.${PrimaryKey.attrname}s.length == 0) {
                alert("请选择一条数据！");
				return;
			}

			confirm('确定要删除选中的记录？', function () {
                 $.ajax({
				    url: "../${pathName}/delete",
                    data: JSON.stringify(vm.${PrimaryKey.attrname}s),
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
		getInfo: function(${PrimaryKey.attrname}){
            $.ajax({
                type: "POST",
                url: "../${pathName}/info/"+${PrimaryKey.attrname},
                dataType: "json",
                success: function(r) {
                    vm.${EntityName} = r.${EntityName};
                }
            })
		},
		reload: function (event) {
            vm.showList = true;
            vm.${PrimaryKey.attrname}s=[];
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
            vm.${PrimaryKey.attrname}s=[];
            for(var i=0;i<event.length;i++){
                vm.${PrimaryKey.attrname}s.push(event[i].${PrimaryKey.attrname});
            }
        }
    }
});