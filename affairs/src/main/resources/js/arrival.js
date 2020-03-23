$(function(){
    vm.init();
});
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        arrival:{},
        partList:[],
        demandNum:'',
        arrivalNum:'',
        name:'',
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
                title: 'id',
                key: 'id',
                align: 'center',
                width:110
            },
            {
                title: 'ncome',
                key: 'ncome',
                align: 'center',
                width:120
            },
            {
                title: 'receiptplace',
                key: 'receiptplace',
                align: 'center',
                width:120
            },
            {
                title: 'parts',
                key: 'parts',
                align: 'center',
                width:120
            },
            {
                title: 'color',
                key: 'color',
                align: 'center',
                width:120
            },
            {
                title: 'plancode',
                key: 'plancode',
                align: 'center',
                width:120
            },
            {
                title: 'receivingDate',
                key: 'receivingDate',
                align: 'center',
                width:120
            },
            {
                title: 'receivingTime',
                key: 'receivingTime',
                align: 'center',
                width:120
            },
            {
                title: 'orderQuantity',
                key: 'orderQuantity',
                align: 'center',
                width:120
            },
            {
                title: 'quantityAdjusted',
                key: 'quantityAdjusted',
                align: 'center',
                width:120
            },
            {
                title: 'completionDate',
                key: 'completionDate',
                align: 'center',
                width:120
            },
            {
                title: 'summonsnumber',
                key: 'summonsnumber',
                align: 'center',
                width:120
            },
            {
                title: 'summonsPublication',
                key: 'summonsPublication',
                align: 'center',
                width:120
            },
            {
                title: 'arrivalRules',
                key: 'arrivalRules',
                align: 'center',
                width:120
            },
            {
                title: 'orderPackingNumber',
                key: 'orderPackingNumber',
                align: 'center',
                width:120
            },
            {
                title: 'dropPoint',
                key: 'dropPoint',
                align: 'center',
                width:120
            },
            {
                title: 'tailProcessingDistinguishing',
                key: 'tailProcessingDistinguishing',
                align: 'center',
                width:120
            }
        ],
        data:[],
        columns16: [
            {
                title: 'Name',
                key: 'name',
                tree: true
            },
            {
                title: 'Age',
                key: 'age'
            },
            {
                title: 'Address',
                key: 'address'
            }
        ],
        data12: [
            {
                id: '100',
                name: 'John Brown',
                age: 18,
                address: 'New York No. 1 Lake Park'
            },
            {
                id: '101',
                name: 'Jim Green',
                age: 24,
                address: 'London No. 1 Lake Park',
                children: [
                    {
                        id: '10100',
                        name: 'John Brown',
                        age: 18,
                        address: 'New York No. 1 Lake Park'
                    },
                    {
                        id: '10101',
                        name: 'Joe Blackn',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park'
                    },
                    {
                        id: '10102',
                        name: 'Jon Snow',
                        age: 26,
                        address: 'Ottawa No. 2 Lake Park',
                        children: [
                            {
                                id: '1010200',
                                name: 'Jim Green',
                                age: 24,
                                address: 'New York No. 1 Lake Park'
                            }
                        ]
                    }
                ]
            },
            {
                id: '102',
                name: 'Joe Black',
                age: 30,
                address: 'Sydney No. 1 Lake Park'
            },
            {
                id: '103',
                name: 'Jon Snow',
                age: 26,
                address: 'Ottawa No. 2 Lake Park'
            }
        ],
        q: {
            name: ''
        },
        ruleValidate:{
        }
    },
    methods: {
        handleLoadData:function (item, callback) {
            setTimeout(function () {
                var data = [
                    {
                        id: '10100',
                        name: 'John Brown',
                        age: 18,
                        address: 'New York No. 1 Lake Park'
                    },
                    {
                        id: '10101',
                        name: 'Joe Blackn',
                        age: 30,
                        address: 'Sydney No. 1 Lake Park'
                    }
                ];
                callback(data);
            }, 2000);
        },
        init:function(){
            var url="arrival/list";
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
            });
            vm.initParts();
        },
        //获取零件
        initParts:function(){
            var url="arrival/getAllParts";
            $.ajax({
                url:url,
                type:"POST",
                dataType:"JSON",
                success:function (data) {
                    vm.partList=[];
                    for(var i=0;i<data.partList.length;i++){
                        // console.log(data.partList[i]);
                        vm.partList.push({key:data.partList[i],name:data.partList[i],value:data.partList[i]});
                    }
                    // vm.partList=data.partList;
                }
            });
        },
        reloadSearch: function() {
            console.log(vm.name);
            $.ajax({
                type: "POST",
                url: "../arrival/test",
                data: {parts:vm.name},
                success: function(r) {
                    console.log("查看数据");
                }
            })
        },
        //确定到货的总数及其详情
        chooseFarm:function(){
            var url="arrival/getArrivalPartsDetail";
            $.ajax({
                url:url,
                type:"POST",
                dataType:"JSON",
                data: {parts:vm.name},
                success:function (data) {
                    vm.arrivalNum=data.demandArrival.arrivalNum;
                    vm.demandNum=data.demandArrival.demandNum;
                }
            });
        },
        excel:function(){
            location.href = 'arrival/excel?parts='+vm.name;
        },
        query: function () {
			vm.reload();
		},
		add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.arrival = {};
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
            var url = vm.arrival.id == null ? "../arrival/save" : "../arrival/update";
            $.ajax({
			    url: url,
                data: JSON.stringify(vm.arrival),
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
				    url: "../arrival/delete",
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
                url: "../arrival/info/"+id,
                dataType: "json",
                success: function(r) {
                    vm.arrival = r.arrival;
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