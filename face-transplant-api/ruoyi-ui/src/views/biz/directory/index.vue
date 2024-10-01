<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      
      <el-form-item label="目录名称" prop="directoryName">
        <el-input
          v-model="queryParams.directoryName"
          placeholder="请输入目录名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目录级别" prop="directoryLevel">
        <el-select v-model="queryParams.directoryLevel" placeholder="请选择目录级别" clearable>
          <el-option
            v-for="dict in dict.type.biz_directory_level"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
     
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['biz:directory:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:directory:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:directory:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:directory:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="directoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="目录id" align="center" prop="id" />
      <el-table-column label="上级目录id" align="center" prop="supDirectoryId" />
      <el-table-column label="目录图片url" align="center" prop="directoryUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.directoryUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <el-table-column label="目录名称" align="center" prop="directoryName" />
      <el-table-column label="目录级别" align="center" prop="directoryLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_directory_level" :value="scope.row.directoryLevel"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="奖励积分" align="center" prop="priceRew" /> -->
      <!-- <el-table-column label="标签" align="center" prop="directoryTag" /> -->
      <el-table-column label="使用次数" align="center" prop="usageTimes" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:directory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:directory:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改目录列对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="550px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="目录级别" prop="directoryLevel">
          <el-select v-model="form.directoryLevel" placeholder="请选择目录级别" @change="updateLevel(form.directoryLevel)">
            <el-option
              v-for="dict in dict.type.biz_directory_level"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
              
            ></el-option>
          </el-select>
        </el-form-item>
        <div v-if="form.directoryLevel != 1" >
          <el-form-item label="上级目录" prop="directoryLevel">
            <el-select v-model="form.supDirectoryId" placeholder="请输入上级目录">
              <el-option
                v-for="dict in directoryDtoList"
                :key="dict.id"
                :label="dict.directoryName"
                :value="dict.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </div>
      
        
        <el-form-item label="目录图片url">
          <image-upload v-model="form.directoryUrl" :limit="1" />
        </el-form-item>
        <el-form-item label="目录名称" prop="directoryName">
          <el-input v-model="form.directoryName" placeholder="请输入目录名称" />
        </el-form-item>
        

        <div v-if="form.directoryLevel == 3" >
          <el-form-item label="普通积分" prop="price">
            <el-input v-model="form.price" placeholder="请输入价格" />
          </el-form-item>
          <el-form-item label="奖励积分" prop="priceRew">
          <el-input v-model="form.priceRew" placeholder="请输入奖励积分" />
        </el-form-item>
        <el-form-item label="基础图片数量" prop="numberFoun">
          <el-input v-model="form.numberFoun" placeholder="请输入基础图片数量" />
        </el-form-item>
        <el-form-item label="奖励图片数量" prop="numberRew">
          <el-input v-model="form.numberRew" placeholder="请输入奖励图片数量" />
        </el-form-item>

        <el-form-item label="目录介绍">
          <editor v-model="form.introduce" :min-height="192"/>
        </el-form-item>


        <el-form-item label="标签" prop="directoryTag">
          <el-select v-model="form.directoryTag" placeholder="请选择标签">
            <el-option
              v-for="dict in dict.type.biz_photo_type"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        </div>
        
        <el-form-item label="使用次数" prop="usageTimes">
          <el-input v-model="form.usageTimes" placeholder="请输入使用次数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDirectory, getDirectory, delDirectory, addDirectory, updateDirectory, getDirelist } from "@/api/biz/directory";

export default {
  name: "Directory",
  dicts: ['biz_directory_level','biz_photo_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 目录列表格数据
      directoryList: [],

      directoryDtoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supDirectoryId: null,
        directoryUrl: null,
        directoryName: null,
        directoryLevel: null,
        price: null,
        priceRew: null,
        directoryTag: null,
        usageTimes: null,
        numberFoun: null,
        numberRew: null,
        introduce: null
      },
      queryParams2:{
        pageNum: 1,
        pageSize: 10000,
        directoryName: null,
        supDirectoryId: null,
        directoryLevel: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    updateLevel(directoryLevel){
      console.log("22222" + directoryLevel);
      if (this.form.directoryLevel - 1 != 0) {
          this.queryParams2.directoryLevel = directoryLevel - 1;
          this.getDirelist(); 

      }
    },


    // 根据上级目录级别查询目录列表
    getDirelist(){
      this.loading = true;
      listDirectory(this.queryParams2).then(response => {
        this.directoryDtoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },


    /** 查询目录列列表 */
    getList() {
      this.loading = true;
      listDirectory(this.queryParams).then(response => {
        this.directoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        supDirectoryId: null,
        directoryUrl: null,
        directoryName: null,
        directoryLevel: null,
        price: null,
        priceRew: null,
        directoryTag: null,
        usageTimes: null,
        createTime: null,
        updateTime: null,
        numberFoun: null,
        numberRew: null,
        introduce: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加目录列";
      
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      
      this.reset();
      const id = row.id || this.ids
      getDirectory(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改目录列";
        if (this.form.directoryLevel - 1 != 0) {
          this.queryParams2.directoryLevel = this.form.directoryLevel - 1;
          this.getDirelist(); 

        }
        
      });
      
      
      
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDirectory(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDirectory(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除目录列编号为"' + ids + '"的数据项？').then(function() {
        return delDirectory(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/directory/export', {
        ...this.queryParams
      }, `directory_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
