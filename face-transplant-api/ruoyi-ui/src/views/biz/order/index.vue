<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="支付用户id" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入支付用户"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="支付结果" prop="payResult">
        <el-select v-model="queryParams.payResult" placeholder="请选择支付结果(0:待支付，1:已支付)" clearable>
          <el-option
            v-for="dict in dict.type.biz_order_pay"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      
      <el-form-item label="微信支付订单号" prop="transactionId">
        <el-input
          v-model="queryParams.transactionId"
          placeholder="请输入微信支付订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['biz:order:add']"
        >新增</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['biz:order:edit']"
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
          v-hasPermi="['biz:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['biz:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单号" align="center" prop="orderId" />
      <el-table-column label="订单流水Id" align="center" prop="orderNo" />
      <el-table-column label="支付用户" align="center" prop="userId" />
      <!-- <el-table-column label="订单版本号" align="center" prop="version" /> -->
      <el-table-column label="套餐Code" align="center" prop="mealId" />
      <el-table-column label="支付结果" align="center" prop="payResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.biz_order_pay" :value="scope.row.payResult"/>
        </template>
      </el-table-column>
      <!-- <el-table-column label="支付方式(1:微信支付)" align="center" prop="payWay" /> -->
      <el-table-column label="支付金额" align="center" prop="payMoney" />
      <el-table-column label="微信支付订单号" align="center" prop="transactionId" />    
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['biz:order:edit']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['biz:order:remove']"
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

    <!-- 添加或修改支付记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px" disabled>
        <el-form-item label="支付用户" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入支付用户" />
        </el-form-item>
        <el-form-item label="订单版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入订单版本号" />
        </el-form-item>
        <el-form-item label="套餐Code" prop="mealId">
          <el-input v-model="form.mealId" placeholder="请输入套餐Code" />
        </el-form-item>
        <el-form-item label="支付结果" prop="payResult">
          <el-select v-model="form.payResult" placeholder="请选择支付结果">
            <el-option
              v-for="dict in dict.type.biz_order_pay"
              :key="dict.value"
              :label="dict.label"
:value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
       
        <el-form-item label="支付金额" prop="payMoney">
          <el-input v-model="form.payMoney" placeholder="请输入支付金额" />
        </el-form-item>
        <el-form-item label="微信支付订单号" prop="transactionId">
          <el-input v-model="form.transactionId" placeholder="请输入微信支付订单号" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/biz/order";

export default {
  name: "Order",
  dicts: ['biz_order_pay'],
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
      // 支付记录表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        userId: null,
        version: null,
        mealId: null,
        payResult: null,
        payWay: null,
        payMoney: null,
        transactionId: null,
        entry: null,
        client: null,
        validDay: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单流水Id不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "支付用户不能为空", trigger: "blur" }
        ],
        mealId: [
          { required: true, message: "套餐Code不能为空", trigger: "blur" }
        ],
        payResult: [
          { required: true, message: "支付结果(0:待支付，1:已支付)不能为空", trigger: "change" }
        ],
        payWay: [
          { required: true, message: "支付方式(1:微信支付)不能为空", trigger: "blur" }
        ],
        payMoney: [
          { required: true, message: "支付金额不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "支付时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询支付记录列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orderId: null,
        orderNo: null,
        userId: null,
        version: null,
        mealId: null,
        payResult: null,
        payWay: null,
        payMoney: null,
        transactionId: null,
        createTime: null,
        updateTime: null,
        entry: null,
        client: null,
        validDay: null
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加支付记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改支付记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除支付记录编号为"' + orderIds + '"的数据项？').then(function() {
        return delOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('biz/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
