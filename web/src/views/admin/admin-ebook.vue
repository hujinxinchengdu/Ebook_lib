<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <p>
        <a-button type="primary" @click="add()" size="large">
          Add
        </a-button>
      </p>
      <a-table
          :columns="columns"
          :row-key = "record => record.id"
          :data-source="ebooks"
          :pagination = "pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text:cover }">
          <img v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{text, record}">
          <a-space>
            <a-button type="primary" @click="edit(record)">
              Edit
            </a-button>
            <a-popconfirm
                title="Are you sure delete this task? After delete data Will Delete Forever"
                ok-text="Yes"
                cancel-text="No"
                @confirm="handleDelete(record.id)"
            >
              <a-button type="danger">
                Delete
              </a-button>
            </a-popconfirm>

          </a-space>
        </template>
      </a-table>
    </a-layout-content>
  </a-layout>
<!--  edit E-book Table-->
  <a-modal
      title="E-Book Table"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleModalOk"
  >
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <a-form-item label="Cover">
        <a-input v-model:value="ebook.cover" />
      </a-form-item>
      <a-form-item label="Name">
        <a-input v-model:value="ebook.name" />
      </a-form-item>
<!--      <a-form-item label="Category">-->
<!--        <a-cascader-->
<!--            v-model:value="categoryIds"-->
<!--            :field-names="{ label: 'name', value: 'id', children: 'children' }"-->
<!--            :options="level1"-->
<!--        />-->
<!--      </a-form-item>-->
      <a-form-item label="Category One">
        <a-input v-model:value="ebook.category1Id" />
      </a-form-item>
      <a-form-item label="Category Two">
        <a-input v-model:value="ebook.category2Id" />
      </a-form-item>
      <a-form-item label="Description">
        <a-input v-model:value="ebook.description" type="textarea" />
      </a-form-item>
    </a-form>

  </a-modal>
</template>

<script lang="ts">
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue';
import { defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue';

  export default defineComponent({
    name: 'AdminEbook',


    setup() {
      // const param = ref();
      // param.value = {};
      const ebooks = ref();
      const pagination = ref({
        current: 1,
        pageSize: 10,
        total: 0
      });
      const loading = ref(false);
      const columns = [
        {
          title: 'Cover',
          dataIndex: 'cover',
          slots: {customRender: 'cover'}
        },
        {
          title: 'Name',
          dataIndex: 'name'
        },
        {
          title: 'Category 1',
          dataIndex: 'category1Id'
        },
        {
          title: 'Category 2',
          dataIndex: 'category2Id'
        },
        {
          title: 'Documents',
          dataIndex: 'docCount'
        },
        {
          title: 'Views',
          dataIndex: 'viewCount'
        },
        {
          title: 'Likes',
          dataIndex: 'voteCount'
        },
        {
          title: 'Action',
          key: 'action',
          slots: {customRender: 'action'}
        }
      ];

      /**
       * 数据查询
       **/
      const handleQuery = (params: any) => {
        loading.value = true;
        // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
        ebooks.value = [];
        axios.get("/ebook/list", {
          params: {
            page: params.page,
            size: params.size
          }
        }).then((response) => {
          loading.value = false;
          const data = response.data;
          console.log(data)
          if(data.success) {
            ebooks.value = data.content.list;
            //重置分页按钮
            pagination.value.current = params.page;
            pagination.value.total = data.content.total;
          } else {
            message.error(data.message);
          }

        });
      };
      /**
       * 表格点击页码时触发
       */
      const handleTableChange = (pagination: any) => {
        console.log("看看自带的分页参数都有啥：" + pagination);
        handleQuery({
          page: pagination.current,
          size: pagination.pageSize
        });
      }

      //------------------Table-----------------
      const ebook = ref({});
      const modalVisible = ref<boolean>(false);
      const modalLoading = ref<boolean>(false);
      const handleModalOk = () => {
        modalLoading.value = true; //点击保存的时候,显示loading的效果
        axios.post("/ebook/save", ebook.value).then((response) => {
          //拿到结果后将loading效果去掉,并且让窗口消失.
          modalLoading.value = false;
          const data = response.data; //data = commonResp
          if (data.success) {
            modalVisible.value = false;

            //重新加载列表
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize,
            });
          } else {
            message.error(data.message);
          }
        });
      };

      /**
       * Edit an E-book
       */
      const edit = (record: any) => {
        modalVisible.value = true;
        ebook.value = record;
      };

      /**
       * Add new E-book
       */
      const add = () => {
        modalVisible.value = true;
        ebook.value = {};
      };

      const handleDelete = (id: number) => {
        axios.delete("/ebook/delete/" + id).then((response) => {
          const data = response.data; //data = commonResp
          if (data.success) {
            //重新加载列表
            handleQuery({
              page: pagination.value.current,
              size: pagination.value.pageSize,
            });
          }
        });
      };

      onMounted(() => {
        handleQuery({
          //这里必须和后端PageReq类的参数名字相同, 这样Controller才会将前端的参数自动映射到后端
          page: 1,
          size: pagination.value.pageSize
        });
      });

      return {
        ebooks,
        pagination,
        columns,
        loading,
        handleTableChange,

        edit,
        add,
        handleDelete,

        ebook,
        modalVisible,
        modalLoading,
        handleModalOk

      };
    },
    components: {
      SmileOutlined,
      DownOutlined,
    },
  });

</script>
<style scoped>
img {
  width: 50px;
  height: 50px;
}
</style>