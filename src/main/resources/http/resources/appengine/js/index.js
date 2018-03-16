;(function ($) {
        Date.prototype.Format = function (fmt) {
            var o = {
                "M+": this.getMonth() + 1, //月份
                "d+": this.getDate(), //日
                "H+": this.getHours(), //小时
                "m+": this.getMinutes(), //分
                "s+": this.getSeconds(), //秒
                "q+": Math.floor((this.getMonth() + 3) / 3), //季度
                "S": this.getMilliseconds() //毫秒
            };
            if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        };
        window.appEngine = {
        dataType: [
            {"value":"short","text":"short"},
            {"value":"int","text":"int"},
            {"value":"long","text":"long"},
            {"value":"float","text":"float"},
            {"value":"double","text":"double"},
            {"value":"boolean","text":"boolean"},
            {"value":"char","text":"char"},
            {"value":"long","text":"long"},
            {"value":"byte","text":"byte"},
            {"value":"Integer","text": "Integer"},
            {"value":"String","text":"String"},
            {"value":"Boolean","text":"Boolean"},
            {"value":"Float","text":"Float"},
            {"value":"Double","text":"Double"},
            {"value":"java.util.Date","text":"java.util.Date"},
            {"value":"java.sql.Timestamp","text":"Timestamp"},
            {"value":"java.sql.Time","text":"Time"},
            {"value":"byte[]","text":"byte[]"},
            {"value":"java.sql.Date","text":"java.sql.Date"},
            {"value":"java.sql.Blob","text":"Blob"},
            {"value":"java.sql.Clob","text":"Clob"}
        ],
        dialogId: new Array(),
        editMenu : function (menu) {
            switch(menu){
                case 'add': editMenu(menu); break;
                case 'del': delMenu(); break;
                case 'mod': editMenu(menu);break;
                default: true
            }
        },
        openDialog:function(options){
            var dialogId = appEngine.dialogId, _b = false;
            if(dialogId.length != 0)
            {
                for ( var _id in dialogId) {
                    $("#"+dialogId[_id]).remove();
                }
                _b = true;
            }
            $(document.body).append("<div id="+options.id+" class='easyui-dialog'></div>");
            $("#"+options.id).dialog({
                width : options.width,
                height : options.height,
                modal : options.modal==null?false:options.modal,
                maximizable : options.maximizable==null?false:options.maximizable,
                title : options.title,
                closed : options.closed==null ? false : options.closed,
                href : options.url,
                buttons : [ {text : "确定",iconCls : "icon-save",handler : function() {
                        options.save();
                    }
                }, {text : "返回",iconCls : "icon-undo",handler : function() {
                        $("#"+options.id).dialog("close");
                    }
                } ]
            });
            if(!_b)
            {
                appEngine.dialogId.push(options.id);
            }else{
                if($.inArray(options.id,dialogId)==-1)
                {
                    appEngine.dialogId.push(options.id);
                }
            }
            $("#"+options.id).dialog("open");
        },
        displayTable: function (clazz) {
            var data = $(clazz).data('detail');
            data = data.replace(new RegExp("'","gm"),'"');
            data = JSON.parse(data);
            var state = $.data($('#menu-list')[0], 'datagrid');
            var tables = data.row.tables, id = data.row.id, length = tables.length;
            var status = $(clazz).attr('status');
            if(status == 'opened'){
                $('#tr-h1-'+id).remove();
                $('#tr-h2-'+id).remove();
                $(clazz).attr('status', 'close');
                return;
            }
            var h1 = '<tr class="treegrid-tr-tree" id="tr-h1-'+id+'" style="height:'+ (length+1)*32 +'px">';
                h1 += '<td colspan="1" style="border-right:0">';
                h1 += '<div class="datagrid-row-detail">&nbsp;</div>';
                h1 += '</td></tr>';
            var h2 = '<tr class="treegrid-tr-tree" id="tr-h2-'+id+'" style="height:'+ (length+1)*32 +'px">';
                h2 += '<td colspan="6"><table id="entity-'+id+'"></table></td>';
            $('#' + state.rowIdPrefix + '-1-' + id).after(h1);
            var tr = $('#' + state.rowIdPrefix + '-2-' + id);
            tr.after(h2);
            $('#entity-'+id).datagrid({
                singleSelect: true,
                rownumbers: true,
                checkbox: true,
                columns:[[
                    {field:'entityName',title:'实体名',width:'20%'},
                    {field:'remark',title:'备注',width:'30%'},
                    {field:'createDate',title:'创建时间',width:'30%', formatter: function (value,row,index) {
                        return new Date(value).Format("yyyy-MM-dd HH:mm:ss");
                    }},
                    {field:'id',align:'center',title:'操作',width:'20%',formatter:function(value,row,index){
                        var h = "<a href='javascript:;' style='text-decoration: none;color:red;' data-id='"+value+"' onclick='appEngine.deleteEntity(this)'>删除</a>";
                        h += "<a href='javascript:;' style='text-decoration: none;margin-left: 5px;' onclick='appEngine.addEntity(\""+value+"\")'>修改</a>";
                        return h;
                    }}
                ]],
                data: tables,
                onClickRow: function (index, row) {
                    $('#entityInfo').form('load', row);
                    //查询实体属性信息
                    $("#datagrid-entity").datagrid({data: row.entityProperties});
                }
            });
            var height = -($('#tr-h2-'+ id + ' .datagrid').offset().top - tr.offset().top - tr.height()) * 2;
            $('#tr-h2-'+ id + ' .datagrid').css('margin-top', height + 'px');
            $(clazz).attr('status', 'opened');
        },
        deleteEntity: function (clazz) {
            var id= $(clazz).data('id');
            if(id == null || id == undefined) return;
            $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
            $.post(path + '/menu/deleteEntity', {id: id}, function (result) {
                $.messager.progress("close");
                if (result.success) {
                    $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                    $('#menu-list').treegrid("reload");
                } else {
                    $.messager.alert("操作提示", result.errorMessage,"question");
                }
            });
        },
        addEntity: function (id) {
            var rows = $("#menu-list").datagrid("getSelected");
            var menuId = rows.id;
            if(id != undefined){
                if(rows == null)return;
            }
            appEngine.openDialog({
                width: 420,
                height: 300,
                modal: true,
                maximizable: false,
                title: (id == undefined) ? '新增实体' : '修改实体',
                closed: true,
                id: "addEntity",
                url: path + '/engine/appengine/menu/entity.html?menuId=' + menuId + '&id='+id,
                save:function(){
                    $('#add-entity').form('submit', {
                        url : path + "/menu/addEntity",
                        method : 'post',
                        onSubmit : function() {
                            var result = $(this).form('validate');
                            if(result)$.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                            return result;
                        },
                        success : function(data) {
                            $.messager.progress("close");
                            var result = eval('(' + data + ')');
                            if (result.success) {
                                $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                                $('#addEntity').dialog("close");
                                $('#menu-list').treegrid("reload");
                            } else {
                                $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.errorMessage + "</span>");
                            }
                        }
                    });
                }
            });
        },
        saveEntity: function () {
            appEngine.endEditing();//结束所有编辑行
            var result = $('#entityInfo').form('validate');
            if(!result)return;
            var entityRow = $('#datagrid-entity').datagrid("getRows");
            if(entityRow.length == 0)return;
            var deleteEntityRow = $('#datagrid-entity').datagrid("getChanges", "deleted");
            var entityInfo = $('#entityInfo').serializeArray();
            entityInfo.push({name: "entityRow", value: JSON.stringify(entityRow)});
            entityInfo.push({name: "deleteEntityRow", value: JSON.stringify(deleteEntityRow)});
            $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
            $.post(path + '/menu/saveEntity', entityInfo , function (result) {
                $.messager.progress("close");
                if (result.success) {
                    $.messager.show({title:'温馨提示',msg:'保存成功!',timeout:3000,showType:'show'});
                    $("#datagrid-entity").datagrid({data: result.data});
                } else {
                    $.messager.alert("操作提示", result.errorMessage,"question");
                }
            });
        },
        uploadTemplate: function () {
            $("#uploadTemlate").remove();
            $(document.body).append("<div id='uploadTemlate' class='easyui-window'></div>");
            $('#uploadTemlate').window({
                width:400,
                height:240,
                title:'选择模板',
                closed:true,
                collapsible:false,
                content:appEngine.uploadFileForm({id:'uploadTemlate',uploadMethodName:'appEngine.uploadFileClick()',fileChangeMethod:'appEngine.fileUploadChange()'}),
                minimizable:false,
                maximizable:false
            });
            $('#uploadTemlate').window('open');
        },
        createEntityCode: function () {  //生成实体代码
            if($("#sysMenuTableId").val() == "") return;
            appEngine.endEditing();//结束所有编辑行
            appEngine.openDialog({
                width: 400,
                height: 200,
                modal: true,
                maximizable: false,
                title: '选择生成业务代码',
                closed: true,
                id: "createEntityMode",
                save:function(){
                    var result = $('#entityInfo').form('validate');
                    if(!result)return;
                    var entityRow = $('#datagrid-entity').datagrid("getRows");
                    if(entityRow.length == 0)return;
                    var entityInfo = $('#entityInfo').serializeArray();
                    var checks = $('#entityMode input:checkbox:checked');
                    if(checks.length == 0) return;
                    var entityMode = [];
                    $.each(checks, function (i, r) {
                        entityMode.push(r.getAttribute("name"));
                    });
                    var deleteEntityRow = $('#datagrid-entity').datagrid("getChanges", "deleted");
                    entityInfo.push({name: "entityMode", value: JSON.stringify(entityMode)});
                    entityInfo.push({name: "entityRow", value: JSON.stringify(entityRow)});
                    entityInfo.push({name: "deleteEntityRow", value: JSON.stringify(deleteEntityRow)});
                    $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                    $.post(path + '/menu/createBusinessCode', entityInfo , function (result) {
                        $.messager.progress("close");
                        if (result.success) {
                            $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                            $('#createEntityMode').dialog("close");
                        } else {
                            $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.errorMessage + "</span>");
                        }
                    });
                }
            });
            var html = '<div style="padding: 5px;"><from id="entityMode">';
            html += '<input type="checkbox" checked name="entity" id="entity"/> <label for="entity">实体代码</label>';
            html += '<input type="checkbox" name="service" id="service"/> <label for="service">业务操作层代码</label>';
            html += '<input type="checkbox" name="dao" id="dao"/> <label for="dao">数据操作层代码</label>';
            html += '<div style="color: #0000FF;">说明：应用引擎只负责生成业务代码，不生成数据库表，新生成的实体代码重新启动项目后，会自动创建数据库表结构。</div>';
            html += '<div><label id="errorMsg" /></div>';
            html += '</from></div>';
            $('#createEntityMode').html(html);
        },
        uploadFileForm: function(options){
            var html = "<form id=\"importFileForm\" enctype=\"multipart/form-data\">";
            html += "<table style=\"margin:5px;height:70px;\">";
            html += "<tr>";
            html += "<td>模板JSON数据:</td>";
            html += "<td><input name='templateJson' class='easyui-textbox' data-options='multiline:true' style='width:260px;'/></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td>模&nbsp;&nbsp;&nbsp;板&nbsp;&nbsp;&nbsp;文&nbsp;&nbsp;&nbsp;件:</td>";
            html += "<td><input class=\"easyui-filebox\" data-options=\"prompt:'点击选择模板文件...',buttonText:'点击上传',onChange:function(){"+options.fileChangeMethod+";}\" id=\"templateFile\" name=\"templateFile\" style=\"width:260px;\"></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td>模板内容类型:</td>";
            html += "<td><select class='easyui-combobox' name='fileType' data-options='required:true' style='width:200px;'>";
            html += "<option value='.jsp'>jsp</option><option value='.html'>html</option><option value='.java'>java</option>" ;
            html +=  "<option value='.sql'>sql</option><option value='.properties'>properties</option><option value='.xml'>xml</option>";
            html +=  "<option value='.js'>js</option><option value='.css'>css</option><option value='.ftl'>xml</option>";
            html +=  "</select></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td colspan=\"4\"><label id=\"fileName\" isCanUpolad='false' /></td>";
            html += "</tr>";
            html += "<tr>";
            html += "<td colspan=\"4\">";
            html += "<label id=\"uploadInfo\" />";
            html += "</td>";
            html += "</tr>";
            html += "</table><div style=\"text-align:center;clear:both;margin:5px;\">";
            html += "<a id=\"uploadFile\" style=\"margin-left:5px;\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-ok'\" onclick=\"javascript:"+options.uploadMethodName+";\" >上传</a>";
            html += "<a class=\"easyui-linkbutton\" style=\"margin-left:5px;\" data-options=\"iconCls:'icon-cancel'\" onclick=\"javascript:$('#"+options.id+"').window('close');\" >关闭</a>";
            html += "</div>"
            html += "</form>";
            return html;
        },
        createTemplate: function () {
            appEngine.openDialog({
                width: 600,
                height: 400,
                modal: true,
                maximizable: false,
                title: '选择生成模板',
                closed: true,
                id: "createTemplate",
                url: path + '/engine/appengine/menu/template.html',
                save:function(){
                    var template_table = $("#template-table");
                    var row = template_table.datagrid("getSelected");
                    if(row == undefined || row == null) return;
                    if(template_table.attr("status") == "closed"){
                        var status = appEngine.createFileByTemplate();
                        return;
                    }
                    $($("#createTemplate").children()[0]).hide("hide", function () {
                        template_table.attr("status", "closed");
                       $('#projectTreeDiv').show(function () {
                           $("#project-template-edit").form("load", row);
                           $('#templateWritePath').combotree('reload', path + '/menu/getProjectDirTree');
                       });
                    });
                }
            });
        },
        createFileByTemplate: function () {
            $('#project-template-edit').form('submit', {
                url : path + "/menu/createFileByTemplate",
                method : 'post',
                onSubmit : function() {
                    var result = $(this).form('validate');
                    if(result)$.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                    return result;
                },
                success : function(data) {
                    $.messager.progress("close");
                    var result = eval('(' + data + ')');
                    if (result.success) {
                        $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                        $('#createTemplate').dialog("close");
                    } else {
                        $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.errorMessage + "</span>");
                    }
                }
            });
        },
        fileUploadChange: function(){
            var file = document.getElementsByName("templateFile")[0].files[0];
            if(file == null ) {
                document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:错误，请上传文件!</span>";
                return;
            }
            var fileName = file.name;
            var file_typename = fileName.substring(fileName.lastIndexOf('.'),fileName.length);
            if(file_typename == ".ftl"){
                var fileSize = 0;
                //计算文件的大小
                if(file.size > 1024*1024){
                    fileSize = Math.round(file.size * 100 / (1024 * 1024)) / 100;
                    if(fileSize > 10 ){ //大于10M，禁止上传
                        document.getElementById('fileName').setAttribute("isCanUpolad","false");
                        document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:错误，文件超过10M，不能上传!</span>";
                        return;
                    }
                    fileSize = fileSize.toString() + 'MB';
                }else{
                    fileSize = Math.round(file.size * 100 / (1024)) / 100 + 'KB';
                }
                document.getElementById('fileName').setAttribute("isCanUpolad","true");
                document.getElementById('fileName').innerHTML = "<span style='color:blue;'>文件名: " + file.name + ',大小: ' + fileSize + "</span>";
            }else{
                document.getElementById('fileName').setAttribute("isCanUpolad","false");
                document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示：当前文件格式为"+file_typename+"，请上传.ftl后缀文件</span>";
            }
        },
        uploadFileClick: function () {
            if(document.getElementById('fileName').getAttribute("isCanUpolad") == "false") {
                document.getElementById('fileName').innerHTML = "<span style='color:Red'>错误提示:错误，请上传文件!</span>";
                return;
            }
            var formData = new FormData($("#importFileForm")[0]);
            $.ajax({
                url: path + '/menu/uploadTemplate',
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (result) {
                    if(result.success){
                        $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                        $('#uploadTemlate').window('close');
                    }else{
                        document.getElementById('uploadInfo').innerHTML = "<span style='color:Red'>错误提示:" + result.errorMessage + "</span>";
                    }
                },
                error: function (result) {
                    document.getElementById('uploadInfo').innerHTML = "<span style='color:Red'>错误提示:" + result.errorMessage + "</span>";
                }
            });
        },
        endEditing:function(index, field){
            if (appEngine.endIndex == undefined){return true}
            if ($('#datagrid-entity').datagrid('validateRow', appEngine.endIndex)){
                //验证数据
                $('#datagrid-entity').datagrid('endEdit', appEngine.endIndex);
                appEngine.endIndex = undefined;
                return true;
            } else {
                return false;
            }
        },
        editEntity: function (type) {
            if(type == 'add'){
                var counts = $('#datagrid-entity').datagrid('getRows').length;
                $('#datagrid-entity').datagrid('appendRow',{ordinal_position: counts + 1, entity_id: $('#sysMenuTableId').val()});
            }else if(type == "del"){
                var row = $('#datagrid-entity').datagrid('getSelected');
                var index = $('#datagrid-entity').datagrid('getRowIndex',row);
                $('#datagrid-entity').datagrid('deleteRow',index); //动态删除
            }else if(type == 'maintain'){  //设置主从表关系
                var id = $('#sysMenuTableId').val();
                if(id == undefined || id == "") return;
                appEngine.openDialog({
                    width: 600,
                    height: 400,
                    modal: true,
                    maximizable: false,
                    title: '设置主从表关系',
                    closed: true,
                    id: "masterSlave",
                    url: path + '/engine/appengine/menu/masterSlave.html',
                    save:function(){
                        var $form = $("#masterSlave-edit");
                        var result = $form.form('validate');
                        if(result){
                            var datagridEntity = $('#datagrid-entity');
                            var rows = datagridEntity.datagrid('getRows');
                            var count = rows.length;
                            var row = {ordinal_position: counts + 1, entity_id: id};
                            var formDataArray = $form.serializeArray();
                            for(var index in formDataArray){
                                if(formDataArray[index].value != ""){
                                    var key = formDataArray[index].name;
                                    var value = formDataArray[index].value;
                                    switch(key)
                                    {
                                        case "formPropertyName": row.column_name = formDataArray[index].value; break;
                                        default: row[key] = value;
                                    }
                                }
                            }
                            row.type_name = row.packageName + '.' + row.businessClassification.toLocaleLowerCase() + '.entity.' + row.type_name;
                            delete row.packageName;
                            delete row.businessClassification;
                            //检查列表中是否已存在字段，如果存在不添加
                            for(var index in rows){
                                if(rows[index].column_name == row.column_name || rows[index].type_name == row.type_name) {
                                    $('#masterSlave').dialog("close");
                                    return;
                                }
                            }
                            datagridEntity.datagrid('appendRow', row);
                            $('#masterSlave').dialog("close");
                        }
                    }
                });
            }
        },
        onClickCell : function(index, field){
            if (appEngine.endEditing(index, field)){
                $('#datagrid-entity').datagrid('selectRow', index)
                    .datagrid('editCell', {index:index,field:field});
                appEngine.endIndex = index;
            }
        }
    };

    function delMenu() {
        var rows = $("#menu-list").datagrid("getSelected");
        if(rows==null)
            return;
        var id= rows.id;
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                $.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                $.post(path + '/menu/delete', {id: id}, function (result) {
                    $.messager.progress("close");
                    if (result.success) {
                        $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                        $('#menu-list').treegrid("reload");
                    } else {
                        $.messager.alert("操作提示", result.errorMessage,"question");
                    }
                });
            }
        });
    }
    //新增菜单
    function editMenu(type) {
        var id = "";
        if(type == 'mod'){
            var rows = $("#menu-list").datagrid("getSelected");if(rows==null)return; id= rows.id;
        };
        appEngine.openDialog({
            width: 550,
            height: 500,
            modal: true,
            maximizable: false,
            title: type == 'add' ? "新增菜单" : "修改菜单",
            closed: true,
            id: "editMenu",
            url: path + '/engine/appengine/menu/index.html?id=' + id,
            save:function(){
                $('#menu-edit').form('submit', {
                    url : path + "/menu/save",
                    method : 'post',
                    onSubmit : function() {
                        var result = $(this).form('validate');
                        if(result)$.messager.progress({title : "温馨提示",msg : "请稍后，正在处理......"});
                        return result;
                    },
                    success : function(data) {
                        $.messager.progress("close");
                        var result = eval('(' + data + ')');
                        if (result.success) {
                            $.messager.show({title:'温馨提示',msg:result.data,timeout:3000,showType:'show'});
                            $('#editMenu').dialog("close");
                            $('#menu-list').treegrid("reload");
                        } else {
                            $('#errorMsg').html("<span style='color:Red'>错误提示:" + result.errorMessage + "</span>");
                        }
                    }
                });
            }
        });
    }
}
)(jQuery);