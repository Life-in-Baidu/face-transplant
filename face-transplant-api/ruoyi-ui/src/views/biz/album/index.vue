<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <!-- <el-form-item label="用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      
      <el-form-item label="身材标签" prop="figureTag">
        <el-select v-model="queryParams.figureTag" placeholder="请选择身材标签" clearable>
          <el-option
            v-for="dict in dict.type.biz_figure_tag"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="所属目录" prop="directoryId">
        <el-select v-model="queryParams.directoryId" placeholder="请选择所属目录" clearable>
          <el-option
            v-for="dict in directoryDtoList"
            :key="dict.id"
            :label="dict.directoryName"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="图片类型" prop="imgType">
        <el-select v-model="queryParams.imgType" placeholder="请选择图片类型" clearable>
          <el-option
            v-for="dict in dict.type.biz_img_type"
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
          v-hasPermi="['biz:album:add']"
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
          v-hasPermi="['biz:album:edit']"
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
          v-hasPermi="['biz:album:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:album:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="albumList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id主键" align="center" prop="id" />
      <!-- <el-table-column label="用户id" align="center" prop="userId" /> -->
      <el-table-column label="图片地址" align="center" prop="imgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="使用次数" align="center" prop="usageTimes" />
      <el-table-column label="身材标签" align="center" prop="figureTag">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_figure_tag" :value="scope.row.figureTag"/>
        </template>
      </el-table-column>

     

      <!-- <el-table-column label="所属目录id" align="center" prop="directoryDtoList.directoryName" /> -->
      <!-- <el-table-column label="普通积分" align="center" prop="ordIntegral" />
      <el-table-column label="奖励积分" align="center" prop="rewIntegral" /> -->
      <el-table-column label="图片类型" align="center" prop="imgType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_img_type" :value="scope.row.imgType"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:album:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:album:remove']"
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

    <!-- 添加或修改相册对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="图片地址">
          <image-upload v-model="form.imgUrl" :limit="1"/>
        </el-form-item>
        <!-- <el-form-item label="使用次数" prop="usageTimes">
          <el-input v-model="form.usageTimes" placeholder="请输入使用次数" />
        </el-form-item> -->
        <el-form-item label="身材标签" prop="figureTag">
          <el-select v-model="form.figureTag" placeholder="请选择身材标签">
            <el-option
              v-for="dict in dict.type.biz_figure_tag"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>

        <div v-if="form.figureTag != 0">
          <el-form-item label="同款图片" prop="styleId">
            <el-input v-model="form.styleId" placeholder="请输入同款图片的正常身材的图片id" />
          </el-form-item>
        </div>

        <el-form-item label="所属目录" prop="directoryId">
          <el-select v-model="form.directoryId" placeholder="请选择目录">
            <el-option
              v-for="dict in directoryDtoList"
              :key="dict.id"
              :label="dict.directoryName"
               :value="dict.id"
            ></el-option>
          </el-select>
        </el-form-item>
      
        <!-- <el-form-item label="普通积分" prop="ordIntegral">
          <el-input v-model="form.ordIntegral" placeholder="请输入普通积分" />
        </el-form-item>
        <el-form-item label="奖励积分" prop="rewIntegral">
          <el-input v-model="form.rewIntegral" placeholder="请输入奖励积分" />
        </el-form-item> -->
        <el-form-item label="图片类型" prop="imgType">
          <el-select v-model="form.imgType" placeholder="请选择图片类型">
            <el-option
              v-for="dict in dict.type.biz_img_type"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
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
import { listAlbum, getAlbum, delAlbum, addAlbum, updateAlbum } from "@/api/biz/album";
import { listDirectory, getDirelist } from "@/api/biz/directory";

export default {
  name: "Album",
  dicts: ['biz_img_type', 'biz_figure_tag'],
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
      // 相册表格数据
      albumList: [],
      directoryDtoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        imgUrl: null,
        usageTimes: null,
        figureTag: null,
        directoryId: null,
        ordIntegral: null,
        rewIntegral: null,
        imgType: null,
        styleId: null
      },
      queryParams2:{
        pageNum: 1,
        pageSize: 10000,
        directoryName: null,
        supDirectoryId: null,
        directoryLevel: 3,
      },
     
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户id不能为空", trigger: "blur" }
        ],
        imgUrl: [
          { required: true, message: "图片地址不能为空", trigger: "blur" }
        ],
        figureTag: [
          { required: true, message: "身材标签不能为空", trigger: "change" }
        ],
        directoryId: [
          { required: true, message: "所属目录id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getDirelist();
    this.getList();
    
  },
  methods: {

    // 根据上级目录级别查询目录列表
    getDirelist(){
      this.loading = true;
      listDirectory(this.queryParams2).then(response => {
        this.directoryDtoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 查询相册列表 */
    getList() {
      this.loading = true;
      listAlbum(this.queryParams).then(response => {
        this.albumList = response.rows;
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
        userId: null,
        imgUrl: null,
        usageTimes: null,
        figureTag: null,
        directoryId: null,
        ordIntegral: null,
        rewIntegral: null,
        imgType: null,
        styleId: null
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
      this.title = "添加相册";

      
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getAlbum(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改相册";
       
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAlbum(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAlbum(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除相册编号为"' + ids + '"的数据项？').then(function() {
        return delAlbum(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/album/export', {
        ...this.queryParams
      }, `album_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
