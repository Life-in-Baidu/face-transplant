<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="套餐名称" prop="mealName">
        <el-input
          v-model="queryParams.mealName"
          placeholder="请输入套餐名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="套餐价格" prop="mealPrice">
        <el-input
          v-model="queryParams.mealPrice"
          placeholder="请输入套餐价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="套餐描述" prop="mealDescribe">
        <el-input
          v-model="queryParams.mealDescribe"
          placeholder="请输入套餐描述"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="排序" prop="sort">
        <el-input
          v-model="queryParams.sort"
          placeholder="请输入排序"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="赠送普通积分" prop="bonus">
        <el-input
          v-model="queryParams.bonus"
          placeholder="请输入赠送普通积分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="普通积分" prop="validNum">
        <el-input
          v-model="queryParams.validNum"
          placeholder="请输入普通积分"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="有效天数" prop="validDay">
        <el-input
          v-model="queryParams.validDay"
          placeholder="请输入有效天数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="控制上线" prop="control">
        <el-select v-model="queryParams.control" placeholder="请选择控制上线" clearable>
          <el-option
            v-for="dict in dict.type.biz_control_state"
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
          v-hasPermi="['biz:setmeal:add']"
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
          v-hasPermi="['biz:setmeal:edit']"
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
          v-hasPermi="['biz:setmeal:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:setmeal:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="setmealList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="套餐Code" align="center" prop="mealId" />
      <el-table-column label="套餐名称" align="center" prop="mealName" />
      <el-table-column label="套餐价格" align="center" prop="mealPrice" />
      <el-table-column label="套餐描述" align="center" prop="mealDescribe" />
      <!-- <el-table-column label="排序" align="center" prop="sort" /> -->
      <el-table-column label="普通积分" align="center" prop="validNum" />
      <el-table-column label="赠送普通积分" align="center" prop="bonus" />
      
      <!-- <el-table-column label="有效天数" align="center" prop="validDay" /> -->
      <el-table-column label="控制上线" align="center" prop="control">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_control_state" :value="scope.row.control"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:setmeal:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:setmeal:remove']"
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

    <!-- 添加或修改套餐对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="套餐名称" prop="mealName">
          <el-input v-model="form.mealName" placeholder="请输入套餐名称" />
        </el-form-item>
        <el-form-item label="套餐价格(单位：分)" prop="mealPrice">
          <el-input v-model="form.mealPrice" placeholder="请输入套餐价格(单位：分)" />
        </el-form-item>
        <el-form-item label="套餐描述" prop="mealDescribe">
          <el-input v-model="form.mealDescribe" placeholder="请输入套餐描述" />
        </el-form-item>
        <!-- <el-form-item label="排序" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序" />
        </el-form-item> -->
        <el-form-item label="普通积分" prop="validNum">
          <el-input v-model="form.validNum" placeholder="请输入普通积分" />
        </el-form-item>
        <el-form-item label="赠送普通积分" prop="bonus">
          <el-input v-model="form.bonus" placeholder="请输入赠送普通积分" />
        </el-form-item>
        
        <!-- <el-form-item label="有效天数" prop="validDay">
          <el-input v-model="form.validDay" placeholder="请输入有效天数" />
        </el-form-item> -->
        <el-form-item label="控制上线" prop="control">
          <el-select v-model="form.control" placeholder="请选择控制上线">
            <el-option
              v-for="dict in dict.type.biz_control_state"
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
import { listSetmeal, getSetmeal, delSetmeal, addSetmeal, updateSetmeal } from "@/api/biz/setmeal";

export default {
  name: "Setmeal",
  dicts: ['biz_control_state'],
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
      // 套餐表格数据
      setmealList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        mealName: null,
        mealPrice: null,
        mealDescribe: null,
        sort: null,
        bonus: null,
        validNum: null,
        validDay: null,
        control: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        mealName: [
          { required: true, message: "套餐名称不能为空", trigger: "blur" }
        ],
        mealPrice: [
          { required: true, message: "套餐价格不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询套餐列表 */
    getList() {
      this.loading = true;
      listSetmeal(this.queryParams).then(response => {
        this.setmealList = response.rows;
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
        mealId: null,
        mealName: null,
        mealPrice: null,
        mealDescribe: null,
        sort: null,
        bonus: null,
        validNum: null,
        validDay: null,
        createTime: null,
        updateTime: null,
        control: null
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
      this.ids = selection.map(item => item.mealId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加套餐";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const mealId = row.mealId || this.ids
      getSetmeal(mealId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改套餐";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.mealId != null) {
            updateSetmeal(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSetmeal(this.form).then(response => {
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
      const mealIds = row.mealId || this.ids;
      this.$modal.confirm('是否确认删除套餐编号为"' + mealIds + '"的数据项？').then(function() {
        return delSetmeal(mealIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/setmeal/export', {
        ...this.queryParams
      }, `setmeal_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
