<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="融合时间" prop="mergeTime">
        <el-date-picker clearable
          v-model="queryParams.mergeTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择融合时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="融合状态" prop="mergeState">
        <el-select v-model="queryParams.mergeState" placeholder="请选择融合状态" clearable>
          <el-option
            v-for="dict in dict.type.biz_merge_state"
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
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['biz:mergeImg:add']"
        >新增</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:mergeImg:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['biz:mergeImg:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:mergeImg:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mergeImgList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="融合图片id" align="center" prop="id" />
      <el-table-column label="上传图片url" align="center" prop="imgUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imgUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="模板图片id" align="center" prop="modelId" />
      <el-table-column label="融合图片url" align="center" prop="mergeUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.mergeUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="融合时间" align="center" prop="mergeTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.mergeTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="融合状态" align="center" prop="mergeState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_merge_state" :value="scope.row.mergeState"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:mergeImg:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:mergeImg:remove']"
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

    <!-- 添加或修改融合图片对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" disabled>
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="上传图片url">
          <image-upload v-model="form.imgUrl" :limit="1"/>
        </el-form-item>
        <el-form-item label="模板图片id" prop="modelId">
          <el-input v-model="form.modelId" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="融合图片url">
          <image-upload v-model="form.mergeUrl" :limit="1"/>
        </el-form-item>
        <el-form-item label="融合时间" prop="mergeTime">
          <el-date-picker clearable
            v-model="form.mergeTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择融合时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="融合状态" prop="mergeState">
          <el-select v-model="form.mergeState" placeholder="请选择融合状态">
            <el-option
              v-for="dict in dict.type.biz_merge_state"
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
import { listMergeImg, getMergeImg, delMergeImg, addMergeImg, updateMergeImg } from "@/api/biz/mergeImg";

export default {
  name: "MergeImg",
  dicts: ['biz_merge_state'],
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
      // 融合图片表格数据
      mergeImgList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        imgUrl: null,
        modelId: null,
        mergeUrl: null,
        mergeTime: null,
        mergeState: null
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
    /** 查询融合图片列表 */
    getList() {
      this.loading = true;
      listMergeImg(this.queryParams).then(response => {
        this.mergeImgList = response.rows;
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
        modelId: null,
        mergeUrl: null,
        mergeTime: null,
        mergeState: null
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
      this.title = "添加融合图片";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMergeImg(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改融合图片";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMergeImg(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMergeImg(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除融合图片编号为"' + ids + '"的数据项？').then(function() {
        return delMergeImg(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/mergeImg/export', {
        ...this.queryParams
      }, `mergeImg_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
