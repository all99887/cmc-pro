<template>
    <div class="main-content-box">
        <el-row>
            <el-col :span="24">
                <div class="content-box" :style="{height: $store.getters.contentBoxHeight}">
                    <div class="button-box">
                        <el-row :gutter="20">
                            <el-col :span="17">
                                <el-button size="medium" type="primary" @click="openDialog('add')">新增</el-button>
                                <el-button size="medium" type="danger" @click="del">删除</el-button>
                            </el-col>
                            <el-col :span="7">

                                <!-- 搜索在这添加 -->

                                <!--<el-col :span="24">-->
                                <!--<el-input v-model="queryForm.roleName" placeholder="按角色名搜索" >-->
                                <!--<el-button slot="append" icon="el-icon-search" @click="search"></el-button>-->
                                <!--</el-input>-->
                                <!--</el-col>-->
                            </el-col>
                        </el-row>
                    </div>

                    <section class="cmc-table">
                        <el-table
                                ref="tableList"
                                :data="tableList"
                                style="width: 100%"
                                @selection-change="handleSelectionChange">

                            <el-table-column type="selection" width="55" align="left" :formatter="tableformatter"></el-table-column>
                            <#list table.fields as field>
                                <#if !field.keyFlag>
                            <el-table-column prop="${field.propertyName}" label="${field.comment}" align="left" :formatter="tableformatter"></el-table-column>
                                </#if>
                            </#list>

                            <el-table-column label="操作" width="180px">
                                <template slot-scope="scope">
                                    <el-button size="mini" type="primary" @click="openDialog('edit', scope.row)">编辑
                                    </el-button>
                                </template>
                            </el-table-column>

                        </el-table>

                        <el-pagination style="margin-top: 10px; text-align: right;"
                                       :current-page="tablePageIndex"
                                       @size-change="handleSizeChange"
                                       @current-change="handleCurrentChange"
                                       :page-sizes="[10, 25, 50, 100]"
                                       :page-size="tablePageSize"
                                       layout="total, sizes, prev, pager, next, jumper"
                                       :total="tableTotal"
                        >

                        </el-pagination>
                    </section>

                    <el-dialog
                            :title="dialogTitle"
                            :visible.sync="dialogVisible"
                            width="500px">

                        <el-form ref="form" :model="form" :rules="rules" label-width="100px" label-suffix="：">
                        <#list table.fields as field>
                            <#if !field.keyFlag>
                            <el-form-item label="${field.comment}" prop="${field.propertyName}">
                                <#if field.type == 'datetime'>
                                <el-date-picker
                                        style="width: 100%;"
                                        v-model="form.${field.propertyName}"
                                        type="datetime"
                                        placeholder="选择日期时间">
                                </el-date-picker>
                                <#else>
                                <el-input v-model="form.${field.propertyName}"></el-input>
                                </#if>
                            </el-form-item>
                            </#if>

                        </#list>

                        </el-form>

                        <span slot="footer" class="dialog-footer">
                            <el-button @click="dialogVisible = false">取 消</el-button>
                            <el-button type="primary" @click="submitForm()">确 定</el-button>
                        </span>
                    </el-dialog>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import util from "~/common/util";

    export default {
        data() {
            return {
                dialogTitle: '新增',
                dialogVisible: false,
                formType: '',
                selectedRows: [],
                tableList: [],
                tableTotal: 0,
                tablePageSize: 10,
                tablePageIndex: 1,
                form: this.initForm(),
                queryForm() {
                    // 搜索在这添加
                },
                rules: {
                    // 字段校验在这添加
                    // realName: [
                    //     // {required: true, message: '请输入真实姓名', trigger: 'blur'},
                    //     {max: 32, message: '长度在32个字符以内', trigger: 'blur'}
                    // ],
                    // email: [
                    //     // {required: true, message: '请输入邮箱地址', trigger: 'blur'},
                    //     {validator: check.checkEmail, trigger: 'blur'},
                    //     {max: 128, message: '长度在128个字符以内', trigger: 'blur'}
                    // ]
                }
            }
        },
        mounted() {
            this.$nextTick(function () {
                this.loadTableData()
            })
        },
        methods: {
            search() {
                this.tablePageIndex = 1
                this.loadTableData(this.queryForm)
            },
            initForm() {
                return {
            <#list table.fields as field>
                    ${field.propertyName}:''<#if field_index != (table.fields?size - 1)>,</#if>
            </#list>
                }
            },
            handleSelectionChange(rows) {
                this.selectedRows = rows
            },
            handleSizeChange(val) {
                this.tablePageIndex = 1
                this.tablePageSize = val
                this.loadTableData()
            },
            handleCurrentChange(val) {
                this.tablePageIndex = val
                this.loadTableData()
            },
            tableformatter(row, column, cellValue, index) {
                switch (column.property) {

                    <#list table.fields as field>
                        <#if field.type == 'datetime'>
                    case '${field.propertyName}': {
                         let date = new Date(cellValue)
                         return util.formatDateTime(date)
                    }
                        </#if>
                    </#list>
                    default: {
                        return cellValue
                    }
                }
            },
            loadTableData(query) {
                let params = {
                    pageindex: this.tablePageIndex,
                    pagesize: this.tablePageSize
                }
                if (query) {
                }
                this.$http.post('/JmdUser/list', params, {loading: true}).then(response => {
                    if (response.data.success) {
                        let page = response.data.page
                        this.tableList = page.records
                        this.tableTotal = page.total
                    } else {
                    }
                }).catch(response => {
                });
            },
            openDialog(formType, data) {
                this.formType = formType
                this.form = this.initForm()
                if (formType == 'edit') {
                    this.dialogTitle = '编辑'
                    Object.assign(this.form, data)
                } else {
                    this.dialogTitle = '新增'
                }
                this.dialogVisible = true
            },
            submitForm() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        let url = ''
                        let successMsg = ''
                        if (this.formType === 'add') {
                            url = '/JmdUser/add'
                            successMsg = '新增成功'
                        } else if (this.formType === 'edit') {
                            url = '/JmdUser/edit'
                            successMsg = '编辑成功'
                        }
                        this.$http.post(url, this.form, {loading: true}).then(response => {
                            if (response.data.success) {
                                this.formType = ''
                                this.$message({
                                    message: successMsg,
                                    type: 'success',
                                });
                                this.dialogVisible = false
                                this.loadTableData()
                            }
                        }).catch(response => {
                        });
                    } else {
                        return false;
                    }
                })
            },
            statusChange(data) {
                let message = ''
                if (data.status === 1) {
                    message = '已启用'
                } else {
                    message = '已停用'
                }
                this.$http.post('/JmdUser/edit', data, {loading: true}).then(response => {
                    if (response.data.success) {
                        this.formType = ''
                        this.$message({
                            message: message,
                            type: 'success',
                        });
                    }
                }).catch(response => {
                });
            },
            del() {
                this.$confirm('确认删除吗?', '提示', {
                }).then(() => {
                    let ids = []
                    this.selectedRows.forEach((item) => {
                    <#list table.fields as field>
                        <#if field.keyFlag>
                            ids.push(item.${field.propertyName})
                        </#if>
                    </#list>
                    })
                    this.$http.post('/JmdUser/del', {ids: ids.join(',')}, {}).then(response => {
                        if (response.data.success) {
                            this.formType = ''
                            this.$message({
                                message: '删除成功',
                                type: 'success',
                            });
                            this.dialogVisible = false
                            this.tablePageIndex = 1
                            this.loadTableData()
                        }
                    }).catch(response => {
                    });
                }).catch(() => {
                });
            }
        }
    }
</script>

<style>


</style>
