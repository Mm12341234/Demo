<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${BasePackageName}${DaoPackageName}.${ClassName}Dao">

    <resultMap type="${BasePackageName}entity.${ClassName}Entity" id="${EntityName}Map">
        <#list columns! as column>
        <result property="${column.attrname}" column="${column.columnName}"/>
        </#list>
    </resultMap>


    <select id="queryObject" resultType="${BasePackageName}entity.${ClassName}Entity">
        select
        <#list columns! as column>
           `${column.columnName}`<#if column_has_next>,</#if>
        </#list>
        from ${TableName}
        where ${PrimaryKey.columnName} = ${r'#{id}'}
    </select>


    <select id="queryList" resultType="${BasePackageName}entity.${ClassName}Entity">
        select
        <#list columns! as column>
           `${column.columnName}`<#if column_has_next>,</#if>
        </#list>
        from ${TableName}
        WHERE 1=1
        <if test="name != null and name.trim() != ''">
            AND name LIKE concat('%',${r'#{name}'},'%')
        </if>
        <choose>
            <when test="sidx != null and sidx.trim() != ''">
                order by ${r'${sidx}'} ${r'${order}'}
            </when>
            <otherwise>
                order by ${PrimaryKey.columnName} desc
            </otherwise>
        </choose>
        <if test="offset != null and limit != null">
            limit ${r'#{offset}'},${r'#{limit}'}
        </if>
    </select>

    <select id="queryTotal" resultType="int">
        select count(*) from ${TableName}
        WHERE 1=1
        <if test="name != null and name.trim() != ''">
            AND name LIKE concat('%',${r'#{name}'},'%')
        </if>
    </select>

    <insert id="save" parameterType="${BasePackageName}entity.${ClassName}Entity" useGeneratedKeys="true" keyProperty="${PrimaryKey.attrname}">
         insert into ${TableName}(
         <#list columns! as column>
             <#if column.columnName!= PrimaryKey.columnName>`${column.columnName}`<#if column_has_next>,</#if></#if>
         </#list>
         ) values(
         <#list columns! as column>
             <#if column.columnName!=PrimaryKey.columnName>${r'#{'}${column.attrname}}<#if column_has_next>,</#if></#if>
         </#list>
         )
    </insert>

    <update id="update" parameterType="${BasePackageName}entity.${ClassName}Entity">
        update ${TableName}
        <set>
            <#list columns! as column>
            <#if column.columnName!= PrimaryKey.columnName><if test="${column.attrname}!= null">`${column.columnName}` =${r'#{'}${column.attrname}}<#if column_has_next>,</#if></if></#if>
            </#list>
        </set>
        where ${PrimaryKey.columnName} = ${r'#{'}${PrimaryKey.attrname}}
    </update>

    <delete id="delete">
        delete from ${TableName} where ${PrimaryKey.columnName} =${r' #{value}'}
    </delete>

    <delete id="deleteBatch">
        delete from ${TableName} where ${PrimaryKey.columnName} in
        <foreach item="${PrimaryKey.attrname}" collection="array" open="(" separator="," close=")">
        ${r'#{'}${PrimaryKey.attrname}}
        </foreach>
    </delete>



</mapper>