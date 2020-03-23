<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <title>${comments}</title>
    <div th:replace="sys/header.html"></div>
    <base th:href="${r'#request.getContextPath()'}+'/'">
    <#assign save='#if($shiro.hasPermission("${pathName}:save"))'>
    <#assign update='#if($shiro.hasPermission("${pathName}:update"))'>
    <#assign delete='#if($shiro.hasPermission("${pathName}:delete"))'>
    <#assign end='#end'>
    <#--${header}-->
</head>
<body>
<div id="rrapp" v-cloak>
    <div v-show="showList">
        <Row :gutter="16">
            <div class="search-group">
                <i-col span="4">
                    <i-input v-model="q.name" @on-enter="query" placeholder="名称"/>
                </i-col>
                <i-button @click="query">查询</i-button>
                <i-button @click="reloadSearch">重置</i-button>
            </div>
            <div class="buttons-group">
                ${save}
                <i-button type="info" @click="add"><i class="fa fa-plus"></i>&nbsp;新增</i-button>
                ${end}
                ${update}
                <i-button type="warning" @click="update"><i class="fa fa-pencil-square-o"></i>&nbsp;修改</i-button>
                ${end}
                ${delete}
                <i-button type="error" @click="del"><i class="fa fa-trash-o"></i>&nbsp;删除</i-button>
                ${end}
            </div>
        </Row>
        <i-table :columns="columns"
                 :data="data"
                 ref="selection"
                 @on-selection-change="handleSelectRow"
        ></i-table>
        <div style="overflow:hidden;">
            <div style="float:right;">
                <Page :total="page.pageTotal" :current="page.pageNum" :page-size="page.pageSize" show-elevator show-sizer show-total
                      placement="top" @on-change="handlePage" @on-page-size-change='handlePageSize'></Page>
            </div>
        </div>
    </div>

    <Card v-show="!showList">
        <p slot="title">{{title}}</p>
        <i-form ref="formValidate" :model="${EntityName}" :rules="ruleValidate" :label-width="80">
            <#list columns! as column>
                <Form-item label="${column.comments}" prop="${column.attrname}">
                    <i-input v-model="${EntityName}.${column.attrname}" placeholder="${column.comments}"/>
                </Form-item>
            </#list>
            <Form-item>
                <i-button type="primary" @click="handleSubmit('formValidate')">提交</i-button>
                <i-button type="warning" @click="reload" style="margin-left: 8px"/>返回</i-button>
                <i-button type="ghost" @click="handleReset('formValidate')" style="margin-left: 8px">重置</i-button>
            </Form-item>
        </i-form>
    </Card>
</div>
<script th:src="@{'js/${pathName}.js?_'+${r'${new java.util.Date().getTime()}'}}"></script>
</body>
</html>